// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Group;
import intranet.GroupRights;
import intranet.Right;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

privileged aspect GroupRights_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "idgroup", referencedColumnName = "idgroup", nullable = false)
    private Group GroupRights.idgroup;
    
    @ManyToOne
    @JoinColumn(name = "idright", referencedColumnName = "idright", nullable = false)
    private Right GroupRights.idright;
    
    public Group GroupRights.getIdgroup() {
        return idgroup;
    }
    
    public void GroupRights.setIdgroup(Group idgroup) {
        this.idgroup = idgroup;
    }
    
    public Right GroupRights.getIdright() {
        return idright;
    }
    
    public void GroupRights.setIdright(Right idright) {
        this.idright = idright;
    }
    
}
