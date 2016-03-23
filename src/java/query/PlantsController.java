/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query;

import EntityBeans.Plants;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import EntityBeans.Users;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import query.exceptions.NonexistentEntityException;

/**
 *
 * @author falbellaihi
 */
public class PlantsController implements Serializable {

     public PlantsController() {
        this.emf = Persistence.createEntityManagerFactory("PlantaloguePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Plants plants) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Users userID = plants.getUserID();
            if (userID != null) {
                userID = em.getReference(userID.getClass(), userID.getId());
                plants.setUserID(userID);
            }
            em.persist(plants);
            if (userID != null) {
                userID.getPlantsCollection().add(plants);
                userID = em.merge(userID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Plants plants) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plants persistentPlants = em.find(Plants.class, plants.getPlantID());
            Users userIDOld = persistentPlants.getUserID();
            Users userIDNew = plants.getUserID();
            if (userIDNew != null) {
                userIDNew = em.getReference(userIDNew.getClass(), userIDNew.getId());
                plants.setUserID(userIDNew);
            }
            plants = em.merge(plants);
            if (userIDOld != null && !userIDOld.equals(userIDNew)) {
                userIDOld.getPlantsCollection().remove(plants);
                userIDOld = em.merge(userIDOld);
            }
            if (userIDNew != null && !userIDNew.equals(userIDOld)) {
                userIDNew.getPlantsCollection().add(plants);
                userIDNew = em.merge(userIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = plants.getPlantID();
                if (findPlants(id) == null) {
                    throw new NonexistentEntityException("The plants with id " + id + " no longer exists.");
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
            Plants plants;
            try {
                plants = em.getReference(Plants.class, id);
                plants.getPlantID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plants with id " + id + " no longer exists.", enfe);
            }
            Users userID = plants.getUserID();
            if (userID != null) {
                userID.getPlantsCollection().remove(plants);
                userID = em.merge(userID);
            }
            em.remove(plants);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Plants> findPlantsEntities() {
        return findPlantsEntities(true, -1, -1);
    }

    public List<Plants> findPlantsEntities(int maxResults, int firstResult) {
        return findPlantsEntities(false, maxResults, firstResult);
    }

    private List<Plants> findPlantsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Plants.class));
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

    public Plants findPlants(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Plants.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlantsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Plants> rt = cq.from(Plants.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
