package intranet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import exceptions.AccessNotAllowedException;
import exceptions.ElementNotFoundException;
import exceptions.FatalErrorException;
import filters.*; // This import is useful to access to filter types. DO NOT DELETE
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import utils.Tools;
import intranet.AppFilter;
import intranet.GroupFilter;
import intranet.UserFilter;

@SuppressWarnings("unused")
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_filter")
@RooDbManaged(automaticallyDelete = true)
public class AppFilter {

	@OneToMany(mappedBy = "filter", cascade = CascadeType.ALL)
	private Set<GroupFilter> groupFilters;

	@OneToMany(mappedBy = "filter", cascade = CascadeType.ALL)
	private Set<UserFilter> userFilters;

	@Column(name = "name", columnDefinition = "VARCHAR", length = 100)
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "class", columnDefinition = "VARCHAR", length = 75)
	private String class1;

	public AppFilter createFilter(String name, String description,
			String classname) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_FILTER")) {
			AppFilter f = new AppFilter();
			f.setDescription(description);
			f.setFilterClass(classname);
			f.setName(name);
			f.persist();
			return f;
		} else
			throw new AccessNotAllowedException("You can't add a filter entry");
	}

	public static AppFilter findFilterByName(String name)
			throws ElementNotFoundException {
		List<AppFilter> elements = AppFilter.findAllAppFilters();
		for (AppFilter element : elements)
			if (element.getName().compareToIgnoreCase(name) == 0)
				return element;
		throw new ElementNotFoundException("No Filter Object with ident "
				+ name);
	}

	public void addFilterToGroup(AppGroup g) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_FILTER_TO_GROUP")) {
			GroupFilter group = new GroupFilter();
			group.setFilter(this);
			group.setGroup(g);
			groupFilters.add(group);
			group.persist();
		} else
			throw new AccessNotAllowedException(
					"You can't add a filter to group: " + g.getIdent());
	}

	public void removeFilterFromGroup(AppGroup group)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_FILTER_FROM_GROUP")) {
			for (GroupFilter gp : groupFilters)
				if (gp.getGroup().equals(group)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove a filter from group: " + group.getIdent());
	}

	public boolean filterHasGroup(AppGroup group) {
		return groupFilters.contains(group);
	}

	public void addFilterToUser(AppUser user) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_FILTER_TO_USER")) {
			UserFilter group = new UserFilter();
			group.setFilter(this);
			group.setUser(user);
			userFilters.add(group);
			group.persist();
		} else
			throw new AccessNotAllowedException(
					"You can't add a filter to user: " + user.getLogin());
	}

	public void removeFilterFromUser(AppUser user)
			throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_FILTER_FROM_USER")) {
			for (UserFilter gp : userFilters)
				if (gp.getUser().equals(user)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove a filter from user: " + user.getLogin());
	}

	public boolean filterHasUser(AppUser user) {
		for (UserFilter elem : userFilters)
			if (elem.getUser().equals(user))
				return true;
		return false;
	}

	public boolean hasUser() {
		return userFilters.size() > 0;
	}

	public int countUser() {
		return userFilters.size();
	}

	public boolean hasGroup() {
		return groupFilters.size() > 0;
	}

	public int countGroup() {
		return groupFilters.size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_FILTERS_NAME"))
			this.name = name;
		else
			throw new AccessNotAllowedException(
					"You can't set a filter name as : " + name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description)
			throws AccessNotAllowedException {
		if (Tools.hasRight("SET_FILTERS_DESCRIPTION"))
			this.description = description;
		else
			throw new AccessNotAllowedException(
					"You can't set a filter description");
	}

	public String getFilterClass() {
		return class1;
	}

	public void setFilterClass(String type_name)
			throws AccessNotAllowedException {
		if (Tools.hasRight("SET_FILTERS_CLASS"))
			this.class1 = type_name;
		else
			throw new AccessNotAllowedException(
					"You can't set a filter class as: " + type_name);
	}

	private void invokeMethod(String typeName, String methodname)
			throws FatalErrorException {
		Class<?> c;
		try {
			c = Class.forName(typeName);
			Method method = c.getMethod(methodname);
			method.invoke(c.newInstance());
		} catch (Exception e) {
			throw new FatalErrorException(e.getMessage() + "\n"
					+ e.getStackTrace());
		}
	}

	public void appliPreFilter() throws FatalErrorException {
		for (GroupFilter group : groupFilters)
			if (Tools.getUser().userHasGroup(group.getGroup()))
				invokeMethod(group.getFilter().getFilterClass(), "preFilter");

		for (UserFilter user : userFilters)
			if (Tools.getUser().equals(user.getUser()))
				invokeMethod(user.getFilter().getFilterClass(), "preFilter");

	}

	public void appliPostFilter() throws FatalErrorException {
		for (GroupFilter group : groupFilters)
			if (Tools.getUser().userHasGroup(group.getGroup()))
				invokeMethod(group.getFilter().getFilterClass(), "postFilter");

		for (UserFilter user : userFilters)
			if (Tools.getUser().equals(user.getUser()))
				invokeMethod(user.getFilter().getFilterClass(), "postFilter");
	}

	public boolean isUse() {
		return userFilters.size() > 0;
	}

}
