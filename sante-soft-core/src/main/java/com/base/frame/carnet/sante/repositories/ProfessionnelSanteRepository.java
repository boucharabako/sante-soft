package com.base.frame.carnet.sante.repositories;

import com.base.frame.carnet.sante.entities.ProfessionelSante;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Bouchara
 */
@Repository
public interface ProfessionnelSanteRepository extends JpaRepository<ProfessionelSante, String> {

    Optional<ProfessionelSante> findByUsername(String username);

    Optional<ProfessionelSante> findByEmail(String email);

    Optional<ProfessionelSante> findByNumeroOrdre(String numeroOrdre);

    @Query(value = "SELECT MAX(CAST(SUBSTRING(numero_ordre, 6) AS INTEGER)) FROM sante.professionnel_sante WHERE numero_ordre LIKE :yearPrefix", nativeQuery = true)
    Optional<Integer> findMaxNumeroOrdreByYear(@Param("yearPrefix") String yearPrefix);
}

