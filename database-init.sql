-- Script de création de la base de données pour l'application de gestion de stock

-- Créer la base de données si elle n'existe pas
CREATE DATABASE IF NOT EXISTS magasin_informatique 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE magasin_informatique;

-- Les tables seront créées automatiquement par Hibernate
-- Ce fichier contient des données de test optionnelles

-- Note: Assurez-vous que MySQL est en cours d'exécution
-- Commande pour exécuter ce script:
-- mysql -u root -p < database-init.sql
