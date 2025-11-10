/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.base.frame.achat.fonctionnelle.entities;

import com.base.frame.socle.utils.audit.AbstractAuditingEntity;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author NANO TECH
 */
@Entity
@Table(name = "ordonnance", schema = "achat")
public class Ordonnance extends AbstractAuditingEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "identifiant_client")
    private String identifiantClient;
    @Column(name = "date_telechargement")
    private Instant dateTelechargement;
    @Lob
    @Column(name = "file_path")
    private byte[] imageOrdonnance;
    @Column(name = "commande_correspondante")
    private String commandeCorresp;
    @Column(name = "type_fichier")
    private String typeFichier;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentifiantClient() {
        return identifiantClient;
    }

    public void setIdentifiantClient(String identifiantClient) {
        this.identifiantClient = identifiantClient;
    }

    public Instant getDateTelechargement() {
        return dateTelechargement;
    }

    public void setDateTelechargement(Instant dateTelechargement) {
        this.dateTelechargement = dateTelechargement;
    }

    public byte[] getImageOrdonnance() {
        return imageOrdonnance;
    }

    public void setImageOrdonnance(byte[] imageOrdonnance) {
        this.imageOrdonnance = imageOrdonnance;
    }

    public String getCommandeCorresp() {
        return commandeCorresp;
    }

    public void setCommandeCorresp(String commandeCorresp) {
        this.commandeCorresp = commandeCorresp;
    }

    public String getTypeFichier() {
        return typeFichier;
    }

    public void setTypeFichier(String typeFichier) {
        this.typeFichier = typeFichier;
    }
    

    @Override
    public String toString() {
        return "Ordonnance{" + "id=" + id + ", identifiantClient=" + identifiantClient + ", dateTelechargement=" + dateTelechargement + ", imageOrdonnance=" + imageOrdonnance + ", commandeCorresp=" + commandeCorresp + '}';
    }
    
}
