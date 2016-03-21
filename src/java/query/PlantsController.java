/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import EntityBeans.Users;
import EntityBeans.Tasks;
import EntityBeans.Labels;
import EntityBeans.Plants;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import query.exceptions.IllegalOrphanException;
import query.exceptions.NonexistentEntityException;

/**
 *
 * @author Falbe
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
            Tasks taskID = plants.getTaskID();
            if (taskID != null) {
                taskID = em.getReference(taskID.getClass(), taskID.getTaskID());
                plants.setTaskID(taskID);
            }
            Labels labels = plants.getLabels();
            if (labels != null) {
                labels = em.getReference(labels.getClass(), labels.getPlantID());
                plants.setLabels(labels);
            }
            em.persist(plants);
            if (userID != null) {
                userID.getPlantsCollection().add(plants);
                userID = em.merge(userID);
            }
            if (taskID != null) {
                taskID.getPlantsCollection().add(plants);
                taskID = em.merge(taskID);
            }
            if (labels != null) {
                Plants oldPlantsOfLabels = labels.getPlants();
                if (oldPlantsOfLabels != null) {
                    oldPlantsOfLabels.setLabels(null);
                    oldPlantsOfLabels = em.merge(oldPlantsOfLabels);
                }
                labels.setPlants(plants);
                labels = em.merge(labels);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Plants plants) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plants persistentPlants = em.find(Plants.class, plants.getPlantID());
            Users userIDOld = persistentPlants.getUserID();
            Users userIDNew = plants.getUserID();
            Tasks taskIDOld = persistentPlants.getTaskID();
            Tasks taskIDNew = plants.getTaskID();
            Labels labelsOld = persistentPlants.getLabels();
            Labels labelsNew = plants.getLabels();
            List<String> illegalOrphanMessages = null;
            if (labelsOld != null && !labelsOld.equals(labelsNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Labels " + labelsOld + " since its plants field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (userIDNew != null) {
                userIDNew = em.getReference(userIDNew.getClass(), userIDNew.getId());
                plants.setUserID(userIDNew);
            }
            if (taskIDNew != null) {
                taskIDNew = em.getReference(taskIDNew.getClass(), taskIDNew.getTaskID());
                plants.setTaskID(taskIDNew);
            }
            if (labelsNew != null) {
                labelsNew = em.getReference(labelsNew.getClass(), labelsNew.getPlantID());
                plants.setLabels(labelsNew);
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
            if (taskIDOld != null && !taskIDOld.equals(taskIDNew)) {
                taskIDOld.getPlantsCollection().remove(plants);
                taskIDOld = em.merge(taskIDOld);
            }
            if (taskIDNew != null && !taskIDNew.equals(taskIDOld)) {
                taskIDNew.getPlantsCollection().add(plants);
                taskIDNew = em.merge(taskIDNew);
            }
            if (labelsNew != null && !labelsNew.equals(labelsOld)) {
                Plants oldPlantsOfLabels = labelsNew.getPlants();
                if (oldPlantsOfLabels != null) {
                    oldPlantsOfLabels.setLabels(null);
                    oldPlantsOfLabels = em.merge(oldPlantsOfLabels);
                }
                labelsNew.setPlants(plants);
                labelsNew = em.merge(labelsNew);
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            Labels labelsOrphanCheck = plants.getLabels();
            if (labelsOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Plants (" + plants + ") cannot be destroyed since the Labels " + labelsOrphanCheck + " in its labels field has a non-nullable plants field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Users userID = plants.getUserID();
            if (userID != null) {
                userID.getPlantsCollection().remove(plants);
                userID = em.merge(userID);
            }
            Tasks taskID = plants.getTaskID();
            if (taskID != null) {
                taskID.getPlantsCollection().remove(plants);
                taskID = em.merge(taskID);
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
