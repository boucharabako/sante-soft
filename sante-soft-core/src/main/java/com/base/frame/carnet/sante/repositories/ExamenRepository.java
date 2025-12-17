package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.Examen;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository pour Examen
 * @author Bouchara
 */
@Repository
public interface ExamenRepository extends JpaRepository<Examen, String> {
    
    /**
     * Trouver tous les examens d'une consultation
     * @param idConsultation ID de la consultation
     * @return Liste des examens
     */
    List<Examen> findByIdConsultation(String idConsultation);
}

