// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.ModuleGroup;

privileged aspect ModuleGroup_Roo_Jpa_Entity {
    
    declare @type: ModuleGroup: @Entity;
    
    declare @type: ModuleGroup: @Table(name = "module_group");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "module_access", columnDefinition = "INT")
    private Integer ModuleGroup.moduleAccess;
    
    public Integer ModuleGroup.getModuleAccess() {
        return this.moduleAccess;
    }
    
    public void ModuleGroup.setModuleAccess(Integer id) {
        this.moduleAccess = id;
    }
    
}