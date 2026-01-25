package com.base.frame.carnet.sante.dtos;

import java.time.Instant;

/**
 * DTO pour Observation
 * @author Bouchara
 */
public class ObservationDTO {
    
    private String id;
    private String idPatient;
    private String typeObservation;
    private String typeObservationLibelle; // Libellé du type d'observation
    private String valeur;
    private String commentaire;
    private Instant dateObservation;

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

    public String getTypeObservation() {
        return typeObservation;
    }

    public void setTypeObservation(String typeObservation) {
        this.typeObservation = typeObservation;
    }

    public String getTypeObservationLibelle() {
        return typeObservationLibelle;
    }

    public void setTypeObservationLibelle(String typeObservationLibelle) {
        this.typeObservationLibelle = typeObservationLibelle;
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
}

