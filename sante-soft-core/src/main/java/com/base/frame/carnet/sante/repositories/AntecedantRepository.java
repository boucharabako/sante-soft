/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.Antecedant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public interface AntecedantRepository extends JpaRepository<Antecedant, String> {
    
    /**
     * Trouver tous les antécédents par type
     * @param typeAntecedent
     * @return 
     */
    List<Antecedant> findByTypeAntecedent(String typeAntecedent);
}

