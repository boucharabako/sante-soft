package com.base.frame.carnet.sante.services;

import com.base.frame.carnet.sante.daos.AntecedentPatientDAO;
import com.base.frame.carnet.sante.dtos.AntecedentPatientDTO;
import com.base.frame.carnet.sante.entities.AntecedentPatient;
import com.base.frame.carnet.sante.iservices.IAntecedentPatientService;
import com.base.frame.carnet.sante.repositories.AntecedentPatientRepository;
import com.base.frame.carnet.sante.repositories.ParamListDTORepository;
import com.base.frame.socle.core.entity.ParamList;
import com.base.frame.socle.core.service.CodificationService;
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
    private AntecedentPatientDAO antecedentPatientDAO;

    @Autowired
    private CodificationService codificationService;

    @Autowired
    private ParamListDTORepository paramListDTORepository;

    @Override
    public AntecedentPatientDTO saveAntecedentPatient(AntecedentPatientDTO dto) {
        // Validation
        this.controleValidationObjetAntecedentPatient(dto);

        AntecedentPatient entity;

        if (dto.getId() != null && !dto.getId().isEmpty() && this.antecedentPatientRepository.existsById(dto.getId())) {
            // Modification
            entity = this.antecedentPatientRepository.findById(dto.getId()).get();
            System.out.println("======================== Modification de l'antecedent ID: " + dto.getId());
        } else {
            // Création
            entity = new AntecedentPatient();
            System.out.println("======================== Création d'un nouvel antecedent");
        }

        // Mapper DTO vers Entity
        entity = this.mapDTOIntoEntity(dto, entity);

        // Sauvegarder
        AntecedentPatient saved = this.antecedentPatientRepository.save(entity);

        System.out.println("======================== Antecedent sauvegarde avec succes: " + saved.getId());

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
        List<AntecedentPatient> entities = this.antecedentPatientRepository.findByIdPatient(idPatient);
        List<AntecedentPatientDTO> dtos = new ArrayList<>();

        for (AntecedentPatient entity : entities) {
            dtos.add(this.mapEntityIntoDTO(entity));
        }

        return dtos;
    }

    @Override
    public void deleteAntecedentPatient(String id) {
        if (this.antecedentPatientRepository.existsById(id)) {
            this.antecedentPatientRepository.deleteById(id);
            System.out.println("======================== Antecedent supprime: " + id);
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
        dto.setTypeAntecedent(entity.getTypeAntecedent());
        dto.setAntecedent(entity.getAntecedent());
        dto.setDescription(entity.getDescription());
        dto.setDateDebut(entity.getDateDebut());
        dto.setDateFin(entity.getDateFin());
        dto.setStatut(entity.getStatut());
        dto.setTraitementSuivi(entity.getTraitementSuivi());

        // Récupérer les libellés depuis ParamList
        if (entity.getCategorieAntecedent() != null) {
            Optional<ParamList> categorie = paramListDTORepository.findById(entity.getCategorieAntecedent());
            if (categorie.isPresent()) {
                dto.setCategorieAntecedentLibelle(categorie.get().getLibelle());
            }
        }

        if (entity.getTypeAntecedent() != null) {
            Optional<ParamList> type = paramListDTORepository.findById(entity.getTypeAntecedent());
            if (type.isPresent()) {
                dto.setTypeAntecedentLibelle(type.get().getLibelle());
            }
        }

        if (entity.getAntecedent() != null) {
            Optional<ParamList> antecedent = paramListDTORepository.findById(entity.getAntecedent());
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

