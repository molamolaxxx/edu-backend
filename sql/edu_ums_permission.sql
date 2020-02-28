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
-- Table structure for table `ums_permission`
--

DROP TABLE IF EXISTS `ums_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ums_permission` (
  `id` varchar(30) NOT NULL COMMENT 'per_(八位)',
  `name` varchar(64) DEFAULT NULL COMMENT '角色名',
  `description` varchar(500) DEFAULT NULL COMMENT '角色描述',
  `value` varchar(50) DEFAULT NULL COMMENT '权限验证值',
  `url` varchar(100) DEFAULT NULL COMMENT '可以访问的url',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ums_permission`
--

LOCK TABLES `ums_permission` WRITE;
/*!40000 ALTER TABLE `ums_permission` DISABLE KEYS */;
INSERT INTO `ums_permission` VALUES ('permission_dahJSHbj','查询角色信息权限','可以查询角色信息','ums:role:find','/umsRole','2019-12-01 21:29:30','2019-12-01 21:29:30'),('permission_HIhHldsk','更新用户信息权限','可以更新用户信息','ums:user:update','/umsUser','2019-12-01 21:27:09','2019-12-01 21:27:09'),('permission_HSKjbJhj','删除角色信息权限','可以删除角色信息','ums:role:delete','/umsRole','2019-12-01 21:29:14','2019-12-01 21:29:14'),('permission_jhe1n8Rt','查询权限信息的权限','可以查询权限信息','ums:permission:find','/umsPermission','2019-12-01 21:36:45','2019-12-01 21:36:45'),('permission_LJHJjshs','删除用户信息权限','可以删除用户信息','ums:user:delete','/umsUser','2019-12-01 21:27:26','2019-12-01 21:27:26'),('permission_MWUrWnhQ','更新权限信息的权限','可以更新权限信息','ums:permission:update','/umsPermission','2019-12-01 21:37:16','2019-12-01 21:37:16'),('permission_nA8NcI52','保存权限信息的权限','可以保存权限信息','ums:permission:save','/umsPermission','2019-12-01 21:37:02','2019-12-01 21:37:02'),('permission_nLYwZBT6','更新角色信息权限','可以更新角色信息','ums:role:update','/umsRole','2019-12-01 21:31:51','2019-12-01 21:31:51'),('permission_PKXlJozE','查询用户信息权限','可以查询用户信息','ums:user:find','/umsUser','2019-12-01 21:08:38','2019-12-01 21:08:38'),('permission_qPV5TgiX','删除权限信息的权限','可以删除权限信息','ums:permission:delete','/umsPermission','2019-12-01 21:37:38','2019-12-01 21:37:38'),('permission_rvCtxVFF','修改角色权限关系权限','可以修改角色权限关系','ums:permission:rela','/umsPermission','2019-12-01 21:36:03','2019-12-01 21:36:03'),('permission_skfLdjns','保存用户信息权限','可以保存用户信息','ums:user:save','/umsUser','2019-12-01 21:26:43','2019-12-01 21:26:43'),('permission_WMKkRNT3','修改用户角色关系权限','可以修改用户角色关系','ums:role:rela','/umsRole','2019-12-01 21:32:53','2019-12-01 21:32:53'),('permission_YEHiMcIE','保存角色信息权限','可以保存角色信息','ums:role:save','/umsRole','2019-12-01 21:30:59','2019-12-01 21:30:59');
/*!40000 ALTER TABLE `ums_permission` ENABLE KEYS */;
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
