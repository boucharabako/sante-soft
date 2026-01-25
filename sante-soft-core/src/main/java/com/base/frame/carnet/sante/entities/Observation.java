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
@Table(name = "observation", schema = "sante")
public class Observation extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "id_patient")
    private String idPatient;
    @Column(name = "type_observation")
    private String typeObservation;
    @Column(name = "valeur")
    private String valeur;
    @Column(name = "commentaire")
    private String commentaire;
    @Column(name = "date_observation")
    private Instant dateObservation;
    @Column(name = "id_consultation")
    private String consultation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeObservation() {
        return typeObservation;
    }

    public void setTypeObservation(String typeObservation) {
        this.typeObservation = typeObservation;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }


    public Instant getDateObservation() {
        return dateObservation;
    }

    public void setDateObservation(Instant dateObservation) {
        this.dateObservation = dateObservation;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public String getConsultation() {
        return consultation;
    }

    public void setConsultation(String consultation) {
        this.consultation = consultation;
    }
    

    @Override
    public String toString() {
        return "Observation{" + "id=" + id + ", typeObservation=" + typeObservation + ", valeur=" + valeur + ", commentaire=" + commentaire + ", dateObservation=" + dateObservation + '}';
    }

     
}
