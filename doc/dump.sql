-- MySQL Script generated by MySQL Workbench
-- Sat Jul 14 20:31:27 2018
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema sounddb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sounddbsound
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `sounddb` DEFAULT CHARACTER SET utf8 ;
USE `sounddb` ;

-- -----------------------------------------------------
-- Table `sounddb`.`sound`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `sounddb`.`sound` ;

CREATE TABLE IF NOT EXISTS `sounddb`.`sound` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `isFavorite` TINYINT NULL,
  `title` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;

CREATE USER IF NOT EXISTS 'sounduser'@'localhost' IDENTIFIED BY '1234';
GRANT SELECT, INSERT, UPDATE, DELETE ON sounddb.sound TO 'sounduser'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
