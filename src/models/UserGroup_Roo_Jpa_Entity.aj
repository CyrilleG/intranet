// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.UserGroup;

privileged aspect UserGroup_Roo_Jpa_Entity {
    
    declare @type: UserGroup: @Entity;
    
    declare @type: UserGroup: @Table(name = "user_group");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_groups", columnDefinition = "INT")
    private Integer UserGroup.userGroups;
    
    public Integer UserGroup.getUserGroups() {
        return this.userGroups;
    }
    
    public void UserGroup.setUserGroups(Integer id) {
        this.userGroups = id;
    }
    
}