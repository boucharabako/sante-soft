package com.base.frame.carnet.sante.dtos;

import java.time.Instant;

/**
 * DTO pour HistoriqueAntecedent
 * @author Bouchara
 */
public class HistoriqueAntecedentDTO {
    
    private String id;
    private String idAntecedent;
    private String idProfessionnel;
    private String professionnelNom;
    private String professionnelPrenom;
    private String professionnelUsername;
    private String professionnelEmail;
    private String professionnelTelephone;
    private String professionnelSpecialite;
    private String professionnelEtablissement;
    private String action;
    private Instant dateAction;

    // Informations de l'antécédent (pour l'affichage)
    private String antecedentLibelle;
    private String typeAntecedentLibelle;
    private String categorieAntecedentLibelle;
    private String description;

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAntecedent() {
        return idAntecedent;
    }

    public void setIdAntecedent(String idAntecedent) {
        this.idAntecedent = idAntecedent;
    }

    public String getIdProfessionnel() {
        return idProfessionnel;
    }

    public void setIdProfessionnel(String idProfessionnel) {
        this.idProfessionnel = idProfessionnel;
    }

    public String getProfessionnelNom() {
        return professionnelNom;
    }

    public void setProfessionnelNom(String professionnelNom) {
        this.professionnelNom = professionnelNom;
    }

    public String getProfessionnelPrenom() {
        return professionnelPrenom;
    }

    public void setProfessionnelPrenom(String professionnelPrenom) {
        this.professionnelPrenom = professionnelPrenom;
    }

    public String getProfessionnelUsername() {
        return professionnelUsername;
    }

    public void setProfessionnelUsername(String professionnelUsername) {
        this.professionnelUsername = professionnelUsername;
    }

    public String getProfessionnelEmail() {
        return professionnelEmail;
    }

    public void setProfessionnelEmail(String professionnelEmail) {
        this.professionnelEmail = professionnelEmail;
    }

    public String getProfessionnelTelephone() {
        return professionnelTelephone;
    }

    public void setProfessionnelTelephone(String professionnelTelephone) {
        this.professionnelTelephone = professionnelTelephone;
    }

    public String getProfessionnelSpecialite() {
        return professionnelSpecialite;
    }

    public void setProfessionnelSpecialite(String professionnelSpecialite) {
        this.professionnelSpecialite = professionnelSpecialite;
    }

    public String getProfessionnelEtablissement() {
        return professionnelEtablissement;
    }

    public void setProfessionnelEtablissement(String professionnelEtablissement) {
        this.professionnelEtablissement = professionnelEtablissement;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Instant getDateAction() {
        return dateAction;
    }

    public void setDateAction(Instant dateAction) {
        this.dateAction = dateAction;
    }

    public String getAntecedentLibelle() {
        return antecedentLibelle;
    }

    public void setAntecedentLibelle(String antecedentLibelle) {
        this.antecedentLibelle = antecedentLibelle;
    }

    public String getTypeAntecedentLibelle() {
        return typeAntecedentLibelle;
    }

    public void setTypeAntecedentLibelle(String typeAntecedentLibelle) {
        this.typeAntecedentLibelle = typeAntecedentLibelle;
    }

    public String getCategorieAntecedentLibelle() {
        return categorieAntecedentLibelle;
    }

    public void setCategorieAntecedentLibelle(String categorieAntecedentLibelle) {
        this.categorieAntecedentLibelle = categorieAntecedentLibelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

