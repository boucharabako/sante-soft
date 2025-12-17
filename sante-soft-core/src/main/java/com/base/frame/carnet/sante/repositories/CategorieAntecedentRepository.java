package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.CategorieAntecedent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public interface CategorieAntecedentRepository extends JpaRepository<CategorieAntecedent, String> {
    
}

