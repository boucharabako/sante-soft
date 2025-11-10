/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.services;

import com.base.frame.achat.fonctionnelle.daos.CommandeDAO;
import com.base.frame.achat.fonctionnelle.dtos.CommandeDTO;
import com.base.frame.achat.fonctionnelle.dtos.CommandePannierDTO;
import com.base.frame.achat.fonctionnelle.dtos.ProduitPannierDTO;
import com.base.frame.achat.fonctionnelle.entities.Commande;
import com.base.frame.achat.fonctionnelle.entities.DetailCommande;
import com.base.frame.achat.fonctionnelle.entities.Ordonnance;
import com.base.frame.achat.fonctionnelle.repositories.CommandeRepository;
import com.base.frame.achat.fonctionnelle.repositories.DetailCommandeRepository;
import com.base.frame.achat.fonctionnelle.repositories.OrdonnanceRepository;
import com.base.frame.achat.fonctionnelle.repositories.ProduitRepository;
import java.math.BigDecimal;
import java.time.Instant;
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
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    @Autowired
    private DetailCommandeRepository detailCommandeRepository;
    @Autowired
    private OrdonnanceRepository ordonnanceRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private CommandeDAO commandeDAO;

    @Transactional
    public CommandeDTO mapEntityIntoDTO(Commande entity) {
        CommandeDTO dTO = new CommandeDTO();

        dTO.setId(entity.getId());
        dTO.setNumeroCommande(entity.getNumeroCommande());
        dTO.setDateCommande(entity.getDateCommande().toString());
        dTO.setPrixTotalCommande(entity.getPrixTotalCommande());
        dTO.setEtatCommande(entity.getEtatCommande());
        dTO.setNumeroTelClient(entity.getNumeroTelClient());
        dTO.setNomClient(entity.getNomClient());
        dTO.setPrenomClient(entity.getPrenomClient());
        dTO.setEmailClient(entity.getEmailClient());
        Optional<Ordonnance> ord = this.ordonnanceRepository.findOrdoByCommande(entity.getId());
        if (ord.isPresent()) {
            dTO.setTypeFichier(ord.get().getTypeFichier());
            String baseEncoder = Base64.getEncoder().encodeToString(ord.get().getImageOrdonnance());
            dTO.setFichier(ord.get().getTypeFichier()+","+baseEncoder);
        }   
        dTO.setListDetailCommande(this.detailCommandeRepository.listDesDetailsCommande(entity.getId()));

        return dTO;
    }

    public CommandePannierDTO saveCommande(CommandePannierDTO commandePannierDTO) {
        BigDecimal prixTotal = BigDecimal.ZERO;
        //this.controleValidationObjetUtilisateur(dto);

        Commande entity = new Commande();

        entity.setNumeroCommande("COM-" + this.commandeRepository.count());
        entity.setDateCommande(Instant.now());
        entity.setPrixTotalCommande(commandePannierDTO.getTotal());
        entity.setNumeroTelClient(commandePannierDTO.getNumeroTelephone());
        entity.setNomClient(commandePannierDTO.getNomClient());
        entity.setPrenomClient(commandePannierDTO.getPrenomsClient());
        entity.setEmailClient(commandePannierDTO.getEmailClient());

        entity = this.commandeRepository.save(entity);

        for (ProduitPannierDTO it : commandePannierDTO.getProduits()) {
            DetailCommande detailCommande = new DetailCommande();
            detailCommande.setIdCommande(entity.getId());
            detailCommande.setPrixUnitaire(new BigDecimal(it.getPrix()));
            detailCommande.setQuantiteCommande(it.getQuantite());
            detailCommande.setProduit(produitRepository.findProduitByCode(it.getCode()).getId());
            detailCommande.setTotal(new BigDecimal(it.getPrix() * it.getQuantite()));
            detailCommandeRepository.save(detailCommande);
        }

        Ordonnance ordonnance = new Ordonnance();
        ordonnance.setCommandeCorresp(entity.getId());
        ordonnance.setDateTelechargement(Instant.now());
        if (commandePannierDTO.getOrdonnance() != null) {
            byte[] imageBytes = Base64.getDecoder().decode(commandePannierDTO.getOrdonnance().split(",")[1]);
            ordonnance.setImageOrdonnance(imageBytes);
            ordonnance.setTypeFichier(commandePannierDTO.getOrdonnance().split(",")[0]);
        }

        ordonnanceRepository.save(ordonnance);
        return commandePannierDTO;
    }

    public void deleteCommande(String id) {
        if (this.commandeRepository.existsById(id)) {
            this.commandeRepository.deleteById(id);
        }
    }

    public Optional<Commande> findcommandeById(String id) {
        return this.commandeRepository.findById(id);
    }

    public CommandeDTO getCommande(String id) {
        CommandeDTO t = new CommandeDTO();
        if (this.commandeRepository.existsById(id)) {
            t = this.mapEntityIntoDTO(this.commandeRepository.findById(id).get());
        }
        return t;
    }

    public Page<CommandeDTO> findBySpecTerm(String mc, Instant date, Pageable pageRequest) {
        Page<Commande> listResult;

        listResult = this.commandeDAO.findPageCommande(mc, pageRequest, date);

        return this.mapEntityPageIntoDTOPage(pageRequest, listResult);
    }

    public Page<CommandeDTO> mapEntityPageIntoDTOPage(Pageable page, Page<Commande> source) {
        List<CommandeDTO> list = mapEntitiesIntoDTOs(source.getContent());
        return new PageImpl<>(list, page, source.getTotalElements());
    }

    public List<CommandeDTO> mapEntitiesIntoDTOs(List<Commande> entities) {
        List<CommandeDTO> result = new ArrayList<>();
        entities.stream().map(temp -> this.mapEntityIntoDTO(temp)).forEachOrdered(obj -> {
            result.add(obj);
        });
        return result;
    }

    public List<CommandeDTO> getAllCommande() {
        List<Commande> listResult = this.commandeRepository.findAll();

        return this.mapEntitiesIntoDTOs(listResult);
    }
}
