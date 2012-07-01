package intranet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import exceptions.AccessNotAllowedException;
import exceptions.ElementNotFoundException;
import exceptions.FatalErrorException;
import utils.Tools;

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

	public static AppUser findFilterByLogin(String login) throws ElementNotFoundException {
		List<AppUser> elements = AppUser.findAllAppUsers();
		for (AppUser element : elements)
			if (element.getLogin().compareToIgnoreCase(login) == 0)
				return element;
		throw new ElementNotFoundException("No User Object found with login: "
				+ login);
	}

	public AppUser createUser(String login, String password, boolean enabled) throws AccessNotAllowedException {
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

	public Set<AppRight> getAllAuthorities() {
		Set<AppRight> rights = new HashSet<AppRight>();
		for (UserRight g : userRights)
			rights.add(g.getRight());
		for (UserGroup g : userGroups)
			rights.addAll(g.getGroup().getRights());
		return rights;
	}

	public Set<AppGroup> getAllGroups() {
		Set<AppGroup> groups = new HashSet<AppGroup>();
		for (UserGroup g : userGroups)
			groups.add(g.getGroup());
		return groups;
	}

	public static boolean logIn(String login, String password) {
		return false;
	}

	public static void logOut() {

	}

	public ModuleData getModuleData(String name) throws AccessNotAllowedException, ElementNotFoundException {
		if (Tools.hasRight("GET_DATA_FROM_OTHER_USER")
				|| Tools.getUser().equals(this)) {
			for (DataUser data : dataUsers)
				if (data.getData().getName().compareTo(name) == 0)
					return data.getData();
			throw new ElementNotFoundException("No Data Object found with name: "
					+ name + " for this user");
		}
		else
			throw new AccessNotAllowedException("You can't get data from this user");
	}

	public void removeDataFromUser(ModuleData ident) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_DATA_FROM_OTHER_USER")
				|| Tools.getUser().equals(ident)) {
			for (DataUser data : dataUsers)
				if (data.getUser().equals(ident))
					data.remove();
		} else
			throw new AccessNotAllowedException("You can't add data to user");
	}

	public void addDataToUser(ModuleData ident) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_DATA_TO_OTHER_USER")
				|| Tools.getUser().equals(ident)) {
			DataUser elem = new DataUser();
			elem.setData(ident);
			elem.setUser(this);
			elem.persist();
			dataUsers.add(elem);
		}
		else
			throw new AccessNotAllowedException("You can't remove data from user");
	}

	public boolean userHasData(ModuleData ident) {
		for (DataUser elem : dataUsers)
			if (elem.getUser().equals(ident))
				return true;
		return false;
	}

	public void addFilterToUser(AppFilter filter) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_FILTER_TO_USER")) {
			UserFilter group = new UserFilter();
			group.setFilter(filter);
			group.setUser(this);
			userFilters.add(group);
			group.persist();
		}
		else
			throw new AccessNotAllowedException("You can't add filter to user");
	}

	public void removeFilterFromUser(AppFilter filter) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_FILTER_FROM_USER")) {
			for (UserFilter gp : userFilters)
				if (gp.getFilter().equals(filter)) {
					gp.remove();
					break;
				}
		}
		else
			throw new AccessNotAllowedException("You can't remove filter from user");
	}

	public boolean userHasFilter(AppFilter filter) {
		for (UserFilter elem : userFilters)
			if (elem.getFilter().equals(filter))
				return true;
		return false;
	}

	public void applyPreFilter(AppFilter filter) throws FatalErrorException {
		filter.appliPreFilter();
	}

	public void applyPostFilter(AppFilter filter) throws FatalErrorException {
		filter.appliPostFilter();
	}

	public void applyUserPreFilters() throws FatalErrorException {
		for (UserFilter filter : userFilters)
			filter.getFilter().appliPreFilter();
	}

	public void applyUserPostFilters() throws FatalErrorException {
		for (UserFilter filter : userFilters)
			filter.getFilter().appliPostFilter();
	}

	public void addUserToGroup(AppGroup group) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_USER_TO_GROUP")) {
			UserGroup groupuser = new UserGroup();
			groupuser.setGroup(group);
			groupuser.setUser(this);
			groupuser.persist();
			userGroups.add(groupuser);
		}
		else
			throw new AccessNotAllowedException("You can't add user to group");
	}

	public void removeUserFromGroup(AppGroup group) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_USER_FROM_GROUP")) {
			for (UserGroup gp : userGroups)
				if (gp.getGroup().equals(group)) {
					gp.remove();
					break;
				}
		}
		else
			throw new AccessNotAllowedException("You can't remove user from group");
	}

	public boolean userHasGroup(AppGroup ident) {
		for (UserGroup elem : userGroups)
			if (elem.getGroup().equals(ident))
				return true;
		return false;
	}

	public void addInformationToUser(UserInfo info, AppGroup privacity) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_INFO_TO_OTHER_USER")
				|| Tools.hasRight("ADD_INFO")) {
			InfoPrivacity priv = new InfoPrivacity();
			priv.setInfo(info);
			priv.setGroup(privacity);
			priv.setUser(this);
			priv.persist();
		}
		else
			throw new AccessNotAllowedException("You can't add information to user");
	}

	public UserInfo getInformation(String key) throws ElementNotFoundException {
		for (InfoPrivacity element : infoPrivacities)
			if (element.getInfo().getKey().compareTo(key) == 0
					&& (Tools.getUser().userHasGroup(element.getGroup()) || Tools
							.getUser().isAdmin()))
				return element.getInfo();
		throw new ElementNotFoundException("No Information Object found with key: "
				+ key + " for this user");

	}

	public void removeInformationFromUser(UserInfo info) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_INFO_FROM_OTHER_USER") || info.isEditable()) {
			for (InfoPrivacity element : infoPrivacities)
				if (element.getInfo().equals(info)) {
					element.remove();
					break;
				}
		}
		else
			throw new AccessNotAllowedException("You can't remove information from user");
	}

	public void addRightToUser(AppRight right) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_RIGHT_TO_USER")) {
			UserRight element = new UserRight();
			element.setRight(right);
			element.setUser(this);
			element.persist();
			userRights.add(element);
		}
		else
			throw new AccessNotAllowedException("You can't add right to user");
	}

	public boolean userHasRight(AppRight right) {
		for (UserRight item : userRights)
			if (item.getRight().equals(right))
				return true;
		return false;
	}

	public boolean userHasRight(String right) {
		for (UserRight item : userRights)
			if (item.getRight().getIdent().compareToIgnoreCase(right) == 0)
				return true;
		return false;
	}

	public void removeRightFromUser(AppRight right) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_USER")) {
			for (UserRight gp : userRights)
				if (gp.getRight().equals(right)) {
					gp.remove();
					break;
				}
		}
		else
			throw new AccessNotAllowedException("You can't remove right from user");
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_USER_LOGIN") || Tools.hasRight("SET_HIS_LOGIN"))
			this.login = login;
		else
			throw new AccessNotAllowedException(
					"You can't edit a user login as: " + login);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) throws AccessNotAllowedException {
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
			return userHasGroup(AppGroup.findGroupByIdent("ADMIN"));
		} catch (ElementNotFoundException e) {
			//throw new FatalErrorException("ADMIN not present. Please resintall application");
			return false;
		}
	}
}
