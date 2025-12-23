package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.Vaccination;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour Vaccination
 * @author Bouchara
 */
@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, String> {
    
    /**
     * Trouver toutes les vaccinations d'un patient
     * @param idPatient ID du patient
     * @return Liste des vaccinations
     */
    List<Vaccination> findByIdPatient(String idPatient);
}

