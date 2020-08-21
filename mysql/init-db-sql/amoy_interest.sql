/*
 Navicat Premium Data Transfer

 Source Server         : server_mysql
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : 120.55.194.144:3306
 Source Schema         : amoy

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 15/08/2020 21:00:24
*/
-- create user canal identified by 'canal';
-- grant select,replication slave,replication client on *.* to 'canal'@'%';
-- flush privileges;
DROP DATABASE IF EXISTS `amoy`;
CREATE DATABASE `amoy`;
use `amoy`;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
  KEY `FK_Reference_13` (`reply_blog_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of blog
-- ----------------------------
BEGIN;
INSERT INTO `blog` VALUES (1, 1, 0, '2020-07-09 00:00:00', '横眉冷对千夫指，俯首甘为孺子牛', 1, 0, 1, -1);
INSERT INTO `blog` VALUES (2, 1, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 2, 1, -1);
INSERT INTO `blog` VALUES (3, 1, 0, '2020-07-09 00:00:00', '心事浩茫连广宇，于无声处听惊雷', 0, 1, 1, -1);
INSERT INTO `blog` VALUES (4, 1, 0, '2020-07-09 00:00:00', '血沃中原肥劲草，寒凝大地发春华', 0, 2, 1, -1);
INSERT INTO `blog` VALUES (5, 2, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 2, 1, -1);
INSERT INTO `blog` VALUES (6, 3, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 0, 2, -1);
INSERT INTO `blog` VALUES (7, 4, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 0, 2, -1);
INSERT INTO `blog` VALUES (8, 5, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 0, 2, -1);
INSERT INTO `blog` VALUES (9, 6, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 0, 2, -1);
INSERT INTO `blog` VALUES (10, 7, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 0, 2, -1);
INSERT INTO `blog` VALUES (11, 8, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 1, 3, -1);
INSERT INTO `blog` VALUES (12, 9, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 0, 3, -1);
INSERT INTO `blog` VALUES (13, 10, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 0, 3, -1);
INSERT INTO `blog` VALUES (14, 11, 0, '2020-07-09 00:00:00', '寄意寒星荃不察，我以我血荐轩辕', 0, 0, 3, -1);
INSERT INTO `blog` VALUES (15, 11, 0, '2020-07-24 00:00:00', '这部分是推荐blog', 0, 0, 4, -1);
INSERT INTO `blog` VALUES (16, 11, 0, '2020-07-24 00:00:00', '这部分是推荐blog', 0, 0, 4, -1);
INSERT INTO `blog` VALUES (17, 11, 0, '2020-07-24 00:00:00', '这部分是推荐blog', 0, 0, 4, -1);
INSERT INTO `blog` VALUES (18, 11, 0, '2020-07-24 00:00:00', '这部分是推荐blog', 0, 0, 4, -1);
INSERT INTO `blog` VALUES (19, 11, 0, '2020-07-24 00:00:00', '这部分是推荐blog', 0, 0, 4, -1);
INSERT INTO `blog` VALUES (20, 11, 0, '2020-07-24 00:00:00', '这部分是推荐blog', 0, 0, 1, -1);
INSERT INTO `blog` VALUES (21, 11, 0, '2020-07-24 00:00:00', '这部分是推荐blog', 0, 0, 1, -1);
INSERT INTO `blog` VALUES (22, 11, 0, '2020-07-24 00:00:00', '这部分是推荐blog', 0, 0, 1, -1);
INSERT INTO `blog` VALUES (23, 11, 0, '2020-07-24 00:00:00', '这部分是推荐blog', 0, 0, 1, -1);
INSERT INTO `blog` VALUES (24, 11, 0, '2020-07-24 00:00:00', '这部分是推荐blog', 0, 0, 1, -1);
INSERT INTO `blog` VALUES (27, 1, 0, '2020-07-24 09:39:58', 'test', 1, 0, 0, NULL);
INSERT INTO `blog` VALUES (28, 1, 1, '2020-07-24 09:53:15', '测试转发', 1, 0, 0, 16);
INSERT INTO `blog` VALUES (29, 21, 0, '2020-07-24 13:51:52', '测试新注册用户能不发博文', 0, 0, 0, NULL);
INSERT INTO `blog` VALUES (30, 21, 1, '2020-07-24 14:02:17', '再试下转发', 0, 0, 0, 29);
INSERT INTO `blog` VALUES (31, 1, 0, '2020-07-24 20:48:39', '测试一下下下', 0, 0, 0, NULL);
INSERT INTO `blog` VALUES (32, 1, 0, '2020-07-24 21:03:26', 'test3', 0, 0, 0, NULL);
INSERT INTO `blog` VALUES (33, 1, 1, '2020-07-24 21:03:39', 'test4', 0, 0, 0, 32);
INSERT INTO `blog` VALUES (34, 23, 0, '2020-07-24 22:41:54', 'test1', 0, 0, 0, NULL);
INSERT INTO `blog` VALUES (35, 23, 0, '2020-07-24 22:41:59', 'test2', 0, 0, 0, NULL);
INSERT INTO `blog` VALUES (36, 23, 1, '2020-07-24 22:45:52', 'test', 0, 0, 0, 2);
INSERT INTO `blog` VALUES (37, 23, 0, '2020-07-24 22:53:38', 'test', 0, 0, 0, NULL);
INSERT INTO `blog` VALUES (38, 24, 0, '2020-07-24 22:56:26', 'test222222222\n', 0, 0, 0, NULL);
INSERT INTO `blog` VALUES (39, 12, 1, '2020-07-24 23:31:54', 'test Reddit', 0, 0, 0, 38);
INSERT INTO `blog` VALUES (40, 12, 0, '2020-07-24 23:32:39', 'test', 0, 0, 0, NULL);
INSERT INTO `blog` VALUES (41, 12, 1, '2020-07-24 23:32:54', 'test', 0, 0, 0, 11);
INSERT INTO `blog` VALUES (42, 12, 1, '2020-07-24 23:34:05', 'test', 0, 0, 0, 11);
INSERT INTO `blog` VALUES (43, 12, 1, '2020-07-24 23:37:39', 'test', 0, 0, 0, 11);
INSERT INTO `blog` VALUES (44, 1, 0, '2020-07-25 09:21:15', 'test', 0, 0, 0, NULL);
INSERT INTO `blog` VALUES (45, 25, 0, '2020-07-25 09:30:02', 'testtest', 1, 0, 0, NULL);
INSERT INTO `blog` VALUES (46, 25, 1, '2020-07-25 09:31:25', 'test2', 0, 0, 0, 44);
INSERT INTO `blog` VALUES (47, 26, 0, '2020-07-25 10:03:06', 'testtest', 1, 0, 0, NULL);
INSERT INTO `blog` VALUES (48, 26, 1, '2020-07-25 10:04:42', 'test4', 0, 0, 0, 11);
INSERT INTO `blog` VALUES (49, 1, 0, '2020-08-11 07:57:27', 'hello', 0, 0, 8, 0);
INSERT INTO `blog` VALUES (50, 27, 0, '2020-08-11 08:52:07', 'hello', 0, 0, 3, 0);
INSERT INTO `blog` VALUES (51, 27, 0, '2020-08-11 16:59:12', '早上好', 0, 0, 3, 0);
INSERT INTO `blog` VALUES (52, 27, 0, '2020-08-11 17:00:02', '早上好', 0, 0, 3, 0);
INSERT INTO `blog` VALUES (53, 28, 0, '2020-08-12 08:14:11', '我试试看发个博文哦', 0, 0, 8, 0);
INSERT INTO `blog` VALUES (54, 28, 0, '2020-08-12 09:08:44', '传照片试试', 0, 0, 8, 0);
INSERT INTO `blog` VALUES (55, 28, 0, '2020-08-12 09:22:31', '我高考考完2年了！', 0, 0, 3, 0);
INSERT INTO `blog` VALUES (56, 28, 0, '2020-08-12 09:31:44', '我要爆炸啦', 0, 0, 8, 0);
INSERT INTO `blog` VALUES (57, 28, 0, '2020-08-12 09:32:31', '我喜欢火药的味道', 0, 0, 8, 0);
INSERT INTO `blog` VALUES (58, 29, 0, '2020-08-12 09:53:44', '今天读了《To The Lighthouse》，开心~~~', 0, 0, 9, 0);
INSERT INTO `blog` VALUES (59, 30, 0, '2020-08-12 09:59:23', '守护全世界最好的学妹', 0, 0, 8, 0);
INSERT INTO `blog` VALUES (60, 30, 0, '2020-08-12 10:02:06', '我考上清华了', 0, 0, 6, 0);
INSERT INTO `blog` VALUES (61, 30, 1, '2020-08-12 10:04:28', '下午好', 0, 0, 3, 52);
INSERT INTO `blog` VALUES (62, 29, 1, '2020-08-12 10:18:08', '晚上好', 0, 0, 3, 52);
INSERT INTO `blog` VALUES (63, 27, 0, '2020-08-12 13:38:18', '又是没有读书的一天', 0, 0, 9, 0);
INSERT INTO `blog` VALUES (64, 27, 0, '2020-08-12 14:07:07', '淘兴趣真有趣', 0, 0, 10, 0);
INSERT INTO `blog` VALUES (65, 27, 0, '2020-08-12 14:09:50', '今儿个真高兴', 0, 0, 10, 0);
INSERT INTO `blog` VALUES (66, 28, 0, '2020-08-13 02:13:17', '炸药真好吃', 0, 0, 8, 0);
INSERT INTO `blog` VALUES (67, 28, 0, '2020-08-13 03:38:49', '我不喜欢塑料袋', 0, 0, 7, 0);
INSERT INTO `blog` VALUES (68, 28, 1, '2020-08-13 03:39:04', '我也不喜欢', 0, 0, 7, 67);
INSERT INTO `blog` VALUES (69, 28, 1, '2020-08-13 03:41:46', '我更不喜欢', 0, 0, 7, 67);
INSERT INTO `blog` VALUES (70, 28, 1, '2020-08-13 03:43:48', '我考上北大了', 0, 0, 6, 60);
INSERT INTO `blog` VALUES (71, 28, 0, '2020-08-13 03:46:12', '哀其不幸，怒其不争', 0, 0, 1, 0);
INSERT INTO `blog` VALUES (72, 28, 1, '2020-08-13 05:37:10', '呃呃呃', 0, 0, 8, 66);
INSERT INTO `blog` VALUES (73, 28, 1, '2020-08-13 05:40:41', '啦啦啦', 0, 0, 8, 66);
INSERT INTO `blog` VALUES (74, 28, 0, '2020-08-14 05:41:26', 'Joey doesn\'t share food!', 0, 0, 11, 0);
INSERT INTO `blog` VALUES (75, 28, 0, '2020-08-14 05:46:29', 'I love friends!', 0, 0, 11, 0);
INSERT INTO `blog` VALUES (76, 28, 0, '2020-08-14 05:48:57', '❤️❤️❤️', 0, 0, 12, 0);
INSERT INTO `blog` VALUES (77, 34, 0, '2020-08-14 12:08:23', 'How you doing~', 0, 0, 8, 0);
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of blog_comment
-- ----------------------------
BEGIN;
INSERT INTO `blog_comment` VALUES (1, 1, 2, -1, 1, '我是没有感情的水军', '2020-09-10 00:00:00', 13, 0, 1);
INSERT INTO `blog_comment` VALUES (2, 2, 2, -1, 1, '我是没有感情的水军', '2020-09-10 00:00:00', 10, 0, 2);
INSERT INTO `blog_comment` VALUES (3, 3, 2, -1, 1, '我是没有感情的水军', '2020-09-10 00:00:00', 10, 0, 3);
INSERT INTO `blog_comment` VALUES (4, 4, 2, -1, 1, '我是没有感情的水军', '2020-09-10 00:00:00', 10, 0, 4);
INSERT INTO `blog_comment` VALUES (5, 5, 2, -1, 1, '我是没有感情的水军', '2020-09-10 00:00:00', 10, 0, 5);
INSERT INTO `blog_comment` VALUES (6, 6, 2, -1, 1, '我是没有感情的水军', '2020-09-10 00:00:00', 10, 0, 6);
INSERT INTO `blog_comment` VALUES (7, 7, 2, -1, 1, '我是没有感情的水军', '2020-09-10 00:00:00', 10, 0, 7);
INSERT INTO `blog_comment` VALUES (8, 8, 2, -1, 1, '我是没有感情的水军', '2020-09-10 00:00:00', 10, 0, 8);
INSERT INTO `blog_comment` VALUES (9, 9, 2, -1, 1, '我是没有感情的水军', '2020-09-10 00:00:00', 10, 0, 9);
INSERT INTO `blog_comment` VALUES (10, 10, 2, -1, 1, '我是没有感情的水军', '2020-09-10 00:00:00', 10, 0, 10);
INSERT INTO `blog_comment` VALUES (11, 1, 1, 1, 1, '我来水拉', '2020-07-24 09:44:58', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (12, 28, 1, 1, 1, '测试最新评论', '2020-07-24 10:34:28', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (13, 5, 21, 2, 1, '我继续水', '2020-07-24 13:53:52', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (14, 29, 21, 21, 1, 'test', '2020-07-24 13:54:13', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (15, 29, 21, 21, 1, 'test', '2020-07-24 14:00:50', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (16, 29, 21, 21, 1, 'test', '2020-07-24 14:00:59', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (17, 30, 21, 21, 1, 'test', '2020-07-24 14:02:25', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (18, 1, 1, 1, 1, '测试评论', '2020-07-24 17:01:29', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (19, 31, 1, 1, 1, 'test', '2020-07-24 20:48:59', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (20, 32, 1, 1, 1, 'test', '2020-07-24 21:03:31', 0, 1, 0);
INSERT INTO `blog_comment` VALUES (21, 33, 1, 1, 1, 'test', '2020-07-24 21:03:45', 0, 1, 0);
INSERT INTO `blog_comment` VALUES (22, 32, 1, 1, 1, 'test', '2020-07-24 21:47:14', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (23, 32, 1, 1, 1, 'test', '2020-07-24 22:19:34', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (24, 33, 1, 1, 1, 'test', '2020-07-24 22:29:54', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (25, 33, 1, 1, 1, 'ttest2', '2020-07-24 22:33:59', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (26, 33, 1, 1, 2, 'test22', '2020-07-24 22:34:07', 0, 0, 25);
INSERT INTO `blog_comment` VALUES (27, 33, 1, 1, 2, 'test222', '2020-07-24 22:39:15', 0, 0, 24);
INSERT INTO `blog_comment` VALUES (28, 33, 1, 1, 2, 'test22222', '2020-07-24 22:39:29', 0, 0, 27);
INSERT INTO `blog_comment` VALUES (29, 36, 23, 23, 1, 'test', '2020-07-24 22:46:40', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (30, 11, 12, 8, 1, 'test', '2020-07-24 23:41:09', 1, 0, 0);
INSERT INTO `blog_comment` VALUES (31, 11, 12, 12, 2, 'test', '2020-07-24 23:41:27', 0, 0, 30);
INSERT INTO `blog_comment` VALUES (32, 44, 25, 1, 1, 'test', '2020-07-25 09:30:51', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (33, 44, 25, 25, 2, 'test', '2020-07-25 09:30:56', 0, 0, 32);
INSERT INTO `blog_comment` VALUES (34, 44, 25, 25, 2, 'test', '2020-07-25 09:31:06', 0, 0, 33);
INSERT INTO `blog_comment` VALUES (35, 11, 26, 8, 1, 'test2', '2020-07-25 10:04:34', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (36, 52, 27, 27, 1, 'hello', '2020-08-11 18:11:40', 1, 0, 0);
INSERT INTO `blog_comment` VALUES (37, 58, 27, 29, 1, '哈哈哈哈哈', '2020-08-12 09:59:28', 1, 0, 0);
INSERT INTO `blog_comment` VALUES (38, 58, 27, 27, 2, '嘿嘿', '2020-08-12 09:59:34', 1, 0, 37);
INSERT INTO `blog_comment` VALUES (39, 58, 27, 27, 2, '嘿嘿', '2020-08-12 09:59:54', 1, 0, 37);
INSERT INTO `blog_comment` VALUES (40, 62, 29, 29, 1, '套娃8错', '2020-08-12 10:18:33', 1, 0, 0);
INSERT INTO `blog_comment` VALUES (41, 62, 29, 29, 2, '娃8错', '2020-08-12 10:20:02', 1, 0, 40);
INSERT INTO `blog_comment` VALUES (42, 62, 29, 29, 2, '8错', '2020-08-12 10:20:08', 0, 1, 40);
INSERT INTO `blog_comment` VALUES (43, 62, 29, 29, 2, '8错', '2020-08-12 10:20:28', 1, 0, 40);
INSERT INTO `blog_comment` VALUES (44, 62, 29, 29, 2, '错', '2020-08-12 10:20:33', 1, 0, 40);
INSERT INTO `blog_comment` VALUES (45, 62, 27, 29, 1, '嘻嘻嘻嘻', '2020-08-12 10:28:25', 1, 1, 0);
INSERT INTO `blog_comment` VALUES (46, 62, 28, 29, 2, '傻孩子', '2020-08-12 12:35:57', 0, 1, 40);
INSERT INTO `blog_comment` VALUES (47, 58, 28, 29, 1, '吼吼吼', '2020-08-12 12:37:46', 1, 0, 0);
INSERT INTO `blog_comment` VALUES (48, 62, 28, 29, 2, '傻傻傻孩子', '2020-08-12 12:38:46', 0, 0, 40);
INSERT INTO `blog_comment` VALUES (49, 59, 28, 30, 1, '你是哪位傻子', '2020-08-12 12:40:01', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (50, 60, 28, 30, 1, '厉害呦', '2020-08-12 12:40:22', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (51, 61, 27, 30, 1, 'hello', '2020-08-12 13:53:20', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (52, 63, 28, 27, 1, '又是码字的一天', '2020-08-12 13:56:53', 1, 0, 0);
INSERT INTO `blog_comment` VALUES (53, 62, 27, 29, 2, '那肯定', '2020-08-12 14:00:45', 0, 0, 40);
INSERT INTO `blog_comment` VALUES (54, 63, 27, 28, 2, '太难了', '2020-08-12 14:02:39', 0, 0, 52);
INSERT INTO `blog_comment` VALUES (55, 77, 28, 34, 1, '嘻嘻嘻', '2020-08-15 03:24:46', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (56, 77, 28, 34, 1, '你咋那么帅', '2020-08-15 03:25:21', 0, 0, 0);
INSERT INTO `blog_comment` VALUES (57, 77, 27, 28, 2, '？？？？？', '2020-08-15 03:37:08', 0, 0, 56);
COMMIT;

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
-- Records of blog_count
-- ----------------------------
BEGIN;
INSERT INTO `blog_count` VALUES (1, 10, 10, 13, 10);
INSERT INTO `blog_count` VALUES (2, 21, 20, 20, 20);
INSERT INTO `blog_count` VALUES (3, 30, 30, 30, 30);
INSERT INTO `blog_count` VALUES (4, 40, 40, 40, 40);
INSERT INTO `blog_count` VALUES (5, 50, 50, 50, 50);
INSERT INTO `blog_count` VALUES (6, 60, 60, 60, 60);
INSERT INTO `blog_count` VALUES (7, 70, 70, 70, 70);
INSERT INTO `blog_count` VALUES (8, 24, 33, 70, 19);
INSERT INTO `blog_count` VALUES (9, 80, 45, 70, 4);
INSERT INTO `blog_count` VALUES (10, 23, 79, 70, 0);
INSERT INTO `blog_count` VALUES (11, 74, 63, 72, 71);
INSERT INTO `blog_count` VALUES (12, 43, 72, 70, 4);
INSERT INTO `blog_count` VALUES (13, 223, 79, 70, 13);
INSERT INTO `blog_count` VALUES (14, 23, 849, 70, 54);
INSERT INTO `blog_count` VALUES (15, 1, 0, 0, 0);
INSERT INTO `blog_count` VALUES (16, 1, 1, 1, 0);
INSERT INTO `blog_count` VALUES (17, 1, 0, 0, 1);
INSERT INTO `blog_count` VALUES (18, 0, 1, 1, 1);
INSERT INTO `blog_count` VALUES (19, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (20, 1, 1, 0, 1);
INSERT INTO `blog_count` VALUES (21, 1, 1, 1, 1);
INSERT INTO `blog_count` VALUES (22, 0, 0, 0, 1);
INSERT INTO `blog_count` VALUES (23, 1, 0, 1, 0);
INSERT INTO `blog_count` VALUES (24, 1, 1, 0, 0);
INSERT INTO `blog_count` VALUES (27, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (28, 0, 0, 1, 0);
INSERT INTO `blog_count` VALUES (29, 0, 0, 2, 0);
INSERT INTO `blog_count` VALUES (30, 0, 0, 1, 0);
INSERT INTO `blog_count` VALUES (31, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (32, 1, 3, 0, 0);
INSERT INTO `blog_count` VALUES (33, 0, 6, 0, 0);
INSERT INTO `blog_count` VALUES (34, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (35, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (36, 0, 1, 0, 0);
INSERT INTO `blog_count` VALUES (37, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (38, 1, 0, 0, 0);
INSERT INTO `blog_count` VALUES (39, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (40, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (41, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (42, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (43, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (44, 1, 3, 1, 0);
INSERT INTO `blog_count` VALUES (45, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (46, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (47, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (48, 0, 0, 0, 2);
INSERT INTO `blog_count` VALUES (49, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (50, 0, 0, 1, 0);
INSERT INTO `blog_count` VALUES (51, 0, 0, 1, 0);
INSERT INTO `blog_count` VALUES (52, 2, 1, 2, 0);
INSERT INTO `blog_count` VALUES (53, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (54, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (55, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (56, 0, 0, 0, 1);
INSERT INTO `blog_count` VALUES (57, 0, 0, 1, 0);
INSERT INTO `blog_count` VALUES (58, 0, 4, 3, 0);
INSERT INTO `blog_count` VALUES (59, 0, 1, 1, 0);
INSERT INTO `blog_count` VALUES (60, 1, 1, 0, 0);
INSERT INTO `blog_count` VALUES (61, 0, 1, 0, 1);
INSERT INTO `blog_count` VALUES (62, 0, 9, 2, 0);
INSERT INTO `blog_count` VALUES (63, 0, 2, 1, 0);
INSERT INTO `blog_count` VALUES (64, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (65, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (66, 2, 0, 0, 0);
INSERT INTO `blog_count` VALUES (67, 2, 0, 0, 0);
INSERT INTO `blog_count` VALUES (68, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (69, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (70, 0, 0, 1, 0);
INSERT INTO `blog_count` VALUES (71, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (72, 0, 0, 0, 0);
INSERT INTO `blog_count` VALUES (73, 0, 0, 1, 0);
INSERT INTO `blog_count` VALUES (74, 0, 0, 2, 0);
INSERT INTO `blog_count` VALUES (75, 0, 0, 1, 0);
INSERT INTO `blog_count` VALUES (76, 0, 0, 3, 0);
INSERT INTO `blog_count` VALUES (77, 0, 3, 1, 0);
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of blog_image
-- ----------------------------
BEGIN;
INSERT INTO `blog_image` VALUES (1, 1, 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg');
INSERT INTO `blog_image` VALUES (2, 2, 'https://cdn.pixabay.com/photo/2018/01/15/11/36/shiba-inu-3083761__340.jpg');
INSERT INTO `blog_image` VALUES (3, 3, 'https://cdn.pixabay.com/photo/2017/04/19/21/29/dog-2243682__340.jpg');
INSERT INTO `blog_image` VALUES (4, 4, 'https://media.istockphoto.com/photos/beautiful-young-black-and-tan-shiba-inu-dog-sitting-outdoor-in-grass-picture-id1047146596?b=1&amp;k=6&amp;m=1047146596&amp;s=170667a&amp;w=0&amp;h=U-Nt72Tg5W44jyjZStzbSJG1SeWEXqv55QP3BuRg7cw=');
INSERT INTO `blog_image` VALUES (5, 5, 'https://cdn.pixabay.com/photo/2020/07/03/11/41/puppy-5366113__340.jpg');
INSERT INTO `blog_image` VALUES (6, 6, 'https://cdn.pixabay.com/photo/2019/08/04/06/29/pets-4383141__480.jpg');
INSERT INTO `blog_image` VALUES (7, 7, 'https://cdn.pixabay.com/photo/2019/06/10/18/45/welsh-corgi-pembroke-4264957__340.jpg');
INSERT INTO `blog_image` VALUES (8, 8, 'https://cdn.pixabay.com/photo/2015/01/11/21/30/cats-596782__340.jpg');
INSERT INTO `blog_image` VALUES (9, 9, 'https://cdn.pixabay.com/photo/2015/05/28/09/43/cat-787798__340.jpg');
INSERT INTO `blog_image` VALUES (10, 10, 'https://cdn.pixabay.com/photo/2017/10/22/16/32/swiss-white-shepherd-2878307__340.jpg');
INSERT INTO `blog_image` VALUES (11, 11, 'https://cdn.pixabay.com/photo/2020/04/26/15/35/beach-5095832__340.jpg');
INSERT INTO `blog_image` VALUES (12, 12, 'https://cdn.pixabay.com/photo/2017/04/20/13/52/mountain-2245788__340.jpg');
INSERT INTO `blog_image` VALUES (13, 13, 'https://cdn.pixabay.com/photo/2015/08/19/08/23/mountaineering-895659__340.jpg');
INSERT INTO `blog_image` VALUES (14, 14, 'https://cdn.pixabay.com/photo/2017/03/02/16/59/winter-2111833__340.jpg');
INSERT INTO `blog_image` VALUES (15, 15, 'https://cdn.pixabay.com/photo/2016/09/08/22/43/books-1655783__340.jpg');
INSERT INTO `blog_image` VALUES (16, 16, 'https://cdn.pixabay.com/photo/2015/11/19/21/11/knowledge-1052011__340.jpg');
INSERT INTO `blog_image` VALUES (17, 17, 'https://cdn.pixabay.com/photo/2015/07/31/11/45/library-869061__340.jpg');
INSERT INTO `blog_image` VALUES (18, 18, 'https://cdn.pixabay.com/photo/2014/05/02/23/52/castle-336498__340.jpg');
INSERT INTO `blog_image` VALUES (19, 19, 'https://cdn.pixabay.com/photo/2014/09/07/21/50/library-438389__340.jpg');
INSERT INTO `blog_image` VALUES (20, 20, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (21, 21, 'https://cdn.pixabay.com/photo/2015/01/09/11/11/seminar-594125__340.jpg');
INSERT INTO `blog_image` VALUES (22, 22, 'https://cdn.pixabay.com/photo/2017/05/01/18/40/school-2276269__340.jpg');
INSERT INTO `blog_image` VALUES (23, 23, 'https://cdn.pixabay.com/photo/2015/10/29/09/46/books-1012088__340.jpg');
INSERT INTO `blog_image` VALUES (24, 24, 'https://cdn.pixabay.com/photo/2016/03/26/13/12/pencils-1280558__340.jpg');
INSERT INTO `blog_image` VALUES (26, 1, 'https://cdn.pixabay.com/photo/2016/03/26/13/12/pencils-1280558__340.jpg');
INSERT INTO `blog_image` VALUES (27, 1, 'https://cdn.pixabay.com/photo/2015/01/09/11/11/seminar-594125__340.jpg');
INSERT INTO `blog_image` VALUES (28, 1, 'https://cdn.pixabay.com/photo/2014/05/02/23/52/castle-336498__340.jpg');
INSERT INTO `blog_image` VALUES (29, 1, 'https://cdn.pixabay.com/photo/2015/01/11/21/30/cats-596782__340.jpg');
INSERT INTO `blog_image` VALUES (30, 1, 'https://cdn.pixabay.com/photo/2015/08/19/08/23/mountaineering-895659__340.jpg');
INSERT INTO `blog_image` VALUES (31, 1, 'https://cdn.pixabay.com/photo/2015/08/19/08/23/mountaineering-895659__340.jpg');
INSERT INTO `blog_image` VALUES (32, 1, 'https://cdn.pixabay.com/photo/2018/01/15/11/36/shiba-inu-3083761__340.jpg');
INSERT INTO `blog_image` VALUES (33, 1, 'https://cdn.pixabay.com/photo/2018/01/15/11/36/shiba-inu-3083761__340.jpg');
INSERT INTO `blog_image` VALUES (34, 2, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (35, 2, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (36, 2, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (37, 2, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (38, 2, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (39, 3, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (40, 3, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (41, 3, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (42, 4, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (43, 5, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (44, 6, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (47, 7, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (48, 8, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (49, 9, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (50, 10, 'https://cdn.pixabay.com/photo/2020/04/23/21/08/architecture-5084075__340.jpg');
INSERT INTO `blog_image` VALUES (51, 49, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200811/1597132646031-post4.png');
INSERT INTO `blog_image` VALUES (52, 50, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200811/1597135926038-post4.png');
INSERT INTO `blog_image` VALUES (53, 52, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597165200186-fun.png');
INSERT INTO `blog_image` VALUES (54, 52, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597165200981-post3.png');
INSERT INTO `blog_image` VALUES (55, 52, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597165201162-post4.png');
INSERT INTO `blog_image` VALUES (56, 52, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597165201335-yummy.png');
INSERT INTO `blog_image` VALUES (57, 54, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597223292289-annie-spratt-dSZQC7vVf5M-unsplash.jpg');
INSERT INTO `blog_image` VALUES (58, 55, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597224138616-nick-fewings-9IKod3Zgg6g-unsplash.jpg');
INSERT INTO `blog_image` VALUES (59, 58, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597226025161-1.jpg');
INSERT INTO `blog_image` VALUES (60, 58, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597226025385-6.jpg');
INSERT INTO `blog_image` VALUES (61, 59, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597226358314-4DAB3A16FFA79A70F352177600E552EC.jpg');
INSERT INTO `blog_image` VALUES (62, 64, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597241226116-background5.png');
INSERT INTO `blog_image` VALUES (63, 71, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200813/1597290371257-372e66a2a8556ce20c318c96ef58e50c.jpeg');
INSERT INTO `blog_image` VALUES (64, 74, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200814/1597383685401-5.gif');
INSERT INTO `blog_image` VALUES (65, 75, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200814/1597383988754-6.jpg');
INSERT INTO `blog_image` VALUES (66, 76, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200814/1597384136405-1f86a718c4df4e9533d25792e0ef3d11.jpeg');
INSERT INTO `blog_image` VALUES (67, 76, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200814/1597384136671-92d940ad6d13f787462df64f8a66fc13.jpg');
COMMIT;

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
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `blog` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of blog_vote
-- ----------------------------
BEGIN;
INSERT INTO `blog_vote` VALUES (3, 52, 27, 1, '2020-08-11 18:47:47', '2020-08-11 18:47:47');
INSERT INTO `blog_vote` VALUES (4, 59, 27, 1, '2020-08-12 11:06:57', '2020-08-12 11:06:57');
INSERT INTO `blog_vote` VALUES (5, 52, 30, 1, '2020-08-12 11:06:57', '2020-08-12 11:06:57');
INSERT INTO `blog_vote` VALUES (6, 51, 30, 1, '2020-08-12 11:06:57', '2020-08-12 11:06:57');
INSERT INTO `blog_vote` VALUES (7, 50, 30, 1, '2020-08-12 11:06:57', '2020-08-12 11:06:57');
INSERT INTO `blog_vote` VALUES (8, 58, 29, 1, '2020-08-12 11:06:57', '2020-08-12 11:06:57');
INSERT INTO `blog_vote` VALUES (9, 62, 29, 1, '2020-08-12 11:06:57', '2020-08-12 11:06:57');
INSERT INTO `blog_vote` VALUES (10, 62, 28, 1, '2020-08-12 13:06:57', '2020-08-12 13:06:57');
INSERT INTO `blog_vote` VALUES (11, 58, 28, 1, '2020-08-12 13:06:57', '2020-08-12 13:06:57');
INSERT INTO `blog_vote` VALUES (12, 58, 27, 1, '2020-08-12 15:06:57', '2020-08-12 15:06:57');
INSERT INTO `blog_vote` VALUES (13, 63, 28, 1, '2020-08-12 15:06:57', '2020-08-12 15:06:57');
INSERT INTO `blog_vote` VALUES (14, 57, 28, 1, '2020-08-12 15:06:57', '2020-08-12 15:06:57');
INSERT INTO `blog_vote` VALUES (15, 70, 28, 1, '2020-08-13 07:06:57', '2020-08-13 07:06:57');
INSERT INTO `blog_vote` VALUES (20, 75, 34, 1, '2020-08-14 12:48:38', '2020-08-14 12:48:38');
INSERT INTO `blog_vote` VALUES (21, 74, 34, 1, '2020-08-14 12:48:38', '2020-08-14 12:48:38');
INSERT INTO `blog_vote` VALUES (23, 77, 28, 1, '2020-08-15 10:48:38', '2020-08-15 10:48:38');
COMMIT;

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
-- Records of role_permission
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sensitive_words
-- ----------------------------
DROP TABLE IF EXISTS `sensitive_words`;
CREATE TABLE `sensitive_words` (
  `keyword` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`keyword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of sensitive_words
-- ----------------------------
BEGIN;
INSERT INTO `sensitive_words` VALUES ('﻿C3炸药');
INSERT INTO `sensitive_words` VALUES ('fd');
INSERT INTO `sensitive_words` VALUES ('te');
INSERT INTO `sensitive_words` VALUES ('tes');
INSERT INTO `sensitive_words` VALUES ('test11');
INSERT INTO `sensitive_words` VALUES ('test8');
INSERT INTO `sensitive_words` VALUES ('test9');
INSERT INTO `sensitive_words` VALUES ('testtest11');
INSERT INTO `sensitive_words` VALUES ('tnt炸药配方');
INSERT INTO `sensitive_words` VALUES ('﻿人体炸弹');
INSERT INTO `sensitive_words` VALUES ('﻿军用手枪钢珠枪');
INSERT INTO `sensitive_words` VALUES ('出售手枪');
INSERT INTO `sensitive_words` VALUES ('出售炸药');
INSERT INTO `sensitive_words` VALUES ('出售炸药test2');
INSERT INTO `sensitive_words` VALUES ('出售炸药test3');
INSERT INTO `sensitive_words` VALUES ('出售炸药test5');
INSERT INTO `sensitive_words` VALUES ('出售炸药test6');
INSERT INTO `sensitive_words` VALUES ('﻿出售雷管');
INSERT INTO `sensitive_words` VALUES ('﻿制作火药配方');
INSERT INTO `sensitive_words` VALUES ('﻿核弹头的制造');
INSERT INTO `sensitive_words` VALUES ('﻿炸药');
INSERT INTO `sensitive_words` VALUES ('﻿硝酸甘油制作');
INSERT INTO `sensitive_words` VALUES ('自制燃烧弹');
INSERT INTO `sensitive_words` VALUES ('蜡笔小新');
INSERT INTO `sensitive_words` VALUES ('﻿高压气枪');
INSERT INTO `sensitive_words` VALUES ('﻿鸡尾酒炸弹制作');
INSERT INTO `sensitive_words` VALUES ('﻿黑火药的成分');
COMMIT;

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of topic
-- ----------------------------
BEGIN;
INSERT INTO `topic` VALUES (1, '鲁迅名言', '2020-07-08 00:00:00', 1, 10, 1, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200813/1597302955543-372e66a2a8556ce20c318c96ef58e50c.jpeg', '这个话题很牛逼');
INSERT INTO `topic` VALUES (2, '广东高考', '2020-07-08 00:00:00', 1, 10, 1, 'https://cdn.pixabay.com/photo/2015/09/20/22/09/anime-948925__340.jpg', '这个话题很牛逼');
INSERT INTO `topic` VALUES (3, '高考出分', '2020-07-20 00:00:00', 0, 11, 1, 'https://cdn.pixabay.com/photo/2015/09/20/22/09/anime-948925__340.jpg', '这个话题很牛逼');
INSERT INTO `topic` VALUES (4, '鲍毓明退出', '2020-07-20 00:00:00', 0, 11, 1, 'https://cdn.pixabay.com/photo/2015/09/20/22/09/anime-948925__340.jpg', '这个话题很牛逼');
INSERT INTO `topic` VALUES (5, '驾校车祸', '2020-07-20 00:00:00', 0, 11, 1, 'https://cdn.pixabay.com/photo/2015/09/20/22/09/anime-948925__340.jpg', '这个话题很牛逼');
INSERT INTO `topic` VALUES (6, '高考放榜', '2020-07-20 00:00:00', 0, 11, 1, 'https://cdn.pixabay.com/photo/2015/09/20/22/09/anime-948925__340.jpg', '这个话题很牛逼');
INSERT INTO `topic` VALUES (7, '禁用塑料袋', '2020-07-20 00:00:00', 1, 11, 1, 'https://cdn.pixabay.com/photo/2015/09/20/22/09/anime-948925__340.jpg', '这个话题很牛逼');
INSERT INTO `topic` VALUES (8, '', '2020-08-11 07:57:27', 0, 0, NULL, 'https://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/common/topic_logo.jpg', NULL);
INSERT INTO `topic` VALUES (9, '读书', '2020-08-12 09:53:44', 0, 0, NULL, 'https://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/common/topic_logo.jpg', NULL);
INSERT INTO `topic` VALUES (10, '淘兴趣第一天', '2020-08-12 14:07:07', 0, 0, NULL, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200812/1597245552748-background2.png', NULL);
INSERT INTO `topic` VALUES (11, 'friends', '2020-08-14 05:41:26', 0, 0, NULL, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200814/1597387017944-7.jpg', 'welcome to the real world!');
INSERT INTO `topic` VALUES (12, '权游', '2020-08-14 05:48:57', 0, 0, NULL, 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200814/1597386879215-917aef1500fef778cc6f3fada45462cd%20(1).jpg', 'Winter is coming.');
COMMIT;

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
-- Records of topic_blog
-- ----------------------------
BEGIN;
INSERT INTO `topic_blog` VALUES (1, 1);
INSERT INTO `topic_blog` VALUES (1, 2);
INSERT INTO `topic_blog` VALUES (1, 3);
INSERT INTO `topic_blog` VALUES (1, 4);
INSERT INTO `topic_blog` VALUES (1, 5);
INSERT INTO `topic_blog` VALUES (1, 6);
INSERT INTO `topic_blog` VALUES (1, 7);
INSERT INTO `topic_blog` VALUES (1, 8);
INSERT INTO `topic_blog` VALUES (1, 9);
INSERT INTO `topic_blog` VALUES (1, 10);
INSERT INTO `topic_blog` VALUES (1, 11);
INSERT INTO `topic_blog` VALUES (1, 12);
INSERT INTO `topic_blog` VALUES (1, 13);
INSERT INTO `topic_blog` VALUES (1, 14);
COMMIT;

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
-- Records of topic_heat
-- ----------------------------
BEGIN;
INSERT INTO `topic_heat` VALUES (1, 10227);
INSERT INTO `topic_heat` VALUES (2, 10228);
INSERT INTO `topic_heat` VALUES (3, 10251);
INSERT INTO `topic_heat` VALUES (4, 10249);
INSERT INTO `topic_heat` VALUES (5, 2000);
INSERT INTO `topic_heat` VALUES (6, 2000);
INSERT INTO `topic_heat` VALUES (7, 200);
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(15) NOT NULL,
  `password` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_type` smallint NOT NULL,
  `is_ban` smallint DEFAULT NULL,
  `is_forbidden` smallint DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '鲁迅', 'OTU2N0NBRTJFMDY3MDlCNzVGM0Q4NUVFRkY1QjQ5RjI=', 0, 0, 1);
INSERT INTO `user` VALUES (2, '大蒜', '111111', 0, 0, 1);
INSERT INTO `user` VALUES (3, 'Binnie', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (4, 'BOZ', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (5, 'Mok', '111111', 0, 1, 1);
INSERT INTO `user` VALUES (6, 'Emma', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (7, 'Kevin', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (8, 'Olivia', '111111', 0, 1, 0);
INSERT INTO `user` VALUES (9, 'William', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (10, 'Richard', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (11, 'Rachel', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (12, 'thunderboy', '111111', 1, 0, 0);
INSERT INTO `user` VALUES (16, '大蒜马甲', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (17, '大蒜马甲2', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (18, '大蒜马甲3', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (19, '大蒜马甲4', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (20, '大蒜马甲5', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (21, '大蒜马甲9', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (22, '大蒜马甲6', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (23, 'ds马甲100', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (24, '大蒜马甲99', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (25, '大蒜马甲101', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (26, '大蒜马甲999', '111111', 0, 0, 0);
INSERT INTO `user` VALUES (27, '一百万', 'QjA2QTVFQkI2OTY2Q0JEOEU2OTI2OUI3OENDMkJFNDI=', 1, 0, 0);
INSERT INTO `user` VALUES (28, '啦啦啦', 'RjA2QTcwMTQ0QTI4OTNEQzVDRkQ2MDNBNDgyNEJGQjI=', 0, 0, 0);
INSERT INTO `user` VALUES (29, '明明', 'OUYzRDdGOEQ3REI1OTU0NTk1MDY0NUQ5NDI5RTM1MDc=', 0, 0, 0);
INSERT INTO `user` VALUES (30, '二哈', 'NzQzMTU0MkVBNEJFQTlFNUUwOUM2QTc4RTkyMDQ3MzE=', 0, 0, 0);
INSERT INTO `user` VALUES (31, '大哥', 'NUE0NDA1RUFDRkU3MTg4NEVEMDY4MzJCMEQwNzc0QTk=', 1, 0, 0);
INSERT INTO `user` VALUES (32, 'BINNNIE', 'MjBBRjExOTc0OUM2RjM4NDkyM0U3RUUyMkI2MzY0MDM=', 0, 0, 0);
INSERT INTO `user` VALUES (33, 'papa', 'MjU3RkE5NjkyMjg2QzEzRTgxNTAwNEZGMUYxMENBMUM=', 0, 0, 0);
INSERT INTO `user` VALUES (34, '王动', 'QkFFMzAzMjI1RDQ4MEZEREI4MDFDMzBCRkJERjA5MTk=', 0, 1, 0);
COMMIT;

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
-- Records of user_ban
-- ----------------------------
BEGIN;
INSERT INTO `user_ban` VALUES (1, '2021-07-26 00:13:36', '2022-08-15 08:32:10');
INSERT INTO `user_ban` VALUES (2, '2021-07-26 00:15:43', '2021-08-15 08:32:58');
INSERT INTO `user_ban` VALUES (5, '2020-08-14 09:06:59', '2022-08-16 09:54:38');
INSERT INTO `user_ban` VALUES (8, '2020-08-15 03:30:30', NULL);
INSERT INTO `user_ban` VALUES (10, '2021-07-25 16:07:25', NULL);
INSERT INTO `user_ban` VALUES (34, '2020-08-15 08:56:53', NULL);
COMMIT;

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
-- Records of user_count
-- ----------------------------
BEGIN;
INSERT INTO `user_count` VALUES (16, 0, 0, 0);
INSERT INTO `user_count` VALUES (17, 0, 0, 0);
INSERT INTO `user_count` VALUES (18, 0, 0, 0);
INSERT INTO `user_count` VALUES (19, 0, 0, 0);
INSERT INTO `user_count` VALUES (21, 0, 0, 0);
INSERT INTO `user_count` VALUES (22, 0, 0, 0);
INSERT INTO `user_count` VALUES (23, 0, 0, 0);
INSERT INTO `user_count` VALUES (24, 0, 0, 0);
INSERT INTO `user_count` VALUES (25, 0, 0, 0);
INSERT INTO `user_count` VALUES (26, 0, 0, 0);
INSERT INTO `user_count` VALUES (27, 0, 0, 0);
INSERT INTO `user_count` VALUES (28, 0, 0, 0);
INSERT INTO `user_count` VALUES (29, 0, 0, 0);
INSERT INTO `user_count` VALUES (30, 0, 0, 0);
INSERT INTO `user_count` VALUES (31, 0, 0, 0);
INSERT INTO `user_count` VALUES (32, 0, 0, 0);
INSERT INTO `user_count` VALUES (33, 0, 0, 0);
INSERT INTO `user_count` VALUES (34, 0, 0, 0);
COMMIT;

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
-- Records of user_follow
-- ----------------------------
BEGIN;
INSERT INTO `user_follow` VALUES (1, 1);
INSERT INTO `user_follow` VALUES (21, 1);
INSERT INTO `user_follow` VALUES (23, 1);
INSERT INTO `user_follow` VALUES (1, 2);
INSERT INTO `user_follow` VALUES (1, 3);
INSERT INTO `user_follow` VALUES (1, 4);
INSERT INTO `user_follow` VALUES (1, 5);
INSERT INTO `user_follow` VALUES (1, 8);
INSERT INTO `user_follow` VALUES (1, 11);
INSERT INTO `user_follow` VALUES (21, 11);
INSERT INTO `user_follow` VALUES (1, 21);
INSERT INTO `user_follow` VALUES (28, 27);
INSERT INTO `user_follow` VALUES (29, 27);
INSERT INTO `user_follow` VALUES (30, 27);
INSERT INTO `user_follow` VALUES (32, 27);
INSERT INTO `user_follow` VALUES (27, 28);
INSERT INTO `user_follow` VALUES (27, 29);
INSERT INTO `user_follow` VALUES (28, 29);
INSERT INTO `user_follow` VALUES (27, 30);
INSERT INTO `user_follow` VALUES (28, 34);
COMMIT;

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
-- Records of user_info
-- ----------------------------
BEGIN;
INSERT INTO `user_info` VALUES (1, 'test@163.com', 0, '广东', 50, '鲁迅是哪里人啊我不知道啊', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '最强之鲁迅');
INSERT INTO `user_info` VALUES (2, 'test@163.com', 0, '广东', 50, '早睡早起身体好', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', 'explodingnerk');
INSERT INTO `user_info` VALUES (3, 'test@163.com', 0, '中国', 50, '淘兴趣首席排版大师', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', 'Binnie');
INSERT INTO `user_info` VALUES (4, 'test@163.com', 0, '中国', 50, '淘兴趣首席架构师', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', 'BoZ');
INSERT INTO `user_info` VALUES (5, 'test@163.com', 0, '广东', 50, '淘兴趣首席执行官', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', 'Mokkkkk');
INSERT INTO `user_info` VALUES (6, 'test@163.com', 0, '广东', 50, '荒野乱斗首席排版大师', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '荒野乱斗首位玩家');
INSERT INTO `user_info` VALUES (7, 'test@163.com', 0, '广东', 50, '荒野乱斗首席执行官', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '别打我行不行');
INSERT INTO `user_info` VALUES (8, 'test@163.com', 0, '广东', 50, '皇室战争首席执行官', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '我想不出名字了');
INSERT INTO `user_info` VALUES (9, 'test@163.com', 0, '广东', 50, '荒野乱斗首席架构师', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '再随便取个');
INSERT INTO `user_info` VALUES (10, 'test@163.com', 0, '广东', 50, '带我飞行不行', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '你是个憨憨');
INSERT INTO `user_info` VALUES (11, 'test@163.com', 0, '广东', 50, '你是个弟弟', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '憨憨二号');
INSERT INTO `user_info` VALUES (12, 'test@163.com', 0, '广东', 50, '你是个弟弟', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '超级憨憨');
INSERT INTO `user_info` VALUES (16, 'test@163.com', 1, '广东', 50, '这个人很懒，什么都没留下', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '大蒜马甲');
INSERT INTO `user_info` VALUES (17, 'test@163.com', 1, '广东', 50, '这个人很懒，什么都没留下', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '大蒜马甲');
INSERT INTO `user_info` VALUES (18, 'test@163.com', 1, '广东', 50, '这个人很懒，什么都没留下', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '大蒜马甲');
INSERT INTO `user_info` VALUES (19, 'test@163.com', 1, '广东', 50, '这个人很懒，什么都没留下', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '大蒜马甲');
INSERT INTO `user_info` VALUES (20, 'test163.com', 0, '广东', 50, '睡得晚身体不好', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '超级大蒜马甲');
INSERT INTO `user_info` VALUES (21, 'test@163.com', 1, '广东', 100, '这个人很懒，什么都没留下', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '大蒜马甲9');
INSERT INTO `user_info` VALUES (22, 'test@163.com', 1, '广东', 100, '这个人很懒，什么都没留下', 'https://cdn.pixabay.com/photo/2020/07/14/02/47/chocolate-labrador-5402611_960_720.jpg', '大蒜马甲6');
INSERT INTO `user_info` VALUES (23, 'test@163.com', 1, '广东', 100, '这个人很懒，什么都没留下', NULL, 'ds马甲100');
INSERT INTO `user_info` VALUES (24, 'test@163.com', 1, '广东', 100, '这个人很懒，什么都没留下', NULL, '大蒜马甲99');
INSERT INTO `user_info` VALUES (25, 'test@163.com', 1, '广东', 100, '这个人很懒，什么都没留下', NULL, '大蒜马甲101');
INSERT INTO `user_info` VALUES (26, 'test@163.com', 1, '广东', 100, '这个人很懒，什么都没留下', NULL, '大蒜马甲999');
INSERT INTO `user_info` VALUES (27, 'binnie@binnie.com', 0, '无', 100, '这个人很懒，什么都没留下', NULL, 'Hei');
INSERT INTO `user_info` VALUES (28, '1186449334@qq.com', 0, '上海交通大学闵行校区东27宿舍', 100, '天才少女', 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200814/1597383734912-7.jpg', '啦啦啦');
INSERT INTO `user_info` VALUES (29, 'lily126dress@126.com', 0, '东川路800号', 100, '这个人很懒，什么都没留下', NULL, '明明');
INSERT INTO `user_info` VALUES (30, 'kkkkkk@sjtu.edu.cn', 0, '北京海淀', 100, '这个人很懒，什么都没留下', NULL, '我是二哈');
INSERT INTO `user_info` VALUES (31, '1234@qq.com', 0, '上海交通大学闵行校区东100宿舍', 100, '这个人很懒，什么都没留下', NULL, '大哥');
INSERT INTO `user_info` VALUES (32, 'binnie@binnie.com', 0, 'hello', 100, '这个人很懒', 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200814/1597373701365-avatar1.jpeg', 'Bin');
INSERT INTO `user_info` VALUES (33, '1658862593@qq.com', 1, '火星', 100, '这个人很懒，什么都没留下', NULL, 'Kingone');
INSERT INTO `user_info` VALUES (34, '1601190449@qq.com', 0, '一二', 100, '这个人很懒，什么都没留下', 'http://amoy-interest-oss.oss-cn-shenzhen.aliyuncs.com/amoy-interest/images/20200814/1597407104991-DFC_V9S%7BO8%7B)05ZM%5BWX9OQP.png', '王动');
COMMIT;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `role_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `User_id` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user_role
-- ----------------------------
BEGIN;
INSERT INTO `user_role` VALUES (1, '鲁迅', 'user');
INSERT INTO `user_role` VALUES (3, '一百万', 'admin');
INSERT INTO `user_role` VALUES (4, '啦啦啦', 'user');
INSERT INTO `user_role` VALUES (5, '明明', 'user');
INSERT INTO `user_role` VALUES (6, '二哈', 'user');
INSERT INTO `user_role` VALUES (7, '大哥', 'admin');
INSERT INTO `user_role` VALUES (8, 'BINNNIE', 'user');
INSERT INTO `user_role` VALUES (9, 'papa', 'user');
INSERT INTO `user_role` VALUES (10, '王动', 'user');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
