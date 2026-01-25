package com.base.frame.carnet.sante.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller MVC pour le tableau de bord
 * @author Bouchara
 */
@Controller
@RequestMapping(path = "/tableau-bord")
public class TableauBordController {

    public static final String PAGE_TABLEAU_BORD_FILE_PATH = "fonctionnelle/tableau_bord";

    /**
     * Afficher la page du tableau de bord
     * @param model
     * @return Vue du tableau de bord
     */
    @RequestMapping("")
    public String afficherTableauBord(Model model) {
        return PAGE_TABLEAU_BORD_FILE_PATH;
    }
}

