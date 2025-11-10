/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.base.frame.sante.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Bouchara
 */

@Controller
@RequestMapping(path = "/deux")
public class deux {
    
     @RequestMapping(path = "/form-elements")
    public String formElements(Model model) {
        return "pages/form-elements";
    }
    
}
