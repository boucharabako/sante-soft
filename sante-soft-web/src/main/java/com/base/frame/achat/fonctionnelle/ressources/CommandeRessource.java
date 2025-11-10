/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.ressources;

import com.base.frame.achat.fonctionnelle.dtos.CommandeDTO;
import com.base.frame.achat.fonctionnelle.dtos.CommandePannierDTO;
import com.base.frame.achat.fonctionnelle.services.CommandeService;
import com.base.frame.socle.utils.Constants;
import com.base.frame.socle.utils.Pager;
import com.base.frame.socle.utils.validators.MessageSourceKV;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(path = "fonctionnelle/commande")
public class CommandeRessource {

    @Autowired
    private CommandeService commandeService;
    @Autowired
    private MessageSourceKV messageSource;

    @RequestMapping(value = "/saveOrUpdateCommande", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> saveOrUpdateCommande(@RequestBody CommandeDTO commandeDto) throws CloneNotSupportedException {
        HashMap<String, Object> modeHashMap = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
//        CommandeDTO result = this.commandeService.saveCommande(commandeDto);
//  
        headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));
        return ResponseEntity.accepted().headers(headers).body(modeHashMap);
    }

    @RequestMapping(value = "/listAllCommande", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listAllCommande() {
        HashMap<String, Object> model = new HashMap<>();

        List<CommandeDTO> listCommande = this.commandeService.getAllCommande();

        model.put("listCommande", listCommande);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        System.out.println("======================== listUtilisateur " + listCommande.size());
        System.out.println("======================== listUtilisateur" + listCommande.toString());
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/list/{pageSize}/{page}", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listCommande(
            @PathVariable("pageSize") Optional<Integer> pageSize, @PathVariable("page") Optional<Integer> page,
            @RequestParam(required = false, name = "mc") String mc,
            @RequestParam(required = false, name = "date") Instant date) {
        HashMap<String, Object> model = new HashMap<>();

        int p = 0;
        int evalPageSize = pageSize.orElse(Pager.INITIAL_PAGE_SIZE);
        int evalPage = 0;
        if (page.isPresent()) {
            evalPage = (page.orElse(0) < 1) ? Pager.INITIAL_PAGE : page.get() - 1;
        }

        Page<CommandeDTO> listCommande = this.commandeService.findBySpecTerm(mc, date, PageRequest.of(evalPage, evalPageSize));

        Pager pager = new Pager(listCommande.getTotalPages(), listCommande.getNumber(), Pager.BUTTONS_TO_SHOW);
        model.put("pageSize", Pager.getPageSize());
        model.put("pager", pager);
        model.put("sequence", Pager.customNumberSequence(pager.getStartPage(), pager.getEndPage()));
        model.put("selectedPageSize", evalPageSize);
        model.put("listCommande", listCommande);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        System.out.println("======================== listProfil " + listCommande.getContent().size());
        //System.out.println("======================== listProfil" + listProfil.getContent().toString());
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/deleteCommande/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HashMap<String, Object>> deleteCommande(@PathVariable("id") String id) {
        try {
            HashMap<String, Object> modeHashMap = new HashMap<>();

            this.commandeService.deleteCommande(id);

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
    @RequestMapping(value = "/getCommande/{id}", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getCommande(@PathVariable("id") String id) {
        HashMap<String, Object> modeHashMap = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        if (id != null && this.commandeService.getCommande(id) != null) {
            modeHashMap.put("commandeDTO", this.commandeService.getCommande(id));
        }
        return ResponseEntity.accepted().headers(headers).body(modeHashMap);
    }

//    @RequestMapping(value = "/listTypeCommande", method = RequestMethod.GET)
//    public ResponseEntity<HashMap<String, Object>> listAllTypeCommande() {
//        HashMap<String, Object> model = new HashMap<>();
//        List<TypeCommandeDTO> listTypeCommande = this.typeCommandeRepository.getListTypeCommande();
//        model.put("listTypeCommande", listTypeCommande);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
//        return ResponseEntity.accepted().headers(headers).body(model);
//
//    }
    @RequestMapping(value = "/saveCommandePannier", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> saveCommandePannier(
            @RequestBody CommandePannierDTO commande) throws CloneNotSupportedException {
        HashMap<String, Object> modeHashMap = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        // Process the received data
        // Save the command and handle the file if necessary
        // Example processing (you can replace this with your actual processing logic)
        CommandePannierDTO result = this.commandeService.saveCommande(commande);

        headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));
        return ResponseEntity.accepted().headers(headers).body(modeHashMap);
    }
}
