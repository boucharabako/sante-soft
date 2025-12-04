package com.base.frame.carnet.sante.dtos;

import java.time.Instant;

/**
 *
 * @author Bouchara
 */
public class ProfessionnelSanteDTO {

    private String id;
    private String numeroOrdre;
    private String specialite;
    private String specialiteLibelle;
    private String etablissement;
    private String etablissementLibelle;
    private Instant dateEnregistrement;

    // Champs hérités de Utilisateur
    private String username;
    private String firstName;
    private String lastName;
    private String sexe;
    private String sexeLibelle;
    private Instant dateNaissance;
    private String email;
    private String tel;
    private String titre;
    private String etat;
    private String libelleEtat;

    // Champs pour le mot de passe
    private String password;
    private String confirmPassword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public void setNumeroOrdre(String numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getSpecialiteLibelle() {
        return specialiteLibelle;
    }

    public void setSpecialiteLibelle(String specialiteLibelle) {
        this.specialiteLibelle = specialiteLibelle;
    }

    public String getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(String etablissement) {
        this.etablissement = etablissement;
    }

    public String getEtablissementLibelle() {
        return etablissementLibelle;
    }

    public void setEtablissementLibelle(String etablissementLibelle) {
        this.etablissementLibelle = etablissementLibelle;
    }

    public Instant getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Instant dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getSexeLibelle() {
        return sexeLibelle;
    }

    public void setSexeLibelle(String sexeLibelle) {
        this.sexeLibelle = sexeLibelle;
    }

    public Instant getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Instant dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getLibelleEtat() {
        return libelleEtat;
    }

    public void setLibelleEtat(String libelleEtat) {
        this.libelleEtat = libelleEtat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "ProfessionnelSanteDTO{" + "id=" + id + ", numeroOrdre=" + numeroOrdre 
                + ", specialite=" + specialite + ", etablissement=" + etablissement
                + ", dateEnregistrement=" + dateEnregistrement + ", username=" + username 
                + ", firstName=" + firstName + ", lastName=" + lastName + ", sexe=" + sexe 
                + ", dateNaissance=" + dateNaissance + ", email=" + email + ", tel=" + tel + '}';
    }
}

