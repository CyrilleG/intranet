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

import exceptions.AccessNotAllowedException;
import exceptions.DataLengthException;
import exceptions.ElementNotFoundException;
import exceptions.NotEmptyException;
import exceptions.NotUniqueException;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_session")
@RooDbManaged(automaticallyDelete = true)
public class AppSession {

	@Column(name = "login", columnDefinition = "VARCHAR", length = 100, unique = true)
	private String login;

	@Column(name = "login_date", columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date loginDate;

	@Column(name = "last_action", columnDefinition = "DATE")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(style = "M-")
	private Date lastAction;

	public static AppSession findByLogin(String login) throws NotEmptyException, DataLengthException {
		
		if (login == null || login.length() == 0)
			throw new NotEmptyException("login cannot be empty");
		
		if (login.length() > 100)
			throw new DataLengthException("login parameter is too long (max: 100 carac)");
		
		List<AppSession> elements = AppSession.findAllAppSessions();
		for (AppSession element : elements)
			if (element.getLogin().compareToIgnoreCase(login) == 0)
				return element;
		return null;
	}

	public String getLogin() {
		return login;
	}

	public boolean isLoginExist(String login) throws DataLengthException, NotEmptyException
	{
		return findByLogin(login) != null;
	}
	
	public void setLogin(String login) throws AccessNotAllowedException, NotEmptyException, DataLengthException, ElementNotFoundException, NotUniqueException {
		
		if (login == null || login.length() == 0)
			throw new NotEmptyException("login cannot be empty");
		
		if (login.length() > 100)
			throw new DataLengthException("login parameter is too long (max: 100 carac)");
		
		if (isLoginExist(login))
			throw new NotUniqueException("login has to be unique");
		
		if (this.login == null)
			this.login = login;
		else
			throw new AccessNotAllowedException("Session login can't be reset");
	}

	public Date getLoginDate() {

		return loginDate;
	}

	public void setLoginDate(Date loginDate) throws AccessNotAllowedException {
		if (this.loginDate == null)
			this.loginDate = loginDate;
		else
			throw new AccessNotAllowedException("Session loginDate can't be reset");
	}

	public Date getLastAction() {
		return lastAction;
	}

	public void setLastAction(Date lastAction) {
		this.lastAction = lastAction;
	}
}
