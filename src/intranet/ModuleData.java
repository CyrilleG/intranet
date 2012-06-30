package intranet;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import utils.Tools;
import utils.Utils;

import java.io.IOException;
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

	@OneToMany(mappedBy = "data", cascade = CascadeType.ALL)
	private Set<DataGroup> dataGroups;

	@OneToMany(mappedBy = "data", cascade = CascadeType.ALL)
	private Set<DataRight> dataRights;

	@OneToMany(mappedBy = "data", cascade = CascadeType.ALL)
	private Set<DataUser> dataUsers;

	@ManyToOne
	@JoinColumn(name = "module", referencedColumnName = "module", nullable = false)
	private AppModule module;

	@Column(name = "name", columnDefinition = "VARCHAR", length = 100, unique = true)
	@NotNull
	private String name;

	@Column(name = "data", columnDefinition = "BLOB")
	private byte[] data;

	public void removeDataFromUser(AppUser user) throws Exception {
		if (Tools.hasRight("REMOVE_DATA_FROM_OTHER_USER")
				|| Tools.getUser().equals(user))
			for (DataUser data : dataUsers)
				if (data.getUser().equals(user))
					data.remove();
	}

	public void addDataToUser(AppUser user) throws Exception {
		if (Tools.hasRight("ADD_DATA_TO_OTHER_USER")
				|| Tools.getUser().equals(user)) {
			DataUser elem = new DataUser();
			elem.setData(this);
			elem.setUser(user);
			elem.persist();
			dataUsers.add(elem);
		}
	}

	public boolean userHasData(AppUser user) {
		for (DataUser elem : dataUsers)
			if (elem.getData().equals(user))
				return true;
		return false;
	}

	public void addRightToData(AppRight data) {
		if (Tools.hasRight("ADD_RIGHT_TO_DATA")) {
			DataRight element = new DataRight();
			element.setRight(data);
			element.setData(this);
			element.persist();
			dataRights.add(element);
		}
	}

	public void removeRightFromData(AppRight data) {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_DATA")) {
			for (DataRight gp : dataRights)
				if (gp.getRight().equals(data)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean dataHasRight(AppRight element) {
		for (DataRight item : dataRights)
			if (item.getRight().equals(element))
				return true;
		return false;
	}

	public void addGroupToData(AppGroup ident) {
		if (Tools.hasRight("ADD_DATA_TO_GROUP")) {
			DataGroup element = new DataGroup();
			element.setGroup(ident);
			element.setData(this);
			element.persist();
			dataGroups.add(element);
		}
	}

	public void removeGroupFromData(AppGroup ident) {
		if (Tools.hasRight("REMOVE_DATA_FROM_GROUP")) {
			for (DataGroup gp : dataGroups)
				if (gp.getGroup().equals(ident)) {
					gp.remove();
					break;
				}
		}
	}

	public boolean dataHasGroup(AppGroup ident) {
		for (DataGroup g : dataGroups)
			if (g.getGroup().equals(ident))
				return true;
		return false;
	}

	public AppModule getModule() {
		return module;
	}

	public void setModule(AppModule module) {
		if (Tools.hasRight("SET_DATA_MODULE"))
			this.module = module;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (Tools.hasRight("SET_DATA_NAME"))
			this.name = name;
	}

	public Object getData() {
		if (Tools.hasRight("GET_DATA_FROM_OTHER_USER")
				|| this.userHasData(Tools.getUser())) {
			try {
				return Utils.ByteArrayToObject(data);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public void setData(Object data) {
		if (Tools.hasRight("EDIT_DATA_FROM_OTHER_USER")
				|| this.userHasData(Tools.getUser())) {

			try {
				this.data = Utils.ObjectToByteArray(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
