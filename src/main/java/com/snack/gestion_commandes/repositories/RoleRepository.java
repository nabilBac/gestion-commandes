package com.snack.gestion_commandes.repositories;

import com.snack.gestion_commandes.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
