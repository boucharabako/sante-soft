/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.entities;

import com.base.frame.socle.utils.audit.AbstractAuditingEntity;
import java.io.Serializable;
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
@Table(name = "type_produit", schema = "achat")
public class TypeProduit extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "code_type_produit")
    private String code;
    @Column(name = "libelle_type_produit")
    private String libelle;
    @Column(name = "description_type_produit")
    private String description;
    @Column(name = "categorie_produit")
    private String idCategorieProduit;

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

    public String getIdCategorieProduit() {
        return idCategorieProduit;
    }

    public void setIdCategorieProduit(String idCategorieProduit) {
        this.idCategorieProduit = idCategorieProduit;
    }
    
    

    @Override
    public String toString() {
        return "TypeProduit{" + "id=" + id + ", code=" + code + ", libelle=" + libelle + ", description=" + description + ", idCategorieProduit=" + idCategorieProduit + '}';
    }
    
   

}
