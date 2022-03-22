-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ynallinpayboot
-- ------------------------------------------------------
-- Server version	5.7.29-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `work_plan`
--

DROP TABLE IF EXISTS `work_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `work_plan` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL COMMENT 'id',
  `title` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '计划名称',
  `person` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '负责人',
  `start_time` date DEFAULT NULL COMMENT '开始时间',
  `end_time` date DEFAULT NULL COMMENT '结束时间',
  `priority` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '优先级',
  `plan_describe` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '计划描述',
  `associated_target` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '关联目标',
  `week` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '周',
  `status` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '状态：0未完成，1已完成',
  `completed_situation` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `create_by` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(32) COLLATE utf8_bin DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `work_plan`
--

LOCK TABLES `work_plan` WRITE;
/*!40000 ALTER TABLE `work_plan` DISABLE KEYS */;
INSERT INTO `work_plan` VALUES ('1228608301529759745','A计划','admin','2020-02-17','2020-02-23','75','A计划','技术创新部2020年一季度完成2个4+1项目','下周','未完成','已完成50%','admin','2020-02-15 17:13:40','admin','2020-02-15 17:14:18'),('1228608644888068098','B计划','admin','2020-02-17','2020-02-23','50','B计划','一季度金勇完成1个项目','下周','未完成','已完成0%','admin','2020-02-15 17:15:02','admin','2020-02-15 17:21:36'),('1228610096587014146','C计划','admin','2020-02-17','2020-02-23','0','C计划','技术创新部2020年一季度完成2个4+1项目',NULL,'0','','admin','2020-02-15 17:20:48',NULL,NULL);
/*!40000 ALTER TABLE `work_plan` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-15 19:45:01
