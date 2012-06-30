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
import org.springframework.security.core.GrantedAuthority;

import utils.Tools;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_right")
@RooDbManaged(automaticallyDelete = true)
public class AppRight implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return null;
	}

	@OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
    private Set<ActionRight> actionRights;
    
    @OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
    private Set<DataRight> dataRights;
    
    @OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
    private Set<GroupRight> groupRights;
    
    @OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
    private Set<ModuleRight> moduleRights;
    
    @OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
    private Set<UserRight> userRights;
    
    @Column(name = "ident", columnDefinition = "VARCHAR", length = 70, unique = true)
    @NotNull
    private String ident;
    
    @Column(name = "name", columnDefinition = "VARCHAR", length = 100)
    @NotNull
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

	public static AppRight findRightByName(String name) {
		List<AppRight> elements = AppRight.findAllAppRights();
		for (AppRight element : elements)
			if (element.getName().compareToIgnoreCase(name) == 0)
				return element;
		return null;
	}

	public static AppRight findRightByIdent(String ident) {
		List<AppRight> elements = AppRight.findAllAppRights();
		for (AppRight element : elements)
			if (element.getIdent().compareToIgnoreCase(ident) == 0)
				return element;
		return null;
	}

	public static AppRight createRight(String name, String ident,
			String description) {
		if (Tools.hasRight("ADD_RIGHT")) {
			AppRight right = new AppRight();
			right.setName(name);
			right.setDescription(description);
			right.setIdent(ident);
			right.persist();
			return right;
		}
		return null;
	}

	public void addRightToAction(ModuleAction action) {
		if (Tools.hasRight("ADD_RIGHT_TO_ACTION")) {
			ActionRight element = new ActionRight();
			element.setRight(this);
			element.setAction(action);
			element.persist();
			actionRights.add(element);
		}
	}

	public void removeRightFromAction(ModuleAction action) {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_ACTION")) {
			for (ActionRight gp : actionRights)
				if (gp.getAction().equals(ident)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean actionHasRight(ModuleAction element) {
		for (ActionRight item : actionRights)
			if (item.getAction().equals(element))
				return true;
		return false;
	}

	public void addRightToGroup(AppGroup group) {
		if (Tools.hasRight("ADD_RIGHT_TO_GROUP")) {
			GroupRight groupright = new GroupRight();
			groupright.setGroup(group);
			groupright.setRight(this);
			groupright.persist();
			groupRights.add(groupright);
		}
	}

	public void removeRightFromGroup(AppGroup group) {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_GROUP")) {
			for (GroupRight gp : groupRights)
				if (gp.getGroup().equals(group)) {
					gp.remove();
					break;
				}
		}

	}

	public boolean groupHasRight(AppGroup ident) {
		for (GroupRight item : groupRights)
			if (item.getGroup().equals(ident))
				return true;
		return false;
	}

	public void addRightToModule(AppModule module) {
		if (Tools.hasRight("ADD_RIGHT_TO_MODULE")) {
			ModuleRight element = new ModuleRight();
			element.setRight(this);
			element.setModule(module);
			element.persist();
			moduleRights.add(element);
		}
	}

	public void removeRightFromModule(AppModule module) {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_MODULE")) {
			for (ModuleRight gp : moduleRights)
				if (gp.getModule().equals(module)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean moduleHasRight(AppModule element) {
		for (ModuleRight item : moduleRights)
			if (item.getModule().equals(element))
				return true;
		return false;
	}

	public void addRightToUser(AppUser user) {
		if (Tools.hasRight("ADD_RIGHT_TO_USER")) {
			UserRight element = new UserRight();
			element.setRight(this);
			element.setUser(user);
			element.persist();
			userRights.add(element);
		}
	}

	public void removeRightFromUser(AppUser user) {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_USER")) {
			for (UserRight gp : userRights)
				if (gp.getUser().equals(user)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean userHasRight(AppUser element) {
		for (UserRight item : userRights)
			if (item.getUser().equals(element))
				return true;
		return false;
	}

	public void addRightToData(ModuleData data) {
		if (Tools.hasRight("ADD_RIGHT_TO_DATA")) {
			DataRight element = new DataRight();
			element.setRight(this);
			element.setData(data);
			element.persist();
			dataRights.add(element);
		}
	}

	public void removeRightFromData(ModuleData data) {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_DATA")) {
			for (DataRight gp : dataRights)
				if (gp.getData().equals(data)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean dataHasRight(ModuleData element) {
		for (DataRight item : dataRights)
			if (item.getData().equals(element))
				return true;
		return false;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		if (Tools.hasRight("SET_RIGHT_IDENT"))
			this.ident = ident;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (Tools.hasRight("SET_RIGHT_NAME"))
			this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (Tools.hasRight("SET_RIGHT_DESCRIP"))
			this.description = description;
	}

	public boolean isRightUsed() {
		return actionRights.size() > 0 || groupRights.size() > 0
				|| moduleRights.size() > 0 || userRights.size() > 0;
	}

}
