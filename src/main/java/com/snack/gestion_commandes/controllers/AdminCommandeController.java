package com.snack.gestion_commandes.controllers;

import com.snack.gestion_commandes.models.StatutCommande;
import com.snack.gestion_commandes.services.CommandeService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/commandes")
@PreAuthorize("hasRole('ADMIN')")  // Restreint tout ce contrôleur aux admins
public class AdminCommandeController {

    private final CommandeService commandeService;

    public AdminCommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    // Liste toutes les commandes côté admin
    @GetMapping
    public String listCommandes(Model model) {
        model.addAttribute("commandes", commandeService.toutesLesCommandes());
        model.addAttribute("statuts", StatutCommande.values()); // pour un select dans la vue
        return "admin/commandes/list"; // Vue Thymeleaf à créer
    }

    // Met à jour le statut d’une commande (via formulaire ou bouton)
    @PostMapping("/{id}/statut")
    public String updateStatut(@PathVariable Long id, @RequestParam StatutCommande statut) {
        commandeService.updateStatut(id, statut);
        return "redirect:/admin/commandes";
    }


}
