package com.snack.gestion_commandes.controllers;



import com.snack.gestion_commandes.models.Commande;
import com.snack.gestion_commandes.services.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user/commandes")
public class UserCommandeController {

    @Autowired
    private CommandeService commandeService;

    @GetMapping
    public String voirMesCommandes(Model model, Authentication authentication) {
        String username = authentication.getName();
        List<Commande> mesCommandes = commandeService.commandesParUtilisateur(username);

        model.addAttribute("commandes", mesCommandes);
        return "user/commandes/list"; // Crée ce fichier HTML
    }
}
