// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.UserInfo;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect UserInfo_Roo_Jpa_Entity {
    
    declare @type: UserInfo: @Entity;
    
    declare @type: UserInfo: @Table(name = "user_info");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "info", columnDefinition = "INT")
    private Integer UserInfo.info;
    
    public Integer UserInfo.getInfo() {
        return this.info;
    }
    
    public void UserInfo.setInfo(Integer id) {
        this.info = id;
    }
    
}
