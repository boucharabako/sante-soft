/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.base.frame.achat.fonctionnelle.entities;

import com.base.frame.socle.utils.audit.AbstractAuditingEntity;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "commande", schema = "achat")
public class Commande extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "numero_commande")
    private String numeroCommande;
    @Column(name = "date_commande")
    private Instant dateCommande;
    @Column(name = "prix_total_commande")
    private BigDecimal prixTotalCommande;
    @Column(name = "etat_commande")
    private String etatCommande;
    @Column(name = "id_utilisateur")
    private String utilisateur;
    @Column(name = "numero_tel_client")
    private String numeroTelClient;
    @Column(name = "nom_client")
    private String nomClient;
    @Column(name = "prenom_client")
    private String prenomClient;
    @Column(name = "email_client")
    private String emailClient;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public Instant getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Instant dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getEtatCommande() {
        return etatCommande;
    }

    public void setEtatCommande(String etatCommande) {
        this.etatCommande = etatCommande;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public BigDecimal getPrixTotalCommande() {
        return prixTotalCommande;
    }

    public void setPrixTotalCommande(BigDecimal prixTotalCommande) {
        this.prixTotalCommande = prixTotalCommande;
    }

    public String getNumeroTelClient() {
        return numeroTelClient;
    }

    public void setNumeroTelClient(String numeroTelClient) {
        this.numeroTelClient = numeroTelClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", numeroCommande=" + numeroCommande + ", dateCommande=" + dateCommande + ", prixTotalCommande=" + prixTotalCommande + ", etatCommande=" + etatCommande + ", utilisateur=" + utilisateur + ", numeroTelClient=" + numeroTelClient + ", nomClient=" + nomClient + ", prenomClient=" + prenomClient + ", emailClient=" + emailClient + '}';
    }

}
