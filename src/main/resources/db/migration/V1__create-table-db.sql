-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema db_scholarship
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema db_scholarship
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `db_scholarship` DEFAULT CHARACTER SET utf8 ;
USE `db_scholarship` ;

-- -----------------------------------------------------
-- Table `db_scholarship`.`Class`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_scholarship`.`Class` (
  `id` BIGINT NOT NULL,
  `name` VARCHAR(200) NULL,
  `status` VARCHAR(200) NOT NULL,
  `start_date` DATE NOT NULL,
  `end_date` DATE NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scholarship`.`Squad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_scholarship`.`Squad` (
  `id` BIGINT NOT NULL,
  `name` VARCHAR(200) NULL,
  `Class_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_Squad_Class1_idx` (`Class_id` ASC) VISIBLE,
  CONSTRAINT `fk_Squad_Class1`
    FOREIGN KEY (`Class_id`)
    REFERENCES `db_scholarship`.`Class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scholarship`.`Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_scholarship`.`Student` (
  `id` BIGINT NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `email` VARCHAR(200) NOT NULL,
  `number` VARCHAR(20) NULL,
  `date_of_birth` DATE NULL,
  `Squad_id` BIGINT NULL,
  `Class_id` BIGINT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE,
  INDEX `fk_Student_Squad_idx` (`Squad_id` ASC) VISIBLE,
  INDEX `fk_Student_Class1_idx` (`Class_id` ASC) VISIBLE,
  CONSTRAINT `fk_Student_Squad`
    FOREIGN KEY (`Squad_id`)
    REFERENCES `db_scholarship`.`Squad` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Student_Class1`
    FOREIGN KEY (`Class_id`)
    REFERENCES `db_scholarship`.`Class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scholarship`.`Organizers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_scholarship`.`Organizer` (
  `id` BIGINT NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `email` VARCHAR(200) NOT NULL,
  `role` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `db_scholarship`.`Class_organizers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `db_scholarship`.`Class_organizers` (
  `Class_id` BIGINT NOT NULL,
  `Organizer_id` BIGINT NOT NULL,
  PRIMARY KEY (`Class_id`, `Organizer_id`),
  INDEX `fk_Class_has_Organizer_Organizer1_idx` (`Organizer_id` ASC) VISIBLE,
  INDEX `fk_Class_has_Organizer_Class1_idx` (`Class_id` ASC) VISIBLE,
  CONSTRAINT `fk_Class_has_Organizer_Class1`
    FOREIGN KEY (`Class_id`)
    REFERENCES `db_scholarship`.`Class` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Class_has_Organizer_Organizer1`
    FOREIGN KEY (`Organizer_id`)
    REFERENCES `db_scholarship`.`Organizer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
