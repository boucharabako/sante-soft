/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.services;

import com.base.frame.account.core.repository.ProfilRepository;
import com.base.frame.account.core.repository.UtilisateurPasswordRepository;
import com.base.frame.account.core.repository.UtilisateurProfilRepository;
import com.base.frame.account.entity.Profil;
import com.base.frame.account.entity.UtilisateurPassword;
import com.base.frame.account.entity.UtilisateurProfil;
import com.base.frame.account.utils.AccountConstant;
import com.base.frame.carnet.sante.daos.PatientDAO;
import com.base.frame.carnet.sante.dtos.PatientDTO;
import com.base.frame.carnet.sante.entities.Patient;
import com.base.frame.carnet.sante.repositories.PatientRepository;
import com.base.frame.socle.core.iservice.ISocleGenericService;
import com.base.frame.socle.core.workflow.entity.Etat;
import com.base.frame.socle.core.workflow.service.IWorkflowService;
import com.base.frame.socle.utils.exceptions.ObjectValidationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bouchara
 */
@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private ProfilRepository profilRepository;

    @Autowired
    private UtilisateurProfilRepository utilisateurProfilRepository;

    @Autowired
    private UtilisateurPasswordRepository utilisateurPasswordRepository;

    @Autowired
    private IWorkflowService workflowCycleService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private ISocleGenericService socleGenericService;

    /**
     * Génère un numéro de carnet unique et incrémental Format: ANNÉE-NUMÉRO
     * (ex: 2025-001, 2025-002, etc.)
     */
    private String generateNumeroCarnet() {
        // Obtenir l'année courante
        int currentYear = java.time.Year.now().getValue();
        String yearPrefix = currentYear + "-%";

        // Trouver le numéro maximum pour l'année courante
        Optional<Integer> maxNumero = patientRepository.findMaxNumeroCarnetByYear(yearPrefix);
        int nextNumero;

        if (maxNumero.isPresent() && maxNumero.get() != null) {
            nextNumero = maxNumero.get() + 1;
        } else {
            // Premier numéro de carnet pour cette année
            nextNumero = 1;
        }

        // Formater le numéro: ANNÉE-XXX (3 chiffres minimum)
        return String.format("%d-%03d", currentYear, nextNumero);
    }

    /**
     * Récupère le prochain numéro de carnet disponible (pour affichage dans le
     * formulaire)
     */
    public String getNextNumeroCarnet() {
        return this.generateNumeroCarnet();
    }

    public PatientDTO mapEntityIntoDTO(Patient entity) {
        PatientDTO dto = new PatientDTO();
        dto.setId(entity.getId());
        dto.setNumeroCarnet(entity.getNumeroCarnet());

        // Garder l'ID du groupe sanguin pour que le select fonctionne
        dto.setGroupeSanguin(entity.getGroupeSanguin());
        dto.setDateEnregistrement(entity.getDateEnregistrement());

        // Mapper les champs de Utilisateur
        dto.setUsername(entity.getUsername());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());

        // Garder l'ID du sexe pour que le select fonctionne
        dto.setSexe(entity.getSexe());
        dto.setDateNaissance(entity.getDateNaissance());
        dto.setEmail(entity.getEmail());
        dto.setTel(entity.getTel());
        dto.setTitre(entity.getTitre());
        if (entity.getEtat() != null) {
            dto.setEtat(entity.getEtat().getId());
            dto.setLibelleEtat(entity.getEtat().getLibelleEtat());
        }

        return dto;
    }

    public PatientDTO savePatient(PatientDTO dto) {
        Patient entity = new Patient();
        boolean isNew = false;

        if (dto.getId() != null && this.patientRepository.existsById(dto.getId())) {
            entity = this.patientRepository.findById(dto.getId()).get();
        } else {
            isNew = true;
            // Définir l'état initial pour un nouvel utilisateur
            Etat e = workflowCycleService.getEtatInitial("WKFL_UTILISATEUR");
            entity.setEtat(e);

            // Générer automatiquement le numéro de carnet pour un nouveau patient
            String numeroCarnet = this.generateNumeroCarnet();
            entity.setNumeroCarnet(numeroCarnet);
            dto.setNumeroCarnet(numeroCarnet);

            // Générer automatiquement la date d'enregistrement pour un nouveau patient
            java.time.Instant dateEnregistrement = java.time.Instant.now();
            entity.setDateEnregistrement(dateEnregistrement);
            dto.setDateEnregistrement(dateEnregistrement);
        }

        // Mapper les champs spécifiques au patient (sauf numeroCarnet et dateEnregistrement pour les nouveaux patients)
        if (!isNew) {
            entity.setNumeroCarnet(dto.getNumeroCarnet());
            entity.setDateEnregistrement(dto.getDateEnregistrement());
        }
        entity.setGroupeSanguin(dto.getGroupeSanguin());

        // Mapper les champs de Utilisateur
        entity.setUsername(dto.getUsername());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setSexe(dto.getSexe());
        entity.setDateNaissance(dto.getDateNaissance());
        entity.setEmail(dto.getEmail());
        entity.setTel(dto.getTel());
        entity.setTitre(dto.getTitre());

        this.patientRepository.save(entity);
        dto.setId(entity.getId());

        // Si c'est un nouveau patient, créer la relation avec le profil PATIENT
        if (isNew) {
            Optional<Profil> profilPatient = profilRepository.findProfilByCode("PT");
            if (profilPatient.isPresent()) {
                UtilisateurProfil utilisateurProfil = new UtilisateurProfil();
                utilisateurProfil.setUtilisateur(entity);
                utilisateurProfil.setProfil(profilPatient.get());
                utilisateurProfilRepository.save(utilisateurProfil);
            }
        }

        // Sauvegarder le mot de passe si fourni
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            this.savePatientPassword(dto);
        }

        return dto;
    }

    /**
     * Sauvegarde ou met à jour le mot de passe d'un patient
     */
    private void savePatientPassword(PatientDTO dto) {
        // Validation du mot de passe
        this.validatePassword(dto);

        UtilisateurPassword entity = new UtilisateurPassword();

        // Vérifier si un mot de passe existe déjà pour cet utilisateur
        Optional<UtilisateurPassword> existingPassword = this.utilisateurPasswordRepository.findByUserId(dto.getId());
        if (existingPassword.isPresent()) {
            entity = existingPassword.get();
        } else {
            // Récupérer l'utilisateur (patient)
            Optional<Patient> patient = this.patientRepository.findById(dto.getId());
            if (patient.isPresent()) {
                entity.setUtilisateur(patient.get());
            }
        }

        // Encoder et sauvegarder le mot de passe
        entity.setPassword(encoder.encode(dto.getPassword()));
        this.utilisateurPasswordRepository.save(entity);
    }

    /**
     * Valide les champs du mot de passe
     */
    private void validatePassword(PatientDTO dto) {
        if (dto.getId() == null || dto.getId().isEmpty() || !this.patientRepository.existsById(dto.getId())) {
            throw new ObjectValidationException("L'identifiant du patient est obligatoire", null);
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new ObjectValidationException("Le mot de passe est obligatoire", null);
        }
        if (dto.getConfirmPassword() == null || dto.getConfirmPassword().isEmpty()) {
            throw new ObjectValidationException("La confirmation du mot de passe est obligatoire", null);
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new ObjectValidationException("Le mot de passe et sa confirmation ne correspondent pas", null);
        }
    }

    public void deletePatient(String id) {
        if (this.patientRepository.existsById(id)) {
            // 1. Supprimer d'abord le mot de passe de l'utilisateur (si existe)
            Optional<UtilisateurPassword> password = this.utilisateurPasswordRepository.findByUserId(id);
            if (password.isPresent()) {
                this.utilisateurPasswordRepository.delete(password.get());
            }

            // 2. Supprimer les profils de l'utilisateur
            List<UtilisateurProfil> profils = this.utilisateurProfilRepository.findByUserId(id);
            if (!profils.isEmpty()) {
                this.utilisateurProfilRepository.deleteAll(profils);
            }

            // 3. Maintenant on peut supprimer le patient (et l'utilisateur par cascade)
            this.patientRepository.deleteById(id);
        }
    }

    public Optional<Patient> findPatientById(String id) {
        return this.patientRepository.findById(id);
    }

    public PatientDTO getPatient(String id) {
        PatientDTO t = new PatientDTO();
        if (this.patientRepository.existsById(id)) {
            t = this.mapEntityIntoDTO(this.patientRepository.findById(id).get());
        }
        return t;
    }

    public Page<PatientDTO> findBySpecTerm(String numeroCarnet, String mc, String groupeSanguin, Pageable pageRequest) {
        Page<Patient> listResult;

        listResult = this.patientDAO.findPagePatient(numeroCarnet, pageRequest, groupeSanguin, mc);

        return this.mapEntityPageIntoDTOPage(pageRequest, listResult);
    }

    public Page<PatientDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Patient> source) {
        List<PatientDTO> list = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(list, page, source.getTotalElements());
    }

    public List<PatientDTO> mapEntitiesIntoDTOs(List<Patient> entities) {
        List<PatientDTO> result = new ArrayList<>();
        entities.stream().map(temp -> this.mapEntityIntoDTO(temp)).forEachOrdered(obj -> {
            result.add(obj);
        });
        return result;
    }

    public List<PatientDTO> getAllPatient(String mc) {
        return this.mapEntitiesIntoDTOs(patientDAO.findListePatient(mc));
    }
}
