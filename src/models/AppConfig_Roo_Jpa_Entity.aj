// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import models.AppConfig;

privileged aspect AppConfig_Roo_Jpa_Entity {
    
    declare @type: AppConfig: @Entity;
    
    declare @type: AppConfig: @Table(name = "app_config");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "app_config", columnDefinition = "INT")
    private Integer AppConfig.appConfig;
    
    public Integer AppConfig.getAppConfig() {
        return this.appConfig;
    }
    
    public void AppConfig.setAppConfig(Integer id) {
        this.appConfig = id;
    }
    
}
