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
@RooJpaActiveRecord(versionField = "", table = "app_module")
@RooDbManaged(automaticallyDelete = true)
public class AppModule {
    @OneToMany(mappedBy = "idmodule", cascade = CascadeType.ALL)
    private Set<ModuleAction> moduleActions;
    
    @OneToMany(mappedBy = "idmodule", cascade = CascadeType.ALL)
    private Set<ModuleGroups> moduleGroupss;
    
    @Column(name = "name", length = 100)
    private String name;
    
    @Column(name = "description", length = 255)
    private String description;
    
    @Column(name = "class", length = 100)
    private String class1;
    
    @Column(name = "enabled")
    @NotNull
    private boolean enabled;
    
    
    public void addActionToModule(ModuleAction a)
    {
    	//TODO rights
    }
    public void removeActionFromModule(ModuleAction a)
    {
    	//TODO rights
    }
    
    public void addModuleToGroup(AppGroup p)
    {
    	//TODO rights
    }
    
    public void removeModuleFromGroup(AppGroup p)
    {
    	//TODO rights
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
    
    public String getModuleClass() {
        return class1;//TODO rights
    }
    
    public void setModuleClass(String type_name) {
        this.class1 = type_name;//TODO rights
    }
    
    public boolean isEnabled() {
        return enabled;//TODO rights
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;//TODO rights
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
    	return moduleGroupss.size() > 0;
    }
    public int countGroup()
    {
    	return moduleGroupss.size();
    }
}
