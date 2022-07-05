drop schema ims;

CREATE SCHEMA IF NOT EXISTS `ims`;

USE `ims` ;

CREATE TABLE IF NOT EXISTS `ims.customers` (
                       `id` INT NOT NULL AUTO_INCREMENT,
                       `first_name` VARCHAR(40) DEFAULT NULL,
                       `surname` VARCHAR(40) DEFAULT NULL,
                       PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS `ims.items` (
                       `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                       `item_name` VARCHAR(30) NOT NULL,
                       `item_cost` DECIMAL(8,2) NOT NULL,
                       `number_in_stock` INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS `ims.orders` (
                        `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                        `customer_id` INT NOT NULL,
                        FOREIGN KEY (`customer_id`) REFERENCES `ims.customers`(`id`),
                        `total_cost` DECIMAL(8,2) NOT NULL,
                        `date` DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS `ims.order_basket` (
                          `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                          `order_id` INT NOT NULL,
                          FOREIGN KEY (`order_id`) REFERENCES `ims.orders`(`id`),
                          `item_id` INT NOT NULL,
                          FOREIGN KEY (`item_id`) REFERENCES `ims.items`(`id`)
);


