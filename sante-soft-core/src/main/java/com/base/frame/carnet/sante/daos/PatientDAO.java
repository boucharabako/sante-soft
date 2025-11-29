/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.carnet.sante.daos;

/**
 *
 * @author Bouchara
 */
import com.base.frame.carnet.sante.entities.Patient;
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
public class PatientDAO extends GenericDAO<Patient, String> {

    public PatientDAO() {
        super(Patient.class);
    }

    @Autowired
    private CodificationService codificationService;

    public Page<Patient> findPagePatient(String numeroCarnet, Pageable p, String groupeSanguin, String motCle) {

        String queryChain = "";

        queryChain = "SELECT DISTINCT p FROM Patient p WHERE 1 = 1 ";

        if (!"undefined".equals(numeroCarnet) && numeroCarnet != null && !numeroCarnet.isEmpty()) {
            System.out.println("--------------------------" + numeroCarnet);
            queryChain += " AND (upper(p.numeroCarnet) LIKE CONCAT('%',:numeroCarnet,'%') )";
        }
        if (!"undefined".equals(groupeSanguin) && groupeSanguin != null && !groupeSanguin.isEmpty()) {
            queryChain += " AND (upper(p.groupeSanguin) LIKE CONCAT('%',:groupeSanguin,'%')) ";
        }

        if (motCle != null && !motCle.isEmpty()) {
            queryChain += " AND (upper(p.numeroCarnet) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.groupeSanguin) LIKE CONCAT('%',:motCle,'%') "
                    + " )";
        }
        queryChain += "ORDER BY p.createdDate DESC";

        System.out.println("queryChain-----------------:" + queryChain);
        Query query = em.createQuery(queryChain);

        if (numeroCarnet != null && !numeroCarnet.isEmpty()) {
            query.setParameter("numeroCarnet", numeroCarnet.toUpperCase());
        }
        if (groupeSanguin != null && !groupeSanguin.isEmpty()) {
            query.setParameter("groupeSanguin", groupeSanguin.toUpperCase());
        }

        if (motCle != null && !motCle.isEmpty()) {
            query.setParameter("motCle", motCle.toUpperCase());
        }

        List<Patient> list = query.getResultList();

        return codificationService.genericPage(list, p);

    }

    public List<Patient> findListePatient(String motCle) {
        String queryChain = "";
        queryChain = "SELECT DISTINCT p FROM Patient p WHERE 1 = 1 ";

        if (motCle != null && !motCle.isEmpty()) {
            queryChain += " AND (upper(p.numeroCarnet) LIKE CONCAT('%',:motCle,'%') "
                    + " OR upper(p.groupeSanguin) LIKE CONCAT('%',:motCle,'%') "
                    + " )";
        }
        queryChain += "ORDER BY p.createdDate DESC";

        System.out.println("queryChain-----------------:" + queryChain);
        Query query = em.createQuery(queryChain);

        if (motCle != null && !motCle.isEmpty()) {
            query.setParameter("motCle", motCle.toUpperCase());
        }

        List<Patient> list = query.getResultList();

        return list;

    }

}

