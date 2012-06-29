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

import utils.Tools;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "user_info")
@RooDbManaged(automaticallyDelete = true)
public class UserInfo {
	@OneToMany(mappedBy = "info", cascade = CascadeType.ALL)
	private Set<InfoPrivacity> infoPrivacities;

	@Column(name = "key", columnDefinition = "VARCHAR", length = 100, unique = true)
	@NotNull
	private String key;

	@Column(name = "value", columnDefinition = "VARCHAR", length = 255)
	private String value;

	@Column(name = "show", columnDefinition = "BIT")
	@NotNull
	private boolean show;

	@Column(name = "editable", columnDefinition = "BIT")
	@NotNull
	private boolean editable;

	public UserInfo createInformation(AppUser user, String key, String value,
			boolean editable, boolean show, AppGroup privacity) {
		if (Tools.hasRight("ADD_INFO_TO_OTHER_USER") && privacity != null) {
			UserInfo element = new UserInfo();
			element.setKey(key);
			element.setValue(value);
			element.setShow(show);
			element.setInfoEditable(editable);
			element.persist();

			InfoPrivacity priv = new InfoPrivacity();
			priv.setInfo(element);
			priv.setGroup(privacity);
			priv.setUser(user);
			priv.persist();
			return element;
		}
		return null;
	}
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		if (Tools.hasRight("SET_INFO_KEY"))
			this.key = key;
	}

	public String getValue() {
		if (Tools.hasRight("GET_DATA_FROM_OTHER_USER") || canUserAccess(Tools.getUser()))
			return value;
		return null;
	}

	public void setValue(String value) {
		if (Tools.hasRight("SET_INFO_VALUE"))
			this.value = value;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;// TODO rights
	}

	public void setInfoEditable(boolean editable) {
		this.editable = editable;// TODO rights
	}

	public boolean isEditable() {
		return editable;// TODO rights
	}

	public void addGroupToPrivacities(AppGroup group, AppUser user) {
		if (Tools.hasRight("ADD_GROUP_TO_INFO")) {
			InfoPrivacity elem = new InfoPrivacity();
			elem.setInfo(this);
			elem.setUser(user);
			elem.setGroup(group);
			elem.persist();
			infoPrivacities.add(elem);
		}
	}

	public void removeGroupFromPrivacities(AppGroup group) {
		if (Tools.hasRight("REMOVE_GROUP_FROM_INFO")) 
	    	for (InfoPrivacity data : infoPrivacities)
	        	if (data.getGroup().equals(group))
	        		data.remove();
	}

	public boolean canGroupAccess(AppGroup group) {
		for (InfoPrivacity priv : infoPrivacities)
		{
			if (group.equals(priv.getGroup()))
				return true;
		}
		return false;
	}
	
	public boolean canUserAccess(AppUser user)
	{
		for (InfoPrivacity priv : infoPrivacities)
		{
			if (priv.getUser().equals(user))
				for (AppGroup group : user.getAllGroups())
				{
					if (group.equals(priv.getGroup()))
						return true;
				}
		}
		return false;
	}
}
