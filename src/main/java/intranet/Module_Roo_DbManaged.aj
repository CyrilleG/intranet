// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Action;
import intranet.Module;
import intranet.ModuleAccess;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

privileged aspect Module_Roo_DbManaged {
    
    @OneToMany(mappedBy = "idmodule", cascade = CascadeType.ALL)
    private Set<Action> Module.actions;
    
    @OneToMany(mappedBy = "idmodule", cascade = CascadeType.ALL)
    private Set<ModuleAccess> Module.moduleAccesses;
    
    @Column(name = "name", length = 100)
    private String Module.name;
    
    @Column(name = "description")
    private String Module.description;
    
    @Column(name = "class", length = 100)
    private String Module.class1;
    
    public Set<Action> Module.getActions() {
        return actions;
    }
    
    public void Module.setActions(Set<Action> actions) {
        this.actions = actions;
    }
    
    public Set<ModuleAccess> Module.getModuleAccesses() {
        return moduleAccesses;
    }
    
    public void Module.setModuleAccesses(Set<ModuleAccess> moduleAccesses) {
        this.moduleAccesses = moduleAccesses;
    }
    
    public String Module.getName() {
        return name;
    }
    
    public void Module.setName(String name) {
        this.name = name;
    }
    
    public String Module.getDescription() {
        return description;
    }
    
    public void Module.setDescription(String description) {
        this.description = description;
    }
    
    public String Module.getClass1() {
        return class1;
    }
    
    public void Module.setClass1(String class1) {
        this.class1 = class1;
    }
    
}
