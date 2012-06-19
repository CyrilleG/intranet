// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Filter;
import intranet.GroupFilters;
import intranet.UserFilters;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;

privileged aspect Filter_Roo_DbManaged {
    
    @OneToMany(mappedBy = "idfilter", cascade = CascadeType.ALL)
    private Set<GroupFilters> Filter.groupFilterss;
    
    @OneToMany(mappedBy = "idfilter", cascade = CascadeType.ALL)
    private Set<UserFilters> Filter.userFilterss;
    
    @Column(name = "name", length = 100)
    private String Filter.name;
    
    @Column(name = "description", length = 255)
    private String Filter.description;
    
    @Column(name = "class", length = 75)
    private String Filter.class1;
    
    public Set<GroupFilters> Filter.getGroupFilterss() {
        return groupFilterss;
    }
    
    public void Filter.setGroupFilterss(Set<GroupFilters> groupFilterss) {
        this.groupFilterss = groupFilterss;
    }
    
    public Set<UserFilters> Filter.getUserFilterss() {
        return userFilterss;
    }
    
    public void Filter.setUserFilterss(Set<UserFilters> userFilterss) {
        this.userFilterss = userFilterss;
    }
    
    public String Filter.getName() {
        return name;
    }
    
    public void Filter.setName(String name) {
        this.name = name;
    }
    
    public String Filter.getDescription() {
        return description;
    }
    
    public void Filter.setDescription(String description) {
        this.description = description;
    }
    
    public String Filter.getClass1() {
        return class1;
    }
    
    public void Filter.setClass1(String class1) {
        this.class1 = class1;
    }
    
}