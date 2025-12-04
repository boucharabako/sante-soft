/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.ressources;

import com.base.frame.carnet.sante.dtos.EtablissementDTO;
import com.base.frame.carnet.sante.dtos.ParamListDTO;
import com.base.frame.carnet.sante.repositories.ParamListDTORepository;
import com.base.frame.carnet.sante.services.EtablissementService;
import com.base.frame.socle.core.utils.SocleConstant;
import com.base.frame.socle.utils.Constants;
import com.base.frame.socle.utils.Pager;
import com.base.frame.socle.utils.validators.MessageSourceKV;
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
@RequestMapping(path = "fonctionnelle/etablissement")
public class EtablissementRessource {

    @Autowired
    private EtablissementService etablissementService;
    @Autowired
    private MessageSourceKV messageSource;
    @Autowired
    private ParamListDTORepository paramListDTORepository;

    @RequestMapping(value = "/saveOrUpdateEtablissement", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> saveOrUpdateEtablissement(@RequestBody EtablissementDTO etablissementDto) throws CloneNotSupportedException {
        HashMap<String, Object> modeHashMap = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        EtablissementDTO result = this.etablissementService.saveEtablissement(etablissementDto);
  
        headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));
        return ResponseEntity.accepted().headers(headers).body(modeHashMap);
    }

    @RequestMapping(value = "/listAllEtablissement", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listAllEtablissement(@RequestParam(required = false, name = "mc") String mc) {
        HashMap<String, Object> model = new HashMap<>();

        List<EtablissementDTO> listEtablissement = this.etablissementService.getAllEtablissement(mc);

        model.put("listEtablissement", listEtablissement);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        System.out.println("======================== listEtablissement " + listEtablissement.size());
        System.out.println("======================== listEtablissement" + listEtablissement.toString());
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/list/{pageSize}/{page}", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listEtablissement(
            @PathVariable("pageSize") Optional<Integer> pageSize, @PathVariable("page") Optional<Integer> page,
            @RequestParam(required = false, name = "mc") String mc,
            @RequestParam(required = false, name = "code") String code,
            @RequestParam(required = false, name = "libelle") String libelle,
            @RequestParam(required = false, name = "region") String region,
            @RequestParam(required = false, name = "typeEtablissement") String typeEtablissement) {
        HashMap<String, Object> model = new HashMap<>();

        int p = 0;
        int evalPageSize = pageSize.orElse(Pager.INITIAL_PAGE_SIZE);
        int evalPage = 0;
        if (page.isPresent()) {
            evalPage = (page.orElse(0) < 1) ? Pager.INITIAL_PAGE : page.get() - 1;
        }

        Page<EtablissementDTO> listEtablissement = this.etablissementService.findBySpecTerm(code, mc, libelle, region, typeEtablissement, PageRequest.of(evalPage, evalPageSize));

        Pager pager = new Pager(listEtablissement.getTotalPages(), listEtablissement.getNumber(), Pager.BUTTONS_TO_SHOW);
        model.put("pageSize", Pager.getPageSize());
        model.put("pager", pager);
        model.put("sequence", Pager.customNumberSequence(pager.getStartPage(), pager.getEndPage()));
        model.put("selectedPageSize", evalPageSize);
        model.put("listEtablissement", listEtablissement);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        System.out.println("======================== listEtablissement " + listEtablissement.getContent().size());
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/deleteEtablissement/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<HashMap<String, Object>> deleteEtablissement(@PathVariable("id") String id) {
        try {
            HashMap<String, Object> modeHashMap = new HashMap<>();

            this.etablissementService.deleteEtablissement(id);

            return ResponseEntity.accepted().body(modeHashMap);
        } catch (Exception e) {
            throw new Error("Impossible");
        }
    }

    /**
     * Cette méthode récupère les informations d'un Etablissement
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/getEtablissement/{id}", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getEtablissement(@PathVariable("id") String id) {
        HashMap<String, Object> modeHashMap = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        if (id != null && this.etablissementService.getEtablissement(id) != null) {
            modeHashMap.put("etablissementDTO", this.etablissementService.getEtablissement(id));
        }
        return ResponseEntity.accepted().headers(headers).body(modeHashMap);
    }
    

    @RequestMapping(value = "/listTypeEtablissement", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listAllTypeEtablissement() {
        HashMap<String, Object> model = new HashMap<>();
        List<ParamListDTO> listTypeEtablissement = this.paramListDTORepository.getParamListDTO(SocleConstant.CODIFICATION_TYPE_ETABLISSEMENT);
        model.put("listTypeEtablissement", listTypeEtablissement);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/listRegion", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listAllRegion() {
        HashMap<String, Object> model = new HashMap<>();
        List<ParamListDTO> listRegion = etablissementService.getListeRegions();
        model.put("listRegion", listRegion);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);
    }

}

