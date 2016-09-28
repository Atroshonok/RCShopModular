-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.7.14-log - MySQL Community Server (GPL)
-- Операционная система:         Win64
-- HeidiSQL Версия:              9.3.0.5077
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных rcshop_db
CREATE DATABASE IF NOT EXISTS `rcshop_db` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;
USE `rcshop_db`;

-- Дамп структуры для таблица rcshop_db.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `orderID` bigint(20) NOT NULL AUTO_INCREMENT,
  `orderState` enum('PROCESSING','PROCESSED','OPEN') COLLATE utf8_unicode_ci DEFAULT NULL,
  `sumPrice` double DEFAULT NULL,
  `userID_FK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`orderID`),
  KEY `userID_FK` (`userID_FK`),
  CONSTRAINT `userID_FK` FOREIGN KEY (`userID_FK`) REFERENCES `users` (`userID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы rcshop_db.orders: ~17 rows (приблизительно)
DELETE FROM `orders`;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`orderID`, `orderState`, `sumPrice`, `userID_FK`) VALUES
	(1, 'OPEN', 600, 2),
	(2, 'OPEN', 646.06, 2),
	(5, 'OPEN', 335.5, 2),
	(6, 'OPEN', 600, 2),
	(7, 'OPEN', 300, 2),
	(8, 'OPEN', 271.8, 2),
	(9, 'OPEN', 300, 2),
	(10, 'OPEN', 300, 2),
	(11, 'OPEN', 600, 2),
	(13, 'OPEN', 600, 2),
	(14, 'OPEN', 630, 2),
	(15, 'OPEN', 1200, 2),
	(16, 'OPEN', 1200, 2),
	(17, 'OPEN', 1200, 2),
	(18, 'OPEN', 1926.8, 2),
	(19, 'OPEN', 707.36, 2),
	(20, 'OPEN', 300, 2),
	(21, 'OPEN', 300, 2),
	(22, 'OPEN', 300, 2),
	(23, 'OPEN', 600, 2),
	(24, 'OPEN', 600, 8),
	(25, 'OPEN', 330, 2);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- Дамп структуры для таблица rcshop_db.orders_orderlines
CREATE TABLE IF NOT EXISTS `orders_orderlines` (
  `orderID_FK` bigint(20) NOT NULL DEFAULT '0',
  `orderLineID_FK` bigint(20) NOT NULL DEFAULT '0',
  KEY `orderLineID_FK` (`orderLineID_FK`),
  KEY `orderID_FK` (`orderID_FK`),
  CONSTRAINT `FK_orders_orderlines_order_lines` FOREIGN KEY (`orderLineID_FK`) REFERENCES `order_lines` (`OrderLineID`),
  CONSTRAINT `FK_orders_orderlines_orders` FOREIGN KEY (`orderID_FK`) REFERENCES `orders` (`orderID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы rcshop_db.orders_orderlines: ~4 rows (приблизительно)
DELETE FROM `orders_orderlines`;
/*!40000 ALTER TABLE `orders_orderlines` DISABLE KEYS */;
INSERT INTO `orders_orderlines` (`orderID_FK`, `orderLineID_FK`) VALUES
	(1, 1),
	(2, 2),
	(2, 3),
	(2, 4),
	(5, 5),
	(5, 6),
	(6, 7),
	(7, 8),
	(8, 9),
	(8, 10),
	(9, 11),
	(10, 12),
	(11, 13),
	(13, 14),
	(14, 15),
	(14, 16),
	(15, 17),
	(15, 18),
	(16, 17),
	(16, 18),
	(17, 17),
	(17, 18),
	(18, 19),
	(18, 20),
	(18, 21),
	(18, 22),
	(19, 23),
	(19, 24),
	(20, 25),
	(21, 26),
	(22, 27),
	(23, 28),
	(24, 29),
	(25, 30);
/*!40000 ALTER TABLE `orders_orderlines` ENABLE KEYS */;

-- Дамп структуры для таблица rcshop_db.order_lines
CREATE TABLE IF NOT EXISTS `order_lines` (
  `OrderLineID` bigint(20) NOT NULL AUTO_INCREMENT,
  `count` int(11) DEFAULT NULL,
  `productID_FK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`OrderLineID`),
  KEY `productID_FK` (`productID_FK`),
  CONSTRAINT `productID_FK` FOREIGN KEY (`productID_FK`) REFERENCES `products` (`productID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы rcshop_db.order_lines: ~25 rows (приблизительно)
DELETE FROM `order_lines`;
/*!40000 ALTER TABLE `order_lines` DISABLE KEYS */;
INSERT INTO `order_lines` (`OrderLineID`, `count`, `productID_FK`) VALUES
	(1, 2, 2),
	(2, 1, 1),
	(3, 1, 3),
	(4, 1, 7),
	(5, 1, 2),
	(6, 1, 7),
	(7, 1, 6),
	(8, 1, 2),
	(9, 1, 11),
	(10, 1, 12),
	(11, 1, 2),
	(12, 1, 2),
	(13, 2, 2),
	(14, 2, 2),
	(15, 1, 2),
	(16, 1, 1),
	(17, 2, 2),
	(18, 1, 6),
	(19, 1, 2),
	(20, 2, 6),
	(21, 1, 3),
	(22, 1, 11),
	(23, 2, 3),
	(24, 1, 11),
	(25, 1, 2),
	(26, 1, 2),
	(27, 1, 2),
	(28, 2, 2),
	(29, 2, 2),
	(30, 1, 1);
/*!40000 ALTER TABLE `order_lines` ENABLE KEYS */;

-- Дамп структуры для таблица rcshop_db.products
CREATE TABLE IF NOT EXISTS `products` (
  `productID` bigint(20) NOT NULL AUTO_INCREMENT,
  `count` int(11) DEFAULT NULL,
  `description` text COLLATE utf8_unicode_ci,
  `name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` double(10,2) DEFAULT NULL,
  `categoryID_FK` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`productID`),
  KEY `categoryID_FK` (`categoryID_FK`),
  CONSTRAINT `categoryID_FK` FOREIGN KEY (`categoryID_FK`) REFERENCES `product_categories` (`categoryID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы rcshop_db.products: ~12 rows (приблизительно)
DELETE FROM `products`;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`productID`, `count`, `description`, `name`, `price`, `categoryID_FK`) VALUES
	(1, 50, 'Remote controle airplane. Ready to fly.', 'Dynam Catalina', 330.00, 2),
	(2, 70, 'Brushless electrical remote controle car for drift. Almost ready to go.', 'MST-01D', 300.00, 1),
	(3, 50, 'Remote controle airplane. Ready to fly.', 'Piper J3 Cup', 280.56, 2),
	(4, 10, 'Brushed electrical remote control car for drift. Ready to go.', 'Maverick Strada DC', 250.00, 1),
	(5, 3, 'Googles for First Person View flyings with head tracker.', 'FAT Shark Altitude', 409.00, 3),
	(6, 3, 'Brushless electrical remote controle car for offroad drive. Ready to go. ', 'Traxxas E-Revo', 600.00, 1),
	(7, 25, 'no descriptions', 'LiPo Charger', 35.50, 4),
	(8, 10, 'Remote controle airplane. Ready to fly.', 'Dynam Cesna', 250.75, 2),
	(9, 10, 'Fly controller.', 'CC3D', 15.35, 4),
	(10, 10, 'Remote controle transmitter.', 'Turnigy 9XR', 50.00, 4),
	(11, 100, 'The Supermarine Spitfire is THE defining aircraft of World War 2 and following on from the Mk1a the Mk5 represents the pinnacle of early Spitfire development.   After the defensive campaign of the Battle of Britain, the RAF, RCAF and the USAAF moved steadily onto the offensive and the Mk5 became part of the spearhead of overall allied strategy, including air operations over France, North Africa and later during the Italian campaign.   Better armed, faster and more maneuverable, the Mk5 gave the RAF and other allied air forces including the USAAF, a decisive advantage over the Luftwaffe’s Bf109E and was on equal terms with the Bf109F, battling both of these axis aircraft in the ETO (European Theatre of Operations) as well as in the air war over the deserts of North Africa and the Mediterranean between 1941 and 1943.  Loved by its pilots and feared by the enemy, the Mk5 Spitfire’s reputation as a thoroughbred fighter was hard earned and well deserved and it will always have a special place in military aviation history as a result.', 'Durafly™ Spitfire Mk5 1100mm (PnF) Desert Scheme', 146.24, 2),
	(12, 10, 'Racing quadrocopter.', 'ZMR250', 125.56, 2);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

-- Дамп структуры для таблица rcshop_db.product_categories
CREATE TABLE IF NOT EXISTS `product_categories` (
  `categoryID` bigint(20) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`categoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы rcshop_db.product_categories: ~4 rows (приблизительно)
DELETE FROM `product_categories`;
/*!40000 ALTER TABLE `product_categories` DISABLE KEYS */;
INSERT INTO `product_categories` (`categoryID`, `categoryName`) VALUES
	(1, 'RC Auto'),
	(2, 'RC Planes'),
	(3, 'FPV'),
	(4, 'Other');
/*!40000 ALTER TABLE `product_categories` ENABLE KEYS */;

-- Дамп структуры для таблица rcshop_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `userID` bigint(20) NOT NULL AUTO_INCREMENT,
  `age` int(11) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `firstName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `isInBlackList` char(1) COLLATE utf8_unicode_ci DEFAULT 'N',
  `lastName` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `login` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `registrDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `shippingAddress` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `userType` enum('ADMIN','CLIENT') COLLATE utf8_unicode_ci DEFAULT 'CLIENT',
  PRIMARY KEY (`userID`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы rcshop_db.users: ~7 rows (приблизительно)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`userID`, `age`, `email`, `firstName`, `isInBlackList`, `lastName`, `login`, `password`, `registrDate`, `shippingAddress`, `userType`) VALUES
	(1, 29, 'atrwanya@tut.by', 'Иван', 'N', 'Атрошонок', 'admin', '21232f297a57a5a743894a0e4a801fc3', '2016-06-02 00:00:00', 'Заславль', 'ADMIN'),
	(2, 29, 'atrwanya@tut.by', 'Иван', 'N', 'Атрошонок', 'user', 'ee11cbb19052e40b07aac0ca060c23ee', '2016-06-02 00:00:00', 'Заславль', 'CLIENT'),
	(3, 26, 'KLiapko@gmail.com', 'Ekaterina', 'N', 'Atroshonok', 'KLiapko', 'f8901d98ab94feaba726f8e3a8ec6d49', '2016-09-20 11:40:34', 'Zaslavl', 'CLIENT'),
	(4, 33, 'victor@tut.by', 'victor', 'N', 'v', 'Victor', 'ffc150a160d37e92012c196b6af4160d', '2016-09-22 15:29:52', 'Moscow', 'CLIENT'),
	(5, 29, 'iroman@tut.by', 'Roma', 'N', 'Игнатович', 'IRoman', 'e10adc3949ba59abbe56e057f20f883e', '2016-08-30 16:35:25', 'Минск', 'CLIENT'),
	(6, 15, 'em@gmail.com', 'ghghf', 'N', 'jjhghf', 'Nina45', '96e79218965eb72c92a549dd5a330112', '2016-09-22 18:46:07', 'ggggg', 'CLIENT'),
	(7, 50, 'johnuoren@tut.com', 'q', 'N', 'q', 'johnuoren', 'd6dacc3e6d7eb078eeb89211d463d1ff', '2016-09-22 19:43:21', 'q', 'CLIENT'),
	(8, 45, 'victor@tut.by', 'v', 'N', 'u', 'Batman', 'ec0e2603172c73a8b644bb9456c1ff6e', '2016-09-27 00:09:39', 'r', 'CLIENT');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
