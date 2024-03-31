-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 31, 2024 at 02:27 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lodging_grievance`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL,
  `admin_name` varchar(500) NOT NULL,
  `admin_email` varchar(500) NOT NULL,
  `admin_password` varchar(500) NOT NULL,
  `admin_department` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`admin_id`, `admin_name`, `admin_email`, `admin_password`, `admin_department`) VALUES
(1, 'admin', 'admin@gmail.com', 'admin', 0),
(2, 'Karnataka Police', 'karnataka.police@gmail.com', 'karnataka.police', 2);

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments` (
  `department_id` int(11) NOT NULL,
  `department_name` varchar(500) NOT NULL,
  `department_category` int(11) NOT NULL,
  `department_location` varchar(500) NOT NULL,
  `department_keywords` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`department_id`, `department_name`, `department_category`, `department_location`, `department_keywords`) VALUES
(1, 'Chennai Police', 1, 'Chennai, Tamilnadu, India', ''),
(2, 'Karnataka Police', 1, 'Bengaluru, Karnataka, India', ''),
(3, 'Chennai Transportation', 2, 'Chennai, Tamilnadu, India', ''),
(4, 'Chennai Education', 3, 'Chennai, Tamilnadu, India', ''),
(5, 'Chennai Health', 4, 'Chennai, Tamilnadu, India', ''),
(6, 'Chennai Social Services', 5, 'Chennai, Tamilnadu, India', ''),
(7, 'Environmental protection', 6, 'Chennai, Tamilnadu, India', ''),
(8, 'Chennai Housing and urban development', 7, 'Chennai, Tamilnadu, India', ''),
(9, 'Chennai Labor welfare', 8, 'Chennai, Tamilnadu, India', ''),
(10, 'Chennai Agriculture development', 9, 'Chennai, Tamilnadu, India', '');

-- --------------------------------------------------------

--
-- Table structure for table `grievance`
--

CREATE TABLE `grievance` (
  `grievance_id` int(11) NOT NULL,
  `grievance_title` varchar(500) NOT NULL,
  `grievance_description` varchar(500) NOT NULL,
  `grievance_person` varchar(500) NOT NULL,
  `grievance_department` varchar(500) NOT NULL,
  `grievance_department_text` varchar(500) NOT NULL,
  `grievance_date` varchar(500) NOT NULL,
  `grievance_status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `grievance`
--

INSERT INTO `grievance` (`grievance_id`, `grievance_title`, `grievance_description`, `grievance_person`, `grievance_department`, `grievance_department_text`, `grievance_date`, `grievance_status`) VALUES
(1, 'violation of rights ', 'violation of rights ', '1', ' 2 5 7 ', '', '2024-03-09', 0),
(2, 'Sample', 'Sample', '1', ' 2 5 7 ', '', '2024-03-09', 0);

-- --------------------------------------------------------

--
-- Table structure for table `person`
--

CREATE TABLE `person` (
  `person_id` int(11) NOT NULL,
  `person_name` varchar(100) NOT NULL,
  `person_email` varchar(100) NOT NULL,
  `person_password` varchar(100) NOT NULL,
  `person_aadhaar` varchar(100) NOT NULL,
  `person_phone` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `person`
--

INSERT INTO `person` (`person_id`, `person_name`, `person_email`, `person_password`, `person_aadhaar`, `person_phone`) VALUES
(1, 'fazil', 'fazil@gmail.com', 'fazil', '', '');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`department_id`);

--
-- Indexes for table `grievance`
--
ALTER TABLE `grievance`
  ADD PRIMARY KEY (`grievance_id`);

--
-- Indexes for table `person`
--
ALTER TABLE `person`
  ADD PRIMARY KEY (`person_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
  MODIFY `department_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `grievance`
--
ALTER TABLE `grievance`
  MODIFY `grievance_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `person_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
