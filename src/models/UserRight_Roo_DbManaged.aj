// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.AppRight;
import models.AppUser;
import models.UserRight;

privileged aspect UserRight_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "right", referencedColumnName = "right")
    private AppRight UserRight.right;
    
    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "user")
    private AppUser UserRight.user;
    
    public AppRight UserRight.getRight() {
        return right;
    }
    
    public void UserRight.setRight(AppRight right) {
        this.right = right;
    }
    
    public AppUser UserRight.getUser() {
        return user;
    }
    
    public void UserRight.setUser(AppUser user) {
        this.user = user;
    }
    
}