package intranet;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "data_field")
@RooDbManaged(automaticallyDelete = true)
public class DataField {
	
    @ManyToOne
    @JoinColumn(name = "module_data", referencedColumnName = "module_data", nullable = false)
    private ModuleData moduleData;
    
    @Column(name = "name", columnDefinition = "VARCHAR", length = 100)
    @NotNull
    private String name;
    
    @Column(name = "value", columnDefinition = "VARCHAR", length = 255)
    private String value;
    
    @Column(name = "type", columnDefinition = "VARCHAR", length = 45)
    @NotNull
    private String type;
    
    /*public UserData getIdobject() {
        return idobject;
    }
    
    public void setIdobject(UserData idobject) {
        this.idobject = idobject;//TODO rights
    }*/
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;//TODO rights
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;//TODO rights
    }
}
