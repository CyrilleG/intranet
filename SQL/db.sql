-- phpMyAdmin SQL Dump
-- version 3.4.10.1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Jeu 07 Juin 2012 à 11:17
-- Version du serveur: 5.5.20
-- Version de PHP: 5.3.10

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données: `intranet`
--

-- --------------------------------------------------------

--
-- Structure de la table `action`
--

CREATE TABLE IF NOT EXISTS `action` (
  `idaction` int(11) NOT NULL AUTO_INCREMENT,
  `idmodule` int(11) NOT NULL,
  `method` varchar(100) NOT NULL,
  `template` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idaction`),
  KEY `action_module` (`idmodule`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `fields`
--

CREATE TABLE IF NOT EXISTS `fields` (
  `idfields` int(11) NOT NULL AUTO_INCREMENT,
  `idobject` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `value` text,
  PRIMARY KEY (`idfields`),
  KEY `fields_has_object` (`idobject`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Structure de la table `filter`
--

CREATE TABLE IF NOT EXISTS `filter` (
  `idfilter` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` text,
  `class` varchar(75) DEFAULT NULL,
  PRIMARY KEY (`idfilter`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `group`
--

CREATE TABLE IF NOT EXISTS `group` (
  `idgroup` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`idgroup`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `group_filter`
--

CREATE TABLE IF NOT EXISTS `group_filter` (
  `idgroup_filter` int(11) NOT NULL,
  `idgroup` int(11) NOT NULL,
  `idfilter` int(11) NOT NULL,
  PRIMARY KEY (`idgroup_filter`),
  KEY `group_filters_filter` (`idfilter`),
  KEY `group_filters_group` (`idgroup`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `group_rights`
--

CREATE TABLE IF NOT EXISTS `group_rights` (
  `idgroup_rights` int(11) NOT NULL AUTO_INCREMENT,
  `idgroup` int(11) NOT NULL,
  `idright` int(11) NOT NULL,
  PRIMARY KEY (`idgroup_rights`),
  KEY `group_rights_group` (`idgroup`),
  KEY `group_rights_right` (`idright`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `module`
--

CREATE TABLE IF NOT EXISTS `module` (
  `idmodule` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `description` text,
  `class` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idmodule`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `module_access`
--

CREATE TABLE IF NOT EXISTS `module_access` (
  `idmodule_access` int(11) NOT NULL AUTO_INCREMENT,
  `idmodule` int(11) NOT NULL,
  `idgroup` int(11) NOT NULL,
  PRIMARY KEY (`idmodule_access`),
  KEY `module_access_module` (`idmodule`),
  KEY `module_access_group` (`idgroup`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `objects`
--

CREATE TABLE IF NOT EXISTS `objects` (
  `idobject` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`idobject`),
  KEY `param_user` (`iduser`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

-- --------------------------------------------------------

--
-- Structure de la table `privacity`
--

CREATE TABLE IF NOT EXISTS `privacity` (
  `idprivacity` int(11) NOT NULL AUTO_INCREMENT,
  `idinfo` int(11) NOT NULL,
  `idgroup` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`idprivacity`),
  KEY `group` (`idgroup`),
  KEY `info` (`idinfo`),
  KEY `user` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `right`
--

CREATE TABLE IF NOT EXISTS `right` (
  `idright` int(11) NOT NULL AUTO_INCREMENT,
  `ident` varchar(70) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text,
  PRIMARY KEY (`idright`),
  UNIQUE KEY `ident_UNIQUE` (`ident`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `session`
--

CREATE TABLE IF NOT EXISTS `session` (
  `idsession` varchar(100) NOT NULL,
  `iduser` int(11) DEFAULT NULL,
  `login_date` date DEFAULT NULL,
  `last_action` date DEFAULT NULL,
  PRIMARY KEY (`idsession`),
  KEY `session_user` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

-- --------------------------------------------------------

--
-- Structure de la table `user_filters`
--

CREATE TABLE IF NOT EXISTS `user_filters` (
  `iduser_filters` int(11) NOT NULL AUTO_INCREMENT,
  `iduser` int(11) NOT NULL,
  `idfilter` int(11) NOT NULL,
  PRIMARY KEY (`iduser_filters`),
  KEY `user_filter_user` (`iduser`),
  KEY `user_filter_filter` (`idfilter`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `user_groups`
--

CREATE TABLE IF NOT EXISTS `user_groups` (
  `iduser_groups` int(11) NOT NULL AUTO_INCREMENT,
  `iduser` int(11) NOT NULL,
  `idgroup` int(11) NOT NULL,
  PRIMARY KEY (`iduser_groups`),
  KEY `iduser` (`iduser`),
  KEY `idgroup` (`idgroup`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `user_info`
--

CREATE TABLE IF NOT EXISTS `user_info` (
  `idinfo` int(11) NOT NULL AUTO_INCREMENT,
  `key` varchar(100) NOT NULL,
  `value` text,
  `iduser` int(11) NOT NULL,
  PRIMARY KEY (`idinfo`),
  UNIQUE KEY `key_UNIQUE` (`key`),
  KEY `user_id` (`iduser`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `user_rights`
--

CREATE TABLE IF NOT EXISTS `user_rights` (
  `iduser_rights` int(11) NOT NULL,
  `iduser` int(11) DEFAULT NULL,
  `idright` int(11) DEFAULT NULL,
  PRIMARY KEY (`iduser_rights`),
  KEY `user_rights_iduser` (`iduser`),
  KEY `user_rights_idright` (`idright`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `action`
--
ALTER TABLE `action`
  ADD CONSTRAINT `action_module` FOREIGN KEY (`idmodule`) REFERENCES `module` (`idmodule`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `fields`
--
ALTER TABLE `fields`
  ADD CONSTRAINT `fields_has_object` FOREIGN KEY (`idobject`) REFERENCES `objects` (`idobject`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `group_filter`
--
ALTER TABLE `group_filter`
  ADD CONSTRAINT `group_filters_filter` FOREIGN KEY (`idfilter`) REFERENCES `filter` (`idfilter`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `group_filters_group` FOREIGN KEY (`idgroup`) REFERENCES `group` (`idgroup`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `group_rights`
--
ALTER TABLE `group_rights`
  ADD CONSTRAINT `group_rights_group` FOREIGN KEY (`idgroup`) REFERENCES `group` (`idgroup`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `group_rights_right` FOREIGN KEY (`idright`) REFERENCES `right` (`idright`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `module_access`
--
ALTER TABLE `module_access`
  ADD CONSTRAINT `module_access_group` FOREIGN KEY (`idgroup`) REFERENCES `group` (`idgroup`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `module_access_module` FOREIGN KEY (`idmodule`) REFERENCES `module` (`idmodule`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `objects`
--
ALTER TABLE `objects`
  ADD CONSTRAINT `param_user` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `privacity`
--
ALTER TABLE `privacity`
  ADD CONSTRAINT `group` FOREIGN KEY (`idgroup`) REFERENCES `group` (`idgroup`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `info` FOREIGN KEY (`idinfo`) REFERENCES `user_info` (`idinfo`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `session`
--
ALTER TABLE `session`
  ADD CONSTRAINT `session_user` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `user_filters`
--
ALTER TABLE `user_filters`
  ADD CONSTRAINT `user_filter_filter` FOREIGN KEY (`idfilter`) REFERENCES `filter` (`idfilter`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_filter_user` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `user_groups`
--
ALTER TABLE `user_groups`
  ADD CONSTRAINT `idgroup` FOREIGN KEY (`idgroup`) REFERENCES `group` (`idgroup`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `iduser` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `user_info`
--
ALTER TABLE `user_info`
  ADD CONSTRAINT `user_id` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Contraintes pour la table `user_rights`
--
ALTER TABLE `user_rights`
  ADD CONSTRAINT `user_rights_idright` FOREIGN KEY (`idright`) REFERENCES `right` (`idright`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_rights_iduser` FOREIGN KEY (`iduser`) REFERENCES `user` (`iduser`) ON DELETE CASCADE ON UPDATE CASCADE;
