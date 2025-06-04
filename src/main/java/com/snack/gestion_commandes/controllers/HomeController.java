package com.snack.gestion_commandes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {



    // Vous pouvez aussi avoir un @GetMapping("/index") si cette page est vraiment distincte
    @GetMapping("/index")
    public String userIndex() {
        return "index"; // Ceci sera votre page "index.html" spécifique aux utilisateurs connectés avec ROLE_USER
    }
}