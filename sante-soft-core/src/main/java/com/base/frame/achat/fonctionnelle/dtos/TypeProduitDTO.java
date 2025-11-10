/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.dtos;

/**
 *
 * @author NANO TECH
 */
public class TypeProduitDTO {
    
    private String id;
    private String codeTypeProduit;
    private String libelleTypeProduit;
    private String descriptionTypeProduit;

    public TypeProduitDTO(String id, String codeTypeProduit, String libelleTypeProduit) {
        this.id = id;
        this.codeTypeProduit = codeTypeProduit;
        this.libelleTypeProduit = libelleTypeProduit;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeTypeProduit() {
        return codeTypeProduit;
    }

    public void setCodeTypeProduit(String codeTypeProduit) {
        this.codeTypeProduit = codeTypeProduit;
    }

    public String getLibelleTypeProduit() {
        return libelleTypeProduit;
    }

    public void setLibelleTypeProduit(String libelleTypeProduit) {
        this.libelleTypeProduit = libelleTypeProduit;
    }

    public String getDescriptionTypeProduit() {
        return descriptionTypeProduit;
    }

    public void setDescriptionTypeProduit(String descriptionTypeProduit) {
        this.descriptionTypeProduit = descriptionTypeProduit;
    }

    @Override
    public String toString() {
        return "TypeProduitDTO{" + "id=" + id + ", codeTypeProduit=" + codeTypeProduit + ", libelleTypeProduit=" + libelleTypeProduit + ", descriptionTypeProduit=" + descriptionTypeProduit + '}';
    }
    
    
    
}
