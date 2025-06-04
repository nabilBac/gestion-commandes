package com.snack.gestion_commandes.repositories;


import com.snack.gestion_commandes.models.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {}