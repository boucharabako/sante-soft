package com.base.frame.carnet.sante.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrôleur MVC pour la gestion des vaccinations
 * @author Bouchara
 */
@Controller
@RequestMapping(path = "/gestion/vaccination")
public class VaccinationController {

    public static final String PAGE_VACCINATION_PATH = "fonctionnelle/gestion_vaccination";

    @RequestMapping()
    public String homeList(Model model) {
        return PAGE_VACCINATION_PATH;
    }
}

