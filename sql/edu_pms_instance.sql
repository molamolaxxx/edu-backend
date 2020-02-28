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
-- Table structure for table `pms_instance`
--

DROP TABLE IF EXISTS `pms_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pms_instance` (
  `id` varchar(30) NOT NULL COMMENT '实例id',
  `uid` varchar(30) DEFAULT NULL COMMENT '实例创建者用户id',
  `name` varchar(200) DEFAULT NULL COMMENT '实例名',
  `data_id` varchar(30) DEFAULT NULL COMMENT '实例数据id',
  `state` varchar(2) DEFAULT NULL COMMENT '0:未执行 1:执行中 2:已完成 -1 执行错误',
  `type` varchar(1) DEFAULT NULL COMMENT '0:资源生成评价 1:资源聚合评价 2:用户情感分析 ',
  `create_time` datetime DEFAULT NULL COMMENT '实例创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '实例更新时间',
  `start_time` datetime DEFAULT NULL COMMENT '实例开始时间',
  `finish_time` datetime DEFAULT NULL COMMENT '实例结束时间',
  `description` varchar(500) DEFAULT NULL COMMENT '实例描述',
  `config` varchar(500) DEFAULT NULL COMMENT '以json形式保存的配置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='算法实例表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pms_instance`
--

LOCK TABLES `pms_instance` WRITE;
/*!40000 ALTER TABLE `pms_instance` DISABLE KEYS */;
INSERT INTO `pms_instance` VALUES ('instance_gnpYpMtS','user_11111111','yyyyyy','data_be9PVI6J','0','0','2020-01-09 14:47:13','2020-02-13 16:58:17','2020-01-13 11:43:31','2020-01-13 11:58:58','23921310',NULL),('instance_LMrgGJ78','user_11111111','计算机','data_be9PVI6J','0','0','2020-01-13 15:32:51','2020-01-13 15:36:35',NULL,NULL,'计算机真尼玛好玩',NULL),('instance_oyY15QDI','user_11111111','测试实例','data_be9PVI6J','0','0','2019-12-31 15:16:53','2019-12-31 15:16:53','2020-01-10 18:36:49','2020-01-10 19:03:10','测试实例',NULL),('instance_PgHAQevX','user_11111111','三元组质量评价2','data_be9PVI6J','0','0','2020-01-09 14:47:06','2020-01-09 14:47:06','2020-01-09 16:20:00','2020-01-09 16:21:51','评价算法批量测试2',NULL),('instance_WDGbmOpZ','user_11111111','三元组质量评价1','data_be9PVI6J','0','0','2020-01-09 14:46:58','2020-01-09 14:46:58','2020-01-09 16:04:07','2020-01-09 16:06:54','评价算法批量测试1',NULL),('instance_Yo5kSeRc15822593','user_11111111','生成质量评价','data_PulC2kJs','0','0','2020-02-21 12:28:32','2020-02-21 12:28:32',NULL,NULL,'测试新接口','{\'entityMaxThreadCount\':66}');
/*!40000 ALTER TABLE `pms_instance` ENABLE KEYS */;
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
