/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.daos.AffectationDAO;
import com.base.frame.carnet.sante.dtos.AffectationDTO;
import com.base.frame.carnet.sante.entities.Affectation;
import com.base.frame.carnet.sante.repositories.AffectationRepository;
import com.base.frame.carnet.sante.repositories.EtablissementRepository;
import com.base.frame.carnet.sante.repositories.ParamListDTORepository;
import com.base.frame.socle.core.entity.ParamList;
import com.base.frame.socle.core.iservice.ISocleGenericService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bouchara
 */
@Service
@Transactional
public class AffectationService {

    @Autowired
    private AffectationRepository affectationRepository;
    
    @Autowired
    private AffectationDAO affectationDAO;
    
    @Autowired
    private ISocleGenericService socleGenericService;
    
    @Autowired
    private EtablissementRepository etablissementRepository;
    
    @Autowired
    private ParamListDTORepository paramListDTORepository;

    /**
     * Mapper une entité Affectation vers un DTO
     */
    public AffectationDTO mapEntityIntoDTO(Affectation entity) {
        if (entity == null) {
            return null;
        }
        
        AffectationDTO dto = new AffectationDTO();
        dto.setId(entity.getId());
        dto.setIdProfessionnel(entity.getIdProfessionnel());
        dto.setIdSpecialite(entity.getIdSpecialite());
        dto.setIdEtablissement(entity.getIdEtablissement());
        dto.setDateDebut(entity.getDateDebut());
        dto.setDateFin(entity.getDateFin());
        
        // Récupérer le libellé de la spécialité
        if (entity.getIdSpecialite() != null) {
            Optional<ParamList> specialite = socleGenericService.findParamListById(entity.getIdSpecialite());
            if (specialite.isPresent()) {
                dto.setSpecialiteLibelle(specialite.get().getLibelle());
            }
        }
        
        // Récupérer le libellé de l'établissement
        if (entity.getIdEtablissement() != null) {
            etablissementRepository.findById(entity.getIdEtablissement()).ifPresent(etab -> {
                dto.setEtablissementLibelle(etab.getLibelleEtablissement());
            });
        }
        
        return dto;
    }

    /**
     * Mapper une liste d'entités vers une liste de DTOs
     */
    public List<AffectationDTO> mapEntitiesIntoDTOs(List<Affectation> entities) {
        List<AffectationDTO> dtos = new ArrayList<>();
        if (entities != null && !entities.isEmpty()) {
            entities.forEach(entity -> {
                dtos.add(mapEntityIntoDTO(entity));
            });
        }
        return dtos;
    }

    /**
     * Sauvegarder ou mettre à jour une affectation
     */
    public AffectationDTO saveAffectation(AffectationDTO dto) {
        Affectation affectation;
        
        if (dto.getId() != null && !dto.getId().isEmpty()) {
            // Mise à jour
            Optional<Affectation> existingOpt = affectationRepository.findById(dto.getId());
            if (existingOpt.isPresent()) {
                affectation = existingOpt.get();
            } else {
                affectation = new Affectation();
            }
        } else {
            // Création
            affectation = new Affectation();
            // Date de début par défaut = maintenant
            if (dto.getDateDebut() == null) {
                dto.setDateDebut(Instant.now());
            }
        }
        
        affectation.setIdProfessionnel(dto.getIdProfessionnel());
        affectation.setIdSpecialite(dto.getIdSpecialite());
        affectation.setIdEtablissement(dto.getIdEtablissement());
        affectation.setDateDebut(dto.getDateDebut());
        affectation.setDateFin(dto.getDateFin());
        
        Affectation saved = affectationRepository.save(affectation);
        return mapEntityIntoDTO(saved);
    }

    /**
     * Supprimer une affectation
     */
    public void deleteAffectation(String id) {
        affectationRepository.deleteById(id);
    }

    /**
     * Récupérer une affectation par son ID
     */
    public AffectationDTO getAffectation(String id) {
        Optional<Affectation> affectation = affectationRepository.findById(id);
        return affectation.map(this::mapEntityIntoDTO).orElse(null);
    }

    /**
     * Récupérer toutes les affectations d'un professionnel
     */
    public List<AffectationDTO> getAffectationsByProfessionnel(String idProfessionnel) {
        List<Affectation> affectations = affectationDAO.findAffectationsByProfessionnel(idProfessionnel);
        return mapEntitiesIntoDTOs(affectations);
    }

    /**
     * Récupérer toutes les affectations d'un établissement
     */
    public List<AffectationDTO> getAffectationsByEtablissement(String idEtablissement) {
        List<Affectation> affectations = affectationDAO.findAffectationsByEtablissement(idEtablissement);
        return mapEntitiesIntoDTOs(affectations);
    }
}

