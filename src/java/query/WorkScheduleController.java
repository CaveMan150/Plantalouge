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
import EntityBeans.Tasks;
import EntityBeans.Users;
import EntityBeans.WorkSchedule;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import query.exceptions.IllegalOrphanException;
import query.exceptions.NonexistentEntityException;
import query.exceptions.PreexistingEntityException;

/**
 *
 * @author Falbe
 */
public class WorkScheduleController implements Serializable {

 public WorkScheduleController() {
                this.emf = Persistence.createEntityManagerFactory("PlantaloguePU");

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(WorkSchedule workSchedule) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Tasks tasksOrphanCheck = workSchedule.getTasks();
        if (tasksOrphanCheck != null) {
            WorkSchedule oldWorkScheduleOfTasks = tasksOrphanCheck.getWorkSchedule();
            if (oldWorkScheduleOfTasks != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Tasks " + tasksOrphanCheck + " already has an item of type WorkSchedule whose tasks column cannot be null. Please make another selection for the tasks field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tasks tasks = workSchedule.getTasks();
            if (tasks != null) {
                tasks = em.getReference(tasks.getClass(), tasks.getTaskID());
                workSchedule.setTasks(tasks);
            }
            Users userID = workSchedule.getUserID();
            if (userID != null) {
                userID = em.getReference(userID.getClass(), userID.getId());
                workSchedule.setUserID(userID);
            }
            em.persist(workSchedule);
            if (tasks != null) {
                tasks.setWorkSchedule(workSchedule);
                tasks = em.merge(tasks);
            }
            if (userID != null) {
                userID.getWorkScheduleCollection().add(workSchedule);
                userID = em.merge(userID);
            }
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

    public void edit(WorkSchedule workSchedule) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WorkSchedule persistentWorkSchedule = em.find(WorkSchedule.class, workSchedule.getTaskID());
            Tasks tasksOld = persistentWorkSchedule.getTasks();
            Tasks tasksNew = workSchedule.getTasks();
            Users userIDOld = persistentWorkSchedule.getUserID();
            Users userIDNew = workSchedule.getUserID();
            List<String> illegalOrphanMessages = null;
            if (tasksNew != null && !tasksNew.equals(tasksOld)) {
                WorkSchedule oldWorkScheduleOfTasks = tasksNew.getWorkSchedule();
                if (oldWorkScheduleOfTasks != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Tasks " + tasksNew + " already has an item of type WorkSchedule whose tasks column cannot be null. Please make another selection for the tasks field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tasksNew != null) {
                tasksNew = em.getReference(tasksNew.getClass(), tasksNew.getTaskID());
                workSchedule.setTasks(tasksNew);
            }
            if (userIDNew != null) {
                userIDNew = em.getReference(userIDNew.getClass(), userIDNew.getId());
                workSchedule.setUserID(userIDNew);
            }
            workSchedule = em.merge(workSchedule);
            if (tasksOld != null && !tasksOld.equals(tasksNew)) {
                tasksOld.setWorkSchedule(null);
                tasksOld = em.merge(tasksOld);
            }
            if (tasksNew != null && !tasksNew.equals(tasksOld)) {
                tasksNew.setWorkSchedule(workSchedule);
                tasksNew = em.merge(tasksNew);
            }
            if (userIDOld != null && !userIDOld.equals(userIDNew)) {
                userIDOld.getWorkScheduleCollection().remove(workSchedule);
                userIDOld = em.merge(userIDOld);
            }
            if (userIDNew != null && !userIDNew.equals(userIDOld)) {
                userIDNew.getWorkScheduleCollection().add(workSchedule);
                userIDNew = em.merge(userIDNew);
            }
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
            Tasks tasks = workSchedule.getTasks();
            if (tasks != null) {
                tasks.setWorkSchedule(null);
                tasks = em.merge(tasks);
            }
            Users userID = workSchedule.getUserID();
            if (userID != null) {
                userID.getWorkScheduleCollection().remove(workSchedule);
                userID = em.merge(userID);
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
