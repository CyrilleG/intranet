// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Privacities;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect Privacities_Roo_Jpa_Entity {
    
    declare @type: Privacities: @Entity;
    
    declare @type: Privacities: @Table(name = "privacities");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idprivacity")
    private Integer Privacities.idprivacity;
    
    public Integer Privacities.getIdprivacity() {
        return this.idprivacity;
    }
    
    public void Privacities.setIdprivacity(Integer id) {
        this.idprivacity = id;
    }
    
}