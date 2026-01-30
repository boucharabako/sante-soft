/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.CompositionMedicament;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public interface CompositionMedicamentRepository extends JpaRepository<CompositionMedicament, String> {
    
    /**
     * Trouver la composition d'un médicament par son nom
     * @param medicament
     * @return 
     */
    Optional<CompositionMedicament> findByMedicament(String medicament);
}

