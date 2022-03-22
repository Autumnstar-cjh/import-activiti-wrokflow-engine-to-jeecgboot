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
-- Table structure for table `day_trx_stat`
--

DROP TABLE IF EXISTS `day_trx_stat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `day_trx_stat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_date` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `merchant_code` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `terminal_code` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `transaction_mony` decimal(19,2) DEFAULT NULL,
  `transaction_number` int(11) DEFAULT NULL,
  `company_income` decimal(9,2) DEFAULT NULL,
  `transaction_type` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `day_trx_stat`
--

LOCK TABLES `day_trx_stat` WRITE;
/*!40000 ALTER TABLE `day_trx_stat` DISABLE KEYS */;
INSERT INTO `day_trx_stat` VALUES (1,'20161010','100731048126221',NULL,4373.60,1,0.00,'结算-至银行账户'),(2,'20161128','100731048126221',NULL,2485.00,1,0.00,'结算-至银行账户'),(3,'20160713','100731050943940',NULL,1.00,1,0.01,'微信支付'),(4,'20161111','100731050943940','8dzZV2',1.00,1,0.01,'微信支付'),(5,'20170307','100731050943940','8dzZV2',3035.00,1,-6.98,'微信支付'),(6,'20170113','100731052115019',NULL,-9940.00,-1,0.00,'结算-至银行账户'),(7,'20170112','100731052115019','BTnS6k',10000.00,1,40.00,'微信支付'),(8,'20160902','100731052117126',NULL,0.10,1,0.00,'结算-至银行账户'),(9,'20160908','100731052117126',NULL,2200.00,1,6.60,'微信支付'),(10,'20170306','100731052117126',NULL,-593.42,-1,0.00,'结算-至银行账户'),(11,'20170801','100731052117126',NULL,-3737.44,-1,0.00,'结算-至银行账户'),(12,'20180129','100731052117126',NULL,-1361.78,-1,0.00,'结算-至银行账户'),(13,'20180929','100731052117126',NULL,-1242.50,-1,0.00,'结算-至银行账户'),(14,'20181228','100731052117126',NULL,-1630.16,-1,0.00,'结算-至银行账户'),(15,'20191008','100731052117126',NULL,-2087.40,-1,0.00,'结算-至银行账户'),(16,'20191025','100731052117126',NULL,-427.42,-1,0.00,'结算-至银行账户'),(17,'20191216','100731052117126',NULL,-12922.00,-1,0.00,'结算-至银行账户'),(18,'20190319','100731052117126','62o7jX',15000.00,1,0.00,'支付宝支付'),(19,'20191215','100731052117126','62o7jX',13000.00,1,52.00,'支付宝支付'),(20,'20160722','100731053113220',NULL,531.35,1,0.00,'结算-至银行账户'),(21,'20160907','100731048126221',NULL,11953.84,1,0.00,'结算-至银行账户'),(22,'20160725','100731053113220',NULL,1482.52,3,0.00,'结算-至银行账户'),(23,'20160923','100731048126221',NULL,3180.80,1,0.00,'结算-至银行账户'),(24,'20160728','100731053113220',NULL,412.19,1,0.00,'结算-至银行账户'),(25,'20160809','100731053113220',NULL,370.33,1,0.00,'结算-至银行账户'),(26,'20161028','100731048126221',NULL,3976.00,1,0.00,'结算-至银行账户'),(27,'20160908','100731053113220',NULL,816.47,1,0.00,'结算-至银行账户'),(28,'20160909','100731053113220',NULL,928.03,1,0.00,'结算-至银行账户'),(29,'20170123','100731050943940','8dzZV2',24000.00,3,96.00,'支付宝支付'),(30,'20160921','100731053113220',NULL,1845.81,1,0.00,'结算-至银行账户'),(31,'20160905','100731052117126',NULL,5330.82,1,0.00,'结算-至银行账户'),(32,'20170217','100731052117126',NULL,-534.77,-1,0.00,'结算-至银行账户'),(33,'20180528','100731052117126',NULL,-1441.30,-1,0.00,'结算-至银行账户'),(34,'20160930','100731053113220',NULL,1259.37,1,0.00,'结算-至银行账户'),(35,'20190509','100731052117126',NULL,-9940.00,-1,0.00,'结算-至银行账户'),(36,'20190711','100731052117126',NULL,-4970.00,-1,0.00,'结算-至银行账户'),(37,'20170621','100731052117126','62o7jX',600.00,1,2.34,'微信支付'),(38,'20161007','100731053113220',NULL,11735.53,6,0.00,'结算-至银行账户'),(39,'20161106','100731048126221','20gntf',990.00,1,3.96,'微信支付'),(40,'20190508','100731052117126','62o7jX',40500.00,6,40.00,'微信支付'),(41,'20170322','100731050943940',NULL,-2772.27,-1,0.00,'结算-至银行账户'),(42,'20170525','100731050943940',NULL,-2316.02,-1,0.00,'结算-至银行账户'),(43,'20161110','100731050943940','8dzZV2',2.00,2,0.02,'微信支付'),(44,'20190901','100731052117126','62o7jX',13000.00,2,12.00,'微信支付'),(45,'20190908','100731052117126','62o7jX',10000.00,1,40.00,'支付宝支付'),(46,'20161011','100731053113220',NULL,1070.30,1,0.00,'结算-至银行账户'),(47,'20190930','100731052117126','62o7jX',3066.00,4,8.40,'微信支付'),(48,'20160906','100731048126221',NULL,12026.00,1,36.08,'微信支付'),(49,'20161122','100731050943940',NULL,5524.65,1,0.00,'结算-至银行账户'),(50,'20160713','100731053113220',NULL,40.16,1,0.00,'结算-至银行账户');
/*!40000 ALTER TABLE `day_trx_stat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-24 23:12:17