-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Jun 25, 2021 at 04:00 PM
-- Server version: 8.0.21
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ecommerce`
--

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(120) COLLATE utf8_unicode_520_ci NOT NULL,
  `imageUrl` text COLLATE utf8_unicode_520_ci,
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_520_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`, `imageUrl`, `createdAt`, `updatedAt`) VALUES
(1, 'Rings', 'Jewelry_LP_Necklaces.jpg', '2021-06-24 00:00:00', '2021-06-25 01:15:06'),
(2, 'Earrings', 'Jewelry_LP_Earrings.jpg', '2021-06-25 01:48:32', '2021-06-25 01:48:32'),
(3, 'Necklaces & Pendants', 'Jewelry_LP_Necklaces.jpg', '2021-06-24 01:48:32', '2021-06-24 01:48:32');

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL,
  `description` varchar(200) COLLATE utf8_unicode_520_ci DEFAULT NULL,
  `address` varchar(200) COLLATE utf8_unicode_520_ci DEFAULT NULL,
  `phone` varchar(15) COLLATE utf8_unicode_520_ci DEFAULT NULL,
  `total` decimal(8,2) DEFAULT '0.00',
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `isFinished` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`)
) ENGINE=MyISAM AUTO_INCREMENT=2188 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_520_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`id`, `userId`, `description`, `address`, `phone`, `total`, `createdAt`, `isFinished`) VALUES
(2181, 2, 'ship nhanh nhanh dum em cuoi chong anh oi', '225 Nguyen Van Cu, Q5, HCMC', '0987654312', '3800.00', '2021-06-24 15:33:37', 1),
(2182, 3, 'ship vao buoi sang nha ban', '69 Nguyen Thi Minh Khai, HCMC', '0843223860', '1700.00', '2021-06-24 15:33:37', 1),
(2186, 2, NULL, '225 Nguyen Van Cu, Q5, HCMC', '0987654312', '860000.00', '2021-06-25 13:23:39', 1),
(2187, 0, NULL, NULL, NULL, '0.00', '2021-06-25 13:23:47', 0);

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
CREATE TABLE IF NOT EXISTS `order_details` (
  `orderId` int NOT NULL,
  `productId` int NOT NULL,
  `quantity` int DEFAULT '1',
  `discountPercent` decimal(8,1) DEFAULT '0.0',
  `subTotal` decimal(8,2) DEFAULT '0.00',
  PRIMARY KEY (`orderId`,`productId`),
  KEY `productId` (`productId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_520_ci;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`orderId`, `productId`, `quantity`, `discountPercent`, `subTotal`) VALUES
(2186, 20, 2, '0.0', '86000.00'),
(2182, 1, 1, '0.0', '1700.00'),
(2181, 3, 1, '0.0', '3800.00');

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(120) COLLATE utf8_unicode_520_ci NOT NULL,
  `description` text COLLATE utf8_unicode_520_ci,
  `imageUrl` text CHARACTER SET utf8 COLLATE utf8_unicode_520_ci,
  `price` decimal(8,2) DEFAULT '0.00',
  `quantityInStock` int DEFAULT '17',
  `soldQuantity` int DEFAULT '0',
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `categoryId` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `categoryId` (`categoryId`)
) ENGINE=MyISAM AUTO_INCREMENT=27 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_520_ci;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `description`, `imageUrl`, `price`, `quantityInStock`, `soldQuantity`, `createdAt`, `updatedAt`, `categoryId`) VALUES
(1, 'T1 Ring in Rose Gold with Diamonds, 2.5 mm', 'Pavé diamonds illuminate one side of this ring, adding a striking radiance to the design. A reinvention of a Tiffany icon, Tiffany T1 designs represent individual strength and perpetual power, worn outwardly to express what lies within. Style this ring with other Tiffany designs in 18k rose gold or mix your metals for a bold look.', '67795253_1010336_ED_M.jpg', '1700.00', 17, 1, '2021-06-24 15:12:23', '2021-06-24 15:12:23', 1),
(2, 'T1 Ring in Rose Gold, 4.5 mm', 'The beveled edge design of this wide ring creates a powerful and bold statement. A reinvention of a Tiffany icon, Tiffany T1 designs represent individual strength and perpetual power, worn outwardly to express what lies within. Wear this Tiffany T1 ring on its own or mix and match with other Tiffany rings to make a statement.', '67796489_1008969_ED.jpg', '2100.00', 26, 0, '2021-06-27 15:12:23', '2021-06-27 15:12:23', 1),
(3, 'Tiffany T T1 Ring', 'Pavé diamonds illuminate one side of this ring, adding a striking radiance to the design. A reinvention of a Tiffany icon, Tiffany T1 designs represent individual strength and perpetual power, worn outwardly to express what lies within. Style this ring with other Tiffany designs in 18k white gold or mix your metals for a bold look.\r\n\r\n- 18k white gold with round brilliant diamonds\r\n- 4.5 mm wide\r\n- Carat total weight .21', '68172071_1016276_SV_1.jpg', '3800.00', 8, 2, '2021-06-25 15:18:38', '2021-06-25 15:18:38', 1),
(4, 'X Closed Wide Ring', 'Highly polished 18k white gold and over 75 hand-set diamonds define this closed wide ring style. The sleek knife edge profile of this design—a code of the house since the introduction of the Tiffany®', '68659647_1018034_ED_M.jpg', '4500.00', 5, 0, '2021-06-29 12:40:50', '2021-06-29 12:40:50', 1),
(5, 'Freshwater Pearl Ring in Sterling Silver', 'Tiffany HardWear is elegantly subversive and captures the spirit of the women of New York City. Two bold bands of sterling silver exude modern sophistication in this double ring design.', '64048457_1004020_ED_M.jpg', '645.00', 21, 4, '2021-06-25 12:42:17', '2021-06-25 12:42:17', 1),
(6, 'Sixteen Stone Ring', 'Jean Schlumberger’s visionary creations are among the world’s most intricate designs. Brilliant diamonds alternate with platinum X\'s to create this dazzling design.', '19186555_924641_ED.jpg', '10500.00', 20, 10, '2021-06-25 12:43:44', '2021-06-25 12:43:44', 1),
(7, 'Sixteen Stone Ring 2.0', 'Jean Schlumberger’s visionary creations are among the world’s most intricate designs. Brilliant diamonds alternate with golden X\'s to create this dazzling design.', '11715966_934392_ED.jpg', '11500.00', 5, 3, '2021-06-25 12:49:10', '2021-06-25 12:49:10', 1),
(8, 'Band Ring', 'Inspired by the fire and radiance of our superlative diamonds, Tiffany Victoria uses a unique combination of cuts for a distinctly romantic sensibility. The feminine arrangement of this ring is the embodiment of subtle glamour.', '19286797_958160_SV_1_M.jpg', '20000.00', 10, 7, '2021-06-25 12:49:10', '2021-06-25 12:49:10', 1),
(9, 'X Closed Wide Ring', 'Highly polished 18k rose gold and over 75 hand-set diamonds define this closed wide ring style. The sleek knife edge profile of this design—a code of the house since the introduction of the Tiffany® Setting engagement ring in 1886—is pushed to a magnified scale for bold visual impact.', '68659590_1018030_ED_M.jpg', '4500.00', 27, 4, '2021-06-25 12:49:10', '2021-06-25 12:49:10', 1),
(10, 'Hexagon Ring', 'The Paloma\'s Studio collection celebrates the designer’s love of exuberant color combinations and bold design. This hexagon ring features a custom-cut blue topaz framed in an 18k gold setting. Stack this ring with other Paloma\'s Studio rings for a unique look that\'s all your own.', '67944291_1016456_ED_M.jpg', '2700.00', 4, 1, '2021-06-25 12:53:25', '2021-06-25 12:53:25', 1),
(11, 'T1 Open Hoop Earrings', 'Rows of striking diamonds illuminate the bold design of these open hoop earrings. A reinvention of a Tiffany icon, Tiffany T1 designs represent individual strength and perpetual power, worn outwardly to express what lies within.', '68408091_1016259_ED.jpg', '7500.00', 10, 2, '2021-06-25 12:53:25', '2021-06-25 12:53:25', 2),
(12, 'Flower Earrings', 'Inspired by the fire and radiance of our superlative diamonds, Tiffany Victoria uses a unique combination of cuts for a distinctly romantic sensibility. The beautiful shape of these classic diamond earrings allows the stones to play off of each other\'s glorious sheen.', '23954168_997901_ED.jpg', '3600.00', 17, 12, '2021-06-25 13:00:37', '2021-06-25 13:00:37', 2),
(13, 'Victoria Earrings', 'With an intensity that rivals the night sky, Tiffany Victoria celebrates the blazing brilliance of Tiffany diamonds. Timeless pearls complement shining diamonds in these classic earrings.', '38050982_984148_ED.jpg', '5200.00', 17, 2, '2021-06-25 13:00:37', '2021-06-25 13:00:37', 2),
(14, 'Platinum Earrings', 'Earrings in platinum with round brilliant diamonds.', '60572895_1027799_ED.jpg', '3500.00', 24, 16, '2021-06-25 13:00:37', '2021-06-25 13:00:37', 2),
(15, 'Diamond Vine Circle Earrings', 'With an intensity that rivals the night sky, Tiffany Victoria celebrates the blazing brilliance of Tiffany diamonds. Mixed-cut diamonds add a striking display of light to these circle earrings.', '68287480_1012497_ED.jpg', '9900.00', 13, 10, '2021-06-26 13:09:07', '2021-06-26 13:09:07', 2),
(16, 'Diamond Vine Drop Earrings', 'With an intensity that rivals the night sky, Tiffany Victoria celebrates the blazing brilliance of Tiffany diamonds. Mixed-cut diamonds add a striking display of light to these vine drop earrings.', '68287448_1007064_ED.jpg', '33600.00', 17, 7, '2021-06-25 13:09:07', '2021-06-25 13:09:07', 2),
(17, 'V-Rope Ear Clips', 'Jean Schlumberger’s visionary creations are among the world’s most intricate designs. Illuminated by brilliant diamonds, this timeless design makes a striking statement.', '19003965_1028001_ED.jpg', '12000.00', 12, 3, '2021-06-25 13:09:07', '2021-06-25 13:09:07', 2),
(18, 'Flame Ear Clips', 'Jean Schlumberger’s visionary creations are among the world’s most intricate designs. Stunning in design, these earrings make a dazzling statement.', '12628692_1028000_ED.jpg', '8000.00', 17, 1, '2021-06-28 13:09:07', '2021-06-28 13:09:07', 2),
(19, 'Earrings', 'Earrings in platinum and 18k gold with radiant Fancy Intense Yellow diamonds encircled by round brilliant white diamonds. Yellow diamonds, carat total weight 2.54; round brilliant white diamonds, carat total weight .58.', '30254406_930978_ED_M.jpg', '64000.00', 5, 6, '2021-06-25 13:09:07', '2021-06-25 13:09:07', 2),
(20, 'Mixed Cluster Drop Earrings', 'Inspired by the fire and radiance of our superlative diamonds, Tiffany Victoria uses a unique combination of cuts for a distinctly romantic sensibility. Alternating mixed-cut diamonds create striking constellations of light.', '35390529_996539_ED.jpg', '43000.00', 12, 3, '2021-06-25 13:09:07', '2021-06-25 13:09:07', 2),
(21, 'Flame Ear Clips', 'Jean Schlumberger’s visionary creations are among the world’s most intricate designs. Scintillating round brilliant diamonds set in platinum and striking strands of 18k yellow gold form these flame ear clips.', '13465819_1020229_ED.jpg', '18000.00', 41, 5, '2021-06-25 13:13:57', '2021-06-25 13:13:57', 2),
(22, 'Three Leaves Ear Clips', 'Jean Schlumberger’s visionary creations are among the world’s most intricate designs. Dazzling diamonds illuminate this sophisticated design.', '13849781_926361_ED.jpg', '22000.00', 10, 2, '2021-06-27 13:13:57', '2021-06-27 13:13:57', 2),
(23, 'Paloma\'s Melody Ring', 'This product is currently unavailable. Please call Customer Service for more information at 800 843 3269', '60699615_980279_ED_M.jpg', '29000.00', 8, 11, '2021-06-25 13:13:57', '2021-06-25 13:13:57', 1),
(24, 'Tiffany Circlet diamond necklace', 'The circular motif, which defines the popular collection Tiffany Circlet, is featured here in an extraordinary 40-inch-long necklace. Round brilliant diamonds, carat total weight 25.16', '24602621_261617_ED.jpg', '180000.00', 3, 1, '2021-06-25 13:20:20', '2021-06-25 13:20:20', 3),
(25, 'Mixed Cluster Necklace', 'Inspired by the fire and radiance of our superlative diamonds, Tiffany Victoria uses a unique combination of cuts for a distinctly romantic sensibility. A dazzling display of light, this necklace is truly captivating.', '35092927_993570_ED.jpg', '118000.00', 5, 1, '2021-06-29 13:20:20', '0000-00-00 00:00:00', 3),
(26, 'Bow Tie Necklace', 'This product is currently unavailable. Please call Customer Service for more information at 800 843 3269', '22348442_1020302_ED.jpg', '70000.00', 4, 2, '2021-06-25 13:20:20', '2021-06-25 13:20:20', 3);

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_520_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_520_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `role`) VALUES
(1, 'Admin'),
(0, 'User'),
(3, 'VIP');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) COLLATE utf8_unicode_520_ci NOT NULL,
  `password` varchar(30) COLLATE utf8_unicode_520_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_unicode_520_ci NOT NULL,
  `phone` varchar(15) COLLATE utf8_unicode_520_ci DEFAULT NULL,
  `email` varchar(30) COLLATE utf8_unicode_520_ci DEFAULT NULL,
  `imageUrl` text COLLATE utf8_unicode_520_ci,
  `createdAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `updatedAt` datetime DEFAULT CURRENT_TIMESTAMP,
  `roleId` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `roleId` (`roleId`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_520_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `name`, `phone`, `email`, `imageUrl`, `createdAt`, `updatedAt`, `roleId`) VALUES
(1, 'admin', '1234', 'Administrator', '0987654312', 'admin.me@gmail.com', NULL, '2021-06-24 01:38:17', '2021-06-24 01:38:17', 1),
(2, 'vdtrung', '1234', 'Vu Dinh Trung', '0312473954', 'trung@gmail.com', NULL, '2021-06-24 01:38:17', '2021-06-24 01:38:17', 0),
(3, 'nguyenvan', '1234', 'Nguyen Nguyen Van', '0941214702', 'nguyenvan@gmail.com', NULL, '2021-06-24 01:40:31', '2021-06-24 01:40:31', 0),
(4, 'ngocyen', '1234', 'Bui Ngoc Yen', '0843223860', 'ngocyen@pyon.com', NULL, '2021-06-24 01:40:31', '2021-06-24 01:40:31', 0),
(5, 'nguyentuan', '1234', 'Nguyen Tuan Tran', '098537293', 'nguyentuan@pyonsama.com', NULL, '2021-06-24 01:42:59', '2021-06-24 01:42:59', 0),
(6, 'darkling', '1234', 'Mr. Darkling', '032157393', 'darkling@netflix.com', NULL, '2021-06-24 01:42:59', '2021-06-24 01:42:59', 0),
(7, 'jonsnow', '1234', 'Jon Snow', '0986845781', 'jon@hbo.com', NULL, '2021-06-24 15:54:03', '2021-06-24 15:54:03', 0);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
