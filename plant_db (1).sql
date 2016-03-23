-- phpMyAdmin SQL Dump
-- version 4.5.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2016 at 07:03 AM
-- Server version: 5.7.9
-- PHP Version: 5.6.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `plant_db`
--
CREATE DATABASE IF NOT EXISTS `plant_db` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `plant_db`;

-- --------------------------------------------------------

--
-- Table structure for table `labels`
--

DROP TABLE IF EXISTS `labels`;
CREATE TABLE IF NOT EXISTS `labels` (
  `Plant_ID` int(10) NOT NULL,
  `Label_Name` varchar(35) NOT NULL,
  `Additional_Notes` text NOT NULL,
  PRIMARY KEY (`Plant_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `plants`
--

DROP TABLE IF EXISTS `plants`;
CREATE TABLE IF NOT EXISTS `plants` (
  `Plant_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Genus` varchar(35) NOT NULL,
  `Species` varchar(35) NOT NULL,
  `Picture_ID` varchar(35) NOT NULL,
  `Table_Number` varchar(35) NOT NULL,
  `Table_Position` varchar(35) NOT NULL,
  `Other_Notes` text NOT NULL,
  `User_ID` int(10) NOT NULL,
  PRIMARY KEY (`Plant_ID`),
  KEY `Plant_ID` (`Plant_ID`),
  KEY `User_ID` (`User_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `plants`
--

INSERT INTO `plants` (`Plant_ID`, `Genus`, `Species`, `Picture_ID`, `Table_Number`, `Table_Position`, `Other_Notes`, `User_ID`) VALUES
(6, 'KJNK', 'KJJKJ', 'LJKJ', 'K', 'K', 'KJK', 2);

-- --------------------------------------------------------

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
CREATE TABLE IF NOT EXISTS `tasks` (
  `Task_ID` int(10) NOT NULL AUTO_INCREMENT,
  `Water_Amount` text NOT NULL,
  `Water_Time` date NOT NULL,
  `Fertilizer` text NOT NULL,
  `Start_Date` date NOT NULL,
  `End_Date` date NOT NULL,
  `Other_Notes` text NOT NULL,
  `Plant_ID` int(10) NOT NULL,
  PRIMARY KEY (`Task_ID`),
  KEY `Plant_ID` (`Plant_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(35) NOT NULL,
  `type` int(3) NOT NULL,
  `Picture_ID` varchar(35) NOT NULL,
  `Name` varchar(35) NOT NULL,
  `Email` text NOT NULL,
  `Phone` text NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `ID_2` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `username`, `password`, `type`, `Picture_ID`, `Name`, `Email`, `Phone`) VALUES
(1, 'admin', '1234', 0, 'Tasks1.png', '7ywan', '7ywan@hotmail.com', '5404296909'),
(2, 'student', '1234', 1, 'Tasks1.png', '', '', ''),
(3, 'donator', '1234', 2, 'Tasks1.png', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `work_schedule`
--

DROP TABLE IF EXISTS `work_schedule`;
CREATE TABLE IF NOT EXISTS `work_schedule` (
  `Time_in` date NOT NULL,
  `time_out` date NOT NULL,
  `Date` date NOT NULL,
  `Comments` text NOT NULL,
  `Task_ID` int(10) NOT NULL,
  `User_ID` int(10) NOT NULL,
  PRIMARY KEY (`Task_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `labels`
--
ALTER TABLE `labels`
  ADD CONSTRAINT `labels_ibfk_1` FOREIGN KEY (`Plant_ID`) REFERENCES `plants` (`Plant_ID`);

--
-- Constraints for table `plants`
--
ALTER TABLE `plants`
  ADD CONSTRAINT `plants_ibfk_1` FOREIGN KEY (`User_ID`) REFERENCES `users` (`ID`);

--
-- Constraints for table `tasks`
--
ALTER TABLE `tasks`
  ADD CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`Plant_ID`) REFERENCES `plants` (`Plant_ID`);

--
-- Constraints for table `work_schedule`
--
ALTER TABLE `work_schedule`
  ADD CONSTRAINT `work_schedule_ibfk_1` FOREIGN KEY (`Task_ID`) REFERENCES `tasks` (`Task_ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
