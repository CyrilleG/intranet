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

import exceptions.AccessNotAllowedException;
import exceptions.DataLengthException;
import exceptions.ElementNotFoundException;
import exceptions.NotEmptyException;

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

	public static ModuleAction findByMethodAndModule(AppModule module,
			String method) throws ElementNotFoundException, NotEmptyException, DataLengthException {
		
		if (module == null)
			throw new NotEmptyException("module cannot be empty");
		
		if (method == null || method.length() == 0)
			throw new NotEmptyException("name cannot be empty");
		
		
		if (method.length() > 100)
			throw new DataLengthException("method parameter is too long (max: 100 carac)");
		
		List<ModuleAction> elements = ModuleAction.findAllModuleActions();
		for (ModuleAction element : elements)
			if (element.getMethod().compareToIgnoreCase(method) == 0
					&& element.getModule().equals(module))
				return element;
		throw new ElementNotFoundException("No Action Object found with name: "
				+ method + " and module: " + module.getName());
	}

	public static ModuleAction create(AppModule module, String method, boolean enabled) throws AccessNotAllowedException, NotEmptyException, DataLengthException {
		
		if (module == null)
			throw new NotEmptyException("module cannot be empty");
		
		if (method == null || method.length() == 0)
			throw new NotEmptyException("name cannot be empty");
		
		if (method.length() > 100)
			throw new DataLengthException("method parameter is too long (max: 100 carac)");
		
		if (Tools.hasRight("ADD_USER")) {
			ModuleAction user = new ModuleAction();
			user.setActionEnabled(enabled);
			user.setMethod(method);
			user.setModule(module);
			user.persist();
			return user;
		} else
			throw new AccessNotAllowedException("You can't add a action entry");
	}
	
	public boolean isUse() {
		return actionGroups.size() > 0 || actionRights.size() > 0;
	}

	public AppModule getModule() {
		return module;
	}

	public void setModule(AppModule module) throws AccessNotAllowedException, NotEmptyException {
		
		if (module == null)
			throw new NotEmptyException("module cannot be empty");
		
		if (Tools.hasRight("SET_ACTION_MODULE"))
			this.module = module;
		else
			throw new AccessNotAllowedException(
					"You can't edit a action module as: " + module.getName());
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) throws AccessNotAllowedException, NotEmptyException, DataLengthException {
		
		if (method == null || method.length() == 0)
			throw new NotEmptyException("name cannot be empty");
		
		if (method.length() > 100)
			throw new DataLengthException("method parameter is too long (max: 100 carac)");
		
		if (Tools.hasRight("SET_ACTION_METHOD"))
			this.method = method;
		else
			throw new AccessNotAllowedException(
					"You can't edit a action method as: " + method);
	}

	public boolean isActionEnabled() {
		return enabled;
	}

	public void setActionEnabled(boolean enabled) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_ACTION_ENABLED"))
			this.enabled = enabled;
		else
			throw new AccessNotAllowedException(
					"You can't edit a action enabled as: " + enabled);
	}

	public void allow(AppGroup group) throws AccessNotAllowedException, NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		if (Tools.hasRight("ADD_ACTION_TO_GROUP")) {
			ActionGroup groupaction = new ActionGroup();
			groupaction.setGroup(group);
			groupaction.setAction(this);
			groupaction.persist();
			actionGroups.add(groupaction);
		}
		else
			throw new AccessNotAllowedException("You can't add action to group");
	}

	public void disallowAccess(AppGroup group) throws AccessNotAllowedException, NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		if (Tools.hasRight("REMOVE_ACTION_FROM_GROUP")) {
			for (ActionGroup gp : actionGroups)
				if (gp.getGroup().equals(group)) {
					gp.remove();
					break;
				}
		}
		else
			throw new AccessNotAllowedException("You can't remove action from group");
	}

	public boolean isAccessAllow(AppGroup group) throws NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		for (ActionGroup item : actionGroups)
			if (item.getGroup().equals(group))
				return true;
		return false;
	}

	public void allow(AppRight right) throws AccessNotAllowedException, NotEmptyException {
		
		if (right == null)
			throw new NotEmptyException("right cannot be empty");
		
		if (Tools.hasRight("ADD_RIGHT_TO_ACTION")) {
			ActionRight element = new ActionRight();
			element.setRight(right);
			element.setAction(this);
			element.persist();
			actionRights.add(element);
		}
		else
			throw new AccessNotAllowedException("You can't add right to action");
	}

	public void disallowAccess(AppRight right) throws AccessNotAllowedException, NotEmptyException {
		
		if (right == null)
			throw new NotEmptyException("right cannot be empty");
		
		if (Tools.hasRight("REMOVE_RIGHT_FROM_ACTION")) {
			for (ActionRight gp : actionRights)
				if (gp.getRight().equals(right)) {
					gp.remove();
					break;
				}
		}
		else
			throw new AccessNotAllowedException("You can't remove right from action");
	}

	public boolean isAccessAllow(AppRight right) throws NotEmptyException {
		
		if (right == null)
			throw new NotEmptyException("right cannot be empty");
		
		for (ActionRight item : actionRights)
			if (item.getRight().equals(right))
				return true;
		return false;
	}

	public boolean isAccessAllow() throws NotEmptyException {
		return isAccessAllow(Tools.getUser());
	}

	public boolean isAccessAllow(AppUser user) throws NotEmptyException {
		
		if (user == null)
			throw new NotEmptyException("user cannot be empty");
		
		for (ActionGroup g : actionGroups)
			if (user.hasGroup(g.getGroup()))
				return true;
		for (ActionRight g : actionRights)
			if (user.hasRight(g.getRight()))
				return true;
		return false;
	}

}
