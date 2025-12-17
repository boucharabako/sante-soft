package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.HistoriqueAntecedent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository pour HistoriqueAntecedent
 * @author Bouchara
 */
@Repository
public interface HistoriqueAntecedentRepository extends JpaRepository<HistoriqueAntecedent, String> {
    
    /**
     * Trouver tous les historiques d'un antécédent
     * @param idAntecedent
     * @return
     */
    @Query("SELECT h FROM HistoriqueAntecedent h WHERE h.idAntecedent = :idAntecedent ORDER BY h.dateAction DESC")
    List<HistoriqueAntecedent> findByIdAntecedent(@Param("idAntecedent") String idAntecedent);
    
    /**
     * Trouver tous les historiques d'un patient (via ses antécédents)
     * @param idPatient
     * @return
     */
    @Query("SELECT h FROM HistoriqueAntecedent h " +
           "JOIN AntecedentPatient a ON h.idAntecedent = a.id " +
           "WHERE a.idPatient = :idPatient " +
           "ORDER BY h.dateAction DESC")
    List<HistoriqueAntecedent> findByIdPatient(@Param("idPatient") String idPatient);
}

