/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.base.frame.carnet.sante.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Bouchara
 */
@Controller
@RequestMapping(path = "/gestion/antecedent")
public class AntecedentController {
    
    public static final String PAGE_ANTECEDENT_PATH = "fonctionnelle/gestion_antecedent";

    @RequestMapping()
    public String homeList(Model model) {
        return PAGE_ANTECEDENT_PATH;
    }
    
}

