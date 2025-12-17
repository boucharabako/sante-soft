package com.base.frame.carnet.sante.services;

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
import com.base.frame.carnet.sante.repositories.ExamenRepository;
import com.base.frame.carnet.sante.repositories.ObservationRepository;
import com.base.frame.carnet.sante.repositories.PrescriptionRepository;
import com.base.frame.carnet.sante.repositories.TypeConsultationRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CategorieConsultationRepository categorieConsultationRepository;

    @Autowired
    private com.base.frame.carnet.sante.repositories.PatientRepository patientRepository;
    
    /**
     * Enregistrer une consultation avec ses prescriptions et examens
     * @param dto DTO de la consultation
     * @param currentUserId ID de l'utilisateur connecté
     * @return DTO de la consultation enregistrée
     */
    public ConsultationDTO enregistrerConsultation(ConsultationDTO dto, String currentUserId) {
        System.out.println("======================== Enregistrement de la consultation");
        
        // 1. Enregistrer la consultation
        Consultation consultation = new Consultation();
        consultation.setIdPatient(dto.getIdPatient());
        consultation.setCategorieConsultation(dto.getCategorieConsultation());
        consultation.setTypeConsultation(dto.getTypeConsultation());
        consultation.setIdProfessionnelSante(currentUserId);
        consultation.setMotif(dto.getMotif());
        consultation.setDiagnostic(dto.getDiagnostic());
        consultation.setTraitement(dto.getTraitement());
        consultation.setDateConsultation(Instant.parse(dto.getDateConsultation()));
        
        consultation = consultationRepository.save(consultation);
        System.out.println("✅ Consultation enregistrée avec ID: " + consultation.getId());
        
        // 2. Enregistrer les prescriptions
        if (dto.getPrescriptions() != null && !dto.getPrescriptions().isEmpty()) {
            for (PrescriptionDTO prescDto : dto.getPrescriptions()) {
                Prescription prescription = new Prescription();
                prescription.setIdConsultation(consultation.getId());
                prescription.setMedicament(prescDto.getMedicament());
                prescription.setPosologie(prescDto.getPosologie());
                prescription.setDuree(prescDto.getDuree());
                
                prescriptionRepository.save(prescription);
            }
            System.out.println("✅ " + dto.getPrescriptions().size() + " prescriptions enregistrées");
        }
        
        // 3. Enregistrer les examens
        if (dto.getExamens() != null && !dto.getExamens().isEmpty()) {
            for (ExamenDTO examenDto : dto.getExamens()) {
                Examen examen = new Examen();
                examen.setIdConsultation(consultation.getId());
                examen.setTypeExamen(examenDto.getTypeExamen());
                examen.setResultat(examenDto.getResultat());
                examen.setFichierJoint(examenDto.getFichierJoint());

                examenRepository.save(examen);
            }
            System.out.println(" " + dto.getExamens().size() + " examens enregistrés");
        }

        // 4. Enregistrer les observations
        if (dto.getObservations() != null && !dto.getObservations().isEmpty()) {
            for (ObservationDTO obsDto : dto.getObservations()) {
                Observation observation = new Observation();
                observation.setIdPatient(dto.getIdPatient());
                observation.setTypeObservation(obsDto.getTypeObservation());
                observation.setValeur(obsDto.getValeur());
                observation.setCommentaire(obsDto.getCommentaire());
                observation.setDateObservation(Instant.parse(dto.getDateConsultation()));

                observationRepository.save(observation);
            }
            System.out.println(" " + dto.getObservations().size() + " observations enregistrées");
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
        dto.setFichierJoint(entity.getFichierJoint());
        return dto;
    }

    private ObservationDTO mapObservationToDTO(Observation entity) {
        ObservationDTO dto = new ObservationDTO();
        dto.setId(entity.getId());
        dto.setIdPatient(entity.getIdPatient());
        dto.setTypeObservation(entity.getTypeObservation());
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
                    System.out.println("👤 Patient: " + nomComplet);
                }
            }

            dtos.add(dto);
        }

        return dtos;
    }
}

