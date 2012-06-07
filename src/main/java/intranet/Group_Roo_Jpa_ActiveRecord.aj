// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Group;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Group_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Group.entityManager;
    
    public static final EntityManager Group.entityManager() {
        EntityManager em = new Group().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Group.countGroups() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Group o", Long.class).getSingleResult();
    }
    
    public static List<Group> Group.findAllGroups() {
        return entityManager().createQuery("SELECT o FROM Group o", Group.class).getResultList();
    }
    
    public static Group Group.findGroup(Integer idgroup) {
        if (idgroup == null) return null;
        return entityManager().find(Group.class, idgroup);
    }
    
    public static List<Group> Group.findGroupEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Group o", Group.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Group.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Group.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Group attached = Group.findGroup(this.idgroup);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Group.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Group.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Group Group.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Group merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
