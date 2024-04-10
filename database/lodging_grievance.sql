-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 10, 2024 at 04:19 PM
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
(2, 'Karnataka Police', 'karnataka.police@gmail.com', 'karnataka.police', 2),
(3, 'Chennai Police', 'chennai.police@gmail.com', 'chennai.police', 1),
(4, 'Chennai Transportation', 'chennai.transportation@gmail.com', 'chennai.transportation', 3),
(5, 'Chennai Education', 'chennai.education@gmail.com', 'chennai.education', 4),
(6, 'Chennai Health', 'chennai.health@gmail.com', 'chennai.health', 5),
(7, 'Chennai Social Services', 'chennai.socialservices@gmail.com', 'chennai.socialservices', 6),
(8, 'Chennai Environmental Protection', 'chennai.environmental.protection@gmail.com', 'chennai.environmental.protection', 7),
(9, 'Chennai Housing and urban development', 'chennai.housingandurbandevelopment@gmail.com', 'chennai.housingandurbandevelopment', 8),
(10, 'Chennai Labor welfare', 'chennai.labor.welfare@gmail.com', 'chennai.labor.welfare', 9),
(11, 'Chennai Agriculture development', 'chennai.agriculture.development@gmail.com', 'chennai.agriculture.development', 10);

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
(7, 'Chennai Environmental protection', 6, 'Chennai, Tamilnadu, India', ''),
(8, 'Chennai Housing and urban development', 7, 'Chennai, Tamilnadu, India', ''),
(9, 'Chennai Labor welfare', 8, 'Chennai, Tamilnadu, India', ''),
(10, 'Chennai Agriculture development', 9, 'Chennai, Tamilnadu, India', ''),
(11, 'Coimbatore Police', 1, 'Coimbatore, Tamilnadu, India', ''),
(12, 'Salem Police', 1, 'Salem, Tamilnadu, India', ''),
(13, 'Tiruppur Police', 1, 'Tiruppur, Tamilnadu, India', ''),
(14, 'Erode Police', 1, 'Erode, Tamilnadu, India', ''),
(15, 'Palakkad Police', 1, 'Palakkad, Kerala, India', ''),
(16, 'Karur Police', 1, 'Karur, Tamilnadu, India', ''),
(17, 'Peelamedu Police', 1, 'Peelamedu, Coimbatore, Tamilnadu, India', ''),
(18, 'Gandhipuram Police', 1, 'Gandhipuram, Coimbatore, Tamilnadu, India', ''),
(19, 'Bengaluru Transportation', 2, 'Bengaluru, Karnataka, India', ''),
(20, 'Hyderabad Transportation', 2, 'Hyderabad, Telangana, India', ''),
(21, 'Thiruvananthapuram Transportation', 2, 'Thiruvananthapuram, Kerala, India', ''),
(22, 'Bengaluru Education', 3, 'Bengaluru, Karnataka, India', ''),
(23, 'Hyderabad Education', 3, 'Hyderabad, Telangana, India', ''),
(24, 'Thiruvananthapuram Education', 3, 'Thiruvananthapuram, Kerala, India', ''),
(25, 'Bengaluru Health', 4, 'Bengaluru, Karnataka, India', ''),
(26, 'Hyderabad Health', 4, 'Hyderabad, Telangana, India', ''),
(27, 'Thiruvananthapuram Health', 4, 'Thiruvananthapuram, Kerala, India', ''),
(28, 'Bengaluru Social Services', 5, 'Bengaluru, Karnataka, India', ''),
(29, 'Hyderabad Social Services', 5, 'Hyderabad, Telangana, India', ''),
(30, 'Thiruvananthapuram Social Services', 5, 'Thiruvananthapuram, Kerala, India', ''),
(31, 'Bengaluru Environmental Protection', 6, 'Bengaluru, Karnataka, India', ''),
(32, 'Hyderabad Environmental Protection', 6, 'Hyderabad, Telangana, India', ''),
(33, 'Thiruvananthapuram Environmental Protection', 6, 'Thiruvananthapuram, Kerala, India', ''),
(34, 'Bengaluru Housing and urban development', 7, 'Bengaluru, Karnataka, India', ''),
(35, 'Hyderabad Housing and urban development', 7, 'Hyderabad, Telangana, India', ''),
(36, 'Thiruvananthapuram Housing and urban development', 7, 'Thiruvananthapuram, Kerala, India', ''),
(37, 'Bengaluru Labor welfare', 8, 'Bengaluru, Karnataka, India', ''),
(38, 'Hyderabad Labor welfare', 8, 'Hyderabad, Telangana, India', ''),
(39, 'Thiruvananthapuram Labor welfare', 8, 'Thiruvananthapuram, Kerala, India', ''),
(40, 'Bengaluru Agriculture development', 9, 'Bengaluru, Karnataka, India', ''),
(41, 'Hyderabad Agriculture development', 9, 'Hyderabad, Telangana, India', ''),
(42, 'Thiruvananthapuram Agriculture development', 9, 'Thiruvananthapuram, Kerala, India', '');

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
(1, 'violation of rights ', 'violation of rights ', '1', ' 2 5 7 ', '', '2024-03-09', 1),
(2, 'Sample', 'Sample', '1', ' 2 5 7 ', '', '2024-03-09', 0),
(3, 'Violation against children', 'Violation against children', '1', ' 3 1 6 ', '', '2024-03-31', 0);

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
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
  MODIFY `department_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `grievance`
--
ALTER TABLE `grievance`
  MODIFY `grievance_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `person`
--
ALTER TABLE `person`
  MODIFY `person_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
