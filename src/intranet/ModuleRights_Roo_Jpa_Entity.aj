// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.ModuleRights;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect ModuleRights_Roo_Jpa_Entity {
    
    declare @type: ModuleRights: @Entity;
    
    declare @type: ModuleRights: @Table(name = "module_rights");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idmodule_right")
    private Integer ModuleRights.idmoduleRight;
    
    public Integer ModuleRights.getIdmoduleRight() {
        return this.idmoduleRight;
    }
    
    public void ModuleRights.setIdmoduleRight(Integer id) {
        this.idmoduleRight = id;
    }
    
}
