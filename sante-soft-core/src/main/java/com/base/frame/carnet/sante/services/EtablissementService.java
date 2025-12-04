/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.daos.EtablissementDAO;
import com.base.frame.carnet.sante.dtos.EtablissementDTO;
import com.base.frame.carnet.sante.dtos.ParamListDTO;
import com.base.frame.carnet.sante.entities.Etablissement;
import com.base.frame.carnet.sante.repositories.EtablissementRepository;
import com.base.frame.carnet.sante.repositories.ParamListDTORepository;
import com.base.frame.socle.core.iservice.ISocleGenericService;
import com.base.frame.socle.core.utils.SocleConstant;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author Bouchara
 */
@Service
@Transactional
public class EtablissementService {

    @Autowired
    private EtablissementRepository etablissementRepository;
    @Autowired
    private EtablissementDAO etablissementDAO;
    @Autowired
    private ISocleGenericService socleGenericService;
    @Autowired
    private ParamListDTORepository paramListDTORepository;

    @Transactional
    public EtablissementDTO mapEntityIntoDTO(Etablissement entity) {
        EtablissementDTO dTO = new EtablissementDTO();

        dTO.setId(entity.getId());
        dTO.setCodeEtablissement(entity.getCodeEtablissement());
        dTO.setLibelleEtablissement(entity.getLibelleEtablissement());
        dTO.setRegionEtablissement(entity.getRegionEtablissement());
        dTO.setTypeEtablissement(entity.getTypeEtablissement());
        dTO.setAdresse(entity.getAdresse());

        // Récupérer le libellé du type d'établissement
        if (entity.getTypeEtablissement() != null && !entity.getTypeEtablissement().isEmpty()) {
            try {
                dTO.setTypeEtablissementLibelle(
                    this.socleGenericService.findParamListById(entity.getTypeEtablissement()).get().getLibelle()
                );
            } catch (Exception e) {
                dTO.setTypeEtablissementLibelle("");
            }
        }

        // Récupérer le libellé de la région
        if (entity.getRegionEtablissement() != null && !entity.getRegionEtablissement().isEmpty()) {
            try {
                dTO.setRegionLibelle(
                    this.socleGenericService.findParamListById(entity.getRegionEtablissement()).get().getLibelle()
                );
            } catch (Exception e) {
                dTO.setRegionLibelle("");
            }
        }

        return dTO;
    }

    public EtablissementDTO saveEtablissement(EtablissementDTO dto) {
        Etablissement entity = new Etablissement();
        if (dto.getId() != null && this.etablissementRepository.existsById(dto.getId())) {
            entity = this.etablissementRepository.findById(dto.getId()).get();
        }
        entity.setCodeEtablissement(dto.getCodeEtablissement());
        entity.setLibelleEtablissement(dto.getLibelleEtablissement());
        entity.setRegionEtablissement(dto.getRegionEtablissement());
        entity.setTypeEtablissement(dto.getTypeEtablissement());
        entity.setAdresse(dto.getAdresse());

        dto.setId(entity.getId());
        this.etablissementRepository.save(entity);
        return dto;
    }

    public void deleteEtablissement(String id) {
        if (this.etablissementRepository.existsById(id)) {
            this.etablissementRepository.deleteById(id);
        }
    }

    public Optional<Etablissement> findEtablissementById(String id) {
        return this.etablissementRepository.findById(id);
    }

    public EtablissementDTO getEtablissement(String id) {
        EtablissementDTO t = new EtablissementDTO();
        if (this.etablissementRepository.existsById(id)) {
            t = this.mapEntityIntoDTO(this.etablissementRepository.findById(id).get());
        }
        return t;
    }

    public Page<EtablissementDTO> findBySpecTerm(String code, String mc, String libelle, String region, String typeEtablissement, Pageable pageRequest) {
        Page<Etablissement> listResult;

        listResult = this.etablissementDAO.findPageEtablissement(code, pageRequest, libelle, region, typeEtablissement, mc);

        return this.mapEntityPageIntoDTOPage(pageRequest, listResult);
    }

    public Page<EtablissementDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Etablissement> source) {
        List<EtablissementDTO> list = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(list, page, source.getTotalElements());
    }

    public List<EtablissementDTO> mapEntitiesIntoDTOs(List<Etablissement> entities) {
        List<EtablissementDTO> result = new ArrayList<>();
        entities.stream().map(temp -> this.mapEntityIntoDTO(temp)).forEachOrdered(obj -> {
            result.add(obj);
        });
        return result;
    }

    public List<EtablissementDTO> getAllEtablissement(String mc) {
        return this.mapEntitiesIntoDTOs(etablissementDAO.findListeEtablissement(mc));
    }

    /**
     * Récupérer la liste des régions
     */
    public List<ParamListDTO> getListeRegions() {
        return paramListDTORepository.getParamListDTO(SocleConstant.CODIFICATION_REGION);
    }
}

