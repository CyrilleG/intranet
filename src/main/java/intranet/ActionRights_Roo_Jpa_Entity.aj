// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.ActionRights;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect ActionRights_Roo_Jpa_Entity {
    
    declare @type: ActionRights: @Entity;
    
    declare @type: ActionRights: @Table(name = "action_rights");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idaction_rights", columnDefinition = "INT")
    private Integer ActionRights.idactionRights;
    
    public Integer ActionRights.getIdactionRights() {
        return this.idactionRights;
    }
    
    public void ActionRights.setIdactionRights(Integer id) {
        this.idactionRights = id;
    }
    
}
