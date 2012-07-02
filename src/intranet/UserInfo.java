package intranet;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import exceptions.AccessNotAllowedException;
import exceptions.DataFormatException;
import exceptions.DataLengthException;
import exceptions.ElementNotFoundException;
import exceptions.NotEmptyException;
import exceptions.NotUniqueException;

import utils.Tools;
import utils.Utils;

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

	@Column(name = "value", columnDefinition = "TEXT")
	private String value;

	@Column(name = "show", columnDefinition = "BIT")
	@NotNull
	private boolean show;

	@Column(name = "editable", columnDefinition = "BIT")
	@NotNull
	private boolean editable;

	public static UserInfo findByKey(String key) throws ElementNotFoundException, NotEmptyException, DataFormatException, DataLengthException {
		
		if (key == null || key.length() == 0)
			throw new NotEmptyException("key cannot be empty");

		if (key.length() > 100)
			throw new DataLengthException("key parameter is too long (max: 100 carac)");
		
		if (!Utils.regexMatch(key, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"key parameter has to match with ([a-zA-Z0-9]+)");
		
		List<UserInfo> elements = UserInfo.findAllUserInfoes();
		for (UserInfo element : elements)
			if (element.getKey().compareToIgnoreCase(key) == 0
					&& element.isAccessAllow(Tools.getUser()))
				return element;
		throw new ElementNotFoundException("No Information Object with key "
				+ key);
	}

	public static UserInfo create(AppUser user, String key, String value,
			boolean editable, boolean show, AppGroup privacity) throws AccessNotAllowedException, NotEmptyException, DataFormatException, DataLengthException, NotUniqueException, ElementNotFoundException {
		
		
		if (user == null)
			throw new NotEmptyException("user cannot be empty");
		
		if (key == null || key.length() == 0)
			throw new NotEmptyException("key cannot be empty");

		if (!Utils.regexMatch(key, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"key parameter has to match with ([a-zA-Z0-9]+)");
		
		if (key.length() > 100)
			throw new DataLengthException("key parameter is too long (max: 100 carac)");
		
		if (isKeyExist(key))
			throw new NotUniqueException("key has to be unique");
		
		if ((Tools.hasRight("ADD_INFO_TO_OTHER_USER") || user.equals(Tools
				.getUser())) && privacity != null) {
			UserInfo element = new UserInfo();
			element.setKey(key);
			element.setValue(value);
			element.setShow(show);
			element.setEditable(editable);
			element.persist();

			InfoPrivacity priv = new InfoPrivacity();
			priv.setInfo(element);
			priv.setGroup(privacity);
			priv.setUser(user);
			priv.persist();
			return element;
		}
		else
			throw new AccessNotAllowedException("You can't add a information entry");
	}

	
	public static boolean isKeyExist(String key) throws NotEmptyException, DataFormatException, DataLengthException
	{
		try {
			return findByKey(key) != null;
		} catch (ElementNotFoundException e) {
			return false;
		}
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) throws AccessNotAllowedException, NotEmptyException, DataFormatException, DataLengthException, ElementNotFoundException, NotUniqueException {
		
		if (key == null || key.length() == 0)
			throw new NotEmptyException("key cannot be empty");

		if (!Utils.regexMatch(key, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"key parameter has to match with ([a-zA-Z0-9]+)");
		
		if (key.length() > 100)
			throw new DataLengthException("key parameter is too long (max: 100 carac)");
		
		if (isKeyExist(key))
			throw new NotUniqueException("key has to be unique");
		
		if (Tools.hasRight("SET_INFO_KEY"))
			this.key = key;
		else
			throw new AccessNotAllowedException(
					"You can't set a information key as: " + key);
	}

	public String getValue() throws AccessNotAllowedException, NotEmptyException {
		if (isAccessAllow(Tools.getUser()))
			return value;
		else
			throw new AccessNotAllowedException(
					"You can't access to this information");
	}

	public void setValue(String value) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_INFO_VALUE")
				&& (editable || Tools.hasRight("EDIT_UNEDITABLE_DATA") || Tools
						.hasRight("SET_INFO_FROM_OTHER_USER")))
			this.value = value;
		else
			throw new AccessNotAllowedException(
					"You can't set a information value as: " + value);
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_HIS_INFO_AS_HIDDEN")
				|| Tools.hasRight("SET_INFO_FROM_OTHER_USER_AS_HIDDEN"))
			this.show = show;
		else
			throw new AccessNotAllowedException(
					"You can't set a information visibility as: " + show);
	}

	public void setEditable(boolean editable) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_INFO_FROM_OTHER_USER_AS_UNEDITABLE"))
			this.editable = editable;
		else
			throw new AccessNotAllowedException(
					"You can't set a information editability as: " + editable);
	}

	public boolean isEditable() {
		return editable;
	}

	public void allowToAccess(AppGroup group, AppUser user) throws AccessNotAllowedException, NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		if (user == null)
			throw new NotEmptyException("user cannot be empty");
		
		
		if (Tools.hasRight("ADD_GROUP_TO_INFO")) {
			InfoPrivacity elem = new InfoPrivacity();
			elem.setInfo(this);
			elem.setUser(user);
			elem.setGroup(group);
			elem.persist();
			infoPrivacities.add(elem);
		}
		else
			throw new AccessNotAllowedException(
					"You can't allow group to see this information");
	}

	public void disallowAccess(AppGroup group) throws AccessNotAllowedException, NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		if (Tools.hasRight("REMOVE_GROUP_FROM_INFO"))
		{
			for (InfoPrivacity data : infoPrivacities)
				if (data.getGroup().equals(group))
					data.remove();
		}
		else
			throw new AccessNotAllowedException(
					"You can't disallow group to see this information");
	}

	public boolean isAccessAllow(AppGroup group) throws NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		for (InfoPrivacity priv : infoPrivacities) {
			if (group.equals(priv.getGroup()))
				return true;
		}
		return false;
	}

	public boolean isAccessAllow(AppUser user) throws NotEmptyException {
		
		if (user == null)
			throw new NotEmptyException("user cannot be empty");
		
		for (InfoPrivacity priv : infoPrivacities) {
			if (priv.getInfo().show || Tools.hasRight("VIEW_HIDDEN_INFO")) {
				if (priv.getUser().equals(user)
						|| Tools.hasRight("GET_INFO_FROM_OTHER_USER"))
					for (AppGroup group : user.getHisGroup()) {
						if (group.equals(priv.getGroup())
								|| Tools.hasRight("GET_INFO_FROM_OTHER_USER"))
							return true;
					}
			}
		}
		return false;
	}
}
