/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.dtos;

import java.time.Instant;

/**
 *
 * @author Bouchara
 */
public class AffectationDTO {
    
    private String id;
    private String idProfessionnel;
    private String idSpecialite;
    private String specialiteLibelle;
    private String idEtablissement;
    private String etablissementLibelle;
    private Instant dateDebut;
    private Instant dateFin;

    public AffectationDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProfessionnel() {
        return idProfessionnel;
    }

    public void setIdProfessionnel(String idProfessionnel) {
        this.idProfessionnel = idProfessionnel;
    }

    public String getIdSpecialite() {
        return idSpecialite;
    }

    public void setIdSpecialite(String idSpecialite) {
        this.idSpecialite = idSpecialite;
    }

    public String getSpecialiteLibelle() {
        return specialiteLibelle;
    }

    public void setSpecialiteLibelle(String specialiteLibelle) {
        this.specialiteLibelle = specialiteLibelle;
    }

    public String getIdEtablissement() {
        return idEtablissement;
    }

    public void setIdEtablissement(String idEtablissement) {
        this.idEtablissement = idEtablissement;
    }

    public String getEtablissementLibelle() {
        return etablissementLibelle;
    }

    public void setEtablissementLibelle(String etablissementLibelle) {
        this.etablissementLibelle = etablissementLibelle;
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

    @Override
    public String toString() {
        return "AffectationDTO{" + "id=" + id + ", idProfessionnel=" + idProfessionnel + ", idSpecialite=" + idSpecialite + ", specialiteLibelle=" + specialiteLibelle + ", idEtablissement=" + idEtablissement + ", etablissementLibelle=" + etablissementLibelle + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + '}';
    }
}

