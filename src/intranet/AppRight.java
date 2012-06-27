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

import utils.Tools;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_right")
@RooDbManaged(automaticallyDelete = true)
public class AppRight implements GrantedAuthority
{
	
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Override
		public String getAuthority() {
			// TODO Auto-generated method stub
			return null;
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
    
    @Column(name = "description", columnDefinition = "VARCHAR", length = 255)
    private String description;
	   
	    public static AppRight createRight(String name, String ident, String description) {
			if (Tools.hasRight("ADD_RIGHT")) {
				AppRight right = new AppRight();
				right.setName(name);
				right.setDescription(description);
				right.setIdent(ident);
				right.persist();
				return right;
			}
			return null;
		}
	    
	    public void addRightToAction(ModuleAction action)
	    {
	    	if (Tools.hasRight("ADD_RIGHT_TO_ACTION")) {
				ActionRight element = new ActionRight();
				element.setRight(this);
				element.setAction(action);
				element.persist();
				actionRights.add(element);
			}
	    }
	    public void removeRightFromAction(ModuleAction action)
	    {
	    	if (Tools.hasRight("REMOVE_RIGHT_FROM_ACTION")) {
				for (ActionRight gp : actionRights)
					if (gp.getAction().equals(ident)) {
						gp.remove();
						break;
					}
			}
	    }
	    public boolean actionHasRight(ModuleAction action)
	    {
	    	for (ActionRight gp : actionRights)
	    		if(gp.getAction().equals(ident))
	    			return true;
			return false;
	    }
	    
	    
	    public void addRightToGroup(AppGroup group)
	    {
	    	if (Tools.hasRight("ADD_RIGHT_TO_GROUP")) {
				GroupRight groupright = new GroupRight();
				groupright.setGroup(group);
				groupright.setRight(this);
				groupright.persist();
				groupRights.add(groupright);
			}
	    }
	    public void removeRightFromGroup(AppGroup group)
	    {
	    	if (Tools.hasRight("REMOVE_RIGHT_FROM_GROUP")) {
				for (GroupRight gp : groupRights)
					if (gp.getGroup().equals(group)) {
						gp.remove();
						break;
					}
			}

	    }
	    public boolean groupHasRight(AppGroup group)
	    {
	    	for (GroupRight gp : groupRights)
	    		if(gp.getGroup().equals(group))
	    			return true;
			return false;
	    }
	    
	    
	    public void addRightToModule(AppModule module)
	    {
	    	if (Tools.hasRight("ADD_RIGHT_TO_MODULE")) {
				ModuleRight element = new ModuleRight();
				element.setRight(this);
				element.setModule(module);
				element.persist();
				moduleRights.add(element);
			}
	    }
	    public void removeRightFromModule(AppModule module)
	    {
	    	if (Tools.hasRight("REMOVE_RIGHT_FROM_MODULE")) {
				for (ModuleRight gp : moduleRights)
					if (gp.getModule().equals(module)) {
						gp.remove();
						break;
					}
			}
	    }
	    public boolean moduleHasRight(AppModule module)
	    {
	    	for (ModuleRight gp : moduleRights)
	    		if(gp.getModule().equals(module))
	    			return true;
			return false;
	    }
	    
	    
	    public void addRightToUser(AppUser user)
	    {
	    	if (Tools.hasRight("ADD_RIGHT_TO_USER")) {
				UserRight element = new UserRight();
				element.setRight(this);
				element.setUser(user);
				element.persist();
				userRights.add(element);
			}
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
	    	return actionRights.size() > 0 || groupRights.size() > 0 || moduleRights.size() > 0 || userRights.size() > 0;
	    }
		
	    
}
