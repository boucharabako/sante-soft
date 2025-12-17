/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.AntecedentPatient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public interface AntecedentPatientRepository extends JpaRepository<AntecedentPatient, String> {

    /**
     * Trouver tous les antécédents d'un patient (incluant les supprimés)
     * @param idPatient
     * @return
     */
    List<AntecedentPatient> findByIdPatient(String idPatient);

    /**
     * Trouver tous les antécédents actifs (non supprimés) d'un patient
     * @param idPatient
     * @return
     */
    @Query("SELECT a FROM AntecedentPatient a WHERE a.idPatient = :idPatient AND (a.deleted = false OR a.deleted IS NULL)")
    List<AntecedentPatient> findActiveByIdPatient(@Param("idPatient") String idPatient);
}

