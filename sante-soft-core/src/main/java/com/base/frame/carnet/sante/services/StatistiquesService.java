package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.dtos.ParamListDTO;
import com.base.frame.carnet.sante.dtos.StatistiquesDTO;
import com.base.frame.carnet.sante.entities.AntecedentPatient;
import com.base.frame.carnet.sante.entities.Consultation;
import com.base.frame.carnet.sante.entities.Patient;
import com.base.frame.carnet.sante.entities.ProfessionelSante;
import com.base.frame.carnet.sante.entities.Vaccination;
import com.base.frame.carnet.sante.repositories.AntecedentPatientRepository;
import com.base.frame.carnet.sante.repositories.CategorieAntecedentRepository;
import com.base.frame.carnet.sante.repositories.ConsultationRepository;
import com.base.frame.carnet.sante.repositories.ParamListDTORepository;
import com.base.frame.carnet.sante.repositories.PatientRepository;
import com.base.frame.carnet.sante.repositories.ProfessionnelSanteRepository;
import com.base.frame.carnet.sante.repositories.TypeConsultationRepository;
import com.base.frame.carnet.sante.repositories.VaccinationRepository;
import com.base.frame.socle.core.repository.ParamListRepository;
import com.base.frame.socle.core.utils.SocleConstant;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour calculer les statistiques du tableau de bord
 * @author Bouchara
 */
@Service
@Transactional(readOnly = true)
public class StatistiquesService {
    
    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private ConsultationRepository consultationRepository;
    
    @Autowired
    private VaccinationRepository vaccinationRepository;
    
    @Autowired
    private ProfessionnelSanteRepository professionnelSanteRepository;
    
    @Autowired
    private AntecedentPatientRepository antecedentPatientRepository;
    
    @Autowired
    private TypeConsultationRepository typeConsultationRepository;
    
    @Autowired
    private CategorieAntecedentRepository categorieAntecedentRepository;

    @Autowired
    private ParamListDTORepository paramListDTORepository;

    @Autowired
    private ParamListRepository paramListRepository;

    /**
     * Récupérer la liste des années où des consultations ont été effectuées
     * @return Liste des années avec consultations
     */
    public List<Integer> getAnneesAvecConsultations() {
        return consultationRepository.findDistinctYears();
    }

    /**
     * Récupérer la liste des spécialités depuis ParamList
     * @return Liste des spécialités
     */
    public List<ParamListDTO> getSpecialites() {
        return paramListDTORepository.getParamListDTO(SocleConstant.CODIFICATION_SPECIALITE);
    }

    /**
     * Calculer toutes les statistiques pour le tableau de bord
     * @param annee Année de filtrage (null = toutes)
     * @param idEtablissement ID de l'établissement (null = tous)
     * @param idProfessionnel ID du professionnel (null = tous)
     * @param specialite Spécialité du professionnel (null = toutes)
     * @return DTO contenant toutes les statistiques
     */
    public StatistiquesDTO calculerStatistiques(Integer annee, String idEtablissement, 
                                                 String idProfessionnel, String specialite) {
        
        StatistiquesDTO stats = new StatistiquesDTO();
        
        // Récupérer toutes les données
        List<Patient> patients = patientRepository.findAll();
        List<Consultation> consultations = consultationRepository.findAll();
        List<Vaccination> vaccinations = vaccinationRepository.findAll();
        List<ProfessionelSante> professionnels = professionnelSanteRepository.findAll();
        List<AntecedentPatient> antecedents = antecedentPatientRepository.findAll();
        
        // Filtrer par année si spécifié
        if (annee != null) {
            consultations = filtrerParAnnee(consultations, annee);
            vaccinations = filtrerVaccinationsParAnnee(vaccinations, annee);
        }
        
        // Filtrer par professionnel si spécifié
        if (idProfessionnel != null && !idProfessionnel.isEmpty()) {
            consultations = consultations.stream()
                .filter(c -> idProfessionnel.equals(c.getIdProfessionnelSante()))
                .collect(Collectors.toList());
        }
        
        // Filtrer par spécialité si spécifié
        if (specialite != null && !specialite.isEmpty()) {
            List<String> profIds = professionnels.stream()
                .filter(p -> specialite.equals(p.getSpecialite()))
                .map(ProfessionelSante::getId)
                .collect(Collectors.toList());
            
            consultations = consultations.stream()
                .filter(c -> profIds.contains(c.getIdProfessionnelSante()))
                .collect(Collectors.toList());
        }
        
        // Calculer les statistiques générales
        stats.setTotalPatients((long) patients.size());
        stats.setTotalConsultations((long) consultations.size());
        stats.setTotalVaccinations((long) vaccinations.size());
        stats.setTotalProfessionnels((long) professionnels.size());
        
        // Calculer les statistiques par période
        calculerStatistiquesPeriode(stats, consultations);
        
        // Calculer la répartition par sexe
        calculerRepartitionSexe(stats, patients);
        
        // Calculer la répartition par groupe sanguin
        calculerRepartitionGroupeSanguin(stats, patients);
        
        // Calculer la répartition par type de consultation
        calculerRepartitionTypeConsultation(stats, consultations);
        
        // Calculer la répartition par antécédent
        calculerRepartitionAntecedents(stats, antecedents);
        
        // Calculer l'évolution des consultations par mois
        calculerEvolutionConsultationsMois(stats, consultations, annee);
        
        // Calculer le top 5 des vaccins
        calculerTopVaccins(stats, vaccinations);
        
        // Calculer les consultations par professionnel
        calculerConsultationsParProfessionnel(stats, consultations, professionnels);
        
        // Calculer le taux de vaccination
        calculerTauxVaccination(stats, patients, vaccinations);
        
        // Calculer l'âge moyen des patients
        calculerAgeMoyenPatients(stats, patients);

        return stats;
    }

    private List<Consultation> filtrerParAnnee(List<Consultation> consultations, Integer annee) {
        return consultations.stream()
            .filter(c -> c.getDateConsultation() != null)
            .filter(c -> {
                LocalDate date = c.getDateConsultation().atZone(ZoneId.systemDefault()).toLocalDate();
                return date.getYear() == annee;
            })
            .collect(Collectors.toList());
    }

    private List<Vaccination> filtrerVaccinationsParAnnee(List<Vaccination> vaccinations, Integer annee) {
        return vaccinations.stream()
            .filter(v -> v.getDateVaccination() != null)
            .filter(v -> {
                LocalDate date = v.getDateVaccination().atZone(ZoneId.systemDefault()).toLocalDate();
                return date.getYear() == annee;
            })
            .collect(Collectors.toList());
    }

    private void calculerStatistiquesPeriode(StatistiquesDTO stats, List<Consultation> consultations) {
        Instant now = Instant.now();
        Instant debutJour = now.truncatedTo(ChronoUnit.DAYS);
        Instant debutSemaine = now.minus(7, ChronoUnit.DAYS);
        Instant debutMois = now.minus(30, ChronoUnit.DAYS);
        Instant debutAnnee = now.minus(365, ChronoUnit.DAYS);

        stats.setConsultationsAujourdhui(consultations.stream()
            .filter(c -> c.getDateConsultation() != null && c.getDateConsultation().isAfter(debutJour))
            .count());

        stats.setConsultationsSemaine(consultations.stream()
            .filter(c -> c.getDateConsultation() != null && c.getDateConsultation().isAfter(debutSemaine))
            .count());

        stats.setConsultationsMois(consultations.stream()
            .filter(c -> c.getDateConsultation() != null && c.getDateConsultation().isAfter(debutMois))
            .count());

        stats.setConsultationsAnnee(consultations.stream()
            .filter(c -> c.getDateConsultation() != null && c.getDateConsultation().isAfter(debutAnnee))
            .count());
    }

    private void calculerRepartitionSexe(StatistiquesDTO stats, List<Patient> patients) {
        stats.setPatientsHommes(patients.stream()
            .filter(p -> "M".equalsIgnoreCase(p.getSexe()))
            .count());

        stats.setPatientsFemmes(patients.stream()
            .filter(p -> "F".equalsIgnoreCase(p.getSexe()))
            .count());
    }

    private void calculerRepartitionGroupeSanguin(StatistiquesDTO stats, List<Patient> patients) {
        Map<String, Long> repartition = patients.stream()
            .filter(p -> p.getGroupeSanguin() != null && !p.getGroupeSanguin().isEmpty())
            .collect(Collectors.groupingBy(Patient::getGroupeSanguin, Collectors.counting()));

        // Enrichir avec les libellés
        Map<String, Long> repartitionAvecLibelles = new LinkedHashMap<>();
        repartition.forEach((groupeSanguinId, count) -> {
            paramListRepository.findById(groupeSanguinId).ifPresent(groupeSanguin -> {
                repartitionAvecLibelles.put(groupeSanguin.getLibelle(), count);
            });
        });

        stats.setRepartitionGroupeSanguin(repartitionAvecLibelles);
    }

    private void calculerRepartitionTypeConsultation(StatistiquesDTO stats, List<Consultation> consultations) {
        Map<String, Long> repartition = consultations.stream()
            .filter(c -> c.getTypeConsultation() != null && !c.getTypeConsultation().isEmpty())
            .collect(Collectors.groupingBy(Consultation::getTypeConsultation, Collectors.counting()));

        // Enrichir avec les libellés
        Map<String, Long> repartitionAvecLibelles = new LinkedHashMap<>();
        repartition.forEach((typeId, count) -> {
            typeConsultationRepository.findById(typeId).ifPresent(type -> {
                repartitionAvecLibelles.put(type.getLibelle(), count);
            });
        });

        stats.setRepartitionTypeConsultation(repartitionAvecLibelles);
    }

    private void calculerRepartitionAntecedents(StatistiquesDTO stats, List<AntecedentPatient> antecedents) {
        Map<String, Long> repartition = antecedents.stream()
            .filter(a -> a.getCategorieAntecedent() != null && !a.getCategorieAntecedent().isEmpty())
            .filter(a -> a.getDeleted() == null || !a.getDeleted())
            .collect(Collectors.groupingBy(AntecedentPatient::getCategorieAntecedent, Collectors.counting()));

        // Enrichir avec les libellés
        Map<String, Long> repartitionAvecLibelles = new LinkedHashMap<>();
        repartition.forEach((catId, count) -> {
            categorieAntecedentRepository.findById(catId).ifPresent(cat -> {
                repartitionAvecLibelles.put(cat.getLibelle(), count);
            });
        });

        stats.setRepartitionAntecedents(repartitionAvecLibelles);
    }

    private void calculerEvolutionConsultationsMois(StatistiquesDTO stats, List<Consultation> consultations, Integer annee) {
        Map<String, Long> evolution = new LinkedHashMap<>();

        int anneeRef = annee != null ? annee : LocalDate.now().getYear();

        for (int mois = 1; mois <= 12; mois++) {
            final int moisFinal = mois;
            long count = consultations.stream()
                .filter(c -> c.getDateConsultation() != null)
                .filter(c -> {
                    LocalDate date = c.getDateConsultation().atZone(ZoneId.systemDefault()).toLocalDate();
                    return date.getYear() == anneeRef && date.getMonthValue() == moisFinal;
                })
                .count();

            String[] moisNoms = {"Jan", "Fév", "Mar", "Avr", "Mai", "Juin",
                                 "Juil", "Août", "Sep", "Oct", "Nov", "Déc"};
            evolution.put(moisNoms[mois - 1], count);
        }

        stats.setEvolutionConsultationsMois(evolution);
    }

    private void calculerTopVaccins(StatistiquesDTO stats, List<Vaccination> vaccinations) {
        Map<String, Long> topVaccins = vaccinations.stream()
            .filter(v -> v.getVaccin() != null && !v.getVaccin().isEmpty())
            .collect(Collectors.groupingBy(Vaccination::getVaccin, Collectors.counting()))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));

        stats.setTopVaccins(topVaccins);
    }

    private void calculerConsultationsParProfessionnel(StatistiquesDTO stats,
                                                        List<Consultation> consultations,
                                                        List<ProfessionelSante> professionnels) {
        Map<String, Long> consultationsParProf = consultations.stream()
            .filter(c -> c.getIdProfessionnelSante() != null && !c.getIdProfessionnelSante().isEmpty())
            .collect(Collectors.groupingBy(Consultation::getIdProfessionnelSante, Collectors.counting()));

        // Enrichir avec les noms des professionnels
        Map<String, Long> consultationsAvecNoms = new LinkedHashMap<>();
        consultationsParProf.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(10)
            .forEach(entry -> {
                professionnels.stream()
                    .filter(p -> p.getId().equals(entry.getKey()))
                    .findFirst()
                    .ifPresent(prof -> {
                        String nom = "Dr. " + prof.getFirstName() + " " + prof.getLastName();
                        consultationsAvecNoms.put(nom, entry.getValue());
                    });
            });

        stats.setConsultationsParProfessionnel(consultationsAvecNoms);
    }

    private void calculerTauxVaccination(StatistiquesDTO stats, List<Patient> patients, List<Vaccination> vaccinations) {
        if (patients.isEmpty()) {
            stats.setTauxVaccination(0.0);
            return;
        }

        // Compter le nombre de patients ayant au moins une vaccination
        long patientsVaccines = vaccinations.stream()
            .map(Vaccination::getIdPatient)
            .distinct()
            .count();

        double taux = (patientsVaccines * 100.0) / patients.size();
        stats.setTauxVaccination(Math.round(taux * 100.0) / 100.0);
    }

    private void calculerAgeMoyenPatients(StatistiquesDTO stats, List<Patient> patients) {
        List<Patient> patientsAvecAge = patients.stream()
            .filter(p -> p.getDateNaissance() != null)
            .collect(Collectors.toList());

        if (patientsAvecAge.isEmpty()) {
            stats.setAgeMoyenPatients(0.0);
            return;
        }

        LocalDate now = LocalDate.now();
        double sommeAges = patientsAvecAge.stream()
            .mapToInt(p -> {
                LocalDate dateNaissance = p.getDateNaissance().atZone(ZoneId.systemDefault()).toLocalDate();
                return Period.between(dateNaissance, now).getYears();
            })
            .average()
            .orElse(0.0);

        stats.setAgeMoyenPatients(Math.round(sommeAges * 10.0) / 10.0);
    }
}

