/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.ressources;

import com.base.frame.account.dto.UtilisateurDTO;
import com.base.frame.account.iservice.IUtilisateurService;
import com.base.frame.carnet.sante.dtos.AntecedentPatientDTO;
import com.base.frame.carnet.sante.entities.Antecedant;
import com.base.frame.carnet.sante.entities.AntecedentPatient;
import com.base.frame.carnet.sante.entities.CategorieAntecedent;
import com.base.frame.carnet.sante.entities.TypeAntecedant;
import com.base.frame.carnet.sante.iservices.IAntecedentPatientService;
import com.base.frame.carnet.sante.repositories.AntecedantRepository;
import com.base.frame.carnet.sante.repositories.AntecedentPatientRepository;
import com.base.frame.carnet.sante.repositories.CategorieAntecedentRepository;
import com.base.frame.carnet.sante.repositories.TypeAntecedentRepository;
import com.base.frame.socle.core.ISecurityUtils;
import com.base.frame.socle.utils.Constants;
import com.base.frame.socle.utils.validators.MessageSourceKV;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bouchara
 */
@RestController
@RequestMapping(path = "/api/antecedent")
public class AntecedentRessource {

    @Autowired
    private CategorieAntecedentRepository categorieAntecedentRepository;

    @Autowired
    private TypeAntecedentRepository typeAntecedentRepository;

    @Autowired
    private AntecedantRepository antecedantRepository;

    @Autowired
    private AntecedentPatientRepository antecedentPatientRepository;

    @Autowired
    private IAntecedentPatientService antecedentPatientService;

    @Autowired
    private ISecurityUtils securityUtils;

    @Autowired
    private IUtilisateurService utilisateurService;

    @Autowired
    private MessageSourceKV messageSource;

    /**
     * Récupère toutes les catégories d'antécédents
     * @return Liste des catégories d'antécédents
     */
    @RequestMapping(value = "/listCategoriesAntecedent", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listCategoriesAntecedent() {
        HashMap<String, Object> model = new HashMap<>();

        List<CategorieAntecedent> listCategoriesAntecedent = categorieAntecedentRepository.findAll();

        // Utiliser le même nom de clé que dans ConsultationRessource pour la compatibilité
        model.put("listCategoriesAntecedent", listCategoriesAntecedent);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listCategoriesAntecedent " + listCategoriesAntecedent.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Récupère tous les types d'antécédents par catégorie
     * @param idCategorie ID de la catégorie
     * @return Liste des types d'antécédents
     */
    @RequestMapping(value = "/listTypesAntecedentByCategorie", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listTypesAntecedentByCategorie(
            @RequestParam(value = "idCategorie", required = false) String idCategorie) {
        HashMap<String, Object> model = new HashMap<>();

        List<TypeAntecedant> listTypesAntecedent;

        if (idCategorie != null && !idCategorie.isEmpty()) {
            listTypesAntecedent = typeAntecedentRepository.findByIdCategorieAntecedent(idCategorie);
        } else {
            listTypesAntecedent = typeAntecedentRepository.findAll();
        }

        // Utiliser le même nom de clé que dans ConsultationRessource pour la compatibilité
        model.put("listTypesAntecedent", listTypesAntecedent);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listTypesAntecedentByCategorie " + listTypesAntecedent.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Récupère tous les antécédents par type
     * @param idTypeAntecedent ID du type d'antécédent
     * @return Liste des antécédents
     */
    @RequestMapping(value = "/listAntecedentsByType", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listAntecedentsByType(
            @RequestParam(value = "idTypeAntecedent", required = false) String idTypeAntecedent) {
        HashMap<String, Object> model = new HashMap<>();

        List<Antecedant> listAntecedents;

        if (idTypeAntecedent != null && !idTypeAntecedent.isEmpty()) {
            listAntecedents = antecedantRepository.findByTypeAntecedent(idTypeAntecedent);
        } else {
            listAntecedents = antecedantRepository.findAll();
        }

        model.put("listAntecedents", listAntecedents);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listAntecedentsByType " + listAntecedents.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Récupère tous les antécédents d'un patient
     * @param idPatient ID du patient
     * @return Liste des antécédents du patient
     */
    @RequestMapping(value = "/listAntecedentsPatient", method = RequestMethod.GET)
    public ResponseEntity<List<HashMap<String, Object>>> listAntecedentsPatient(
            @RequestParam(value = "idPatient", required = true) String idPatient) {

        List<HashMap<String, Object>> antecedents = new ArrayList<>();

        List<AntecedentPatient> listAntecedentsPatient = antecedentPatientRepository.findByIdPatient(idPatient);

        for (AntecedentPatient ap : listAntecedentsPatient) {
            HashMap<String, Object> antecedent = new HashMap<>();
            antecedent.put("id", ap.getId());
            antecedent.put("description", ap.getDescription());
            antecedent.put("dateDebut", ap.getDateDebut());
            antecedent.put("dateFin", ap.getDateFin());
            antecedent.put("traitementSuivi", ap.getTraitementSuivi());
            antecedent.put("statut", ap.getStatut());

            // Charger l'objet Antecedent complet
            if (ap.getAntecedent() != null && !ap.getAntecedent().isEmpty()) {
                Antecedant antecedantObj = antecedantRepository.findById(ap.getAntecedent()).orElse(null);
                antecedent.put("antecedent", antecedantObj);
            } else {
                antecedent.put("antecedent", null);
            }

            // Charger l'objet TypeAntecedent complet
            if (ap.getTypeAntecedent() != null && !ap.getTypeAntecedent().isEmpty()) {
                TypeAntecedant typeAntecedantObj = typeAntecedentRepository.findById(ap.getTypeAntecedent()).orElse(null);
                antecedent.put("typeAntecedent", typeAntecedantObj);
            } else {
                antecedent.put("typeAntecedent", null);
            }

            // Charger l'objet CategorieAntecedent complet
            if (ap.getCategorieAntecedent() != null && !ap.getCategorieAntecedent().isEmpty()) {
                CategorieAntecedent categorieAntecedentObj = categorieAntecedentRepository.findById(ap.getCategorieAntecedent()).orElse(null);
                antecedent.put("categorieAntecedent", categorieAntecedentObj);
            } else {
                antecedent.put("categorieAntecedent", null);
            }

            antecedents.add(antecedent);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listAntecedentsPatient pour patient " + idPatient + " : " + antecedents.size());

        return ResponseEntity.accepted().headers(headers).body(antecedents);
    }

    /**
     * Récupère l'historique des actions sur les antécédents d'un patient
     * @param idPatient ID du patient
     * @return Liste de l'historique
     */
    @RequestMapping(value = "/historique", method = RequestMethod.GET)
    public ResponseEntity<List<HashMap<String, Object>>> getHistorique(
            @RequestParam(value = "idPatient", required = true) String idPatient) {

        List<HashMap<String, Object>> historique = new ArrayList<>();

        // Récupérer l'utilisateur connecté
        Optional<String> username = securityUtils.getCurrentUserLogin();
        HashMap<String, Object> utilisateurConnecte = null;

        if (username.isPresent() && utilisateurService.findUtilisateurUsername(username.get()).isPresent()) {
            String userId = utilisateurService.findUtilisateurUsername(username.get()).get().getId();
            UtilisateurDTO userDTO = utilisateurService.getUtilisateur(userId);

            if (userDTO != null) {
                utilisateurConnecte = new HashMap<>();
                utilisateurConnecte.put("nom", userDTO.getLastName() != null ? userDTO.getLastName() : "");
                utilisateurConnecte.put("prenom", userDTO.getFirstName() != null ? userDTO.getFirstName() : "");
                utilisateurConnecte.put("email", userDTO.getEmail() != null ? userDTO.getEmail() : "");
                //utilisateurConnecte.put("telephone", userDTO.getTel()!= null ? userDTO.getPhoneNumber() : "");
                utilisateurConnecte.put("specialite", "Cardiologue"); // TODO: Récupérer depuis le profil
                utilisateurConnecte.put("etablissement", "CHU Lomé"); // TODO: Récupérer depuis le profil
            }
        }

        // Récupérer les antécédents du patient pour créer l'historique
        List<AntecedentPatient> listAntecedentsPatient = antecedentPatientRepository.findByIdPatient(idPatient);

        for (AntecedentPatient ap : listAntecedentsPatient) {
            HashMap<String, Object> entry = new HashMap<>();

            // Déterminer le type d'action (pour l'instant, on considère tout comme "add")
            // TODO: Implémenter un vrai système d'historique avec table dédiée
            entry.put("type", "add");
            entry.put("action", "Ajout antécédent");

            // Récupérer le libellé de l'antécédent
            String details = ap.getDescription();
            if (ap.getAntecedent() != null && !ap.getAntecedent().isEmpty()) {
                Antecedant antecedantObj = antecedantRepository.findById(ap.getAntecedent()).orElse(null);
                if (antecedantObj != null) {
                    details = antecedantObj.getLibelle();
                }
            }
            entry.put("details", details);

            // Date de l'action (utiliser dateDebut comme approximation)
            entry.put("dateAction", ap.getDateDebut() != null ? ap.getDateDebut() : Instant.now());

            // Ajouter les informations du professionnel (utilisateur connecté)
            if (utilisateurConnecte != null) {
                entry.put("professionnel", utilisateurConnecte);
            }

            historique.add(entry);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== getHistorique pour patient " + idPatient + " : " + historique.size() + " entrées");

        return ResponseEntity.accepted().headers(headers).body(historique);
    }

    /**
     * Sauvegarde ou modifie un antécédent
     * @param antecedentDTO Données de l'antécédent
     * @return Résultat de la sauvegarde
     */
    @RequestMapping(value = "/saveAntecedent", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> saveAntecedent(
            @RequestBody AntecedentPatientDTO antecedentDTO) {

        HashMap<String, Object> model = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        try {
            // Utiliser le service pour sauvegarder
            AntecedentPatientDTO result = this.antecedentPatientService.saveAntecedentPatient(antecedentDTO);

            model.put("success", true);
            model.put("antecedent", result);

            headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));

            return ResponseEntity.accepted().headers(headers).body(model);
        } catch (Exception e) {
            System.err.println("======================== Erreur saveAntecedent: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("error", e.getMessage());

            headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
            headers.add("X-nframe-alert", "Erreur lors de l'enregistrement de l'antécédent");

            return ResponseEntity.badRequest().headers(headers).body(model);
        }
    }

    /**
     * Supprime un antécédent
     * @param id ID de l'antécédent à supprimer
     * @return Résultat de la suppression
     */
    @RequestMapping(value = "/deleteAntecedent", method = RequestMethod.DELETE)
    public ResponseEntity<HashMap<String, Object>> deleteAntecedent(
            @RequestParam(value = "id", required = true) String id) {

        HashMap<String, Object> model = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        try {
            // Utiliser le service pour supprimer
            this.antecedentPatientService.deleteAntecedentPatient(id);

            model.put("success", true);
            headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));

            return ResponseEntity.accepted().headers(headers).body(model);
        } catch (Exception e) {
            System.err.println("======================== Erreur deleteAntecedent: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("error", e.getMessage());

            headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
            headers.add("X-nframe-alert", "Erreur lors de la suppression de l'antécédent");

            return ResponseEntity.badRequest().headers(headers).body(model);
        }
    }
}

