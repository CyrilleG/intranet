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

import utils.Tools;

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
    
    @Column(name = "name", columnDefinition = "VARCHAR", length = 100, unique = true)
    @NotNull
    private String name;
    
    @Column(name = "description", columnDefinition = "VARCHAR", length = 255)
    private String description;

	public Set<AppRight> getRights() {
		Set<AppRight> rights = new HashSet<AppRight>();
		for (GroupRight g : groupRights)
			rights.add(g.getRight());
		return rights;
	}
	
	public static AppGroup findGroupByName(String name) {
		List<AppGroup> elements = AppGroup.findAllAppGroups();
		for (AppGroup element : elements)
			if (element.getName().compareToIgnoreCase(name) == 0)
				return element;
		return null;
	}
	
	public static AppGroup createGroup(String name, String description) {
		if (Tools.hasRight("ADD_GROUP")) {
			AppGroup group = new AppGroup();
			group.setName(name);
			group.setDescription(description);
			group.persist();
			return group;
		}
		return null;
	}

	public void addRightToGroup(AppRight right) {
		if (Tools.hasRight("ADD_RIGHT_TO_GROUP")) {
			GroupRight groupright = new GroupRight();
			groupright.setGroup(this);
			groupright.setRight(right);
			groupright.persist();
			groupRights.add(groupright);
		}
	}

	public void removeRightFromGroup(AppRight right) {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_GROUP")) {
			for (GroupRight gp : groupRights)
				if (gp.getRight().equals(right)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean groupHasRight(String ident) {
		for (GroupRight gp : groupRights)
    		if(gp.getRight().getIdent().compareTo(ident) == 0)
    			return true;
		return false;
	}

	public boolean groupHasRight(AppRight right) {
		return groupHasRight(right.getIdent());
	}

	public void addGroupToUser(AppUser ident) {
		if (Tools.hasRight("ADD_USER_TO_GROUP")) {
			UserGroup groupuser = new UserGroup();
			groupuser.setGroup(this);
			groupuser.setUser(ident);
			groupuser.persist();
			userGroups.add(groupuser);
		}
	}

	public void removeGroupFromUser(AppUser ident) {
		if (Tools.hasRight("REMOVE_USER_FROM_GROUP")) {
			for (UserGroup gp : userGroups)
				if (gp.getUser().equals(ident)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean userHasGroup(AppUser ident) {
		for (UserGroup elem : userGroups)
			if (elem.getUser().equals(ident))
				return true;
		return false;
	}

	
	public void addGroupToData(ModuleData ident) {
		if (Tools.hasRight("ADD_DATA_TO_GROUP")) {
			DataGroup element = new DataGroup();
			element.setGroup(this);
			element.setData(ident);
			element.persist();
			dataGroups.add(element);
		}
	}

	public void removeGroupFromData(ModuleData ident) {
		if (Tools.hasRight("REMOVE_DATA_FROM_GROUP")) {
			for (DataGroup gp : dataGroups)
				if (gp.getData().equals(ident)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean dataHasGroup(ModuleData ident) {
		for (DataGroup g : dataGroups)
			if (g.getData().equals(ident))
				return true;
		return false;
	}
	
	
	public void addGroupToModule(AppModule ident) {
		if (Tools.hasRight("ADD_MODULE_TO_GROUP")) {
			ModuleGroup groupuser = new ModuleGroup();
			groupuser.setGroup(this);
			groupuser.setModule(ident);
			groupuser.persist();
			moduleGroups.add(groupuser);
		}
	}

	public void removeGroupFromModule(AppModule ident) {
		if (Tools.hasRight("REMOVE_MODULE_FROM_GROUP")) {
			for (ModuleGroup gp : moduleGroups)
				if (gp.getModule().equals(ident)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean moduleHasGroup(AppModule ident) {
		return moduleGroups.contains(ident);
	}

	public void addGroupToAction(ModuleAction ident) {
		if (Tools.hasRight("ADD_ACTION_TO_GROUP")) {
			ActionGroup groupaction = new ActionGroup();
			groupaction.setGroup(this);
			groupaction.setAction(ident);
			groupaction.persist();
			actionGroups.add(groupaction);
		}
	}

	public void removeGroupFromAction(ModuleAction ident) {
		if (Tools.hasRight("REMOVE_ACTION_FROM_GROUP")) {
			for (ActionGroup gp : actionGroups)
				if (gp.getAction().equals(ident)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean actionHasGroup(ModuleAction ident) {
		for (ActionGroup item : actionGroups)
			if (item.getAction().equals(ident))
				return true;
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (Tools.hasRight("SET_GROUP_NAME"))
			this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (Tools.hasRight("SET_GROUP_DESCRIPTION"))
			this.description = description;
	}

	public boolean isUse() {
		return actionGroups.size() > 0 || groupFilters.size() > 0
				|| groupRights.size() > 0 || infoPrivacities.size() > 0
				|| moduleGroups.size() > 0 || userGroups.size() > 0;
	}
}
