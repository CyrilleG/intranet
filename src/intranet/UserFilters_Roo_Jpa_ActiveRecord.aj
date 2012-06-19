// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.UserFilters;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect UserFilters_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager UserFilters.entityManager;
    
    public static final EntityManager UserFilters.entityManager() {
        EntityManager em = new UserFilters().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long UserFilters.countUserFilterses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM UserFilters o", Long.class).getSingleResult();
    }
    
    public static List<UserFilters> UserFilters.findAllUserFilterses() {
        return entityManager().createQuery("SELECT o FROM UserFilters o", UserFilters.class).getResultList();
    }
    
    public static UserFilters UserFilters.findUserFilters(Integer iduserFilters) {
        if (iduserFilters == null) return null;
        return entityManager().find(UserFilters.class, iduserFilters);
    }
    
    public static List<UserFilters> UserFilters.findUserFiltersEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM UserFilters o", UserFilters.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void UserFilters.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void UserFilters.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            UserFilters attached = UserFilters.findUserFilters(this.iduserFilters);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void UserFilters.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void UserFilters.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public UserFilters UserFilters.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        UserFilters merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}