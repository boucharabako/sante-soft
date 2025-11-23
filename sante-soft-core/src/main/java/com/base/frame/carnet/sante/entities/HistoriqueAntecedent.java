/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.entities;

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
@Table(name = "historique_antecedent", schema = "sante")
public class HistoriqueAntecedent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "id_antecedent")
    private String idAntecedent;
    @Column(name = "id_professionnel")
    private String idProfessionnel;
    @Column(name = "action")
    private String action;
    @Column(name = "date_action")
    private Instant dateAction;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdAntecedent() {
        return idAntecedent;
    }

    public void setIdAntecedent(String idAntecedent) {
        this.idAntecedent = idAntecedent;
    }

    public String getIdProfessionnel() {
        return idProfessionnel;
    }

    public void setIdProfessionnel(String idProfessionnel) {
        this.idProfessionnel = idProfessionnel;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Instant getDateAction() {
        return dateAction;
    }

    public void setDateAction(Instant dateAction) {
        this.dateAction = dateAction;
    }

    @Override
    public String toString() {
        return "HistoriqueAntecedent{" + "id=" + id + ", idAntecedent=" + idAntecedent + ", idProfessionnel=" + idProfessionnel + ", action=" + action + ", dateAction=" + dateAction + '}';
    }
    
    
}
