# Gestion Commandes

Application Java Spring Boot pour la gestion des commandes, avec gestion des utilisateurs, rôles, produits, et interface admin/utilisateur.

## 🛠 Technologies utilisées

- Java 17  
- Spring Boot  
- Maven  
- Thymeleaf  
- MySQL  
- Spring Security  
- Bootstrap  

## ✅ Fonctionnalités principales

- Authentification / Autorisation (ADMIN, USER)  
- CRUD complet des produits (burgers, menus, desserts, etc.)  
- Gestion des utilisateurs et des rôles  
- Interface utilisateur avec affichage des produits par catégorie  
- Panier et création de commandes  

## ⚙️ Installation

### Prérequis

- Java 17 installé  
- Maven installé  
- MySQL installé avec une base nommée `gestion_commandes`  

### Étapes d’installation

1. Cloner le dépôt :  
   ```bash
   git clone https://github.com/TON_UTILISATEUR/gestion-commandes.git
   cd gestion-commandes

  

2. Configurer la connexion à la base de données

Ouvre le fichier `src/main/resources/application.properties` et modifie les paramètres de connexion à ta base de données MySQL, notamment :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_commandes?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=

3. Construire et lancer l’application :

```bash
mvn clean install
mvn spring-boot:run

Accéder à l’application via :
http://localhost:8080


