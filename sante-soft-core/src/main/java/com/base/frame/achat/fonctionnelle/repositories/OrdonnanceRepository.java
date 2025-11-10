/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.base.frame.achat.fonctionnelle.repositories;

import com.base.frame.achat.fonctionnelle.entities.Ordonnance;
import com.base.frame.achat.fonctionnelle.entities.TypeProduit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NANO TECH
 */
public interface OrdonnanceRepository extends JpaRepository<Ordonnance, String> {

    @Query("select o from Ordonnance o where o.commandeCorresp=:commandeCorresp")
    public Optional<Ordonnance> findOrdoByCommande(@Param("commandeCorresp") String commandeCorresp);

}
