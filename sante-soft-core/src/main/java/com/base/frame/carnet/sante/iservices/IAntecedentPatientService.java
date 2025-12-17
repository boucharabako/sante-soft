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
     * @param currentUserId ID de l'utilisateur connecté
     * @param currentUsername Username de l'utilisateur connecté
     * @return
     */
    AntecedentPatientDTO saveAntecedentPatient(AntecedentPatientDTO dto, String currentUserId, String currentUsername);

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
     * Récupérer tous les antécédents d'un patient (avec mot-clé optionnel)
     * @param idPatient
     * @param mc
     * @return
     */
    List<AntecedentPatientDTO> getAllAntecedentsByPatient(String idPatient, String mc);

    /**
     * Supprimer un antécédent patient
     * @param id
     * @param currentUserId ID de l'utilisateur connecté
     * @param currentUsername Username de l'utilisateur connecté
     */
    void deleteAntecedentPatient(String id, String currentUserId, String currentUsername);
}

