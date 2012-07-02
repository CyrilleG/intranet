package intranet;

import java.util.List;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import exceptions.DataLengthException;
import exceptions.ElementNotFoundException;
import exceptions.AccessNotAllowedException;
import exceptions.DataFormatException;
import exceptions.NotEmptyException;
import exceptions.NotUniqueException;
import utils.Tools;
import utils.Utils;

import javax.persistence.Column;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_config")
@RooDbManaged(automaticallyDelete = true)
public class AppConfig {

	@Column(name = "key", columnDefinition = "VARCHAR", length = 100, unique = true)
	private String key;

	@Column(name = "value", columnDefinition = "TEXT")
	private String value;

	public static AppConfig findByKey(String key)
			throws ElementNotFoundException, DataFormatException,
			NotEmptyException {

		if (key == null || key.length() == 0)
			throw new NotEmptyException("key cannot be empty");

		if (!Utils.regexMatch(key, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"key parameter has to match with ([a-zA-Z0-9]+)");

		List<AppConfig> confs = AppConfig.findAllAppConfigs();
		for (AppConfig conf : confs)
			if (conf.getKey().compareToIgnoreCase(key) == 0)
				return conf;
		throw new ElementNotFoundException("No Config Object with ident " + key);
	}

	public static String getConfig(String key) throws ElementNotFoundException,
			DataFormatException, NotEmptyException {
		return findByKey(key).getValue();
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

	public static AppConfig create(String key, String value)
			throws AccessNotAllowedException, NotEmptyException,
			DataFormatException, DataLengthException, NotUniqueException {

		if (key == null || key.length() == 0)
			throw new NotEmptyException("key cannot be empty");

		if (!Utils.regexMatch(key, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"key parameter has to match with ([a-zA-Z0-9]+)");

		if (isKeyExist(key))
			throw new NotUniqueException("key has to be unique");
		
		if (Tools.hasRight("ADD_CONF")) {
			AppConfig conf = new AppConfig();
			conf.setKey(key);
			conf.setValue(value);
			conf.persist();
			return conf;
		}
		throw new AccessNotAllowedException(
				"You can't add a configuration entry");
	}

	public void setKey(String key) throws AccessNotAllowedException,
			NotEmptyException, DataFormatException, DataLengthException, NotUniqueException {
		if (key == null || key.length() == 0)
			throw new NotEmptyException("key cannot be empty");

		if (!Utils.regexMatch(key, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"key parameter has to match with ([a-zA-Z0-9]+)");

		if (isKeyExist(key))
			throw new NotUniqueException("key has to be unique");
		
		if (Tools.hasRight("SET_CONF_KEY"))
			this.key = key;
		else
			throw new AccessNotAllowedException(
					"You can't edit a configuration key as: " + key);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_CONF_VALUE"))
			this.value = value;
		else
			throw new AccessNotAllowedException(
					"You can't edit a configuration value as: " + value);
	}
}
