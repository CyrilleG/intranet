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
import org.springframework.security.core.GrantedAuthority;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_right")
@RooDbManaged(automaticallyDelete = true)
public class AppRight implements GrantedAuthority
{
	
		@Override
		public String getAuthority() {
			// TODO Auto-generated method stub
			return null;
		}
	
	 	@OneToMany(mappedBy = "idright", cascade = CascadeType.ALL)
	    private Set<ActionRights> actionRightss;
	    
	    @OneToMany(mappedBy = "idright", cascade = CascadeType.ALL)
	    private Set<GroupRights> groupRightss;
	    
	    @OneToMany(mappedBy = "idright", cascade = CascadeType.ALL)
	    private Set<ModuleRights> moduleRightss;
	    
	    @OneToMany(mappedBy = "idright", cascade = CascadeType.ALL)
	    private Set<UserRights> userRightss;
	    
	    @Column(name = "ident", length = 70, unique = true)
	    @NotNull
	    private String ident;
	    
	    @Column(name = "name", length = 100)
	    @NotNull
	    private String name;
	    
	    @Column(name = "description", length = 255)
	    private String description;
	   
	    
	    public void addRightToAction(ModuleAction action)
	    {
	    	//TODO rights
	    }
	    public void removeRightFromAction(ModuleAction action)
	    {
	    	//TODO rights
	    }
	    public boolean actionHasRight(ModuleAction action)
	    {
	    	return false;
	    }
	    
	    
	    public void addRightToGroup(AppGroup group)
	    {
	    	//TODO rights
	    }
	    public void removeRightFromGroup(AppGroup group)
	    {
	    	//TODO rights
	    }
	    public boolean groupHasRight(AppGroup group)
	    {
	    	return false;
	    }
	    
	    
	    public void addRightToModule(AppModule module)
	    {
	    	//TODO rights
	    }
	    public void removeRightFromModule(AppModule module)
	    {
	    	//TODO rights
	    }
	    public boolean moduleHasRight(AppModule module)
	    {
	    	return false;
	    }
	    
	    
	    public void addRightToUser(AppUser user)
	    {
	    	//TODO rights
	    }
	    public void removeRightFromUser(AppUser user)
	    {
	    	//TODO rights
	    }
	    public boolean userHasRight(AppUser user)
	    {
	    	return false;
	    }
	    
	    public String getIdent() {
	        return ident;//TODO rights
	    }
	    
	    public void setIdent(String ident) {
	        this.ident = ident;//TODO rights
	    }
	    
	    public String getName() {
	        return name;//TODO rights
	    }
	    
	    public void setName(String name) {
	        this.name = name;//TODO rights
	    }
	    
	    public String getDescription() {
	        return description;//TODO rights
	    }
	    
	    public void setDescription(String description) {
	        this.description = description;//TODO rights
	    }
	    public boolean isRightUsed()
	    {
	    	return actionRightss.size() > 0 || groupRightss.size() > 0 || moduleRightss.size() > 0 || userRightss.size() > 0;
	    }
		
	    
}
