package intranet;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_session")
@RooDbManaged(automaticallyDelete = true)
public class AppSession {
	
	@Column(name = "login", columnDefinition = "VARCHAR", length = 70, unique = true)
    private String login;
    
    @Column(name = "login_date", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date loginDate;
    
    @Column(name = "last_action", columnDefinition = "DATE")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date lastAction;
    
    
    public static AppSession findSessionByLogin(String login) {
		List<AppSession> elements = AppSession.findAllAppSessions();
		for (AppSession element : elements)
			if (element.getLogin().compareToIgnoreCase(login) == 0)
				return element;
		return null;
	}
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
    	if (this.login == null) 
    		this.login = login;
    }
    
    public Date getLoginDate() {
    	
        return loginDate;
    }
    
    public void setLoginDate(Date loginDate) {
    	if (this.loginDate == null) 
    	this.loginDate = loginDate;
    }
    
    public Date getLastAction() {
        return lastAction;
    }
    
    public void setLastAction(Date lastAction) {
        this.lastAction = lastAction;
    }
}
