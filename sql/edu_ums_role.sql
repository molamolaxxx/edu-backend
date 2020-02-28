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
-- Table structure for table `ums_role`
--

DROP TABLE IF EXISTS `ums_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_role` (
  `id` varchar(30) NOT NULL,
  `name` varchar(64) DEFAULT NULL COMMENT '角色名',
  `description` varchar(500) DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_role`
--

LOCK TABLES `ums_role` WRITE;
/*!40000 ALTER TABLE `ums_role` DISABLE KEYS */;
INSERT INTO `ums_role` VALUES ('role_JuPmFMT1','一般管理员','可以查看相应的数据，但无法修改他们','2019-12-01 21:44:37','2019-12-01 21:44:37'),('role_r6Ai4bUY','超级管理员','无视一切权限的超级管理员','2019-12-01 21:43:29','2019-12-01 21:43:29'),('role_UYr5JdCG','普通用户','只可以访问不需要授权的部分','2019-12-01 21:45:21','2019-12-01 21:45:21');
/*!40000 ALTER TABLE `ums_role` ENABLE KEYS */;
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
