package com.base.frame.carnet.sante.ressources;

import com.base.frame.carnet.sante.dtos.ParamListDTO;
import com.base.frame.carnet.sante.dtos.StatistiquesDTO;
import com.base.frame.carnet.sante.services.StatistiquesService;
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
 * REST Controller pour les statistiques du tableau de bord
 * @author Bouchara
 */
@RestController
@RequestMapping(path = "/api/statistiques")
public class StatistiquesRessource {

    @Autowired
    private StatistiquesService statistiquesService;

    /**
     * Récupérer toutes les statistiques pour le tableau de bord
     * @param annee Année de filtrage (optionnel)
     * @param idEtablissement ID de l'établissement (optionnel)
     * @param idProfessionnel ID du professionnel (optionnel)
     * @param specialite Spécialité du professionnel (optionnel)
     * @return Statistiques complètes
     */
    @RequestMapping(value = "/tableau-bord", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getStatistiquesTableauBord(
            @RequestParam(required = false, name = "annee") Integer annee,
            @RequestParam(required = false, name = "idEtablissement") String idEtablissement,
            @RequestParam(required = false, name = "idProfessionnel") String idProfessionnel,
            @RequestParam(required = false, name = "specialite") String specialite) {

        HashMap<String, Object> model = new HashMap<>();

        try {
            System.out.println("======================== Calcul des statistiques");
            System.out.println("   Année: " + (annee != null ? annee : "Toutes"));
            System.out.println("   Établissement: " + (idEtablissement != null ? idEtablissement : "Tous"));
            System.out.println("   Professionnel: " + (idProfessionnel != null ? idProfessionnel : "Tous"));
            System.out.println("   Spécialité: " + (specialite != null ? specialite : "Toutes"));

            StatistiquesDTO statistiques = statistiquesService.calculerStatistiques(
                annee, idEtablissement, idProfessionnel, specialite
            );

            model.put("statistiques", statistiques);
            model.put("success", true);

            System.out.println("✅ Statistiques calculées avec succès");
            System.out.println("   Total patients: " + statistiques.getTotalPatients());
            System.out.println("   Total consultations: " + statistiques.getTotalConsultations());
            System.out.println("   Total vaccinations: " + statistiques.getTotalVaccinations());

            HttpHeaders headers = new HttpHeaders();
            headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);

            return ResponseEntity.ok().headers(headers).body(model);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors du calcul des statistiques: " + e.getMessage());
            e.printStackTrace();

            model.put("success", false);
            model.put("message", "Erreur lors du calcul des statistiques: " + e.getMessage());

            return ResponseEntity.status(500).body(model);
        }
    }

    /**
     * Récupérer la liste des années avec consultations
     * @return Liste des années
     */
    @RequestMapping(value = "/annees", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getAnnees() {
        HashMap<String, Object> model = new HashMap<>();
        List<Integer> listeAnnees = this.statistiquesService.getAnneesAvecConsultations();
        model.put("listeAnnees", listeAnnees);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);
    }

    /**
     * Récupérer la liste des spécialités
     * @return Liste des spécialités
     */
    @RequestMapping(value = "/specialites", method = RequestMethod.GET)
    public ResponseEntity<HashMap<String, Object>> getSpecialites() {
        HashMap<String, Object> model = new HashMap<>();
        List<ParamListDTO> listeSpecialites = this.statistiquesService.getSpecialites();
        model.put("listeSpecialites", listeSpecialites);
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constants.PROPRIETE_HEADERS_RESPONDED, Constants.PROPRIETE_HEADERS_TIMEZONESCONTROLLER);
        return ResponseEntity.accepted().headers(headers).body(model);
    }
}

