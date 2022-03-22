-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 10.96.1.250    Database: ynallinpayboot
-- ------------------------------------------------------
-- Server version	5.7.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `project_info`
--

DROP TABLE IF EXISTS `project_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `project_info` (
  `id` varchar(36) COLLATE utf8_bin NOT NULL COMMENT 'Id',
  `host_department` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '项目主办部门',
  `start_time` datetime NOT NULL COMMENT '立项时间',
  `project_name` varchar(8) COLLATE utf8_bin NOT NULL COMMENT '项目名称',
  `project_code` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '项目编号',
  `budget` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '预算总额',
  `expect_online_time` datetime NOT NULL COMMENT '预计上线时间',
  `project_leader` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '项目负责人',
  `project_transactor` varchar(45) COLLATE utf8_bin NOT NULL COMMENT '项目经办人',
  `project_plan_description` text COLLATE utf8_bin NOT NULL COMMENT '项目方案简述',
  `market_strategy_analysis` text COLLATE utf8_bin NOT NULL COMMENT '市场战略分析',
  `business_realization_pattern` text COLLATE utf8_bin NOT NULL COMMENT '业务实现模式',
  `business_process_describe` text COLLATE utf8_bin NOT NULL COMMENT '业务流程详述',
  `risk_control_compliance_analysis` text COLLATE utf8_bin NOT NULL COMMENT '风控合规分析',
  `investment_benefit_analysis` text COLLATE utf8_bin NOT NULL COMMENT '投资效益分析',
  `conclusions_recommendations` text COLLATE utf8_bin COMMENT '结论及建议',
  `create_by` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '创建人登录名称',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '更新人登录名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_info`
--

LOCK TABLES `project_info` WRITE;
/*!40000 ALTER TABLE `project_info` DISABLE KEYS */;
INSERT INTO `project_info` VALUES ('1209680684717178882','技术创新部','2019-12-25 00:00:00','cs','1','1','2019-12-26 00:00:00','1','1','cs','cs','cs','cs','cs','cs','cs','admin','2019-12-25 00:00:00','admin','2019-12-25 11:42:25'),('8a80cb816f3b0286016f3b1c4bf40002','1','2019-12-25 00:00:00','1','1','1','2019-12-25 00:00:00','1','1','1','1','1','1','1','1','1','admin','2019-12-25 11:31:47','admin','2019-12-25 11:31:59');
/*!40000 ALTER TABLE `project_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-25 11:48:49
