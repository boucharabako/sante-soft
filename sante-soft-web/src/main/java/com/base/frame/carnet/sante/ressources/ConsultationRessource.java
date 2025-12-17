/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.ressources;

import com.base.frame.carnet.sante.entities.CategorieAntecedent;
import com.base.frame.carnet.sante.entities.TypeAntecedant;
import com.base.frame.carnet.sante.entities.TypeObservation;
import com.base.frame.carnet.sante.repositories.CategorieAntecedentRepository;
import com.base.frame.carnet.sante.repositories.TypeAntecedentRepository;
import com.base.frame.carnet.sante.repositories.TypeObservationRepository;
import com.base.frame.socle.utils.Constants;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bouchara
 */
@RestController
@RequestMapping(path = "/api/consultation")
public class ConsultationRessource {

    @Autowired
    private TypeObservationRepository typeObservationRepository;

    @Autowired
    private CategorieAntecedentRepository categorieAntecedentRepository;

    @Autowired
    private TypeAntecedentRepository typeAntecedentRepository;

    /**
     * Récupère tous les types d'observations avec leurs unités et valeurs min/max
     * @return Liste des types d'observations
     */
    @RequestMapping(value = "/listTypesObservation", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listTypesObservation() {
        HashMap<String, Object> model = new HashMap<>();

        List<TypeObservation> listTypesObservation = typeObservationRepository.findAll();

        model.put("listTypesObservation", listTypesObservation);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listTypesObservation " + listTypesObservation.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Récupère toutes les catégories d'antécédents
     * @return Liste des catégories d'antécédents
     */
    @RequestMapping(value = "/listCategoriesAntecedent", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listCategoriesAntecedent() {
        HashMap<String, Object> model = new HashMap<>();

        List<CategorieAntecedent> listCategoriesAntecedent = categorieAntecedentRepository.findAll();

        model.put("listCategoriesAntecedent", listCategoriesAntecedent);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listCategoriesAntecedent " + listCategoriesAntecedent.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Récupère tous les types d'antécédents par catégorie
     * @param idCategorie ID de la catégorie
     * @return Liste des types d'antécédents
     */
    @RequestMapping(value = "/listTypesAntecedentByCategorie", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listTypesAntecedentByCategorie(
            @RequestParam(value = "idCategorie", required = false) String idCategorie) {
        HashMap<String, Object> model = new HashMap<>();

        List<TypeAntecedant> listTypesAntecedent;

        if (idCategorie != null && !idCategorie.isEmpty()) {
            listTypesAntecedent = typeAntecedentRepository.findByIdCategorieAntecedent(idCategorie);
        } else {
            listTypesAntecedent = typeAntecedentRepository.findAll();
        }

        model.put("listTypesAntecedent", listTypesAntecedent);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        System.out.println("======================== listTypesAntecedentByCategorie " + listTypesAntecedent.size());

        return ResponseEntity.accepted().headers(headers).body(model);
    }
}

