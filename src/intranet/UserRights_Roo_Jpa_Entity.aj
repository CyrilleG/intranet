// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.UserRights;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect UserRights_Roo_Jpa_Entity {
    
    declare @type: UserRights: @Entity;
    
    declare @type: UserRights: @Table(name = "user_rights");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iduser_rights")
    private Integer UserRights.iduserRights;
    
    public Integer UserRights.getIduserRights() {
        return this.iduserRights;
    }
    
    public void UserRights.setIduserRights(Integer id) {
        this.iduserRights = id;
    }
    
}