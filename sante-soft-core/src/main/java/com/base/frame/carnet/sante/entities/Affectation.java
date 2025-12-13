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
 * @author NANO TECH
 */
@Entity
@Table(name = "affectation", schema = "sante")
public class Affectation extends AbstractAuditingEntity implements Serializable{
        @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "id_professionnel")
    private String idProfessionnel;
    @Column(name = "id_specialite")
    private String idSpecialite;
    @Column(name = "id_etablissement")
    private String idEtablissement;
    @Column(name = "date_debut")
    private Instant dateDebut;
    @Column(name = "date_Fin")
    private Instant dateFin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProfessionnel() {
        return idProfessionnel;
    }

    public void setIdProfessionnel(String idProfessionnel) {
        this.idProfessionnel = idProfessionnel;
    }

    public String getIdSpecialite() {
        return idSpecialite;
    }

    public void setIdSpecialite(String idSpecialite) {
        this.idSpecialite = idSpecialite;
    }

    public String getIdEtablissement() {
        return idEtablissement;
    }

    public void setIdEtablissement(String idEtablissement) {
        this.idEtablissement = idEtablissement;
    }

    public Instant getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Instant dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Instant getDateFin() {
        return dateFin;
    }

    public void setDateFin(Instant dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Affectation{" + "id=" + id + ", idProfessionnel=" + idProfessionnel + ", idSpecialite=" + idSpecialite + ", idEtablissement=" + idEtablissement + ", dateDebut=" + dateDebut + ", dateFin=" + dateFin + '}';
    }
    
    
}
