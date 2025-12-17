/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.ressources;

import com.base.frame.account.core.repository.UtilisateurRepository;
import com.base.frame.account.entity.Utilisateur;
import com.base.frame.carnet.sante.dtos.ConsultationDTO;
import com.base.frame.carnet.sante.entities.CategorieAntecedent;
import com.base.frame.carnet.sante.entities.CategorieConsultation;
import com.base.frame.carnet.sante.entities.ConsultationTypeExamenAutorise;
import com.base.frame.carnet.sante.entities.TypeAntecedant;
import com.base.frame.carnet.sante.entities.TypeConsultation;
import com.base.frame.carnet.sante.entities.TypeObservation;
import com.base.frame.carnet.sante.repositories.CategorieAntecedentRepository;
import com.base.frame.carnet.sante.repositories.CategorieConsultationRepository;
import com.base.frame.carnet.sante.repositories.ConsultationTypeExamenAutoriseRepository;
import com.base.frame.carnet.sante.repositories.TypeAntecedentRepository;
import com.base.frame.carnet.sante.repositories.TypeConsultationRepository;
import com.base.frame.carnet.sante.repositories.TypeObservationRepository;
import com.base.frame.carnet.sante.services.ConsultationService;
import com.base.frame.socle.utils.Constants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping(path = "/api/consultation")
public class ConsultationRessource {

    @Autowired
    private TypeObservationRepository typeObservationRepository;

    @Autowired
    private CategorieAntecedentRepository categorieAntecedentRepository;

    @Autowired
    private TypeAntecedentRepository typeAntecedentRepository;

    @Autowired
    private CategorieConsultationRepository categorieConsultationRepository;

    @Autowired
    private TypeConsultationRepository typeConsultationRepository;

    @Autowired
    private ConsultationTypeExamenAutoriseRepository consultationTypeExamenAutoriseRepository;

    @Autowired
    private ConsultationService consultationService;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    /**
     * Récupère tous les types d'observations avec leurs unités et valeurs min/max
     * @return Liste des types d'observations
     */
    @RequestMapping(value = "/listTypesObservation", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listTypesObservation() {
        HashMap<String, Object> model = new HashMap<>();

        List<TypeObservation> listTypesObservation = typeObservationRepository.findAll();

        model.put("listTypesObservation", listTypesObservation);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listTypesObservation " + listTypesObservation.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Récupère toutes les catégories d'antécédents
     * @return Liste des catégories d'antécédents
     */
    @RequestMapping(value = "/listCategoriesAntecedent", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listCategoriesAntecedent() {
        HashMap<String, Object> model = new HashMap<>();

        List<CategorieAntecedent> listCategoriesAntecedent = categorieAntecedentRepository.findAll();

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

        model.put("listTypesAntecedent", listTypesAntecedent);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listTypesAntecedentByCategorie " + listTypesAntecedent.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Récupère toutes les catégories de consultation
     * @return Liste des catégories de consultation
     */
    @RequestMapping(value = "/listCategoriesConsultation", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listCategoriesConsultation() {
        HashMap<String, Object> model = new HashMap<>();

        List<CategorieConsultation> listCategoriesConsultation = categorieConsultationRepository.findAll();

        model.put("listCategoriesConsultation", listCategoriesConsultation);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listCategoriesConsultation " + listCategoriesConsultation.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Récupère tous les types de consultation par catégorie
     * @param idCategorie ID de la catégorie
     * @return Liste des types de consultation
     */
    @RequestMapping(value = "/listTypesConsultationByCategorie", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listTypesConsultationByCategorie(
            @RequestParam(value = "idCategorie", required = false) String idCategorie) {
        HashMap<String, Object> model = new HashMap<>();

        List<TypeConsultation> listTypesConsultation;

        if (idCategorie != null && !idCategorie.isEmpty()) {
            listTypesConsultation = typeConsultationRepository.findByCategorieConsultation(idCategorie);
        } else {
            listTypesConsultation = typeConsultationRepository.findAll();
        }

        model.put("listTypesConsultation", listTypesConsultation);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listTypesConsultation " + listTypesConsultation.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Récupère tous les types d'examen autorisés pour un type de consultation
     * @param idTypeConsultation ID du type de consultation
     * @return Liste des types d'examen autorisés
     */
    @RequestMapping(value = "/listTypesExamenByTypeConsultation", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listTypesExamenByTypeConsultation(
            @RequestParam(value = "idTypeConsultation", required = false) String idTypeConsultation) {
        HashMap<String, Object> model = new HashMap<>();

        List<ConsultationTypeExamenAutorise> listTypesExamen;

        if (idTypeConsultation != null && !idTypeConsultation.isEmpty()) {
            listTypesExamen = consultationTypeExamenAutoriseRepository.findByTypeConsultation(idTypeConsultation);
        } else {
            listTypesExamen = consultationTypeExamenAutoriseRepository.findAll();
        }

        model.put("listTypesExamen", listTypesExamen);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listTypesExamen pour type consultation " + idTypeConsultation + " : " + listTypesExamen.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Enregistrer une consultation avec ses prescriptions et examens
     * @param consultationDTO DTO de la consultation
     * @return Réponse avec l'ID de la consultation créée
     */
    @RequestMapping(value = "/enregistrerConsultation", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> enregistrerConsultation(@RequestBody ConsultationDTO consultationDTO) {
        HashMap<String, Object> model = new HashMap<>();

        try {
            // Récupérer l'utilisateur connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserId = authentication.getName();
            Optional<Utilisateur> utilisateur=this.utilisateurRepository.findByUsername(currentUserId);

            System.out.println("======================== Enregistrement consultation pour patient: " + consultationDTO.getIdPatient());
            System.out.println("   Professionnel: " + utilisateur.get().getId());
            System.out.println("   Type consultation: " + consultationDTO.getTypeConsultation());
            System.out.println("   Nombre de prescriptions: " + (consultationDTO.getPrescriptions() != null ? consultationDTO.getPrescriptions().size() : 0));
            System.out.println("   Nombre d'examens: " + (consultationDTO.getExamens() != null ? consultationDTO.getExamens().size() : 0));

            // Enregistrer la consultation
            ConsultationDTO savedConsultation = consultationService.enregistrerConsultation(consultationDTO, utilisateur.get().getId());

            model.put("success", true);
            model.put("message", "Consultation enregistrée avec succès");
            model.put("consultationId", savedConsultation.getId());
            model.put("consultation", savedConsultation);

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(model);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'enregistrement de la consultation: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur lors de l'enregistrement: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    /**
     * Récupérer toutes les consultations d'un patient
     * @param idPatient ID du patient
     * @return Liste des consultations
     */
    @RequestMapping(value = "/listConsultationsByPatient", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listConsultationsByPatient(
            @RequestParam(value = "idPatient") String idPatient) {
        HashMap<String, Object> model = new HashMap<>();

        try {
            System.out.println("======================== Liste consultations pour patient: " + idPatient);

            List<ConsultationDTO> consultations = consultationService.getConsultationsByPatient(idPatient);

            model.put("listConsultations", consultations);
            model.put("success", true);

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            System.out.println(" " + consultations.size() +"  "+ consultations.toString()+ " consultations trouvées");

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println(" Erreur lors de la récupération des consultations: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur: " + e.getMessage());
            model.put("listConsultations", new ArrayList<>());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    /**
     * Récupérer une consultation par son ID avec tous ses détails
     * @param id ID de la consultation
     * @return Consultation complète
     */
    @RequestMapping(value = "/getConsultation", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getConsultation(
            @RequestParam(value = "id") String id) {
        HashMap<String, Object> model = new HashMap<>();

        try {
            System.out.println("======================== Récupération consultation: " + id);

            ConsultationDTO consultation = consultationService.getConsultationById(id);

            if (consultation != null) {
                model.put("consultation", consultation);
                model.put("success", true);

                System.out.println("✅ Consultation trouvée avec " +
                    (consultation.getPrescriptions() != null ? consultation.getPrescriptions().size() : 0) + " prescriptions, " +
                    (consultation.getExamens() != null ? consultation.getExamens().size() : 0) + " examens, " +
                    (consultation.getObservations() != null ? consultation.getObservations().size() : 0) + " observations");
            } else {
                model.put("success", false);
                model.put("message", "Consultation non trouvée");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération de la consultation: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    /**
     * Récupérer toutes les consultations du professionnel connecté
     * @return Liste des consultations
     */
    @RequestMapping(value = "/listConsultationsByProfessionnel", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listConsultationsByProfessionnel() {
        HashMap<String, Object> model = new HashMap<>();

        try {
            // Récupérer l'utilisateur connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserId = authentication.getName();
            Optional<Utilisateur> utilisateur=this.utilisateurRepository.findByUsername(currentUserId);

            System.out.println("======================== Liste consultations pour professionnel: " + utilisateur.get().getId());

            List<ConsultationDTO> consultations = consultationService.getConsultationsByProfessionnel(utilisateur.get().getId());

            model.put("listConsultations", consultations);
            model.put("success", true);

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            System.out.println("✅ " + consultations.size() + " consultations trouvées");

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des consultations: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur: " + e.getMessage());
            model.put("listConsultations", new ArrayList<>());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    /**
     * Récupérer tous les types de consultation
     * @return Liste des types de consultation
     */
    @RequestMapping(value = "/listTypesConsultation", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listTypesConsultation() {
        HashMap<String, Object> model = new HashMap<>();

        try {
            System.out.println("======================== Liste tous les types de consultation");

            List<TypeConsultation> typesConsultation = typeConsultationRepository.findAll();

            model.put("listTypesConsultation", typesConsultation);
            model.put("success", true);

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            System.out.println("" + typesConsultation.size() + " types de consultation trouvés");

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println(" Erreur lors de la récupération des types: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur: " + e.getMessage());
            model.put("listTypesConsultation", new ArrayList<>());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }
}

