package intranet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;
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
    
    public AppFilter createFilter(String name, String description, String classname)
    {
    	if (Tools.hasRight("ADD_FILTER"))
    	{
	    	AppFilter f = new AppFilter();
	    	f.setDescription(description);
	    	f.setFilterClass(classname);
	    	f.setName(name);
	    	f.persist();
	    	return f;
    	}
    	return null;
    }
    
    public static AppFilter findFilterByName(String name) {
		List<AppFilter> elements = AppFilter.findAllAppFilters();
		for (AppFilter element : elements)
			if (element.getName().compareToIgnoreCase(name) == 0)
				return element;
		return null;
	}
    
    public void addFilterToGroup(AppGroup g)
    {
    	if (Tools.hasRight("ADD_FILTER_TO_GROUP"))
    	{
    		GroupFilter group = new GroupFilter();
    		group.setFilter(this);
    		group.setGroup(g);
    		groupFilters.add(group);
    		group.persist();
    	}
    }
    
    public void removeFilterFromGroup(AppGroup group)
    {
    	if (Tools.hasRight("REMOVE_FILTER_FROM_GROUP"))
    	{
    		for (GroupFilter gp : groupFilters)
	    		if(gp.getGroup().equals(group))
	    		{
	    			gp.remove();
	    			break;
	    		}
    	}
    }
    
    public boolean filterHasGroup(AppGroup group)
    {
    	return groupFilters.contains(group);
    }

    public void addFilterToUser(AppUser user)
    {
    	if (Tools.hasRight("ADD_FILTER_TO_USER"))
    	{
    		UserFilter group = new UserFilter();
    		group.setFilter(this);
    		group.setUser(user);
    		userFilters.add(group);
    		group.persist();
    	}
    }
    
    public void removeFilterFromUser(AppUser user)
    {
    	if (Tools.hasRight("REMOVE_FILTER_FROM_USER"))
    	{
    		for (UserFilter gp : userFilters)
	    		if(gp.getUser().equals(user))
	    		{
	    			gp.remove();
	    			break;
	    		}
    	}
    }
    
    public boolean filterHasUser(AppUser user)
    {
    	for (UserFilter elem : userFilters)
    		if (elem.getUser().equals(user))
    			return true;
    	return false;
    }
    
    public boolean hasUser()
    {
    	return userFilters.size() > 0;
    }
    public int countUser()
    {
    	return userFilters.size();
    }
    
    public boolean hasGroup()
    {
    	return groupFilters.size() > 0;
    }
    public int countGroup()
    {
    	return groupFilters.size();
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	if (Tools.hasRight("SET_FILTERS_NAMES"))
    		this.name = name;
    }
    
    public String getDescription() {
    		return description;
    }
    
    public void setDescription(String description) {
    	if (Tools.hasRight("SET_FILTERS_DESCRIPTIONS"))
    		this.description = description;
    }
    
    public String getFilterClass() {
    	return class1;
    }
    
    public void setFilterClass(String type_name) {
    	if (Tools.hasRight("SET_FILTERS_CLASS"))
    		this.class1 = type_name;
    }
    
    private void invokeMethod(String typeName, String methodname)
    {
    	Class<?> c;
		try {
			c = Class.forName(typeName);
			Method method = c.getMethod(methodname);
			method.invoke(c.newInstance());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    public void appliPreFilter()
    {
    	for (GroupFilter group : groupFilters)
    		if (Tools.getUser().userHasGroup(group.getGroup()))
    			invokeMethod(group.getFilter().getFilterClass(), "preFilter");

    	for (UserFilter user : userFilters)
    		if (Tools.getUser().equals(user.getUser()))
    			invokeMethod(user.getFilter().getFilterClass(), "preFilter");

    	
    }
    public void appliPostFilter()
    {
    	for (GroupFilter group : groupFilters)
    		if (Tools.getUser().userHasGroup(group.getGroup()))
    			invokeMethod(group.getFilter().getFilterClass(), "postFilter");
    	
    	for (UserFilter user : userFilters)
    		if (Tools.getUser().equals(user.getUser()))
    			invokeMethod(user.getFilter().getFilterClass(), "postFilter");
    }
    
    public boolean isUse()
    {
    	return userFilters.size() > 0;
    }

}
