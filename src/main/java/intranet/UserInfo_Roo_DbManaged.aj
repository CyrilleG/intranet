// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Privacities;
import intranet.User;
import intranet.UserInfo;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

privileged aspect UserInfo_Roo_DbManaged {
    
    @OneToMany(mappedBy = "idinfo", cascade = CascadeType.ALL)
    private Set<Privacities> UserInfo.privacitieses;
    
    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser", nullable = false)
    private User UserInfo.iduser;
    
    @Column(name = "key", length = 100, unique = true)
    @NotNull
    private String UserInfo.key;
    
    @Column(name = "value", length = 255)
    private String UserInfo.value;
    
    public Set<Privacities> UserInfo.getPrivacitieses() {
        return privacitieses;
    }
    
    public void UserInfo.setPrivacitieses(Set<Privacities> privacitieses) {
        this.privacitieses = privacitieses;
    }
    
    public User UserInfo.getIduser() {
        return iduser;
    }
    
    public void UserInfo.setIduser(User iduser) {
        this.iduser = iduser;
    }
    
    public String UserInfo.getKey() {
        return key;
    }
    
    public void UserInfo.setKey(String key) {
        this.key = key;
    }
    
    public String UserInfo.getValue() {
        return value;
    }
    
    public void UserInfo.setValue(String value) {
        this.value = value;
    }
    
}
