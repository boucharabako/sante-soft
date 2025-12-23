/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.entities;

import com.base.frame.socle.utils.audit.AbstractAuditingEntity;
import java.io.Serializable;
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
 * @author Bouchara
 */
@Entity
@Table(name = "vaccination", schema = "sante")
public class Vaccination extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "vaccin")
    private String vaccin;
    @Column(name = "numero_lot")
    private String numeroLot;
    @Column(name = "id_patient")
    private String idPatient;
    @Column(name = "lieu_vaccination")
    private String lieuVaccination;
    @Column(name = "date_vaccination")
    private Instant dateVaccination;
    @Column(name = "date_rappel")
    private Instant dateRappel;
    @Column(name = "statut")
    private String statut;
    @Column(name = "observations")
    private String observations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVaccin() {
        return vaccin;
    }

    public void setVaccin(String vaccin) {
        this.vaccin = vaccin;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public String getLieuVaccination() {
        return lieuVaccination;
    }

    public void setLieuVaccination(String lieuVaccination) {
        this.lieuVaccination = lieuVaccination;
    }

    public Instant getDateVaccination() {
        return dateVaccination;
    }

    public void setDateVaccination(Instant dateVaccination) {
        this.dateVaccination = dateVaccination;
    }

    public Instant getDateRappel() {
        return dateRappel;
    }

    public void setDateRappel(Instant dateRappel) {
        this.dateRappel = dateRappel;
    }

    public String getNumeroLot() {
        return numeroLot;
    }

    public void setNumeroLot(String numeroLot) {
        this.numeroLot = numeroLot;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }


    @Override
    public String toString() {
        return "Vaccination{" + "id=" + id + ", vaccin=" + vaccin + ", idPatient=" + idPatient + ", lieuVaccination=" + lieuVaccination + ", dateVaccination=" + dateVaccination + ", dateRappel=" + dateRappel + ", statut=" + statut + ", observations=" + observations + '}';
    }

   

}
