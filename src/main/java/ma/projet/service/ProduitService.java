package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import ma.projet.classes.Categorie;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProduitService implements IDao<Produit> {
    
    @Override
    public boolean create(Produit produit) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(produit);
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
    public boolean delete(Produit produit) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Produit managedProduit = em.find(Produit.class, produit.getId());
            if (managedProduit != null) {
                em.remove(managedProduit);
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
    public boolean update(Produit produit) {
        EntityManager em = HibernateUtil.getEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.merge(produit);
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
    public Produit findById(int id) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.find(Produit.class, id);
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Produit> findAll() {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Produit p", Produit.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Afficher la liste des produits par catégorie
     * @param categorie la catégorie des produits
     * @return liste des produits de la catégorie
     */
    public List<Produit> findByCategorie(Categorie categorie) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                "SELECT p FROM Produit p WHERE p.categorie = :categorie", 
                Produit.class
            );
            query.setParameter("categorie", categorie);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Afficher les produits commandés entre deux dates
     * @param dateDebut date de début
     * @param dateFin date de fin
     * @return liste des produits commandés
     */
    public List<Produit> findProduitsCommandesEntreDates(Date dateDebut, Date dateFin) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                "SELECT DISTINCT p FROM Produit p " +
                "JOIN p.lignesCommande lc " +
                "JOIN lc.commande c " +
                "WHERE c.date BETWEEN :dateDebut AND :dateFin " +
                "ORDER BY p.reference",
                Produit.class
            );
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Afficher les produits d'une commande donnée
     * @param commandeId l'identifiant de la commande
     * @return liste des produits de la commande
     */
    public List<Produit> findProduitsByCommande(int commandeId) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<Produit> query = em.createQuery(
                "SELECT p FROM Produit p " +
                "JOIN p.lignesCommande lc " +
                "WHERE lc.commande.id = :commandeId",
                Produit.class
            );
            query.setParameter("commandeId", commandeId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Afficher les produits dont le prix est supérieur à un montant donné
     * en utilisant une requête nommée
     * @param prix le prix minimum
     * @return liste des produits dont le prix est supérieur
     */
    public List<Produit> findByPrixSuperieurA(float prix) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            TypedQuery<Produit> query = em.createNamedQuery(
                "Produit.findByPrixSuperieurA", 
                Produit.class
            );
            query.setParameter("prix", prix);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
