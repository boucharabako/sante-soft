package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.Prescription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour Prescription
 * @author Bouchara
 */
@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, String> {
    
    /**
     * Trouver toutes les prescriptions d'une consultation
     * @param idConsultation ID de la consultation
     * @return Liste des prescriptions
     */
    List<Prescription> findByIdConsultation(String idConsultation);
}

