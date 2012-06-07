// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.GroupRights;
import intranet.Right;
import intranet.UserRights;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

privileged aspect Right_Roo_DbManaged {
    
    @OneToMany(mappedBy = "idright", cascade = CascadeType.ALL)
    private Set<GroupRights> Right.groupRightss;
    
    @OneToMany(mappedBy = "idright", cascade = CascadeType.ALL)
    private Set<UserRights> Right.userRightss;
    
    @Column(name = "ident", length = 70, unique = true)
    @NotNull
    private String Right.ident;
    
    @Column(name = "name", length = 100)
    @NotNull
    private String Right.name;
    
    @Column(name = "description")
    private String Right.description;
    
    public Set<GroupRights> Right.getGroupRightss() {
        return groupRightss;
    }
    
    public void Right.setGroupRightss(Set<GroupRights> groupRightss) {
        this.groupRightss = groupRightss;
    }
    
    public Set<UserRights> Right.getUserRightss() {
        return userRightss;
    }
    
    public void Right.setUserRightss(Set<UserRights> userRightss) {
        this.userRightss = userRightss;
    }
    
    public String Right.getIdent() {
        return ident;
    }
    
    public void Right.setIdent(String ident) {
        this.ident = ident;
    }
    
    public String Right.getName() {
        return name;
    }
    
    public void Right.setName(String name) {
        this.name = name;
    }
    
    public String Right.getDescription() {
        return description;
    }
    
    public void Right.setDescription(String description) {
        this.description = description;
    }
    
}
