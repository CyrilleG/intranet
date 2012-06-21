package intranet;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "module_action")
@RooDbManaged(automaticallyDelete = true)
public class ModuleAction {
	
	  @OneToMany(mappedBy = "idaction", cascade = CascadeType.ALL)
	    private Set<ActionGroups> actionGroupss;
	    
	    @OneToMany(mappedBy = "idaction", cascade = CascadeType.ALL)
	    private Set<ActionRights> actionRightss;
	    
	    @ManyToOne
	    @JoinColumn(name = "idmodule", referencedColumnName = "idmodule", nullable = false)
	    private AppModule idmodule;
	    
	    @Column(name = "method", length = 100)
	    @NotNull
	    private String method;
	    
	    @Column(name = "template", length = 100)
	    private String template;
	    
	    @Column(name = "enabled")
	    @NotNull
	    private boolean enabled;
	    
	    public boolean canBeUse()
	    {
	    	return actionGroupss.size() > 0 || actionRightss.size() > 0;
	    }
	    
	    public AppModule getModule() {
	        return idmodule;
	    }
	    
	    public void setModule(AppModule idmodule) {
	        this.idmodule = idmodule;//TODO rights
	    }
	    
	    public String getMethod() {
	        return method;//TODO rights
	    }
	    
	    public void setMethod(String method) {
	        this.method = method;//TODO rights
	    }
	    
	    public String getTemplate() {
	        return template;
	    }
	    
	    public void setTemplate(String template) {
	        this.template = template;//TODO rights
	    }
	    
	    public boolean isEnabled() {
	        return enabled;
	    }
	    
	    public void setEnabled(boolean enabled) {
	        this.enabled = enabled;//TODO rights
	    }
}
