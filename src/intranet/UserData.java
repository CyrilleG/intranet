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
@RooJpaActiveRecord(versionField = "", table = "user_data")
@RooDbManaged(automaticallyDelete = true)
public class UserData {

	@OneToMany(mappedBy = "idobject", cascade = CascadeType.ALL)
    private Set<DataField> dataFields;

	@ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser", nullable = false)
    private AppUser iduser;

	@Column(name = "name", length = 100)
    @NotNull
    private String name;

	public Set<DataField> getFields() {
        return dataFields;//TODO rights
    }
	public DataField getField(String name) {
        return null;
    }
	public boolean hasField(String name) {
        return false;
    }
	public void setField(String key, String value) {
		//TODO rights
    }

	public AppUser getUser() {
        return iduser;//TODO rights
    }

	public void setIduser(AppUser iduser) {
        this.iduser = iduser;//TODO rights
    }

	public String getName() {
        return name;//TODO rights
    }

	public void setName(String name) {
        this.name = name;//TODO rights
    }
}
