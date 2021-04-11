-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: sed
-- ------------------------------------------------------
-- Server version	8.0.21

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
-- Table structure for table `administrator`
--

DROP TABLE IF EXISTS `administrator`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `administrator` (
  `id` varchar(255) NOT NULL,
  `emailAddress` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phoneNumber` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_esogmqxeek1uwdyhxvubme3qf` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrator`
--

LOCK TABLES `administrator` WRITE;
/*!40000 ALTER TABLE `administrator` DISABLE KEYS */;
INSERT INTO `administrator` VALUES ('21a1dac8-cccc-4100-9354-a33e8c493a07','candrei@gmail.com','Andrei','Chis','12345aA','0123456789','andreic'),('39b769a5-d08b-49b5-871b-fda7969114d4','emanueln@gmail.com','Emanuel','Necula','12345eE','0123456789','emanueln'),('55388ba0-4445-4d33-a98f-2ce823f85f8a','tomh@yahoo.com','Thomas','Henderson','12345tT','0987654321','tomh'),('913c2a90-dc38-40f3-a189-e7499b12b74c','ana11@gmail.com','Anne','Simon','12345aA','0123456789','anas'),('c7256973-ff15-4013-b25b-debc493ce102','emagroza@yahoo.com','Ema','Groza','12345eE','1113456789','emagroza');
/*!40000 ALTER TABLE `administrator` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document_type`
--

DROP TABLE IF EXISTS `document_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `document_type` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document_type`
--

LOCK TABLES `document_type` WRITE;
/*!40000 ALTER TABLE `document_type` DISABLE KEYS */;
INSERT INTO `document_type` VALUES ('378306ec-0f5c-4d4d-9799-8c4da0a5e0ab','Adeverinta'),('4334c418-0524-45cb-8bdd-e6d4a8f1c225','Cerere'),('541379e4-a107-4166-b3bf-d16362810a02','Chitanta');
/*!40000 ALTER TABLE `document_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request` (
  `id` varchar(255) NOT NULL,
  `approval_date` date DEFAULT NULL,
  `approved` bit(1) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `fk_document_type` varchar(255) DEFAULT NULL,
  `fk_residence` varchar(255) DEFAULT NULL,
  `fk_user` varchar(255) DEFAULT NULL,
  `request_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKcky6jv8vuo8sve0vjytpatev1` (`fk_document_type`),
  KEY `FKngb3pa43a8mcm3sfso4e77i8f` (`fk_residence`),
  KEY `FK3xxca7vvxcp608k2wojrl69ee` (`fk_user`),
  CONSTRAINT `FK3xxca7vvxcp608k2wojrl69ee` FOREIGN KEY (`fk_user`) REFERENCES `user` (`id`),
  CONSTRAINT `FKcky6jv8vuo8sve0vjytpatev1` FOREIGN KEY (`fk_document_type`) REFERENCES `document_type` (`id`),
  CONSTRAINT `FKngb3pa43a8mcm3sfso4e77i8f` FOREIGN KEY (`fk_residence`) REFERENCES `residence` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` VALUES ('04cf0fd8-b80c-4990-b5cb-ddb0cf1a2d73',NULL,_binary '\0','Salariu pe an','378306ec-0f5c-4d4d-9799-8c4da0a5e0ab','0d7a0f5a-1435-4a93-8d9e-1001c243eb32','2cc9e498-a364-436c-9978-ab78ec4dcbdc','2021-03-19'),('1c2ba99d-e149-4d3a-bae2-f97a06ec0592','2021-03-10',_binary '','Salariu','541379e4-a107-4166-b3bf-d16362810a02','6846603c-35ed-4e05-8380-0378e0677839','72aeef95-2760-4932-b6d0-326461e0394c','2021-03-02'),('30385f51-3505-457b-874f-e6172069c8b8','2021-03-25',_binary '','Salariu','378306ec-0f5c-4d4d-9799-8c4da0a5e0ab','6d836d6e-c3cf-4212-9aef-1ce950ef9975','100207d5-b908-4586-a3cb-7ae9a5ef3707','2021-03-23'),('3aac8554-623b-48aa-8957-36a16bf8fd8f',NULL,_binary '\0','Venit','541379e4-a107-4166-b3bf-d16362810a02','f7c8c047-2bcc-4b42-9133-bfcc18b5ea75','2cc9e498-a364-436c-9978-ab78ec4dcbdc','2021-03-25'),('6deb0363-96ce-4e29-97c4-ec1698e5db6b',NULL,_binary '\0','Venit pe an','541379e4-a107-4166-b3bf-d16362810a02','d32313e7-a229-4e26-8941-83a54b1f453e','8ecf2db4-0c3c-40bf-9920-3573396da3f5','2020-02-12'),('70b314a0-5e46-4533-bf30-79bcf050a972','2021-03-25',_binary '','Pentru locul de munca','378306ec-0f5c-4d4d-9799-8c4da0a5e0ab','6d836d6e-c3cf-4212-9aef-1ce950ef9975','100207d5-b908-4586-a3cb-7ae9a5ef3707','2021-02-07'),('7ce86f00-35a0-4041-80fc-9c2ceef27523','2021-03-25',_binary '','Locul de munca','4334c418-0524-45cb-8bdd-e6d4a8f1c225','6846603c-35ed-4e05-8380-0378e0677839','72aeef95-2760-4932-b6d0-326461e0394c','2021-03-25'),('81bece03-9dd9-422f-81d6-f6b3a4d467cd',NULL,_binary '\0','Salar','378306ec-0f5c-4d4d-9799-8c4da0a5e0ab','6846603c-35ed-4e05-8380-0378e0677839','72aeef95-2760-4932-b6d0-326461e0394c','2021-03-25'),('c343d141-edff-4289-954d-e3df64d081e7','2021-03-25',_binary '','Salariu','378306ec-0f5c-4d4d-9799-8c4da0a5e0ab','0d7a0f5a-1435-4a93-8d9e-1001c243eb32','2cc9e498-a364-436c-9978-ab78ec4dcbdc','2021-03-22'),('caf99dcc-292a-4fa4-bbca-d66bbb96dad5','2021-03-25',_binary '','Salariu','378306ec-0f5c-4d4d-9799-8c4da0a5e0ab','6846603c-35ed-4e05-8380-0378e0677839','72aeef95-2760-4932-b6d0-326461e0394c','2021-03-25'),('df878bf0-984e-4b9d-b893-13080c971aac',NULL,_binary '\0','Venit pe an','541379e4-a107-4166-b3bf-d16362810a02','6846603c-35ed-4e05-8380-0378e0677839','72aeef95-2760-4932-b6d0-326461e0394c','2021-03-25'),('e14486cf-f8d6-4f36-93b0-1f33fa08cea9','2021-03-25',_binary '','De venit','378306ec-0f5c-4d4d-9799-8c4da0a5e0ab','46caefaf-d517-4ec2-a813-64f9565ebd37','139d0f5a-6924-4ff1-8697-d9e4a98d90bd','2021-03-20'),('fb56a3f2-a897-4288-b810-fd7de76d7959',NULL,_binary '\0','Facultate','4334c418-0524-45cb-8bdd-e6d4a8f1c225','0d7a0f5a-1435-4a93-8d9e-1001c243eb32','2cc9e498-a364-436c-9978-ab78ec4dcbdc','2021-03-21');
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `residence`
--

DROP TABLE IF EXISTS `residence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `residence` (
  `id` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `number` int NOT NULL,
  `street` varchar(255) NOT NULL,
  `fk_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfienwlcpuc87iu4erf4giixpa` (`fk_user`),
  CONSTRAINT `FKfienwlcpuc87iu4erf4giixpa` FOREIGN KEY (`fk_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `residence`
--

LOCK TABLES `residence` WRITE;
/*!40000 ALTER TABLE `residence` DISABLE KEYS */;
INSERT INTO `residence` VALUES ('09b8fcdb-8967-4f09-8495-ddf05000e557','Oradea',14,'Vineri','646861d7-a9ad-4092-bd0b-64471316bd4b'),('0d7a0f5a-1435-4a93-8d9e-1001c243eb32','Baia-Mare',54,'Soare','2cc9e498-a364-436c-9978-ab78ec4dcbdc'),('46caefaf-d517-4ec2-a813-64f9565ebd37','Cluj-Napoca',34,'Observator','139d0f5a-6924-4ff1-8697-d9e4a98d90bd'),('6846603c-35ed-4e05-8380-0378e0677839','Oradea',14,'Vineri','72aeef95-2760-4932-b6d0-326461e0394c'),('6d836d6e-c3cf-4212-9aef-1ce950ef9975','Cluj-Napoca',34,'Observator','100207d5-b908-4586-a3cb-7ae9a5ef3707'),('7ad4b4dc-4c84-4473-b60a-ed822b6f555b','Zalau',70,'Traian','94d845f9-9dbc-420a-9129-cd14a8ad7e4b'),('913dd6bb-d121-49f2-9b3b-2cf518f226b6','Sibiu',7,'Frunzis','100207d5-b908-4586-a3cb-7ae9a5ef3707'),('c0ea4a1a-7f31-48fe-b734-226fbb8d0e12','Zalau',18,'Traian','100207d5-b908-4586-a3cb-7ae9a5ef3707'),('d14ec64c-a251-4db5-9e44-a8b3a74f778f','Oradea',12,'Horea','2cc9e498-a364-436c-9978-ab78ec4dcbdc'),('d32313e7-a229-4e26-8941-83a54b1f453e','Arad',25,'Padurii','8ecf2db4-0c3c-40bf-9920-3573396da3f5'),('f7c8c047-2bcc-4b42-9133-bfcc18b5ea75','Zalau',8,'Coposu','2cc9e498-a364-436c-9978-ab78ec4dcbdc');
/*!40000 ALTER TABLE `residence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `emailAddress` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phoneNumber` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('100207d5-b908-4586-a3cb-7ae9a5ef3707','mihaig@gmail.com','George','Mihai','12345gG','0123456787','georgem'),('139d0f5a-6924-4ff1-8697-d9e4a98d90bd','sergius@gmail.com','Sergiu','Stoica','12345sS','0123456789','stiless'),('2cc9e498-a364-436c-9978-ab78ec4dcbdc','crism@yahoo.com','Cristina','Mihai','12345cC','0123456788','cristinam'),('646861d7-a9ad-4092-bd0b-64471316bd4b','anaf@yahoo.com','Ana','Filip','1212a','0987654321','anafilip'),('72aeef95-2760-4932-b6d0-326461e0394c','ferana@gmail.com','Ana','Fer','1212a','0123456789','anaf'),('8ecf2db4-0c3c-40bf-9920-3573396da3f5','ferc@gmail.com','Constantin','Fer','12345cC','1234567890','ferc'),('94d845f9-9dbc-420a-9129-cd14a8ad7e4b','hannah@yahoo.com','Hannah','Henderson','12345hH','0987654321','hannah');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-03-25 21:43:12
