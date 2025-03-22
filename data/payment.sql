DROP TABLE IF EXISTS `payment_events`;
CREATE TABLE `payment_events` (
                                  `txn_id` varchar(50) not null ,
                                  `hotel_id` int not null,
                                  `customer_id` int not null default 0,
                                  `is_payment_done` boolean default false,
                                  `payment_type` enum('pay_in','pay_out','refund'),
                                  `status` int NOT NULL DEFAULT '1',
                                  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  PRIMARY KEY (`txn_id`),
                                  KEY `hotel_id` (hotel_id) USING BTREE ,
                                  KEY `customer_id` (customer_id) USING BTREE,
                                  KEY `status` (`status`) USING BTREE
) ENGINE = InnoDb;



DROP TABLE IF EXISTS `payment_bookings`;
CREATE TABLE `payment_bookings`
(
    `booking_id` int NOT NULL,
    `txn_id` varchar(50),
    `customer_id` int NOT NULL ,
    `hotel_id` int NOT NULL,
    `amount` decimal(19,4) NOT NULL,
    `currency` varchar(3) NOT NULL,
    `method` enum('vnpay','paypal','hbanking','visa'),
    `payment_status` enum('not_started','executing','success','failed','expired') default 'not_started',
    `ledger_updated` boolean default false,
    `wallet_updated` boolean default false,
    `status` int NOT NULL DEFAULT '1',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`booking_id`,`txn_id`),
    KEY `hotel_id` (`hotel_id`) USING BTREE,
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDb;


DROP TABLE IF EXISTS `refund_bookings`;
CREATE TABLE `refund_bookings`
(
    `booking_id` int NOT NULL,
    `pay_in_txn_id` varchar(50),
    `txn_id` varchar(50),
    `customer_id` int NOT NULL ,
    `hotel_id` int NOT NULL,
    `amount` decimal(19,4) NOT NULL,
    `currency` varchar(3) NOT NULL,
    `method` enum('vnpay','paypal','hbanking','visa'),
    `reason` enum('user_cancel','hotel_cancel','system_fail') ,
    `payment_status` enum('not_started','executing','success','failed','expired') default 'not_started',
    `ledger_updated` boolean default false,
    `wallet_updated` boolean default false,
    `status` int NOT NULL DEFAULT '1',
    `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`booking_id`,`txn_id`),
    KEY `customer_id` (`hotel_id`) USING BTREE,
    KEY `hotel_id` (`hotel_id`) USING BTREE,
    KEY `status` (`status`) USING BTREE
) ENGINE = InnoDb;


DROP TABLE IF EXISTS `hotel_wallets`;
CREATE TABLE `hotel_wallets` (
                                 `id` int not null auto_increment,
                                 `hotel_id` int not null,
                                 `balance` varchar(255),
                                 `currency` varchar(3) NOT NULL,
                                 `status` int NOT NULL DEFAULT '1',
                                 `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                 `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 PRIMARY KEY (`id`),
                                 KEY `hotel_id` (`hotel_id`) USING BTREE,
                                 KEY `status` (`status`) USING BTREE
) ENGINE = InnoDb;

DROP TABLE IF EXISTS `ledgers`;
CREATE TABLE `ledgers` (
                           `id` int not null auto_increment,
                           `transaction_id` int not null,
                           `account_id` int not null,
                           `debit` decimal(19,4) ,
                           `credit` decimal(19,4),
                           `currency` VARCHAR(3),
                           `status` int NOT NULL DEFAULT '1',
                           `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                           `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                           PRIMARY KEY (`id`),
                           KEY `account_id` (`account_id`) USING BTREE ,
                           KEY `transaction_id` (`transaction_id`) USING BTREE ,
                           KEY `status` (`status`) USING BTREE
) ENGINE = InnoDb;

DROP TABLE IF EXISTS `hotel_payment_info`;
CREATE TABLE `hotel_payment_info` (
                                      `id` int  auto_increment,
                                      `type` enum('paypal','bank_transfer') not null ,
                                      `detail_id` int not null,
                                      `status` int NOT NULL DEFAULT '1',
                                      `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                      `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                      PRIMARY KEY (`id`),
                                      KEY `detail_id` (`detail_id`) USING BTREE,
                                      KEY `type` (`type` ) USING BTREE,
                                      UNIQUE (`detail_id`,`type`),
                                      KEY `status` (`status`) USING BTREE
) ENGINE = InnoDb;


DROP TABLE IF EXISTS `paypal_info`;
CREATE TABLE `paypal_info` (
                               `id` INT AUTO_INCREMENT,
                               `hotel_id` INT,
                               `salt` varchar(50) DEFAULT NULL,
                               `email` VARCHAR(255),
                               `status` int NOT NULL DEFAULT '1',
                               `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                               `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`),
                               KEY `hotel_id` (hotel_id) USING BTREE,
                               KEY `status` (`status`) USING BTREE,
                               UNIQUE KEY `email` (`email`)
)ENGINE = InnoDb;

DROP TABLE IF EXISTS `bank_info`;
CREATE TABLE `bank_info` (
                             `id` INT AUTO_INCREMENT,
                             `hotel_id` INT  NOT NULL ,
                             `bank_name` VARCHAR(50) NOT NULL ,
                             `bank_branch` VARCHAR(50) NOT NULL ,
                             `salt` varchar(50) DEFAULT NULL,
                             `account_name` VARCHAR(50) NOT NULL ,
                             `account_number` VARCHAR(255) NOT NULL ,
                             `swift_code` VARCHAR(50) NOT NULL ,
                             `status` int NOT NULL DEFAULT '1',
                             `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                             `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                             PRIMARY KEY (`id`),
                             KEY `hotel_id` (hotel_id) USING BTREE,
                             KEY `status` (`status`) USING BTREE,
                             UNIQUE(`swift_code`,`account_number`)
)ENGINE = InnoDb;