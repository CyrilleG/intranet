// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.GroupRights;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect GroupRights_Roo_Jpa_Entity {
    
    declare @type: GroupRights: @Entity;
    
    declare @type: GroupRights: @Table(name = "group_rights");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idgroup_rights")
    private Integer GroupRights.idgroupRights;
    
    public Integer GroupRights.getIdgroupRights() {
        return this.idgroupRights;
    }
    
    public void GroupRights.setIdgroupRights(Integer id) {
        this.idgroupRights = id;
    }
    
}
