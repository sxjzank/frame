/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50626
 Source Host           : localhost
 Source Database       : ak

 Target Server Type    : MySQL
 Target Server Version : 50626
 File Encoding         : utf-8

 Date: 06/27/2016 18:36:12 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tb_frame_image`
-- ----------------------------
DROP TABLE IF EXISTS `tb_frame_image`;
CREATE TABLE `tb_frame_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '类型',
  `hashcode` varchar(255) DEFAULT NULL,
  `file_name` varchar(50) NOT NULL DEFAULT '' COMMENT '文件名',
  `is_delete` varchar(255) NOT NULL DEFAULT '0' COMMENT '逻辑删除标志，1删除，默认0',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `tb_frame_permission`
-- ----------------------------
DROP TABLE IF EXISTS `tb_frame_permission`;
CREATE TABLE `tb_frame_permission` (
  `id` int(11) NOT NULL,
  `permission_name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `tb_frame_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_frame_role`;
CREATE TABLE `tb_frame_role` (
  `id` int(11) NOT NULL,
  `role_name` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `tb_frame_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_frame_user`;
CREATE TABLE `tb_frame_user` (
  `id` int(11) NOT NULL,
  `account` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `tb_frame_user_permission`
-- ----------------------------
DROP TABLE IF EXISTS `tb_frame_user_permission`;
CREATE TABLE `tb_frame_user_permission` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `tb_frame_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `tb_frame_user_role`;
CREATE TABLE `tb_frame_user_role` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `test`
-- ----------------------------
DROP TABLE IF EXISTS `test`;
CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typeId` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `ix_typeId` (`typeId`) USING BTREE,
  KEY `ix_created_At` (`typeId`,`created_at`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;
