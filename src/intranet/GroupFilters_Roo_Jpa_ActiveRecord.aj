// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.GroupFilters;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect GroupFilters_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager GroupFilters.entityManager;
    
    public static final EntityManager GroupFilters.entityManager() {
        EntityManager em = new GroupFilters().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long GroupFilters.countGroupFilterses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM GroupFilters o", Long.class).getSingleResult();
    }
    
    public static List<GroupFilters> GroupFilters.findAllGroupFilterses() {
        return entityManager().createQuery("SELECT o FROM GroupFilters o", GroupFilters.class).getResultList();
    }
    
    public static GroupFilters GroupFilters.findGroupFilters(Integer idgroupFilter) {
        if (idgroupFilter == null) return null;
        return entityManager().find(GroupFilters.class, idgroupFilter);
    }
    
    public static List<GroupFilters> GroupFilters.findGroupFiltersEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM GroupFilters o", GroupFilters.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void GroupFilters.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void GroupFilters.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            GroupFilters attached = GroupFilters.findGroupFilters(this.idgroupFilter);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void GroupFilters.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void GroupFilters.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public GroupFilters GroupFilters.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        GroupFilters merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}