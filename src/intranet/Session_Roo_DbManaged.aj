// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Session;
import intranet.User;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

privileged aspect Session_Roo_DbManaged {
    
    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    private User Session.iduser;
    
    @Column(name = "login_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date Session.loginDate;
    
    @Column(name = "last_action")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date Session.lastAction;
    
    public User Session.getIduser() {
        return iduser;
    }
    
    public void Session.setIduser(User iduser) {
        this.iduser = iduser;
    }
    
    public Date Session.getLoginDate() {
        return loginDate;
    }
    
    public void Session.setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
    
    public Date Session.getLastAction() {
        return lastAction;
    }
    
    public void Session.setLastAction(Date lastAction) {
        this.lastAction = lastAction;
    }
    
}