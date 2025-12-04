package com.base.frame.carnet.sante.daos;

import com.base.frame.carnet.sante.entities.Etablissement;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public class EtablissementDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Etablissement> findListeEtablissement(String mc) {
        String req = "SELECT e FROM Etablissement e WHERE 1=1 ";

        if (mc != null && !mc.trim().isEmpty()) {
            req += " AND (LOWER(e.codeEtablissement) LIKE LOWER(:mc) "
                    + "OR LOWER(e.libelleEtablissement) LIKE LOWER(:mc) "
                    + "OR LOWER(e.regionEtablissement) LIKE LOWER(:mc) "
                    + "OR LOWER(e.adresse) LIKE LOWER(:mc))";
        }

        req += " ORDER BY e.createdDate DESC";

        Query query = em.createQuery(req);

        if (mc != null && !mc.trim().isEmpty()) {
            query.setParameter("mc", "%" + mc + "%");
        }

        return query.getResultList();
    }

    public Page<Etablissement> findPageEtablissement(String code, Pageable page, String libelle, String region, String typeEtablissement, String mc) {
        String req = "SELECT e FROM Etablissement e WHERE 1=1 ";

        if (code != null && !code.trim().isEmpty()) {
            req += " AND LOWER(e.codeEtablissement) LIKE LOWER(:code) ";
        }

        if (libelle != null && !libelle.trim().isEmpty()) {
            req += " AND LOWER(e.libelleEtablissement) LIKE LOWER(:libelle) ";
        }

        if (region != null && !region.trim().isEmpty()) {
            req += " AND LOWER(e.regionEtablissement) LIKE LOWER(:region) ";
        }

        if (typeEtablissement != null && !typeEtablissement.trim().isEmpty()) {
            req += " AND e.typeEtablissement = :typeEtablissement ";
        }

        if (mc != null && !mc.trim().isEmpty()) {
            req += " AND (LOWER(e.codeEtablissement) LIKE LOWER(:mc) "
                    + "OR LOWER(e.libelleEtablissement) LIKE LOWER(:mc) "
                    + "OR LOWER(e.regionEtablissement) LIKE LOWER(:mc) "
                    + "OR LOWER(e.adresse) LIKE LOWER(:mc))";
        }

        req += " ORDER BY e.createdDate DESC";

        Query query = em.createQuery(req);

        if (code != null && !code.trim().isEmpty()) {
            query.setParameter("code", "%" + code + "%");
        }

        if (libelle != null && !libelle.trim().isEmpty()) {
            query.setParameter("libelle", "%" + libelle + "%");
        }

        if (region != null && !region.trim().isEmpty()) {
            query.setParameter("region", "%" + region + "%");
        }

        if (typeEtablissement != null && !typeEtablissement.trim().isEmpty()) {
            query.setParameter("typeEtablissement", typeEtablissement);
        }

        if (mc != null && !mc.trim().isEmpty()) {
            query.setParameter("mc", "%" + mc + "%");
        }

        // Compter le nombre total d'éléments (sans ORDER BY)
        String countReq = req.replace("SELECT e FROM", "SELECT COUNT(e) FROM");
        countReq = countReq.replaceAll("ORDER BY.*", ""); // Supprimer le ORDER BY pour le COUNT
        Query countQuery = em.createQuery(countReq);

        if (code != null && !code.trim().isEmpty()) {
            countQuery.setParameter("code", "%" + code + "%");
        }

        if (libelle != null && !libelle.trim().isEmpty()) {
            countQuery.setParameter("libelle", "%" + libelle + "%");
        }

        if (region != null && !region.trim().isEmpty()) {
            countQuery.setParameter("region", "%" + region + "%");
        }

        if (typeEtablissement != null && !typeEtablissement.trim().isEmpty()) {
            countQuery.setParameter("typeEtablissement", typeEtablissement);
        }

        if (mc != null && !mc.trim().isEmpty()) {
            countQuery.setParameter("mc", "%" + mc + "%");
        }

        Long total = (Long) countQuery.getSingleResult();

        // Pagination
        query.setFirstResult((int) page.getOffset());
        query.setMaxResults(page.getPageSize());

        List<Etablissement> resultList = query.getResultList();

        return new PageImpl<>(resultList, page, total);
    }
}

