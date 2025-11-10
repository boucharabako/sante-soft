/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.base.frame.achat.fonctionnelle.repositories;

import com.base.frame.achat.fonctionnelle.entities.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NANO TECH
 */
public interface ProduitRepository extends JpaRepository<Produit, String>{
    
    @Query("Select p from Produit p where p.code=:code")
    public Produit findProduitByCode(@Param("code") String code);
}
