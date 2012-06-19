package intranet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "user")
@RooDbManaged(automaticallyDelete = true)
public class User {
	//private org.springframework.security.core.userdetails.User springUser;
	

	@OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<Objects> objectss;

	@OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<Privacities> privacitieses;

	@OneToMany(mappedBy = "iduser", cascade = CascadeType.ALL)
    private Set<Session> sessions;

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
	
	private int findObjectsByName(String name)
	{
		Objects[] elems = (Objects[]) objectss.toArray();
		for (int i = 0; i < elems.length; i++)
			if (elems[i].getName().compareTo(name) == 0)
				return i;
		return -1;
		
	}
	public boolean hasObject(String name)
	{
		return findObjectsByName(name) != -1;
	}
	public Objects getObject(String name) throws Exception
	{
		int index = findObjectsByName(name);
		if (index != -1)
			return (Objects) objectss.toArray()[index]; //TODO rights
		else
			throw new Exception("Object not found for this User");
	}
	
	public void removeObject(String name) throws Exception
	{
		int index = findObjectsByName(name);
		if (index != -1)
		{
			Objects e = (Objects) objectss.toArray()[index];//TODO rights
			for(Fields f : e.getFieldss())
				f.remove();
			e.remove();
		}
		else
			throw new Exception("Object not found for this User");
	}
	
	public void setObjects(Objects o) throws Exception{
        if (!hasObject(o.getName())) //TODO rights
        	objectss.add(o);
        else
        	throw new Exception("Object name must be unique for an User");
    }

	public Set<Privacities> getPrivacitieses() {
        return privacitieses;
    }

	public void setPrivacitieses(Set<Privacities> privacitieses) {
        this.privacitieses = privacitieses;
    }

	public Set<Session> getSessions() {
        return sessions;
    }

	public void setSessions(Set<Session> sessions) {
        this.sessions = sessions;
    }

	public Set<UserFilters> getUserFilterss() {
        return userFilterss;
    }

	public void setUserFilterss(Set<UserFilters> userFilterss) {
        this.userFilterss = userFilterss;
    }

	public Set<UserGroups> getUserGroupss() {
        return userGroupss;
    }

	public void setUserGroupss(Set<UserGroups> userGroupss) {
        this.userGroupss = userGroupss;
    }

	public Set<UserInfo> getUserInfoes() {
        return userInfoes;
    }

	public void setUserInfoes(Set<UserInfo> userInfoes) {
        this.userInfoes = userInfoes;
    }

	public Set<UserRights> getUserRightss() {
        return userRightss;
    }

	public void setUserRightss(Set<UserRights> userRightss) {
        this.userRightss = userRightss;
    }

	public String getLogin() {
        return login;
    }

	public void setLogin(String login) {
        this.login = login;
    }

	public String getPassword() {
        return password;
    }

	public void setPassword(String password) {
        this.password = password;
    }

	public boolean isEnabled() {
        return enabled;
    }

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
    }
}
