package com.snack.gestion_commandes.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {

    @NotBlank(message = "L'email est requis.")
    @Email(message = "Veuillez saisir une adresse email valide.")
    private String email;

    @NotBlank(message = "Le nom d'utilisateur est requis.")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit contenir entre 3 et 50 caractères.")
    private String username;

    @NotBlank(message = "Le mot de passe est requis.")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères.")
    private String password;

    @NotBlank(message = "La confirmation du mot de passe est requise.")
    private String confirmPassword;

    // Getters et Setters (générés par votre IDE ou ajoutés manuellement)
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getConfirmPassword() { return confirmPassword; }
    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }
}
