package com.snack.gestion_commandes.controllers;

import com.snack.gestion_commandes.models.Produit;
import com.snack.gestion_commandes.models.TypeProduit;
import com.snack.gestion_commandes.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class ProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("/burgers")
    public String getBurgers(Model model) {
        model.addAttribute("produits", produitRepository.findByType(TypeProduit.BURGER));
        model.addAttribute("type", "Burgers");
        return "produits/liste";
    }

    @GetMapping("/menus")
    public String getMenus(Model model) {
        model.addAttribute("produits", produitRepository.findByType(TypeProduit.MENU));
        model.addAttribute("type", "Menus");
        return "produits/liste";
    }

    @GetMapping("/boissons")
    public String getBoissons(Model model) {
        model.addAttribute("produits", produitRepository.findByType(TypeProduit.BOISSON));
        model.addAttribute("type", "Boissons");
        return "produits/liste";
    }

    @GetMapping("/desserts")
    public String getDesserts(Model model) {
        model.addAttribute("produits", produitRepository.findByType(TypeProduit.DESSERT));
        model.addAttribute("type", "Desserts");
        return "produits/liste";
    }
    @GetMapping("/sandwichs")
    public String getSandwichs(Model model) {
        model.addAttribute("produits", produitRepository.findByType(TypeProduit.SANDWICH));
        model.addAttribute("type", "Sandwichs");
        return "produits/liste";
    }
}
