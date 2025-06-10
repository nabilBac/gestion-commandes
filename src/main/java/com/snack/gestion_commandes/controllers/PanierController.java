package com.snack.gestion_commandes.controllers;

import com.snack.gestion_commandes.models.LignePanier;
import com.snack.gestion_commandes.models.Produit;
import com.snack.gestion_commandes.services.ProduitService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@SessionAttributes("panier")
@Controller
@RequestMapping("/panier")
public class PanierController {

    private final ProduitService produitService;

    public PanierController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @ModelAttribute("panier")
    public List<LignePanier> initPanier() {
        return new ArrayList<>();
    }

    @PostMapping("/ajouter")
    public String ajouterProduit(@RequestParam Long produitId,
                                 @RequestParam(defaultValue = "1") int quantite,
                                 @ModelAttribute("panier") List<LignePanier> panier) {
        Produit produit = produitService.findById(produitId);
        if (produit != null) {
            // Vérifier si le produit est déjà dans le panier
            boolean trouve = false;
            for (LignePanier ligne : panier) {
                if (ligne.getProduit().getId().equals(produitId)) {
                    ligne.setQuantite(ligne.getQuantite() + quantite);
                    // Pas besoin de setTotal, car getTotal() est calculé
                    trouve = true;
                    break;
                }
            }
            if (!trouve) {
                // Le total de LignePanier sera calculé via getTotal()
                panier.add(new LignePanier(produit, quantite));
            }
        }
        return "redirect:/panier";
    }

    @GetMapping
    public String voirPanier(@ModelAttribute("panier") List<LignePanier> panier, Model model) {
        // Calculer le total général ici
        // Utiliser LignePanier::getTotal pour obtenir le total de chaque ligne
        double totalGeneral = panier.stream()
                .mapToDouble(LignePanier::getTotal)
                .sum();

        model.addAttribute("panier", panier);
        model.addAttribute("totalGeneral", totalGeneral); // Ajouter le total général au modèle
        return "user/panier";
    }

    @PostMapping("/modifier")
    public String modifierQuantite(@RequestParam int index,
                                   @RequestParam int quantite,
                                   HttpSession session) {

        List<LignePanier> panier = (List<LignePanier>) session.getAttribute("panier");

        if (panier != null && index >= 0 && index < panier.size()) {
            if (quantite > 0) {
                panier.get(index).setQuantite(quantite);
            } else {
                panier.remove(index); // si quantité <= 0, on supprime la ligne
            }
            session.setAttribute("panier", panier); // mise à jour dans la session
        }

        return "redirect:/panier";
    }


    @PostMapping("/supprimer")
    public String supprimerProduit(@RequestParam int index,
                                   @ModelAttribute("panier") List<LignePanier> panier) {
        if (index >= 0 && index < panier.size()) {
            panier.remove(index);
        }
        return "redirect:/panier";
    }


}