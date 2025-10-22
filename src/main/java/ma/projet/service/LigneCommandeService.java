package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LigneCommandeService implements IDao<LigneCommandeProduit> {
    
    @Override
    public boolean create(LigneCommandeProduit ligneCommande) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(ligneCommande);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
    @Override
    public boolean delete(LigneCommandeProduit ligneCommande) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            LigneCommandeProduit managedLigne = em.find(LigneCommandeProduit.class, ligneCommande.getId());
            if (managedLigne != null) {
                em.remove(managedLigne);
            }
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
    @Override
    public boolean update(LigneCommandeProduit ligneCommande) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(ligneCommande);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
    @Override
    public LigneCommandeProduit findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(LigneCommandeProduit.class, id);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<LigneCommandeProduit> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT lc FROM LigneCommandeProduit lc", LigneCommandeProduit.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Récupérer les lignes de commande pour une commande donnée
     * @param commandeId l'identifiant de la commande
     * @return liste des lignes de commande
     */
    public List<LigneCommandeProduit> findByCommande(int commandeId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<LigneCommandeProduit> query = em.createQuery(
                "SELECT lc FROM LigneCommandeProduit lc WHERE lc.commande.id = :commandeId",
                LigneCommandeProduit.class
            );
            query.setParameter("commandeId", commandeId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
