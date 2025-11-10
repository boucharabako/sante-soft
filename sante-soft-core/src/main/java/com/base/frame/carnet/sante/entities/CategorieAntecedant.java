/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.entities;

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
@Table(name = "categorie_antecedent", schema = "sante")
public class CategorieAntecedant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "type_antecedent")
    private String typeAntecedent;
    @Column(name = "description")
    private String libelle;
    @Column(name = "libelle")
    private String lienFamilial;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeAntecedent() {
        return typeAntecedent;
    }

    public void setTypeAntecedent(String typeAntecedent) {
        this.typeAntecedent = typeAntecedent;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLienFamilial() {
        return lienFamilial;
    }

    public void setLienFamilial(String lienFamilial) {
        this.lienFamilial = lienFamilial;
    }

    public CategorieAntecedant() {
    }
    
    
    

}
