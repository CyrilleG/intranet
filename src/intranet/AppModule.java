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

import utils.Tools;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_module")
@RooDbManaged(automaticallyDelete = true)
public class AppModule {
@OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private Set<ModuleAction> moduleActions;
    
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private Set<ModuleData> moduleDatas;
    
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private Set<ModuleGroup> moduleGroups;
    
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    private Set<ModuleRight> moduleRights;
    
    @Column(name = "name", columnDefinition = "VARCHAR", length = 100, unique = true)
    private String name;
    
    @Column(name = "description", columnDefinition = "VARCHAR", length = 255)
    private String description;
    
    @Column(name = "class", columnDefinition = "VARCHAR", length = 100)
    private String class1;
    
    @Column(name = "enabled", columnDefinition = "BIT")
    @NotNull
    private boolean enabled;
    
	public static AppModule createModule(String name, String description, String controller) {
		if (Tools.hasRight("ADD_MODULE")) {
			AppModule module = new AppModule();
			module.setName(name);
			module.setDescription(description);
			module.setModuleController(controller);
			module.persist();
			return module;
		}
		return null;
	}
	
	
	public boolean install()
	{
		return false;
	}
	
	public void uninstall()
	{
		
	}
	
    public void addActionToModule(ModuleAction action)
    {
    	if (Tools.hasRight("ADD_ACTION_TO_MODULE")) {
			ModuleAction module = new ModuleAction();
			module.setModule(this);
			module.persist();
			moduleActions.add(module);
		}
    }
    public void removeActionFromModule(ModuleAction ident)
    {
    	if (Tools.hasRight("REMOVE_ACTION_FROM_MODULE")) {
			for (ModuleAction gp : moduleActions)
				if (gp.equals(ident)) {
					gp.remove();
					break;
				}
		}
    }
    public boolean moduleHasAction(ModuleAction ident)
    {
    	for (ModuleAction gp : moduleActions)
    		if(gp.equals(ident))
    			return true;
		return false;
    }
    
    public void addModuleToGroup(AppGroup p)
    {
    	if (Tools.hasRight("ADD_MODULE_TO_GROUP")) {
			ModuleGroup groupuser = new ModuleGroup();
			groupuser.setGroup(p);
			groupuser.setModule(this);
			groupuser.persist();
			moduleGroups.add(groupuser);
		}
    }
    
    public void removeModuleFromGroup(AppGroup ident)
    {
    	if (Tools.hasRight("REMOVE_MODULE_FROM_GROUP")) {
			for (ModuleGroup gp : moduleGroups)
				if (gp.getGroup().equals(ident)) {
					gp.remove();
					break;
				}
		}
    }
    public boolean groupCanAccessToModule(AppGroup ident)
    {
    	for (ModuleGroup gp : moduleGroups)
    		if(gp.getGroup().equals(ident))
    			return true;
		return false;
    }
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
    	if (Tools.hasRight("SET_MODULE_NAME"))
    		this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
    	if (Tools.hasRight("SET_MODULE_DESCRIPTION"))
    		this.description = description;
    }
    
    public String getModuleController() {
        return class1;
    }
    
    public void setModuleController(String controller) {
    	if (Tools.hasRight("SET_MODULE_CONTROLLER"))
    		this.class1 = controller;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
    	if (Tools.hasRight("SET_MODULE_ENABLED"))
    		this.enabled = enabled;
    }
    public boolean hasAction()
    {
    	return moduleActions.size() > 0;
    }
    public int countAction()
    {
    	return moduleActions.size();
    }
    
    public boolean hasGroup()
    {
    	return moduleGroups.size() > 0;
    }
    public int countGroup()
    {
    	return moduleGroups.size();
    }
}
