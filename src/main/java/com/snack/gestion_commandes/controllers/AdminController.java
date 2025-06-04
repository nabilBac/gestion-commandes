package com.snack.gestion_commandes.controllers;

import com.snack.gestion_commandes.models.Commande;
import com.snack.gestion_commandes.services.CommandeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;


import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final CommandeService commandeService;
    public AdminController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }



    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/dashboard"; // Assure-toi d’avoir ce template dans templates/admin/dashboard.html
    }
    @GetMapping("/commandes/simple")
    @PreAuthorize("hasRole('ADMIN')")
    public String listeCommandesAdmin(Model model) {
        List<Commande> commandes = commandeService.toutesLesCommandes();
        model.addAttribute("commandes", commandes);
        return "admin/liste_commandes"; // à adapter selon ton chemin de fichier
    }

}
