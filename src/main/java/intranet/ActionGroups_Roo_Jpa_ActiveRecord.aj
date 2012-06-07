// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.ActionGroups;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ActionGroups_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager ActionGroups.entityManager;
    
    public static final EntityManager ActionGroups.entityManager() {
        EntityManager em = new ActionGroups().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ActionGroups.countActionGroupses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ActionGroups o", Long.class).getSingleResult();
    }
    
    public static List<ActionGroups> ActionGroups.findAllActionGroupses() {
        return entityManager().createQuery("SELECT o FROM ActionGroups o", ActionGroups.class).getResultList();
    }
    
    public static ActionGroups ActionGroups.findActionGroups(Integer idactionGroup) {
        if (idactionGroup == null) return null;
        return entityManager().find(ActionGroups.class, idactionGroup);
    }
    
    public static List<ActionGroups> ActionGroups.findActionGroupsEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ActionGroups o", ActionGroups.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void ActionGroups.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ActionGroups.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            ActionGroups attached = ActionGroups.findActionGroups(this.idactionGroup);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void ActionGroups.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ActionGroups.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ActionGroups ActionGroups.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ActionGroups merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
