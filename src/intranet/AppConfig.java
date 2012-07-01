package intranet;

import java.util.List;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import exceptions.ElementNotFoundException;
import exceptions.AccessNotAllowedException;
import utils.Tools;
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

	public static AppConfig findByKey(String key) throws ElementNotFoundException {
		List<AppConfig> confs = AppConfig.findAllAppConfigs();
		for (AppConfig conf : confs)
			if (conf.getKey().compareToIgnoreCase(key) == 0)
				return conf;
		throw new ElementNotFoundException("No Config Object with ident " + key);
	}

	public static String getConfig(String key) throws ElementNotFoundException {
		return findByKey(key).getValue();
	}

	public String getKey() {
		return key;
	}

	public AppConfig create(String key, String value) throws AccessNotAllowedException{
		if (Tools.hasRight("ADD_CONF")) {
			AppConfig conf = new AppConfig();
			conf.setKey(key);
			conf.setValue(value);
			conf.persist();
			return conf;
		}
		throw new AccessNotAllowedException("You can't add a configuration entry");
	}

	public void setKey(String key) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_CONF_KEY"))
			this.key = key;
		else
			throw new AccessNotAllowedException("You can't edit a configuration key as: " + key);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) throws AccessNotAllowedException {
		if (Tools.hasRight("SET_CONF_VALUE"))
			this.value = value;
		else
			throw new AccessNotAllowedException("You can't edit a configuration value as: " + value);
	}
}
