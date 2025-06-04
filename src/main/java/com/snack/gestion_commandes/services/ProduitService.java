package com.snack.gestion_commandes.services;

import com.snack.gestion_commandes.models.Produit;
import com.snack.gestion_commandes.models.TypeProduit;
import java.util.List;

public interface ProduitService {
    List<Produit> findByType(TypeProduit type);
    Produit findById(Long id);
    // autres méthodes utiles...
}
