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
@Table(name = "professionnel_sante", schema = "sante")
public class ProfessionelSante extends Utilisateur implements Serializable {

    @Column(name = "numero_ordre")
    private String numeroOrdre;


    public ProfessionelSante() {
    }

    public ProfessionelSante(String numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
      
    }

    public String getNumeroOrdre() {
        return numeroOrdre;
    }

    public void setNumeroOrdre(String numeroOrdre) {
        this.numeroOrdre = numeroOrdre;
    }

    @Override
    public String toString() {
        return "ProfessionelSante{" + "numeroOrdre=" + numeroOrdre + '}';
    }

}
