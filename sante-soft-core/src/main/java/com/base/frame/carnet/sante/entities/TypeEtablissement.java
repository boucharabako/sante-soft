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
@Table(name = "type_etablissement", schema = "sante")
public class TypeEtablissement extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "code_type_etablissement")
    private String codeTypeEtablissement;
    @Column(name = "libelle_type_etablissement")
    private String libelleTypeEtablissement;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCodeTypeEtablissement() {
        return codeTypeEtablissement;
    }

    public void setCodeTypeEtablissement(String codeTypeEtablissement) {
        this.codeTypeEtablissement = codeTypeEtablissement;
    }

    public String getLibelleTypeEtablissement() {
        return libelleTypeEtablissement;
    }

    public void setLibelleTypeEtablissement(String libelleTypeEtablissement) {
        this.libelleTypeEtablissement = libelleTypeEtablissement;
    }

    @Override
    public String toString() {
        return "TypeEtablissement{" + "id=" + id + ", codeTypeEtablissement=" + codeTypeEtablissement + ", libelleTypeEtablissement=" + libelleTypeEtablissement + '}';
    }

   
}
