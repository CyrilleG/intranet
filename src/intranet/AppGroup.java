package intranet;

import java.util.HashSet;
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

	@OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
	private Set<ActionGroups> actionGroupss;//

	@OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
	private Set<GroupFilters> groupFilterss;

	@OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
	private Set<GroupRights> groupRightss;//

	@OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
	private Set<InfoPrivacities> infoPrivacitieses;

	@OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
	private Set<ModuleGroups> moduleGroupss;

	@OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
	private Set<UserGroups> userGroupss;//

	@Column(name = "name", length = 100, unique = true)
	@NotNull
	private String name;

	@Column(name = "description", length = 255)
	private String description;

	public Set<AppRight> getRights() {
		Set<AppRight> rights = new HashSet<AppRight>();
		for (GroupRights g : groupRightss)
			rights.add(g.getIdright());
		return rights;
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
			GroupRights groupright = new GroupRights();
			groupright.setIdgroup(this);
			groupright.setIdright(right);
			groupright.persist();
			groupRightss.add(groupright);
		}
	}

	public void removeRightFromGroup(AppRight right) {
		if (Tools.hasRight("REMOVE_FILTER_FROM_GROUP")) {
			for (GroupRights gp : groupRightss)
				if (gp.getIdgroup().equals(right)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean groupHasRight(String ident) {
		for (GroupRights gp : groupRightss)
    		if(gp.getIdright().getIdent().compareTo(ident) == 0)
    			return true;
		return false;
	}

	public boolean groupHasRight(AppRight right) {
		return groupHasRight(right.getIdent());
	}

	public void addGroupToUser(AppUser ident) {
		if (Tools.hasRight("ADD_USER_TO_GROUP")) {
			UserGroups groupuser = new UserGroups();
			groupuser.setIdgroup(this);
			groupuser.setIduser(ident);
			groupuser.persist();
			userGroupss.add(groupuser);
		}
	}

	public void removeGroupFromUser(AppUser ident) {
		if (Tools.hasRight("REMOVE_USER_FROM_GROUP")) {
			for (UserGroups gp : userGroupss)
				if (gp.getIduser().equals(ident)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean userHasGroup(AppUser ident) {
		for (UserGroups gp : userGroupss)
    		if(gp.getIduser().equals(ident))
    			return true;
		return false;
	}

	public void addGroupToModule(AppModule ident) {
		if (Tools.hasRight("ADD_MODULE_TO_GROUP")) {
			ModuleGroups groupuser = new ModuleGroups();
			groupuser.setIdgroup(this);
			groupuser.setIdmodule(ident);
			groupuser.persist();
			moduleGroupss.add(groupuser);
		}
	}

	public void removeGroupFromModule(AppModule ident) {
		if (Tools.hasRight("REMOVE_MODULE_FROM_GROUP")) {
			for (ModuleGroups gp : moduleGroupss)
				if (gp.getIdmodule().equals(ident)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean moduleHasGroup(AppModule ident) {
		for (ModuleGroups gp : moduleGroupss)
    		if(gp.getIdmodule().equals(ident))
    			return true;
		return false;
	}

	public void addGroupToAction(ModuleAction ident) {
		if (Tools.hasRight("ADD_ACTION_TO_GROUP")) {
			ActionGroups groupaction = new ActionGroups();
			groupaction.setIdgroup(this);
			groupaction.setIdaction(ident);
			groupaction.persist();
			actionGroupss.add(groupaction);
		}
	}

	public void removeGroupFromAction(ModuleAction ident) {
		if (Tools.hasRight("REMOVE_ACTION_FROM_GROUP")) {
			for (ActionGroups gp : actionGroupss)
				if (gp.getIdaction().equals(ident)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean actionHasGroup(ModuleAction ident) {
		for (ActionGroups gp : actionGroupss)
    		if(gp.getIdaction().equals(ident))
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
		return actionGroupss.size() > 0 || groupFilterss.size() > 0
				|| groupRightss.size() > 0 || infoPrivacitieses.size() > 0
				|| moduleGroupss.size() > 0 || userGroupss.size() > 0;
	}
}
