/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.ressources;

import com.base.frame.account.core.repository.UtilisateurRepository;
import com.base.frame.account.entity.Utilisateur;
import com.base.frame.carnet.sante.dtos.ConsultationDTO;
import com.base.frame.carnet.sante.dtos.EtablissementDTO;
import com.base.frame.carnet.sante.dtos.PatientDTO;
import com.base.frame.carnet.sante.dtos.PrescriptionDTO;
import com.base.frame.carnet.sante.dtos.ProfessionnelSanteDTO;
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
import com.base.frame.carnet.sante.services.AllergyCheckService;
import com.base.frame.carnet.sante.services.ConsultationService;
import com.base.frame.carnet.sante.services.FileStorageService;
import com.base.frame.socle.utils.Constants;
import com.base.frame.socle.utils.exceptions.ObjectValidationException;
import com.base.frame.socle.utils.validators.MessageSourceKV;
import java.io.ByteArrayOutputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import net.sf.dynamicreports.report.builder.DynamicReports.*;

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

    @Autowired
    private MessageSourceKV messageSource;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AllergyCheckService allergyCheckService;

    @Autowired
    private com.base.frame.carnet.sante.services.PatientService patientService;

    @Autowired
    private com.base.frame.carnet.sante.services.ProfessionnelSanteService professionnelSanteService;

    @Autowired
    private com.base.frame.carnet.sante.services.EtablissementService etablissementService;

    @Autowired
    private com.base.frame.carnet.sante.services.AffectationService affectationService;

    /**
     * Récupère tous les types d'observations avec leurs unités et valeurs
     * min/max
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
     *
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
            Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByUsername(currentUserId);

            System.out.println("======================== Enregistrement consultation pour patient: " + consultationDTO.getIdPatient());
            System.out.println("   Professionnel: " + utilisateur.get().getId());
            System.out.println("   Type consultation: " + consultationDTO.getTypeConsultation());
            System.out.println("   Nombre de prescriptions: " + (consultationDTO.getPrescriptions() != null ? consultationDTO.getPrescriptions().size() : 0));
            System.out.println("   Nombre d'examens: " + (consultationDTO.getExamens() != null ? consultationDTO.getExamens().size() : 0));

            // Enregistrer la consultation
            ConsultationDTO savedConsultation = consultationService.enregistrerConsultation(consultationDTO, utilisateur.get().getId());

            model.put("success", true);
            model.put("consultationId", savedConsultation.getId());
            model.put("consultation", savedConsultation);

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
            headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));

            return ResponseEntity.accepted().headers(headers).body(model);

        } catch (ObjectValidationException e) {
            // Relancer l'exception pour qu'elle soit gérée par ApiExceptionHandler
            System.err.println(" Erreur de validation: " + e.getCode());
            throw e;
        } catch (Exception e) {
            System.err.println(" Erreur lors de l'enregistrement de la consultation: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur lors de l'enregistrement: " + e.getMessage());

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
            headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_FAILD_MSG_CODE, new String[]{}));

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(model);
        }
    }

    /**
     * Récupérer toutes les consultations d'un patient
     *
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

            System.out.println(" " + consultations.size() + "  " + consultations.toString() + " consultations trouvées");

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
     *
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

                System.out.println(" Consultation trouvée avec "
                        + (consultation.getPrescriptions() != null ? consultation.getPrescriptions().size() : 0) + " prescriptions, "
                        + (consultation.getExamens() != null ? consultation.getExamens().size() : 0) + " examens, "
                        + (consultation.getObservations() != null ? consultation.getObservations().size() : 0) + " observations");
            } else {
                model.put("success", false);
                model.put("message", "Consultation non trouvée");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println(" Erreur lors de la récupération de la consultation: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    /**
     * Récupérer toutes les consultations du professionnel connecté
     *
     * @return Liste des consultations
     */
    @RequestMapping(value = "/listConsultationsByProfessionnel", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listConsultationsByProfessionnel() {
        HashMap<String, Object> model = new HashMap<>();

        try {
            // Récupérer l'utilisateur connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserId = authentication.getName();
            Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByUsername(currentUserId);

            System.out.println("======================== Liste consultations pour professionnel: " + utilisateur.get().getId());

            List<ConsultationDTO> consultations = consultationService.getConsultationsByProfessionnel(utilisateur.get().getId());

            model.put("listConsultations", consultations);
            model.put("success", true);

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            System.out.println(" " + consultations.size() + " consultations trouvées");

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
     * Récupérer tous les types de consultation
     *
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

    /**
     * Vérifier les allergies du patient pour une liste de médicaments
     *
     * @param requestData Map contenant idPatient et medicaments
     * @return Liste des alertes d'allergie
     */
    @RequestMapping(value = "/checkAllergies", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> checkAllergies(@RequestBody HashMap<String, Object> requestData) {
        HashMap<String, Object> model = new HashMap<>();

        try {
            String idPatient = (String) requestData.get("idPatient");
            List<String> medicaments = (List<String>) requestData.get("medicaments");

            System.out.println(" Vérification des allergies pour patient: " + idPatient);
            System.out.println(" Médicaments à vérifier: " + medicaments);

            List<String> alertes = allergyCheckService.checkAllergies(idPatient, medicaments);

            model.put("alertes", alertes);
            model.put("success", true);

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println(" Erreur lors de la vérification des allergies: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur lors de la vérification des allergies");
            model.put("alertes", new ArrayList<>());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    /**
     * Télécharger un fichier d'examen
     *
     * @param cheminFichier Chemin relatif du fichier
     * @return Fichier en bytes
     */
    @RequestMapping(value = "/telechargerFichierExamen", method = RequestMethod.GET)
    public ResponseEntity<byte[]> telechargerFichierExamen(
            @RequestParam(value = "chemin") String cheminFichier) {

        try {
            System.out.println(" Téléchargement du fichier: " + cheminFichier);

            // Lire le fichier depuis le disque
            byte[] fichierBytes = fileStorageService.lireFichier(cheminFichier);

            if (fichierBytes == null) {
                System.err.println(" Fichier non trouvé: " + cheminFichier);
                return ResponseEntity.notFound().build();
            }

            // Déterminer le type MIME depuis le chemin
            String typeMime = determinerTypeMimeDepuisChemin(cheminFichier);

            // Extraire le nom du fichier
            String nomFichier = cheminFichier.substring(cheminFichier.lastIndexOf("/") + 1);

            // Préparer les headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(typeMime));
            headers.setContentLength(fichierBytes.length);
            headers.set("Content-Disposition", "inline; filename=\"" + nomFichier + "\"");

            System.out.println(" Fichier téléchargé: " + nomFichier + " (" + fichierBytes.length + " octets)");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fichierBytes);

        } catch (Exception e) {
            System.err.println(" Erreur lors du téléchargement du fichier: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Déterminer le type MIME depuis le chemin du fichier
     */
    private String determinerTypeMimeDepuisChemin(String cheminFichier) {
        if (cheminFichier == null) {
            return "application/octet-stream";
        }

        String extension = cheminFichier.substring(cheminFichier.lastIndexOf(".") + 1).toLowerCase();
        switch (extension) {
            case "pdf":
                return "application/pdf";
            case "jpg":
            case "jpeg":
                return "image/jpeg";
            case "png":
                return "image/png";
            case "gif":
                return "image/gif";
            case "doc":
                return "application/msword";
            case "docx":
                return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "xls":
                return "application/vnd.ms-excel";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            default:
                return "application/octet-stream";
        }
    }

    /**
     * Récupérer les consultations d'un professionnel avec pagination
     *
     * @param motCle Mot-clé de recherche
     * @param typeConsultation Type de consultation
     * @param dateDebut Date de début (timestamp en millisecondes)
     * @param dateFin Date de fin (timestamp en millisecondes)
     * @param page Numéro de page (commence à 0)
     * @param size Taille de la page
     * @return Page de consultations
     */
    @RequestMapping(value = "/paginateConsultationsByProfessionnel", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> paginateConsultationsByProfessionnel(
            @RequestParam(required = false, name = "mc") String motCle,
            @RequestParam(required = false, name = "typeConsultation") String typeConsultation,
            @RequestParam(required = false, name = "dateDebut") Long dateDebutMillis,
            @RequestParam(required = false, name = "dateFin") Long dateFinMillis,
            @RequestParam(required = false, name = "page") int page,
            @RequestParam(required = false, name = "size") int size) {

        HashMap<String, Object> model = new HashMap<>();

        try {
            // Récupérer l'utilisateur connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserId = authentication.getName();
            Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByUsername(currentUserId);

            if (!utilisateur.isPresent()) {
                model.put("success", false);
                model.put("message", "Utilisateur non trouvé");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(model);
            }

            String professionnelId = utilisateur.get().getId();
            System.out.println("======================== Pagination consultations pour professionnel: " + professionnelId);
            System.out.println(" Paramètres - Page: " + page + ", Size: " + size + ", MC: " + motCle);

            // Convertir les timestamps en Instant
            Instant dateDebut = dateDebutMillis != null ? Instant.ofEpochMilli(dateDebutMillis) : null;
            Instant dateFin = dateFinMillis != null ? Instant.ofEpochMilli(dateFinMillis) : null;

            // Créer la pagination
            Pageable pageRequest = PageRequest.of(page, size);

            // Récupérer les consultations paginées
            Page<ConsultationDTO> listConsultations = consultationService.findConsultationsByProfessionnel(
                    professionnelId, motCle, typeConsultation, dateDebut, dateFin, pageRequest);

            model.put("listConsultations", listConsultations);
            model.put("success", true);

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            System.out.println(" " + listConsultations.getTotalElements() + " consultations trouvées (page "
                    + (page + 1) + "/" + listConsultations.getTotalPages() + ")");

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println(" Erreur lors de la pagination des consultations: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    /**
     * Récupère les statistiques de consultations pour le professionnel connecté
     *
     * @return Statistiques (aujourdhui, semaine, mois)
     */
    @RequestMapping(value = "/statistiques", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Long>> getStatistiques() {
        try {
            // Récupérer l'utilisateur connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUserId = authentication.getName();
            Optional<Utilisateur> utilisateur = this.utilisateurRepository.findByUsername(currentUserId);

            if (!utilisateur.isPresent()) {
                System.err.println(" Utilisateur non trouvé pour les statistiques");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new HashMap<>());
            }

            String professionnelId = utilisateur.get().getId();
            System.out.println(" Calcul des statistiques pour professionnel: " + professionnelId);

            Map<String, Long> stats = consultationService.getStatistiquesConsultations(professionnelId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            System.err.println(" Erreur lors du calcul des statistiques: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HashMap<>());
        }
    }

    /**
     * Exporter l'ordonnance (prescriptions) en PDF
     */
    @RequestMapping(value = "/exportPrescription", method = RequestMethod.GET)
    public ResponseEntity<byte[]> exportPrescription(@RequestParam String id) {
        try {
            System.out.println(" Export ordonnance pour consultation: " + id);

            // 1. Récupérer la consultation avec les prescriptions
            ConsultationDTO consultation = consultationService.getConsultationById(id);
            if (consultation == null || consultation.getPrescriptions() == null || consultation.getPrescriptions().isEmpty()) {
                System.err.println(" Consultation non trouvée ou sans prescriptions");
                return ResponseEntity.notFound().build();
            }

            // 2. Récupérer les informations du patient
            PatientDTO patient = patientService.getPatient(consultation.getIdPatient());
            if (patient == null) {
                System.err.println(" Patient non trouvé");
                return ResponseEntity.notFound().build();
            }

            // 3. Récupérer les informations du professionnel
            ProfessionnelSanteDTO professionnel = professionnelSanteService.getProfessionnelSante(consultation.getIdProfessionnelSante());
            if (professionnel == null) {
                System.err.println(" Professionnel non trouvé");
                return ResponseEntity.notFound().build();
            }

            // 4. Récupérer l'affectation active du professionnel (contient établissement et spécialité)
            java.util.List<com.base.frame.carnet.sante.dtos.AffectationDTO> affectations
                    = affectationService.getAffectationsByProfessionnel(consultation.getIdProfessionnelSante());

            com.base.frame.carnet.sante.dtos.AffectationDTO affectationActive = null;
            String etablissementLibelle = "Centre de Santé";
            String etablissementAdresse = "";
            String specialiteLibelle = "";

            if (affectations != null && !affectations.isEmpty()) {
                // Prendre la première affectation (la plus récente, triée par dateDebut DESC)
                affectationActive = affectations.get(0);

                // Récupérer les libellés depuis l'affectation
                if (affectationActive.getEtablissementLibelle() != null) {
                    etablissementLibelle = affectationActive.getEtablissementLibelle();
                }
                if (affectationActive.getSpecialiteLibelle() != null) {
                    specialiteLibelle = affectationActive.getSpecialiteLibelle();
                }

                // Récupérer l'adresse de l'établissement
                if (affectationActive.getIdEtablissement() != null) {
                    com.base.frame.carnet.sante.dtos.EtablissementDTO etab
                            = etablissementService.getEtablissement(affectationActive.getIdEtablissement());
                    if (etab != null && etab.getAdresse() != null) {
                        etablissementAdresse = etab.getAdresse();
                    }
                }
            }

            // 5. Formater la date de consultation
            String dateFormatee = "";
            if (consultation.getDateConsultation() != null && !consultation.getDateConsultation().isEmpty()) {
                try {
                    java.time.Instant instant = java.time.Instant.parse(consultation.getDateConsultation());
                    java.time.LocalDateTime dateTime = java.time.LocalDateTime.ofInstant(
                            instant, java.time.ZoneId.systemDefault());
                    dateFormatee = dateTime.format(
                            java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (Exception e) {
                    dateFormatee = consultation.getDateConsultation();
                }
            }

            // 6. Générer le PDF avec DynamicReports - Layout professionnel amélioré
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            // Styles personnalisés
            net.sf.dynamicreports.report.builder.style.StyleBuilder headerStyle
                    = net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                            .bold()
                            .setFontSize(14)
                            .setBackgroundColor(new java.awt.Color(41, 128, 185))
                            .setForegroundColor(java.awt.Color.WHITE)
                            .setPadding(5);

            net.sf.dynamicreports.report.builder.style.StyleBuilder sectionTitleStyle
                    = net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                            .bold()
                            .setFontSize(11)
                            .setBackgroundColor(new java.awt.Color(236, 240, 241))
                            .setPadding(3)
                            .setBorder(net.sf.dynamicreports.report.builder.DynamicReports.stl.pen1Point());

            // Créer le rapport avec un design professionnel
            net.sf.dynamicreports.report.builder.DynamicReports.report()
                    .setTemplate(net.sf.dynamicreports.report.builder.DynamicReports.template()
                            .setPageMargin(net.sf.dynamicreports.report.builder.DynamicReports.margin(30)))
                    .title(
                            net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalList(
                                    // En-tête avec logo et établissement
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.horizontalList(
                                            net.sf.dynamicreports.report.builder.DynamicReports.cmp.image(
                                                    getClass().getResourceAsStream("/static/images/logo-sante.png"))
                                                    .setFixedDimension(70, 70),
                                            net.sf.dynamicreports.report.builder.DynamicReports.cmp.horizontalGap(15),
                                            net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalList(
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text(etablissementLibelle)
                                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                                    .bold().setFontSize(18).setForegroundColor(new java.awt.Color(41, 128, 185))),
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(3),
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text(etablissementAdresse)
                                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                                    .setFontSize(10).setForegroundColor(new java.awt.Color(127, 140, 141)))
                                            )
                                    ),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(10),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.line()
                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                    .setLinePen(net.sf.dynamicreports.report.builder.DynamicReports.stl.pen2Point()
                                                            .setLineColor(new java.awt.Color(41, 128, 185)))),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(15),
                                    // Titre principal
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text("ORDONNANCE MÉDICALE")
                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                    .bold().setFontSize(20)
                                                    .setForegroundColor(new java.awt.Color(44, 62, 80))
                                                    .setHorizontalTextAlignment(
                                                            net.sf.dynamicreports.report.constant.HorizontalTextAlignment.CENTER)),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(20),
                                    // Section Médecin
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text("INFORMATIONS DU MÉDECIN")
                                            .setStyle(sectionTitleStyle),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(8),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.horizontalList(
                                            net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalList(
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text("Dr. "
                                                            + professionnel.getFirstName() + " " + professionnel.getLastName())
                                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                                    .bold().setFontSize(13).setForegroundColor(new java.awt.Color(52, 73, 94))),
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(4),
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text(
                                                            "Spécialité: " + (!specialiteLibelle.isEmpty() ? specialiteLibelle : "Médecine Générale"))
                                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                                    .setFontSize(10).setForegroundColor(new java.awt.Color(127, 140, 141))),
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text(
                                                            "Tél: " + (professionnel.getTel() != null ? professionnel.getTel() : "N/A"))
                                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                                    .setFontSize(10).setForegroundColor(new java.awt.Color(127, 140, 141))),
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text(
                                                            "Email: " + (professionnel.getEmail() != null ? professionnel.getEmail() : "N/A"))
                                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                                    .setFontSize(10).setForegroundColor(new java.awt.Color(127, 140, 141)))
                                            )
                                    ),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(15),
                                    // Section Patient
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text("INFORMATIONS DU PATIENT")
                                            .setStyle(sectionTitleStyle),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(8),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.horizontalList(
                                            net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalList(
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text(
                                                            patient.getFirstName() + " " + patient.getLastName())
                                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                                    .bold().setFontSize(12).setForegroundColor(new java.awt.Color(52, 73, 94))),
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(4),
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text(
                                                            "Date de consultation: " + (!dateFormatee.isEmpty() ? dateFormatee : "N/A"))
                                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                                    .setFontSize(10).setForegroundColor(new java.awt.Color(127, 140, 141)))
                                            )
                                    ),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(20),
                                    // Section Prescriptions
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text("PRESCRIPTIONS")
                                            .setStyle(sectionTitleStyle),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(10)
                            )
                    )
                    .columns(
                            net.sf.dynamicreports.report.builder.DynamicReports.col.column("Médicament", "medicament",
                                    net.sf.dynamicreports.report.builder.DynamicReports.type.stringType())
                                    .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                            .setFontSize(10).setPadding(5)),
                            net.sf.dynamicreports.report.builder.DynamicReports.col.column("Posologie", "posologie",
                                    net.sf.dynamicreports.report.builder.DynamicReports.type.stringType())
                                    .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                            .setFontSize(10).setPadding(5)),
                            net.sf.dynamicreports.report.builder.DynamicReports.col.column("Durée", "duree",
                                    net.sf.dynamicreports.report.builder.DynamicReports.type.stringType())
                                    .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                            .setFontSize(10).setPadding(5))
                    )
                    .setColumnTitleStyle(headerStyle)
                    .setColumnStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                            .setBorder(net.sf.dynamicreports.report.builder.DynamicReports.stl.pen1Point()
                                    .setLineColor(new java.awt.Color(189, 195, 199))))
                    .setDetailEvenRowStyle(
                            net.sf.dynamicreports.report.builder.DynamicReports.stl.simpleStyle()
                                    .setBackgroundColor(new java.awt.Color(250, 250, 250))
                    )
                    .setDataSource(createPrescriptionDataSource(consultation.getPrescriptions()))
                    .pageFooter(
                            net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalList(
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(30),
                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.horizontalList(
                                            net.sf.dynamicreports.report.builder.DynamicReports.cmp.filler().setFixedWidth(300),
                                            net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalList(
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.text("Signature et cachet du médecin")
                                                            .setStyle(net.sf.dynamicreports.report.builder.DynamicReports.stl.style()
                                                                    .setFontSize(9).italic()
                                                                    .setHorizontalTextAlignment(
                                                                            net.sf.dynamicreports.report.constant.HorizontalTextAlignment.CENTER)),
                                                    net.sf.dynamicreports.report.builder.DynamicReports.cmp.verticalGap(50)
                                            ).setFixedWidth(200)
                                    )
                            )
                    )
                    .toPdf(outputStream);

            // 6. Préparer le nom du fichier
            String dateJour = java.time.LocalDate.now().format(
                    java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
            String numeroCarnet = patient.getNumeroCarnet() != null ? patient.getNumeroCarnet() : "000";
            String filename = "ordonnance_" + dateJour + "_" + numeroCarnet + ".pdf";

            // 7. Retourner le PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

            System.out.println(" Ordonnance générée: " + filename);
            return new ResponseEntity<>(outputStream.toByteArray(), headers, HttpStatus.OK);

        } catch (Exception e) {
            System.err.println(" Erreur lors de l'export de l'ordonnance: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Créer une source de données pour les prescriptions
     */
    private net.sf.jasperreports.engine.JRDataSource createPrescriptionDataSource(
            java.util.List<PrescriptionDTO> prescriptions) {

        java.util.List<java.util.Map<String, ?>> data = new java.util.ArrayList<>();

        for (PrescriptionDTO presc : prescriptions) {
            java.util.Map<String, Object> row = new java.util.HashMap<>();
            row.put("medicament", presc.getMedicament());
            row.put("posologie", presc.getPosologie());
            row.put("duree", presc.getDuree() + " jours");
            data.add(row);
        }

        return new net.sf.jasperreports.engine.data.JRMapCollectionDataSource(data);
    }
}
