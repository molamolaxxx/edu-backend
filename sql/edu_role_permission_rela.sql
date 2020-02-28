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
-- Table structure for table `role_permission_rela`
--

DROP TABLE IF EXISTS `role_permission_rela`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permission_rela` (
  `rid` varchar(30) NOT NULL COMMENT '角色名',
  `pid` varchar(30) NOT NULL COMMENT '权限名',
  PRIMARY KEY (`rid`,`pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permission_rela`
--

LOCK TABLES `role_permission_rela` WRITE;
/*!40000 ALTER TABLE `role_permission_rela` DISABLE KEYS */;
INSERT INTO `role_permission_rela` VALUES ('role_JuPmFMT1','permission_dahJSHbj'),('role_JuPmFMT1','permission_jhe1n8Rt'),('role_JuPmFMT1','permission_PKXlJozE'),('role_r6Ai4bUY','permission_dahJSHbj'),('role_r6Ai4bUY','permission_HIhHldsk'),('role_r6Ai4bUY','permission_HSKjbJhj'),('role_r6Ai4bUY','permission_jhe1n8Rt'),('role_r6Ai4bUY','permission_LJHJjshs'),('role_r6Ai4bUY','permission_MWUrWnhQ'),('role_r6Ai4bUY','permission_nA8NcI52'),('role_r6Ai4bUY','permission_nLYwZBT6'),('role_r6Ai4bUY','permission_PKXlJozE'),('role_r6Ai4bUY','permission_qPV5TgiX'),('role_r6Ai4bUY','permission_rvCtxVFF'),('role_r6Ai4bUY','permission_skfLdjns'),('role_r6Ai4bUY','permission_WMKkRNT3'),('role_r6Ai4bUY','permission_YEHiMcIE');
/*!40000 ALTER TABLE `role_permission_rela` ENABLE KEYS */;
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
