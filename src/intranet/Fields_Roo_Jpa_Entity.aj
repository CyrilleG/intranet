// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Fields;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect Fields_Roo_Jpa_Entity {
    
    declare @type: Fields: @Entity;
    
    declare @type: Fields: @Table(name = "fields");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idfields")
    private Integer Fields.idfields;
    
    public Integer Fields.getIdfields() {
        return this.idfields;
    }
    
    public void Fields.setIdfields(Integer id) {
        this.idfields = id;
    }
    
}