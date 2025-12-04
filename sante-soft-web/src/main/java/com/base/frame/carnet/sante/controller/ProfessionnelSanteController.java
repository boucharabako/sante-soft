/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
@RequestMapping(path = "/gestion/professionnel")
public class ProfessionnelSanteController {

    public static final String PAGE_LIST_FILE_PATH = "fonctionnelle/gestion_professionnel";

    @RequestMapping()
    public String homeList(Model model) {
        return PAGE_LIST_FILE_PATH;
    }
}

