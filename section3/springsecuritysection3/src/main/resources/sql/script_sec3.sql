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

INSERT IGNORE INTO `users` VALUES (NULL, 'happy', '$2a$10$HsfhAD4D6dlyDkahIyS.JOjyVOqTLkpD0FPDH//DVMlbS9hhKo1bG', '1');
INSERT IGNORE INTO `authorities` VALUES (NULL, 'happy', 'write');
-- END SECT3-CHAP25 (JdbUserDetailsManager)

