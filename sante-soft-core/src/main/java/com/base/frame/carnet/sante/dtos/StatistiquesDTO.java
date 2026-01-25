package com.base.frame.carnet.sante.dtos;

import java.util.Map;

/**
 * DTO pour les statistiques du tableau de bord
 * @author Bouchara
 */
public class StatistiquesDTO {
    
    // Statistiques générales
    private Long totalPatients;
    private Long totalConsultations;
    private Long totalVaccinations;
    private Long totalProfessionnels;
    
    // Statistiques par période
    private Long consultationsAujourdhui;
    private Long consultationsSemaine;
    private Long consultationsMois;
    private Long consultationsAnnee;
    
    // Répartition par sexe
    private Long patientsHommes;
    private Long patientsFemmes;
    
    // Répartition par groupe sanguin
    private Map<String, Long> repartitionGroupeSanguin;
    
    // Répartition par type de consultation
    private Map<String, Long> repartitionTypeConsultation;
    
    // Répartition par catégorie d'antécédent
    private Map<String, Long> repartitionAntecedents;
    
    // Évolution des consultations par mois
    private Map<String, Long> evolutionConsultationsMois;
    
    // Top 5 des vaccins les plus administrés
    private Map<String, Long> topVaccins;
    
    // Statistiques par professionnel
    private Map<String, Long> consultationsParProfessionnel;
    
    // Taux de vaccination
    private Double tauxVaccination;
    
    // Âge moyen des patients
    private Double ageMoyenPatients;

    // Getters et Setters
    public Long getTotalPatients() {
        return totalPatients;
    }

    public void setTotalPatients(Long totalPatients) {
        this.totalPatients = totalPatients;
    }

    public Long getTotalConsultations() {
        return totalConsultations;
    }

    public void setTotalConsultations(Long totalConsultations) {
        this.totalConsultations = totalConsultations;
    }

    public Long getTotalVaccinations() {
        return totalVaccinations;
    }

    public void setTotalVaccinations(Long totalVaccinations) {
        this.totalVaccinations = totalVaccinations;
    }

    public Long getTotalProfessionnels() {
        return totalProfessionnels;
    }

    public void setTotalProfessionnels(Long totalProfessionnels) {
        this.totalProfessionnels = totalProfessionnels;
    }

    public Long getConsultationsAujourdhui() {
        return consultationsAujourdhui;
    }

    public void setConsultationsAujourdhui(Long consultationsAujourdhui) {
        this.consultationsAujourdhui = consultationsAujourdhui;
    }

    public Long getConsultationsSemaine() {
        return consultationsSemaine;
    }

    public void setConsultationsSemaine(Long consultationsSemaine) {
        this.consultationsSemaine = consultationsSemaine;
    }

    public Long getConsultationsMois() {
        return consultationsMois;
    }

    public void setConsultationsMois(Long consultationsMois) {
        this.consultationsMois = consultationsMois;
    }

    public Long getConsultationsAnnee() {
        return consultationsAnnee;
    }

    public void setConsultationsAnnee(Long consultationsAnnee) {
        this.consultationsAnnee = consultationsAnnee;
    }

    public Long getPatientsHommes() {
        return patientsHommes;
    }

    public void setPatientsHommes(Long patientsHommes) {
        this.patientsHommes = patientsHommes;
    }

    public Long getPatientsFemmes() {
        return patientsFemmes;
    }

    public void setPatientsFemmes(Long patientsFemmes) {
        this.patientsFemmes = patientsFemmes;
    }

    public Map<String, Long> getRepartitionGroupeSanguin() {
        return repartitionGroupeSanguin;
    }

    public void setRepartitionGroupeSanguin(Map<String, Long> repartitionGroupeSanguin) {
        this.repartitionGroupeSanguin = repartitionGroupeSanguin;
    }

    public Map<String, Long> getRepartitionTypeConsultation() {
        return repartitionTypeConsultation;
    }

    public void setRepartitionTypeConsultation(Map<String, Long> repartitionTypeConsultation) {
        this.repartitionTypeConsultation = repartitionTypeConsultation;
    }

    public Map<String, Long> getRepartitionAntecedents() {
        return repartitionAntecedents;
    }

    public void setRepartitionAntecedents(Map<String, Long> repartitionAntecedents) {
        this.repartitionAntecedents = repartitionAntecedents;
    }

    public Map<String, Long> getEvolutionConsultationsMois() {
        return evolutionConsultationsMois;
    }

    public void setEvolutionConsultationsMois(Map<String, Long> evolutionConsultationsMois) {
        this.evolutionConsultationsMois = evolutionConsultationsMois;
    }

    public Map<String, Long> getTopVaccins() {
        return topVaccins;
    }

    public void setTopVaccins(Map<String, Long> topVaccins) {
        this.topVaccins = topVaccins;
    }

    public Map<String, Long> getConsultationsParProfessionnel() {
        return consultationsParProfessionnel;
    }

    public void setConsultationsParProfessionnel(Map<String, Long> consultationsParProfessionnel) {
        this.consultationsParProfessionnel = consultationsParProfessionnel;
    }

    public Double getTauxVaccination() {
        return tauxVaccination;
    }

    public void setTauxVaccination(Double tauxVaccination) {
        this.tauxVaccination = tauxVaccination;
    }

    public Double getAgeMoyenPatients() {
        return ageMoyenPatients;
    }

    public void setAgeMoyenPatients(Double ageMoyenPatients) {
        this.ageMoyenPatients = ageMoyenPatients;
    }
}

