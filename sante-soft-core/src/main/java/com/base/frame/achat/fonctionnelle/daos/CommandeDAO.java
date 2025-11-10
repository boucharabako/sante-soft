/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.achat.fonctionnelle.daos;

/**
 *
 * @author Bouchara
 */
import com.base.frame.achat.fonctionnelle.entities.Commande;
import com.base.frame.socle.core.codification.annotation.dao.GenericDAO;
import com.base.frame.socle.core.codification.utils.SprUtils;
import com.base.frame.socle.core.service.CodificationService;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Bouchara
 */
@Component
@Transactional
public class CommandeDAO extends GenericDAO<Commande, String> {

    public CommandeDAO() {
        super(Commande.class);
    }

    @Autowired
    private CodificationService codificationService;

    public Page<Commande> findPageCommande(String motCle, Pageable p, Instant date) {

        //String queryChain = "SELECT DISTINCT d FROM Engin d WHERE 1=1";
        String queryChain = "";

        queryChain = "SELECT DISTINCT p FROM Commande p WHERE 1 = 1 ";
        if (!"undefined".equals(date) && date != null) {
            queryChain += " AND p.dateCommande BETWEEN :dateMin AND :dateMax  ";
        }

        if (motCle != null && !motCle.isEmpty()) {
            queryChain += "AND (upper(p.numeroCommande) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.etatCommande) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.numeroTelClient) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.nomClient) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.prenomClient) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.emailClient) LIKE CONCAT('%',:motCle,'%') "
                    + " )";
        }
        queryChain += "ORDER BY p.createdDate DESC";

        System.out.println("queryChain-----------------:" + queryChain);
        Query query = em.createQuery(queryChain);
        if (date != null) {
            query.setParameter("dateMin", SprUtils.getDateToUTC(date, LocalTime.MIN));
        }
        if (date != null) {
            query.setParameter("dateMax", SprUtils.getDateToUTC(date, LocalTime.MAX));
        }

        if (motCle != null && !motCle.isEmpty()) {
            query.setParameter("motCle", motCle.toUpperCase());
        }

        List<Commande> list = query.getResultList();

        return codificationService.genericPage(list, p);

    }

}
