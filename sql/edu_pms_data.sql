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
-- Table structure for table `pms_data`
--

DROP TABLE IF EXISTS `pms_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pms_data` (
  `id` varchar(30) NOT NULL COMMENT '数据id',
  `name` varchar(64) DEFAULT NULL COMMENT '数据名',
  `uid` varchar(30) DEFAULT NULL COMMENT '工程创建者id',
  `description` varchar(500) DEFAULT NULL COMMENT '数据描述',
  `create_time` datetime DEFAULT NULL COMMENT '数据创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '数据更改时间',
  `data_type` varchar(1) DEFAULT NULL COMMENT '0:csv 1:json数据 2:rdf数据',
  `data_size` varchar(20) DEFAULT NULL COMMENT '数据大小，单位为b',
  `data_path` varchar(200) DEFAULT NULL COMMENT '数据绝对路径',
  `instance_type` varchar(1) DEFAULT NULL COMMENT '该数据对应什么类型的实例\n0:资源生成评价 1:资源聚合评价 2:用户情感分析 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='算法数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pms_data`
--

LOCK TABLES `pms_data` WRITE;
/*!40000 ALTER TABLE `pms_data` DISABLE KEYS */;
INSERT INTO `pms_data` VALUES ('data_m01UCk1H','json','user_11111111','是json','2020-02-13 18:23:51','2020-02-13 18:23:51','1','187','/home/mola/IdeaProjects/edu/data-cache/4qBmlpHE-output.json','0'),('data_PulC2kJs','呵呵哈哈哈','user_11111111','测试数据','2020-01-13 15:38:54','2020-01-13 15:53:17','0','44','/home/mola/IdeaProjects/edu/data-cache/z2CigmFq-test.csv','0');
/*!40000 ALTER TABLE `pms_data` ENABLE KEYS */;
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
