package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import utils.Tools;

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

	public static AppSession findByLogin(String login)
			throws NotEmptyException, DataLengthException,
			ElementNotFoundException {

		if (login == null || login.length() == 0)
			throw new NotEmptyException("login cannot be empty");

		if (login.length() > 100)
			throw new DataLengthException(
					"login parameter is too long (max: 100 carac)");

		List<AppSession> elements = AppSession.findAllAppSessions();
		for (AppSession element : elements)
			if (element.getLogin().compareToIgnoreCase(login) == 0)
				return element;
		throw new ElementNotFoundException("this login is not registred");
	}

	public String getLogin() {
		return login;
	}

	public static AppSession create(String login)
			throws AccessNotAllowedException, NotEmptyException,
			DataLengthException, NotUniqueException {
		AppSession session = new AppSession();
		session.setLogin(login);
		session.setLoginDate(new Date());
		session.setLastAction(new Date());
		session.persist();
		return session;
	}

	public void delete() throws AccessNotAllowedException {
		if (Tools.hasRight("REMOVE_SESSION"))
			this.remove();
		else
			throw new AccessNotAllowedException(
					"You can't delete a session entry");
	}

	public boolean isLoginExist(String login) throws NotEmptyException,
			DataLengthException {

		try {
			return findByLogin(login) != null;
		} catch (ElementNotFoundException e) {
			return false;
		}
	}

	public void setLogin(String login) throws AccessNotAllowedException,
			NotEmptyException, DataLengthException, NotUniqueException {

		if (login == null || login.length() == 0)
			throw new NotEmptyException("login cannot be empty");

		if (login.length() > 100)
			throw new DataLengthException(
					"login parameter is too long (max: 100 carac)");

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
			throw new AccessNotAllowedException(
					"Session loginDate can't be reset");
	}

	public Date getLastAction() {
		return lastAction;
	}

	public void setLastAction(Date lastAction) {
		this.lastAction = lastAction;
	}

	@PersistenceContext
	public transient EntityManager entityManager;

	public static final EntityManager entityManager() {
		EntityManager em = new AppSession().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static long countAppSessions() {
		return entityManager().createQuery("SELECT COUNT(o) FROM AppSession o",
				Long.class).getSingleResult();
	}

	public static List<AppSession> findAllAppSessions() {
		return entityManager().createQuery("SELECT o FROM AppSession o",
				AppSession.class).getResultList();
	}

	public static AppSession findAppSession(Integer session) {
		return entityManager().find(AppSession.class, session);
	}

	public static List<AppSession> findAppSessionEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM AppSession o", AppSession.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
	}

	@Transactional
	public void remove() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		if (this.entityManager.contains(this)) {
			this.entityManager.remove(this);
		} else {
			AppSession attached = this;
			this.entityManager.remove(attached);
		}
	}

	@Transactional
	public void flush() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.flush();
	}

	@Transactional
	public void clear() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.clear();
	}

	@Transactional
	public AppSession merge() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		AppSession merged = this.entityManager.merge(this);
		this.entityManager.flush();
		return merged;
	}
}
