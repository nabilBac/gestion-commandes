package com.snack.gestion_commandes.controllers;

import com.snack.gestion_commandes.dto.RegistrationRequest; // Importe le DTO d'inscription
import com.snack.gestion_commandes.services.UserService;    // Importe votre service utilisateur
import jakarta.validation.Valid;                           // Pour la validation des DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;       // Pour collecter les erreurs de validation
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Pour les messages flash après redirection

@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Affiche le formulaire d'inscription.
     * Initialise un objet RegistrationRequest vide pour le formulaire Thymeleaf.
     * @param model Le modèle pour ajouter des attributs à la vue.
     * @return Le nom de la vue (template HTML) pour l'inscription.
     */
    @GetMapping("/signup")
    public String showRegistrationForm(Model model) {
        // Ajoute un objet RegistrationRequest vide au modèle.
        // C'est cet objet qui sera rempli par le formulaire Thymeleaf.
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "signup"; // Renvoie vers le template Thymeleaf signup.html
    }

    /**
     * Traite la soumission du formulaire d'inscription.
     * Valide les données saisies par l'utilisateur et tente d'enregistrer le nouvel utilisateur.
     * @param registrationRequest L'objet DTO contenant les données du formulaire, validé par @Valid.
     * @param bindingResult Contient les résultats de la validation et les éventuelles erreurs.
     * @param model Le modèle pour ajouter des attributs à la vue en cas d'erreur.
     * @param redirectAttributes Pour ajouter des messages flash en cas de succès avant une redirection.
     * @return Une redirection ou le nom de la vue d'inscription en cas d'erreur.
     */
    @PostMapping("/signup")
    public String registerUser(@Valid @ModelAttribute("registrationRequest") RegistrationRequest registrationRequest,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {

        // 1. Vérification des erreurs de validation du DTO (depuis les annotations @NotBlank, @Email, @Size)
        if (bindingResult.hasErrors()) {
            // Si des erreurs de validation existent, retourne la page d'inscription
            // pour afficher ces erreurs à côté des champs concernés.
            return "signup";
        }

        // 2. Tente d'enregistrer l'utilisateur via le service.
        // Le service contient la logique de vérification d'unicité et de correspondance des mots de passe.
        try {
            userService.registerNewUser(registrationRequest);
            // Si l'inscription réussit, ajoute un message de succès "flash"
            // (visible une seule fois après la redirection) et redirige vers la page de connexion.
            redirectAttributes.addFlashAttribute("successMessage", "Votre inscription a bien été prise en compte ! Veuillez vous connecter.");
            return "redirect:/login"; // Redirection vers la page de connexion
        } catch (Exception e) {
            // 3. Gestion des exceptions levées par le UserService (email/username déjà pris, mots de passe non correspondants)
            // Nous ajoutons l'erreur spécifique au BindingResult pour qu'elle s'affiche à côté du champ concerné.
            if (e.getMessage().contains("L'email")) {
                bindingResult.rejectValue("email", "email.duplicate", e.getMessage());
            } else if (e.getMessage().contains("Le nom d'utilisateur")) {
                bindingResult.rejectValue("username", "username.duplicate", e.getMessage());
            } else if (e.getMessage().contains("Les mots de passe ne correspondent pas.")) {
                bindingResult.rejectValue("confirmPassword", "password.mismatch", e.getMessage());
            } else {
                // Pour toute autre erreur inattendue, ajoute un message d'erreur global au modèle.
                model.addAttribute("errorMessage", "Une erreur est survenue lors de l'inscription : " + e.getMessage());
            }
            // Retourne la page d'inscription pour afficher les erreurs.
            return "signup";
        }
    }
}