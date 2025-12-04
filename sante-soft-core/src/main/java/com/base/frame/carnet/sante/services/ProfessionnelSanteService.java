/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.services;

import com.base.frame.account.core.repository.ProfilRepository;
import com.base.frame.account.core.repository.UtilisateurPasswordRepository;
import com.base.frame.account.core.repository.UtilisateurProfilRepository;
import com.base.frame.account.core.repository.UtilisateurRepository;
import com.base.frame.account.entity.Profil;
import com.base.frame.account.entity.UtilisateurPassword;
import com.base.frame.account.entity.UtilisateurProfil;
import com.base.frame.carnet.sante.daos.ProfessionnelSanteDAO;
import com.base.frame.carnet.sante.dtos.ProfessionnelSanteDTO;
import com.base.frame.carnet.sante.entities.ProfessionelSante;
import com.base.frame.carnet.sante.repositories.ProfessionnelSanteRepository;
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
public class ProfessionnelSanteService {

    @Autowired
    private ProfessionnelSanteRepository professionnelSanteRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ProfessionnelSanteDAO professionnelSanteDAO;

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
     * Génère un numéro d'ordre unique et incrémental Format: ANNÉE-NUMÉRO
     * (ex: 2025-001, 2025-002, etc.)
     */
    private String generateNumeroOrdre() {
        // Obtenir l'année courante
        int currentYear = java.time.Year.now().getValue();
        String yearPrefix = currentYear + "-%";

        // Trouver le numéro maximum pour l'année courante
        Optional<Integer> maxNumero = professionnelSanteRepository.findMaxNumeroOrdreByYear(yearPrefix);
        int nextNumero;

        if (maxNumero.isPresent() && maxNumero.get() != null) {
            nextNumero = maxNumero.get() + 1;
        } else {
            // Premier numéro d'ordre pour cette année
            nextNumero = 1;
        }

        // Formater le numéro: ANNÉE-XXX (3 chiffres minimum)
        return String.format("%d-%03d", currentYear, nextNumero);
    }

    /**
     * Récupère le prochain numéro d'ordre disponible (pour affichage dans le
     * formulaire)
     */
    public String getNextNumeroOrdre() {
        return this.generateNumeroOrdre();
    }

    public ProfessionnelSanteDTO mapEntityIntoDTO(ProfessionelSante entity) {
        ProfessionnelSanteDTO dto = new ProfessionnelSanteDTO();
        dto.setId(entity.getId());
        dto.setNumeroOrdre(entity.getNumeroOrdre());

//        if (entity.getSpecialite() != null && !entity.getSpecialite().isEmpty()) {
//            dto.setSpecialite(entity.getSpecialite());
//            dto.setSpecialiteLibelle(this.socleGenericService.findParamListById(entity.getSpecialite()).get().getLibelle());
//        }

//        dto.setEtablissement(entity.getEtablissement());
//        dto.setDateEnregistrement(entity.getDateEnregistrement());

        dto.setUsername(entity.getUsername());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());

        if (entity.getSexe() != null && !entity.getSexe().isEmpty()) {
            dto.setSexe(entity.getSexe());
            dto.setSexeLibelle(this.socleGenericService.findParamListById(entity.getSexe()).get().getLibelle());
        }
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

    public ProfessionnelSanteDTO saveProfessionnelSante(ProfessionnelSanteDTO dto) {
        // Valider les données du professionnel
        this.controleValidationObjetProfessionnelSante(dto);

        ProfessionelSante entity = new ProfessionelSante();
        boolean isNew = false;

        if (dto.getId() != null && this.professionnelSanteRepository.existsById(dto.getId())) {
            entity = this.professionnelSanteRepository.findById(dto.getId()).get();
        } else {
            isNew = true;
            // Définir l'état initial pour un nouvel utilisateur
            Etat e = workflowCycleService.getEtatInitial("WKFL_UTILISATEUR");
            entity.setEtat(e);

            // Générer automatiquement le numéro d'ordre pour un nouveau professionnel
            String numeroOrdre = this.generateNumeroOrdre();
            entity.setNumeroOrdre(numeroOrdre);
            dto.setNumeroOrdre(numeroOrdre);

            // Générer automatiquement la date d'enregistrement pour un nouveau professionnel
            java.time.Instant dateEnregistrement = java.time.Instant.now();
//            entity.setDateEnregistrement(dateEnregistrement);
            dto.setDateEnregistrement(dateEnregistrement);
        }

        // Mapper les champs spécifiques au professionnel (sauf numeroOrdre et dateEnregistrement pour les nouveaux)
        if (!isNew) {
            entity.setNumeroOrdre(dto.getNumeroOrdre());
//            entity.setDateEnregistrement(dto.getDateEnregistrement());
        }
//        entity.setSpecialite(dto.getSpecialite());
//        entity.setEtablissement(dto.getEtablissement());

        // Mapper les champs de Utilisateur
        entity.setUsername(dto.getUsername());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setSexe(dto.getSexe());
        entity.setDateNaissance(dto.getDateNaissance());
        entity.setEmail(dto.getEmail());
        entity.setTel(dto.getTel());
        entity.setTitre(dto.getTitre());

        this.professionnelSanteRepository.save(entity);
        dto.setId(entity.getId());

        // Si c'est un nouveau professionnel, créer la relation avec le profil PROFESSIONNEL_SANTE
        if (isNew) {
            Optional<Profil> profilProfessionnel = profilRepository.findProfilByCode("PS");
            if (profilProfessionnel.isPresent()) {
                UtilisateurProfil utilisateurProfil = new UtilisateurProfil();
                utilisateurProfil.setUtilisateur(entity);
                utilisateurProfil.setProfil(profilProfessionnel.get());
                utilisateurProfilRepository.save(utilisateurProfil);
            }
        }

        // Sauvegarder le mot de passe si fourni
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            this.saveProfessionnelPassword(dto);
        }

        return dto;
    }

    /**
     * Sauvegarde ou met à jour le mot de passe d'un professionnel
     */
    private void saveProfessionnelPassword(ProfessionnelSanteDTO dto) {
        // Validation du mot de passe
        this.validatePassword(dto);

        UtilisateurPassword entity = new UtilisateurPassword();

        // Vérifier si un mot de passe existe déjà pour cet utilisateur
        Optional<UtilisateurPassword> existingPassword = this.utilisateurPasswordRepository.findByUserId(dto.getId());
        if (existingPassword.isPresent()) {
            entity = existingPassword.get();
        } else {
            // Récupérer l'utilisateur (professionnel)
            Optional<ProfessionelSante> professionnel = this.professionnelSanteRepository.findById(dto.getId());
            if (professionnel.isPresent()) {
                entity.setUtilisateur(professionnel.get());
            }
        }

        // Encoder et sauvegarder le mot de passe
        entity.setPassword(encoder.encode(dto.getPassword()));
        this.utilisateurPasswordRepository.save(entity);
    }

    /**
     * Valide les champs du mot de passe
     */
    private void validatePassword(ProfessionnelSanteDTO dto) {
        if (dto.getId() == null || dto.getId().isEmpty() || !this.professionnelSanteRepository.existsById(dto.getId())) {
            throw new ObjectValidationException("L'identifiant du professionnel est obligatoire", null);
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

    public void deleteProfessionnelSante(String id) {
        if (this.professionnelSanteRepository.existsById(id)) {
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

            // 3. Maintenant on peut supprimer le professionnel (et l'utilisateur par cascade)
            this.professionnelSanteRepository.deleteById(id);
        }
    }

    public Optional<ProfessionelSante> findProfessionnelSanteById(String id) {
        return this.professionnelSanteRepository.findById(id);
    }

    public ProfessionnelSanteDTO getProfessionnelSante(String id) {
        ProfessionnelSanteDTO t = new ProfessionnelSanteDTO();
        if (this.professionnelSanteRepository.existsById(id)) {
            t = this.mapEntityIntoDTO(this.professionnelSanteRepository.findById(id).get());
        }
        return t;
    }

    public Page<ProfessionnelSanteDTO> findBySpecTerm(String numeroOrdre, String mc, String specialite, Pageable pageRequest) {
        Page<ProfessionelSante> listResult;

        listResult = this.professionnelSanteDAO.findPageProfessionnelSante(numeroOrdre, pageRequest, specialite, mc);

        return this.mapEntityPageIntoDTOPage(pageRequest, listResult);
    }

    public Page<ProfessionnelSanteDTO> mapEntityPageIntoDTOPage(Pageable page, Page<ProfessionelSante> source) {
        List<ProfessionnelSanteDTO> list = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(list, page, source.getTotalElements());
    }

    public List<ProfessionnelSanteDTO> mapEntitiesIntoDTOs(List<ProfessionelSante> entities) {
        List<ProfessionnelSanteDTO> result = new ArrayList<>();
        entities.stream().map(temp -> this.mapEntityIntoDTO(temp)).forEachOrdered(obj -> {
            result.add(obj);
        });
        return result;
    }

    public List<ProfessionnelSanteDTO> getAllProfessionnelSante(String mc) {
        return this.mapEntitiesIntoDTOs(professionnelSanteDAO.findListeProfessionnelSante(mc));
    }

    /**
     * Méthode de validation des données du professionnel de santé
     * @param dto
     */
    public void controleValidationObjetProfessionnelSante(ProfessionnelSanteDTO dto) {
        // Validation du nom d'utilisateur (username)
        if (dto.getUsername() == null || dto.getUsername().trim().isEmpty()) {
            throw new ObjectValidationException("Nom d'utilisateur obligatoire", null);
        }

        // Vérifier si le username existe déjà
        if (this.utilisateurRepository.findByUsername(dto.getUsername()).isPresent()) {
            if (dto.getId() == null || dto.getId().trim().isEmpty() || !this.professionnelSanteRepository.existsById(dto.getId())) {
                throw new ObjectValidationException("Nom d'utilisateur existe déjà", null);
            } else {
                ProfessionelSante professionnel = this.professionnelSanteRepository.findById(dto.getId()).get();
                if (!professionnel.getUsername().equalsIgnoreCase(dto.getUsername())) {
                    throw new ObjectValidationException("Nom d'utilisateur existe déjà", null);
                }
            }
        }

        // Validation du nom (lastName)
        if (dto.getLastName() == null || dto.getLastName().trim().isEmpty()) {
            throw new ObjectValidationException("Nom obligatoire", null);
        }

        // Validation du prénom (firstName)
        if (dto.getFirstName() == null || dto.getFirstName().trim().isEmpty()) {
            throw new ObjectValidationException("Prénom obligatoire", null);
        }

        // Validation du sexe
        if (dto.getSexe() == null || dto.getSexe().trim().isEmpty()) {
            throw new ObjectValidationException("Sexe obligatoire", null);
        }

        // Validation de la date de naissance
        if (dto.getDateNaissance() == null) {
            throw new ObjectValidationException("Date de naissance obligatoire", null);
        }

        // Validation de l'âge minimum (18 ans)
        java.time.LocalDate dateNaissance = dto.getDateNaissance().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        java.time.LocalDate today = java.time.LocalDate.now();
        long age = java.time.temporal.ChronoUnit.YEARS.between(dateNaissance, today);

        if (age < 18) {
            throw new ObjectValidationException("L'âge du professionnel doit être supérieur ou égal à 18 ans. Âge actuel: " + age + " ans", null);
        }

        // NOTE: La spécialité n'est plus obligatoire ici car elle est gérée via les affectations
        // Les affectations (spécialité + établissement) sont enregistrées séparément après la création du professionnel

        // Vérifier si l'email existe déjà (si fourni)
        if (dto.getEmail() != null && !dto.getEmail().trim().isEmpty()) {
            if (this.utilisateurRepository.findByEmail(dto.getEmail()).isPresent()) {
                if (dto.getId() == null || dto.getId().trim().isEmpty() || !this.professionnelSanteRepository.existsById(dto.getId())) {
                    throw new ObjectValidationException("Email existe déjà", null);
                } else {
                    ProfessionelSante professionnel = this.professionnelSanteRepository.findById(dto.getId()).get();
                    if (!professionnel.getEmail().equalsIgnoreCase(dto.getEmail())) {
                        throw new ObjectValidationException("Email existe déjà", null);
                    }
                }
            }
        }

        // Validation du mot de passe pour un nouveau professionnel
        if (dto.getId() == null || dto.getId().trim().isEmpty()) {
            if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
                throw new ObjectValidationException("Mot de passe obligatoire", null);
            }
            if (dto.getConfirmPassword() == null || dto.getConfirmPassword().trim().isEmpty()) {
                throw new ObjectValidationException("Confirmation du mot de passe obligatoire", null);
            }
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                throw new ObjectValidationException("Confirmation et mot de passe ne correspondent pas", null);
            }
        }
    }
}


