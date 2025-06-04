package com.snack.gestion_commandes.repositories;

import com.snack.gestion_commandes.models.Produit;
import com.snack.gestion_commandes.models.TypeProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByType(TypeProduit type);
}
