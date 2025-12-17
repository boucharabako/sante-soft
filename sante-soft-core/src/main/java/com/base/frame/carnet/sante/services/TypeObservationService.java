package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.entities.TypeObservation;
import com.base.frame.carnet.sante.repositories.TypeObservationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bouchara
 */
@Service
@Transactional
public class TypeObservationService {

    @Autowired
    private TypeObservationRepository typeObservationRepository;

    /**
     * Récupère tous les types d'observations
     * @return Liste de tous les types d'observations
     */
    public List<TypeObservation> findAll() {
        return typeObservationRepository.findAll();
    }
}

