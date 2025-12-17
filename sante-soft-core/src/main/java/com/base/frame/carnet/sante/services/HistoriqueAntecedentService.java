package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.dtos.HistoriqueAntecedentDTO;
import com.base.frame.carnet.sante.entities.Affectation;
import com.base.frame.carnet.sante.entities.AntecedentPatient;
import com.base.frame.carnet.sante.entities.Etablissement;
import com.base.frame.carnet.sante.entities.HistoriqueAntecedent;
import com.base.frame.carnet.sante.iservices.IHistoriqueAntecedentService;
import com.base.frame.carnet.sante.repositories.AffectationRepository;
import com.base.frame.carnet.sante.repositories.AntecedentPatientRepository;
import com.base.frame.carnet.sante.repositories.EtablissementRepository;
import com.base.frame.carnet.sante.repositories.HistoriqueAntecedentRepository;
import com.base.frame.carnet.sante.repositories.ParamListDTORepository;
import com.base.frame.account.core.repository.UtilisateurRepository;
import com.base.frame.account.entity.Utilisateur;
import com.base.frame.socle.core.entity.ParamList;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour HistoriqueAntecedent
 * @author Bouchara
 */
@Service
@Transactional
public class HistoriqueAntecedentService implements IHistoriqueAntecedentService {
    
    @Autowired
    private HistoriqueAntecedentRepository historiqueAntecedentRepository;

    @Autowired
    private AntecedentPatientRepository antecedentPatientRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ParamListDTORepository paramListDTORepository;

    @Autowired
    private AffectationRepository affectationRepository;

    @Autowired
    private EtablissementRepository etablissementRepository;
    
    @Override
    public void enregistrerAction(String idAntecedent, String idProfessionnel, String action) {
        HistoriqueAntecedent historique = new HistoriqueAntecedent();
        historique.setIdAntecedent(idAntecedent);
        historique.setIdProfessionnel(idProfessionnel);
        historique.setAction(action);
        historique.setDateAction(Instant.now());
        
        this.historiqueAntecedentRepository.save(historique);
        
        System.out.println("======================== Historique enregistré: " + action + " par " + idProfessionnel + " sur antécédent " + idAntecedent);
    }
    
    @Override
    public List<HistoriqueAntecedentDTO> getHistoriqueByAntecedent(String idAntecedent) {
        List<HistoriqueAntecedent> entities = this.historiqueAntecedentRepository.findByIdAntecedent(idAntecedent);
        List<HistoriqueAntecedentDTO> dtos = new ArrayList<>();
        
        for (HistoriqueAntecedent entity : entities) {
            dtos.add(this.mapEntityIntoDTO(entity));
        }
        
        return dtos;
    }
    
    @Override
    public List<HistoriqueAntecedentDTO> getHistoriqueByPatient(String idPatient) {
        List<HistoriqueAntecedent> entities = this.historiqueAntecedentRepository.findByIdPatient(idPatient);
        List<HistoriqueAntecedentDTO> dtos = new ArrayList<>();
        
        for (HistoriqueAntecedent entity : entities) {
            dtos.add(this.mapEntityIntoDTO(entity));
        }
        
        System.out.println("======================== Historique récupéré pour patient " + idPatient + " : " + dtos.size() + " entrées");
        
        return dtos;
    }
    
    /**
     * Mapper Entity vers DTO
     */
    private HistoriqueAntecedentDTO mapEntityIntoDTO(HistoriqueAntecedent entity) {
        HistoriqueAntecedentDTO dto = new HistoriqueAntecedentDTO();
        
        dto.setId(entity.getId());
        dto.setIdAntecedent(entity.getIdAntecedent());
        dto.setIdProfessionnel(entity.getIdProfessionnel());
        dto.setAction(entity.getAction());
        dto.setDateAction(entity.getDateAction());
        
        // Récupérer les informations du professionnel
        if (entity.getIdProfessionnel() != null && !entity.getIdProfessionnel().isEmpty()) {
            Optional<Utilisateur> utilisateur = this.utilisateurRepository.findById(entity.getIdProfessionnel());
            if (utilisateur.isPresent()) {
                Utilisateur user = utilisateur.get();
                dto.setProfessionnelNom(user.getLastName());
                dto.setProfessionnelPrenom(user.getFirstName());
                dto.setProfessionnelUsername(user.getUsername());
                dto.setProfessionnelEmail(user.getEmail());
                dto.setProfessionnelTelephone(user.getTel());

                // Récupérer l'affectation active du professionnel
                List<Affectation> affectations = this.affectationRepository.findByIdProfessionnel(entity.getIdProfessionnel());
                if (affectations != null && !affectations.isEmpty()) {
                    // Prendre la première affectation (la plus récente si triée par date)
                    Affectation affectation = affectations.get(0);

                    // Récupérer le libellé de la spécialité
                    if (affectation.getIdSpecialite() != null) {
                        Optional<ParamList> specialite = this.paramListDTORepository.findById(affectation.getIdSpecialite());
                        if (specialite.isPresent()) {
                            dto.setProfessionnelSpecialite(specialite.get().getLibelle());
                        }
                    }

                    // Récupérer le libellé de l'établissement
                    if (affectation.getIdEtablissement() != null) {
                        Optional<Etablissement> etablissement = this.etablissementRepository.findById(affectation.getIdEtablissement());
                        if (etablissement.isPresent()) {
                            dto.setProfessionnelEtablissement(etablissement.get().getLibelleEtablissement());
                        }
                    }
                }
            }
        }
        
        // Récupérer les informations de l'antécédent
        if (entity.getIdAntecedent() != null && !entity.getIdAntecedent().isEmpty()) {
            Optional<AntecedentPatient> antecedent = this.antecedentPatientRepository.findById(entity.getIdAntecedent());
            if (antecedent.isPresent()) {
                AntecedentPatient ap = antecedent.get();
                dto.setDescription(ap.getDescription());
                
                // Récupérer les libellés
                if (ap.getAntecedent() != null) {
                    Optional<ParamList> antecedentParam = paramListDTORepository.findById(ap.getAntecedent());
                    if (antecedentParam.isPresent()) {
                        dto.setAntecedentLibelle(antecedentParam.get().getLibelle());
                    }
                }
                
                if (ap.getTypeAntecedent() != null) {
                    Optional<ParamList> typeParam = paramListDTORepository.findById(ap.getTypeAntecedent());
                    if (typeParam.isPresent()) {
                        dto.setTypeAntecedentLibelle(typeParam.get().getLibelle());
                    }
                }
                
                if (ap.getCategorieAntecedent() != null) {
                    Optional<ParamList> categorieParam = paramListDTORepository.findById(ap.getCategorieAntecedent());
                    if (categorieParam.isPresent()) {
                        dto.setCategorieAntecedentLibelle(categorieParam.get().getLibelle());
                    }
                }
            }
        }
        
        return dto;
    }
}

