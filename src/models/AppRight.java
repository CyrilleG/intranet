package models;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import org.springframework.roo.addon.dbre.RooDbManaged;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

import exceptions.AccessNotAllowedException;
import exceptions.DataFormatException;
import exceptions.DataLengthException;
import exceptions.ElementNotFoundException;
import exceptions.NotEmptyException;
import exceptions.NotUniqueException;

import utils.Tools;
import utils.Utils;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(versionField = "", table = "app_right")
@RooDbManaged(automaticallyDelete = true)
public class AppRight implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getAuthority() {
		return ident;
	}

	@OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
	private Set<ActionRight> actionRights;

	@OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
	private Set<DataRight> dataRights;

	@OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
	private Set<GroupRight> groupRights;

	@OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
	private Set<ModuleRight> moduleRights;

	@OneToMany(mappedBy = "right", cascade = CascadeType.ALL)
	private Set<UserRight> userRights;

	@Column(name = "ident", columnDefinition = "VARCHAR", length = 70, unique = true)
	@NotNull
	private String ident;

	@Column(name = "name", columnDefinition = "VARCHAR", length = 100)
	@NotNull
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	public static AppRight findByName(String name)
			throws ElementNotFoundException, NotEmptyException, DataLengthException {
		
		if (name == null || name.length() == 0)
			throw new NotEmptyException("name cannot be empty");
		
		if (name.length() > 100)
			throw new DataLengthException("name parameter is too long (max: 100 carac)");
		
	
		List<AppRight> elements = AppRight.findAllAppRights();
		for (AppRight element : elements)
			if (element.getName().compareToIgnoreCase(name) == 0)
				return element;
		throw new ElementNotFoundException("No Right Object found with name: "
				+ name);
	}

	public static AppRight findByIdent(String ident)
			throws ElementNotFoundException, NotEmptyException, DataFormatException, DataLengthException {
		
		
		if (ident == null || ident.length() == 0)
			throw new NotEmptyException("ident cannot be empty");

		if (!Utils.regexMatch(ident, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"ident parameter has to match with ([a-zA-Z0-9]+)");
		
		if (ident.length() > 70)
			throw new DataLengthException("ident parameter is too long (max: 70 carac)");
		
		List<AppRight> elements = AppRight.findAllAppRights();
		for (AppRight element : elements)
			if (element.getIdent().compareToIgnoreCase(ident) == 0)
				return element;
		throw new ElementNotFoundException("No Right Object found with ident: "
				+ ident);
	}

	public static AppRight create(String name, String ident,
			String description) throws AccessNotAllowedException, NotEmptyException, DataFormatException, DataLengthException, NotUniqueException {
		
		if (name == null || name.length() == 0)
			throw new NotEmptyException("name cannot be empty");
		
		if (ident == null || ident.length() == 0)
			throw new NotEmptyException("key cannot be empty");

		if (!Utils.regexMatch(ident, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"key parameter has to match with ([a-zA-Z0-9]+)");
		
		if (ident.length() > 70)
			throw new DataLengthException("ident parameter is too long (max: 70 carac)");
		
		if (name.length() > 100)
			throw new DataLengthException("name parameter is too long (max: 100 carac)");
		
		if (isIdentExist(ident))
			throw new NotUniqueException("ident has to be unique");
		
		if (Tools.hasRight("ADD_RIGHT")) {
			AppRight right = new AppRight();
			right.setName(name);
			right.setDescription(description);
			right.setIdent(ident);
			right.persist();
			return right;
		} else
			throw new AccessNotAllowedException("You can't add a Right entry");
	}

	public static boolean isIdentExist(String ident) throws NotEmptyException, DataFormatException, DataLengthException
	{
		try {
			return findByIdent(ident) != null;
		} catch (ElementNotFoundException e) {
			return false;
		}
	}
	public void allowAccess(ModuleAction action)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (action == null)
			throw new NotEmptyException("action cannot be empty");
		
		if (Tools.hasRight("ADD_RIGHT_TO_ACTION")) {
			ActionRight element = new ActionRight();
			element.setRight(this);
			element.setAction(action);
			element.persist();
			actionRights.add(element);
		} else
			throw new AccessNotAllowedException("You can't add right to action");
	}

	public void delete() throws AccessNotAllowedException
	{
		if (Tools.hasRight("REMOVE_RIGHT"))
			this.remove();
		else
			throw new AccessNotAllowedException(
					"You can't delete a right entry");
	}
	
	public void update() throws AccessNotAllowedException
	{
		if (Tools.hasRight("UPDATE_RIGHT"))
			this.merge();
		else
			throw new AccessNotAllowedException(
					"You can't delete a right entry");
	}
	
	public void disallowAccess(ModuleAction action)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (action == null)
			throw new NotEmptyException("action cannot be empty");
		
		if (Tools.hasRight("REMOVE_RIGHT_FROM_ACTION")) {
			for (ActionRight gp : actionRights)
				if (gp.getAction().equals(ident)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from action");
	}

	public boolean isAccessAllow(ModuleAction element) throws NotEmptyException {
		
		if (element == null)
			throw new NotEmptyException("element cannot be empty");
		
		for (ActionRight item : actionRights)
			if (item.getAction().equals(element))
				return true;
		return false;
	}

	public void allowAccess(AppGroup group)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		if (Tools.hasRight("ADD_RIGHT_TO_GROUP")) {
			GroupRight groupright = new GroupRight();
			groupright.setGroup(group);
			groupright.setRight(this);
			groupright.persist();
			groupRights.add(groupright);
		} else
			throw new AccessNotAllowedException("You can't add right to group");
	}

	public void disallowAccess(AppGroup group)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		if (Tools.hasRight("REMOVE_RIGHT_FROM_GROUP")) {
			for (GroupRight gp : groupRights)
				if (gp.getGroup().equals(group)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from group");

	}

	public boolean isAccessAllow(AppGroup group) throws NotEmptyException {
		
		if (group == null)
			throw new NotEmptyException("group cannot be empty");
		
		for (GroupRight item : groupRights)
			if (item.getGroup().equals(group))
				return true;
		return false;
	}

	public void allowAccess(AppModule module)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (module == null)
			throw new NotEmptyException("module cannot be empty");
		
		if (Tools.hasRight("ADD_RIGHT_TO_MODULE")) {
			ModuleRight element = new ModuleRight();
			element.setRight(this);
			element.setModule(module);
			element.persist();
			moduleRights.add(element);
		} else
			throw new AccessNotAllowedException("You can't add right to module");
	}

	public void disallowAccess(AppModule module)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (module == null)
			throw new NotEmptyException("module cannot be empty");
		
		if (Tools.hasRight("REMOVE_RIGHT_FROM_MODULE")) {
			for (ModuleRight gp : moduleRights)
				if (gp.getModule().equals(module)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from module");
	}

	public boolean isAccessAllow(AppModule element) throws NotEmptyException {
		
		if (element == null)
			throw new NotEmptyException("element cannot be empty");
		
		for (ModuleRight item : moduleRights)
			if (item.getModule().equals(element))
				return true;
		return false;
	}

	public void addToUser(AppUser user) throws AccessNotAllowedException, NotEmptyException {
		
		if (user == null)
			throw new NotEmptyException("user cannot be empty");
		
		if (Tools.hasRight("ADD_RIGHT_TO_USER")) {
			UserRight element = new UserRight();
			element.setRight(this);
			element.setUser(user);
			element.persist();
			userRights.add(element);
		} else
			throw new AccessNotAllowedException("You can't add right to user");
	}

	public void disallowAccess(AppUser user)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (user == null)
			throw new NotEmptyException("user cannot be empty");
		
		if (Tools.hasRight("REMOVE_RIGHT_FROM_USER")) {
			for (UserRight gp : userRights)
				if (gp.getUser().equals(user)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from user");
	}

	public boolean userHasRight(AppUser user) throws NotEmptyException {
		
		if (user == null)
			throw new NotEmptyException("user cannot be empty");
		
		for (UserRight item : userRights)
			if (item.getUser().equals(user))
				return true;
		return false;
	}

	public void allowAccess(ModuleData data)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (data == null)
			throw new NotEmptyException("data cannot be empty");
		
		if (Tools.hasRight("ADD_RIGHT_TO_DATA")) {
			DataRight element = new DataRight();
			element.setRight(this);
			element.setData(data);
			element.persist();
			dataRights.add(element);
		} else
			throw new AccessNotAllowedException("You can't add right to data");
	}

	public void disallowAccess(ModuleData data)
			throws AccessNotAllowedException, NotEmptyException {
		
		if (data == null)
			throw new NotEmptyException("data cannot be empty");
		
		if (Tools.hasRight("REMOVE_RIGHT_FROM_DATA")) {
			for (DataRight gp : dataRights)
				if (gp.getData().equals(data)) {
					gp.remove();
					break;
				}
		} else
			throw new AccessNotAllowedException(
					"You can't remove right from data");
	}

	public boolean isAccessAllow(ModuleData data) throws NotEmptyException {
		
		if (data == null)
			throw new NotEmptyException("data cannot be empty");
		
		for (DataRight item : dataRights)
			if (item.getData().equals(data))
				return true;
		return false;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) throws AccessNotAllowedException, DataFormatException, NotEmptyException, DataLengthException, NotUniqueException {
		
		if (ident == null || ident.length() == 0)
			throw new NotEmptyException("ident cannot be empty");

		if (!Utils.regexMatch(ident, "[a-zA-Z0-9-_]+"))
			throw new DataFormatException(
					"ident parameter has to match with ([a-zA-Z0-9]+)");
		
		if (ident.length() > 70)
			throw new DataLengthException("ident parameter is too long (max: 70 carac)");
		
		if (isIdentExist(ident))
			throw new NotUniqueException("ident has to be unique");
		
		if (Tools.hasRight("SET_RIGHT_IDENT"))
			this.ident = ident;
		else
			throw new AccessNotAllowedException(
					"You can't edit a right ident as: " + ident);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws AccessNotAllowedException, NotEmptyException, DataLengthException {
		
		if (name == null || name.length() == 0)
			throw new NotEmptyException("name cannot be empty");
		
		if (name.length() > 100)
			throw new DataLengthException("name parameter is too long (max: 100 carac)");
		
		if (Tools.hasRight("SET_RIGHT_NAME"))
			this.name = name;
		else
			throw new AccessNotAllowedException(
					"You can't edit a right name as: " + name);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description)
			throws AccessNotAllowedException {
		if (Tools.hasRight("SET_RIGHT_DESCRIP"))
			this.description = description;
		else
			throw new AccessNotAllowedException(
					"You can't edit a right description");
	}

	public boolean isUse() {
		return actionRights.size() > 0 || groupRights.size() > 0
				|| moduleRights.size() > 0 || userRights.size() > 0;
	}
	
	   
    @PersistenceContext
    public transient EntityManager entityManager;
    
    public static final EntityManager entityManager() {
        EntityManager em = new AppRight().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long countAppRights() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AppRight o", Long.class).getSingleResult();
    }
    
    public static List<AppRight> findAllAppRights() {
        return entityManager().createQuery("SELECT o FROM AppRight o", AppRight.class).getResultList();
    }
    
    public static AppRight findAppRight(Integer right) {
        if (right == null) return null;
        return entityManager().find(AppRight.class, right);
    }
    
    public static List<AppRight> findAppRightEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AppRight o", AppRight.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AppRight attached = this;
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public AppRight merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AppRight merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

}
