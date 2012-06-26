package intranet;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_user")
@RooDbManaged(automaticallyDelete = true)
public class AppUser {

	
	@OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<InfoPrivacities> infoPrivacitieses;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<UserData> userDatas;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<UserFilters> userFilterss;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<UserGroups> userGroupss;

    @OneToMany(mappedBy = "iduser")
    private Set<UserInfo> userInfoes;

    @OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<UserRights> userRightss;

    @Column(name = "login", length = 45, unique = true)
    @NotNull
    private String login;

    @Column(name = "password", length = 100)
    @NotNull
    private String password;

    @Column(name = "enabled")
    @NotNull
    private boolean enabled;

   

    
    /*public void refreshRights()
    {
    	allrights = null;
    }*/
    public Set<AppRight> getAllAuthorities()
    {
    	//if(this.allrights != null)
    		//return this.allrights;
    	
    	Set<AppRight> rights = new HashSet<AppRight>();
    	for (UserRights g : userRightss)
    		rights.add(g.getIdright());
    	for (UserGroups g : userGroupss)
    		rights.addAll(g.getIdgroup().getRights());
    	//this.allrights = rights;
    	return rights;
    }
      
    public static boolean logIn(String login, String password)
    {
    	return false;
    }
    public static void logOut()
    {
    	
    }
    
    private int findUserDataByName(String name) {
        UserData[] elems = (UserData[]) userDatas.toArray();
        for (int i = 0; i < elems.length; i++) 
        	if (elems[i].getName().compareTo(name) == 0)
        		return i;
        return -1;
    }

    public boolean hasObject(String name) {
        return findUserDataByName(name) != -1;
    }

    public UserData getObject(String name) throws Exception {
        int index = findUserDataByName(name);
        if (index != -1) //TODO rights
        	return (UserData) userDatas.toArray()[index];
        else
        	throw new Exception("Object not found for this User");
    }

    public void removeObject(String name) throws Exception {
        int index = findUserDataByName(name);//TODO rights
        if (index != -1) {
            UserData e = (UserData) userDatas.toArray()[index];
            for (DataField f : e.getFields()) f.remove();
            	e.remove();
        } else throw new Exception("Object not found for this User");
    }

    public void setUserData(UserData o) throws Exception {
        if (!hasObject(o.getName())) //TODO rights
        	userDatas.add(o); 
        else 
        	throw new Exception("Object name must be unique for an User");
    }

    public void addFilterToUser(AppFilter filter) {
    	//TODO rights
    }
    public void removeFilterByName(String name) {
    	//TODO rights
    }
    public void removeFilterByObject(AppFilter filter) {
    	//TODO rights
    }
    public void applyPreFilter(AppFilter filter) {
    	//TODO rights
    }
    public void applyPostFilter(AppFilter filter) {
    	//TODO rights
    }
    public void applyUserPreFilters() {
    	//TODO rights
    }
    public void applyUserPostFilters() {
    	//TODO rights
    }
    
    public boolean userhasFilter(AppFilter filter)
    {
    	return false;//TODO rights
    }
    public void addUserToGroup(AppGroup group)
    {
    	//TODO rights
    }
    public void removeUserFromGroup(AppGroup group)
    {
    	//TODO rights
    }
    public boolean hasGroup(AppGroup group)
    {
    	return false;
    }
    
    public void setInformation(String key, String value)
    {
    	//TODO rights
    }

    public void setInformation(UserInfo info)
    {
    	//TODO rights
    }
    
    public String getInformation(String key)
    {
    	return null; //TODO rights + privacities
    }
    
    public void removeInformation(String key)
    {
    	//TODO rights  + privacities
    }
    
    public void removeInformation(UserInfo info)
    {
    	//TODO rights  + privacities
    }
    
    public void giveRight(AppRight right)
    {
    	//TODO rights
    }
    
    public void giveRight(String ident)
    {
    	//TODO rights
    }
    
    public boolean hasRight(AppRight right)
    {
    	return false;
    }
    
    public boolean hasRight(String ident)
    {
    	return false;
    }
    
    public void removeRight(AppRight right)
    {
    	//TODO rights
    }
    
    public void removeRight(String ident)
    {
    	//TODO rights
    }
    
    public String getLogin() {
        return login;//TODO rights
    }

    public void setLogin(String login) {
        this.login = login;//TODO rights
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;//TODO rights 
    }

    public boolean isEnabled() {
        return enabled;//TODO rights
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;//TODO rights
    }
    public UserData getData(String name)
    {
    	return null;
    }
    
    public void hasData(String name)
    {
    	
    }
    
    public void removeData(String name)
    {
    	
    }
    public boolean ownData()
    {
    	return false;
    }
    
    public void grantUser()
    {
    	
    }
    
    public void revokeGrant()
    {
    	
    }
    
    public boolean isAdmin()
    {
    	return false;
    }
}
