package com.snack.gestion_commandes.controllers;

import com.snack.gestion_commandes.models.Commande;
import com.snack.gestion_commandes.models.LigneCommande;
import com.snack.gestion_commandes.models.LignePanier;
import com.snack.gestion_commandes.services.CommandeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/commande")
public class CommandeController {

    private final CommandeService commandeService;

    // 🔧 Constructeur pour injecter CommandeService
    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping("/valider")
    public String validerCommande(HttpSession session, Model model, Authentication authentication) {
        List<LignePanier> panier = (List<LignePanier>) session.getAttribute("panier");

        if (panier == null || panier.isEmpty()) {
            return "redirect:/panier";
        }

        String username = authentication.getName(); // récupérer l’utilisateur connecté

        // Appeler le service avec panier et username (ce que la méthode attend)
        Commande commande = commandeService.validerCommande(panier, username);

        session.removeAttribute("panier");
        model.addAttribute("commande", commande);

        return "confirmation"; // confirmation.html à créer
    }
    @PostMapping("/supprimer/{id}")
    public String supprimerCommande(@PathVariable Long id) {
        commandeService.supprimerCommande(id);
        return "redirect:/commande/liste";
    }
    @GetMapping("/liste")
    public String listerCommandes(Model model) {
        List<Commande> commandes = commandeService.toutesLesCommandes();
        model.addAttribute("commandes", commandes);
        return "admin/liste_commandes";
        
    }
}
