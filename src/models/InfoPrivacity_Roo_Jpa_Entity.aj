// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.InfoPrivacity;

privileged aspect InfoPrivacity_Roo_Jpa_Entity {
    
    declare @type: InfoPrivacity: @Entity;
    
    declare @type: InfoPrivacity: @Table(name = "info_privacity");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "privacity", columnDefinition = "INT")
    private Integer InfoPrivacity.privacity;
    
    public Integer InfoPrivacity.getPrivacity() {
        return this.privacity;
    }
    
    public void InfoPrivacity.setPrivacity(Integer id) {
        this.privacity = id;
    }
    
}