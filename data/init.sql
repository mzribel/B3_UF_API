-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 09 mai 2025 à 22:57
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `breeders_app`
--

DROP DATABASE IF EXISTS `breeders_app`;
CREATE DATABASE IF NOT EXISTS `breeders_app`;
USE `breeders_app`;

-- --------------------------------------------------------

--
-- Structure de la table `addresses`
--

CREATE TABLE `addresses` (
  `id` int(11) NOT NULL,
  `street` varchar(255) DEFAULT NULL,
  `complement` varchar(255) DEFAULT NULL,
  `postal_code` varchar(12) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `addresses`
--

INSERT INTO `addresses` (`id`, `street`, `complement`, `postal_code`, `city`, `country`) VALUES
(1, 'Rue de la République', 'Résidence les Pélicans bât. B', '13130', 'Berre l\'Etang', NULL),
(2, '35 Lotissement le Pré Benoît', NULL, '39310', 'Lamoura', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `breeders`
--

CREATE TABLE `breeders` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `siret` varchar(255) DEFAULT NULL,
  `affix` varchar(255) NOT NULL,
  `is_affix_prefix` tinyint(1) NOT NULL DEFAULT 0,
  `owner_id` int(11) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `created_by_cattery_id` int(11) DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `is_derogatory` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `breeders`
--

INSERT INTO `breeders` (`id`, `name`, `siret`, `affix`, `is_affix_prefix`, `owner_id`, `address_id`, `created_by_cattery_id`, `is_active`, `is_derogatory`) VALUES
(1, 'Chatterie RathGate', NULL, 'RATHGATE\'S', 1, NULL, NULL, 1, 1, 0),
(2, 'Chatterie de Man Douss Kahz', '82823478100015', 'DE MAN DOUSS KAHZ', 0, 2, 2, 1, 1, 0),
(3, NULL, NULL, 'DES HUIT CHEMINS', 0, NULL, NULL, NULL, 1, 0),
(4, NULL, NULL, 'DE LA TERRE DE BRASCO', 0, NULL, NULL, NULL, 1, 0);

-- --------------------------------------------------------

--
-- Structure de la table `breeds`
--

CREATE TABLE `breeds` (
  `id` int(11) NOT NULL,
  `code` varchar(8) NOT NULL,
  `name` varchar(255) NOT NULL,
  `details` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `breeds`
--

INSERT INTO `breeds` (`id`, `code`, `name`, `details`) VALUES
(1, 'MCO', 'Maine Coon', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `cats`
--

DROP TABLE IF EXISTS `cats`;

CREATE TABLE `cats` (
    `id` INT(11) NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `surname` VARCHAR(255) DEFAULT NULL,
    `sex` TINYINT(1) NOT NULL DEFAULT 0,
    `birth_date` DATE DEFAULT NULL,
    `litter_id` INT(11) DEFAULT NULL,
    `origin_breeder_id` INT(11) DEFAULT NULL,
    `current_breeder_id` INT(11) DEFAULT NULL,
    `breed_id` INT(11) DEFAULT NULL,
    `eye_color_id` INT(11) DEFAULT NULL,
    `poly_type_id` INT(11) DEFAULT NULL,
    `pedigree_no` VARCHAR(255) DEFAULT NULL,
    `identification_no` VARCHAR(255) DEFAULT NULL,
    `is_neutered` TINYINT(1) NOT NULL DEFAULT 0,
    `neutered_date` DATE DEFAULT NULL,
    `is_deceased` TINYINT(1) NOT NULL DEFAULT 0,
    `deceased_date` DATE DEFAULT NULL,
    `created_by_cattery_id` INT(11) NOT NULL,
    `is_in_cattery` TINYINT(1) NOT NULL DEFAULT 1,
    `notes` TEXT DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


--
-- Déchargement des données de la table `cats`
--

INSERT INTO `cats`
(`id`, `name`, `surname`, `sex`, `birth_date`, `origin_breeder_id`, `current_breeder_id`, `breed_id`,
 `poly_type_id`, `eye_color_id`, `litter_id`, `pedigree_no`, `identification_no`, `is_neutered`,
 `neutered_date`, `is_deceased`, `deceased_date`, `created_by_cattery_id`, `is_in_cattery`, `notes`)
VALUES
    (1, 'Serkhan', 'Ptit Loup', 0, '2021-07-05', 3, 2, 1, 1, NULL, NULL, 'LOOF 2021.40533', '250269590612101', 0, NULL, 0, NULL, 1, 1, NULL),
    (2, 'Salemm', NULL, 1, '2021-04-13', 4, 2, 1, 2, NULL, NULL, 'LOOF 2021.28950', '250268502119223', 0, NULL, 0, NULL, 1, 1, NULL),
    (3, 'Scarlett', NULL, 1, '2021-04-07', 4, NULL, 1, NULL, NULL, NULL,'LOOF 2021.27416', '250268502119253', 1, NULL, 0, NULL, 1, 1, NULL),
    (4, 'Uranus', 'Bingus', 0, '2023-02-20', 2, 1, 1, NULL, NULL, NULL, 'LOOF 2023.14148', '250268780001262', 1, NULL, 0, NULL, 1, 1, NULL),
    (5, 'Teba', NULL, 0, NULL, 2, 1, 1, 1, NULL, NULL, 'LOOF 2022.51603', '2503268780001287', 1, NULL, 0, NULL, 1, 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `catteries`
--

CREATE TABLE `catteries` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `breeder_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `catteries`
--

INSERT INTO `catteries` (`id`, `user_id`, `breeder_id`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `cattery_users`
--

CREATE TABLE `cattery_users` (
  `cattery_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `cat_coats`
--

CREATE TABLE `cat_coats` (
  `cat_id` int(11) NOT NULL,
  `base_color_id` int(11) NOT NULL,
  `pattern_id` int(11) DEFAULT NULL,
  `effect_id` int(11) DEFAULT NULL,
  `white_marking_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `cat_coats`
--

INSERT INTO `cat_coats` (`cat_id`, `base_color_id`, `pattern_id`, `effect_id`, `white_marking_id`) VALUES
(1, 4, NULL, NULL, NULL),
(2, 1, NULL, NULL, NULL),
(3, 16, 8, NULL, NULL),
(4, 1, NULL, NULL, NULL),
(5, 5, 8, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `coat_base_colors`
--

CREATE TABLE `coat_base_colors` (
  `id` int(11) NOT NULL,
  `code` varchar(8) NOT NULL,
  `name` varchar(255) NOT NULL,
  `details` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `coat_base_colors`
--

INSERT INTO `coat_base_colors` (`id`, `code`, `name`, `details`) VALUES
(1, 'n', 'noir', NULL),
(2, 'b', 'chocolat', NULL),
(3, 'o', 'cinnamon', NULL),
(4, 'd', 'roux', NULL),
(5, 'a', 'bleu', NULL),
(6, 'c', 'lilas', NULL),
(7, 'p', 'fawn', NULL),
(8, 'e', 'crème', NULL),
(9, 'f', 'black tortie', NULL),
(10, 'h', 'chocolat tortie', NULL),
(11, 'q', 'cinnamon tortie', NULL),
(12, 'w', 'blanc', NULL),
(13, 'g', 'blue tortie', NULL),
(14, 'j', 'lilac tortie', NULL),
(15, 'r', 'fawn tortie', NULL),
(16, 'n', 'brown', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `coat_effects`
--

CREATE TABLE `coat_effects` (
  `id` int(11) NOT NULL,
  `code` varchar(8) NOT NULL,
  `name` varchar(255) NOT NULL,
  `details` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `coat_effects`
--

INSERT INTO `coat_effects` (`id`, `code`, `name`, `details`) VALUES
(1, 's', 'silver', NULL),
(2, 's', 'smoke', NULL),
(3, 'u', 'copper', NULL),
(4, 'u', 'sunshine', NULL),
(5, 'y', 'golden', NULL),
(6, 'z', 'carnelian', NULL),
(7, 'z', 'copal', NULL),
(8, '14', 'charcoal', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `coat_patterns`
--

CREATE TABLE `coat_patterns` (
  `id` int(11) NOT NULL,
  `code` varchar(8) NOT NULL,
  `name` varchar(255) NOT NULL,
  `details` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `coat_patterns`
--

INSERT INTO `coat_patterns` (`id`, `code`, `name`, `details`) VALUES
(1, '11', 'shaded', NULL),
(2, '12', 'shell', NULL),
(3, '12', 'chinchilla', NULL),
(4, '21', 'tabby', '(sans précision de motif)'),
(5, '22', 'blotched tabby', NULL),
(6, '23', 'mackerel tabby', NULL),
(7, '24', 'spotted tabby', NULL),
(8, '25', 'ticked tabby', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `coat_white_markings`
--

CREATE TABLE `coat_white_markings` (
  `id` int(11) NOT NULL,
  `code` varchar(8) NOT NULL,
  `name` varchar(255) NOT NULL,
  `details` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `coat_white_markings`
--

INSERT INTO `coat_white_markings` (`id`, `code`, `name`, `details`) VALUES
(1, '01', '& blanc van', NULL),
(2, '02', '& blanc arlequin', NULL),
(3, '03', '& blanc', '(pour les bicolores)'),
(4, '04', 'mitted', NULL),
(5, '05', 'snowshoe', NULL),
(6, '06', 'loquet', NULL),
(7, '09', '& blanc', '(sans indication de quantité de blanc)');

-- --------------------------------------------------------

--
-- Structure de la table `eye_colors`
--

CREATE TABLE `eye_colors` (
  `id` int(11) NOT NULL,
  `code` varchar(8) NOT NULL,
  `name` varchar(255) NOT NULL,
  `details` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `eye_colors`
--

INSERT INTO `eye_colors` (`id`, `code`, `name`, `details`) VALUES
(1, '61', 'yeux bleus', NULL),
(2, '62', 'yeux or', NULL),
(3, '63', 'yeux impairs', NULL),
(4, '64', 'yeux verts', NULL),
(5, '66', 'yeux aigue-marine', NULL),
(6, '68', 'yeux bleus ou impairs/locket', NULL),
(7, '69', 'yeux or à vert/locket', '(latents issus de 68)');

-- --------------------------------------------------------

--
-- Structure de la table `gestations`
--

CREATE TABLE `gestations` (
  `id` int(11) NOT NULL,
  `mating_id` int(11) DEFAULT NULL,
  `dam_id` int(11) NOT NULL,
  `sire_id` int(11) DEFAULT NULL,
  `estimated_start_date` date NOT NULL,
  `estimated_due_date` date DEFAULT NULL,
  `confirmation_date` date DEFAULT NULL,
  `litter_id` int(11) DEFAULT NULL,
  `notes` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `gestation_health_logs`
--

CREATE TABLE `gestation_health_logs` (
  `id` int(11) NOT NULL,
  `health_log_id` int(11) NOT NULL,
  `mammary_observations` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `health_logs`
--

CREATE TABLE `health_logs` (
  `id` int(11) NOT NULL,
  `cat_id` int(11) NOT NULL,
  `weight_in_grams` decimal(10,0) DEFAULT NULL,
  `temperature_in_celsius` decimal(10,0) DEFAULT NULL,
  `appetite` varchar(255) DEFAULT NULL,
  `hydratation` varchar(255) DEFAULT NULL,
  `behavior` varchar(255) DEFAULT NULL,
  `stool_quality` varchar(255) DEFAULT NULL,
  `urine_observations` varchar(255) DEFAULT NULL,
  `notes` text DEFAULT NULL,
  `date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `heats`
--

CREATE TABLE `heats` (
  `id` int(11) NOT NULL,
  `dam_id` int(11) NOT NULL,
  `start_date` datetime NOT NULL DEFAULT current_timestamp(),
  `end_date` datetime DEFAULT NULL,
  `notes` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `kitten_health_logs`
--

CREATE TABLE `kitten_health_logs` (
  `id` int(11) NOT NULL,
  `health_log_id` int(11) NOT NULL,
  `open_eyes_date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `litters`
--

CREATE TABLE `litters` (
  `id` int(11) NOT NULL,
  `dam_id` int(11) DEFAULT NULL,
  `sire_id` int(11) DEFAULT NULL,
  `origin_breeder_id` int(11) DEFAULT NULL,
  `date_of_birth` datetime DEFAULT NULL,
  `loof_identification_number` varchar(255) DEFAULT NULL,
  `loof_declaration_date` date DEFAULT NULL,
  `kitten_count` int(11) DEFAULT NULL,
  `notes` text DEFAULT NULL,
  `created_by_cattery_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `litters`
--

INSERT INTO `litters` (`id`, `dam_id`, `sire_id`, `origin_breeder_id`, `date_of_birth`, `loof_identification_number`, `loof_declaration_date`, `kitten_count`, `notes`, `created_by_cattery_id`) VALUES
(1, 3, 1, 2, '2022-08-19 00:00:00', NULL, NULL, 4, '1 mâle MCO n 25 p\r\n1 mâle MCO a 25 P\r\n1 femelle MCO f 25 P\r\n1 femelle MCO g 25', 1);

-- --------------------------------------------------------

--
-- Structure de la table `matings`
--

CREATE TABLE `matings` (
  `id` int(11) NOT NULL,
  `dam_id` int(11) NOT NULL,
  `sire_id` int(11) NOT NULL,
  `heat_id` int(11) DEFAULT NULL,
  `start_date` datetime NOT NULL DEFAULT current_timestamp(),
  `end_date` datetime DEFAULT NULL,
  `heat_stopped` tinyint(1) DEFAULT NULL,
  `is_planned` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `persons`
--

CREATE TABLE `persons` (
  `id` int(11) NOT NULL,
  `sex` tinyint(1) DEFAULT NULL,
  `is_legal_person` tinyint(1) NOT NULL DEFAULT 0,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `address_id` int(11) DEFAULT NULL,
  `notes` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `persons`
--

INSERT INTO `persons` (`id`, `sex`, `is_legal_person`, `first_name`, `last_name`, `date_of_birth`, `email`, `phone_number`, `address_id`, `notes`) VALUES
(1, 1, 0, 'Marianne', 'Corbel', '1999-05-30', 'chatterie@rathgate.fr', '0645228103', 1, NULL),
(2, 1, 0, 'Sylvaine', 'Simon', '1976-05-26', 'sylvaine.simon35@gmail.com', NULL, 2, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `poly_types`
--

CREATE TABLE `poly_types` (
  `id` int(11) NOT NULL,
  `code` varchar(8) NOT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `poly_types`
--

INSERT INTO `poly_types` (`id`, `code`, `name`) VALUES
(1, 'P', 'polydactyle pattes avant'),
(2, 'PP', 'polydactyle 4 pattes');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `display_name` varchar(255) DEFAULT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `email`, `password`, `display_name`, `is_admin`) VALUES
(1, 'chatterie@rathgate.fr', '', 'Chatterie RathGate', 0),
(2, 'admin@rathgate.fr', '', 'RathGate', 1);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `breeders`
--
ALTER TABLE `breeders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `address_id` (`address_id`),
  ADD KEY `created_by_cattery_id` (`created_by_cattery_id`);

--
-- Index pour la table `breeds`
--
ALTER TABLE `breeds`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `cats`
--
ALTER TABLE `cats`
  ADD KEY `origin_breeder_id` (`origin_breeder_id`),
  ADD KEY `current_breeder_id` (`current_breeder_id`),
  ADD KEY `breed_id` (`breed_id`),
  ADD KEY `poly_type_id` (`poly_type_id`),
  ADD KEY `eye_color_id` (`eye_color_id`),
  ADD KEY `created_by_cattery_id` (`created_by_cattery_id`),
  ADD KEY `fk_cats_litter_id` (`litter_id`);

--
-- Index pour la table `catteries`
--
ALTER TABLE `catteries`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `fk_catteries_breeder_id` (`breeder_id`);

--
-- Index pour la table `cattery_users`
--
ALTER TABLE `cattery_users`
  ADD PRIMARY KEY (`cattery_id`,`user_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Index pour la table `cat_coats`
--
ALTER TABLE `cat_coats`
  ADD PRIMARY KEY (`cat_id`),
  ADD KEY `base_color_id` (`base_color_id`),
  ADD KEY `pattern_id` (`pattern_id`),
  ADD KEY `effect_id` (`effect_id`),
  ADD KEY `white_marking_id` (`white_marking_id`);

--
-- Index pour la table `coat_base_colors`
--
ALTER TABLE `coat_base_colors`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `coat_effects`
--
ALTER TABLE `coat_effects`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `coat_patterns`
--
ALTER TABLE `coat_patterns`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `coat_white_markings`
--
ALTER TABLE `coat_white_markings`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `eye_colors`
--
ALTER TABLE `eye_colors`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `gestations`
--
ALTER TABLE `gestations`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mating_id` (`mating_id`),
  ADD KEY `dam_id` (`dam_id`),
  ADD KEY `sire_id` (`sire_id`),
  ADD KEY `fk_gestations_litter_id` (`litter_id`);

--
-- Index pour la table `gestation_health_logs`
--
ALTER TABLE `gestation_health_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `health_log_id` (`health_log_id`);

--
-- Index pour la table `health_logs`
--
ALTER TABLE `health_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `cat_id` (`cat_id`);

--
-- Index pour la table `heats`
--
ALTER TABLE `heats`
  ADD PRIMARY KEY (`id`),
  ADD KEY `dam_id` (`dam_id`);

--
-- Index pour la table `kitten_health_logs`
--
ALTER TABLE `kitten_health_logs`
  ADD PRIMARY KEY (`id`),
  ADD KEY `health_log_id` (`health_log_id`);

--
-- Index pour la table `litters`
--
ALTER TABLE `litters`
  ADD PRIMARY KEY (`id`),
  ADD KEY `dam_id` (`dam_id`),
  ADD KEY `sire_id` (`sire_id`),
  ADD KEY `origin_breeder_id` (`origin_breeder_id`),
  ADD KEY `created_by_cattery_id` (`created_by_cattery_id`);

--
-- Index pour la table `matings`
--
ALTER TABLE `matings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `dam_id` (`dam_id`),
  ADD KEY `sire_id` (`sire_id`),
  ADD KEY `heat_id` (`heat_id`);

--
-- Index pour la table `persons`
--
ALTER TABLE `persons`
  ADD PRIMARY KEY (`id`),
  ADD KEY `address_id` (`address_id`);

--
-- Index pour la table `poly_types`
--
ALTER TABLE `poly_types`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `addresses`
--
ALTER TABLE `addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `breeders`
--
ALTER TABLE `breeders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `breeds`
--
ALTER TABLE `breeds`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `cats`
--
ALTER TABLE `cats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `catteries`
--
ALTER TABLE `catteries`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `coat_base_colors`
--
ALTER TABLE `coat_base_colors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT pour la table `coat_effects`
--
ALTER TABLE `coat_effects`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `coat_patterns`
--
ALTER TABLE `coat_patterns`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `coat_white_markings`
--
ALTER TABLE `coat_white_markings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `eye_colors`
--
ALTER TABLE `eye_colors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT pour la table `gestations`
--
ALTER TABLE `gestations`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `gestation_health_logs`
--
ALTER TABLE `gestation_health_logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `health_logs`
--
ALTER TABLE `health_logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `heats`
--
ALTER TABLE `heats`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `kitten_health_logs`
--
ALTER TABLE `kitten_health_logs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `litters`
--
ALTER TABLE `litters`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `matings`
--
ALTER TABLE `matings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `persons`
--
ALTER TABLE `persons`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `poly_types`
--
ALTER TABLE `poly_types`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `breeders`
--
ALTER TABLE `breeders`
  ADD CONSTRAINT `breeders_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `breeders_ibfk_2` FOREIGN KEY (`created_by_cattery_id`) REFERENCES `catteries` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `cats`
--
ALTER TABLE `cats`
  ADD CONSTRAINT `cats_ibfk_1` FOREIGN KEY (`origin_breeder_id`) REFERENCES `breeders` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `cats_ibfk_2` FOREIGN KEY (`current_breeder_id`) REFERENCES `breeders` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `cats_ibfk_3` FOREIGN KEY (`breed_id`) REFERENCES `breeds` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `cats_ibfk_4` FOREIGN KEY (`poly_type_id`) REFERENCES `poly_types` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `cats_ibfk_5` FOREIGN KEY (`eye_color_id`) REFERENCES `eye_colors` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `cats_ibfk_8` FOREIGN KEY (`created_by_cattery_id`) REFERENCES `catteries` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_cats_litter_id` FOREIGN KEY (`litter_id`) REFERENCES `litters` (`id`) ON DELETE SET NULL;

--
-- Contraintes pour la table `catteries`
--
ALTER TABLE `catteries`
  ADD CONSTRAINT `catteries_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `fk_catteries_breeder_id` FOREIGN KEY (`breeder_id`) REFERENCES `breeders` (`id`) ON DELETE SET NULL;

--
-- Contraintes pour la table `cattery_users`
--
ALTER TABLE `cattery_users`
  ADD CONSTRAINT `cattery_users_ibfk_1` FOREIGN KEY (`cattery_id`) REFERENCES `catteries` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cattery_users_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `cat_coats`
--
ALTER TABLE `cat_coats`
  ADD CONSTRAINT `cat_coats_ibfk_1` FOREIGN KEY (`base_color_id`) REFERENCES `coat_base_colors` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `cat_coats_ibfk_2` FOREIGN KEY (`pattern_id`) REFERENCES `coat_patterns` (`id`),
  ADD CONSTRAINT `cat_coats_ibfk_3` FOREIGN KEY (`effect_id`) REFERENCES `coat_effects` (`id`),
  ADD CONSTRAINT `cat_coats_ibfk_4` FOREIGN KEY (`white_marking_id`) REFERENCES `coat_white_markings` (`id`),
  ADD CONSTRAINT `fk_coats_breeder_id` FOREIGN KEY (`cat_id`) REFERENCES `cats` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `gestations`
--
ALTER TABLE `gestations`
  ADD CONSTRAINT `fk_gestations_litter_id` FOREIGN KEY (`litter_id`) REFERENCES `litters` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `gestations_ibfk_1` FOREIGN KEY (`mating_id`) REFERENCES `matings` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `gestations_ibfk_2` FOREIGN KEY (`dam_id`) REFERENCES `cats` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `gestations_ibfk_3` FOREIGN KEY (`sire_id`) REFERENCES `cats` (`id`) ON DELETE SET NULL;

--
-- Contraintes pour la table `gestation_health_logs`
--
ALTER TABLE `gestation_health_logs`
  ADD CONSTRAINT `gestation_health_logs_ibfk_1` FOREIGN KEY (`health_log_id`) REFERENCES `health_logs` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `health_logs`
--
ALTER TABLE `health_logs`
  ADD CONSTRAINT `health_logs_ibfk_1` FOREIGN KEY (`cat_id`) REFERENCES `cats` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `heats`
--
ALTER TABLE `heats`
  ADD CONSTRAINT `heats_ibfk_1` FOREIGN KEY (`dam_id`) REFERENCES `cats` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `kitten_health_logs`
--
ALTER TABLE `kitten_health_logs`
  ADD CONSTRAINT `kitten_health_logs_ibfk_1` FOREIGN KEY (`health_log_id`) REFERENCES `health_logs` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `litters`
--
ALTER TABLE `litters`
  ADD CONSTRAINT `litters_ibfk_1` FOREIGN KEY (`dam_id`) REFERENCES `cats` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `litters_ibfk_2` FOREIGN KEY (`sire_id`) REFERENCES `cats` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `litters_ibfk_3` FOREIGN KEY (`origin_breeder_id`) REFERENCES `breeders` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `litters_ibfk_4` FOREIGN KEY (`created_by_cattery_id`) REFERENCES `catteries` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `matings`
--
ALTER TABLE `matings`
  ADD CONSTRAINT `matings_ibfk_1` FOREIGN KEY (`dam_id`) REFERENCES `cats` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `matings_ibfk_2` FOREIGN KEY (`sire_id`) REFERENCES `cats` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `matings_ibfk_3` FOREIGN KEY (`heat_id`) REFERENCES `heats` (`id`) ON DELETE SET NULL;

--
-- Contraintes pour la table `persons`
--
ALTER TABLE `persons`
  ADD CONSTRAINT `persons_ibfk_1` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`id`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
