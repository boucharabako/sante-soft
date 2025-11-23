/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.entities;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author NANO TECH
 */
@Entity
@Table(name = "antecedant_medical", schema = "sante")
public class AntecedentMedical {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "id_patient")
    private String idPatient;
    @Column(name = "description")
    private String description;
    @Column(name = "date_debut")
    private Instant dateDebut;
     @Column(name = "date_Fin")
    private Instant dateFin;
    @Column(name = "traitement_suivi")
    private String traitementSuivi;
    @Column(name = "statut")
    private String statut;
    @Column(name = "type_antecedent")
    private String typeAntecedent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTraitementSuivi() {
        return traitementSuivi;
    }

    public void setTraitementSuivi(String traitementSuivi) {
        this.traitementSuivi = traitementSuivi;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getTypeAntecedent() {
        return typeAntecedent;
    }

    public void setTypeAntecedent(String typeAntecedent) {
        this.typeAntecedent = typeAntecedent;
    }

   
    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    @Override
    public String toString() {
        return "AntecedentMedical{" + "id=" + id + ", idPatient=" + idPatient + ", description=" + description + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + ", traitementSuivi=" + traitementSuivi + ", statut=" + statut + ", typeAntecedent=" + typeAntecedent + '}';
    }
     
}
