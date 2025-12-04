/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.ressources;

import com.base.frame.carnet.sante.dtos.ParamListDTO;
import com.base.frame.carnet.sante.dtos.PatientDTO;
import com.base.frame.carnet.sante.repositories.ParamListDTORepository;
import com.base.frame.carnet.sante.services.PatientService;
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
@RequestMapping(path = "/api/patient")
public class PatientRessource {

    @Autowired
    private PatientService patientService;

    @Autowired
    private MessageSourceKV messageSource;
    @Autowired
    private ISocleGenericService socleGenericService;
    @Autowired
    private ParamListDTORepository paramListDTORepository;

    @RequestMapping(value = "/saveOrUpdatePatient", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> saveOrUpdatePatient(@RequestBody PatientDTO patientDto) throws CloneNotSupportedException {
        HashMap<String, Object> modeHashMap = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        PatientDTO result = this.patientService.savePatient(patientDto);
        
        headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));
        return ResponseEntity.accepted().headers(headers).body(modeHashMap);
    }

    @RequestMapping(value = "/listAllPatient", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listAllPatient(@RequestParam(required = false, name = "mc") String mc) {
        HashMap<String, Object> model = new HashMap<>();

        List<PatientDTO> listPatient = this.patientService.getAllPatient(mc);

        model.put("listPatient", listPatient);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        System.out.println("======================== listPatient " + listPatient.size());
        System.out.println("======================== listPatient" + listPatient.toString());
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/getPatient", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getPatient(@RequestParam(required = true, name = "id") String id) {
        HashMap<String, Object> model = new HashMap<>();

        PatientDTO patient = this.patientService.getPatient(id);

        model.put("patient", patient);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/deletePatient", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> deletePatient(@RequestParam(required = true, name = "id") String id) {
        HashMap<String, Object> model = new HashMap<>();

        this.patientService.deletePatient(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));
        return ResponseEntity.accepted().headers(headers).body(model);

    }

    @RequestMapping(value = "/paginatePatient", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> paginatePatient(
            @RequestParam(required = false, name = "numeroCarnet") String numeroCarnet,
            @RequestParam(required = false, name = "groupeSanguin") String groupeSanguin,
            @RequestParam(required = false, name = "mc") String mc,
            @RequestParam(required = false, name = "page") int page,
            @RequestParam(required = false, name = "size") int size) {
        HashMap<String, Object> model = new HashMap<>();
        Pageable pageRequest = PageRequest.of(page, size);
        Page<PatientDTO> listPatient = this.patientService.findBySpecTerm(numeroCarnet, mc, groupeSanguin, pageRequest);

        model.put("listPatient", listPatient);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);

    }
    
    @RequestMapping(value = "/listSexe", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listSexe() {
        HashMap<String, Object> model = new HashMap<>();
        //List<ParamList> listSexe = this.socleGenericService.findParamListByCodeParamCode(SocleConstant.CODIFICATION_SEXE);
        List<ParamListDTO> listSexe = this.paramListDTORepository.getParamListDTO(SocleConstant.CODIFICATION_SEXE);
        model.put("listSexe", listSexe);
        System.out.println("LISTE SEXE *******"+listSexe);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);
    }
    
   @RequestMapping(value = "/listGroupeSanguin", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listGroupeSanguin() {
        HashMap<String, Object> model = new HashMap<>();
        List<ParamListDTO> listGroupeSanguin = this.paramListDTORepository.getParamListDTO(SocleConstant.CODIFICATION_GROUPE_SANGUIN);
        model.put("listGroupeSanguin", listGroupeSanguin);
        System.out.println("LISTE GROUPE SANGUIN *******"+listGroupeSanguin);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);
    }

    @RequestMapping(value = "/getNextNumeroCarnet", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getNextNumeroCarnet() {
        HashMap<String, Object> model = new HashMap<>();
        String nextNumeroCarnet = this.patientService.getNextNumeroCarnet();
        model.put("nextNumeroCarnet", nextNumeroCarnet);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);
    }
}

