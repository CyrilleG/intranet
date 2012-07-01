package intranet;

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

	public static AppModule findModuleByName(String name)
			throws ElementNotFoundException {
		List<AppModule> elements = AppModule.findAllAppModules();
		for (AppModule element : elements)
			if (element.getName().compareToIgnoreCase(name) == 0)
				return element;
		throw new ElementNotFoundException("No Module Object found with name: "
				+ name);
	}

	public static AppModule createModule(String name, String description,
			String controller) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_MODULE")) {
			AppModule module = new AppModule();
			module.setName(name);
			module.setDescription(description);
			module.setModuleController(controller);
			module.persist();
			return module;
		}
		else
			throw new AccessNotAllowedException("You can't add a module entry");
	}

	public boolean install() {
		return false;
	}

	public void uninstall() {

	}

	public void addDataToModule(ModuleData ident) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_DATA_TO_MODULE")) {
			ident.setModule(this);
			moduleDatas.add(ident);
		} else
			throw new AccessNotAllowedException("You can't add data to module");
	}

	public void removeDataFromModule(ModuleData ident) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_DATA_FROM_MODULE")) {
			for (ModuleData gp : moduleDatas)
				if (gp.equals(ident)) {
					gp.remove();
					break;
				}
		}else
			throw new AccessNotAllowedException("You can't remove data from module");
	}

	public boolean moduleHasData(ModuleData ident) {
		return moduleActions.contains(ident);
	}

	public void addRightToModule(AppRight ident) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_RIGHT_TO_MODULE")) {
			ModuleRight element = new ModuleRight();
			element.setModule(this);
			element.setRight(ident);
			element.persist();
			moduleRights.add(element);
		}else
			throw new AccessNotAllowedException("You can't add right to module");
	}

	public void removeRightFromModule(ModuleAction ident) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_MODULE")) {
			for (ModuleAction gp : moduleActions)
				if (gp.equals(ident)) {
					gp.remove();
					break;
				}
		}
		else
			throw new AccessNotAllowedException("You can't remove right from module");
	}

	public boolean moduleHasRight(ModuleRight ident) {
		return moduleRights.contains(ident);
	}

	public void addActionToModule(ModuleAction action) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_ACTION_TO_MODULE")) {
			ModuleAction module = new ModuleAction();
			module.setModule(this);
			module.persist();
			moduleActions.add(module);
		}
		else
			throw new AccessNotAllowedException("You can't add action to module");
	}

	public void removeActionFromModule(ModuleAction ident) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_ACTION_FROM_MODULE")) {
			for (ModuleAction gp : moduleActions)
				if (gp.equals(ident)) {
					gp.remove();
					break;
				}
		}
		else
			throw new AccessNotAllowedException("You can't remove action from module");
	}

	public boolean moduleHasAction(ModuleAction ident) {
		return moduleActions.contains(ident);
	}

	public void addModuleToGroup(AppGroup p) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_MODULE_TO_GROUP")) {
			ModuleGroup groupuser = new ModuleGroup();
			groupuser.setGroup(p);
			groupuser.setModule(this);
			groupuser.persist();
			moduleGroups.add(groupuser);
		}else
			throw new AccessNotAllowedException("You can't add group to module");
	}

	public void removeModuleFromGroup(AppGroup ident) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_MODULE_FROM_GROUP")) {
			for (ModuleGroup gp : moduleGroups)
				if (gp.getGroup().equals(ident)) {
					gp.remove();
					break;
				}
		}
		else
			throw new AccessNotAllowedException("You can't remove group from module");
	}

	public boolean groupCanAccessToModule(AppGroup ident) {
		return moduleGroups.contains(ident);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_MODULE_NAME"))
			this.name = name;
		else
			throw new AccessNotAllowedException(
					"You can't edit a module name as: " + name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_MODULE_DESCRIPTION"))
			this.description = description;
		else
			throw new AccessNotAllowedException(
					"You can't edit a module description");
	}

	public String getModuleController() {
		return class1;
	}

	public void setModuleController(String controller) throws AccessNotAllowedException {
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
}
