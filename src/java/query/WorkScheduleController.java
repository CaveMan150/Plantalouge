/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query;

import EntityBeans.WorkSchedule;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import query.exceptions.NonexistentEntityException;
import query.exceptions.PreexistingEntityException;

/**
 *
 * @author falbellaihi
 */
public class WorkScheduleController implements Serializable {

    public WorkScheduleController() {
                this.emf = Persistence.createEntityManagerFactory("PlantaloguePU");

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(WorkSchedule workSchedule) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(workSchedule);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findWorkSchedule(workSchedule.getTaskID()) != null) {
                throw new PreexistingEntityException("WorkSchedule " + workSchedule + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(WorkSchedule workSchedule) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            workSchedule = em.merge(workSchedule);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = workSchedule.getTaskID();
                if (findWorkSchedule(id) == null) {
                    throw new NonexistentEntityException("The workSchedule with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WorkSchedule workSchedule;
            try {
                workSchedule = em.getReference(WorkSchedule.class, id);
                workSchedule.getTaskID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The workSchedule with id " + id + " no longer exists.", enfe);
            }
            em.remove(workSchedule);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<WorkSchedule> findWorkScheduleEntities() {
        return findWorkScheduleEntities(true, -1, -1);
    }

    public List<WorkSchedule> findWorkScheduleEntities(int maxResults, int firstResult) {
        return findWorkScheduleEntities(false, maxResults, firstResult);
    }

    private List<WorkSchedule> findWorkScheduleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(WorkSchedule.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public WorkSchedule findWorkSchedule(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(WorkSchedule.class, id);
        } finally {
            em.close();
        }
    }

    public int getWorkScheduleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<WorkSchedule> rt = cq.from(WorkSchedule.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
