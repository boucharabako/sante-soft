/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.Affectation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public interface AffectationRepository extends JpaRepository<Affectation, String> {
    
    List<Affectation> findByIdProfessionnel(String idProfessionnel);
    
    List<Affectation> findByIdEtablissement(String idEtablissement);
}

