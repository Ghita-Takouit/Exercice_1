package ma.projet.dao;

import java.util.List;

/**
 * Interface générique pour les opérations DAO
 * @param <T> Type de l'entité
 */
public interface IDao<T> {
    
    /**
     * Créer une nouvelle entité
     * @param o l'entité à créer
     * @return true si la création a réussi, false sinon
     */
    boolean create(T o);
    
    /**
     * Supprimer une entité
     * @param o l'entité à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    boolean delete(T o);
    
    /**
     * Mettre à jour une entité
     * @param o l'entité à mettre à jour
     * @return true si la mise à jour a réussi, false sinon
     */
    boolean update(T o);
    
    /**
     * Rechercher une entité par son identifiant
     * @param id l'identifiant de l'entité
     * @return l'entité trouvée ou null
     */
    T findById(int id);
    
    /**
     * Récupérer toutes les entités
     * @return liste de toutes les entités
     */
    List<T> findAll();
}
