package com.snack.gestion_commandes.models;

import jakarta.persistence.*;


import jakarta.validation.constraints.*;

@Entity
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom du produit est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String nom;
    @NotBlank(message = "La description est obligatoire")
    @Size(min = 5, max = 255, message = "La description doit contenir entre 5 et 255 caractères")
    private String description;
    @DecimalMin(value = "0.1", inclusive = true, message = "Le prix doit être supérieur à 0")
    private double prix;

    @NotNull(message = "Le type de produit est obligatoire")
    @Enumerated(EnumType.STRING)
    private TypeProduit type;
    //@NotBlank(message = "L'URL de l'image est obligatoire")
    @Column(name = "image_url")  // <-- Ajoute ça ici
    private String imageUrl;

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public TypeProduit getType() {
        return type;
    }

    public void setType(TypeProduit type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
