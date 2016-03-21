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
import EntityBeans.WorkSchedule;
import EntityBeans.Plants;
import EntityBeans.Tasks;
import java.util.ArrayList;
import java.util.Collection;
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
public class TasksController implements Serializable {

    public TasksController() {
        this.emf = Persistence.createEntityManagerFactory("PlantaloguePU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tasks tasks) throws PreexistingEntityException, Exception {
        if (tasks.getPlantsCollection() == null) {
            tasks.setPlantsCollection(new ArrayList<Plants>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            WorkSchedule workSchedule = tasks.getWorkSchedule();
            if (workSchedule != null) {
                workSchedule = em.getReference(workSchedule.getClass(), workSchedule.getTaskID());
                tasks.setWorkSchedule(workSchedule);
            }
            Collection<Plants> attachedPlantsCollection = new ArrayList<Plants>();
            for (Plants plantsCollectionPlantsToAttach : tasks.getPlantsCollection()) {
                plantsCollectionPlantsToAttach = em.getReference(plantsCollectionPlantsToAttach.getClass(), plantsCollectionPlantsToAttach.getPlantID());
                attachedPlantsCollection.add(plantsCollectionPlantsToAttach);
            }
            tasks.setPlantsCollection(attachedPlantsCollection);
            em.persist(tasks);
            if (workSchedule != null) {
                Tasks oldTasksOfWorkSchedule = workSchedule.getTasks();
                if (oldTasksOfWorkSchedule != null) {
                    oldTasksOfWorkSchedule.setWorkSchedule(null);
                    oldTasksOfWorkSchedule = em.merge(oldTasksOfWorkSchedule);
                }
                workSchedule.setTasks(tasks);
                workSchedule = em.merge(workSchedule);
            }
            for (Plants plantsCollectionPlants : tasks.getPlantsCollection()) {
                Tasks oldTaskIDOfPlantsCollectionPlants = plantsCollectionPlants.getTaskID();
                plantsCollectionPlants.setTaskID(tasks);
                plantsCollectionPlants = em.merge(plantsCollectionPlants);
                if (oldTaskIDOfPlantsCollectionPlants != null) {
                    oldTaskIDOfPlantsCollectionPlants.getPlantsCollection().remove(plantsCollectionPlants);
                    oldTaskIDOfPlantsCollectionPlants = em.merge(oldTaskIDOfPlantsCollectionPlants);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTasks(tasks.getTaskID()) != null) {
                throw new PreexistingEntityException("Tasks " + tasks + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tasks tasks) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tasks persistentTasks = em.find(Tasks.class, tasks.getTaskID());
            WorkSchedule workScheduleOld = persistentTasks.getWorkSchedule();
            WorkSchedule workScheduleNew = tasks.getWorkSchedule();
            Collection<Plants> plantsCollectionOld = persistentTasks.getPlantsCollection();
            Collection<Plants> plantsCollectionNew = tasks.getPlantsCollection();
            List<String> illegalOrphanMessages = null;
            if (workScheduleOld != null && !workScheduleOld.equals(workScheduleNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain WorkSchedule " + workScheduleOld + " since its tasks field is not nullable.");
            }
            for (Plants plantsCollectionOldPlants : plantsCollectionOld) {
                if (!plantsCollectionNew.contains(plantsCollectionOldPlants)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Plants " + plantsCollectionOldPlants + " since its taskID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (workScheduleNew != null) {
                workScheduleNew = em.getReference(workScheduleNew.getClass(), workScheduleNew.getTaskID());
                tasks.setWorkSchedule(workScheduleNew);
            }
            Collection<Plants> attachedPlantsCollectionNew = new ArrayList<Plants>();
            for (Plants plantsCollectionNewPlantsToAttach : plantsCollectionNew) {
                plantsCollectionNewPlantsToAttach = em.getReference(plantsCollectionNewPlantsToAttach.getClass(), plantsCollectionNewPlantsToAttach.getPlantID());
                attachedPlantsCollectionNew.add(plantsCollectionNewPlantsToAttach);
            }
            plantsCollectionNew = attachedPlantsCollectionNew;
            tasks.setPlantsCollection(plantsCollectionNew);
            tasks = em.merge(tasks);
            if (workScheduleNew != null && !workScheduleNew.equals(workScheduleOld)) {
                Tasks oldTasksOfWorkSchedule = workScheduleNew.getTasks();
                if (oldTasksOfWorkSchedule != null) {
                    oldTasksOfWorkSchedule.setWorkSchedule(null);
                    oldTasksOfWorkSchedule = em.merge(oldTasksOfWorkSchedule);
                }
                workScheduleNew.setTasks(tasks);
                workScheduleNew = em.merge(workScheduleNew);
            }
            for (Plants plantsCollectionNewPlants : plantsCollectionNew) {
                if (!plantsCollectionOld.contains(plantsCollectionNewPlants)) {
                    Tasks oldTaskIDOfPlantsCollectionNewPlants = plantsCollectionNewPlants.getTaskID();
                    plantsCollectionNewPlants.setTaskID(tasks);
                    plantsCollectionNewPlants = em.merge(plantsCollectionNewPlants);
                    if (oldTaskIDOfPlantsCollectionNewPlants != null && !oldTaskIDOfPlantsCollectionNewPlants.equals(tasks)) {
                        oldTaskIDOfPlantsCollectionNewPlants.getPlantsCollection().remove(plantsCollectionNewPlants);
                        oldTaskIDOfPlantsCollectionNewPlants = em.merge(oldTaskIDOfPlantsCollectionNewPlants);
                    }
                }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            WorkSchedule workScheduleOrphanCheck = tasks.getWorkSchedule();
            if (workScheduleOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tasks (" + tasks + ") cannot be destroyed since the WorkSchedule " + workScheduleOrphanCheck + " in its workSchedule field has a non-nullable tasks field.");
            }
            Collection<Plants> plantsCollectionOrphanCheck = tasks.getPlantsCollection();
            for (Plants plantsCollectionOrphanCheckPlants : plantsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tasks (" + tasks + ") cannot be destroyed since the Plants " + plantsCollectionOrphanCheckPlants + " in its plantsCollection field has a non-nullable taskID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
