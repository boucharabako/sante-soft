/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.base.frame.achat.fonctionnelle.repositories;

import com.base.frame.achat.fonctionnelle.entities.CategorieProduit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author NANO TECH
 */
public interface CategorieProduitRepository extends JpaRepository<CategorieProduit, String>{
    
}
