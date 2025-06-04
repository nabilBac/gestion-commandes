package com.snack.gestion_commandes.services;

import com.snack.gestion_commandes.models.Produit;
import com.snack.gestion_commandes.models.TypeProduit;
import com.snack.gestion_commandes.repositories.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitServiceImpl implements ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    @Override
    public List<Produit> findByType(TypeProduit type) {
        return produitRepository.findByType(type);
    }

    @Override
    public Produit findById(Long id) {
        return produitRepository.findById(id).orElse(null);
    }
}
