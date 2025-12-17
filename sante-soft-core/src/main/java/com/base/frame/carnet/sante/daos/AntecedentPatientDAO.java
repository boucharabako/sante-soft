package com.base.frame.carnet.sante.daos;

import com.base.frame.carnet.sante.entities.AntecedentPatient;
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
 * DAO pour AntecedentPatient
 * @author Bouchara
 */
@Component
@Transactional
public class AntecedentPatientDAO extends GenericDAO<AntecedentPatient, String> {

    public AntecedentPatientDAO() {
        super(AntecedentPatient.class);
    }

    @Autowired
    private CodificationService codificationService;

    /**
     * Recherche paginée des antécédents d'un patient
     * @param idPatient
     * @param categorieAntecedent
     * @param typeAntecedent
     * @param motCle
     * @param p
     * @return 
     */
    public Page<AntecedentPatient> findPageAntecedentPatient(String idPatient, String categorieAntecedent,
            String typeAntecedent, String motCle, Pageable p) {

        String queryChain = "SELECT DISTINCT a FROM AntecedentPatient a WHERE 1 = 1 ";

        // Filtrer les antécédents supprimés
        queryChain += " AND (a.deleted = false OR a.deleted IS NULL) ";

        if (idPatient != null && !idPatient.isEmpty() && !"undefined".equals(idPatient)) {
            queryChain += " AND a.idPatient = :idPatient ";
        }

        if (categorieAntecedent != null && !categorieAntecedent.isEmpty() && !"undefined".equals(categorieAntecedent)) {
            queryChain += " AND a.categorieAntecedent = :categorieAntecedent ";
        }

        if (typeAntecedent != null && !typeAntecedent.isEmpty() && !"undefined".equals(typeAntecedent)) {
            queryChain += " AND a.typeAntecedent = :typeAntecedent ";
        }

        if (motCle != null && !motCle.isEmpty() && !"undefined".equals(motCle)) {
            queryChain += " AND (UPPER(a.description) LIKE CONCAT('%',:motCle,'%') "
                    + " OR UPPER(a.statut) LIKE CONCAT('%',:motCle,'%') "
                    + " OR UPPER(a.traitementSuivi) LIKE CONCAT('%',:motCle,'%')) ";
        }

        queryChain += " ORDER BY a.createdDate DESC";

        System.out.println("queryChain AntecedentPatient: " + queryChain);
        Query query = em.createQuery(queryChain);

        if (idPatient != null && !idPatient.isEmpty() && !"undefined".equals(idPatient)) {
            query.setParameter("idPatient", idPatient);
        }

        if (categorieAntecedent != null && !categorieAntecedent.isEmpty() && !"undefined".equals(categorieAntecedent)) {
            query.setParameter("categorieAntecedent", categorieAntecedent);
        }

        if (typeAntecedent != null && !typeAntecedent.isEmpty() && !"undefined".equals(typeAntecedent)) {
            query.setParameter("typeAntecedent", typeAntecedent);
        }

        if (motCle != null && !motCle.isEmpty() && !"undefined".equals(motCle)) {
            query.setParameter("motCle", motCle.toUpperCase());
        }

        List<AntecedentPatient> list = query.getResultList();

        return codificationService.genericPage(list, p);
    }

    /**
     * Recherche liste des antécédents d'un patient
     * @param idPatient
     * @return
     */
    public List<AntecedentPatient> findListeAntecedentPatient(String idPatient) {
        String queryChain = "SELECT DISTINCT a FROM AntecedentPatient a WHERE a.idPatient = :idPatient "
                + "AND (a.deleted = false OR a.deleted IS NULL) "
                + "ORDER BY a.createdDate DESC";

        System.out.println("queryChain Liste AntecedentPatient: " + queryChain);
        Query query = em.createQuery(queryChain);
        query.setParameter("idPatient", idPatient);

        List<AntecedentPatient> list = query.getResultList();

        return list;
    }
}

