-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema parkaway
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema parkaway
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `parkaway` DEFAULT CHARACTER SET utf8 ;
USE `parkaway` ;

-- -----------------------------------------------------
-- Table `parkaway`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkaway`.`user` (
  `user_id` BIGINT(0) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(100) NOT NULL,
  `last_name` VARCHAR(100) NOT NULL,
  `email` VARCHAR(100) NOT NULL,
  `phone` VARCHAR(13) NOT NULL,
  `password` VARCHAR(300) NOT NULL,
  `status` ENUM('ACTIVE', 'INACTIVE', 'UNVERIFIED') NOT NULL,
  `verification_code` VARCHAR(64) NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `userId_UNIQUE` (`user_id` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `phone_UNIQUE` (`phone` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `parkaway`.`location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkaway`.`location` (
  `location_id` BIGINT(0) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`location_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `parkaway`.`parking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkaway`.`parking` (
  `parking_id` BIGINT(0) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `hourly_price` INT NOT NULL,
  `image_link` VARCHAR(300) NOT NULL,
  `location_id` BIGINT(0) NOT NULL,
  `capacity` INT NOT NULL,
  PRIMARY KEY (`parking_id`),
  UNIQUE INDEX `parking_id_UNIQUE` (`parking_id` ASC) VISIBLE,
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  CONSTRAINT `PARKING-LOCATION FK`
    FOREIGN KEY (`location_id`)
    REFERENCES `parkaway`.`location` (`location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `parkaway`.`booking`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkaway`.`booking` (
  `booking_id` BIGINT(0) NOT NULL AUTO_INCREMENT,
  `parking_id` BIGINT(0) NOT NULL,
  `start_time` TIMESTAMP NOT NULL,
  `end_time` TIMESTAMP NOT NULL,
  `user_id` BIGINT(0) NOT NULL,
  `booking_status` ENUM('BOOKED', 'FAILED') NOT NULL,
  `created_at` TIMESTAMP NOT NULL,
  PRIMARY KEY (`booking_id`),
  UNIQUE INDEX `booking_id_UNIQUE` (`booking_id` ASC) VISIBLE,
  INDEX `BOOKING-USER FK_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `BOOKING-USER FK`
    FOREIGN KEY (`user_id`)
    REFERENCES `parkaway`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `BOOKING-PARKING FK`
    FOREIGN KEY (`parking_id`)
    REFERENCES `parkaway`.`parking` (`parking_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `parkaway`.`facility`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkaway`.`facility` (
  `facility_id` BIGINT(0) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(300) NOT NULL,
  PRIMARY KEY (`facility_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `parkaway`.`parking_facility`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkaway`.`parking_facility` (
  `id` BIGINT(0) NOT NULL AUTO_INCREMENT,
  `parking_id` BIGINT(0) NOT NULL,
  `facility_id` BIGINT(0) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `PARKING-FACILITY FK1_idx` (`parking_id` ASC) VISIBLE,
  CONSTRAINT `PARKING-FACILITY FK1`
    FOREIGN KEY (`parking_id`)
    REFERENCES `parkaway`.`parking` (`parking_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `PARKING-FACILITY FK2`
    FOREIGN KEY (`facility_id`)
    REFERENCES `parkaway`.`facility` (`facility_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `parkaway`.`availability`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `parkaway`.`availability` (
  `availability_id` BIGINT(0) NOT NULL AUTO_INCREMENT,
  `hour` INT NOT NULL,
  `date` DATE NOT NULL,
  `availability` INT NOT NULL,
  `parking_id` BIGINT(0) NOT NULL,
  PRIMARY KEY (`availability_id`),
  UNIQUE INDEX `id_UNIQUE` (`availability_id` ASC) VISIBLE,
  INDEX `Parking-Availability FK_idx` (`parking_id` ASC) VISIBLE,
  CONSTRAINT `Parking-Availability FK`
    FOREIGN KEY (`parking_id`)
    REFERENCES `parkaway`.`parking` (`parking_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
