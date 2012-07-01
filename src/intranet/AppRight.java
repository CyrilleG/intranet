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

import exceptions.AccessNotAllowedException;
import exceptions.ElementNotFoundException;

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
		return ident;
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

	public static AppRight findByName(String name)
			throws ElementNotFoundException {
		List<AppRight> elements = AppRight.findAllAppRights();
		for (AppRight element : elements)
			if (element.getName().compareToIgnoreCase(name) == 0)
				return element;
		throw new ElementNotFoundException("No Right Object found with name: "
				+ name);
	}

	public static AppRight findByIdent(String ident)
			throws ElementNotFoundException {
		List<AppRight> elements = AppRight.findAllAppRights();
		for (AppRight element : elements)
			if (element.getIdent().compareToIgnoreCase(ident) == 0)
				return element;
		throw new ElementNotFoundException("No Right Object found with ident: "
				+ ident);
	}

	public static AppRight create(String name, String ident,
			String description) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_RIGHT")) {
			AppRight right = new AppRight();
			right.setName(name);
			right.setDescription(description);
			right.setIdent(ident);
			right.persist();
			return right;
		} else
			throw new AccessNotAllowedException("You can't add a Right entry");
	}

	public void allowAccess(ModuleAction action)
			throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_RIGHT_TO_ACTION")) {
			ActionRight element = new ActionRight();
			element.setRight(this);
			element.setAction(action);
			element.persist();
			actionRights.add(element);
		} else
			throw new AccessNotAllowedException("You can't add right to action");
	}

	public void disallowAccess(ModuleAction action)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_ACTION")) {
			for (ActionRight gp : actionRights)
				if (gp.getAction().equals(ident)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from action");
	}

	public boolean isAccessAllow(ModuleAction element) {
		for (ActionRight item : actionRights)
			if (item.getAction().equals(element))
				return true;
		return false;
	}

	public void allowAccess(AppGroup group)
			throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_RIGHT_TO_GROUP")) {
			GroupRight groupright = new GroupRight();
			groupright.setGroup(group);
			groupright.setRight(this);
			groupright.persist();
			groupRights.add(groupright);
		} else
			throw new AccessNotAllowedException("You can't add right to group");
	}

	public void disallowAccess(AppGroup group)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_GROUP")) {
			for (GroupRight gp : groupRights)
				if (gp.getGroup().equals(group)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from group");

	}

	public boolean isAccessAllow(AppGroup ident) {
		for (GroupRight item : groupRights)
			if (item.getGroup().equals(ident))
				return true;
		return false;
	}

	public void allowAccess(AppModule module)
			throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_RIGHT_TO_MODULE")) {
			ModuleRight element = new ModuleRight();
			element.setRight(this);
			element.setModule(module);
			element.persist();
			moduleRights.add(element);
		} else
			throw new AccessNotAllowedException("You can't add right to module");
	}

	public void disallowAccess(AppModule module)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_MODULE")) {
			for (ModuleRight gp : moduleRights)
				if (gp.getModule().equals(module)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from module");
	}

	public boolean isAccessAllow(AppModule element) {
		for (ModuleRight item : moduleRights)
			if (item.getModule().equals(element))
				return true;
		return false;
	}

	public void addToUser(AppUser user) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_RIGHT_TO_USER")) {
			UserRight element = new UserRight();
			element.setRight(this);
			element.setUser(user);
			element.persist();
			userRights.add(element);
		} else
			throw new AccessNotAllowedException("You can't add right to user");
	}

	public void disallowAccess(AppUser user)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_USER")) {
			for (UserRight gp : userRights)
				if (gp.getUser().equals(user)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from user");
	}

	public boolean userHasRight(AppUser element) {
		for (UserRight item : userRights)
			if (item.getUser().equals(element))
				return true;
		return false;
	}

	public void allowAccess(ModuleData data)
			throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_RIGHT_TO_DATA")) {
			DataRight element = new DataRight();
			element.setRight(this);
			element.setData(data);
			element.persist();
			dataRights.add(element);
		} else
			throw new AccessNotAllowedException("You can't add right to data");
	}

	public void disallowAccess(ModuleData data)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_DATA")) {
			for (DataRight gp : dataRights)
				if (gp.getData().equals(data)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from data");
	}

	public boolean isAccessAllow(ModuleData element) {
		for (DataRight item : dataRights)
			if (item.getData().equals(element))
				return true;
		return false;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_RIGHT_IDENT"))
			this.ident = ident;
		else
			throw new AccessNotAllowedException(
					"You can't edit a right ident as: " + ident);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_RIGHT_NAME"))
			this.name = name;
		else
			throw new AccessNotAllowedException(
					"You can't edit a right name as: " + name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description)
			throws AccessNotAllowedException {
		if (Tools.hasRight("SET_RIGHT_DESCRIP"))
			this.description = description;
		else
			throw new AccessNotAllowedException(
					"You can't edit a right description");
	}

	public boolean isUse() {
		return actionRights.size() > 0 || groupRights.size() > 0
				|| moduleRights.size() > 0 || userRights.size() > 0;
	}

}
