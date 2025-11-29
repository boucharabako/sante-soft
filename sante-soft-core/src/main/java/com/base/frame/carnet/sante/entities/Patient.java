/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.entities;

import com.base.frame.account.entity.Utilisateur;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author NANO TECH
 */
@Entity
@Table(name = "patient", schema = "sante")
public class Patient extends Utilisateur implements Serializable {

    @Column(name = "groupe_sanguin")
    private String groupeSanguin;
    @Column(name = "numero_carnet")
    private String numeroCarnet;
    @Column(name = "date_enregistrement")
    private Instant dateEnregistrement;

    public String getGroupeSanguin() {
        return groupeSanguin;
    }

    public void setGroupeSanguin(String groupeSanguin) {
        this.groupeSanguin = groupeSanguin;
    }

    public String getNumeroCarnet() {
        return numeroCarnet;
    }

    public void setNumeroCarnet(String numeroCarnet) {
        this.numeroCarnet = numeroCarnet;
    }

    public Instant getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Instant dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

}
