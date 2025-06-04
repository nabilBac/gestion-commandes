package com.snack.gestion_commandes.dto;


import com.snack.gestion_commandes.models.Commande;
import com.snack.gestion_commandes.models.StatutCommande;

import java.time.LocalDateTime;

public class CommandeDTO {

    private Long id;
    private LocalDateTime date;
    private Double total;
    private StatutCommande statut;
    private String username;  // optionnel, pour afficher l’utilisateur dans l’admin

    public CommandeDTO(Commande commande) {
        this.id = commande.getId();
        this.date = commande.getDateCommande();
        this.total = commande.getTotal();
        this.statut = commande.getStatut();
        this.username = commande.getUser().getUsername();
    }

    // getters et setters ici
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public StatutCommande getStatut() { return statut; }
    public void setStatut(StatutCommande statut) { this.statut = statut; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
