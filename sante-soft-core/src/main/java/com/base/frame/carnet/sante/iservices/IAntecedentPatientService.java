package com.base.frame.carnet.sante.iservices;

import com.base.frame.carnet.sante.dtos.AntecedentPatientDTO;
import com.base.frame.carnet.sante.entities.AntecedentPatient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interface du service pour AntecedentPatient
 * @author Bouchara
 */
public interface IAntecedentPatientService {

    /**
     * Sauvegarder ou mettre à jour un antécédent patient
     * @param dto
     * @return
     */
    AntecedentPatientDTO saveAntecedentPatient(AntecedentPatientDTO dto);

    /**
     * Récupérer un antécédent patient par son ID
     * @param id
     * @return
     */
    AntecedentPatientDTO getAntecedentPatient(String id);

    /**
     * Trouver un antécédent patient par son ID (entité)
     * @param id
     * @return
     */
    Optional<AntecedentPatient> findAntecedentPatientById(String id);

    /**
     * Recherche paginée des antécédents d'un patient
     * @param idPatient
     * @param categorieAntecedent
     * @param typeAntecedent
     * @param motCle
     * @param pageRequest
     * @return
     */
    Page<AntecedentPatientDTO> findBySpecTerm(String idPatient, String categorieAntecedent,
            String typeAntecedent, String motCle, Pageable pageRequest);

    /**
     * Récupérer tous les antécédents d'un patient
     * @param idPatient
     * @return
     */
    List<AntecedentPatientDTO> findAntecedentsByPatient(String idPatient);

    /**
     * Supprimer un antécédent patient
     * @param id
     */
    void deleteAntecedentPatient(String id);
}

