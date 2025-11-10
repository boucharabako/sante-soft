/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.entities;

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
@Table(name = "etablissement", schema = "sante")
public class Etablissement extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "code_etablissement")
    private String codeEtablissement;
    @Column(name = "libelle_etablissement")
    private String libelleEtablissement;
    @Column(name = "region_etablissement")
    private String regionEtablissement;
    @Column(name = "type_etablissement")
    private String typeEtablissement;
    @Column(name = "adresse_etablissement")
    private String adresse;

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

    @Override
    public String toString() {
        return "Etablissement{" + "id=" + id + ", codeEtablissement=" + codeEtablissement + ", libelleEtablissement=" + libelleEtablissement + ", regionEtablissement=" + regionEtablissement + ", typeEtablissement=" + typeEtablissement + ", adresse=" + adresse + '}';
    }
    
    
}
