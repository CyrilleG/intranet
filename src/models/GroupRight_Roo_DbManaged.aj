// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.AppGroup;
import models.AppRight;
import models.GroupRight;

privileged aspect GroupRight_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "group", referencedColumnName = "group", nullable = false)
    private AppGroup GroupRight.group;
    
    @ManyToOne
    @JoinColumn(name = "right", referencedColumnName = "right", nullable = false)
    private AppRight GroupRight.right;
    
    public AppGroup GroupRight.getGroup() {
        return group;
    }
    
    public void GroupRight.setGroup(AppGroup group) {
        this.group = group;
    }
    
    public AppRight GroupRight.getRight() {
        return right;
    }
    
    public void GroupRight.setRight(AppRight right) {
        this.right = right;
    }
    
}
