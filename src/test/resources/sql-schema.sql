DROP TABLE `customers`, `items`, `orders`, `order_basket`;
-- DROP DATABASE test_ims;

-- CREATE DATABASE IF NOT EXISTS test_ims;

CREATE TABLE IF NOT EXISTS `customers` (
                                       `id` INT NOT NULL AUTO_INCREMENT,
                                       `first_name` VARCHAR(40) DEFAULT NULL,
                                       `surname` VARCHAR(40) DEFAULT NULL,
                                       PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `items` (
                                   `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                                   `item_name` VARCHAR(30) NOT NULL,
                                   `item_cost` DECIMAL(8,2) NOT NULL,
                                   `number_in_stock` INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS `orders` (
                                    `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                                    `customer_id` INT NOT NULL,
                                    FOREIGN KEY (`customer_id`) REFERENCES `customers`(`id`) ON DELETE CASCADE,
                                    `total_cost` DECIMAL(8,2) NOT NULL

);

CREATE TABLE IF NOT EXISTS `order_basket` (
                                  `id` INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                                  `order_id` INT NOT NULL,
                                  FOREIGN KEY (`order_id`) REFERENCES `orders`(`id`) ON DELETE CASCADE,
                                  `item_id` INT NOT NULL,
                                  FOREIGN KEY (`item_id`) REFERENCES `items`(`id`) ON DELETE CASCADE
);


