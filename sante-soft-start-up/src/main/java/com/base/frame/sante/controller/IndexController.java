package com.base.frame.sante.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import groovyjarjarpicocli.CommandLine.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author AdminPost
 */
@Controller
@RequestMapping(path = "/l")
public class IndexController {

    @GetMapping(name = "/")
    public String home(Model model) {
        return "teste";
    }
}
