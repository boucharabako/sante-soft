/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.dtos;

import java.io.Serializable;

/**
 *
 * @author Bouchara
 */
public class EtablissementDTO implements Serializable {

    private String id;
    private String codeEtablissement;
    private String libelleEtablissement;
    private String regionEtablissement;
    private String typeEtablissement;
    private String adresse;

    // Champs pour affichage
    private String typeEtablissementLibelle;
    private String regionLibelle;

    public EtablissementDTO() {
    }

    public EtablissementDTO(String id, String codeEtablissement, String libelleEtablissement, String regionEtablissement, String typeEtablissement, String adresse) {
        this.id = id;
        this.codeEtablissement = codeEtablissement;
        this.libelleEtablissement = libelleEtablissement;
        this.regionEtablissement = regionEtablissement;
        this.typeEtablissement = typeEtablissement;
        this.adresse = adresse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeEtablissement() {
        return codeEtablissement;
    }

    public void setCodeEtablissement(String codeEtablissement) {
        this.codeEtablissement = codeEtablissement;
    }

    public String getLibelleEtablissement() {
        return libelleEtablissement;
    }

    public void setLibelleEtablissement(String libelleEtablissement) {
        this.libelleEtablissement = libelleEtablissement;
    }

    public String getRegionEtablissement() {
        return regionEtablissement;
    }

    public void setRegionEtablissement(String regionEtablissement) {
        this.regionEtablissement = regionEtablissement;
    }

    public String getTypeEtablissement() {
        return typeEtablissement;
    }

    public void setTypeEtablissement(String typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTypeEtablissementLibelle() {
        return typeEtablissementLibelle;
    }

    public void setTypeEtablissementLibelle(String typeEtablissementLibelle) {
        this.typeEtablissementLibelle = typeEtablissementLibelle;
    }

    public String getRegionLibelle() {
        return regionLibelle;
    }

    public void setRegionLibelle(String regionLibelle) {
        this.regionLibelle = regionLibelle;
    }

    @Override
    public String toString() {
        return "EtablissementDTO{" + "id=" + id + ", codeEtablissement=" + codeEtablissement + ", libelleEtablissement=" + libelleEtablissement + ", regionEtablissement=" + regionEtablissement + ", typeEtablissement=" + typeEtablissement + ", adresse=" + adresse + '}';
    }
}

