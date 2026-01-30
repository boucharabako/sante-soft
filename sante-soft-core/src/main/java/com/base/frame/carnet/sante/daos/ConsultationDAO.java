package com.base.frame.carnet.sante.daos;

import com.base.frame.carnet.sante.entities.Consultation;
import com.base.frame.socle.core.service.CodificationService;
import java.time.Instant;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * DAO pour la gestion des consultations avec pagination
 * @author Bouchara
 */
@Component
@Transactional
public class ConsultationDAO {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private CodificationService codificationService;

    /**
     * Recherche paginée des consultations d'un professionnel
     * @param idProfessionnel ID du professionnel
     * @param motCle Mot-clé de recherche (patient, motif, diagnostic)
     * @param typeConsultation Type de consultation
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @param p Pagination
     * @return Page de consultations
     */
    public Page<Consultation> findPageConsultationByProfessionnel(
            String idProfessionnel,
            String motCle,
            String typeConsultation,
            Instant dateDebut,
            Instant dateFin,
            Pageable p) {

        // Requête optimisée sans jointure - la recherche par nom patient se fera côté service si nécessaire
        String queryChain = "SELECT c FROM Consultation c " +
                           "WHERE c.idProfessionnelSante = :idProfessionnel ";

        // Filtre par mot-clé (recherche dans motif et diagnostic uniquement pour la performance)
        if (motCle != null && !motCle.isEmpty() && !"undefined".equals(motCle)) {
            queryChain += " AND (UPPER(c.motif) LIKE CONCAT('%',:motCle,'%') " +
                         " OR UPPER(c.diagnostic) LIKE CONCAT('%',:motCle,'%')) ";
        }

        // Filtre par type de consultation
        if (typeConsultation != null && !typeConsultation.isEmpty() && !"undefined".equals(typeConsultation)) {
            queryChain += " AND c.typeConsultation = :typeConsultation ";
        }

        // Filtre par date de début
        if (dateDebut != null && !"undefined".equals(dateDebut)) {
            queryChain += " AND c.dateConsultation >= :dateDebut ";
        }

        // Filtre par date de fin
        if (dateFin != null && !"undefined".equals(dateFin)) {
            queryChain += " AND c.dateConsultation <= :dateFin ";
        }

        queryChain += " ORDER BY c.dateConsultation DESC";

        Query query = em.createQuery(queryChain);

        // Paramètres obligatoires
        query.setParameter("idProfessionnel", idProfessionnel);

        // Paramètres optionnels
        if (motCle != null && !motCle.isEmpty() && !"undefined".equals(motCle)) {
            query.setParameter("motCle", motCle.toUpperCase());
        }

        if (typeConsultation != null && !typeConsultation.isEmpty() && !"undefined".equals(typeConsultation)) {
            query.setParameter("typeConsultation", typeConsultation);
        }

        if (dateDebut != null && !"undefined".equals(dateDebut)) {
            query.setParameter("dateDebut", dateDebut);
        }

        if (dateFin != null && !"undefined".equals(dateFin)) {
            query.setParameter("dateFin", dateFin);
        }

        // Pagination directe avec setFirstResult et setMaxResults pour de meilleures performances
        int pageSize = p.getPageSize();
        int pageNumber = p.getPageNumber();

        // Compter le total d'éléments
        String countQuery = queryChain.replace("SELECT c FROM", "SELECT COUNT(c) FROM");
        countQuery = countQuery.substring(0, countQuery.indexOf("ORDER BY")); // Enlever ORDER BY pour le count

        Query countQ = em.createQuery(countQuery);
        countQ.setParameter("idProfessionnel", idProfessionnel);

        if (motCle != null && !motCle.isEmpty() && !"undefined".equals(motCle)) {
            countQ.setParameter("motCle", motCle.toUpperCase());
        }
        if (typeConsultation != null && !typeConsultation.isEmpty() && !"undefined".equals(typeConsultation)) {
            countQ.setParameter("typeConsultation", typeConsultation);
        }
        if (dateDebut != null && !"undefined".equals(dateDebut)) {
            countQ.setParameter("dateDebut", dateDebut);
        }
        if (dateFin != null && !"undefined".equals(dateFin)) {
            countQ.setParameter("dateFin", dateFin);
        }

        Long total = (Long) countQ.getSingleResult();

        // Récupérer les résultats paginés
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        List<Consultation> list = query.getResultList();

        return new org.springframework.data.domain.PageImpl<>(list, p, total);
    }

    /**
     * Recherche paginée des consultations d'un patient
     * @param idPatient ID du patient
     * @param motCle Mot-clé de recherche (motif, diagnostic)
     * @param typeConsultation Type de consultation
     * @param dateDebut Date de début
     * @param dateFin Date de fin
     * @param p Pagination
     * @return Page de consultations
     */
    public Page<Consultation> findPageConsultationByPatient(
            String idPatient,
            String motCle,
            String typeConsultation,
            Instant dateDebut,
            Instant dateFin,
            Pageable p) {

        // Requête optimisée sans jointure pour de meilleures performances
        String queryChain = "SELECT c FROM Consultation c " +
                           "WHERE c.idPatient = :idPatient ";

        // Filtre par mot-clé (recherche dans motif et diagnostic uniquement pour la performance)
        if (motCle != null && !motCle.isEmpty() && !"undefined".equals(motCle)) {
            queryChain += " AND (UPPER(c.motif) LIKE CONCAT('%',:motCle,'%') " +
                         " OR UPPER(c.diagnostic) LIKE CONCAT('%',:motCle,'%')) ";
        }

        // Filtre par type de consultation
        if (typeConsultation != null && !typeConsultation.isEmpty() && !"undefined".equals(typeConsultation)) {
            queryChain += " AND c.typeConsultation = :typeConsultation ";
        }

        // Filtre par date de début
        if (dateDebut != null && !"undefined".equals(dateDebut)) {
            queryChain += " AND c.dateConsultation >= :dateDebut ";
        }

        // Filtre par date de fin
        if (dateFin != null && !"undefined".equals(dateFin)) {
            queryChain += " AND c.dateConsultation <= :dateFin ";
        }

        queryChain += " ORDER BY c.dateConsultation DESC";

        Query query = em.createQuery(queryChain);

        // Paramètres obligatoires
        query.setParameter("idPatient", idPatient);

        // Paramètres optionnels
        if (motCle != null && !motCle.isEmpty() && !"undefined".equals(motCle)) {
            query.setParameter("motCle", motCle.toUpperCase());
        }

        if (typeConsultation != null && !typeConsultation.isEmpty() && !"undefined".equals(typeConsultation)) {
            query.setParameter("typeConsultation", typeConsultation);
        }

        if (dateDebut != null && !"undefined".equals(dateDebut)) {
            query.setParameter("dateDebut", dateDebut);
        }

        if (dateFin != null && !"undefined".equals(dateFin)) {
            query.setParameter("dateFin", dateFin);
        }

        // Pagination directe avec setFirstResult et setMaxResults pour de meilleures performances
        int pageSize = p.getPageSize();
        int pageNumber = p.getPageNumber();

        // Compter le total d'éléments
        String countQuery = queryChain.replace("SELECT c FROM", "SELECT COUNT(c) FROM");
        countQuery = countQuery.substring(0, countQuery.indexOf("ORDER BY")); // Enlever ORDER BY pour le count

        Query countQ = em.createQuery(countQuery);
        countQ.setParameter("idPatient", idPatient);

        if (motCle != null && !motCle.isEmpty() && !"undefined".equals(motCle)) {
            countQ.setParameter("motCle", motCle.toUpperCase());
        }
        if (typeConsultation != null && !typeConsultation.isEmpty() && !"undefined".equals(typeConsultation)) {
            countQ.setParameter("typeConsultation", typeConsultation);
        }
        if (dateDebut != null && !"undefined".equals(dateDebut)) {
            countQ.setParameter("dateDebut", dateDebut);
        }
        if (dateFin != null && !"undefined".equals(dateFin)) {
            countQ.setParameter("dateFin", dateFin);
        }

        Long total = (Long) countQ.getSingleResult();

        // Récupérer les résultats paginés
        query.setFirstResult(pageNumber * pageSize);
        query.setMaxResults(pageSize);

        List<Consultation> list = query.getResultList();

        return new org.springframework.data.domain.PageImpl<>(list, p, total);
    }

    /**
     * Compte les consultations d'un professionnel dans une période donnée
     * @param idProfessionnel ID du professionnel
     * @param dateDebut Date de début (inclusive)
     * @param dateFin Date de fin (exclusive, peut être null)
     * @return Nombre de consultations
     */
    public Long countConsultationsByProfessionnelAndDateRange(
            String idProfessionnel,
            Instant dateDebut,
            Instant dateFin) {

        String queryChain = "SELECT COUNT(c) FROM Consultation c " +
                           "WHERE c.idProfessionnelSante = :idProfessionnel " +
                           "AND c.dateConsultation >= :dateDebut";

        if (dateFin != null) {
            queryChain += " AND c.dateConsultation < :dateFin";
        }

        Query query = em.createQuery(queryChain);
        query.setParameter("idProfessionnel", idProfessionnel);
        query.setParameter("dateDebut", dateDebut);

        if (dateFin != null) {
            query.setParameter("dateFin", dateFin);
        }

        return (Long) query.getSingleResult();
    }
}

