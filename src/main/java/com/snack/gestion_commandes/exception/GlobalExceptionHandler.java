package com.snack.gestion_commandes.exception;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception ex, Model model) {
        model.addAttribute("message", "Une erreur inattendue est survenue : " + ex.getMessage());
        return "erreur/generale";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(Exception ex, Model model) {
        model.addAttribute("message", "Accès refusé.");
        return "erreur/acces";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(Exception ex, Model model) {
        model.addAttribute("message", "Élément introuvable.");
        return "erreur/notfound";
    }
}
