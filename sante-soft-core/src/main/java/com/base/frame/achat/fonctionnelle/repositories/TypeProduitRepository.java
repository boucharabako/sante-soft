/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.base.frame.achat.fonctionnelle.repositories;

import com.base.frame.achat.fonctionnelle.dtos.TypeProduitDTO;
import com.base.frame.achat.fonctionnelle.entities.TypeProduit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NANO TECH
 */
public interface TypeProduitRepository extends JpaRepository<TypeProduit, String>{
    
    @Query("select new com.base.frame.achat.fonctionnelle.dtos.TypeProduitDTO(tp.id,tp.code,tp.libelle) from TypeProduit tp")
    public List<TypeProduitDTO>getListTypeProduit();

    @Query("select tp from TypeProduit tp where tp.id=:idTypeProduit")
    public TypeProduit findTypeProduit(@Param("idTypeProduit")String idTypeProduit);
    
}
