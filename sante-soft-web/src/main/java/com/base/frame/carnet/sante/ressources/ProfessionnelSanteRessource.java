/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.ressources;

import com.base.frame.carnet.sante.dtos.ParamListDTO;
import com.base.frame.carnet.sante.dtos.ProfessionnelSanteDTO;
import com.base.frame.carnet.sante.repositories.ParamListDTORepository;
import com.base.frame.carnet.sante.services.ProfessionnelSanteService;
import com.base.frame.socle.core.iservice.ISocleGenericService;
import com.base.frame.socle.core.utils.SocleConstant;
import com.base.frame.socle.utils.Constants;
import com.base.frame.socle.utils.validators.MessageSourceKV;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(path = "/api/professionnel")
public class ProfessionnelSanteRessource {

    @Autowired
    private ProfessionnelSanteService professionnelSanteService;

    @Autowired
    private MessageSourceKV messageSource;
    @Autowired
    private ISocleGenericService socleGenericService;
    @Autowired
    private ParamListDTORepository paramListDTORepository;

    @RequestMapping(value = "/saveOrUpdateProfessionnel", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> saveOrUpdateProfessionnel(@RequestBody ProfessionnelSanteDTO professionnelDto) throws CloneNotSupportedException {
        HashMap<String, Object> modeHashMap = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        ProfessionnelSanteDTO result = this.professionnelSanteService.saveProfessionnelSante(professionnelDto);

        modeHashMap.put("professionnelSanteDTO", result);
        headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));
        return ResponseEntity.accepted().headers(headers).body(modeHashMap);
    }

    @RequestMapping(value = "/listAllProfessionnel", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listAllProfessionnel(@RequestParam(required = false, name = "mc") String mc) {
        HashMap<String, Object> model = new HashMap<>();

        List<ProfessionnelSanteDTO> listProfessionnel = this.professionnelSanteService.getAllProfessionnelSante(mc);

        model.put("listProfessionnelSante", listProfessionnel);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        System.out.println("======================== listProfessionnelSante " + listProfessionnel.size());
        System.out.println("======================== listProfessionnelSante" + listProfessionnel.toString());
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/getProfessionnel", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getProfessionnel(@RequestParam(required = true, name = "id") String id) {
        HashMap<String, Object> model = new HashMap<>();

        ProfessionnelSanteDTO professionnel = this.professionnelSanteService.getProfessionnelSante(id);

        model.put("professionnelSante", professionnel);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/deleteProfessionnel", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> deleteProfessionnel(@RequestParam(required = true, name = "id") String id) {
        HashMap<String, Object> model = new HashMap<>();

        this.professionnelSanteService.deleteProfessionnelSante(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/paginateProfessionnel", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> paginateProfessionnel(
            @RequestParam(required = false, name = "numeroOrdre") String numeroOrdre,
            @RequestParam(required = false, name = "specialite") String specialite,
            @RequestParam(required = false, name = "mc") String mc,
            @RequestParam(required = false, name = "page") int page,
            @RequestParam(required = false, name = "size") int size) {
        HashMap<String, Object> model = new HashMap<>();
        Pageable pageRequest = PageRequest.of(page, size);
        Page<ProfessionnelSanteDTO> listProfessionnel = this.professionnelSanteService.findBySpecTerm(numeroOrdre, mc, specialite, pageRequest);

        model.put("listProfessionnel", listProfessionnel);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/listSexe", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listSexe() {
        HashMap<String, Object> model = new HashMap<>();
        List<ParamListDTO> listSexe = this.paramListDTORepository.getParamListDTO(SocleConstant.CODIFICATION_SEXE);
        model.put("listSexe", listSexe);
        System.out.println("LISTE SEXE *******"+listSexe);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);
    }

   @RequestMapping(value = "/listSpecialite", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listSpecialite() {
        HashMap<String, Object> model = new HashMap<>();
        List<ParamListDTO> listSpecialite = this.paramListDTORepository.getParamListDTO(SocleConstant.CODIFICATION_SPECIALITE);
        model.put("listSpecialite", listSpecialite);
        System.out.println("LISTE SPECIALITE *******"+listSpecialite);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);
    }

    @RequestMapping(value = "/getNextNumeroOrdre", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getNextNumeroOrdre() {
        HashMap<String, Object> model = new HashMap<>();
        String nextNumeroOrdre = this.professionnelSanteService.getNextNumeroOrdre();
        model.put("nextNumeroOrdre", nextNumeroOrdre);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);
    }
}


