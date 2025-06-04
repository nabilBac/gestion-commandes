package com.snack.gestion_commandes.repositories;

import com.snack.gestion_commandes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    // Doit ressembler à ça pour utiliser Optional
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}