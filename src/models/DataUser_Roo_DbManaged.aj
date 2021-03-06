// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.AppUser;
import models.DataUser;
import models.ModuleData;

privileged aspect DataUser_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "user", nullable = false)
    private AppUser DataUser.user;
    
    @ManyToOne
    @JoinColumn(name = "data", referencedColumnName = "module_data", nullable = false)
    private ModuleData DataUser.data;
    
    public AppUser DataUser.getUser() {
        return user;
    }
    
    public void DataUser.setUser(AppUser user) {
        this.user = user;
    }
    
    public ModuleData DataUser.getData() {
        return data;
    }
    
    public void DataUser.setData(ModuleData data) {
        this.data = data;
    }
    
}
