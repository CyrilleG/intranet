// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.ModuleGroups;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect ModuleGroups_Roo_Jpa_Entity {
    
    declare @type: ModuleGroups: @Entity;
    
    declare @type: ModuleGroups: @Table(name = "module_groups");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idmodule_access")
    private Integer ModuleGroups.idmoduleAccess;
    
    public Integer ModuleGroups.getIdmoduleAccess() {
        return this.idmoduleAccess;
    }
    
    public void ModuleGroups.setIdmoduleAccess(Integer id) {
        this.idmoduleAccess = id;
    }
    
}