-- MySQL Script generated by MySQL Workbench
-- Mon Jul 27 22:48:55 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema food_delivery
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `food_delivery` ;

-- -----------------------------------------------------
-- Schema food_delivery
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `food_delivery` DEFAULT CHARACTER SET utf8 ;
USE `food_delivery` ;

-- -----------------------------------------------------
-- Table `food_delivery`.`loyalty_points`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`loyalty_points` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`loyalty_points` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `points_amount` INT NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`wallet`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`wallet` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`wallet` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `money_amount` DECIMAL(7,2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`user_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`user_address` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`user_address` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `user_address_string` VARCHAR(500) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `iduser_address_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`user_account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`user_account` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`user_account` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(200) NOT NULL,
  `user_password` VARCHAR(100) NOT NULL,
  `user_email` VARCHAR(200) NOT NULL,
  `is_active` TINYINT NOT NULL DEFAULT 1,
  `user_phone_number` VARCHAR(13) NOT NULL,
  `loyalty_points_id` BIGINT(100) NOT NULL,
  `wallet_id` BIGINT(100) NOT NULL,
  `user_address_id` BIGINT(100) NOT NULL,
  PRIMARY KEY (`id`, `loyalty_points_id`, `wallet_id`, `user_address_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `user_name_UNIQUE` (`user_name` ASC) VISIBLE,
  UNIQUE INDEX `user_email_UNIQUE` (`user_email` ASC) VISIBLE,
  INDEX `fk_user_account_loyalty_points1_idx` (`loyalty_points_id` ASC) VISIBLE,
  INDEX `fk_user_account_wallet1_idx` (`wallet_id` ASC) VISIBLE,
  INDEX `fk_user_account_user_address1_idx` (`user_address_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_account_loyalty_points1`
    FOREIGN KEY (`loyalty_points_id`)
    REFERENCES `food_delivery`.`loyalty_points` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_account_wallet1`
    FOREIGN KEY (`wallet_id`)
    REFERENCES `food_delivery`.`wallet` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_account_user_address1`
    FOREIGN KEY (`user_address_id`)
    REFERENCES `food_delivery`.`user_address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`user_role` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`user_role` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`account_to_roles`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`account_to_roles` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`account_to_roles` (
  `user_account_id` BIGINT(100) NOT NULL,
  `user_role_id` BIGINT(100) NOT NULL,
  PRIMARY KEY (`user_account_id`, `user_role_id`),
  INDEX `fk_user_account_has_user_role_user_role1_idx` (`user_role_id` ASC) VISIBLE,
  INDEX `fk_user_account_has_user_role_user_account_idx` (`user_account_id` ASC) VISIBLE,
  CONSTRAINT `fk_user_account_has_user_role_user_account`
    FOREIGN KEY (`user_account_id`)
    REFERENCES `food_delivery`.`user_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_account_has_user_role_user_role1`
    FOREIGN KEY (`user_role_id`)
    REFERENCES `food_delivery`.`user_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`delivery_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`delivery_address` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`delivery_address` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `user_address_id` BIGINT(100) NOT NULL,
  `custom_address_string` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `user_address_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_delivery_address_user_address1_idx` (`user_address_id` ASC) VISIBLE,
  CONSTRAINT `fk_delivery_address_user_address1`
    FOREIGN KEY (`user_address_id`)
    REFERENCES `food_delivery`.`user_address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`user_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`user_order` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`user_order` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `time_of_delivery` DATETIME NOT NULL,
  `order_status` VARCHAR(50) NOT NULL,
  `user_account_id` BIGINT(100) NOT NULL,
  `delivery_address_id` BIGINT(100) NOT NULL,
  `customer_name` VARCHAR(200) NULL,
  `customer_phone_number` VARCHAR(13) NULL,
  PRIMARY KEY (`id`, `user_account_id`, `delivery_address_id`),
  INDEX `fk_order_user_account1_idx` (`user_account_id` ASC) VISIBLE,
  INDEX `fk_user_order_delivery_address1_idx` (`delivery_address_id` ASC) VISIBLE,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  CONSTRAINT `fk_order_user_account1`
    FOREIGN KEY (`user_account_id`)
    REFERENCES `food_delivery`.`user_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_order_delivery_address1`
    FOREIGN KEY (`delivery_address_id`)
    REFERENCES `food_delivery`.`delivery_address` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`order_feedback`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`order_feedback` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`order_feedback` (
  `order_id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `order_rating` INT NOT NULL,
  `order_comment` VARCHAR(1000) NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_order_feedback_order1_idx` (`order_id` ASC) VISIBLE,
  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_feedback_order1`
    FOREIGN KEY (`order_id`)
    REFERENCES `food_delivery`.`user_order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`dish_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`dish_category` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`dish_category` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `category_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `category_name_UNIQUE` (`category_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`dish`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`dish` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`dish` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `dish_name` VARCHAR(200) NOT NULL,
  `dish_cost` DECIMAL(5,2) NOT NULL,
  `dish_description` VARCHAR(1000) NOT NULL,
  `dish_picture` MEDIUMBLOB NOT NULL,
  `dish_category_id` BIGINT(100) NOT NULL,
  PRIMARY KEY (`id`, `dish_category_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  UNIQUE INDEX `dish_name_UNIQUE` (`dish_name` ASC) VISIBLE,
  INDEX `fk_dish_dish_category1_idx` (`dish_category_id` ASC) VISIBLE,
  CONSTRAINT `fk_dish_dish_category1`
    FOREIGN KEY (`dish_category_id`)
    REFERENCES `food_delivery`.`dish_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`dish_feedback`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`dish_feedback` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`dish_feedback` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `dish_rating` INT NOT NULL,
  `dish_comment` VARCHAR(1000) NULL,
  `user_account_id` BIGINT(100) NOT NULL,
  `dish_id` BIGINT(100) NOT NULL,
  PRIMARY KEY (`id`, `user_account_id`, `dish_id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
  INDEX `fk_dish_feedback_user_account1_idx` (`user_account_id` ASC) VISIBLE,
  INDEX `fk_dish_feedback_dish1_idx` (`dish_id` ASC) VISIBLE,
  CONSTRAINT `fk_dish_feedback_user_account1`
    FOREIGN KEY (`user_account_id`)
    REFERENCES `food_delivery`.`user_account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_dish_feedback_dish1`
    FOREIGN KEY (`dish_id`)
    REFERENCES `food_delivery`.`dish` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `food_delivery`.`order_item`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `food_delivery`.`order_item` ;

CREATE TABLE IF NOT EXISTS `food_delivery`.`order_item` (
  `id` BIGINT(100) NOT NULL AUTO_INCREMENT,
  `dish_amount` INT NOT NULL,
  `item_cost` DECIMAL(5,2) NOT NULL,
  `dish_id` BIGINT(100) NOT NULL,
  `user_order_id` BIGINT(100) NOT NULL,
  PRIMARY KEY (`id`, `dish_id`, `user_order_id`),
  INDEX `fk_order_menu_dish1_idx` (`dish_id` ASC) VISIBLE,
  INDEX `fk_order_menu_item_user_order1_idx` (`user_order_id` ASC) VISIBLE,
  CONSTRAINT `fk_order_menu_dish1`
    FOREIGN KEY (`dish_id`)
    REFERENCES `food_delivery`.`dish` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_order_menu_item_user_order1`
    FOREIGN KEY (`user_order_id`)
    REFERENCES `food_delivery`.`user_order` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;