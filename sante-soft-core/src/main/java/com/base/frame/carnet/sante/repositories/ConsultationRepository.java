package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.Consultation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository pour Consultation
 * @author Bouchara
 */
@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, String> {
    
    /**
     * Trouver toutes les consultations d'un patient
     * @param idPatient ID du patient
     * @return Liste des consultations
     */
    List<Consultation> findByIdPatient(String idPatient);

    /**
     * Trouver toutes les consultations d'un professionnel de santé
     * @param idProfessionnelSante ID du professionnel
     * @return Liste des consultations
     */
    List<Consultation> findByIdProfessionnelSante(String idProfessionnelSante);

    /**
     * Récupère les années distinctes où des consultations ont été effectuées
     * @return Liste des années (triées par ordre décroissant)
     */
    @Query(value = "SELECT DISTINCT CAST(EXTRACT(YEAR FROM date_consultation) AS INTEGER) " +
                   "FROM sante.consultation " +
                   "WHERE date_consultation IS NOT NULL " +
                   "ORDER BY CAST(EXTRACT(YEAR FROM date_consultation) AS INTEGER) DESC",
           nativeQuery = true)
    List<Integer> findDistinctYears();
}

