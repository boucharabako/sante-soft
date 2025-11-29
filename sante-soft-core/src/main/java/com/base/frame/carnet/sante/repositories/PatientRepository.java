/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Bouchara
 */
public interface PatientRepository extends JpaRepository<Patient, String>{
    
    @Query("Select p from Patient p where p.numeroCarnet=:numeroCarnet")
    public Patient findPatientByNumeroCarnet(@Param("numeroCarnet") String numeroCarnet);
}

