/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.Etablissement;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement, String> {
    Optional<Etablissement> findByCodeEtablissement(String codeEtablissement);
}

