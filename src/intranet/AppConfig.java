package intranet;

import java.util.List;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

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

	public static AppConfig findConfigByKey(String key) {
		List<AppConfig> confs = AppConfig.findAllAppConfigs();
		for (AppConfig conf : confs)
			if (conf.getKey().compareToIgnoreCase(key) == 0)
				return conf;
		return null;
	}

	public static String getConfig(String key) {
		return findConfigByKey(key).getValue();
	}

	public String getKey() {
		return key;
	}

	public AppConfig createConf(String key, String value) {
		if (Tools.hasRight("ADD_CONF")) {
			AppConfig conf = new AppConfig();
			conf.setKey(key);
			conf.setValue(value);
			conf.persist();
			return conf;
		}
		return null;
	}

	public void setKey(String key) {
		if (Tools.hasRight("SET_CONF_KEY"))
			this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		if (Tools.hasRight("SET_CONF_VALUE"))
			this.value = value;
	}
}
