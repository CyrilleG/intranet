// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Group;
import intranet.User;
import intranet.UserGroups;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

privileged aspect UserGroups_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "idgroup", referencedColumnName = "idgroup", nullable = false)
    private Group UserGroups.idgroup;
    
    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser", nullable = false)
    private User UserGroups.iduser;
    
    public Group UserGroups.getIdgroup() {
        return idgroup;
    }
    
    public void UserGroups.setIdgroup(Group idgroup) {
        this.idgroup = idgroup;
    }
    
    public User UserGroups.getIduser() {
        return iduser;
    }
    
    public void UserGroups.setIduser(User iduser) {
        this.iduser = iduser;
    }
    
}
