/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.ressources;

import com.base.frame.carnet.sante.dtos.AffectationDTO;
import com.base.frame.carnet.sante.services.AffectationService;
import com.base.frame.socle.utils.validators.MessageSourceKV;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bouchara
 */
@RestController
@RequestMapping(path = "fonctionnelle/affectation")
public class AffectationRessource {

    @Autowired
    private AffectationService affectationService;

    @Autowired
    private MessageSourceKV messageSource;

    /**
     * Sauvegarder ou mettre à jour une affectation
     */
    @PostMapping(path = "/saveOrUpdateAffectation")
    public ResponseEntity<Object> saveOrUpdateAffectation(@RequestBody AffectationDTO affectationDTO) {
        try {
            AffectationDTO saved = affectationService.saveAffectation(affectationDTO);
            return new ResponseEntity<>(saved, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur d'enrégistrement", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Récupérer une affectation par son ID
     */
    @GetMapping(path = "/getAffectation/{id}")
    public ResponseEntity<Object> getAffectation(@PathVariable String id) {
        try {
            AffectationDTO affectation = affectationService.getAffectation(id);
            if (affectation != null) {
                return new ResponseEntity<>(affectation, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Non trouvé", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de récupération", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Supprimer une affectation
     */
    @DeleteMapping(path = "/deleteAffectation/{id}")
    public ResponseEntity<Object> deleteAffectation(@PathVariable String id) {
        try {
            affectationService.deleteAffectation(id);
            return new ResponseEntity<>("Supprimé", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur de suppression", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Récupérer toutes les affectations d'un professionnel
     */
    @GetMapping(path = "/listByProfessionnel/{idProfessionnel}")
    public ResponseEntity<Object> getAffectationsByProfessionnel(@PathVariable String idProfessionnel) {
        try {
            List<AffectationDTO> affectations = affectationService.getAffectationsByProfessionnel(idProfessionnel);
            return new ResponseEntity<>(affectations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la récupération", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Récupérer toutes les affectations d'un établissement
     */
    @GetMapping(path = "/listByEtablissement/{idEtablissement}")
    public ResponseEntity<Object> getAffectationsByEtablissement(@PathVariable String idEtablissement) {
        try {
            List<AffectationDTO> affectations = affectationService.getAffectationsByEtablissement(idEtablissement);
            return new ResponseEntity<>(affectations, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de la récupération", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

