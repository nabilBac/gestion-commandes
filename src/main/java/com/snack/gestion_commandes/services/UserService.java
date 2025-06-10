// src/main/java/com/snack/gestion_commandes/services/UserService.java
package com.snack.gestion_commandes.services;

import com.snack.gestion_commandes.models.User;
import com.snack.gestion_commandes.models.Role;
import com.snack.gestion_commandes.repositories.UserRepository;
import com.snack.gestion_commandes.repositories.RoleRepository;
import com.snack.gestion_commandes.dto.RegistrationRequest; // IMPORTANT : Nouvelle importation pour le DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // IMPORTANT : Nouvelle importation pour @Transactional

import java.util.Set;
import java.util.Optional; // IMPORTANT : Nouvelle importation pour Optional

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional // S'assure que toutes les opérations (recherche, insertion) sont atomiques
    public User registerNewUser(RegistrationRequest registrationRequest) throws Exception {
        // 1. Vérifier si l'email existe déjà
        Optional<User> existingUserByEmail = userRepository.findByEmail(registrationRequest.getEmail());
        if (existingUserByEmail.isPresent()) {
            throw new Exception("L'email '" + registrationRequest.getEmail() + "' est déjà utilisé.");
        }

        // 2. Vérifier si le nom d'utilisateur existe déjà
        Optional<User> existingUserByUsername = userRepository.findByUsername(registrationRequest.getUsername());
        if (existingUserByUsername.isPresent()) {
            throw new Exception("Le nom d'utilisateur '" + registrationRequest.getUsername() + "' est déjà pris.");
        }

        // 3. Vérifier que les mots de passe correspondent
        if (!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())) {
            throw new Exception("Les mots de passe ne correspondent pas.");
        }

        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        // Assigner le rôle par défaut (ex: ROLE_USER)
        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleRepository.save(userRole); // Sauvegarde le rôle si il n'existe pas
        }
        user.setRoles(Set.of(userRole));

        return userRepository.save(user); // Retourne l'utilisateur sauvegardé
    }


}