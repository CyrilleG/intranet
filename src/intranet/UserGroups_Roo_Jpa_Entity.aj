// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.UserGroups;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect UserGroups_Roo_Jpa_Entity {
    
    declare @type: UserGroups: @Entity;
    
    declare @type: UserGroups: @Table(name = "user_groups");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iduser_groups")
    private Integer UserGroups.iduserGroups;
    
    public Integer UserGroups.getIduserGroups() {
        return this.iduserGroups;
    }
    
    public void UserGroups.setIduserGroups(Integer id) {
        this.iduserGroups = id;
    }
    
}