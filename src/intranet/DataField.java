package intranet;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import utils.Tools;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "data_field")
@RooDbManaged(automaticallyDelete = true)
public class DataField {

	@ManyToOne
	@JoinColumn(name = "module_data", referencedColumnName = "module_data", nullable = false)
	private ModuleData moduleData;

	@Column(name = "name", columnDefinition = "VARCHAR", length = 100)
	@NotNull
	private String name;

	@Column(name = "value", columnDefinition = "VARCHAR", length = 255)
	private String value;

	@Column(name = "type", columnDefinition = "VARCHAR", length = 45)
	@NotNull
	private String type;

	public static DataField findFieldByNameAndModule(ModuleData data, String name) {
		List<DataField> elements = DataField.findAllDataFields();
		for (DataField element : elements)
			if (element.getName().compareToIgnoreCase(name) == 0 && element.getData().equals(data))
				return element;
		return null;
	}
	
	public DataField createField(ModuleData module, String name, String value,
			Class<?> type) {
		DataField data = new DataField();
		data.setName(name);
		data.setValue(value);
		data.setType(type);
		data.setData(module);

		return data;
	}

	public void addFieldToData(ModuleData ident) {
		if (Tools.hasRight("ADD_FIELD_TO_DATA")) {
			moduleData = ident;
		}
	}

	public void removeDataFromModule(ModuleData ident) {
		if (Tools.hasRight("REMOVE_FIELD_FROM_DATA")) {
			moduleData = null;
		}
	}

	public boolean fieldHasData(ModuleData ident) {
		return moduleData != null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (Tools.hasRight("SET_FIELD_NAME"))
			this.name = name;
	}

	public Object getValue() {
		try {
			Class<?> typed = Class.forName(this.type);
			return typed.cast(value);
		} catch (ClassNotFoundException e) {
			return value;
		}

	}

	public void setValue(Object value) {
		if (Tools.hasRight("SET_FIELD_VALUE")) {

			try {
				Class<?> typed = Class.forName(this.type);
				if (value.getClass().equals(typed))
					this.value = value.toString();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String getType() {
		return type;
	}

	public void setType(Class<?> type) {
		if (Tools.hasRight("SET_FIELD_TYPE"))
			this.type = type.getName();
	}

	public ModuleData getData() {
		return moduleData;
	}

	public void setData(ModuleData data) {
		if (Tools.hasRight("SET_FIELD_DATA"))
			this.moduleData = data;
	}
}
