// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Filter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect Filter_Roo_Jpa_Entity {
    
    declare @type: Filter: @Entity;
    
    declare @type: Filter: @Table(name = "filter");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idfilter")
    private Integer Filter.idfilter;
    
    public Integer Filter.getIdfilter() {
        return this.idfilter;
    }
    
    public void Filter.setIdfilter(Integer id) {
        this.idfilter = id;
    }
    
}
