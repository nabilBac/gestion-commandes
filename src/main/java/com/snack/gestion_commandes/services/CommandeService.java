package com.snack.gestion_commandes.services;

import com.snack.gestion_commandes.dto.CommandeDTO;
import com.snack.gestion_commandes.models.*;
import com.snack.gestion_commandes.repositories.CommandeRepository;
import com.snack.gestion_commandes.repositories.LigneCommandeRepository;
import com.snack.gestion_commandes.repositories.ProduitRepository;
import com.snack.gestion_commandes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepo;
    private final LigneCommandeRepository ligneCommandeRepo;
    private final UserRepository userRepo;
    private final ProduitRepository produitRepo;

    @Autowired  // injection automatique de ce bean WebSocket
    private SimpMessagingTemplate messagingTemplate;

    public CommandeService(CommandeRepository commandeRepo,
                           LigneCommandeRepository ligneCommandeRepo,
                           UserRepository userRepo,
                           ProduitRepository produitRepo) {
        this.commandeRepo = commandeRepo;
        this.ligneCommandeRepo = ligneCommandeRepo;
        this.userRepo = userRepo;
        this.produitRepo = produitRepo;
    }



    /**
     * Valide une commande à partir du panier utilisateur.
     */
    @Transactional
    public Commande validerCommande(List<LignePanier> panier, String username) {
        if (panier == null || panier.isEmpty()) {
            throw new IllegalArgumentException("Le panier ne peut pas être vide");
        }

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé: " + username));

        Commande commande = new Commande();
        commande.setDate(LocalDateTime.now());
        commande.setDateCommande(LocalDateTime.now());
        commande.setUser(user);
        commande.setStatut(StatutCommande.EN_ATTENTE);
        commande = commandeRepo.save(commande);

        double total = 0;
        for (LignePanier lp : panier) {
            Produit produit = produitRepo.findById(lp.getProduit().getId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé: " + lp.getProduit().getId()));
            LigneCommande ligne = new LigneCommande();
            ligne.setCommande(commande);
            ligne.setProduit(produit);
            ligne.setQuantite(lp.getQuantite());
            ligne.setPrixUnitaire(produit.getPrix());
            ligne.setTotal(produit.getPrix() * lp.getQuantite());
            ligneCommandeRepo.save(ligne);
            commande.getLignes().add(ligne);
            total += ligne.getTotal();
        }

        commande.setTotal(total);
        commande = commandeRepo.save(commande);

        // Envoi du message WebSocket pour notifier l'admin d'une nouvelle commande
        messagingTemplate.convertAndSend("/topic/nouvelle-commande", new CommandeDTO(commande));

        return commande;
    }


    /**
     * Supprime une commande et ses lignes associées.
     */
    @Transactional
    public void supprimerCommande(Long id) {
        if (!commandeRepo.existsById(id)) {
            throw new RuntimeException("Commande introuvable : " + id);
        }
        commandeRepo.deleteById(id);
    }

    /**
     * Récupère toutes les commandes.
     */
    @Transactional(readOnly = true)
    public List<Commande> toutesLesCommandes() {
        return commandeRepo.findAll();
    }

    /**
     * Récupère une commande par son ID.
     */
    @Transactional(readOnly = true)
    public Commande findById(Long id) {
        return commandeRepo.findById(id).orElse(null);
    }

    /**
     * Met à jour le statut d'une commande.
     */
    @Transactional
    public void updateStatut(Long id, StatutCommande nouveauStatut) {
        Commande commande = findById(id);
        if (commande != null) {
            commande.setStatut(nouveauStatut);
            commandeRepo.save(commande);

            // Envoi du message WebSocket pour notifier les abonnés
            messagingTemplate.convertAndSend(
                    "/topic/statut-commande",
                    new StatutCommandeMessage(commande.getId(), nouveauStatut)
            );

        } else {
            throw new RuntimeException("Commande introuvable : " + id);
        }
    }

    // Classe interne pour le message WebSocket
    public static class StatutCommandeMessage {
        private Long commandeId;
        private StatutCommande statut;

        public StatutCommandeMessage(Long commandeId, StatutCommande statut) {
            this.commandeId = commandeId;
            this.statut = statut;
        }

        // getters et setters nécessaires pour la sérialisation JSON
        public Long getCommandeId() {
            return commandeId;
        }
        public void setCommandeId(Long commandeId) {
            this.commandeId = commandeId;
        }
        public StatutCommande getStatut() {
            return statut;
        }
        public void setStatut(StatutCommande statut) {
            this.statut = statut;
        }
    }

    @Transactional(readOnly = true)
    public List<Commande> commandesParUtilisateur(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé : " + username));
        return commandeRepo.findByUser(user);
    }


}
