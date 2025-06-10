package com.snack.gestion_commandes.controllers;

import com.snack.gestion_commandes.models.User; // Keep this import if you intend to use User objects in other methods (e.g., for profile updates)
import com.snack.gestion_commandes.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute; // REMOVE THIS LINE if no other method uses it
// import org.springframework.web.bind.annotation.PostMapping;    // REMOVE THIS LINE if no other method uses it

import java.security.Principal;


@Controller
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/user/home")
    public String userHome() {
        return "user/home";
    }

    // --- START: REMOVE THESE METHODS ---
    // Affiche le formulaire d'inscription
    // @GetMapping("/signup")
    // public String showSignupForm(Model model) {
    //     model.addAttribute("user", new User());
    //     return "signup";
    // }

    // Traite le formulaire d'inscription
    // @PostMapping("/signup")
    // public String processSignup(@ModelAttribute("user") User user) {
    //     userService.saveUser(user); // This line specifically caused the previous error
    //     return "redirect:/login";
    // }
    // --- END: REMOVE THESE METHODS ---

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // Récupérer les infos de l'utilisateur connecté
        String username = principal.getName();

        // Tu peux aussi charger d'autres données ici si tu veux.
        // Par exemple, récupérer l'objet User complet depuis le service :
        // User user = userService.findByUsername(username).orElse(null);
        // model.addAttribute("user", user);

        model.addAttribute("username", username);

        return "user/profile";
    }

}