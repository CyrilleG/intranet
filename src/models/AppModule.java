package models;

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

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_module")
@RooDbManaged(automaticallyDelete = true)
public class AppModule {
	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
	private Set<ModuleAction> moduleActions;

	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
	private Set<ModuleData> moduleDatas;

	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
	private Set<ModuleGroup> moduleGroups;

	@OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
	private Set<ModuleRight> moduleRights;

	@Column(name = "name", columnDefinition = "VARCHAR", length = 100, unique = true)
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "class", columnDefinition = "VARCHAR", length = 100)
	private String class1;

	@Column(name = "enabled", columnDefinition = "BIT")
	@NotNull
	private boolean enabled;

	public static AppModule findByName(String name)
			throws ElementNotFoundException, NotEmptyException,
			DataLengthException {

		if (name == null || name.length() == 0)
			throw new NotEmptyException("name cannot be empty");

		if (name.length() > 100)
			throw new DataLengthException(
					"ident parameter is too long (max: 100 carac)");

		List<AppModule> elements = AppModule.findAllAppModules();
		for (AppModule element : elements)
			if (element.getName().compareToIgnoreCase(name) == 0)
				return element;
		throw new ElementNotFoundException("No Module Object found with name: "
				+ name);
	}

	public static AppModule create(String name, String description,
			String controller) throws AccessNotAllowedException,
			NotEmptyException, DataLengthException, NotUniqueException,
			DataFormatException {

		if (name == null || name.length() == 0)
			throw new NotEmptyException("name cannot be empty");

		if (name.length() > 100)
			throw new DataLengthException(
					"ident parameter is too long (max: 100 carac)");

		if (controller == null || controller.length() == 0)
			throw new NotEmptyException("controller cannot be empty");

		if (isNameExist(name))
			throw new NotUniqueException("name has to be unique");

		if (Tools.hasRight("ADD_MODULE")) {
			AppModule module = new AppModule();
			module.setName(name);
			module.setDescription(description);
			module.setModuleController(controller);
			module.persist();
			return module;
		} else
			throw new AccessNotAllowedException("You can't add a module entry");
	}

	public static boolean isNameExist(String name) throws NotEmptyException,
			DataFormatException, DataLengthException {
		try {
			return findByName(name) != null;
		} catch (ElementNotFoundException e) {
			return false;
		}
	}

	public boolean install() {
		return false;
	}

	public void uninstall() {

	}

	public void addData(ModuleData data) throws AccessNotAllowedException,
			NotEmptyException {

		if (data == null)
			throw new NotEmptyException("data cannot be empty");

		if (Tools.hasRight("ADD_DATA_TO_MODULE")) {
			data.setModule(this);
			moduleDatas.add(data);
		} else
			throw new AccessNotAllowedException("You can't add data to module");
	}

	public void removeData(ModuleData data) throws AccessNotAllowedException,
			NotEmptyException {

		if (data == null)
			throw new NotEmptyException("data cannot be empty");

		if (Tools.hasRight("REMOVE_DATA_FROM_MODULE")) {
			for (ModuleData gp : moduleDatas)
				if (gp.equals(data)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove data from module");
	}

	public boolean isAffected(ModuleData data) throws NotEmptyException {

		if (data == null)
			throw new NotEmptyException("data cannot be empty");

		for (ModuleData item : moduleDatas)
			if (item.equals(data))
				return true;
		return false;
	}

	public void allowAccess(AppRight right) throws AccessNotAllowedException,
			NotEmptyException {

		if (right == null)
			throw new NotEmptyException("right cannot be empty");

		if (Tools.hasRight("ADD_RIGHT_TO_MODULE")) {
			ModuleRight element = new ModuleRight();
			element.setModule(this);
			element.setRight(right);
			element.persist();
			moduleRights.add(element);
		} else
			throw new AccessNotAllowedException("You can't add right to module");
	}

	public void disallowAccess(AppRight right)
			throws AccessNotAllowedException, NotEmptyException {

		if (right == null)
			throw new NotEmptyException("right cannot be empty");

		if (Tools.hasRight("REMOVE_RIGHT_FROM_MODULE")) {
			for (ModuleRight gp : moduleRights)
				if (gp.getRight().equals(right)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from module");
	}

	public boolean isAccessAllow(AppRight right) throws NotEmptyException {

		if (right == null)
			throw new NotEmptyException("right cannot be empty");

		for (ModuleRight item : moduleRights)
			if (item.getRight().equals(right))
				return true;
		return false;
	}

	public void addAction(ModuleAction action)
			throws AccessNotAllowedException, NotEmptyException {

		if (action == null)
			throw new NotEmptyException("action cannot be empty");

		if (Tools.hasRight("ADD_ACTION_TO_MODULE")) {
			action.setModule(this);
			action.persist();
			moduleActions.add(action);
		} else
			throw new AccessNotAllowedException(
					"You can't add action to module");
	}

	public void removeAction(ModuleAction action)
			throws AccessNotAllowedException, NotEmptyException {

		if (action == null)
			throw new NotEmptyException("action cannot be empty");

		if (Tools.hasRight("REMOVE_ACTION_FROM_MODULE")) {
			for (ModuleAction gp : moduleActions)
				if (gp.equals(action)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove action from module");
	}

	public boolean isAffected(ModuleAction action) throws NotEmptyException {

		if (action == null)
			throw new NotEmptyException("action cannot be empty");

		for (ModuleAction item : moduleActions)
			if (item.equals(action))
				return true;
		return false;
	}

	public void allowAccess(AppGroup group) throws AccessNotAllowedException,
			NotEmptyException {

		if (group == null)
			throw new NotEmptyException("group cannot be empty");

		if (Tools.hasRight("ADD_MODULE_TO_GROUP")) {
			ModuleGroup groupuser = new ModuleGroup();
			groupuser.setGroup(group);
			groupuser.setModule(this);
			groupuser.persist();
			moduleGroups.add(groupuser);
		} else
			throw new AccessNotAllowedException("You can't add group to module");
	}

	public void disallowAccess(AppGroup group)
			throws AccessNotAllowedException, NotEmptyException {

		if (group == null)
			throw new NotEmptyException("group cannot be empty");

		if (Tools.hasRight("REMOVE_MODULE_FROM_GROUP")) {
			for (ModuleGroup gp : moduleGroups)
				if (gp.getGroup().equals(group)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove group from module");
	}

	public boolean isAccessAllow(AppGroup group) throws NotEmptyException {

		if (group == null)
			throw new NotEmptyException("group cannot be empty");

		for (ModuleGroup item : moduleGroups)
			if (item.getGroup().equals(group))
				return true;
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws AccessNotAllowedException,
			NotEmptyException, DataLengthException, DataFormatException, NotUniqueException {

		if (name == null || name.length() == 0)
			throw new NotEmptyException("name cannot be empty");

		if (name.length() > 100)
			throw new DataLengthException(
					"ident parameter is too long (max: 100 carac)");

		if (isNameExist(name))
			throw new NotUniqueException("name has to be unique");
		
		if (Tools.hasRight("SET_MODULE_NAME"))
			this.name = name;
		else
			throw new AccessNotAllowedException(
					"You can't edit a module name as: " + name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description)
			throws AccessNotAllowedException {
		if (Tools.hasRight("SET_MODULE_DESCRIPTION"))
			this.description = description;
		else
			throw new AccessNotAllowedException(
					"You can't edit a module description");
	}

	public String getModuleController() {
		return class1;
	}

	public void setModuleController(String controller)
			throws AccessNotAllowedException, NotEmptyException {

		if (controller == null || controller.length() == 0)
			throw new NotEmptyException("controller cannot be empty");

		if (Tools.hasRight("SET_MODULE_CONTROLLER"))
			this.class1 = controller;
		else
			throw new AccessNotAllowedException(
					"You can't edit a module controller as: " + controller);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) throws AccessNotAllowedException {

		if (Tools.hasRight("SET_MODULE_ENABLED"))
			this.enabled = enabled;
		else
			throw new AccessNotAllowedException(
					"You can't edit a module enabled as: " + enabled);
	}

	public boolean hasAction() {
		return moduleActions.size() > 0;
	}

	public int countAction() {
		return moduleActions.size();
	}

	public boolean hasGroup() {
		return moduleGroups.size() > 0;
	}

	public int countGroup() {
		return moduleGroups.size();
	}

	@PersistenceContext
    transient EntityManager entityManager;

	static final EntityManager entityManager() {
        EntityManager em = new AppModule().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

	static long countAppModules() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AppModule o", Long.class).getSingleResult();
    }

	static List<AppModule> findAllAppModules() {
        return entityManager().createQuery("SELECT o FROM AppModule o", AppModule.class).getResultList();
    }

	static AppModule findAppModule(Integer module) {
        if (module == null) return null;
        return entityManager().find(AppModule.class, module);
    }

	static List<AppModule> findAppModuleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AppModule o", AppModule.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            AppModule attached = this;
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
	AppModule merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AppModule merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
