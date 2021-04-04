/*
 Navicat Premium Data Transfer

 Source Server         : windows
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : localhost:3306
 Source Schema         : note

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : 65001

 Date: 04/04/2021 20:34:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note_id` int(11) NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 98 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of likes
-- ----------------------------
INSERT INTO `likes` VALUES (7, 9, 2);
INSERT INTO `likes` VALUES (65, 9, 2);
INSERT INTO `likes` VALUES (94, 10, 8);
INSERT INTO `likes` VALUES (95, 9, 8);

-- ----------------------------
-- Table structure for note
-- ----------------------------
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `page` int(11) NULL DEFAULT NULL,
  `img_count` int(255) NULL DEFAULT NULL,
  `open` int(11) NULL DEFAULT NULL,
  `repository_id` int(11) NULL DEFAULT NULL,
  `group_id` int(11) NULL DEFAULT NULL,
  `date` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of note
-- ----------------------------
INSERT INTO `note` VALUES (2, 'ret', 3, 2, 0, 14, 14, '2021-04-04 10:46:07');
INSERT INTO `note` VALUES (3, '111', 1, 0, 1, 7, 13, '2021-04-04 10:53:26');

-- ----------------------------
-- Table structure for note_group
-- ----------------------------
DROP TABLE IF EXISTS `note_group`;
CREATE TABLE `note_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `repository_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of note_group
-- ----------------------------
INSERT INTO `note_group` VALUES (1, '2', 3);
INSERT INTO `note_group` VALUES (2, '111', 4);
INSERT INTO `note_group` VALUES (3, 'sss', 4);
INSERT INTO `note_group` VALUES (4, 'fff', 4);
INSERT INTO `note_group` VALUES (5, 'ff', 4);
INSERT INTO `note_group` VALUES (6, 'fff', 4);
INSERT INTO `note_group` VALUES (7, '1', 4);
INSERT INTO `note_group` VALUES (8, 'f', 4);
INSERT INTO `note_group` VALUES (12, 'sadg', 13);
INSERT INTO `note_group` VALUES (13, 'sadg', 7);
INSERT INTO `note_group` VALUES (14, 'cvbc', 14);

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `detail` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES (2, 'sadfa', 'sadfgfhjfghasdfasdffffffffff');
INSERT INTO `notice` VALUES (7, 'asdf', 'asdgfsadg');

-- ----------------------------
-- Table structure for page
-- ----------------------------
DROP TABLE IF EXISTS `page`;
CREATE TABLE `page`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `note_id` int(11) NULL DEFAULT NULL,
  `page` int(255) NULL DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `count` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of page
-- ----------------------------
INSERT INTO `page` VALUES (12, 2, 1, '111111111111', 12);
INSERT INTO `page` VALUES (13, 2, 2, '222222222222222<img src=\"file:///C:/Users/Z/Desktop/pp/MyNotes1/src/com/dengzhitao/notes/html/img/2_1.jpg\"><img src=\"file:///C:/Users/Z/Desktop/pp/MyNotes1/src/com/dengzhitao/notes/html/img/2_2.png\">', 15);
INSERT INTO `page` VALUES (14, 3, 1, 'sfasf', 5);
INSERT INTO `page` VALUES (15, 2, 3, 'sadfasdf', 8);

-- ----------------------------
-- Table structure for reply
-- ----------------------------
DROP TABLE IF EXISTS `reply`;
CREATE TABLE `reply`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reply` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `user_comment_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of reply
-- ----------------------------
INSERT INTO `reply` VALUES (1, 'asdg', 6, 5);
INSERT INTO `reply` VALUES (2, 'asdg', 6, 5);
INSERT INTO `reply` VALUES (4, 'sadgsadg', 5, 7);
INSERT INTO `reply` VALUES (5, 'sadg', 5, 7);
INSERT INTO `reply` VALUES (6, 'asdh', 5, 7);
INSERT INTO `reply` VALUES (7, 'fsadf', 5, 8);
INSERT INTO `reply` VALUES (10, '33333333333', 5, 7);
INSERT INTO `reply` VALUES (11, 'ggg', 5, 8);
INSERT INTO `reply` VALUES (16, 'gggggggggg', 6, 8);

-- ----------------------------
-- Table structure for repository
-- ----------------------------
DROP TABLE IF EXISTS `repository`;
CREATE TABLE `repository`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  `open` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of repository
-- ----------------------------
INSERT INTO `repository` VALUES (1, NULL, 0, NULL);
INSERT INTO `repository` VALUES (2, '111', 1, 1);
INSERT INTO `repository` VALUES (3, '1', 1, 0);
INSERT INTO `repository` VALUES (4, '121', 1, 1);
INSERT INTO `repository` VALUES (5, 'f', 5, 0);
INSERT INTO `repository` VALUES (6, 'fff', 5, 1);
INSERT INTO `repository` VALUES (7, 'ffffff', 5, 1);
INSERT INTO `repository` VALUES (8, 'fasd', 5, 1);
INSERT INTO `repository` VALUES (13, 'asdg', 6, 0);
INSERT INTO `repository` VALUES (14, 'cgh', 6, 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `phone_number` char(12) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `power` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '123', '125', 'sadddddd', '哈', 1, '11111111111', 2);
INSERT INTO `user` VALUES (2, '12434', '124', 'a', '男', 1, '11111111111', 2);
INSERT INTO `user` VALUES (3, '1234', 'a', 'sss', '男', 1, '11111111111', 1);
INSERT INTO `user` VALUES (4, 'a', 'a', 'aa', 'a', 1, '11111111111', 3);
INSERT INTO `user` VALUES (5, '1', '1', 'dd', '男', 1, '11111111111', 2);
INSERT INTO `user` VALUES (6, 's', 's', 'sd', '男', 1, '11111111111', 2);
INSERT INTO `user` VALUES (7, '1113', 'a', '1', '1', 1, '11111111111', 3);
INSERT INTO `user` VALUES (8, '4', '4', '11', '男', 1, '11111111111', 2);

-- ----------------------------
-- Table structure for user_comment
-- ----------------------------
DROP TABLE IF EXISTS `user_comment`;
CREATE TABLE `user_comment`  (
  `id` int(255) NOT NULL AUTO_INCREMENT,
  `user_comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user_comment
-- ----------------------------
INSERT INTO `user_comment` VALUES (8, 'ggg', 5);

SET FOREIGN_KEY_CHECKS = 1;
