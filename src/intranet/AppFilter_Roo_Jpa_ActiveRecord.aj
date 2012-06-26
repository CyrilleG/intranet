// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.AppFilter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AppFilter_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager AppFilter.entityManager;
    
    public static final EntityManager AppFilter.entityManager() {
        EntityManager em = new AppFilter().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AppFilter.countAppFilters() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AppFilter o", Long.class).getSingleResult();
    }
    
    public static List<AppFilter> AppFilter.findAllAppFilters() {
        return entityManager().createQuery("SELECT o FROM AppFilter o", AppFilter.class).getResultList();
    }
    
    public static AppFilter AppFilter.findAppFilter(Integer idfilter) {
        if (idfilter == null) return null;
        return entityManager().find(AppFilter.class, idfilter);
    }
    
    public static List<AppFilter> AppFilter.findAppFilterEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AppFilter o", AppFilter.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void AppFilter.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AppFilter.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AppFilter attached = AppFilter.findAppFilter(this.idfilter);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AppFilter.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AppFilter.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public AppFilter AppFilter.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AppFilter merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}