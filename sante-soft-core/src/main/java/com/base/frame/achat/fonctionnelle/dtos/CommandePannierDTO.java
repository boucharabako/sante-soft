/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.dtos;

import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Bouchara
 */
public class CommandePannierDTO {

    private List<ProduitPannierDTO> produits;
    private String ordonnance;
    private String numeroTelephone;
    private String nomClient;
    private String prenomsClient;
    private String emailClient;
    private BigDecimal total;

    public CommandePannierDTO() {
    }

    public List<ProduitPannierDTO> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitPannierDTO> produits) {
        this.produits = produits;
    }

    public String getOrdonnance() {
        return ordonnance;
    }

    public void setOrdonnance(String ordonnance) {
        this.ordonnance = ordonnance;
    }

    public String getNumeroTelephone() {
        return numeroTelephone;
    }

    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomsClient() {
        return prenomsClient;
    }

    public void setPrenomsClient(String prenomsClient) {
        this.prenomsClient = prenomsClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
