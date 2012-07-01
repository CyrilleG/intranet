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

	public static AppGroup findGroupByIdent(String ident)
			throws ElementNotFoundException {
		List<AppGroup> elements = AppGroup.findAllAppGroups();
		for (AppGroup element : elements)
			if (element.getIdent().compareToIgnoreCase(ident) == 0)
				return element;
		throw new ElementNotFoundException("No Group Object with ident: "
				+ ident);
	}

	public static AppGroup createGroup(String ident, String label,
			String description) throws AccessNotAllowedException {
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

	public void addRightToGroup(AppRight right)
			throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_RIGHT_TO_GROUP")) {
			GroupRight groupright = new GroupRight();
			groupright.setGroup(this);
			groupright.setRight(right);
			groupright.persist();
			groupRights.add(groupright);
		} else
			throw new AccessNotAllowedException("You can't add right to group");
	}

	public void removeRightFromGroup(AppRight right)
			throws AccessNotAllowedException {
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

	public boolean groupHasRight(String ident) {
		for (GroupRight gp : groupRights)
			if (gp.getRight().getIdent().compareTo(ident) == 0)
				return true;
		return false;
	}

	public boolean groupHasRight(AppRight right) {
		return groupHasRight(right.getIdent());
	}

	public void addGroupToUser(AppUser ident) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_USER_TO_GROUP")
				|| Tools.hasRight("ADD_HIMSELF_TO_GROUP_")) {
			UserGroup groupuser = new UserGroup();
			groupuser.setGroup(this);
			groupuser.setUser(ident);
			groupuser.persist();
			userGroups.add(groupuser);
		} else
			throw new AccessNotAllowedException("You can't add user to group");
	}

	public void removeGroupFromUser(AppUser ident)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_USER_FROM_GROUP")) {
			for (UserGroup gp : userGroups)
				if (gp.getUser().equals(ident)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from group");
	}

	public boolean userHasGroup(AppUser ident) {
		for (UserGroup elem : userGroups)
			if (elem.getUser().equals(ident))
				return true;
		return false;
	}

	public void addGroupToData(ModuleData ident)
			throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_DATA_TO_GROUP")) {
			DataGroup element = new DataGroup();
			element.setGroup(this);
			element.setData(ident);
			element.persist();
			dataGroups.add(element);
		} else
			throw new AccessNotAllowedException("You can't data right to group");
	}

	public void removeGroupFromData(ModuleData ident)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_DATA_FROM_GROUP")) {
			for (DataGroup gp : dataGroups)
				if (gp.getData().equals(ident)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove data from group");
	}

	public boolean dataHasGroup(ModuleData ident) {
		for (DataGroup g : dataGroups)
			if (g.getData().equals(ident))
				return true;
		return false;
	}

	public void addGroupToModule(AppModule ident)
			throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_MODULE_TO_GROUP")) {
			ModuleGroup groupuser = new ModuleGroup();
			groupuser.setGroup(this);
			groupuser.setModule(ident);
			groupuser.persist();
			moduleGroups.add(groupuser);
		} else
			throw new AccessNotAllowedException("You can't add module to group");
	}

	public void removeGroupFromModule(AppModule ident)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_MODULE_FROM_GROUP")) {
			for (ModuleGroup gp : moduleGroups)
				if (gp.getModule().equals(ident)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove module from group");
	}

	public boolean moduleHasGroup(AppModule ident) {
		return moduleGroups.contains(ident);
	}

	public void addGroupToAction(ModuleAction ident)
			throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_ACTION_TO_GROUP")) {
			ActionGroup groupaction = new ActionGroup();
			groupaction.setGroup(this);
			groupaction.setAction(ident);
			groupaction.persist();
			actionGroups.add(groupaction);
		} else
			throw new AccessNotAllowedException("You can't add action to group");
	}

	public void removeGroupFromAction(ModuleAction ident)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_ACTION_FROM_GROUP")) {
			for (ActionGroup gp : actionGroups)
				if (gp.getAction().equals(ident)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove action from group");
	}

	public boolean actionHasGroup(ModuleAction ident) {
		for (ActionGroup item : actionGroups)
			if (item.getAction().equals(ident))
				return true;
		return false;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_GROUP_LABEL"))
			this.label = label;
		else
			throw new AccessNotAllowedException(
					"You can't edit a group entry label as: " + label);
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) throws AccessNotAllowedException {
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

}
