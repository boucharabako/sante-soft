/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.TypeAntecedant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public interface TypeAntecedentRepository extends JpaRepository<TypeAntecedant, String> {
    
    /**
     * Trouver tous les types d'antécédents par catégorie
     * @param idCategorieAntecedent
     * @return 
     */
    List<TypeAntecedant> findByIdCategorieAntecedent(String idCategorieAntecedent);
}

