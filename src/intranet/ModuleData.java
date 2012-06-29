package intranet;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "module_data")
@RooDbManaged(automaticallyDelete = true)
public class ModuleData {

	@OneToMany(mappedBy = "moduleData", cascade = CascadeType.ALL)
    private Set<DataField> dataFields;

	@OneToMany(mappedBy = "data", cascade = CascadeType.ALL)
    private Set<DataGroup> dataGroups;

	@OneToMany(mappedBy = "data", cascade = CascadeType.ALL)
    private Set<DataRight> dataRights;

	@OneToMany(mappedBy = "data", cascade = CascadeType.ALL)
    private Set<DataUser> dataUsers;

	@ManyToOne
    @JoinColumn(name = "module", referencedColumnName = "module", nullable = false)
    private AppModule module;

	@Column(name = "name", columnDefinition = "VARCHAR", length = 45)
    @NotNull
    private String name;

	public AppModule getModule() {
        return module;
    }

	public void setModule(AppModule module) {
        this.module = module;
    }

	public String getName() {
        return name;
    }

	public void setName(String name) {
        this.name = name;
    }
}
