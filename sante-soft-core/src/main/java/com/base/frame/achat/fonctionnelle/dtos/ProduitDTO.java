/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.dtos;

import java.math.BigDecimal;

/**
 *
 * @author Bouchara
 */
public class ProduitDTO {
    
    private String id;
    private String code;
    private String libelle;
    private String description;
    private BigDecimal prix;
    private Integer quantite;
    private String typeProduit;
    private String libelleTypeProduit;
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public String getTypeProduit() {
        return typeProduit;
    }

    public void setTypeProduit(String typeProduit) {
        this.typeProduit = typeProduit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLibelleTypeProduit() {
        return libelleTypeProduit;
    }

    public void setLibelleTypeProduit(String libelleTypeProduit) {
        this.libelleTypeProduit = libelleTypeProduit;
    }

    @Override
    public String toString() {
        return "ProduitDTO{" + "id=" + id + ", code=" + code + ", libelle=" + libelle + ", description=" + description + ", prix=" + prix + ", quantite=" + quantite + ", typeProduit=" + typeProduit + ", libelleTypeProduit=" + libelleTypeProduit + ", image=" + image + '}';
    }

    
}