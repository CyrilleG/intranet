// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Right;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect Right_Roo_Jpa_Entity {
    
    declare @type: Right: @Entity;
    
    declare @type: Right: @Table(name = "right");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idright")
    private Integer Right.idright;
    
    public Integer Right.getIdright() {
        return this.idright;
    }
    
    public void Right.setIdright(Integer id) {
        this.idright = id;
    }
    
}
