// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.DataUser;

privileged aspect DataUser_Roo_Jpa_Entity {
    
    declare @type: DataUser: @Entity;
    
    declare @type: DataUser: @Table(name = "data_user");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "data_user", columnDefinition = "INT")
    private Integer DataUser.dataUser;
    
    public Integer DataUser.getDataUser() {
        return this.dataUser;
    }
    
    public void DataUser.setDataUser(Integer id) {
        this.dataUser = id;
    }
    
}
