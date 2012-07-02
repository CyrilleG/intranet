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
import exceptions.NotEmptyException;
import exceptions.NotUniqueException;

import utils.Tools;
import utils.Utils;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_group")
@RooDbManaged(automaticallyDelete = true)
public class AppGroup {

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<ActionGroup> actionGroups;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<DataGroup> dataGroups;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<GroupFilter> groupFilters;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<GroupRight> groupRights;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<InfoPrivacity> infoPrivacities;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<ModuleGroup> moduleGroups;

	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<UserGroup> userGroups;

	@Column(name = "ident", columnDefinition = "VARCHAR", length = 70, unique = true)
	@NotNull
	private String ident;

	@Column(name = "label", columnDefinition = "VARCHAR", length = 100)
	@NotNull
	private String label;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	public Set<AppRight> getRights() {
		Set<AppRight> rights = new HashSet<AppRight>();
		for (GroupRight g : groupRights)
			rights.add(g.getRight());
		return rights;
	}

	public static AppGroup findByIdent(String ident)
			throws ElementNotFoundException, NotEmptyException,
			DataFormatException, DataLengthException {

		if (ident == null || ident.length() == 0)
			throw new NotEmptyException("ident cannot be empty");

		if (ident.length() > 70)
			throw new DataLengthException("ident parameter is too long (max: 70 carac)");
		
		if (!Utils.regexMatch(ident, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"ident parameter has to match with ([a-zA-Z0-9]+)");

		List<AppGroup> elements = AppGroup.findAllAppGroups();
		for (AppGroup element : elements)
			if (element.getIdent().compareToIgnoreCase(ident) == 0)
				return element;
		throw new ElementNotFoundException("No Group Object with ident: "
				+ ident);
	}

	public static AppGroup create(String ident, String label, String description)
			throws AccessNotAllowedException, NotEmptyException,
			DataFormatException, DataLengthException, NotUniqueException {

		if (ident == null || ident.length() == 0)
			throw new NotEmptyException("ident cannot be empty");
		if (label == null || ident.length() == 0)
			throw new NotEmptyException("label cannot be empty");
		if (!Utils.regexMatch(ident, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"ident parameter has to match with ([a-zA-Z0-9]+)");

		if (ident.length() > 70)
			throw new DataLengthException("ident parameter is too long (max: 70 carac)");
		
		if (label.length() > 100)
			throw new DataLengthException("label parameter is too long (max: 100 carac)");
		
		if (isIdentExist(ident))
			throw new NotUniqueException("ident has to be unique");
		
		if (Tools.hasRight("ADD_GROUP")) {
			AppGroup group = new AppGroup();
			group.setIdent(ident);
			group.setLabel(label);
			group.setDescription(description);
			group.persist();
			return group;
		} else
			throw new AccessNotAllowedException("You can't add a group entry");
	}

	
	public static boolean isIdentExist(String ident) throws NotEmptyException, DataFormatException, DataLengthException
	{
		try {
			return findByIdent(ident) != null;
		} catch (ElementNotFoundException e) {
			return false;
		}
	}
	
	public void addRight(AppRight right) throws AccessNotAllowedException,
			NotEmptyException {
		if (right == null)
			throw new NotEmptyException("right cannot be null");
		
		if (Tools.hasRight("ADD_RIGHT_TO_GROUP")) {
			GroupRight groupright = new GroupRight();
			groupright.setGroup(this);
			groupright.setRight(right);
			groupright.persist();
			groupRights.add(groupright);
		} else
			throw new AccessNotAllowedException("You can't add right to group");
	}

	public void disallowToAccess(AppRight right)
			throws AccessNotAllowedException, NotEmptyException {

		if (right == null)
			throw new NotEmptyException("right cannot be null");

		if (Tools.hasRight("REMOVE_RIGHT_FROM_GROUP")) {
			for (GroupRight gp : groupRights)
				if (gp.getRight().equals(right)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from group");
	}

	public boolean hasRight(String ident) throws DataFormatException,
			NotEmptyException, DataLengthException {

		if (ident == null || ident.length() == 0)
			throw new NotEmptyException("ident cannot be empty");
		if (!Utils.regexMatch(ident, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"ident parameter has to match with ([a-zA-Z0-9]+)");

		if (ident.length() > 70)
			throw new DataLengthException("ident parameter is too long (max: 70 carac)");
		
		for (GroupRight gp : groupRights)
			if (gp.getRight().getIdent().compareTo(ident) == 0)
				return true;
		return false;
	}

	public boolean hasRight(AppRight right) throws DataFormatException,
			NotEmptyException, DataLengthException {
		return hasRight(right.getIdent());
	}

	public void allowToAccess(AppUser user) throws AccessNotAllowedException,
			NotEmptyException {
		if (Tools.hasRight("ADD_USER_TO_GROUP")
				|| Tools.hasRight("ADD_HIMSELF_TO_GROUP_")) {

			if (user == null)
				throw new NotEmptyException("user cannot be null");

			UserGroup groupuser = new UserGroup();
			groupuser.setGroup(this);
			groupuser.setUser(user);
			groupuser.persist();
			userGroups.add(groupuser);
		} else
			throw new AccessNotAllowedException("You can't add user to group");
	}

	public void disallowToAccess(AppUser user)
			throws AccessNotAllowedException, NotEmptyException {

		if (user == null)
			throw new NotEmptyException("user cannot be null");

		if (Tools.hasRight("REMOVE_USER_FROM_GROUP")) {
			for (UserGroup gp : userGroups)
				if (gp.getUser().equals(user)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from group");
	}

	public boolean userHasGroup(AppUser user) throws NotEmptyException {
		if (user == null)
			throw new NotEmptyException("user cannot be null");

		for (UserGroup elem : userGroups)
			if (elem.getUser().equals(user))
				return true;
		return false;
	}

	public void allowToAccess(ModuleData data)
			throws AccessNotAllowedException, NotEmptyException {

		if (data == null)
			throw new NotEmptyException("data cannot be null");

		if (Tools.hasRight("ADD_DATA_TO_GROUP")) {
			DataGroup element = new DataGroup();
			element.setGroup(this);
			element.setData(data);
			element.persist();
			dataGroups.add(element);
		} else
			throw new AccessNotAllowedException("You can't data right to group");
	}

	public void disallowToAccess(ModuleData data)
			throws AccessNotAllowedException, NotEmptyException {

		if (data == null)
			throw new NotEmptyException("data cannot be null");

		if (Tools.hasRight("REMOVE_DATA_FROM_GROUP")) {
			for (DataGroup gp : dataGroups)
				if (gp.getData().equals(data)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove data from group");
	}

	public boolean isAccessAllow(ModuleData data) throws NotEmptyException {
		if (data == null)
			throw new NotEmptyException("data cannot be null");

		for (DataGroup g : dataGroups)
			if (g.getData().equals(data))
				return true;
		return false;
	}

	public void allowToAccess(AppModule module)
			throws AccessNotAllowedException, NotEmptyException {

		if (module == null)
			throw new NotEmptyException("module cannot be null");

		if (Tools.hasRight("ADD_MODULE_TO_GROUP")) {
			ModuleGroup groupuser = new ModuleGroup();
			groupuser.setGroup(this);
			groupuser.setModule(module);
			groupuser.persist();
			moduleGroups.add(groupuser);
		} else
			throw new AccessNotAllowedException("You can't add module to group");
	}

	public void disallowToAccess(AppModule module)
			throws AccessNotAllowedException, NotEmptyException {

		if (module == null)
			throw new NotEmptyException("module cannot be null");

		if (Tools.hasRight("REMOVE_MODULE_FROM_GROUP")) {
			for (ModuleGroup gp : moduleGroups)
				if (gp.getModule().equals(module)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove module from group");
	}

	public boolean isAccessAllow(AppModule module) throws NotEmptyException {

		if (module == null)
			throw new NotEmptyException("module cannot be null");

		for (ModuleGroup item : moduleGroups)
			if (item.getModule().equals(module))
				return true;
		return false;
	}

	public void allowToAccess(ModuleAction action)
			throws AccessNotAllowedException, NotEmptyException {

		if (action == null)
			throw new NotEmptyException("action cannot be null");

		if (Tools.hasRight("ADD_ACTION_TO_GROUP")) {
			ActionGroup groupaction = new ActionGroup();
			groupaction.setGroup(this);
			groupaction.setAction(action);
			groupaction.persist();
			actionGroups.add(groupaction);
		} else
			throw new AccessNotAllowedException("You can't add action to group");
	}

	public void disallowToAccess(ModuleAction action)
			throws AccessNotAllowedException, NotEmptyException {
		if (action == null)
			throw new NotEmptyException("action cannot be null");

		if (Tools.hasRight("REMOVE_ACTION_FROM_GROUP")) {
			for (ActionGroup gp : actionGroups)
				if (gp.getAction().equals(action)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove action from group");
	}

	public boolean isAccessAllow(ModuleAction action) throws NotEmptyException {

		if (action == null)
			throw new NotEmptyException("action cannot be null");

		for (ActionGroup item : actionGroups)
			if (item.getAction().equals(action))
				return true;
		return false;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) throws AccessNotAllowedException,
			NotEmptyException, DataLengthException {

		if (label == null || label.length() == 0)
			throw new NotEmptyException("label cannot be empty");

		if (label.length() > 100)
			throw new DataLengthException("ident parameter is too long (max: 100 carac)");
		
		if (Tools.hasRight("SET_GROUP_LABEL"))
			this.label = label;
		else
			throw new AccessNotAllowedException(
					"You can't edit a group entry label as: " + label);
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) throws AccessNotAllowedException,
			NotEmptyException, DataFormatException, DataLengthException, NotUniqueException {

		if (ident == null || ident.length() == 0)
			throw new NotEmptyException("name cannot be empty");

		if (ident.length() > 70)
			throw new DataLengthException("ident parameter is too long (max: 70 carac)");
		
		if (!Utils.regexMatch(ident, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"ident parameter has to match with ([a-zA-Z0-9]+)");

		if (isIdentExist(ident))
			throw new NotUniqueException("ident has to be unique");
		
		if (Tools.hasRight("SET_GROUP_IDENT"))
			this.ident = ident;
		else
			throw new AccessNotAllowedException(
					"You can't edit a group ident as: " + ident);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description)
			throws AccessNotAllowedException {
		if (Tools.hasRight("SET_GROUP_DESCRIPTION"))
			this.description = description;
		else
			throw new AccessNotAllowedException(
					"You can't edit group description");
	}

	public boolean isUse() {
		return actionGroups.size() > 0 || groupFilters.size() > 0
				|| groupRights.size() > 0 || infoPrivacities.size() > 0
				|| moduleGroups.size() > 0 || userGroups.size() > 0;
	}


	@PersistenceContext
    transient EntityManager entityManager;

	static final EntityManager entityManager() {
        EntityManager em = new AppGroup().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	static long countAppGroups() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AppGroup o", Long.class).getSingleResult();
    }

	static List<AppGroup> findAllAppGroups() {
        return entityManager().createQuery("SELECT o FROM AppGroup o", AppGroup.class).getResultList();
    }

	static AppGroup findAppGroup(Integer group) {
        if (group == null) return null;
        return entityManager().find(AppGroup.class, group);
    }

	static List<AppGroup> findAppGroupEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AppGroup o", AppGroup.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            AppGroup attached = this;//AppGroup.findAppGroup(this.group);
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
    AppGroup merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AppGroup merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
