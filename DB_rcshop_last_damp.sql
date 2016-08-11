-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               5.7.12-log - MySQL Community Server (GPL)
-- Операционная система:         Win32
-- HeidiSQL Версия:              9.3.0.5077
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных rcshopdb
CREATE DATABASE IF NOT EXISTS `rcshopdb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `rcshopdb`;

-- Дамп структуры для таблица rcshopdb.orderedproductslist
CREATE TABLE IF NOT EXISTS `orderedproductslist` (
  `orderID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `productCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`orderID`,`productID`),
  KEY `FK_productsID` (`productID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Дамп данных таблицы rcshopdb.orderedproductslist: ~17 rows (приблизительно)
DELETE FROM `orderedproductslist`;
/*!40000 ALTER TABLE `orderedproductslist` DISABLE KEYS */;
INSERT INTO `orderedproductslist` (`orderID`, `productID`, `productCount`) VALUES
	(1, 1, 1),
	(1, 3, 4),
	(1, 8, 2),
	(2, 2, 3),
	(2, 4, 2),
	(2, 6, 1),
	(3, 1, 0),
	(3, 2, 1),
	(3, 3, 0),
	(3, 4, 1),
	(4, 2, 1),
	(4, 4, 1),
	(5, 3, 2),
	(5, 8, 1),
	(6, 2, 1),
	(6, 4, 1),
	(6, 6, 1),
	(7, 2, 1),
	(7, 4, 1),
	(7, 6, 1),
	(8, 4, 1);
/*!40000 ALTER TABLE `orderedproductslist` ENABLE KEYS */;

-- Дамп структуры для таблица rcshopdb.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) NOT NULL,
  `sumPrice` decimal(10,2) NOT NULL DEFAULT '0.00',
  `orderState` enum('PROCESSING','PROCESSED','OPEN') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'OPEN',
  PRIMARY KEY (`ID`),
  KEY `FK_userID_idx` (`userID`),
  CONSTRAINT `FK_userID_order` FOREIGN KEY (`userID`) REFERENCES `users` (`ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы rcshopdb.orders: ~6 rows (приблизительно)
DELETE FROM `orders`;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` (`ID`, `userID`, `sumPrice`, `orderState`) VALUES
	(1, 2, 1951.00, 'OPEN'),
	(2, 2, 2000.00, 'OPEN'),
	(3, 2, 550.00, 'OPEN'),
	(4, 2, 550.00, 'OPEN'),
	(5, 2, 810.50, 'OPEN'),
	(6, 2, 1150.00, 'OPEN'),
	(7, 2, 1150.00, 'OPEN'),
	(8, 9, 250.00, 'OPEN');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;

-- Дамп структуры для таблица rcshopdb.productcategories
CREATE TABLE IF NOT EXISTS `productcategories` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(150) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `id_UNIQUE` (`ID`),
  UNIQUE KEY `category_UNIQUE` (`categoryName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы rcshopdb.productcategories: ~4 rows (приблизительно)
DELETE FROM `productcategories`;
/*!40000 ALTER TABLE `productcategories` DISABLE KEYS */;
INSERT INTO `productcategories` (`ID`, `categoryName`) VALUES
	(3, 'FPV'),
	(4, 'OTHERS'),
	(1, 'RCAUTO'),
	(2, 'RCPLANE');
/*!40000 ALTER TABLE `productcategories` ENABLE KEYS */;

-- Дамп структуры для таблица rcshopdb.products
CREATE TABLE IF NOT EXISTS `products` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `price` decimal(10,2) NOT NULL DEFAULT '0.00',
  `categoryID` int(11) NOT NULL,
  `count` int(11) NOT NULL DEFAULT '0',
  `description` varchar(200) NOT NULL DEFAULT 'no descriptions',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  KEY `FK_categoryID_idx` (`categoryID`),
  CONSTRAINT `FK_categoryID` FOREIGN KEY (`categoryID`) REFERENCES `productcategories` (`ID`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- Дамп данных таблицы rcshopdb.products: ~9 rows (приблизительно)
DELETE FROM `products`;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` (`ID`, `name`, `price`, `categoryID`, `count`, `description`) VALUES
	(1, 'Dynam Catalina', 330.00, 2, 10, 'Remote controle airplane. Ready to fly.'),
	(2, 'MST-01D', 300.00, 1, 2, 'Brushless electrical remote controle car for drift. Almost ready to go.'),
	(3, 'Piper J3 Cup', 280.56, 2, 5, 'Remote controle airplane. Ready to fly.'),
	(4, 'Maverick Strada DC', 250.00, 1, 10, 'Brushed electrical remote control car for drift. Ready to go.'),
	(5, 'FAT Shark Altitude', 409.00, 3, 3, 'Googles for First Person View flyings with head tracker.'),
	(6, 'Traxxas E-Revo', 600.00, 1, 2, 'Brushless electrical remote controle car for offroad drive. Ready to go. '),
	(7, 'LiPo Charger', 35.50, 4, 20, 'no descriptions'),
	(8, 'Dynam Cesna', 250.75, 2, 10, 'Remote controle airplane. Ready to fly.'),
	(9, 'CC3D', 15.35, 4, 10, 'Fly controller.');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;

-- Дамп структуры для таблица rcshopdb.users
CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `registrDate` date NOT NULL,
  `login` varchar(45) CHARACTER SET ucs2 NOT NULL,
  `password` varchar(45) CHARACTER SET utf8 NOT NULL,
  `email` varchar(100) CHARACTER SET utf8 NOT NULL,
  `firstName` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `lastName` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `shippingAddress` varchar(200) CHARACTER SET utf8 DEFAULT 'none',
  `age` int(11) NOT NULL DEFAULT '0',
  `userType` enum('ADMIN','CLIENT') CHARACTER SET utf8 NOT NULL DEFAULT 'CLIENT',
  `isInBlackList` enum('true','false') CHARACTER SET utf8 NOT NULL DEFAULT 'false',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID_UNIQUE` (`ID`),
  UNIQUE KEY `password_UNIQUE` (`password`),
  UNIQUE KEY `login_UNIQUE` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Дамп данных таблицы rcshopdb.users: ~6 rows (приблизительно)
DELETE FROM `users`;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` (`ID`, `registrDate`, `login`, `password`, `email`, `firstName`, `lastName`, `shippingAddress`, `age`, `userType`, `isInBlackList`) VALUES
	(1, '2016-06-02', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'atrwanya@tut.by', 'Иван', 'Атрошонок', 'Заславль', 29, 'ADMIN', 'false'),
	(2, '2016-06-02', 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 'atrwanya@tut.by', 'Иван', 'Атрошонок', 'Заславль', 29, 'CLIENT', 'false'),
	(3, '2016-06-02', 'bond007', 'bond', 'bond@tut.by', 'Bond', 'Bondotich', 'Spain', 43, 'CLIENT', 'false'),
	(4, '2016-06-02', 'vistl', 'cvcvcb', 'vk@gmail.com', 'Vitya', 'Doloto', 'Russia', 12, 'CLIENT', 'false'),
	(5, '2016-06-05', 'rembo', 'retop', 'rembo@tut.by', 'Rembo', 'Rembovitch', 'USA', 23, 'CLIENT', 'true'),
	(6, '2016-06-05', 'ased', 'qwer', 'asd@rur.by', 'Ased', 'Twist', 'Canada', 21, 'CLIENT', 'true'),
	(7, '2016-06-07', 'wlancer', 'wlancer', 'wlancer@rr.by', 'Wanya', 'Winters', 'Minsk', 29, 'CLIENT', 'false'),
	(8, '2016-06-08', 'cheker', '9bcf0edc75944b260de8279d7a6d8174', 'checker@gmail.com', 'Chek', 'Rigtht', 'Minsk', 60, 'CLIENT', 'false'),
	(9, '2016-06-09', 'newuser', '0354d89c28ec399c00d3cb2d094cf093', 'newuser@tut.by', 'user', 'user', 'Minsk', 12, 'CLIENT', 'false');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
