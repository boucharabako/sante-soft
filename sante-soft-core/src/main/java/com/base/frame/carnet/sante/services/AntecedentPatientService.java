package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.daos.AntecedentPatientDAO;
import com.base.frame.carnet.sante.dtos.AntecedentPatientDTO;
import com.base.frame.carnet.sante.entities.Antecedant;
import com.base.frame.carnet.sante.entities.AntecedentPatient;
import com.base.frame.carnet.sante.entities.CategorieAntecedent;
import com.base.frame.carnet.sante.entities.TypeAntecedant;
import com.base.frame.carnet.sante.iservices.IAntecedentPatientService;
import com.base.frame.carnet.sante.iservices.IHistoriqueAntecedentService;
import com.base.frame.carnet.sante.repositories.AntecedantRepository;
import com.base.frame.carnet.sante.repositories.AntecedentPatientRepository;
import com.base.frame.carnet.sante.repositories.CategorieAntecedentRepository;
import com.base.frame.carnet.sante.repositories.ParamListDTORepository;
import com.base.frame.carnet.sante.repositories.TypeAntecedentRepository;
import com.base.frame.socle.core.entity.ParamList;
import com.base.frame.socle.core.service.CodificationService;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour AntecedentPatient
 * @author Bouchara
 */
@Service
@Transactional
public class AntecedentPatientService implements IAntecedentPatientService {

    @Autowired
    private AntecedentPatientRepository antecedentPatientRepository;
    
    
    @Autowired
    private CategorieAntecedentRepository categorieAntecedentRepository;

    @Autowired
    private AntecedentPatientDAO antecedentPatientDAO;

    @Autowired
    private CodificationService codificationService;

    @Autowired
    private ParamListDTORepository paramListDTORepository;

    @Autowired
    private IHistoriqueAntecedentService historiqueAntecedentService;
    
    @Autowired
    private TypeAntecedentRepository typeAntecedentRepository;
    
    @Autowired
    private AntecedantRepository antecedentRepository;


    @Override
    public AntecedentPatientDTO saveAntecedentPatient(AntecedentPatientDTO dto, String currentUserId, String currentUsername) {
        // Validation
        this.controleValidationObjetAntecedentPatient(dto);

        AntecedentPatient entity;
        boolean isNew = false;
        String action = "";

        if (dto.getId() != null && !dto.getId().isEmpty() && this.antecedentPatientRepository.existsById(dto.getId())) {
            // Modification
            entity = this.antecedentPatientRepository.findById(dto.getId()).get();
            action = "MODIFICATION";
            System.out.println("======================== Modification de l'antecedent ID: " + dto.getId());
        } else {
            // Création
            entity = new AntecedentPatient();
            isNew = true;
            action = "AJOUT";
            System.out.println("======================== Création d'un nouvel antecedent");
        }

        // Mapper DTO vers Entity
        entity = this.mapDTOIntoEntity(dto, entity);

        // Définir createdDate pour les nouveaux antécédents
        if (isNew) {
            entity.setCreatedDate(Instant.now());
            System.out.println("======================== createdDate défini: " + entity.getCreatedDate());
        }

        // Sauvegarder
        AntecedentPatient saved = this.antecedentPatientRepository.save(entity);

        System.out.println("======================== Antecedent sauvegarde avec succes: " + saved.getId());

        // Enregistrer l'action dans l'historique
        this.historiqueAntecedentService.enregistrerAction(saved.getId(), currentUserId, action);

        // Retourner le DTO
        return this.mapEntityIntoDTO(saved);
    }

    @Override
    public AntecedentPatientDTO getAntecedentPatient(String id) {
        AntecedentPatientDTO dto = new AntecedentPatientDTO();
        if (this.antecedentPatientRepository.existsById(id)) {
            dto = this.mapEntityIntoDTO(this.antecedentPatientRepository.findById(id).get());
        }
        return dto;
    }

    @Override
    public Optional<AntecedentPatient> findAntecedentPatientById(String id) {
        return this.antecedentPatientRepository.findById(id);
    }

    @Override
    public Page<AntecedentPatientDTO> findBySpecTerm(String idPatient, String categorieAntecedent,
            String typeAntecedent, String motCle, Pageable pageRequest) {
        Page<AntecedentPatient> listResult;

        listResult = this.antecedentPatientDAO.findPageAntecedentPatient(idPatient, categorieAntecedent,
                typeAntecedent, motCle, pageRequest);

        return this.mapEntityPageIntoDTOPage(pageRequest, listResult);
    }

    @Override
    public List<AntecedentPatientDTO> findAntecedentsByPatient(String idPatient) {
        // Utiliser la méthode qui filtre les antécédents supprimés
        List<AntecedentPatient> entities = this.antecedentPatientRepository.findActiveByIdPatient(idPatient);
        List<AntecedentPatientDTO> dtos = new ArrayList<>();

        for (AntecedentPatient entity : entities) {
            dtos.add(this.mapEntityIntoDTO(entity));
        }

        return dtos;
    }

    @Override
    public List<AntecedentPatientDTO> getAllAntecedentsByPatient(String idPatient, String mc) {
        // Utiliser le DAO pour récupérer la liste triée par date DESC
        List<AntecedentPatient> entities = this.antecedentPatientDAO.findListeAntecedentPatient(idPatient);
        List<AntecedentPatientDTO> dtos = new ArrayList<>();

        for (AntecedentPatient entity : entities) {
            dtos.add(this.mapEntityIntoDTO(entity));
        }

        System.out.println("======================== getAllAntecedentsByPatient pour patient " + idPatient + " : " + dtos.size() + " antécédents");

        return dtos;
    }

    @Override
    public void deleteAntecedentPatient(String id, String currentUserId, String currentUsername) {
        if (this.antecedentPatientRepository.existsById(id)) {
            System.out.println("======================== Suppression logique de l'antecedent ID: " + id);

            // Récupérer l'antécédent
            AntecedentPatient entity = this.antecedentPatientRepository.findById(id).get();

            // Marquer comme supprimé (suppression logique)
            entity.setDeleted(true);

            // Sauvegarder la modification
            this.antecedentPatientRepository.save(entity);

            // Enregistrer l'action dans l'historique APRÈS la suppression logique
            this.historiqueAntecedentService.enregistrerAction(id, currentUserId, "SUPPRESSION");

            System.out.println("======================== Antecedent marque comme supprime avec succes");
        }
    }

    /**
     * Validation de l'objet AntecedentPatientDTO
     * @param dto 
     */
    private void controleValidationObjetAntecedentPatient(AntecedentPatientDTO dto) {
        if (dto.getIdPatient() == null || dto.getIdPatient().isEmpty()) {
            throw new IllegalArgumentException("L'ID du patient est obligatoire");
        }
        if (dto.getCategorieAntecedent() == null || dto.getCategorieAntecedent().isEmpty()) {
            throw new IllegalArgumentException("La catégorie d'antécédent est obligatoire");
        }
        if (dto.getTypeAntecedent() == null || dto.getTypeAntecedent().isEmpty()) {
            throw new IllegalArgumentException("Le type d'antécédent est obligatoire");
        }
        if (dto.getAntecedent() == null || dto.getAntecedent().isEmpty()) {
            throw new IllegalArgumentException("L'antécédent est obligatoire");
        }
        if (dto.getDescription() == null || dto.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("La description est obligatoire");
        }
        if (dto.getDateDebut() == null) {
            throw new IllegalArgumentException("La date de début est obligatoire");
        }
    }

    /**
     * Mapper Entity vers DTO
     * @param entity
     * @return
     */
    private AntecedentPatientDTO mapEntityIntoDTO(AntecedentPatient entity) {
        AntecedentPatientDTO dto = new AntecedentPatientDTO();

        dto.setId(entity.getId());
        dto.setIdPatient(entity.getIdPatient());
        dto.setCategorieAntecedent(entity.getCategorieAntecedent());
        //dto.setCategorieAntecedentLibelle(this.categorieAntecedentRepository.findById(entity.getCategorieAntecedent()).get().getLibelle());
        dto.setTypeAntecedent(entity.getTypeAntecedent());
        dto.setAntecedent(entity.getAntecedent());
        dto.setDescription(entity.getDescription());
        dto.setDateDebut(entity.getDateDebut());
        dto.setDateFin(entity.getDateFin());
        dto.setStatut(entity.getStatut());
        dto.setTraitementSuivi(entity.getTraitementSuivi());
        dto.setDeleted(entity.getDeleted());

        // Récupérer les libellés depuis ParamList
        if (entity.getCategorieAntecedent() != null) {
//            Optional<ParamList> categorie = paramListDTORepository.findById(entity.getCategorieAntecedent());
//            if (categorie.isPresent()) {
//                dto.setCategorieAntecedentLibelle(categorie.get().getLibelle());
//            }
            Optional<CategorieAntecedent> categorie = categorieAntecedentRepository.findById(entity.getCategorieAntecedent());
            if (categorie.isPresent()) {
                dto.setCategorieAntecedentLibelle(categorie.get().getLibelle());
                dto.setCategorieAntecedentCode(categorie.get().getCode());
            }
        }

        if (entity.getTypeAntecedent() != null) {
//            Optional<ParamList> type = paramListDTORepository.findById(entity.getTypeAntecedent());
//            if (type.isPresent()) {
//                dto.setTypeAntecedentLibelle(type.get().getLibelle());
//            }
            Optional<TypeAntecedant> typeAntecedent = typeAntecedentRepository.findById(entity.getCategorieAntecedent());
            if (typeAntecedent.isPresent()) {
                dto.setTypeAntecedentLibelle(typeAntecedent.get().getLibelle());
            }
        }

        if (entity.getAntecedent() != null) {
//            Optional<ParamList> antecedent = paramListDTORepository.findById(entity.getAntecedent());
//            if (antecedent.isPresent()) {
//                dto.setAntecedentLibelle(antecedent.get().getLibelle());
//            }
            Optional<Antecedant> antecedent = antecedentRepository.findById(entity.getAntecedent());
            if (antecedent.isPresent()) {
                dto.setAntecedentLibelle(antecedent.get().getLibelle());
            }
        }

        return dto;
    }

    /**
     * Mapper DTO vers Entity
     * @param dto
     * @param entity
     * @return
     */
    private AntecedentPatient mapDTOIntoEntity(AntecedentPatientDTO dto, AntecedentPatient entity) {
        entity.setIdPatient(dto.getIdPatient());
        entity.setCategorieAntecedent(dto.getCategorieAntecedent());
        entity.setTypeAntecedent(dto.getTypeAntecedent());
        entity.setAntecedent(dto.getAntecedent());
        entity.setDescription(dto.getDescription());
        entity.setDateDebut(dto.getDateDebut());
        entity.setDateFin(dto.getDateFin());
        entity.setStatut(dto.getStatut());
        entity.setTraitementSuivi(dto.getTraitementSuivi());

        // Ne pas mapper le champ deleted depuis le DTO (géré uniquement par la méthode de suppression)
        // entity.setDeleted(dto.getDeleted());

        return entity;
    }

    /**
     * Mapper Page Entity vers Page DTO
     * @param pageRequest
     * @param source
     * @return
     */
    private Page<AntecedentPatientDTO> mapEntityPageIntoDTOPage(Pageable pageRequest, Page<AntecedentPatient> source) {
        List<AntecedentPatientDTO> dtos = new ArrayList<>();

        for (AntecedentPatient entity : source.getContent()) {
            dtos.add(this.mapEntityIntoDTO(entity));
        }

        return new PageImpl<>(dtos, pageRequest, source.getTotalElements());
    }
}

