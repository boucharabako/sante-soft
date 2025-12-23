package com.base.frame.carnet.sante.dtos;

/**
 * DTO pour Vaccination
 * @author Bouchara
 */
public class VaccinationDTO {
    
    private String id;
    private String idPatient;
    private String vaccin;
    private String dateVaccination;
    private String lot;
    private String prochainRappel;
    private String statut;
    private String lieuVaccination;
    private String observations;

    // Libellés pour l'affichage
    private String patientNom;

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

    public String getVaccin() {
        return vaccin;
    }

    public void setVaccin(String vaccin) {
        this.vaccin = vaccin;
    }

    public String getDateVaccination() {
        return dateVaccination;
    }

    public void setDateVaccination(String dateVaccination) {
        this.dateVaccination = dateVaccination;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public String getProchainRappel() {
        return prochainRappel;
    }

    public void setProchainRappel(String prochainRappel) {
        this.prochainRappel = prochainRappel;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getLieuVaccination() {
        return lieuVaccination;
    }

    public void setLieuVaccination(String lieuVaccination) {
        this.lieuVaccination = lieuVaccination;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }

    public String getPatientNom() {
        return patientNom;
    }

    public void setPatientNom(String patientNom) {
        this.patientNom = patientNom;
    }

    @Override
    public String toString() {
        return "VaccinationDTO{" + "id=" + id + ", idPatient=" + idPatient + ", vaccin=" + vaccin + ", dateVaccination=" + dateVaccination + ", lot=" + lot + ", prochainRappel=" + prochainRappel + ", statut=" + statut + ", lieuVaccination=" + lieuVaccination + ", observations=" + observations + '}';
    }
}

