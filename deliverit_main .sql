-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 15, 2015 at 11:58 AM
-- Server version: 5.6.21
-- PHP Version: 5.5.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `deliverit_main`
--

-- --------------------------------------------------------

--
-- Table structure for table `order_data`
--

CREATE TABLE IF NOT EXISTS `order_data` (
`order_id` int(11) NOT NULL,
  `userOrdered` varchar(255) NOT NULL,
  `order_date` date NOT NULL,
  `oder_type` varchar(255) DEFAULT NULL,
  `order_pickup_address` varchar(255) NOT NULL,
  `oder_destination_address` varchar(255) NOT NULL,
  `order_goods_type` varchar(255) DEFAULT NULL,
  `order_goods_amount` int(11) NOT NULL,
  `unit` varchar(25) DEFAULT NULL,
  `order_status` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order_data`
--

INSERT INTO `order_data` (`order_id`, `userOrdered`, `order_date`, `oder_type`, `order_pickup_address`, `oder_destination_address`, `order_goods_type`, `order_goods_amount`, `unit`, `order_status`) VALUES
(2, 'admin', '2015-05-07', 'NORMAL', 'SURABAYA', 'JAKARTA', 'FURNITURE', 90, '', 1),
(3, 'admin', '2015-05-07', 'FAST', 'BALI', 'MAKASSAR', 'FOOD', 23, '', 0),
(7, 'customer', '2015-05-08', 'NORMAL', 'A', 'b', 'ELECTRONIC', 241, 'BOX', 1),
(8, 'customer', '2015-05-08', 'FAST', 'S', 'f', 'FURNITURE', 2222, 'TON', 1),
(9, 'customer', '2015-05-09', 'NORMAL', 'A', 'c', 'FURNITURE', 2, 'BOX', 0),
(10, 'customer', '2015-05-09', 'FAST', 'FGY', 'telkom', 'FURNITURE', 3, 'UNIT', 1),
(11, 'customer', '2015-05-10', 'NORMAL', 'JAKARTA', 'SURABAYA', 'FURNITURE', 235, 'BOX', 1),
(12, 'John', '2016-03-31', 'FAST', 'Hérouville-Saint-Clair', 'Ichalkaranji', 'Food', 871, 'Box', 1),
(13, 'admin', '2015-09-19', 'FAST', 'Veenendaal', 'Esen', 'Food', 266, 'Box', 0),
(14, 'admin', '2015-02-20', 'FAST', 'Lebbeke', 'Wimmertingen', 'Food', 718, 'Box', 0),
(15, 'admin', '2016-04-11', 'FAST', 'Racine', 'Tilff', 'Food', 203, 'Box', 1),
(16, 'admin', '2015-03-25', 'FAST', 'Anjou', 'Louisville', 'Food', 982, 'Box', 0),
(17, 'admin', '2015-09-03', 'FAST', 'Ciudad Real', 'Tsiigehtchic', 'Food', 779, 'Box', 0),
(18, 'admin', '2014-10-02', 'FAST', 'Lasne', 'St. Johann in Tirol', 'Food', 992, 'Box', 0),
(19, 'admin', '2016-01-11', 'FAST', 'Thanjavur', 'Namur', 'Food', 480, 'Box', 0),
(20, 'admin', '2015-02-21', 'FAST', 'Northallerton', 'l''Escaill?re', 'Food', 740, 'Box', 0),
(21, 'admin', '2014-10-26', 'FAST', 'Neudörfl', 'Dillingen', 'Food', 506, 'Box', 0),
(22, 'admin', '2015-10-30', 'FAST', 'Kohima', 'Chelmsford', 'Food', 785, 'Box', 0),
(23, 'admin', '2015-01-10', 'FAST', 'Renfrew', '?slahiye', 'Food', 992, 'Box', 0),
(24, 'admin', '2015-08-05', 'FAST', 'Annapolis Royal', 'Landelies', 'Food', 925, 'Box', 0),
(25, 'admin', '2014-06-28', 'FAST', 'High Level', 'King Township', 'Food', 245, 'Box', 0),
(26, 'admin', '2015-10-24', 'FAST', 'Sivry-Rance', 'Grimbergen', 'Food', 994, 'Box', 0),
(27, 'admin', '2015-04-08', 'FAST', 'Merksplas', 'Sanzeno', 'Food', 519, 'Box', 0),
(28, 'admin', '2015-07-13', 'FAST', 'Matera', 'St. John''s', 'Food', 842, 'Box', 0),
(29, 'admin', '2015-08-24', 'FAST', 'Huntingdon', 'Kitchener', 'Food', 339, 'Box', 0),
(30, 'admin', '2015-04-30', 'FAST', 'Tongrinne', 'Lalbahadur Nagar', 'Food', 475, 'Box', 0),
(31, 'admin', '2016-02-09', 'FAST', 'Meerle', 'Westlock', 'Food', 59, 'Box', 0),
(32, 'admin', '2014-10-18', 'FAST', 'Maria', 'Dieppe', 'Food', 121, 'Box', 0),
(33, 'admin', '2015-05-04', 'FAST', 'Sparwood', 'Montresta', 'Food', 473, 'Box', 0),
(34, 'admin', '2014-08-17', 'FAST', 'Ospedaletto Lodigiano', 'Herk-de-Stad', 'Food', 517, 'Box', 0),
(35, 'admin', '2015-12-17', 'FAST', 'Town of Yarmouth', 'Montebello', 'Food', 210, 'Box', 0),
(36, 'admin', '2015-01-05', 'FAST', 'Shillong', 'Wallasey', 'Food', 841, 'Box', 0),
(37, 'admin', '2016-01-07', 'FAST', 'Guadalupe', 'Llanidloes', 'Food', 641, 'Box', 0),
(38, 'admin', '2015-11-19', 'FAST', 'Mellery', 'Salt Spring Island', 'Food', 570, 'Box', 0),
(39, 'admin', '2014-12-09', 'FAST', 'Petacciato', 'Carbonear', 'Food', 838, 'Box', 0),
(40, 'admin', '2014-10-18', 'FAST', 'Loy', 'Nevers', 'Food', 514, 'Box', 0),
(41, 'admin', '2014-09-25', 'FAST', 'Price', 'Aieta', 'Food', 101, 'Box', 0),
(42, 'admin', '2014-07-18', 'FAST', 'Carterton', 'Warspite', 'Food', 977, 'Box', 0),
(43, 'admin', '2015-11-01', 'FAST', 'Kapiti', 'Mission', 'Food', 754, 'Box', 0),
(44, 'admin', '2015-08-07', 'FAST', 'Villanova d''Albenga', 'Neath', 'Food', 642, 'Box', 0),
(45, 'admin', '2015-10-19', 'FAST', 'Bhind', 'Chelsea', 'Food', 409, 'Box', 0),
(46, 'admin', '2016-04-30', 'FAST', 'Ottawa-Carleton', 'San Marcello Pistoiese', 'Food', 354, 'Box', 0),
(47, 'admin', '2015-12-03', 'FAST', 'Merksplas', 'Oyen', 'Food', 190, 'Box', 0),
(48, 'admin', '2015-03-09', 'FAST', 'Cobourg', 'Heerlen', 'Food', 692, 'Box', 0),
(49, 'admin', '2014-09-24', 'FAST', 'Harrisburg', 'Belford Roxo', 'Food', 190, 'Box', 0),
(50, 'admin', '2014-09-29', 'FAST', 'Glendale', 'Sanluri', 'Food', 879, 'Box', 0),
(51, 'admin', '2015-11-09', 'FAST', 'Birmingham', 'Stranraer', 'Food', 654, 'Box', 0),
(52, 'admin', '2014-09-03', 'FAST', 'Laon', 'Haren', 'Food', 105, 'Box', 0),
(53, 'admin', '2014-06-29', 'FAST', 'Hazaribag', 'Columbus', 'Food', 128, 'Box', 0),
(54, 'admin', '2015-11-10', 'FAST', 'Kakinada', 'Parkland County', 'Food', 561, 'Box', 0),
(55, 'admin', '2014-08-16', 'FAST', 'Ceuta', 'Boise', 'Food', 212, 'Box', 0),
(56, 'admin', '2015-03-30', 'FAST', 'Gojra', 'Wilhelmshaven', 'Food', 64, 'Box', 0),
(57, 'admin', '2015-11-06', 'FAST', 'Wattrelos', 'Lutsel K''e', 'Food', 506, 'Box', 0),
(58, 'admin', '2015-03-15', 'FAST', 'Sedgewick', 'Wondelgem', 'Food', 639, 'Box', 0),
(59, 'admin', '2015-09-01', 'FAST', 'Recklinghausen', 'Ebenthal in Kärnten', 'Food', 541, 'Box', 0),
(60, 'admin', '2015-09-22', 'FAST', 'Milton Keynes', 'Lokeren', 'Food', 565, 'Box', 0),
(61, 'admin', '2014-05-30', 'FAST', 'Puri', 'Colli a Volturno', 'Food', 210, 'Box', 0),
(62, 'admin', '2015-08-12', 'FAST', 'Lacombe', 'Meix-le-Tige', 'Food', 910, 'Box', 0),
(63, 'admin', '2014-10-07', 'FAST', 'Szczecin', 'San Martino in Badia/St. Martin in Thurn', 'Food', 719, 'Box', 0),
(64, 'admin', '2015-07-28', 'FAST', 'Saint-Eug?ne-de-Ladri?re', 'Overland Park', 'Food', 714, 'Box', 0),
(65, 'admin', '2014-06-12', 'FAST', 'Bad D?rkheim', 'Lapscheure', 'Food', 445, 'Box', 0),
(66, 'admin', '2016-04-11', 'FAST', 'Orilla', 'Malegaon', 'Food', 728, 'Box', 0),
(67, 'admin', '2015-03-17', 'FAST', 'Saltara', 'Kaaskerke', 'Food', 953, 'Box', 0),
(68, 'admin', '2014-10-25', 'FAST', 'Baie-d''Urf?', 'Issy-les-Moulineaux', 'Food', 829, 'Box', 0),
(69, 'admin', '2014-07-04', 'FAST', 'Donnas', 'Matagami', 'Food', 191, 'Box', 0),
(70, 'admin', '2015-06-02', 'FAST', 'Mödling', 'Tulita', 'Food', 848, 'Box', 0),
(71, 'admin', '2015-02-28', 'FAST', 'Boston', 'Oppido Mamertina', 'Food', 695, 'Box', 0),
(72, 'admin', '2015-05-16', 'FAST', 'Rimbey', 'Jumet', 'Food', 646, 'Box', 0),
(73, 'admin', '2015-11-30', 'FAST', 'Subbiano', 'Villanova d''Albenga', 'Food', 491, 'Box', 0),
(74, 'admin', '2015-12-12', 'FAST', 'K?z?lcahamam', 'Bostaniçi', 'Food', 219, 'Box', 0),
(75, 'admin', '2015-04-21', 'FAST', 'Otukpo', 'Tiegem', 'Food', 225, 'Box', 0),
(76, 'admin', '2015-07-09', 'FAST', 'Palanzano', 'Aubagne', 'Food', 296, 'Box', 0),
(77, 'admin', '2014-06-23', 'FAST', 'Savannah', 'Augusta', 'Food', 50, 'Box', 0),
(78, 'admin', '2015-03-27', 'FAST', 'Shawinigan', 'Nevers', 'Food', 107, 'Box', 0),
(79, 'admin', '2015-02-18', 'FAST', 'Mussy-la-Ville', 'Bhopal', 'Food', 553, 'Box', 0),
(80, 'admin', '2015-07-27', 'FAST', 'Wroc?aw', 'Dhanbad', 'Food', 215, 'Box', 0),
(81, 'admin', '2014-09-02', 'FAST', 'Sart-Dames-Avelines', 'Market Drayton', 'Food', 616, 'Box', 0),
(82, 'admin', '2015-08-16', 'FAST', 'M?lheim', 'Louveign?', 'Food', 649, 'Box', 0),
(83, 'admin', '2015-10-28', 'FAST', 'Charny', 'Ficulle', 'Food', 711, 'Box', 0),
(84, 'admin', '2015-12-16', 'FAST', 'Pirmasens', 'Trani', 'Food', 579, 'Box', 0),
(85, 'admin', '2015-07-03', 'FAST', 'Clearwater Municipal District', 'Mignanego', 'Food', 863, 'Box', 0),
(86, 'admin', '2015-07-30', 'FAST', 'Zevekote', 'Beaconsfield', 'Food', 135, 'Box', 0),
(87, 'admin', '2014-08-30', 'FAST', 'Zuccarello', 'Sirsa', 'Food', 643, 'Box', 0),
(88, 'admin', '2016-02-04', 'FAST', 'Busso', 'Montebelluna', 'Food', 793, 'Box', 0),
(89, 'admin', '2015-07-28', 'FAST', 'Dornbirn', 'Joliet', 'Food', 913, 'Box', 0),
(90, 'admin', '2015-07-03', 'FAST', 'Minneapolis', 'Coreglia Antelminelli', 'Food', 162, 'Box', 0),
(91, 'admin', '2014-11-13', 'FAST', 'Landau', 'Hastings', 'Food', 621, 'Box', 0),
(92, 'admin', '2014-09-02', 'FAST', 'Sossano', 'Salzburg', 'Food', 50, 'Box', 0),
(93, 'admin', '2014-08-01', 'FAST', 'Herselt', 'Morena', 'Food', 135, 'Box', 0),
(94, 'admin', '2014-09-18', 'FAST', 'Coutisse', 'Gallodoro', 'Food', 784, 'Box', 0),
(95, 'admin', '2015-05-12', 'FAST', 'Sautin', 'Linsmeau', 'Food', 574, 'Box', 0),
(96, 'admin', '2015-07-10', 'FAST', 'Celle', 'Cannalonga', 'Food', 859, 'Box', 0),
(97, 'admin', '2016-03-23', 'FAST', 'Sousa', 'Stratford', 'Food', 797, 'Box', 0),
(98, 'admin', '2015-04-29', 'FAST', 'Maiolati Spontini', 'Morro Reatino', 'Food', 404, 'Box', 0),
(99, 'admin', '2015-02-08', 'FAST', 'Challand-Saint-Victor', 'Sandy', 'Food', 236, 'Box', 0),
(100, 'admin', '2014-10-22', 'FAST', 'Loksbergen', 'North Battleford', 'Food', 74, 'Box', 0),
(101, 'admin', '2014-12-26', 'FAST', 'Aalen', 'Criciúma', 'Food', 293, 'Box', 0),
(102, 'admin', '2015-12-24', 'FAST', 'Gualdo Cattaneo', 'Gierle', 'Food', 482, 'Box', 0),
(103, 'admin', '2016-05-06', 'FAST', 'Cambridge', 'Newbury', 'Food', 210, 'Box', 0),
(104, 'admin', '2015-10-03', 'FAST', 'Lugo', 'Sioux City', 'Food', 328, 'Box', 0),
(105, 'admin', '2015-02-06', 'FAST', 'Bornival', 'Springfield', 'Food', 597, 'Box', 0),
(106, 'admin', '2015-05-18', 'FAST', 'Burlington', 'Bridlington', 'Food', 654, 'Box', 0),
(107, 'admin', '2015-07-29', 'FAST', 'Raymond', 'Halisahar', 'Food', 457, 'Box', 0),
(108, 'admin', '2015-11-12', 'FAST', 'Chippenham', 'Pali', 'Food', 496, 'Box', 0),
(109, 'admin', '2014-05-10', 'FAST', 'Shimla', 'Thorold', 'Food', 668, 'Box', 0),
(110, 'admin', '2015-04-12', 'FAST', 'Silverton', 'Ludwigsfelde', 'Food', 355, 'Box', 0),
(111, 'admin', '2015-06-20', 'FAST', 'Arzano', 'Charleville-Mézières', 'Food', 330, 'Box', 0);

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE IF NOT EXISTS `transaction` (
`transaction_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `orderchecker_name` varchar(50) NOT NULL,
  `username` varchar(10) NOT NULL,
  `payment_status` varchar(255) NOT NULL,
  `total` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transaction_id`, `order_id`, `orderchecker_name`, `username`, `payment_status`, `total`) VALUES
(32, 2, 'Zlatan Ibrahimovic', 'admin', '', 0),
(33, 7, 'Zlatan Ibrahimovic', 'customer', '', 1928000),
(34, 12, 'Zlatan Ibrahimovic', 'John', '', 200000),
(35, 8, 'Zlatan Ibrahimovic', 'customer', '', 399960000),
(36, 10, 'Zlatan Ibrahimovic', 'customer', '', 245000),
(37, 15, 'Zlatan Ibrahimovic', 'admin', 'Paid', 200000),
(38, 11, 'Zlatan Ibrahimovic', 'customer', 'Paid', 1880000);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
`userID` int(50) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `userType` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userID`, `username`, `password`, `userType`) VALUES
(1, 'admin', 'root', 'admin'),
(2, 'ordercheck', 'ordercheck', 'orderchecker'),
(3, 'customer', 'customer', 'customer'),
(4, 'John', 'john', 'customer');

-- --------------------------------------------------------

--
-- Table structure for table `user_data`
--

CREATE TABLE IF NOT EXISTS `user_data` (
  `username` varchar(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `phoneNumber` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_data`
--

INSERT INTO `user_data` (`username`, `name`, `email`, `address`, `phoneNumber`) VALUES
('admin', 'Administrator', 'administrator@gmail.com', 'Bandung', '+6281222225317'),
('customer', 'Lionel Messi', 'leo.messi@fcbarcelona.com', 'Barcelona', '+8765438674533'),
('ordercheck', 'Zlatan Ibrahimovic', 'zlatan@psg.com', 'Paris', '+24456671120');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `order_data`
--
ALTER TABLE `order_data`
 ADD PRIMARY KEY (`order_id`), ADD KEY `order_data_ibfk_2` (`userOrdered`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
 ADD PRIMARY KEY (`transaction_id`), ADD UNIQUE KEY `order_id` (`order_id`), ADD KEY `orderIndex` (`order_id`), ADD KEY `FK_USERNAME` (`username`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
 ADD PRIMARY KEY (`userID`), ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `user_data`
--
ALTER TABLE `user_data`
 ADD UNIQUE KEY `username` (`username`), ADD KEY `name` (`name`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `order_data`
--
ALTER TABLE `order_data`
MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=112;
--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
MODIFY `transaction_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=39;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
MODIFY `userID` int(50) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `order_data`
--
ALTER TABLE `order_data`
ADD CONSTRAINT `order_data_ibfk_2` FOREIGN KEY (`userOrdered`) REFERENCES `user` (`username`);

--
-- Constraints for table `user_data`
--
ALTER TABLE `user_data`
ADD CONSTRAINT `FK_USER` FOREIGN KEY (`username`) REFERENCES `user` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
