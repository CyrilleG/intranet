// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.AppUser;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect AppUser_Roo_Jpa_Entity {
    
    declare @type: AppUser: @Entity;
    
    declare @type: AppUser: @Table(name = "app_user");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iduser")
    private Integer AppUser.iduser;
    
    public Integer AppUser.getIduser() {
        return this.iduser;
    }
    
    public void AppUser.setIduser(Integer id) {
        this.iduser = id;
    }
    
}