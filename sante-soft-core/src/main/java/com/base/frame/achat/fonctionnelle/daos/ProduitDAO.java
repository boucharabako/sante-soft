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
import com.base.frame.achat.fonctionnelle.entities.Produit;
import com.base.frame.socle.core.codification.annotation.dao.GenericDAO;
import com.base.frame.socle.core.service.CodificationService;
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
public class ProduitDAO extends GenericDAO<Produit, String> {

    public ProduitDAO() {
        super(Produit.class);
    }

    @Autowired
    private CodificationService codificationService;

    public Page<Produit> findPageProduit(String code, Pageable p, String libelle, String description, String typeProduit, String motCle) {

        //String queryChain = "SELECT DISTINCT d FROM Engin d WHERE 1=1";
        String queryChain = "";

        queryChain = "SELECT DISTINCT p FROM Produit p WHERE 1 = 1 ";

        if (!"undefined".equals(code) && code != null && !code.isEmpty()) {
            System.out.println("--------------------------" + code);
            queryChain += " AND (upper(p.code) LIKE CONCAT('%',:code,'%') )";
        }
        if (!"undefined".equals(libelle) && libelle != null && !libelle.isEmpty()) {
            queryChain += " AND (upper(p.libelle) LIKE CONCAT('%',:libelle,'%')) ";
        }
        if (!"undefined".equals(description) && description != null && !description.isEmpty()) {
            queryChain += " AND (upper(p.description) LIKE CONCAT('%',:description,'%')) ";
        }

        if (!"undefined".equals(typeProduit) && typeProduit != null && !typeProduit.isEmpty()) {
            System.out.println("typeProduit" + typeProduit);
            queryChain += " AND p.typeProduit=:typeProduit ";
        }

        if (motCle != null && !motCle.isEmpty()) {
            queryChain += " AND (upper(p.code) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.libelle) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.description) LIKE CONCAT('%',:motCle,'%') "
                    + " )";
        }
        queryChain += "ORDER BY p.createdDate DESC";

        System.out.println("queryChain-----------------:" + queryChain);
        Query query = em.createQuery(queryChain);
//        if (motCle != null && !motCle.isEmpty()) {
//            query.setParameter("motCle", motCle);
//        }
        if (code != null && !code.isEmpty()) {
            query.setParameter("code", code.toUpperCase());
        }
        if (libelle != null && !libelle.isEmpty()) {
            query.setParameter("libelle", libelle.toUpperCase());
        }

        if (description != null && !description.isEmpty()) {
            query.setParameter("description", description.toUpperCase());
        }
        if (typeProduit != null && !typeProduit.isEmpty()) {
            query.setParameter("typeProduit", typeProduit.toUpperCase());
        }
        if (motCle != null && !motCle.isEmpty()) {
            query.setParameter("motCle", motCle.toUpperCase());
        }

        List<Produit> list = query.getResultList();

        return codificationService.genericPage(list, p);

    }

    public List<Produit> findListeProduit(String motCle) {
        String queryChain = "";
        queryChain = "SELECT DISTINCT p FROM Produit p, TypeProduit tp WHERE tp.id=p.typeProduit ";

        if (motCle != null && !motCle.isEmpty()) {
            queryChain += " AND (upper(p.code) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.libelle) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.description) LIKE CONCAT('%',:motCle,'%') "
//                    + " OR p.prix LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(tp.libelle) LIKE CONCAT('%',:motCle,'%') "
                    + " )";
        }
        queryChain += "ORDER BY p.createdDate DESC";

        System.out.println("queryChain-----------------:" + queryChain);
        Query query = em.createQuery(queryChain);

        if (motCle != null && !motCle.isEmpty()) {
            query.setParameter("motCle", motCle.toUpperCase());
        }

        List<Produit> list = query.getResultList();

        return list;

    }

}
