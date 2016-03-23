/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query;

import EntityBeans.Labels;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import EntityBeans.Plants;
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
 * @author falbellaihi
 */
public class LabelsController implements Serializable {

    public LabelsController() {

               this.emf = Persistence.createEntityManagerFactory("PlantaloguePU");

    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Labels labels) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Plants plantsOrphanCheck = labels.getPlants();
        if (plantsOrphanCheck != null) {
            Labels oldLabelsOfPlants = plantsOrphanCheck.getLabels();
            if (oldLabelsOfPlants != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Plants " + plantsOrphanCheck + " already has an item of type Labels whose plants column cannot be null. Please make another selection for the plants field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plants plants = labels.getPlants();
            if (plants != null) {
                plants = em.getReference(plants.getClass(), plants.getPlantID());
                labels.setPlants(plants);
            }
            em.persist(labels);
            if (plants != null) {
                plants.setLabels(labels);
                plants = em.merge(plants);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findLabels(labels.getPlantID()) != null) {
                throw new PreexistingEntityException("Labels " + labels + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Labels labels) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Labels persistentLabels = em.find(Labels.class, labels.getPlantID());
            Plants plantsOld = persistentLabels.getPlants();
            Plants plantsNew = labels.getPlants();
            List<String> illegalOrphanMessages = null;
            if (plantsNew != null && !plantsNew.equals(plantsOld)) {
                Labels oldLabelsOfPlants = plantsNew.getLabels();
                if (oldLabelsOfPlants != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Plants " + plantsNew + " already has an item of type Labels whose plants column cannot be null. Please make another selection for the plants field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (plantsNew != null) {
                plantsNew = em.getReference(plantsNew.getClass(), plantsNew.getPlantID());
                labels.setPlants(plantsNew);
            }
            labels = em.merge(labels);
            if (plantsOld != null && !plantsOld.equals(plantsNew)) {
                plantsOld.setLabels(null);
                plantsOld = em.merge(plantsOld);
            }
            if (plantsNew != null && !plantsNew.equals(plantsOld)) {
                plantsNew.setLabels(labels);
                plantsNew = em.merge(plantsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = labels.getPlantID();
                if (findLabels(id) == null) {
                    throw new NonexistentEntityException("The labels with id " + id + " no longer exists.");
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
            Labels labels;
            try {
                labels = em.getReference(Labels.class, id);
                labels.getPlantID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The labels with id " + id + " no longer exists.", enfe);
            }
            Plants plants = labels.getPlants();
            if (plants != null) {
                plants.setLabels(null);
                plants = em.merge(plants);
            }
            em.remove(labels);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Labels> findLabelsEntities() {
        return findLabelsEntities(true, -1, -1);
    }

    public List<Labels> findLabelsEntities(int maxResults, int firstResult) {
        return findLabelsEntities(false, maxResults, firstResult);
    }

    private List<Labels> findLabelsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Labels.class));
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

    public Labels findLabels(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Labels.class, id);
        } finally {
            em.close();
        }
    }

    public int getLabelsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Labels> rt = cq.from(Labels.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
