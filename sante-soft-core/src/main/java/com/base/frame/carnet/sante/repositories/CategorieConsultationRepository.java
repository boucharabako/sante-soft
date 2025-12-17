package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.CategorieConsultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour CategorieConsultation
 * @author Bouchara
 */
@Repository
public interface CategorieConsultationRepository extends JpaRepository<CategorieConsultation, String> {
    
}

