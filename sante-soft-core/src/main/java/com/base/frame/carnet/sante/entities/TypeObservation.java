/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.entities;

import com.base.frame.socle.utils.audit.AbstractAuditingEntity;
import java.io.Serializable;
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
 * @author Bouchara
 */
@Entity
@Table(name = "type_observation", schema = "sante")
public class TypeObservation extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "unite")
    private String unite;
    @Column(name = "libelle")
    private String libelle;
    @Column(name = "description")
    private String description;
    @Column(name = "valeur_min")
    private String valeurMin;
    @Column(name = "valeur_max")
    private String valeurMax;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
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

    public String getValeurMin() {
        return valeurMin;
    }

    public void setValeurMin(String valeurMin) {
        this.valeurMin = valeurMin;
    }

    public String getValeurMax() {
        return valeurMax;
    }

    public void setValeurMax(String valeurMax) {
        this.valeurMax = valeurMax;
    }

    @Override
    public String toString() {
        return "TypeObservation{" + "id=" + id + ", unite=" + unite + ", libelle=" + libelle + ", description=" + description + ", valeurMin=" + valeurMin + ", valeurMax=" + valeurMax + '}';
    }


}
