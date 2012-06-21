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
@RooJpaActiveRecord(versionField = "", table = "app_group")
@RooDbManaged(automaticallyDelete = true)
public class AppGroup {
	
    @OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
    private Set<ActionGroups>actionGroupss;
    
    @OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
    private Set<GroupFilters> groupFilterss;
    
    @OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
    private Set<GroupRights> groupRightss;
    
    @OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
    private Set<InfoPrivacities> infoPrivacitieses;
    
    @OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
    private Set<ModuleGroups> moduleGroupss;
    
    @OneToMany(mappedBy = "idgroup", cascade = CascadeType.ALL)
    private Set<UserGroups>  userGroupss;
    
    @Column(name = "name", length = 100, unique = true)
    @NotNull
    private String name;
    
    @Column(name = "description", length = 255)
    private String description;

    public void addRightToGroup(String ident)
    {
    	//TODO rights
    }
    public void removeRightToGroup(String ident)
    {
    	//TODO rights
    }
    
    public void addRightFromGroup(AppRight right)
    {
    	//TODO rights
    }
    public void removeRightFromGroup(AppRight right)
    {
    	//TODO rights
    }
    public boolean groupHasRight(String ident)
    {
    	return false;
    }
    public boolean groupHasRight(AppRight right)
    {
    	return false;
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
    
    public boolean isUse()
    {
    	return actionGroupss.size() > 0 
    			|| groupFilterss.size() > 0 
    			|| groupRightss.size() > 0 
    			|| infoPrivacitieses.size() > 0 
    			|| moduleGroupss.size() > 0 
    			|| userGroupss.size() > 0; 
    }
}
