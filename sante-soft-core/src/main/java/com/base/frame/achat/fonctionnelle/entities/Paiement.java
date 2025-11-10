/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.base.frame.achat.fonctionnelle.entities;

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
@Table(name = "paiements", schema = "achat")
public class Paiement extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "date_paiement")
    private Instant datePaiement;
    @Column(name = "moyen_paiement")
    private String moyenPaiement;
    @Column(name = "montant_paiement")
    private BigDecimal montantPaiement;
    @Column(name = "commande_correspondante")
    private String commandeCorrespondante;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Instant datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getMoyenPaiement() {
        return moyenPaiement;
    }

    public void setMoyenPaiement(String moyenPaiement) {
        this.moyenPaiement = moyenPaiement;
    }

    public BigDecimal getMontantPaiement() {
        return montantPaiement;
    }

    public void setMontantPaiement(BigDecimal montantPaiement) {
        this.montantPaiement = montantPaiement;
    }

    public String getCommandeCorrespondante() {
        return commandeCorrespondante;
    }

    public void setCommandeCorrespondante(String commandeCorrespondante) {
        this.commandeCorrespondante = commandeCorrespondante;
    }
    
    

    @Override
    public String toString() {
        return "Paiement{" + "id=" + id + ", datePaiement=" + datePaiement + ", moyenPaiement=" + moyenPaiement + ", montantPaiement=" + montantPaiement + ", commandeCorrespondante=" + commandeCorrespondante + '}';
    }
    
    
    
    
    

}
