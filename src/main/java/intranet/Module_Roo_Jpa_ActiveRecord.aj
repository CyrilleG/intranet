// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.Module;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Module_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Module.entityManager;
    
    public static final EntityManager Module.entityManager() {
        EntityManager em = new Module().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Module.countModules() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Module o", Long.class).getSingleResult();
    }
    
    public static List<Module> Module.findAllModules() {
        return entityManager().createQuery("SELECT o FROM Module o", Module.class).getResultList();
    }
    
    public static Module Module.findModule(Integer idmodule) {
        if (idmodule == null) return null;
        return entityManager().find(Module.class, idmodule);
    }
    
    public static List<Module> Module.findModuleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Module o", Module.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Module.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Module.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Module attached = Module.findModule(this.idmodule);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Module.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Module.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Module Module.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Module merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
