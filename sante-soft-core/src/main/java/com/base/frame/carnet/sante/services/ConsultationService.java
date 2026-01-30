package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.daos.ConsultationDAO;
import com.base.frame.carnet.sante.dtos.ConsultationDTO;
import com.base.frame.carnet.sante.dtos.ExamenDTO;
import com.base.frame.carnet.sante.dtos.ObservationDTO;
import com.base.frame.carnet.sante.dtos.PrescriptionDTO;
import com.base.frame.carnet.sante.entities.CategorieConsultation;
import com.base.frame.carnet.sante.entities.Consultation;
import com.base.frame.carnet.sante.entities.Examen;
import com.base.frame.carnet.sante.entities.Observation;
import com.base.frame.carnet.sante.entities.Prescription;
import com.base.frame.carnet.sante.entities.TypeConsultation;
import com.base.frame.carnet.sante.repositories.CategorieConsultationRepository;
import com.base.frame.carnet.sante.repositories.ConsultationRepository;
import com.base.frame.carnet.sante.repositories.ConsultationTypeExamenAutoriseRepository;
import com.base.frame.carnet.sante.repositories.ExamenRepository;
import com.base.frame.carnet.sante.repositories.ObservationRepository;
import com.base.frame.carnet.sante.repositories.PrescriptionRepository;
import com.base.frame.carnet.sante.repositories.TypeConsultationRepository;
import com.base.frame.carnet.sante.repositories.TypeObservationRepository;
import com.base.frame.socle.utils.exceptions.ObjectValidationException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour la gestion des consultations
 * @author Bouchara
 */
@Service
@Transactional
public class ConsultationService {
    
    @Autowired
    private ConsultationRepository consultationRepository;
    
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private TypeConsultationRepository typeConsultationRepository;
    
    @Autowired
    private TypeObservationRepository typeObservationRepository;

    @Autowired
    private CategorieConsultationRepository categorieConsultationRepository;

    @Autowired
    private com.base.frame.carnet.sante.repositories.PatientRepository patientRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private TypeObservationService typeObservationService;

    @Autowired
    private ConsultationTypeExamenAutoriseRepository consultationTypeExamenAutoriseRepository;

    @Autowired
    private ConsultationDAO consultationDAO;

    /**
     * Enregistrer une consultation avec ses prescriptions et examens
     * @param dto DTO de la consultation
     * @param currentUserId ID de l'utilisateur connecté
     * @return DTO de la consultation enregistrée
     */
    public ConsultationDTO enregistrerConsultation(ConsultationDTO dto, String currentUserId) {
        boolean modeEdition = dto.getId() != null && !dto.getId().isEmpty();
        System.out.println("======================== " + (modeEdition ? "Mise à jour" : "Enregistrement") + " de la consultation");
        if (modeEdition) {
            System.out.println("   ID consultation: " + dto.getId());
        }

        // Valider les données de la consultation
        this.controleValidationObjetConsultation(dto);

        // 1. Enregistrer ou mettre à jour la consultation
        Consultation consultation;
        if (modeEdition) {
            // Mode édition : charger la consultation existante
            consultation = consultationRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Consultation non trouvée avec l'ID: " + dto.getId()));
            System.out.println(" Consultation existante chargée");
        } else {
            // Mode création : nouvelle consultation
            consultation = new Consultation();
        }

        consultation.setIdPatient(dto.getIdPatient());
        consultation.setCategorieConsultation(dto.getCategorieConsultation());
        consultation.setTypeConsultation(dto.getTypeConsultation());
        consultation.setIdProfessionnelSante(currentUserId);
        consultation.setMotif(dto.getMotif());
        consultation.setDiagnostic(dto.getDiagnostic());
        consultation.setTraitement(dto.getTraitement());
        consultation.setDateConsultation(Instant.parse(dto.getDateConsultation()));

        consultation = consultationRepository.save(consultation);
        System.out.println("✅ Consultation " + (modeEdition ? "mise à jour" : "enregistrée") + " avec ID: " + consultation.getId());
        
        // 2. Gérer les prescriptions
        if (dto.getPrescriptions() != null && !dto.getPrescriptions().isEmpty()) {
            for (PrescriptionDTO prescDto : dto.getPrescriptions()) {
                Prescription prescription;
                if (prescDto.getId() != null && !prescDto.getId().isEmpty()) {
                    // Mise à jour d'une prescription existante
                    prescription = prescriptionRepository.findById(prescDto.getId())
                        .orElse(new Prescription());
                } else {
                    // Nouvelle prescription
                    prescription = new Prescription();
                }

                prescription.setIdConsultation(consultation.getId());
                prescription.setMedicament(prescDto.getMedicament());
                prescription.setPosologie(prescDto.getPosologie());
                prescription.setDuree(prescDto.getDuree());

                prescriptionRepository.save(prescription);
            }
            System.out.println(" " + dto.getPrescriptions().size() + " prescriptions " + (modeEdition ? "mises à jour" : "enregistrées"));
        }

        // 3. Gérer les examens
        if (dto.getExamens() != null && !dto.getExamens().isEmpty()) {
            for (ExamenDTO examenDto : dto.getExamens()) {
                Examen examen;
                if (examenDto.getId() != null && !examenDto.getId().isEmpty()) {
                    // Mise à jour d'un examen existant
                    examen = examenRepository.findById(examenDto.getId())
                        .orElse(new Examen());
                } else {
                    // Nouvel examen
                    examen = new Examen();
                }

                examen.setIdConsultation(consultation.getId());
                examen.setTypeExamen(examenDto.getTypeExamen());
                examen.setResultat(examenDto.getResultat());
                examen.setCommentaire(examenDto.getCommentaire());

                // Gérer le fichier joint (si présent)
                if (examenDto.getFichierJoint() != null && !examenDto.getFichierJoint().isEmpty()) {
                    try {
                        // Supprimer l'ancien fichier si on met à jour
                        if (examen.getCheminFichier() != null && !examen.getCheminFichier().isEmpty()) {
                            fileStorageService.supprimerFichier(examen.getCheminFichier());
                            System.out.println(" Ancien fichier supprimé: " + examen.getCheminFichier());
                        }

                        // Déterminer le type MIME depuis le nom de fichier ou le Base64
                        String typeMime = determinerTypeMime(examenDto.getFichierJoint(), examenDto.getNomFichier());

                        // Récupérer les informations du patient pour le nom du fichier
                        Optional<com.base.frame.carnet.sante.entities.Patient> patientOpt =
                            patientRepository.findById(consultation.getIdPatient());
                        String numeroCarnet = patientOpt.isPresent() ? patientOpt.get().getNumeroCarnet() : "UNKNOWN";
                        String nomPatient = patientOpt.isPresent() ? patientOpt.get().getLastName() : "UNKNOWN";

                        // Sauvegarder le nouveau fichier sur le disque
                        String cheminRelatif = fileStorageService.sauvegarderFichier(
                            examenDto.getFichierJoint(),
                            examenDto.getNomFichier() != null ? examenDto.getNomFichier() : "examen_" + System.currentTimeMillis(),
                            typeMime,
                            "examens",
                            numeroCarnet,
                            nomPatient
                        );

                        // Stocker les métadonnées en base
                        examen.setCheminFichier(cheminRelatif);
                        examen.setNomFichier(examenDto.getNomFichier());
                        examen.setTypeMime(typeMime);

                        System.out.println(" Fichier sauvegardé: " + examenDto.getNomFichier() + " → " + cheminRelatif);

                    } catch (Exception e) {
                        System.err.println(" Erreur lors de la sauvegarde du fichier: " + e.getMessage());
                        e.printStackTrace();
                        // On continue quand même l'enregistrement de l'examen
                    }
                }

                examenRepository.save(examen);
            }
            System.out.println("✅ " + dto.getExamens().size() + " examens " + (modeEdition ? "mis à jour" : "enregistrés"));
        }

        // 4. Gérer les observations
        if (dto.getObservations() != null && !dto.getObservations().isEmpty()) {
            for (ObservationDTO obsDto : dto.getObservations()) {
                Observation observation;
                if (obsDto.getId() != null && !obsDto.getId().isEmpty()) {
                    // Mise à jour d'une observation existante
                    observation = observationRepository.findById(obsDto.getId())
                        .orElse(new Observation());
                } else {
                    // Nouvelle observation
                    observation = new Observation();
                }

                observation.setIdPatient(dto.getIdPatient());
                observation.setTypeObservation(obsDto.getTypeObservation());
                observation.setValeur(obsDto.getValeur());
                observation.setCommentaire(obsDto.getCommentaire());
                observation.setDateObservation(Instant.parse(dto.getDateConsultation()));
                observation.setConsultation(consultation.getId()); // ✅ Lier l'observation à la consultation

                observationRepository.save(observation);
            }
            System.out.println("✅ " + dto.getObservations().size() + " observations " + (modeEdition ? "mises à jour" : "enregistrées"));
        }

        // Retourner le DTO avec l'ID généré
        dto.setId(consultation.getId());
        return dto;
    }
    
    /**
     * Récupérer une consultation par son ID
     * @param id ID de la consultation
     * @return DTO de la consultation
     */
    public ConsultationDTO getConsultationById(String id) {
        Optional<Consultation> optional = consultationRepository.findById(id);
        if (optional.isPresent()) {
            Consultation consultation = optional.get();
            ConsultationDTO dto = mapEntityToDTO(consultation);

            // Charger les prescriptions
            List<Prescription> prescriptions = prescriptionRepository.findByIdConsultation(id);
            List<PrescriptionDTO> prescriptionDTOs = new ArrayList<>();
            for (Prescription p : prescriptions) {
                prescriptionDTOs.add(mapPrescriptionToDTO(p));
            }
            dto.setPrescriptions(prescriptionDTOs);

            // Charger les examens
            List<Examen> examens = examenRepository.findByIdConsultation(id);
            List<ExamenDTO> examenDTOs = new ArrayList<>();
            for (Examen e : examens) {
                examenDTOs.add(mapExamenToDTO(e));
            }
            dto.setExamens(examenDTOs);

            // Charger les observations de cette consultation spécifique
            List<Observation> observations = observationRepository.findByConsultation(consultation.getId());
            List<ObservationDTO> observationDTOs = new ArrayList<>();
            for (Observation o : observations) {
                observationDTOs.add(mapObservationToDTO(o));
            }
            dto.setObservations(observationDTOs);

            System.out.println("✅ Consultation chargée avec " +
                prescriptionDTOs.size() + " prescriptions, " +
                examenDTOs.size() + " examens, " +
                observationDTOs.size() + " observations");

            return dto;
        }
        return null;
    }
    
    /**
     * Récupérer toutes les consultations d'un patient
     * @param idPatient ID du patient
     * @return Liste des consultations
     */
    public List<ConsultationDTO> getConsultationsByPatient(String idPatient) {
        try {
            List<Consultation> consultations = consultationRepository.findByIdPatient(idPatient);
            List<ConsultationDTO> dtos = new ArrayList<>();

            for (Consultation consultation : consultations) {
                dtos.add(mapEntityToDTO(consultation));
            }

            System.out.println("✅ Service: " + dtos.size() + " consultations trouvées pour patient " + idPatient);
            return dtos;
        } catch (Exception e) {
            System.err.println("❌ Erreur dans getConsultationsByPatient: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    // Méthodes de mapping
    private ConsultationDTO mapEntityToDTO(Consultation entity) {
        ConsultationDTO dto = new ConsultationDTO();
        dto.setId(entity.getId());
        dto.setIdPatient(entity.getIdPatient());
        dto.setCategorieConsultation(entity.getCategorieConsultation());
        dto.setTypeConsultation(entity.getTypeConsultation());
        dto.setIdProfessionnelSante(entity.getIdProfessionnelSante());
        dto.setMotif(entity.getMotif());
        dto.setDiagnostic(entity.getDiagnostic());
        dto.setTraitement(entity.getTraitement());
        dto.setDateConsultation(entity.getDateConsultation().toString());

        // Enrichir avec les libellés
        try {
            if (entity.getTypeConsultation() != null) {
                Optional<TypeConsultation> typeConsult = typeConsultationRepository.findById(entity.getTypeConsultation());
                if (typeConsult.isPresent()) {
                    dto.setTypeConsultationLibelle(typeConsult.get().getLibelle());
                }
            }

            if (entity.getCategorieConsultation() != null) {
                Optional<CategorieConsultation> catConsult = categorieConsultationRepository.findById(entity.getCategorieConsultation());
                if (catConsult.isPresent()) {
                    dto.setCategorieConsultationLibelle(catConsult.get().getLibelle());
                }
            }
        } catch (Exception e) {
            System.err.println("⚠️ Erreur lors de l'enrichissement des libellés: " + e.getMessage());
        }

        return dto;
    }

    private PrescriptionDTO mapPrescriptionToDTO(Prescription entity) {
        PrescriptionDTO dto = new PrescriptionDTO();
        dto.setId(entity.getId());
        dto.setIdConsultation(entity.getIdConsultation());
        dto.setMedicament(entity.getMedicament());
        dto.setPosologie(entity.getPosologie());
        dto.setDuree(entity.getDuree());
        return dto;
    }

    private ExamenDTO mapExamenToDTO(Examen entity) {
        ExamenDTO dto = new ExamenDTO();
        dto.setId(entity.getId());
        dto.setIdConsultation(entity.getIdConsultation());
        dto.setTypeExamen(entity.getTypeExamen());
        dto.setResultat(entity.getResultat());
        dto.setCommentaire(entity.getCommentaire());

        // Métadonnées du fichier (pas le contenu)
        dto.setCheminFichier(entity.getCheminFichier());
        dto.setNomFichier(entity.getNomFichier());
        dto.setTypeMime(entity.getTypeMime());

        // Résoudre le libellé du type d'examen
        if (entity.getTypeExamen() != null) {
            consultationTypeExamenAutoriseRepository.findById(entity.getTypeExamen())
                .ifPresent(type -> dto.setTypeExamenLibelle(type.getLibelle()));
        }

        // Ne pas retourner le fichierJoint (Base64) lors de la lecture
        // Le fichier sera téléchargé via un endpoint dédié

        return dto;
    }

    private ObservationDTO mapObservationToDTO(Observation entity) {
        ObservationDTO dto = new ObservationDTO();
        dto.setId(entity.getId());
        dto.setIdPatient(entity.getIdPatient());
        dto.setTypeObservation(entity.getTypeObservation());

        // Récupérer le libellé du type d'observation
        if (entity.getTypeObservation() != null) {
            typeObservationRepository.findById(entity.getTypeObservation()).ifPresent(typeObs -> {
                dto.setTypeObservationLibelle(typeObs.getLibelle());
            });
        }

        dto.setValeur(entity.getValeur());
        dto.setCommentaire(entity.getCommentaire());
        dto.setDateObservation(entity.getDateObservation());
        return dto;
    }

    /**
     * Récupérer toutes les consultations d'un professionnel de santé
     * @param professionnelId ID du professionnel
     * @return Liste des consultations avec informations enrichies
     */
    public List<ConsultationDTO> getConsultationsByProfessionnel(String professionnelId) {
        System.out.println(" Recherche consultations pour professionnel: " + professionnelId);

        // Récupérer toutes les consultations du professionnel
        List<Consultation> consultations = consultationRepository.findByIdProfessionnelSante(professionnelId);

        System.out.println(" " + consultations.size() + " consultations trouvées");

        // Convertir en DTO et enrichir avec les informations
        List<ConsultationDTO> dtos = new ArrayList<>();
        for (Consultation consultation : consultations) {
            ConsultationDTO dto = mapEntityToDTO(consultation);

            // Enrichir avec le nom du patient
            if (consultation.getIdPatient() != null) {
                Optional<com.base.frame.carnet.sante.entities.Patient> patient =
                    patientRepository.findById(consultation.getIdPatient());
                if (patient.isPresent()) {
                    String nomComplet = patient.get().getFirstName() + " " + patient.get().getLastName();
                    dto.setPatientNom(nomComplet);
                    System.out.println(" Patient: " + nomComplet);
                }
            }

            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * Méthode de validation des données de la consultation
     * @param dto DTO de la consultation à valider
     * @throws ObjectValidationException si les données sont invalides
     */
    public void controleValidationObjetConsultation(ConsultationDTO dto) {
        System.out.println("🔍 Validation consultation - Patient: " + dto.getIdPatient());
        System.out.println("🔍 Validation consultation - Catégorie: '" + dto.getCategorieConsultation() + "'");
        System.out.println("🔍 Validation consultation - Type: '" + dto.getTypeConsultation() + "'");

        // Validation de l'ID du patient
        if (dto.getIdPatient() == null || dto.getIdPatient().trim().isEmpty()) {
            System.out.println("❌ Patient obligatoire");
            throw new ObjectValidationException("Patient obligatoire", null);
        }

        // Vérifier que le patient existe
        if (!patientRepository.existsById(dto.getIdPatient())) {
            System.out.println("❌ Patient inexistant");
            throw new ObjectValidationException("Patient inexistant", null);
        }

        // Validation de la catégorie de consultation
        if (dto.getCategorieConsultation() == null || dto.getCategorieConsultation().trim().isEmpty()) {
            System.out.println("❌ Catégorie de consultation obligatoire");
            throw new ObjectValidationException("Catégorie de consultation obligatoire", null);
        }

        // Vérifier que la catégorie existe
        if (!categorieConsultationRepository.existsById(dto.getCategorieConsultation())) {
            System.out.println("❌ Catégorie de consultation inexistante");
            throw new ObjectValidationException("Catégorie de consultation inexistante", null);
        }

        // Validation du type de consultation
        if (dto.getTypeConsultation() == null || dto.getTypeConsultation().trim().isEmpty()) {
            throw new ObjectValidationException("Type de consultation obligatoire", null);
        }

        // Vérifier que le type existe
        if (!typeConsultationRepository.existsById(dto.getTypeConsultation())) {
            throw new ObjectValidationException("Type de consultation inexistant", null);
        }

        // Validation du motif
        if (dto.getMotif() == null || dto.getMotif().trim().isEmpty()) {
            throw new ObjectValidationException("Motif de la consultation obligatoire", null);
        }

        // Validation du diagnostic
        if (dto.getDiagnostic() == null || dto.getDiagnostic().trim().isEmpty()) {
            throw new ObjectValidationException("Diagnostic obligatoire", null);
        }

        // Validation de la date de consultation
        if (dto.getDateConsultation() == null || dto.getDateConsultation().trim().isEmpty()) {
            throw new ObjectValidationException("Date de consultation obligatoire", null);
        }

        // Validation des observations (si présentes)
        if (dto.getObservations() != null && !dto.getObservations().isEmpty()) {
            for (ObservationDTO obs : dto.getObservations()) {
                if (obs.getTypeObservation() == null || obs.getTypeObservation().trim().isEmpty()) {
                    throw new ObjectValidationException("Type d'observation obligatoire", null);
                }
                if (obs.getValeur() == null || obs.getValeur().trim().isEmpty()) {
                    throw new ObjectValidationException("Valeur de l'observation obligatoire", null);
                }
            }
        }

        // Validation des prescriptions (si présentes)
        if (dto.getPrescriptions() != null && !dto.getPrescriptions().isEmpty()) {
            for (PrescriptionDTO presc : dto.getPrescriptions()) {
                if (presc.getMedicament() == null || presc.getMedicament().trim().isEmpty()) {
                    throw new ObjectValidationException("Médicament obligatoire pour la prescription", null);
                }
                if (presc.getPosologie() == null || presc.getPosologie().trim().isEmpty()) {
                    throw new ObjectValidationException("Posologie obligatoire pour la prescription", null);
                }
            }
        }

        // Validation des examens (si présents)
        if (dto.getExamens() != null && !dto.getExamens().isEmpty()) {
            for (ExamenDTO exam : dto.getExamens()) {
                if (exam.getTypeExamen() == null || exam.getTypeExamen().trim().isEmpty()) {
                    throw new ObjectValidationException("Type d'examen obligatoire", null);
                }
            }
        }
    }

    /**
     * Déterminer le type MIME d'un fichier
     * @param base64Data Données Base64 (peut contenir le préfixe data:...)
     * @param nomFichier Nom du fichier
     * @return Type MIME
     */
    private String determinerTypeMime(String base64Data, String nomFichier) {
        // Si le Base64 contient le type MIME (data:image/png;base64,...)
        if (base64Data != null && base64Data.startsWith("data:")) {
            int indexVirgule = base64Data.indexOf(",");
            if (indexVirgule > 0) {
                String prefix = base64Data.substring(0, indexVirgule);
                // Extraire le type MIME : data:application/pdf;base64 → application/pdf
                String[] parts = prefix.split(":");
                if (parts.length > 1) {
                    String typeMime = parts[1].split(";")[0];
                    return typeMime;
                }
            }
        }

        // Sinon, déterminer depuis l'extension du fichier
        if (nomFichier != null) {
            String extension = nomFichier.substring(nomFichier.lastIndexOf(".") + 1).toLowerCase();
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

        return "application/octet-stream";
    }

    /**
     * Recherche paginée des consultations d'un professionnel
     * @param idProfessionnel ID du professionnel
     * @param motCle Mot-clé de recherche
     * @param typeConsultation Type de consultation
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @param pageRequest Pagination
     * @return Page de consultations
     */
    public Page<ConsultationDTO> findConsultationsByProfessionnel(
            String idProfessionnel,
            String motCle,
            String typeConsultation,
            Instant dateDebut,
            Instant dateFin,
            Pageable pageRequest) {

        Page<Consultation> listResult = this.consultationDAO.findPageConsultationByProfessionnel(
                idProfessionnel, motCle, typeConsultation, dateDebut, dateFin, pageRequest);

        return this.mapEntityPageIntoDTOPage(pageRequest, listResult);
    }

    /**
     * Recherche paginée des consultations d'un patient
     * @param idPatient ID du patient
     * @param motCle Mot-clé de recherche
     * @param typeConsultation Type de consultation
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @param pageRequest Pagination
     * @return Page de consultations
     */
    public Page<ConsultationDTO> findConsultationsByPatient(
            String idPatient,
            String motCle,
            String typeConsultation,
            Instant dateDebut,
            Instant dateFin,
            Pageable pageRequest) {

        Page<Consultation> listResult = this.consultationDAO.findPageConsultationByPatient(
                idPatient, motCle, typeConsultation, dateDebut, dateFin, pageRequest);

        return this.mapEntityPageIntoDTOPage(pageRequest, listResult);
    }

    /**
     * Convertir une page d'entités en page de DTOs
     * @param page Pagination
     * @param source Page d'entités
     * @return Page de DTOs
     */
    public Page<ConsultationDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Consultation> source) {
        List<ConsultationDTO> list = new ArrayList<>();

        for (Consultation consultation : source.getContent()) {
            ConsultationDTO dto = mapEntityToDTO(consultation);

            // Enrichir avec le nom du patient
            if (consultation.getIdPatient() != null) {
                Optional<com.base.frame.carnet.sante.entities.Patient> patient =
                    patientRepository.findById(consultation.getIdPatient());
                if (patient.isPresent()) {
                    String nomComplet = patient.get().getFirstName() + " " + patient.get().getLastName();
                    dto.setPatientNom(nomComplet);
                }
            }

            list.add(dto);
        }

        return new PageImpl<>(list, page, source.getTotalElements());
    }

    /**
     * Calcule les statistiques de consultations pour un professionnel
     * @param idProfessionnel ID du professionnel
     * @return Map contenant les statistiques (aujourdhui, semaine, mois)
     */
    public Map<String, Long> getStatistiquesConsultations(String idProfessionnel) {
        Instant now = Instant.now();
        Instant debutJour = now.truncatedTo(ChronoUnit.DAYS);
        Instant debutSemaine = now.minus(7, ChronoUnit.DAYS);
        Instant debutMois = now.minus(30, ChronoUnit.DAYS);

        Long aujourdhui = consultationDAO.countConsultationsByProfessionnelAndDateRange(
                idProfessionnel, debutJour, null);
        Long semaine = consultationDAO.countConsultationsByProfessionnelAndDateRange(
                idProfessionnel, debutSemaine, null);
        Long mois = consultationDAO.countConsultationsByProfessionnelAndDateRange(
                idProfessionnel, debutMois, null);

        Map<String, Long> stats = new HashMap<>();
        stats.put("aujourdhui", aujourdhui);
        stats.put("semaine", semaine);
        stats.put("mois", mois);

        return stats;
    }
}

