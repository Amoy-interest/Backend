/*
Navicat MySQL Data Transfer

Source Server         : dd
Source Server Version : 80020
Source Host           : localhost:3306
Source Database       : amoy_interest

Target Server Type    : MYSQL
Target Server Version : 80020
File Encoding         : 65001

Date: 2020-07-16 08:32:39
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
  `topic_id` int DEFAULT NULL,
  `reply_blog_id` int DEFAULT NULL,
  PRIMARY KEY (`blog_id`),
  KEY `FK_Reference_4` (`user_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS `blog_comment`;
CREATE TABLE `blog_comment` (
  `comment_id` int NOT NULL,
  `blog_id` int DEFAULT NULL,
  `nickname` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `reply_comment_nickname` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `comment_level` smallint DEFAULT NULL,
  `comment_text` varchar(140) DEFAULT NULL,
  `comment_time` datetime DEFAULT NULL,
  `vote_count` int DEFAULT NULL,
  `is_deleted` smallint DEFAULT NULL,
  `root_comment_id` int DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FK_Reference_5` (`blog_id`),
  KEY `FK_Reference_10` (`nickname`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`nickname`) REFERENCES `user` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
  `blog_id` int NOT NULL,
  `blog_image` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`blog_id`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`) ON DELETE CASCADE ON UPDATE CASCADE
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
  PRIMARY KEY (`topic_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for topic_blog
-- ----------------------------
DROP TABLE IF EXISTS `topic_blog`;
CREATE TABLE `topic_blog` (
  `topic_id` int NOT NULL,
  `blog_id` int NOT NULL,
  PRIMARY KEY (`topic_id`,`blog_id`),
  KEY `FK_Reference_7` (`blog_id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`topic_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`blog_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(15) NOT NULL,
  `password` varchar(15) NOT NULL,
  `user_type` smallint NOT NULL,
  `user_status` smallint DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for user_count
-- ----------------------------
DROP TABLE IF EXISTS `user_count`;
CREATE TABLE `user_count` (
  `user_id` int NOT NULL,
  `follow_count` int DEFAULT NULL,
  `fan_count` int DEFAULT NULL,
  `blog_count` int DEFAULT NULL,
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
  `email` varchar(20) NOT NULL,
  `sex` smallint NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `credits` int DEFAULT NULL,
  `introduction` varchar(50) DEFAULT NULL,
  `avatar_path` varchar(1024) DEFAULT NULL,
  `nickname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_Relationship_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
