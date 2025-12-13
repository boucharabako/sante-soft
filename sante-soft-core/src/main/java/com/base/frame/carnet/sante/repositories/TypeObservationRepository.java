package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.TypeObservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public interface TypeObservationRepository extends JpaRepository<TypeObservation, String> {
    
}

