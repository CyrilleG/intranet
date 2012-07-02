package models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import exceptions.AccessNotAllowedException;
import exceptions.DataFormatException;
import exceptions.DataLengthException;
import exceptions.ElementNotFoundException;
import exceptions.FatalErrorException;
import exceptions.NotEmptyException;
import exceptions.NotUniqueException;
import utils.Tools;
import utils.Utils;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_user")
@RooDbManaged(automaticallyDelete = true)
public class AppUser {

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<DataUser> dataUsers;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<InfoPrivacity> infoPrivacities;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserFilter> userFilters;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserGroup> userGroups;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<UserRight> userRights;

	@Column(name = "login", columnDefinition = "VARCHAR", length = 100, unique = true)
	@NotNull
	private String login;

	@Column(name = "password", columnDefinition = "VARCHAR", length = 100)
	@NotNull
	private String password;

	@Column(name = "enabled", columnDefinition = "BIT")
	@NotNull
	private boolean enabled;

	public static AppUser findByLogin(String login)
			throws ElementNotFoundException, DataLengthException, NotEmptyException {
		
		if (login == null || login.length() == 0)
			throw new NotEmptyException("login cannot be empty");
		
		if (login.length() > 100)
			throw new DataLengthException("login parameter is too long (max: 100 carac)");
		
		List<AppUser> elements = AppUser.findAllAppUsers();
		for (AppUser element : elements)
			if (element.getLogin().compareToIgnoreCase(login) == 0)
				return element;
		throw new ElementNotFoundException("No User Object found with login: "
				+ login);
	}

	public static AppUser create(String login, String password, boolean enabled)
			throws AccessNotAllowedException, NotEmptyException, DataLengthException, ElementNotFoundException, NotUniqueException {
		
		if (login == null || login.length() == 0)
			throw new NotEmptyException("ident cannot be empty");
		
		if (password == null || password.length() == 0)
			throw new NotEmptyException("ident cannot be empty");
		
		if (login.length() > 100)
			throw new DataLengthException("login parameter is too long (max: 100 carac)");
		if (password.length() > 100)
			throw new DataLengthException("password parameter is too long (max: 100 carac)");
		
		if (isLoginExist(login))
			throw new NotUniqueException("login has to be unique");
		
		if (Tools.hasRight("ADD_USER")) {
			AppUser user = new AppUser();
			user.setLogin(login);
			user.setPassword(password);
			user.setEnabled(enabled);
			user.persist();
			return user;
		} else
			throw new AccessNotAllowedException("You can't add a user entry");
	}

	public static boolean isLoginExist(String login) throws DataLengthException, NotEmptyException
	{
		try {
			return findByLogin(login) != null;
		} catch (ElementNotFoundException e) {
			return false;
		}
	}
	public Set<AppRight> getAllAuthorities() {
		Set<AppRight> rights = new HashSet<AppRight>();
		for (UserRight g : userRights)
			rights.add(g.getRight());
		for (UserGroup g : userGroups)
			rights.addAll(g.getGroup().getRights());
		return rights;
	}

	public Set<AppGroup> getHisGroup() {
		Set<AppGroup> groups = new HashSet<AppGroup>();
		for (UserGroup g : userGroups)
			groups.add(g.getGroup());
		return groups;
	}

	public static boolean logIn(String login, String password) throws NotEmptyException, DataLengthException {
		
		if (login == null || login.length() == 0)
			throw new NotEmptyException("login cannot be empty");
		
		if (password == null || password.length() == 0)
			throw new NotEmptyException("password cannot be empty");
		
		if (login.length() > 100)
			throw new DataLengthException("login parameter is too long (max: 100 carac)");
		
		if (password.length() > 100)
			throw new DataLengthException("password parameter is too long (max: 100 carac)");
		
		return false;
	}

	public static void logOut() {

	}

	public ModuleData getModuleData(String name)
			throws AccessNotAllowedException, ElementNotFoundException, NotEmptyException, DataLengthException {
		
		if (name == null || name.length() == 0)
			throw new NotEmptyException("name cannot be empty");
		
		if (name.length() > 100)
			throw new DataLengthException("name parameter is too long (max: 100 carac)");
		
		if (Tools.hasRight("GET_DATA_FROM_OTHER_USER")
				|| Tools.getUser().equals(this)) {
			for (DataUser data : dataUsers)
				if (data.getData().getName().compareTo(name) == 0)
					return data.getData();
			throw new ElementNotFoundException(
					"No Data Object found with name: " + name
							+ " for this user");
		} else
			throw new AccessNotAllowedException(
					"You can't get data from this user");
	}

	public void removeData(ModuleData data) throws AccessNotAllowedException, NotEmptyException {
		
		if (data == null)
			throw new NotEmptyException("data cannot be empty");
		
		if (Tools.hasRight("REMOVE_DATA_FROM_OTHER_USER")
				|| Tools.getUser().equals(data)) {
			for (DataUser elt : dataUsers)
				if (elt.getUser().equals(data))
					elt.remove();
		} else
			throw new AccessNotAllowedException("You can't add data to user");
	}

	public void addModule(ModuleData data) throws AccessNotAllowedException, NotEmptyException {
		
		if (data == null)
			throw new NotEmptyException("data cannot be empty");
		
		if (Tools.hasRight("ADD_DATA_TO_OTHER_USER")
				|| Tools.getUser().equals(data)) {
			DataUser elem = new DataUser();
			elem.setData(data);
			elem.setUser(this);
			elem.persist();
			dataUsers.add(elem);
		} else
			throw new AccessNotAllowedException(
					"You can't remove data from user");
	}

	public boolean hasData(ModuleData data) throws NotEmptyException {
		
		if (data == null)
			throw new NotEmptyException("data cannot be empty");
		
		for (DataUser elem : dataUsers)
			if (elem.getUser().equals(data))
				return true;
		return false;
	}

	public void addFilter(AppFilter filter) throws AccessNotAllowedException, NotEmptyException {
		
		if (filter == null)
			throw new NotEmptyException("filter cannot be empty");
		
		if (Tools.hasRight("ADD_FILTER_TO_USER")) {
			UserFilter group = new UserFilter();
			group.setFilter(filter);
			group.setUser(this);
			userFilters.add(group);
			group.persist();
		} else
			throw new AccessNotAllowedException("You can't add filter to user");
	}

	public void removeFilter(AppFilter filter) throws AccessNotAllowedException, NotEmptyException {
		
		if (filter == null)
			throw new NotEmptyException("filter cannot be empty");
		
		if (Tools.hasRight("REMOVE_FILTER_FROM_USER")) {
			for (UserFilter gp : userFilters)
				if (gp.getFilter().equals(filter)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove filter from user");
	}

	public boolean hasFilter(AppFilter filter) throws NotEmptyException {
		
		if (filter == null)
			throw new NotEmptyException("filter cannot be empty");
		
		for (UserFilter elem : userFilters)
			if (elem.getFilter().equals(filter))
				return true;
		return false;
	}

	public void applyPreFilter(AppFilter filter) throws FatalErrorException,
			NotEmptyException, DataLengthException {
		filter.executePreFilter();
	}

	public void applyPostFilter(AppFilter filter) throws FatalErrorException,
			NotEmptyException, DataLengthException {
		filter.executePostFilter();
	}

	public void applyHisPreFilters() throws FatalErrorException,
			NotEmptyException, DataLengthException {
		for (UserFilter filter : userFilters)
			filter.getFilter().executePreFilter();
	}

	public void applyHisPostFilters() throws FatalErrorException,
			NotEmptyException, DataLengthException {
		for (UserFilter filter : userFilters)
			filter.getFilter().executePostFilter();
	}

	public void addGroup(AppGroup group) throws AccessNotAllowedException, NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		if (Tools.hasRight("ADD_USER_TO_GROUP")) {
			UserGroup groupuser = new UserGroup();
			groupuser.setGroup(group);
			groupuser.setUser(this);
			groupuser.persist();
			userGroups.add(groupuser);
		} else
			throw new AccessNotAllowedException("You can't add user to group");
	}

	public void removeGroup(AppGroup group) throws AccessNotAllowedException, NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		if (Tools.hasRight("REMOVE_USER_FROM_GROUP")) {
			for (UserGroup gp : userGroups)
				if (gp.getGroup().equals(group)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove user from group");
	}

	public boolean hasGroup(AppGroup group) throws NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		for (UserGroup elem : userGroups)
			if (elem.getGroup().equals(group))
				return true;
		return false;
	}

	public void addAccess(UserInfo info, AppGroup privacity)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (info == null)
			throw new NotEmptyException("info cannot be empty");
		
		if (privacity == null)
			throw new NotEmptyException("privacity cannot be empty");
		
		if (Tools.hasRight("ADD_INFO_TO_OTHER_USER")
				|| Tools.hasRight("ADD_INFO")) {
			InfoPrivacity priv = new InfoPrivacity();
			priv.setInfo(info);
			priv.setGroup(privacity);
			priv.setUser(this);
			priv.persist();
		} else
			throw new AccessNotAllowedException(
					"You can't add information to user");
	}

	public UserInfo getInformation(String key) throws ElementNotFoundException, NotEmptyException, DataFormatException, DataLengthException {
		
		if (key == null || key.length() == 0)
			throw new NotEmptyException("key cannot be empty");

		if (key.length() > 100)
			throw new DataLengthException("key parameter is too long (max: 100 carac)");
		
		if (!Utils.regexMatch(key, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"key parameter has to match with ([a-zA-Z0-9]+)");
		
		for (InfoPrivacity element : infoPrivacities)
			if (element.getInfo().getKey().compareTo(key) == 0
					&& (Tools.getUser().hasGroup(element.getGroup()) || Tools
							.getUser().isAdmin()))
				return element.getInfo();
		throw new ElementNotFoundException(
				"No Information Object found with key: " + key
						+ " for this user");

	}

	public void removeInformation(UserInfo info)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (info == null)
			throw new NotEmptyException("info cannot be empty");
		
		if (Tools.hasRight("REMOVE_INFO_FROM_OTHER_USER") || info.isEditable()) {
			for (InfoPrivacity element : infoPrivacities)
				if (element.getInfo().equals(info)) {
					element.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove information from user");
	}

	public void addRight(AppRight right) throws AccessNotAllowedException, NotEmptyException {
		
		if (right == null)
			throw new NotEmptyException("right cannot be empty");
		
		if (Tools.hasRight("ADD_RIGHT_TO_USER")) {
			UserRight element = new UserRight();
			element.setRight(right);
			element.setUser(this);
			element.persist();
			userRights.add(element);
		} else
			throw new AccessNotAllowedException("You can't add right to user");
	}

	public boolean hasRight(AppRight right) throws NotEmptyException {
		
		if (right == null)
			throw new NotEmptyException("right cannot be empty");
		
		for (UserRight item : userRights)
			if (item.getRight().equals(right))
				return true;
		return false;
	}

	public boolean hasRight(String right) throws NotEmptyException {
		
		if (right == null)
			throw new NotEmptyException("right cannot be empty");
		
		for (UserRight item : userRights)
			if (item.getRight().getIdent().compareToIgnoreCase(right) == 0)
				return true;
		return false;
	}

	public void removeRight(AppRight right) throws AccessNotAllowedException, NotEmptyException {
		
		if (right == null)
			throw new NotEmptyException("right cannot be empty");
		
		if (Tools.hasRight("REMOVE_RIGHT_FROM_USER")) {
			for (UserRight gp : userRights)
				if (gp.getRight().equals(right)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from user");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) throws AccessNotAllowedException, NotEmptyException, DataLengthException, NotUniqueException, ElementNotFoundException {
		
		if (login == null || login.length() == 0)
			throw new NotEmptyException("key cannot be empty");
		if (login.length() > 100)
			throw new DataLengthException("login parameter is too long (max: 100 carac)");
		
		if (isLoginExist(login))
			throw new NotUniqueException("login has to be unique");
		
		if (Tools.hasRight("SET_USER_LOGIN") || Tools.hasRight("SET_HIS_LOGIN"))
			this.login = login;
		else
			throw new AccessNotAllowedException(
					"You can't edit a user login as: " + login);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws AccessNotAllowedException, NotEmptyException, DataLengthException {
		
		if (password == null || password.length() == 0)
			throw new NotEmptyException("key cannot be empty");
		
		if (login.length() > 100)
			throw new DataLengthException("login parameter is too long (max: 100 carac)");
		
		if (Tools.hasRight("SET_USER_PASSWORD")
				|| Tools.hasRight("SET_HIS_PASSWORD"))
			this.password = password;
		else
			throw new AccessNotAllowedException(
					"You can't edit a user password");
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_USER_ENABLED"))
			this.enabled = enabled;
		else
			throw new AccessNotAllowedException(
					"You can't edit a user enabled as: " + enabled);
	}

	public boolean isAdmin() {
		try {
			return hasGroup(AppGroup.findByIdent("ADMIN"));
		} catch (ElementNotFoundException e) {
			// throw new
			// FatalErrorException("ADMIN not present. Please resintall application");
			return false;
		} catch (NotEmptyException e) {
			return false; // never use
		} catch (DataFormatException e) {
			return false; // never use
		} catch (DataLengthException e) {
			return false; // never use
		}
	}

	@PersistenceContext
    transient EntityManager entityManager;

	static final EntityManager entityManager() {
        EntityManager em = new AppUser().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	static long countAppUsers() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AppUser o", Long.class).getSingleResult();
    }

	static List<AppUser> findAllAppUsers() {
        return entityManager().createQuery("SELECT o FROM AppUser o", AppUser.class).getResultList();
    }

	static AppUser findAppUser(Integer user) {
        if (user == null) return null;
        return entityManager().find(AppUser.class, user);
    }

	static List<AppUser> findAppUserEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AppUser o", AppUser.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

	@Transactional
	void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

	@Transactional
	void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AppUser attached = this;
            this.entityManager.remove(attached);
        }
    }

	@Transactional
	void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

	@Transactional
	void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

	@Transactional
	AppUser merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AppUser merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
