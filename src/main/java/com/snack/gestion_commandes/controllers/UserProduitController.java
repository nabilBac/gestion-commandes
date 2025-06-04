package com.snack.gestion_commandes.controllers;
import com.snack.gestion_commandes.models.Produit;
import com.snack.gestion_commandes.models.TypeProduit;
import com.snack.gestion_commandes.services.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping("/burgers")
    public String voirBurgers(Model model) {
        List<Produit> burgers = produitService.findByType(TypeProduit.BURGER);
        model.addAttribute("produits", burgers);
        return "user/burgers";
    }

    @GetMapping("/menus")
    public String voirMenus(Model model) {
        List<Produit> menus = produitService.findByType(TypeProduit.MENU);
        model.addAttribute("produits", menus);
        return "user/menus";
    }

    @GetMapping("/desserts")
    public String voirDesserts(Model model) {
        List<Produit> desserts = produitService.findByType(TypeProduit.DESSERT);
        model.addAttribute("produits", desserts);
        return "user/desserts";
    }

    @GetMapping("/boissons")
    public String voirBoissons(Model model) {
        List<Produit> boissons = produitService.findByType(TypeProduit.BOISSON);
        model.addAttribute("produits", boissons);
        return "user/boissons";
    }
    @GetMapping("/sandwichs")
    public String voirSandwichs(Model model) {
        List<Produit> sandwichs = produitService.findByType(TypeProduit.SANDWICH);
        model.addAttribute("produits", sandwichs);
        return "user/sandwichs";
    }
}
