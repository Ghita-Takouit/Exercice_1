package ma.projet.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe utilitaire pour gérer les EntityManager avec Spring Boot et Hibernate
 */
@Component
public class HibernateUtil {
    
    private static EntityManagerFactory entityManagerFactory;
    
    @Autowired
    public HibernateUtil(EntityManagerFactory emf) {
        HibernateUtil.entityManagerFactory = emf;
    }
    
    /**
     * Récupère un EntityManager
     * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException("EntityManagerFactory n'est pas initialisé");
        }
        return entityManagerFactory.createEntityManager();
    }
    
    /**
     * Ferme l'EntityManagerFactory
     */
    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
