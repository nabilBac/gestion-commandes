package com.snack.gestion_commandes.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {




    @GetMapping("/index")
    public String userIndex() {
        return "index";
    }
}