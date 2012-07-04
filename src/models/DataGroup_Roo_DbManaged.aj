// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package models;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.AppGroup;
import models.DataGroup;
import models.ModuleData;

privileged aspect DataGroup_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "group", referencedColumnName = "group")
    private AppGroup DataGroup.group;
    
    @ManyToOne
    @JoinColumn(name = "data", referencedColumnName = "module_data", nullable = false)
    private ModuleData DataGroup.data;
    
    public AppGroup DataGroup.getGroup() {
        return group;
    }
    
    public void DataGroup.setGroup(AppGroup group) {
        this.group = group;
    }
    
    public ModuleData DataGroup.getData() {
        return data;
    }
    
    public void DataGroup.setData(ModuleData data) {
        this.data = data;
    }
    
}