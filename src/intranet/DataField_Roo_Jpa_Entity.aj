// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.DataField;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect DataField_Roo_Jpa_Entity {
    
    declare @type: DataField: @Entity;
    
    declare @type: DataField: @Table(name = "data_field");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idfields")
    private Integer DataField.idfields;
    
    public Integer DataField.getIdfields() {
        return this.idfields;
    }
    
    public void DataField.setIdfields(Integer id) {
        this.idfields = id;
    }
    
}