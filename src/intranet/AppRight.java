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
				ActionRights element = new ActionRights();
				element.setIdright(this);
				element.setIdaction(action);
				element.persist();
				actionRightss.add(element);
			}
	    }
	    public void removeRightFromAction(ModuleAction action)
	    {
	    	if (Tools.hasRight("REMOVE_RIGHT_FROM_ACTION")) {
				for (ActionRights gp : actionRightss)
					if (gp.getIdaction().equals(ident)) {
						gp.remove();
						break;
					}
			}
	    }
	    public boolean actionHasRight(ModuleAction action)
	    {
	    	for (ActionRights gp : actionRightss)
	    		if(gp.getIdaction().equals(ident))
	    			return true;
			return false;
	    }
	    
	    
	    public void addRightToGroup(AppGroup group)
	    {
	    	if (Tools.hasRight("ADD_RIGHT_TO_GROUP")) {
				GroupRights groupright = new GroupRights();
				groupright.setIdgroup(group);
				groupright.setIdright(this);
				groupright.persist();
				groupRightss.add(groupright);
			}
	    }
	    public void removeRightFromGroup(AppGroup group)
	    {
	    	if (Tools.hasRight("REMOVE_RIGHT_FROM_GROUP")) {
				for (GroupRights gp : groupRightss)
					if (gp.getIdgroup().equals(group)) {
						gp.remove();
						break;
					}
			}

	    }
	    public boolean groupHasRight(AppGroup group)
	    {
	    	for (GroupRights gp : groupRightss)
	    		if(gp.getIdgroup().equals(group))
	    			return true;
			return false;
	    }
	    
	    
	    public void addRightToModule(AppModule module)
	    {
	    	if (Tools.hasRight("ADD_RIGHT_TO_MODULE")) {
				ModuleRights element = new ModuleRights();
				element.setIdright(this);
				element.setIdmodule(module);
				element.persist();
				moduleRightss.add(element);
			}
	    }
	    public void removeRightFromModule(AppModule module)
	    {
	    	if (Tools.hasRight("REMOVE_RIGHT_FROM_MODULE")) {
				for (ModuleRights gp : moduleRightss)
					if (gp.getIdmodule().equals(module)) {
						gp.remove();
						break;
					}
			}
	    }
	    public boolean moduleHasRight(AppModule module)
	    {
	    	for (ModuleRights gp : moduleRightss)
	    		if(gp.getIdmodule().equals(module))
	    			return true;
			return false;
	    }
	    
	    
	    public void addRightToUser(AppUser user)
	    {
	    	if (Tools.hasRight("ADD_RIGHT_TO_USER")) {
				UserRights element = new UserRights();
				element.setIdright(this);
				element.setIduser(user);
				element.persist();
				userRightss.add(element);
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
	    	return actionRightss.size() > 0 || groupRightss.size() > 0 || moduleRightss.size() > 0 || userRightss.size() > 0;
	    }
		
	    
}
