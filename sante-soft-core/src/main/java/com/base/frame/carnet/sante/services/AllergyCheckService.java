/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.entities.AntecedentPatient;
import com.base.frame.carnet.sante.repositories.AntecedentPatientRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bouchara
 */
@Service
public class AllergyCheckService {
    
    @Autowired
    private AntecedentPatientRepository antecedentPatientRepository;
    
    @Autowired
    private MedicamentService medicamentService;
    
    /**
     * Vérifier les allergies d'un patient pour une liste de médicaments
     * @param idPatient
     * @param medicaments
     * @return Liste des messages d'alerte
     */
    public List<String> checkAllergies(String idPatient, List<String> medicaments) {
        List<String> alertes = new ArrayList<>();
        
        if (idPatient == null || medicaments == null || medicaments.isEmpty()) {
            return alertes;
        }
        
        // 1. Récupérer les allergies du patient
        List<AntecedentPatient> antecedents = antecedentPatientRepository.findActiveByIdPatient(idPatient);
        List<String> allergies = new ArrayList<>();
        
        for (AntecedentPatient antecedent : antecedents) {
            // Filtrer uniquement les allergies (vous pouvez ajuster le critère selon votre système)
            if (antecedent.getAntecedent() != null && !antecedent.getAntecedent().isEmpty()) {
                allergies.add(antecedent.getAntecedent().toLowerCase());
            }
            if (antecedent.getDescription() != null && !antecedent.getDescription().isEmpty()) {
                allergies.add(antecedent.getDescription().toLowerCase());
            }
        }
        
        System.out.println(" Allergies du patient: " + allergies);
        
        // 2. Pour chaque médicament, vérifier la composition
        for (String medicament : medicaments) {
            if (medicament == null || medicament.trim().isEmpty()) {
                continue;
            }
            
            List<String> composition = medicamentService.getComposition(medicament);
            
            // 3. Comparer la composition avec les allergies
            for (String element : composition) {
                String elementLower = element.toLowerCase();
                
                for (String allergie : allergies) {
                    // Vérification bidirectionnelle (substring matching)
                    if (elementLower.contains(allergie) || allergie.contains(elementLower)) {
                        String alerte = " ALLERGIE DÉTECTÉE: Le médicament \"" + medicament + 
                                      "\" contient \"" + element + "\" auquel le patient est allergique.";
                        alertes.add(alerte);
                        System.out.println(" " + alerte);
                        break; // Éviter les doublons pour le même médicament
                    }
                }
            }
        }
        
        return alertes;
    }
}

