package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.dtos.VaccinationDTO;
import com.base.frame.carnet.sante.entities.Vaccination;
import com.base.frame.carnet.sante.repositories.VaccinationRepository;
import com.base.frame.carnet.sante.repositories.PatientRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour la gestion des vaccinations
 * @author Bouchara
 */
@Service
@Transactional
public class VaccinationService {
    
    @Autowired
    private VaccinationRepository vaccinationRepository;
    
    @Autowired
    private PatientRepository patientRepository;
    
    /**
     * Enregistrer ou mettre à jour une vaccination
     * @param dto DTO de la vaccination
     * @return DTO de la vaccination enregistrée
     */
    public VaccinationDTO saveVaccination(VaccinationDTO dto) {
        System.out.println("======================== Enregistrement de la vaccination");
        
        Vaccination vaccination = new Vaccination();
        
        // Si c'est une mise à jour
        if (dto.getId() != null && vaccinationRepository.existsById(dto.getId())) {
            vaccination = vaccinationRepository.findById(dto.getId()).get();
            System.out.println("✏️ Mise à jour de la vaccination ID: " + dto.getId());
        } else {
            System.out.println("➕ Nouvelle vaccination");
        }
        
        // Mapper les données du DTO vers l'entité
        vaccination.setIdPatient(dto.getIdPatient());
        vaccination.setVaccin(dto.getVaccin());
        vaccination.setNumeroLot(dto.getLot());
        vaccination.setLieuVaccination(dto.getLieuVaccination());
        vaccination.setStatut(dto.getStatut());
        vaccination.setObservations(dto.getObservations());
        
        // Convertir les dates String en Instant
        if (dto.getDateVaccination() != null && !dto.getDateVaccination().isEmpty()) {
            vaccination.setDateVaccination(parseDate(dto.getDateVaccination()));
        }

        if (dto.getProchainRappel() != null && !dto.getProchainRappel().isEmpty()) {
            vaccination.setDateRappel(parseDate(dto.getProchainRappel()));
        }
        
        // Sauvegarder
        vaccination = vaccinationRepository.save(vaccination);
        System.out.println("✅ Vaccination enregistrée avec ID: " + vaccination.getId());
        
        // Retourner le DTO avec l'ID généré
        dto.setId(vaccination.getId());
        return dto;
    }
    
    /**
     * Récupérer une vaccination par son ID
     * @param id ID de la vaccination
     * @return DTO de la vaccination
     */
    public VaccinationDTO getVaccinationById(String id) {
        Optional<Vaccination> optional = vaccinationRepository.findById(id);
        if (optional.isPresent()) {
            return mapEntityToDTO(optional.get());
        }
        return null;
    }
    
    /**
     * Récupérer toutes les vaccinations d'un patient
     * @param idPatient ID du patient
     * @return Liste des vaccinations
     */
    public List<VaccinationDTO> getVaccinationsByPatient(String idPatient) {
        try {
            List<Vaccination> vaccinations = vaccinationRepository.findByIdPatient(idPatient);
            List<VaccinationDTO> dtos = new ArrayList<>();
            
            for (Vaccination vaccination : vaccinations) {
                dtos.add(mapEntityToDTO(vaccination));
            }
            
            System.out.println("✅ Service: " + dtos.size() + " vaccinations trouvées pour patient " + idPatient);
            return dtos;
        } catch (Exception e) {
            System.err.println("❌ Erreur dans getVaccinationsByPatient: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    
    /**
     * Supprimer une vaccination
     * @param id ID de la vaccination à supprimer
     */
    public void deleteVaccination(String id) {
        if (vaccinationRepository.existsById(id)) {
            vaccinationRepository.deleteById(id);
            System.out.println("✅ Vaccination supprimée: " + id);
        }
    }
    
    /**
     * Mapper une entité Vaccination vers un DTO
     */
    private VaccinationDTO mapEntityToDTO(Vaccination entity) {
        VaccinationDTO dto = new VaccinationDTO();
        dto.setId(entity.getId());
        dto.setIdPatient(entity.getIdPatient());
        dto.setVaccin(entity.getVaccin());
        dto.setLot(entity.getNumeroLot());
        dto.setLieuVaccination(entity.getLieuVaccination());
        dto.setStatut(entity.getStatut());
        dto.setObservations(entity.getObservations());
        
        // Convertir les dates Instant en String
        if (entity.getDateVaccination() != null) {
            dto.setDateVaccination(entity.getDateVaccination().toString());
        }
        
        if (entity.getDateRappel() != null) {
            dto.setProchainRappel(entity.getDateRappel().toString());
        }
        
        // Enrichir avec le nom du patient si nécessaire
        if (entity.getIdPatient() != null) {
            Optional<com.base.frame.carnet.sante.entities.Patient> patient = 
                patientRepository.findById(entity.getIdPatient());
            if (patient.isPresent()) {
                String nomComplet = patient.get().getFirstName() + " " + patient.get().getLastName();
                dto.setPatientNom(nomComplet);
            }
        }
        
        return dto;
    }

    /**
     * Convertir une date String en Instant
     * Gère les formats: "yyyy-MM-dd" et "yyyy-MM-dd'T'HH:mm:ss'Z'"
     * @param dateStr Date au format String
     * @return Instant
     */
    private Instant parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        try {
            // Si la date contient déjà l'heure (format ISO 8601 complet)
            if (dateStr.contains("T")) {
                return Instant.parse(dateStr);
            }

            // Sinon, c'est juste une date (yyyy-MM-dd), on ajoute l'heure à minuit UTC
            LocalDate localDate = LocalDate.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDateTime localDateTime = localDate.atStartOfDay();
            return localDateTime.toInstant(ZoneOffset.UTC);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors du parsing de la date: " + dateStr);
            e.printStackTrace();
            return null;
        }
    }
}

