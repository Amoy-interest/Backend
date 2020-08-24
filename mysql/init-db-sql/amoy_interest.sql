/*
Navicat MySQL Data Transfer

Source Server         : mok
Source Server Version : 80020
Source Host           : localhost:3306
Source Database       : amoy_interest

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2020-08-24 17:14:32
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `blog_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `blog_type` smallint DEFAULT NULL,
  `blog_time` datetime DEFAULT NULL,
  `blog_text` varchar(140) DEFAULT NULL,
  `is_deleted` smallint DEFAULT NULL,
  `check_status` smallint DEFAULT NULL,
  `reply_blog_id` int DEFAULT '0',
  PRIMARY KEY (`blog_id`),
  KEY `FK_Reference_4` (`user_id`),
  KEY `FK_Reference_13` (`reply_blog_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `blog_id` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  `reply_user_id` int DEFAULT NULL,
  `comment_level` smallint DEFAULT NULL,
  `comment_text` varchar(140) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `vote_count` int DEFAULT NULL,
  `is_deleted` smallint DEFAULT NULL,
  `root_comment_id` int DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK_Reference_5` (`blog_id`),
  KEY `FK_Reference_10` (`user_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for blog_count
-- ----------------------------
DROP TABLE IF EXISTS `blog_count`;
CREATE TABLE `blog_count` (
  `blog_id` int NOT NULL,
  `forward_count` int DEFAULT NULL,
  `comment_count` int DEFAULT NULL,
  `vote_count` int DEFAULT NULL,
  `report_count` int DEFAULT NULL,
  PRIMARY KEY (`blog_id`),
  CONSTRAINT `FK_Reference_9` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for blog_image
-- ----------------------------
DROP TABLE IF EXISTS `blog_image`;
CREATE TABLE `blog_image` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `blog_id` int NOT NULL,
  `blog_image` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FK_Reference_15` (`blog_id`),
  CONSTRAINT `FK_Reference_15` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for blog_report
-- ----------------------------
DROP TABLE IF EXISTS `blog_report`;
CREATE TABLE `blog_report` (
  `blog_id` int NOT NULL,
  `user_id` int NOT NULL,
  `report_reason` varchar(20) NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`blog_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for blog_vote
-- ----------------------------
DROP TABLE IF EXISTS `blog_vote`;
CREATE TABLE `blog_vote` (
  `id` int NOT NULL AUTO_INCREMENT,
  `blog_id` int NOT NULL,
  `user_id` int NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '1',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `blog_id` (`blog_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `blog_id` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) NOT NULL,
  `permission` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for sensitive_words
-- ----------------------------
DROP TABLE IF EXISTS `sensitive_words`;
CREATE TABLE `sensitive_words` (
  `keyword` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
  `topic_id` int NOT NULL AUTO_INCREMENT,
  `topic_name` varchar(50) DEFAULT NULL,
  `topic_time` datetime DEFAULT NULL,
  `check_status` smallint DEFAULT NULL,
  `report_count` int DEFAULT NULL,
  `host_id` int DEFAULT NULL,
  `logo_path` varchar(1024) DEFAULT NULL,
  `topic_intro` varchar(140) DEFAULT NULL,
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for topic_blog
-- ----------------------------
DROP TABLE IF EXISTS `topic_blog`;
CREATE TABLE `topic_blog` (
  `topic_id` int NOT NULL,
  `blog_id` int NOT NULL,
  `topic_name` varchar(50) NOT NULL,
  PRIMARY KEY (`topic_id`,`blog_id`),
  KEY `FK_Reference_7` (`blog_id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`topic_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for topic_heat
-- ----------------------------
DROP TABLE IF EXISTS `topic_heat`;
CREATE TABLE `topic_heat` (
  `topic_id` int NOT NULL,
  `heat` int DEFAULT NULL,
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_type` smallint NOT NULL,
  `is_ban` smallint DEFAULT NULL,
  `is_forbidden` smallint DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_ban
-- ----------------------------
DROP TABLE IF EXISTS `user_ban`;
CREATE TABLE `user_ban` (
  `user_id` int NOT NULL,
  `ban_time` datetime DEFAULT NULL,
  `forbidden_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_Reference_12` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_count
-- ----------------------------
DROP TABLE IF EXISTS `user_count`;
CREATE TABLE `user_count` (
  `user_id` int NOT NULL,
  `follow_count` int DEFAULT NULL,
  `fan_count` int DEFAULT NULL,
  `blog_count` int DEFAULT NULL,
  `report_count` int DEFAULT '0',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_follow
-- ----------------------------
DROP TABLE IF EXISTS `user_follow`;
CREATE TABLE `user_follow` (
  `user_id` int NOT NULL,
  `follow_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`follow_id`),
  KEY `FK_Reference_3` (`follow_id`),
  CONSTRAINT `FK_Reference_3` FOREIGN KEY (`follow_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Relationship_3` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `user_id` int NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sex` smallint NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `credits` int DEFAULT NULL,
  `introduction` varchar(50) DEFAULT NULL,
  `avatar_path` varchar(1024) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_Relationship_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_report
-- ----------------------------
DROP TABLE IF EXISTS `user_report`;
CREATE TABLE `user_report` (
  `user_id` int NOT NULL,
  `reporter_id` int NOT NULL,
  `report_reason` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`reporter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'user',
  PRIMARY KEY (`id`),
  KEY `User_id` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
