// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Session;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect Session_Roo_Jpa_Entity {
    
    declare @type: Session: @Entity;
    
    declare @type: Session: @Table(name = "session");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idsession", columnDefinition = "VARCHAR", length = 100)
    private String Session.idsession;
    
    public String Session.getIdsession() {
        return this.idsession;
    }
    
    public void Session.setIdsession(String id) {
        this.idsession = id;
    }
    
}
