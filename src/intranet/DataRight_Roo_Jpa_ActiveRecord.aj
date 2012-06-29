// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package intranet;

import intranet.DataRight;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect DataRight_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager DataRight.entityManager;
    
    public static final EntityManager DataRight.entityManager() {
        EntityManager em = new DataRight().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long DataRight.countDataRights() {
        return entityManager().createQuery("SELECT COUNT(o) FROM DataRight o", Long.class).getSingleResult();
    }
    
    public static List<DataRight> DataRight.findAllDataRights() {
        return entityManager().createQuery("SELECT o FROM DataRight o", DataRight.class).getResultList();
    }
    
    public static DataRight DataRight.findDataRight(Integer dataRight) {
        if (dataRight == null) return null;
        return entityManager().find(DataRight.class, dataRight);
    }
    
    public static List<DataRight> DataRight.findDataRightEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM DataRight o", DataRight.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void DataRight.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void DataRight.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            DataRight attached = DataRight.findDataRight(this.dataRight);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void DataRight.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void DataRight.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public DataRight DataRight.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        DataRight merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}