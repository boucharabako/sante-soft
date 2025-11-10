/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.base.frame.achat.fonctionnelle.repositories;

import com.base.frame.achat.fonctionnelle.dtos.DetailCommandeDTO;
import com.base.frame.achat.fonctionnelle.entities.DetailCommande;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NANO TECH
 */
public interface DetailCommandeRepository extends JpaRepository<DetailCommande, String>{
    
    @Query("Select new com.base.frame.achat.fonctionnelle.dtos.DetailCommandeDTO(d.id,d.idCommande,d.prixUnitaire,d.quantiteCommande,d.total,p.libelle) from DetailCommande d, Produit p where p.id=d.produit and d.idCommande=:idCommande")
    public List<DetailCommandeDTO> listDesDetailsCommande(@Param("idCommande") String idCommande);
    
}
