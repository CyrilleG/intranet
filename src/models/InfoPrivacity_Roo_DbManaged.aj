// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.AppGroup;
import models.AppUser;
import models.InfoPrivacity;
import models.UserInfo;

privileged aspect InfoPrivacity_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "group", referencedColumnName = "group", nullable = false)
    private AppGroup InfoPrivacity.group;
    
    @ManyToOne
    @JoinColumn(name = "info", referencedColumnName = "info", nullable = false)
    private UserInfo InfoPrivacity.info;
    
    public AppGroup InfoPrivacity.getGroup() {
        return group;
    }
    
    public void InfoPrivacity.setGroup(AppGroup group) {
        this.group = group;
    }
    
    public UserInfo InfoPrivacity.getInfo() {
        return info;
    }
    
    public void InfoPrivacity.setInfo(UserInfo info) {
        this.info = info;
    }
    
    
    
}