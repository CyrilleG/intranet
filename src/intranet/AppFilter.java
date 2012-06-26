package intranet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import filters.*; // This import is useful to access to filter types. DO NOT DELETE
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import utils.Tools;
import intranet.AppFilter;
import intranet.GroupFilters;
import intranet.UserFilters;

@SuppressWarnings("unused")
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_filter")
@RooDbManaged(automaticallyDelete = true)
public class AppFilter {
	
    @OneToMany(mappedBy = "idfilter", cascade = CascadeType.ALL)
    private Set<GroupFilters> groupFilterss;
    
    @OneToMany(mappedBy = "idfilter", cascade = CascadeType.ALL)
    private Set<UserFilters> userFilterss;
    
    @Column(name = "name", length = 100)
    private String name;
    
    @Column(name = "description", length = 255)
    private String description;
    
    @Column(name = "class", length = 75)
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
    
    public void addFilterToGroup(AppGroup g)
    {
    	if (Tools.hasRight("ADD_FILTER_TO_GROUP"))
    	{
    		GroupFilters group = new GroupFilters();
    		group.setIdfilter(this);
    		group.setIdgroup(g);
    		groupFilterss.add(group);
    		group.persist();
    	}
    }
    
    public void removeFilterFromGroup(AppGroup group)
    {
    	if (Tools.hasRight("REMOVE_FILTER_FROM_GROUP"))
    	{
    		for (GroupFilters gp : groupFilterss)
	    		if(gp.getIdgroup().equals(group))
	    		{
	    			gp.remove();
	    			break;
	    		}
    	}
    }
    public void addFilterToUser(AppUser user)
    {
    	if (Tools.hasRight("ADD_FILTER_TO_USER"))
    	{
    		UserFilters group = new UserFilters();
    		group.setIdfilter(this);
    		group.setIduser(user);
    		userFilterss.add(group);
    		group.persist();
    	}
    }
    
    public void removeFilterFromUser(AppUser user)
    {
    	if (Tools.hasRight("REMOVE_FILTER_FROM_USER"))
    	{
    		for (UserFilters gp : userFilterss)
	    		if(gp.getIduser().equals(user))
	    		{
	    			gp.remove();
	    			break;
	    		}
    	}
    }
    
    public boolean hasUser()
    {
    	return userFilterss.size() > 0;
    }
    public int countUser()
    {
    	return userFilterss.size();
    }
    
    public boolean hasGroup()
    {
    	return groupFilterss.size() > 0;
    }
    public int countGroup()
    {
    	return groupFilterss.size();
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
    	for (GroupFilters group : groupFilterss)
    		if (Tools.getUser().hasGroup(group.getIdgroup()))
    			invokeMethod(group.getIdfilter().getFilterClass(), "preFilter");

    	for (UserFilters user : userFilterss)
    		if (Tools.getUser().equals(user.getIduser()))
    			invokeMethod(user.getIdfilter().getFilterClass(), "preFilter");

    	
    }
    public void appliPostFilter()
    {
    	for (GroupFilters group : groupFilterss)
    		if (Tools.getUser().hasGroup(group.getIdgroup()))
    			invokeMethod(group.getIdfilter().getFilterClass(), "postFilter");
    	
    	for (UserFilters user : userFilterss)
    		if (Tools.getUser().equals(user.getIduser()))
    			invokeMethod(user.getIdfilter().getFilterClass(), "postFilter");
    }
    
    public boolean isUse()
    {
    	return userFilterss.size() > 0;
    }

}
