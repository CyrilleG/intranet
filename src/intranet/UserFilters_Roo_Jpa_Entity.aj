// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.UserFilters;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect UserFilters_Roo_Jpa_Entity {
    
    declare @type: UserFilters: @Entity;
    
    declare @type: UserFilters: @Table(name = "user_filters");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "iduser_filters")
    private Integer UserFilters.iduserFilters;
    
    public Integer UserFilters.getIduserFilters() {
        return this.iduserFilters;
    }
    
    public void UserFilters.setIduserFilters(Integer id) {
        this.iduserFilters = id;
    }
    
}