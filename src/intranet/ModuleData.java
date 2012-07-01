package intranet;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import exceptions.AccessNotAllowedException;
import exceptions.ElementNotFoundException;
import exceptions.FatalErrorException;

import utils.Tools;
import utils.Utils;

import java.io.IOException;
import java.util.List;
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

	
	public ModuleData create(AppModule module, String name, Object data) throws AccessNotAllowedException, FatalErrorException {
		if (Tools.hasRight("ADD_USER")) {
			ModuleData element = new ModuleData();
			element.setName(name);
			element.setData(data);
			element.setModule(module);
			element.persist();
			return element;
		} else
			throw new AccessNotAllowedException("You can't add a data entry");
	}
	
	public static ModuleAction findByNameAndModule(AppModule module,
			String name) throws ElementNotFoundException {
		List<ModuleAction> elements = ModuleAction.findAllModuleActions();
		for (ModuleAction element : elements)
			if (element.getMethod().compareToIgnoreCase(name) == 0
					&& element.getModule().equals(module))
				return element;
		throw new ElementNotFoundException("No Action Object found with name: "
				+ name + " and module: " + module.getName());
	}
	
	public void disallowAccess(AppUser user) throws Exception {
		if (Tools.hasRight("REMOVE_DATA_FROM_OTHER_USER")
				|| Tools.getUser().equals(user))
		{
			for (DataUser data : dataUsers)
				if (data.getUser().equals(user))
					data.remove();
		}
		else
			throw new AccessNotAllowedException("You can't remove data from user");
	}

	public void allowAccess(AppUser user) throws Exception {
		if (Tools.hasRight("ADD_DATA_TO_OTHER_USER")
				|| Tools.getUser().equals(user)) {
			DataUser elem = new DataUser();
			elem.setData(this);
			elem.setUser(user);
			elem.persist();
			dataUsers.add(elem);
		}
		else
			throw new AccessNotAllowedException("You can't add data to user");
	}

	public boolean isAccessAllow(AppUser user) {
		for (DataUser elem : dataUsers)
			if (elem.getData().equals(user))
				return true;
		return false;
	}

	public void allowAccess(AppRight data) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_RIGHT_TO_DATA")) {
			DataRight element = new DataRight();
			element.setRight(data);
			element.setData(this);
			element.persist();
			dataRights.add(element);
		}
		else
			throw new AccessNotAllowedException("You can't add right to data");
	}

	public void disallowAccess(AppRight data) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_RIGHT_FROM_DATA")) {
			for (DataRight gp : dataRights)
				if (gp.getRight().equals(data)) {
					gp.remove();
					break;
				}
		}else
			throw new AccessNotAllowedException("You can't remove right from data");
	}

	public boolean isAccessAllow(AppRight element) {
		for (DataRight item : dataRights)
			if (item.getRight().equals(element))
				return true;
		return false;
	}

	public void allowAccess(AppGroup ident) throws AccessNotAllowedException {
		if (Tools.hasRight("ADD_DATA_TO_GROUP")) {
			DataGroup element = new DataGroup();
			element.setGroup(ident);
			element.setData(this);
			element.persist();
			dataGroups.add(element);
		}
		else
			throw new AccessNotAllowedException("You can't add group to data");
	}

	public void disallowAccess(AppGroup ident) throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_DATA_FROM_GROUP")) {
			for (DataGroup gp : dataGroups)
				if (gp.getGroup().equals(ident)) {
					gp.remove();
					break;
				}
		}
		else
			throw new AccessNotAllowedException("You can't remove group from data");
	}

	public boolean isAccessAllow(AppGroup ident) {
		for (DataGroup g : dataGroups)
			if (g.getGroup().equals(ident))
				return true;
		return false;
	}

	public AppModule getModule() {
		return module;
	}

	public void setModule(AppModule module) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_DATA_MODULE"))
			this.module = module;
		else
			throw new AccessNotAllowedException(
					"You can't edit a data module as: " + module.getName());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_DATA_NAME"))
			this.name = name;
		else
			throw new AccessNotAllowedException(
					"You can't edit a data name as: " + name);
	}

	public Object getData() throws FatalErrorException, AccessNotAllowedException {
		if (Tools.hasRight("GET_DATA_FROM_OTHER_USER")
				|| this.isAccessAllow(Tools.getUser())) {
			try {
				return Utils.ByteArrayToObject(data);
			} catch (Exception e) {
				throw new FatalErrorException("Convert to byte array error");
			}
		}
		else
			throw new AccessNotAllowedException(
					"You can't access to this data");
	}

	public void setData(Object data) throws FatalErrorException {
		if (Tools.hasRight("EDIT_DATA_FROM_OTHER_USER")
				|| this.isAccessAllow(Tools.getUser())) {

			try {
				this.data = Utils.ObjectToByteArray(data);
			} catch (IOException e) {
				throw new FatalErrorException("Convert to object error");
			}
		}
	}

}
