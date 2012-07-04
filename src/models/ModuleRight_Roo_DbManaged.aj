// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.AppModule;
import models.AppRight;
import models.ModuleRight;

privileged aspect ModuleRight_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "right", referencedColumnName = "right", nullable = false)
    private AppRight ModuleRight.right;
    
    @ManyToOne
    @JoinColumn(name = "module", referencedColumnName = "module", nullable = false)
    private AppModule ModuleRight.module;
    
    public AppRight ModuleRight.getRight() {
        return right;
    }
    
    public void ModuleRight.setRight(AppRight right) {
        this.right = right;
    }
    
    public AppModule ModuleRight.getModule() {
        return module;
    }
    
    public void ModuleRight.setModule(AppModule module) {
        this.module = module;
    }
    
}