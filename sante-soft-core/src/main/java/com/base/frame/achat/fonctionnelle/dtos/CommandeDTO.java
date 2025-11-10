/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.dtos;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NANO TECH
 */
public class CommandeDTO {

    private String id;
    private String numeroCommande;
    private String dateCommande;
    private BigDecimal prixTotalCommande;
    private String etatCommande;
    private String utilisateur;
    private String numeroTelClient;
    private String nomClient;
    private String prenomClient;
    private String emailClient;
    private List<DetailCommandeDTO> listDetailCommande = new ArrayList<>();
    private String typeFichier;
    private String fichier;

    public String getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(String typeFichier) {
        this.typeFichier = typeFichier;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }
    
    

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

    public String getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(String dateCommande) {
        this.dateCommande = dateCommande;
    }

    public BigDecimal getPrixTotalCommande() {
        return prixTotalCommande;
    }

    public void setPrixTotalCommande(BigDecimal prixTotalCommande) {
        this.prixTotalCommande = prixTotalCommande;
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

    public List<DetailCommandeDTO> getListDetailCommande() {
        return listDetailCommande;
    }

    public void setListDetailCommande(List<DetailCommandeDTO> listDetailCommande) {
        this.listDetailCommande = listDetailCommande;
    }

    @Override
    public String toString() {
        return "CommandeDTO{" + "id=" + id + ", numeroCommande=" + numeroCommande + ", dateCommande=" + dateCommande + ", prixTotalCommande=" + prixTotalCommande + ", etatCommande=" + etatCommande + ", utilisateur=" + utilisateur + ", numeroTelClient=" + numeroTelClient + ", nomClient=" + nomClient + ", prenomClient=" + prenomClient + ", emailClient=" + emailClient + ", listDetailCommande=" + listDetailCommande + '}';
    }

}
