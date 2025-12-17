package com.base.frame.carnet.sante.dtos;

import java.time.Instant;

/**
 * DTO pour AntecedentPatient
 * @author Bouchara
 */
public class AntecedentPatientDTO {

    private String id;
    private String idPatient;
    private String categorieAntecedent;
    private String categorieAntecedentLibelle;
    private String typeAntecedent;
    private String typeAntecedentLibelle;
    private String antecedent;
    private String antecedentLibelle;
    private String description;
    private Instant dateDebut;
    private Instant dateFin;
    private String statut;
    private String traitementSuivi;
    private String actionEffectuee;

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public String getCategorieAntecedent() {
        return categorieAntecedent;
    }

    public void setCategorieAntecedent(String categorieAntecedent) {
        this.categorieAntecedent = categorieAntecedent;
    }

    public String getCategorieAntecedentLibelle() {
        return categorieAntecedentLibelle;
    }

    public String getActionEffectuee() {
        return actionEffectuee;
    }

    public void setActionEffectuee(String actionEffectuee) {
        this.actionEffectuee = actionEffectuee;
    }
    
    

    public void setCategorieAntecedentLibelle(String categorieAntecedentLibelle) {
        this.categorieAntecedentLibelle = categorieAntecedentLibelle;
    }

    public String getTypeAntecedent() {
        return typeAntecedent;
    }

    public void setTypeAntecedent(String typeAntecedent) {
        this.typeAntecedent = typeAntecedent;
    }

    public String getTypeAntecedentLibelle() {
        return typeAntecedentLibelle;
    }

    public void setTypeAntecedentLibelle(String typeAntecedentLibelle) {
        this.typeAntecedentLibelle = typeAntecedentLibelle;
    }

    public String getAntecedent() {
        return antecedent;
    }

    public void setAntecedent(String antecedent) {
        this.antecedent = antecedent;
    }

    public String getAntecedentLibelle() {
        return antecedentLibelle;
    }

    public void setAntecedentLibelle(String antecedentLibelle) {
        this.antecedentLibelle = antecedentLibelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTraitementSuivi() {
        return traitementSuivi;
    }

    public void setTraitementSuivi(String traitementSuivi) {
        this.traitementSuivi = traitementSuivi;
    }
}

