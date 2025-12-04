package com.base.frame.carnet.sante.daos;

import com.base.frame.carnet.sante.entities.ProfessionelSante;
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
public class ProfessionnelSanteDAO {

    @PersistenceContext
    private EntityManager em;

    public List<ProfessionelSante> findListeProfessionnelSante(String mc) {
        String req = "SELECT p FROM ProfessionelSante p WHERE 1=1 ";

        if (mc != null && !mc.trim().isEmpty()) {
            req += " AND (LOWER(p.username) LIKE LOWER(:mc) "
                    + "OR LOWER(p.firstName) LIKE LOWER(:mc) "
                    + "OR LOWER(p.lastName) LIKE LOWER(:mc) "
                    + "OR LOWER(p.numeroOrdre) LIKE LOWER(:mc) "
                    + "OR LOWER(p.email) LIKE LOWER(:mc))";
        }

        req += " ORDER BY p.createdDate DESC";

        Query query = em.createQuery(req);

        if (mc != null && !mc.trim().isEmpty()) {
            query.setParameter("mc", "%" + mc + "%");
        }

        return query.getResultList();
    }

    public Page<ProfessionelSante> findPageProfessionnelSante(String numeroOrdre, Pageable page, String specialite, String mc) {
        String req = "SELECT p FROM ProfessionelSante p WHERE 1=1 ";

        if (numeroOrdre != null && !numeroOrdre.trim().isEmpty()) {
            req += " AND LOWER(p.numeroOrdre) LIKE LOWER(:numeroOrdre) ";
        }

        if (specialite != null && !specialite.trim().isEmpty()) {
            req += " AND p.specialite = :specialite ";
        }

        if (mc != null && !mc.trim().isEmpty()) {
            req += " AND (LOWER(p.username) LIKE LOWER(:mc) "
                    + "OR LOWER(p.firstName) LIKE LOWER(:mc) "
                    + "OR LOWER(p.lastName) LIKE LOWER(:mc) "
                    + "OR LOWER(p.numeroOrdre) LIKE LOWER(:mc) "
                    + "OR LOWER(p.email) LIKE LOWER(:mc))";
        }

        req += " ORDER BY p.createdDate DESC";

        Query query = em.createQuery(req);

        if (numeroOrdre != null && !numeroOrdre.trim().isEmpty()) {
            query.setParameter("numeroOrdre", "%" + numeroOrdre + "%");
        }

        if (specialite != null && !specialite.trim().isEmpty()) {
            query.setParameter("specialite", specialite);
        }

        if (mc != null && !mc.trim().isEmpty()) {
            query.setParameter("mc", "%" + mc + "%");
        }

        // Compter le nombre total d'éléments
        String countReq = req.replace("SELECT p FROM", "SELECT COUNT(p) FROM");
        Query countQuery = em.createQuery(countReq);

        if (numeroOrdre != null && !numeroOrdre.trim().isEmpty()) {
            countQuery.setParameter("numeroOrdre", "%" + numeroOrdre + "%");
        }

        if (specialite != null && !specialite.trim().isEmpty()) {
            countQuery.setParameter("specialite", specialite);
        }

        if (mc != null && !mc.trim().isEmpty()) {
            countQuery.setParameter("mc", "%" + mc + "%");
        }

        Long total = (Long) countQuery.getSingleResult();

        // Pagination
        query.setFirstResult((int) page.getOffset());
        query.setMaxResults(page.getPageSize());

        List<ProfessionelSante> resultList = query.getResultList();

        return new PageImpl<>(resultList, page, total);
    }
}

