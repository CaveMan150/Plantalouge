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
import EntityBeans.Plants;
import EntityBeans.Tasks;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import query.exceptions.NonexistentEntityException;

/**
 *
 * @author falbellaihi
 */
public class TasksController implements Serializable {

    public TasksController() {
               this.emf = Persistence.createEntityManagerFactory("PlantaloguePU");

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tasks tasks) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plants plantID = tasks.getPlantID();
            if (plantID != null) {
                plantID = em.getReference(plantID.getClass(), plantID.getPlantID());
                tasks.setPlantID(plantID);
            }
            em.persist(tasks);
            if (plantID != null) {
                plantID.getTasksCollection().add(tasks);
                plantID = em.merge(plantID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    
       public Tasks findT(String value) {
        try
       {
           EntityManager em = getEntityManager();
           Tasks task=em.createNamedQuery("Users.findByUsername", Tasks.class).setParameter("Task_ID", value).getSingleResult();
           if(task==null)
               return null;
            return task;
       }
       catch (Exception e)
       {
            return null;
       }
    }
    
    public void edit(Tasks tasks) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tasks persistentTasks = em.find(Tasks.class, tasks.getTaskID());
            Plants plantIDOld = persistentTasks.getPlantID();
            Plants plantIDNew = tasks.getPlantID();
            if (plantIDNew != null) {
                plantIDNew = em.getReference(plantIDNew.getClass(), plantIDNew.getPlantID());
                tasks.setPlantID(plantIDNew);
            }
            tasks = em.merge(tasks);
            if (plantIDOld != null && !plantIDOld.equals(plantIDNew)) {
                plantIDOld.getTasksCollection().remove(tasks);
                plantIDOld = em.merge(plantIDOld);
            }
            if (plantIDNew != null && !plantIDNew.equals(plantIDOld)) {
                plantIDNew.getTasksCollection().add(tasks);
                plantIDNew = em.merge(plantIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tasks.getTaskID();
                if (findTasks(id) == null) {
                    throw new NonexistentEntityException("The tasks with id " + id + " no longer exists.");
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
            Tasks tasks;
            try {
                tasks = em.getReference(Tasks.class, id);
                tasks.getTaskID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tasks with id " + id + " no longer exists.", enfe);
            }
            Plants plantID = tasks.getPlantID();
            if (plantID != null) {
                plantID.getTasksCollection().remove(tasks);
                plantID = em.merge(plantID);
            }
            em.remove(tasks);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tasks> findTasksEntities() {
        return findTasksEntities(true, -1, -1);
    }

    public List<Tasks> findTasksEntities(int maxResults, int firstResult) {
        return findTasksEntities(false, maxResults, firstResult);
    }

    private List<Tasks> findTasksEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tasks.class));
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

    public Tasks findTasks(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tasks.class, id);
        } finally {
            em.close();
        }
    }

    public int getTasksCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tasks> rt = cq.from(Tasks.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
