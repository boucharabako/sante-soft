package com.base.frame.carnet.sante.ressources;

import com.base.frame.carnet.sante.dtos.VaccinationDTO;
import com.base.frame.carnet.sante.services.VaccinationService;
import com.base.frame.socle.utils.Constants;
import com.base.frame.socle.utils.validators.MessageSourceKV;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller pour la gestion des vaccinations
 * @author Bouchara
 */
@RestController
@RequestMapping(path = "/api/vaccination")
public class VaccinationRessource {

    @Autowired
    private VaccinationService vaccinationService;

//    @Autowired
//    private MessageSource messageSource;
    @Autowired
    private MessageSourceKV messageSource;

    /**
     * Enregistrer ou mettre à jour une vaccination
     * @param vaccinationDTO DTO de la vaccination
     * @return Réponse avec l'ID de la vaccination créée
     */
    @RequestMapping(value = "/saveOrUpdateVaccination", method = RequestMethod.POST)
    public ResponseEntity<HashMap<String, Object>> saveOrUpdateVaccination(@RequestBody VaccinationDTO vaccinationDTO) {
        HashMap<String, Object> model = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        try {
            System.out.println("======================== Enregistrement vaccination");
            System.out.println("Vaccin: " + vaccinationDTO.getVaccin());
            System.out.println("Patient: " + vaccinationDTO.getIdPatient());

            VaccinationDTO savedVaccination = vaccinationService.saveVaccination(vaccinationDTO);

            model.put("success", true);
            model.put("vaccinationId", savedVaccination.getId());
            model.put("vaccination", savedVaccination);

            // Ajouter le message de notification dans le header
            headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));

            return ResponseEntity.accepted().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'enregistrement de la vaccination: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur lors de l'enregistrement: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    /**
     * Récupérer toutes les vaccinations d'un patient
     * @param idPatient ID du patient
     * @return Liste des vaccinations
     */
    @RequestMapping(value = "/listVaccinationsByPatient", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> listVaccinationsByPatient(
            @RequestParam(value = "idPatient") String idPatient) {
        HashMap<String, Object> model = new HashMap<>();

        try {
            System.out.println("======================== Liste vaccinations pour patient: " + idPatient);

            List<VaccinationDTO> vaccinations = vaccinationService.getVaccinationsByPatient(idPatient);

            model.put("listVaccinations", vaccinations);
            model.put("success", true);

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            System.out.println("✅ " + vaccinations.size() + " vaccinations trouvées");

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des vaccinations: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur: " + e.getMessage());
            model.put("listVaccinations", new ArrayList<>());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    /**
     * Récupérer une vaccination par son ID
     * @param id ID de la vaccination
     * @return Vaccination complète
     */
    @RequestMapping(value = "/getVaccination", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getVaccination(
            @RequestParam(value = "id") String id) {
        HashMap<String, Object> model = new HashMap<>();

        try {
            System.out.println("======================== Récupération vaccination: " + id);

            VaccinationDTO vaccination = vaccinationService.getVaccinationById(id);

            if (vaccination != null) {
                model.put("vaccination", vaccination);
                model.put("success", true);

                System.out.println("✅ Vaccination trouvée: " + vaccination.getVaccin());
            } else {
                model.put("success", false);
                model.put("message", "Vaccination non trouvée");
            }

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération de la vaccination: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }

    /**
     * Supprimer une vaccination
     * @param id ID de la vaccination à supprimer
     * @return Réponse de suppression
     */
    @RequestMapping(value = "/deleteVaccination", method = RequestMethod.DELETE)
    public ResponseEntity<HashMap<String, Object>> deleteVaccination(
            @RequestParam(value = "id") String id) {
        HashMap<String, Object> model = new HashMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

        try {
            System.out.println("======================== Suppression vaccination: " + id);

            vaccinationService.deleteVaccination(id);

            model.put("success", true);

            // Ajouter le message de notification dans le header
        headers.add("X-nframe-alert", this.messageSource.getMessage(Constants.OP_SUCCESS_MSG_CODE, new String[]{}));

            System.out.println("✅ Vaccination supprimée");

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la suppression de la vaccination: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
        }
    }
}

