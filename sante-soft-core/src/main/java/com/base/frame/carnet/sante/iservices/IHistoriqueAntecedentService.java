package com.base.frame.carnet.sante.iservices;

import com.base.frame.carnet.sante.dtos.HistoriqueAntecedentDTO;
import java.util.List;

/**
 * Interface du service pour HistoriqueAntecedent
 * @author Bouchara
 */
public interface IHistoriqueAntecedentService {
    
    /**
     * Enregistrer une action dans l'historique
     * @param idAntecedent ID de l'antécédent
     * @param idProfessionnel ID du professionnel (utilisateur connecté)
     * @param action Type d'action (AJOUT, MODIFICATION, SUPPRESSION)
     */
    void enregistrerAction(String idAntecedent, String idProfessionnel, String action);
    
    /**
     * Récupérer l'historique d'un antécédent
     * @param idAntecedent
     * @return
     */
    List<HistoriqueAntecedentDTO> getHistoriqueByAntecedent(String idAntecedent);
    
    /**
     * Récupérer l'historique de tous les antécédents d'un patient
     * @param idPatient
     * @return
     */
    List<HistoriqueAntecedentDTO> getHistoriqueByPatient(String idPatient);
}

