package com.snack.gestion_commandes.repositories;

import com.snack.gestion_commandes.models.Commande;
import com.snack.gestion_commandes.models.LigneCommande;
import com.snack.gestion_commandes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByUser(User user);

}


