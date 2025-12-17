package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.TypeConsultation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour TypeConsultation
 * @author Bouchara
 */
@Repository
public interface TypeConsultationRepository extends JpaRepository<TypeConsultation, String> {
    
    /**
     * Trouver tous les types de consultation par catégorie
     * @param categorieConsultation ID de la catégorie
     * @return Liste des types de consultation
     */
    List<TypeConsultation> findByCategorieConsultation(String categorieConsultation);
}

