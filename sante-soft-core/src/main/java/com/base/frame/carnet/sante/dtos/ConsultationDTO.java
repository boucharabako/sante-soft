package com.base.frame.carnet.sante.dtos;

import java.time.Instant;
import java.util.List;

/**
 * DTO pour Consultation
 * @author Bouchara
 */
public class ConsultationDTO {
    
    private String id;
    private String idPatient;
    private String categorieConsultation;
    private String typeConsultation;
    private String idProfessionnelSante;
    private String motif;
    private String diagnostic;
    private String traitement;
    private String dateConsultation;

    // Libellés pour l'affichage
    private String categorieConsultationLibelle;
    private String typeConsultationLibelle;
    private String professionnelSanteNom;
    private String patientNom;

    // Listes associées
    private List<PrescriptionDTO> prescriptions;
    private List<ExamenDTO> examens;
    private List<ObservationDTO> observations;

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

    public String getCategorieConsultation() {
        return categorieConsultation;
    }

    public void setCategorieConsultation(String categorieConsultation) {
        this.categorieConsultation = categorieConsultation;
    }

    public String getTypeConsultation() {
        return typeConsultation;
    }

    public void setTypeConsultation(String typeConsultation) {
        this.typeConsultation = typeConsultation;
    }

    public String getIdProfessionnelSante() {
        return idProfessionnelSante;
    }

    public void setIdProfessionnelSante(String idProfessionnelSante) {
        this.idProfessionnelSante = idProfessionnelSante;
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

    public String getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(String dateConsultation) {
        this.dateConsultation = dateConsultation;
    }


    public List<PrescriptionDTO> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<PrescriptionDTO> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public List<ExamenDTO> getExamens() {
        return examens;
    }

    public void setExamens(List<ExamenDTO> examens) {
        this.examens = examens;
    }

    public List<ObservationDTO> getObservations() {
        return observations;
    }

    public void setObservations(List<ObservationDTO> observations) {
        this.observations = observations;
    }

    public String getCategorieConsultationLibelle() {
        return categorieConsultationLibelle;
    }

    public void setCategorieConsultationLibelle(String categorieConsultationLibelle) {
        this.categorieConsultationLibelle = categorieConsultationLibelle;
    }

    public String getTypeConsultationLibelle() {
        return typeConsultationLibelle;
    }

    public void setTypeConsultationLibelle(String typeConsultationLibelle) {
        this.typeConsultationLibelle = typeConsultationLibelle;
    }

    public String getProfessionnelSanteNom() {
        return professionnelSanteNom;
    }

    public void setProfessionnelSanteNom(String professionnelSanteNom) {
        this.professionnelSanteNom = professionnelSanteNom;
    }

    public String getPatientNom() {
        return patientNom;
    }

    public void setPatientNom(String patientNom) {
        this.patientNom = patientNom;
    }

    @Override
    public String toString() {
        return "ConsultationDTO{" + "id=" + id + ", idPatient=" + idPatient + ", categorieConsultation=" + categorieConsultation + ", typeConsultation=" + typeConsultation + ", idProfessionnelSante=" + idProfessionnelSante + ", motif=" + motif + ", diagnostic=" + diagnostic + ", traitement=" + traitement + ", dateConsultation=" + dateConsultation + ", categorieConsultationLibelle=" + categorieConsultationLibelle + ", typeConsultationLibelle=" + typeConsultationLibelle + ", professionnelSanteNom=" + professionnelSanteNom + ", patientNom=" + patientNom + ", prescriptions=" + prescriptions + ", examens=" + examens + ", observations=" + observations + '}';
    }
    
    
}

