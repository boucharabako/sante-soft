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
@Table(name = "consultation", schema = "sante")
public class Consultation extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "id_patient")
    private String idPatient;
    @Column(name = "type_consultation")
    private String typeConsultation;
    @Column(name = "id_professionnel_sante")
    private String idProfessionnelSante;
    @Column(name = "motif")
    private String motif;
    @Column(name = "diagnostic")
    private String diagnostic;
    @Column(name = "traitement")
    private String traitement;
     @Column(name = "date_consultation")
    private Instant dateConsultation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getTraitement() {
        return traitement;
    }

    public void setTraitement(String traitement) {
        this.traitement = traitement;
    }

    public Instant getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(Instant dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public String getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(String idPatient) {
        this.idPatient = idPatient;
    }

    public String getIdProfessionnelSante() {
        return idProfessionnelSante;
    }

    public void setIdProfessionnelSante(String idProfessionnelSante) {
        this.idProfessionnelSante = idProfessionnelSante;
    }

    public String getTypeConsultation() {
        return typeConsultation;
    }

    public void setTypeConsultation(String typeConsultation) {
        this.typeConsultation = typeConsultation;
    }

    @Override
    public String toString() {
        return "Consultation{" + "id=" + id + ", idPatient=" + idPatient + ", typeConsultation=" + typeConsultation + ", idProfessionnelSante=" + idProfessionnelSante + ", motif=" + motif + ", diagnostic=" + diagnostic + ", traitement=" + traitement + ", dateConsultation=" + dateConsultation + '}';
    }


  
}
