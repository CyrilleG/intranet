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

	@OneToMany(mappedBy = "action", cascade = CascadeType.ALL)
	private Set<ActionGroup> actionGroups;

	@OneToMany(mappedBy = "action", cascade = CascadeType.ALL)
	private Set<ActionRight> actionRights;

	@ManyToOne
	@JoinColumn(name = "module", referencedColumnName = "module", nullable = false)
	private AppModule module;

	@Column(name = "method", columnDefinition = "VARCHAR", length = 100)
	@NotNull
	private String method;

	@Column(name = "enabled", columnDefinition = "BIT")
	@NotNull
	private boolean enabled;

	public boolean canBeUse() {
		return actionGroups.size() > 0 || actionRights.size() > 0;
	}

	public AppModule getModule() {
		return module;
	}

	public void setModule(AppModule idmodule) {
		this.module = idmodule;// TODO rights
	}

	public String getMethod() {
		return method;// TODO rights
	}

	public void setMethod(String method) {
		this.method = method;// TODO rights
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;// TODO rights
	}

	public void allowGroup(ActionGroup g) {

	}

	public void disallowGroup(ActionGroup g) {

	}

	public void allowRight(ActionRight g) {

	}

	public void disallowRight(ActionRight g) {

	}

	public boolean canAccess() {
		return false;
	}

	public boolean canAccess(AppUser e) {
		return false;
	}

}
