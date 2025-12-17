/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.base.frame.achat.fonctionnelle.controller;

/**
 *
 * @author ATD
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/gestion/consultation")
public class ConsultationController {

    public static final String PAGE_CONSULTATION_PATH = "fonctionnelle/gestion_consultation";
    public static final String PAGE_LISTE_CONSULTATIONS_PATH = "fonctionnelle/liste_consultations";

    @RequestMapping()
    public String homeList(Model model) {
        return PAGE_CONSULTATION_PATH;
    }

    @RequestMapping("/liste")
    public String listeConsultations(Model model) {
        return PAGE_LISTE_CONSULTATIONS_PATH;
    }

}
