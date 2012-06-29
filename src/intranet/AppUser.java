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
@RooJpaActiveRecord(versionField = "", table = "app_user")
@RooDbManaged(automaticallyDelete = true)
public class AppUser {

	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<DataUser> dataUsers;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<InfoPrivacity> infoPrivacities;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserFilter> userFilters;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserGroup> userGroups;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRight> userRights;
    
    @Column(name = "login", columnDefinition = "VARCHAR", length = 45, unique = true)
    @NotNull
    private String login;
    
    @Column(name = "password", columnDefinition = "VARCHAR", length = 100)
    @NotNull
    private String password;
    
    @Column(name = "enabled", columnDefinition = "BIT")
    @NotNull
    private boolean enabled;

    public static AppUser findFilterByLogin(String login) {
		List<AppUser> elements = AppUser.findAllAppUsers();
		for (AppUser element : elements)
			if (element.getLogin().compareToIgnoreCase(login) == 0)
				return element;
		return null;
	}
    public AppUser createUser(String login, String password, boolean enabled)
    {
    	if (Tools.hasRight("ADD_USER"))
    	{
	    	AppUser user = new AppUser();
	    	user.setLogin(login);
	    	user.setPassword(password);
	    	user.setEnabled(enabled);
	    	user.persist();
	    	return user;
    	}
    	return null;
    }
    public Set<AppRight> getAllAuthorities()
    {    	
    	Set<AppRight> rights = new HashSet<AppRight>();
    	for (UserRight g : userRights)
    		rights.add(g.getRight());
    	for (UserGroup g : userGroups)
    		rights.addAll(g.getGroup().getRights());
    	return rights;
    }
     
    public Set<AppGroup> getAllGroups()
    {    	
    	Set<AppGroup> groups = new HashSet<AppGroup>();
    	for (UserGroup g : userGroups)
    		groups.add(g.getGroup());
    	return groups;
    }
    public static boolean logIn(String login, String password)
    {
    	return false;
    }
    public static void logOut()
    {
    	
    }
    
    /*private int findUserDataByName(String name) {
        UserData[] elems = (UserData[]) userDatas.toArray();
        for (int i = 0; i < elems.length; i++) 
        	if (elems[i].getName().compareTo(name) == 0)
        		return i;
        return -1;
    }*/
/*
    public boolean hasObject(String name) {
        return findUserDataByName(name) != -1;
    }
*/
    public ModuleData getModuleData(String name) throws Exception {
		if (Tools.hasRight("GET_DATA_FROM_OTHER_USER")
				|| Tools.getUser().equals(this)) 
    	for (DataUser data : dataUsers)
        	if (data.getData().getName().compareTo(name) == 0)
        		return data.getData();
        return null;
    }

    public void removeModuleData(String name) throws Exception {
    	if (Tools.hasRight("REMOVE_DATA_FROM_USER") || Tools.getUser().equals(this)) 
	    	for (DataUser data : dataUsers)
	        	if (data.getData().getName().compareTo(name) == 0)
	        		data.remove();
    }

    public void addModuleData(ModuleData data) throws Exception {
    	if (Tools.hasRight("ADD_DATA_TO_USER")) {
			DataUser elem = new DataUser();
			elem.setData(data);
			elem.setUser(this);
			elem.persist();
			dataUsers.add(elem);
		}
    }
    public boolean moduleHasData(ModuleData data)
    {
    	for (DataUser elem : dataUsers)
    		if (elem.getData().equals(data))
    			return true;
    	return false;
    }
    public void addFilterToUser(AppFilter filter) {
    	if (Tools.hasRight("ADD_FILTER_TO_USER"))
    	{
    		UserFilter group = new UserFilter();
    		group.setFilter(filter);
    		group.setUser(this);
    		userFilters.add(group);
    		group.persist();
    	}
    }
    public void removeFilterFromUser(AppFilter filter) {
    	if (Tools.hasRight("REMOVE_FILTER_FROM_USER"))
    	{
    		for (UserFilter gp : userFilters)
	    		if(gp.getFilter().equals(filter))
	    		{
	    			gp.remove();
	    			break;
	    		}
    	}
    }
    
    public boolean userHasFilter(AppFilter filter)
    {
    	for (UserFilter elem : userFilters)
    		if (elem.getFilter().equals(filter))
    			return true;
    	return false;
    }
    
    public void applyPreFilter(AppFilter filter) {
    	filter.appliPreFilter();
    }
    public void applyPostFilter(AppFilter filter) {
    	filter.appliPostFilter();
    }
    public void applyUserPreFilters() {
    	for(UserFilter filter: userFilters)
    		filter.getFilter().appliPreFilter();
    }
    public void applyUserPostFilters() {
    	for(UserFilter filter: userFilters)
    		filter.getFilter().appliPostFilter();
    }
    
    public void addUserToGroup(AppGroup group)
    {
    	if (Tools.hasRight("ADD_USER_TO_GROUP")) {
			UserGroup groupuser = new UserGroup();
			groupuser.setGroup(group);
			groupuser.setUser(this);
			groupuser.persist();
			userGroups.add(groupuser);
		}
    }
    public void removeUserFromGroup(AppGroup group)
    {
    	if (Tools.hasRight("REMOVE_USER_FROM_GROUP")) {
			for (UserGroup gp : userGroups)
				if (gp.getGroup().equals(group)) {
					gp.remove();
					break;
				}
		}
    }
    public boolean userHasGroup(AppGroup ident)
    {
    	for (UserGroup elem : userGroups)
			if (elem.getGroup().equals(ident))
				return true;
		return false;
    }

    public void addInformationToUser(UserInfo info, AppGroup privacity)
    {
    	if (Tools.hasRight("ADD_INFO_TO_OTHER_USER") || Tools.hasRight("ADD_INFO"))
    	{
    		InfoPrivacity priv = new InfoPrivacity();
    		priv.setInfo(info);
    		priv.setGroup(privacity);
    		priv.setUser(this);
    		priv.persist();
    	}
    }
    
    public UserInfo getInformation(String key)
    {
    	for (InfoPrivacity element : infoPrivacities)
    		if (element.getInfo().getKey().compareTo(key) == 0 && (
    				Tools.getUser().userHasGroup(element.getGroup()) || Tools.getUser().isAdmin()))
    			return element.getInfo();
    	return null;
    			
    }
    
    public void removeInformationFromUser(UserInfo info)
    {
    	if (Tools.hasRight("REMOVE_INFO_FROM_OTHER_USER") || info.isEditable()) {
			for (InfoPrivacity element : infoPrivacities)
				if (element.getInfo().equals(info)) {
					element.remove();
					break;
				}
		}
    }
    
    public void addRightToUser(AppRight right)
    {
    	if (Tools.hasRight("ADD_RIGHT_TO_USER")) {
			UserRight element = new UserRight();
			element.setRight(right);
			element.setUser(this);
			element.persist();
			userRights.add(element);
		}
    }
    
    public boolean userHasRight(AppRight right)
    {
    	for(UserRight item : userRights)
    		if(item.getRight().equals(right))
    			return true;
    	return false;
    }
    
    public boolean userHasRight(String right)
    {
    	for(UserRight item : userRights)
    		if(item.getRight().getIdent().compareToIgnoreCase(right) == 0)
    			return true;
    	return false;
    }
    
    public void removeRightFromUser(AppRight right)
    {
    	if (Tools.hasRight("REMOVE_RIGHT_FROM_USER")) {
			for (UserRight gp : userRights)
				if (gp.getRight().equals(right)) {
					gp.remove();
					break;
				}
		}
    }

    
    public String getLogin() {
    		return login;
    }

    public void setLogin(String login) {
    	if (Tools.hasRight("SET_USER_LOGIN"))
    		this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
    	if (Tools.hasRight("SET_USER_PASSWORD"))
    		this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
    	if (Tools.hasRight("SET_USER_ENABLED"))
    		this.enabled = enabled;
    }
    public boolean isAdmin()
    {
    	return false;
    }
}
