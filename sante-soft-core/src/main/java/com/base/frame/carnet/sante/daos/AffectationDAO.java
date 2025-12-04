/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.daos;

import com.base.frame.carnet.sante.entities.Affectation;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public class AffectationDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Récupère toutes les affectations d'un professionnel
     * @param idProfessionnel
     * @return 
     */
    public List<Affectation> findAffectationsByProfessionnel(String idProfessionnel) {
        String jpql = "SELECT a FROM Affectation a WHERE a.idProfessionnel = :idProfessionnel ORDER BY a.dateDebut DESC";
        TypedQuery<Affectation> query = em.createQuery(jpql, Affectation.class);
        query.setParameter("idProfessionnel", idProfessionnel);
        return query.getResultList();
    }
    
    /**
     * Récupère toutes les affectations d'un établissement
     * @param idEtablissement
     * @return 
     */
    public List<Affectation> findAffectationsByEtablissement(String idEtablissement) {
        String jpql = "SELECT a FROM Affectation a WHERE a.idEtablissement = :idEtablissement ORDER BY a.dateDebut DESC";
        TypedQuery<Affectation> query = em.createQuery(jpql, Affectation.class);
        query.setParameter("idEtablissement", idEtablissement);
        return query.getResultList();
    }
}

