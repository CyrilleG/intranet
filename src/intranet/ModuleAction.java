package intranet;

import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import utils.Tools;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "module_action")
@RooDbManaged(automaticallyDelete = true)
public class ModuleAction {

	@OneToMany(mappedBy = "action", cascade = CascadeType.ALL)
	private Set<ActionGroup> actionGroups;

	@OneToMany(mappedBy = "action", cascade = CascadeType.ALL)
	private Set<ActionRight> actionRights;

	@ManyToOne
	@JoinColumn(name = "module", referencedColumnName = "module", nullable = false)
	private AppModule module;

	@Column(name = "method", columnDefinition = "VARCHAR", length = 100, unique = true)
	@NotNull
	private String method;

	@Column(name = "enabled", columnDefinition = "BIT")
	@NotNull
	private boolean enabled;

	public static ModuleAction findActionByNameAndModule(AppModule module,
			String name) {
		List<ModuleAction> elements = ModuleAction.findAllModuleActions();
		for (ModuleAction element : elements)
			if (element.getMethod().compareToIgnoreCase(name) == 0
					&& element.getModule().equals(module))
				return element;
		return null;
	}

	public boolean canBeUse() {
		return actionGroups.size() > 0 || actionRights.size() > 0;
	}

	public AppModule getModule() {
		return module;
	}

	public void setModule(AppModule idmodule) {
		if (Tools.hasRight("SET_ACTION_MODULE"))
			this.module = idmodule;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		if (Tools.hasRight("SET_ACTION_METHOD"))
			this.method = method;
	}

	public boolean isActionEnabled() {
		return enabled;
	}

	public void setActionEnabled(boolean enabled) {
		if (Tools.hasRight("SET_ACTION_ENABLED"))
			this.enabled = enabled;
	}

	public void addGroupToAction(AppGroup g) {
		if (Tools.hasRight("ADD_ACTION_TO_GROUP")) {
			ActionGroup groupaction = new ActionGroup();
			groupaction.setGroup(g);
			groupaction.setAction(this);
			groupaction.persist();
			actionGroups.add(groupaction);
		}
	}

	public void removeGroupFromAction(AppGroup g) {
		if (Tools.hasRight("REMOVE_ACTION_FROM_GROUP")) {
			for (ActionGroup gp : actionGroups)
				if (gp.getGroup().equals(g)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean actionHasGroup(AppGroup g) {
		for (ActionGroup item : actionGroups)
			if (item.getGroup().equals(g))
				return true;
		return false;
	}

	public void addRightToAction(AppRight right) {
		if (Tools.hasRight("ADD_RIGHT_TO_ACTION")) {
			ActionRight element = new ActionRight();
			element.setRight(right);
			element.setAction(this);
			element.persist();
			actionRights.add(element);
		}
	}

	public void removeRightFromAction(AppRight right) {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_ACTION")) {
			for (ActionRight gp : actionRights)
				if (gp.getRight().equals(right)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean actionHasRight(AppRight right) {
		for (ActionRight item : actionRights)
			if (item.getRight().equals(right))
				return true;
		return false;
	}

	public boolean canAccess() {
		return canAccess(Tools.getUser());
	}

	public boolean canAccess(AppUser e) {
		for (ActionGroup g : actionGroups)
			if (e.userHasGroup(g.getGroup()))
				return true;
		for (ActionRight g : actionRights)
			if (e.userHasRight(g.getRight()))
				return true;
		return false;
	}

}
