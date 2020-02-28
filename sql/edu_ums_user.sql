-- MySQL dump 10.13  Distrib 5.7.29, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: edu
-- ------------------------------------------------------
-- Server version	5.7.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ums_user`
--

DROP TABLE IF EXISTS `ums_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_user` (
  `id` varchar(30) NOT NULL COMMENT 'user_(八位随机)',
  `name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '加密密码',
  `header_icon` varchar(100) DEFAULT NULL COMMENT '头像路径',
  `description` varchar(500) DEFAULT NULL COMMENT '用户描述',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `email` varchar(64) DEFAULT NULL COMMENT '用户邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_user`
--

LOCK TABLES `ums_user` WRITE;
/*!40000 ALTER TABLE `ums_user` DISABLE KEYS */;
INSERT INTO `ums_user` VALUES ('user_11111111','admin','$2a$10$aZwg19ztnmU30WW/Aik6u.O4gd90fjPMH4rslmKmSd5VG5LjoX4du','/icon','更新啦','2019-11-25 12:17:49','2020-02-21 12:28:10','408888888'),('user_evsMaCbp','test','$2a$10$86GzYaw0n8s/f/E3LPGqCeVjXtiM.AoEB4vmeORNMUsp0hHeSH4ZO','','这是一个一般管理员','2019-12-01 21:52:55','2019-12-01 21:52:55','3333333@qq.com');
/*!40000 ALTER TABLE `ums_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-28 22:30:39
