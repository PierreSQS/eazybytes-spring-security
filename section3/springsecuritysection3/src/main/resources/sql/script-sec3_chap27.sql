DROP DATABASE IF EXISTS eazybytessection3db;
CREATE DATABASE eazybytessection3db;

USE eazybytessection3db;

-- THE TABLE AS PER REQUEST IN SECT3-CHAP25 (JdbUserDetailsManager)!!!!!!!!!
-- BEGIN
CREATE TABLE `users` (
`id` INT NOT NULL AUTO_INCREMENT,
`username` VARCHAR(45) NOT NULL,
`password` VARCHAR(100) NOT NULL,
`enabled` INT NOT NULL,
PRIMARY KEY (`id`));

CREATE TABLE `authorities` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `authority` varchar(45) NOT NULL,
  PRIMARY KEY (`id`));

INSERT IGNORE INTO `users` VALUES (NULL, 'happy', '$2a$10$HsfhAD4D6dlyDkahIyS.JOjyVOqTLkpD0FPDH//DVMlbS9hhKo1bG', '1');#12345
INSERT IGNORE INTO `authorities` VALUES (NULL, 'happy', 'write');
-- END SECT3-CHAP25 (JdbUserDetailsManager)

-- THE ADDITIVE TABLE AS PER REQUEST IN SECT3-CHAP27 (Custom UserDetailsManager)!!!!!!!!!
CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(100) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);

INSERT INTO `customer` (`email`, `pwd`, `role`)
 VALUES ('johndoe@example.com', '$2a$10$eoZ9eJcnNYCKs1fxShfsvO6uVIUJ14a5vDi/JQTXrm8AkCy9t0eGy', 'admin');#54321

