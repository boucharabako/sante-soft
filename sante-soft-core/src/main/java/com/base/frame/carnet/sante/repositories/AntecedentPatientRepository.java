/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.AntecedentPatient;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public interface AntecedentPatientRepository extends JpaRepository<AntecedentPatient, String> {

    /**
     * Trouver tous les antécédents d'un patient
     * @param idPatient
     * @return
     */
    List<AntecedentPatient> findByIdPatient(String idPatient);
}

