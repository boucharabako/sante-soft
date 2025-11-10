/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.services;

import com.base.frame.achat.fonctionnelle.daos.ProduitDAO;
import com.base.frame.achat.fonctionnelle.dtos.ProduitDTO;
import com.base.frame.achat.fonctionnelle.entities.Produit;
import com.base.frame.achat.fonctionnelle.repositories.ProduitRepository;
import com.base.frame.achat.fonctionnelle.repositories.TypeProduitRepository;
import com.base.frame.socle.core.utils.SocleConstant;
import com.base.frame.socle.core.workflow.dto.ActionDTO;
import com.base.frame.socle.core.workflow.dto.WorkFlowCycleActionSimpleObject;
import com.base.frame.socle.core.workflow.dto.WorkFlowCycleEtatSimpleObject;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
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
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private TypeProduitRepository typeProduitRepository;
    @Autowired
    private ProduitDAO produitDAO;

    @Transactional
    public ProduitDTO mapEntityIntoDTO(Produit entity) {
        ProduitDTO dTO = new ProduitDTO();

        dTO.setId(entity.getId());
        dTO.setLibelle(entity.getLibelle());
        dTO.setCode(entity.getCode());
        dTO.setTypeProduit(entity.getTypeProduit());
        dTO.setLibelleTypeProduit(this.typeProduitRepository.findTypeProduit(entity.getTypeProduit()).getLibelle());
        dTO.setDescription(entity.getDescription());
        dTO.setPrix(entity.getPrix());
        if (entity.getImage() != null) {

            String base64Image = Base64.getEncoder().encodeToString(entity.getImage());

            // Ajouter le préfixe data:image/png;base64, si nécessaire
            String base64ImageWithPrefix = "data:image/png;base64," + base64Image;
            dTO.setImage(base64ImageWithPrefix);
        }

        dTO.setQuantite(entity.getQuantite());

        return dTO;
    }

    public ProduitDTO saveProduit(ProduitDTO dto) {
        //this.controleValidationObjetUtilisateur(dto);
        Produit entity = new Produit();
        if (dto.getId() != null && this.produitRepository.existsById(dto.getId())) {
            entity = this.produitRepository.findById(dto.getId()).get();
        }
        entity.setCode(dto.getCode());
        entity.setDescription(dto.getDescription());
        entity.setLibelle(dto.getLibelle());
        entity.setPrix(dto.getPrix());
        entity.setQuantite(dto.getQuantite());
        byte[] imageBytes = Base64.getDecoder().decode(dto.getImage().split(",")[1]);
        System.out.println("dto:" + dto.getImage().split(",")[1]);
        System.out.println("imageBytes:" + imageBytes);
        entity.setImage(imageBytes);
//entity.setImage(dto.getImage());
        entity.setTypeProduit(dto.getTypeProduit());

        dto.setId(entity.getId());
        this.produitRepository.save(entity);
        return dto;
    }

    public void deleteProduit(String id) {
        if (this.produitRepository.existsById(id)) {
            this.produitRepository.deleteById(id);
        }
    }

    public Optional<Produit> findProduitById(String id) {
        return this.produitRepository.findById(id);
    }

    public ProduitDTO getProduit(String id) {
        ProduitDTO t = new ProduitDTO();
        if (this.produitRepository.existsById(id)) {
            t = this.mapEntityIntoDTO(this.produitRepository.findById(id).get());
        }
        return t;
    }

    public Page<ProduitDTO> findBySpecTerm(String code, String mc, String libelle, String description, String typeProduit, Pageable pageRequest) {
        Page<Produit> listResult;

        listResult = this.produitDAO.findPageProduit(code, pageRequest, libelle, description, typeProduit, mc);

        return this.mapEntityPageIntoDTOPage(pageRequest, listResult);
    }

    public Page<ProduitDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Produit> source) {
        List<ProduitDTO> list = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(list, page, source.getTotalElements());
    }

    public List<ProduitDTO> mapEntitiesIntoDTOs(List<Produit> entities) {
        List<ProduitDTO> result = new ArrayList<>();
        entities.stream().map(temp -> this.mapEntityIntoDTO(temp)).forEachOrdered(obj -> {
            result.add(obj);
        });
        return result;
    }

    public List<ProduitDTO> getAllProduit(String mc) {

        return this.mapEntitiesIntoDTOs(produitDAO.findListeProduit(mc));
    }
//    public List<ProduitDTO> getAllProduit() {
//        List<Produit> listResult = this.produitRepository.findAll();
//
//        return this.mapEntitiesIntoDTOs(listResult);
//    }
}
