-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 15, 2017 at 07:20 PM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 7.1.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `tutorspoint`
--

-- --------------------------------------------------------

--
-- Table structure for table `comments`
--

CREATE TABLE `comments` (
  `parent_id`  INT(11)      NOT NULL DEFAULT '-1',
  `comment_id` INT(11)      NOT NULL,
  `video_id`   INT(11)      NOT NULL,
  `comment`    VARCHAR(512) NOT NULL,
  `user_id`    INT(11)      NOT NULL,
  `user_type`  VARCHAR(16)  NOT NULL,
  `timestamp`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `comments`
--

INSERT INTO `comments` (`parent_id`, `comment_id`, `video_id`, `comment`, `user_id`, `user_type`, `timestamp`) VALUES
  (-1, 9, 29, 'sounds like Q.', 4, 'student', '2017-10-09 18:34:35'),
  (-1, 13, 28, 'random comment', 4, 'student', '2017-10-11 16:04:54'),
  (-1, 16, 37, 'commenting for testing', 4, 'student', '2017-10-11 16:50:35'),
  (-1, 22, 39, 'castle on the hill', 4, 'teacher', '2017-10-11 19:27:59'),
  (-1, 23, 39, 'thank you.', 4, 'student', '2017-10-11 19:29:24'),
  (-1, 25, 41, 'comment on tree', 1, 'teacher', '2017-10-12 04:49:09');

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `course_id`  INT(11)     NOT NULL,
  `teacher_id` INT(11)     NOT NULL,
  `name`       VARCHAR(64) NOT NULL,
  `avg_rating` FLOAT       NOT NULL DEFAULT '0'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `courses`
--

INSERT INTO `courses` (`course_id`, `teacher_id`, `name`, `avg_rating`) VALUES
  (1, 1, 'Cryptography', 3.5),
  (2, 1, 'OS', 4),
  (14, 1, 'Modelling', 3),
  (15, 3, 'OR', 3),
  (17, 3, 'Automata', 4),
  (18, 4, 'MP', 3),
  (19, 1, 'DS', 0);

-- --------------------------------------------------------

--
-- Table structure for table `course_ratings`
--

CREATE TABLE `course_ratings` (
  `student_id` INT(11)     NOT NULL,
  `course_id`  INT(11)     NOT NULL,
  `rating`     SMALLINT(6) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `course_ratings`
--

INSERT INTO `course_ratings` (`student_id`, `course_id`, `rating`) VALUES
  (4, 1, 3),
  (4, 2, 4),
  (4, 14, 4),
  (4, 17, 4),
  (4, 18, 3),
  (5, 1, 4),
  (5, 14, 2),
  (5, 15, 3);

-- --------------------------------------------------------

--
-- Table structure for table `in_progress_courses`
--

CREATE TABLE `in_progress_courses` (
  `student_id` INT(11) NOT NULL,
  `course_id`  INT(11) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `in_progress_courses`
--

INSERT INTO `in_progress_courses` (`student_id`, `course_id`) VALUES
  (4, 1),
  (4, 15),
  (4, 18),
  (4, 19),
  (5, 1),
  (5, 14);

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_id` INT(11)      NOT NULL,
  `name`       VARCHAR(48)  NOT NULL,
  `email`      VARCHAR(96)  NOT NULL,
  `gender`     VARCHAR(8)   NOT NULL,
  `password`   VARCHAR(128) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_id`, `name`, `email`, `gender`, `password`) VALUES
  (4, 'samyak', 'samyak@mail.com', 'Male', '$31$16$leZsVwnWHgsE8AXVH4T0yzW5av5IhvzXLvIjfKG4zWM'),
  (5, 'Student', 'student@mail.com', 'Male', '$31$16$oRbazRfwapOX_c8cTIzQTuxpvqA_jkiRKqCLNt6p0tY');

-- --------------------------------------------------------

--
-- Table structure for table `subscriptions`
--

CREATE TABLE `subscriptions` (
  `student_id` INT(11) NOT NULL,
  `teacher_id` INT(11) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `subscriptions`
--

INSERT INTO `subscriptions` (`student_id`, `teacher_id`) VALUES
  (4, 1),
  (4, 4),
  (5, 1);

-- --------------------------------------------------------

--
-- Table structure for table `subtopics`
--

CREATE TABLE `subtopics` (
  `course_id`   INT(11)     NOT NULL,
  `subtopic_id` INT(11)     NOT NULL,
  `name`        VARCHAR(48) NOT NULL,
  `description` VARCHAR(96) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `subtopics`
--

INSERT INTO `subtopics` (`course_id`, `subtopic_id`, `name`, `description`) VALUES
  (1, 1, 'Symmetric Key', 'Old is Gold.'),
  (1, 2, 'Asymmetric cipher', 'Not Symmetric'),
  (2, 3, 'Process Scheduling', 'FCFS'),
  (15, 11, 'Game Theory', 'Sounds Coo!'),
  (17, 12, 'DFA', 'RL\'s'),
  (18, 13, 'Delays', 'timers'),
  (14, 15, 'Java', 'OOPS'),
  (2, 16, 'mutex', 'none'),
  (19, 17, 'Tree', 'Binary Tree'),
  (1, 18, 'AES', 'very old');

-- --------------------------------------------------------

--
-- Table structure for table `teachers`
--

CREATE TABLE `teachers` (
  `teacher_id` INT(11)      NOT NULL,
  `name`       VARCHAR(48)  NOT NULL,
  `email`      VARCHAR(96)  NOT NULL,
  `gender`     VARCHAR(8)   NOT NULL,
  `password`   VARCHAR(128) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `teachers`
--

INSERT INTO `teachers` (`teacher_id`, `name`, `email`, `gender`, `password`) VALUES
  (1, 'samyak', 'samyak@mail.com', 'Male', '$31$16$FCzhobz4j5IVzJvVX_ItgR4EUq_jsrR7oy-McIffvXU'),
  (3, 'SJ', 'samy@samy.com', 'Male', '$31$16$Q6YZ4kX52iq5UK8q11sbCGlf1IsGWBnRRTUXafJ1o5I'),
  (4, 'Teacher', 'teacher@mail.com', 'Male', '$31$16$ACxcMRjdNKBvs5yqOotNBSiXxvTQArBPXuDNC3ba0ZQ');

-- --------------------------------------------------------

--
-- Table structure for table `videos`
--

CREATE TABLE `videos` (
  `subtopic_id` INT(11)      NOT NULL,
  `video_id`    INT(11)      NOT NULL,
  `name`        VARCHAR(96)  NOT NULL,
  `path`        VARCHAR(128) NOT NULL,
  `likes`       INT(11)      NOT NULL DEFAULT '0'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `videos`
--

INSERT INTO `videos` (`subtopic_id`, `video_id`, `name`, `path`, `likes`) VALUES
  (1, 28, 'DES', 'server_tutorials/2017.10.09.22.30.11.mp4', 2),
  (3, 29, 'FCFS', 'server_tutorials/2017.10.09.22.35.44.mp4', 1),
  (13, 37, 'audio support', 'server_tutorials/2017.10.11.12.26.22.mp3', 1),
  (13, 39, 'lec01', 'server_tutorials/2017.10.12.00.57.25.mp4', 1),
  (2, 40, 'public key', 'server_tutorials/2017.10.12.09.29.54.mp4', 0),
  (17, 41, 'presentation', 'server_tutorials/2017.10.12.10.17.31.mp4', 0),
  (1, 42, 'MKV', 'server_tutorials/2017.10.13.21.46.23.mkv', 0);

-- --------------------------------------------------------

--
-- Table structure for table `video_likes`
--

CREATE TABLE `video_likes` (
  `student_id` INT(11) NOT NULL,
  `video_id`   INT(11) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `video_likes`
--

INSERT INTO `video_likes` (`student_id`, `video_id`) VALUES
  (4, 28),
  (4, 37),
  (4, 39),
  (5, 28),
  (5, 29);

-- --------------------------------------------------------

--
-- Table structure for table `video_tags`
--

CREATE TABLE `video_tags` (
  `tag_id`   INT(11)      NOT NULL,
  `video_id` INT(11)      NOT NULL,
  `name`     VARCHAR(256) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `video_tags`
--

INSERT INTO `video_tags` (`tag_id`, `video_id`, `name`) VALUES
  (1, 28, 'imp'),
  (2, 29, 'important'),
  (3, 28, 'tough'),
  (5, 29, 'exam'),
  (6, 39, 'tough'),
  (7, 37, 'very important'),
  (8, 41, 'algo'),
  (9, 41, 'pl'),
  (10, 28, 'tag');

-- --------------------------------------------------------

--
-- Table structure for table `watchlist`
--

CREATE TABLE `watchlist` (
  `student_id` INT(11) NOT NULL,
  `video_id`   INT(11) NOT NULL
)
  ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

--
-- Dumping data for table `watchlist`
--

INSERT INTO `watchlist` (`student_id`, `video_id`) VALUES
  (4, 4),
  (4, 21),
  (4, 29),
  (4, 39),
  (5, 28),
  (5, 29);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`comment_id`);

--
-- Indexes for table `courses`
--
ALTER TABLE `courses`
  ADD PRIMARY KEY (`course_id`),
  ADD KEY `teacher_id` (`teacher_id`);

--
-- Indexes for table `course_ratings`
--
ALTER TABLE `course_ratings`
  ADD UNIQUE KEY `rate_once` (`student_id`, `course_id`);

--
-- Indexes for table `in_progress_courses`
--
ALTER TABLE `in_progress_courses`
  ADD UNIQUE KEY `student_id` (`student_id`, `course_id`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `subscriptions`
--
ALTER TABLE `subscriptions`
  ADD UNIQUE KEY `student_id` (`student_id`, `teacher_id`);

--
-- Indexes for table `subtopics`
--
ALTER TABLE `subtopics`
  ADD PRIMARY KEY (`subtopic_id`);

--
-- Indexes for table `teachers`
--
ALTER TABLE `teachers`
  ADD PRIMARY KEY (`teacher_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `videos`
--
ALTER TABLE `videos`
  ADD PRIMARY KEY (`video_id`),
  ADD UNIQUE KEY `video_id` (`video_id`);

--
-- Indexes for table `video_likes`
--
ALTER TABLE `video_likes`
  ADD UNIQUE KEY `UNIQUE_LIKE` (`student_id`, `video_id`);

--
-- Indexes for table `video_tags`
--
ALTER TABLE `video_tags`
  ADD PRIMARY KEY (`tag_id`),
  ADD KEY `name` (`name`);

--
-- Indexes for table `watchlist`
--
ALTER TABLE `watchlist`
  ADD UNIQUE KEY `WATCHLIST` (`student_id`, `video_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `comments`
--
ALTER TABLE `comments`
  MODIFY `comment_id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 26;
--
-- AUTO_INCREMENT for table `courses`
--
ALTER TABLE `courses`
  MODIFY `course_id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 20;
--
-- AUTO_INCREMENT for table `students`
--
ALTER TABLE `students`
  MODIFY `student_id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 6;
--
-- AUTO_INCREMENT for table `subtopics`
--
ALTER TABLE `subtopics`
  MODIFY `subtopic_id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 19;
--
-- AUTO_INCREMENT for table `teachers`
--
ALTER TABLE `teachers`
  MODIFY `teacher_id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 5;
--
-- AUTO_INCREMENT for table `videos`
--
ALTER TABLE `videos`
  MODIFY `video_id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 43;
--
-- AUTO_INCREMENT for table `video_tags`
--
ALTER TABLE `video_tags`
  MODIFY `tag_id` INT(11) NOT NULL AUTO_INCREMENT,
  AUTO_INCREMENT = 11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
