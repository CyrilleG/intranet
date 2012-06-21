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
@RooJpaActiveRecord(versionField = "", table = "user_info")
@RooDbManaged(automaticallyDelete = true)
public class UserInfo {
	   @OneToMany(mappedBy = "idinfo", cascade = CascadeType.ALL)
	    private Set<InfoPrivacities> infoPrivacitieses;
	    
	    @ManyToOne
	    @JoinColumn(name = "iduser", referencedColumnName = "iduser", nullable = false)
	    private AppUser iduser;
	    
	    @Column(name = "key", length = 100, unique = true)
	    @NotNull
	    private String key;
	    
	    @Column(name = "value", length = 255)
	    private String value;
	    
	    @Column(name = "show")
	    @NotNull
	    private boolean show;
	    
	    public boolean canBeViewBy(AppUser user)
	    {
	    	return false;
	    }
	    
	    public AppUser getUser() {
	        return iduser;//TODO rights
	    }
	    
	    public void setUser(AppUser iduser) {
	        this.iduser = iduser;//TODO rights
	    }
	    
	    public String getKey() {
	        return key;
	    }
	    
	    public void setKey(String key) {
	        this.key = key;//TODO rights
	    }
	    
	    public String getValue() {
	        return value;//TODO rights + privacities
	    }
	    
	    public void setValue(String value) {
	        this.value = value;//TODO rights
	    }
	    
	    public boolean isShow() {
	        return show;
	    }
	    
	    public void setShow(boolean show) {
	        this.show = show;//TODO rights
	    }
}
