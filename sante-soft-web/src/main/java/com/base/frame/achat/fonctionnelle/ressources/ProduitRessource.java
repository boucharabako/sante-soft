/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.ressources;

import com.base.frame.achat.fonctionnelle.dtos.ProduitDTO;
import com.base.frame.achat.fonctionnelle.dtos.ProduitDTO;
import com.base.frame.achat.fonctionnelle.dtos.TypeProduitDTO;
import com.base.frame.achat.fonctionnelle.repositories.TypeProduitRepository;
import com.base.frame.achat.fonctionnelle.services.ProduitService;
import com.base.frame.socle.core.codification.utils.ApiError;
import com.base.frame.socle.core.codification.utils.ErrorMessage;
import com.base.frame.socle.core.codification.utils.RestConstants;
import com.base.frame.socle.core.codification.utils.TypeError;
import com.base.frame.socle.core.workflow.dto.WorkflowCycleParameter;
import com.base.frame.socle.utils.Constants;
import com.base.frame.socle.utils.Pager;
import com.base.frame.socle.utils.validators.MessageSourceKV;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bouchara
 */
@RestController
@RequestMapping(path = "fonctionnelle/produit")
public class ProduitRessource {

    @Autowired
    private ProduitService produitService;
    @Autowired
    private MessageSourceKV messageSource;
    @Autowired
    private TypeProduitRepository typeProduitRepository;

    @RequestMapping(value = "/saveOrUpdateProduit", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> saveOrUpdateProduit(@RequestBody ProduitDTO produitDto) throws CloneNotSupportedException {
        HashMap<String, Object> modeHashMap = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        ProduitDTO result = this.produitService.saveProduit(produitDto);
//  
        headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));
        return ResponseEntity.accepted().headers(headers).body(modeHashMap);
        //return ResponseEntity.accepted().headers(headers).body(modeHashMap);
    }

    @RequestMapping(value = "/listAllProduit", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listAllProduit(@RequestParam(required = false, name = "mc") String mc) {
        HashMap<String, Object> model = new HashMap<>();

        List<ProduitDTO> listProduit = this.produitService.getAllProduit(mc);

        model.put("listProduit", listProduit);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        System.out.println("======================== listUtilisateur " + listProduit.size());
        System.out.println("======================== listUtilisateur" + listProduit.toString());
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/list/{pageSize}/{page}", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listProduit(
            @PathVariable("pageSize") Optional<Integer> pageSize, @PathVariable("page") Optional<Integer> page,
            @RequestParam(required = false, name = "mc") String mc,
            @RequestParam(required = false, name = "code") String code,
            @RequestParam(required = false, name = "libelle") String libelle,
            @RequestParam(required = false, name = "description") String description,
            @RequestParam(required = false, name = "typeProduit") String typeProduit) {
        HashMap<String, Object> model = new HashMap<>();

        int p = 0;
        int evalPageSize = pageSize.orElse(Pager.INITIAL_PAGE_SIZE);
        int evalPage = 0;
        if (page.isPresent()) {
            evalPage = (page.orElse(0) < 1) ? Pager.INITIAL_PAGE : page.get() - 1;
        }

        Page<ProduitDTO> listProduit = this.produitService.findBySpecTerm(code, mc, libelle, description, typeProduit, PageRequest.of(evalPage, evalPageSize));

        Pager pager = new Pager(listProduit.getTotalPages(), listProduit.getNumber(), Pager.BUTTONS_TO_SHOW);
        model.put("pageSize", Pager.getPageSize());
        model.put("pager", pager);
        model.put("sequence", Pager.customNumberSequence(pager.getStartPage(), pager.getEndPage()));
        model.put("selectedPageSize", evalPageSize);
        model.put("listProduit", listProduit);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        System.out.println("======================== listProfil " + listProduit.getContent().size());
        //System.out.println("======================== listProfil" + listProfil.getContent().toString());
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/deleteProduit/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HashMap<String, Object>> deleteProduit(@PathVariable("id") String id) {
        try {
            HashMap<String, Object> modeHashMap = new HashMap<>();

            this.produitService.deleteProduit(id);

            return ResponseEntity.accepted().body(modeHashMap);
        } catch (Exception e) {
            throw new Error("Impossible");
            //throw new InternalServerException("Impossible", null);
        }
    }

    /**
     * Cette méthode récupère les informations d'un Utilisateur
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getProduit/{id}", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getProduit(@PathVariable("id") String id) {
        HashMap<String, Object> modeHashMap = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        if (id != null && this.produitService.getProduit(id) != null) {
            modeHashMap.put("produitDTO", this.produitService.getProduit(id));
        }
        return ResponseEntity.accepted().headers(headers).body(modeHashMap);
    }
    

    @RequestMapping(value = "/listTypeProduit", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listAllTypeProduit() {
        HashMap<String, Object> model = new HashMap<>();
        List<TypeProduitDTO> listTypeProduit = this.typeProduitRepository.getListTypeProduit();
        model.put("listTypeProduit", listTypeProduit);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);

    }

}
