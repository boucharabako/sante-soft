package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.ConsultationTypeExamenAutorise;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour ConsultationTypeExamenAutorise
 * @author Bouchara
 */
@Repository
public interface ConsultationTypeExamenAutoriseRepository extends JpaRepository<ConsultationTypeExamenAutorise, String> {
    
    /**
     * Trouver tous les types d'examen autorisés pour un type de consultation
     * @param typeConsultation ID du type de consultation
     * @return Liste des types d'examen autorisés
     */
    List<ConsultationTypeExamenAutorise> findByTypeConsultation(String typeConsultation);
}

