-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2015 at 04:18 AM
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
-- Table structure for table `customer_data`
--

CREATE TABLE IF NOT EXISTS `customer_data` (
`customer_id` int(11) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phoneNumber` varchar(255) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer_data`
--

INSERT INTO `customer_data` (`customer_id`, `nama`, `username`, `password`, `email`, `address`, `phoneNumber`) VALUES
(1, 'Mr.Administrator', 'admin', 'root', 'adminstrator@mysite.com', 'Bandung, Indonesia', '+62'),
(2, 'andorid002', 'dummy2', 'abc123', '', NULL, NULL),
(3, 'android001', 'dummy', 'abc123', '', NULL, NULL),
(14, 'Test', 'test', '123', 'test@gmail.com', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `order_data`
--

CREATE TABLE IF NOT EXISTS `order_data` (
`order_id` int(11) NOT NULL,
  `userOrdered` varchar(255) NOT NULL,
  `order_date` date NOT NULL,
  `oder_type` varchar(255) NOT NULL,
  `order_pickup_address` varchar(255) NOT NULL,
  `oder_destination_address` varchar(255) NOT NULL,
  `order_goods_type` varchar(255) NOT NULL,
  `order_goods_amount` int(11) NOT NULL,
  `order_status` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `order_data`
--

INSERT INTO `order_data` (`order_id`, `userOrdered`, `order_date`, `oder_type`, `order_pickup_address`, `oder_destination_address`, `order_goods_type`, `order_goods_amount`, `order_status`) VALUES
(13, 'admin', '2015-03-14', 'FAST', 'JL.GAJAH MADA NO 210 LUBUK BASUNG , AGAM', 'Perumahan Buah Batu Blok A no 20 , Bandung , Jawa Barat', 'FOOD', 8000, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `customer_data`
--
ALTER TABLE `customer_data`
 ADD PRIMARY KEY (`customer_id`), ADD UNIQUE KEY `username` (`username`);

--
-- Indexes for table `order_data`
--
ALTER TABLE `order_data`
 ADD PRIMARY KEY (`order_id`), ADD KEY `userOrdered` (`userOrdered`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `customer_data`
--
ALTER TABLE `customer_data`
MODIFY `customer_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `order_data`
--
ALTER TABLE `order_data`
MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=18;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `order_data`
--
ALTER TABLE `order_data`
ADD CONSTRAINT `order_data_ibfk_1` FOREIGN KEY (`userOrdered`) REFERENCES `customer_data` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
