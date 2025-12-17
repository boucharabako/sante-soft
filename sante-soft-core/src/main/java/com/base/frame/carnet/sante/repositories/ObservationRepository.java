package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.Observation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour Observation
 * @author Bouchara
 */
@Repository
public interface ObservationRepository extends JpaRepository<Observation, String> {
    
    /**
     * Trouver toutes les observations d'un patient
     * @param idPatient ID du patient
     * @return Liste des observations
     */
    List<Observation> findByIdPatient(String idPatient);
}

