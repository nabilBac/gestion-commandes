package com.snack.gestion_commandes.controllers.admin;

import com.snack.gestion_commandes.models.Produit;
import com.snack.gestion_commandes.models.TypeProduit;
import com.snack.gestion_commandes.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;



@Controller
@RequestMapping("/admin/produits")
@SessionAttributes("produit")
public class AdminProduitController {

    @Autowired
    private ProduitRepository produitRepository;

    @GetMapping("")
    public String listeProduits(Model model) {
        List<Produit> tousLesProduits = produitRepository.findAll();

        model.addAttribute("burgers", tousLesProduits.stream().filter(p -> p.getType() == TypeProduit.BURGER).toList());
        model.addAttribute("boissons", tousLesProduits.stream().filter(p -> p.getType() == TypeProduit.BOISSON).toList());
        model.addAttribute("menus", tousLesProduits.stream().filter(p -> p.getType() == TypeProduit.MENU).toList());
        model.addAttribute("sandwichs", tousLesProduits.stream().filter(p -> p.getType() == TypeProduit.SANDWICH).toList());
        model.addAttribute("desserts", tousLesProduits.stream().filter(p -> p.getType() == TypeProduit.DESSERT).toList());

        return "admin/produits/list"; // nom du fichier HTML
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("produit", new Produit());
        model.addAttribute("types", TypeProduit.values());
        return "admin/produits/create";
    }

    @PostMapping("/create")
    public String createProduit(@Valid @ModelAttribute Produit produit,
                                BindingResult result,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Model model) {
        if (imageFile.isEmpty()) {
            result.rejectValue("imageUrl", "NotBlank", "L'image est obligatoire");
        }

        if (result.hasErrors()) {
            model.addAttribute("types", TypeProduit.values());
            return "admin/produits/create";
        }

        try {
            String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path uploadPath = Paths.get(System.getProperty("user.dir") + "/uploads");
            Files.createDirectories(uploadPath);
            Files.copy(imageFile.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            produit.setImageUrl("/uploads/" + fileName);  // chemin relatif pour affichage dans Thymeleaf
        } catch (IOException e) {
            e.printStackTrace();
            model.addAttribute("error", "Erreur lors de l’envoi de l’image.");
            model.addAttribute("types", TypeProduit.values());
            return "admin/produits/create";
        }

        produitRepository.save(produit);
        return "redirect:/admin/produits";
    }




    @GetMapping("/edit/{id}")
    public String editProduit(@PathVariable Long id, Model model) {
        Produit produit = produitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));
        model.addAttribute("produit", produit);
        model.addAttribute("types", TypeProduit.values());
        return "admin/produits/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateProduit(@PathVariable Long id,
                                @Valid @ModelAttribute Produit produit,
                                BindingResult result,
                                @RequestParam("imageFile") MultipartFile imageFile,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("types", TypeProduit.values());
            return "admin/produits/edit";
        }

        if (!imageFile.isEmpty()) {
            try {
                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path uploadPath = Paths.get(System.getProperty("user.dir") + "/uploads");
                Files.createDirectories(uploadPath);
                Files.copy(imageFile.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                produit.setImageUrl("/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("error", "Erreur lors de l’envoi de l’image.");
                model.addAttribute("types", TypeProduit.values());
                return "admin/produits/edit";
            }
        } else {
            // conserver l'image existante si pas de nouveau fichier
            Produit produitExistant = produitRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));
            produit.setImageUrl(produitExistant.getImageUrl());
        }

        produit.setId(id);
        produitRepository.save(produit);
        return "redirect:/admin/produits";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduit(@PathVariable Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable avec l'id: " + id));
        produitRepository.delete(produit);
        return "redirect:/admin/produits";
    }


}
