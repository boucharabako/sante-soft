/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.dtos;

import java.math.BigDecimal;

/**
 *
 * @author NANO TECH
 */
public class DetailCommandeDTO {
    
    private String id;
    private String idCommande;
    private BigDecimal prixUnitaire;
    private Integer quantiteCommande;
    private BigDecimal total;
    private String produit;
    private String libelleProduit;

    public DetailCommandeDTO(String id, String idCommande, BigDecimal prixUnitaire, Integer quantiteCommande, BigDecimal total, String produit) {
        this.id = id;
        this.idCommande = idCommande;
        this.prixUnitaire = prixUnitaire;
        this.quantiteCommande = quantiteCommande;
        this.total = total;
        this.produit = produit;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(String idCommande) {
        this.idCommande = idCommande;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Integer getQuantiteCommande() {
        return quantiteCommande;
    }

    public void setQuantiteCommande(Integer quantiteCommande) {
        this.quantiteCommande = quantiteCommande;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    
    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

    public String getLibelleProduit() {
        return libelleProduit;
    }

    public void setLibelleProduit(String libelleProduit) {
        this.libelleProduit = libelleProduit;
    }

    @Override
    public String toString() {
        return "CommandeDetailDTO{" + "id=" + id + ", idCommande=" + idCommande + ", prixUnitaire=" + prixUnitaire + ", quantiteCommande=" + quantiteCommande + ", total=" + total + ", produit=" + produit + ", libelleProduit=" + libelleProduit + '}';
    }
 
}
