/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.dtos.CompositionMedicamentDTO;
import com.base.frame.carnet.sante.entities.CompositionMedicament;
import com.base.frame.carnet.sante.repositories.CompositionMedicamentRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Bouchara
 */
@Service
public class MedicamentService {
    
    @Autowired
    private CompositionMedicamentRepository compositionMedicamentRepository;
    
    @Autowired
    private RestTemplate restTemplate;
    
    private static final String FDA_API_URL = "https://api.fda.gov/drug/label.json?search=openfda.brand_name:";
    
    /**
     * Récupérer la composition d'un médicament (depuis le cache ou l'API FDA)
     * @param medicamentName
     * @return Liste des éléments de composition
     */
    public List<String> getComposition(String medicamentName) {
        if (medicamentName == null || medicamentName.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        String medicamentNormalized = medicamentName.trim().toLowerCase();
        
        // 1. Vérifier si la composition existe déjà en base de données
        Optional<CompositionMedicament> existingComposition = 
            compositionMedicamentRepository.findByMedicament(medicamentNormalized);
        
        if (existingComposition.isPresent()) {
            System.out.println(" Composition trouvée en cache pour: " + medicamentName);
            return parseComposition(existingComposition.get().getComposition());
        }
        
        // 2. Si pas en cache, appeler l'API FDA
        System.out.println(" Appel API FDA pour: " + medicamentName);
        List<String> composition = callFdaApiAndSave(medicamentNormalized);
        
        return composition;
    }
    
    /**
     * Appeler l'API FDA et sauvegarder la composition en base
     * @param medicamentName
     * @return Liste des éléments de composition
     */
    private List<String> callFdaApiAndSave(String medicamentName) {
        try {
            String url = FDA_API_URL + medicamentName;
            System.out.println(" URL FDA: " + url);
            
            CompositionMedicamentDTO response = restTemplate.getForObject(url, CompositionMedicamentDTO.class);
            
            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                CompositionMedicamentDTO.Result result = response.getResults().get(0);
                List<String> splElements = result.getSpl_product_data_elements();
                
                if (splElements != null && !splElements.isEmpty()) {
                    // Sauvegarder en base de données
                    String compositionString = String.join("||", splElements);
                    saveComposition(medicamentName, compositionString);
                    
                    System.out.println(" Composition récupérée et sauvegardée pour: " + medicamentName);
                    return splElements;
                }
            }
            
            System.out.println(" Aucune composition trouvée dans l'API FDA pour: " + medicamentName);
            return new ArrayList<>();
            
        } catch (Exception e) {
            System.err.println(" Erreur lors de l'appel API FDA pour " + medicamentName + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Sauvegarder la composition d'un médicament en base de données
     * @param medicamentName
     * @param composition
     */
    private void saveComposition(String medicamentName, String composition) {
        CompositionMedicament entity = new CompositionMedicament();
        entity.setMedicament(medicamentName);
        entity.setComposition(composition);
        compositionMedicamentRepository.save(entity);
    }
    
    /**
     * Parser la composition stockée en base (format: élément1||élément2||...)
     * @param compositionString
     * @return Liste des éléments
     */
    private List<String> parseComposition(String compositionString) {
        List<String> elements = new ArrayList<>();
        if (compositionString != null && !compositionString.isEmpty()) {
            String[] parts = compositionString.split("\\|\\|");
            for (String part : parts) {
                elements.add(part.trim());
            }
        }
        return elements;
    }
}

