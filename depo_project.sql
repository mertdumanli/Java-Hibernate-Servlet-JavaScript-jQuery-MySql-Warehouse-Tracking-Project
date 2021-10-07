/*
 Navicat Premium Data Transfer

 Source Server         : local-Mysql
 Source Server Type    : MySQL
 Source Server Version : 100421
 Source Host           : localhost:3306
 Source Schema         : depo_project

 Target Server Type    : MySQL
 Target Server Version : 100421
 File Encoding         : 65001

 Date: 03/09/2021 22:19:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `ad_id` int NOT NULL AUTO_INCREMENT,
  `ad_email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ad_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ad_password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `ad_surname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`ad_id`) USING BTREE,
  UNIQUE INDEX `UK_lws3ngac6dakyjap6blfitfhe`(`ad_email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES (1, 'admin@mail.com', 'adminName', 'c20ad4d76fe97759aa27a0c99bff6710', 'adminSurname');

-- ----------------------------
-- Table structure for adminfollow
-- ----------------------------
DROP TABLE IF EXISTS `adminfollow`;
CREATE TABLE `adminfollow`  (
  `adminFollow_id` int NOT NULL AUTO_INCREMENT,
  `localDateTime` datetime NULL DEFAULT NULL,
  `status` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `admin_ad_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`adminFollow_id`) USING BTREE,
  INDEX `FKrf113n6l5dy69dh0f7qr3mxrs`(`admin_ad_id`) USING BTREE,
  CONSTRAINT `FKrf113n6l5dy69dh0f7qr3mxrs` FOREIGN KEY (`admin_ad_id`) REFERENCES `admin` (`ad_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of adminfollow
-- ----------------------------
INSERT INTO `adminfollow` VALUES (1, '2021-09-03 17:14:28', 'Giriş', 1);
INSERT INTO `adminfollow` VALUES (2, '2021-09-03 18:27:41', 'Giriş', 1);
INSERT INTO `adminfollow` VALUES (3, '2021-09-03 18:31:57', 'Çıkış', 1);
INSERT INTO `adminfollow` VALUES (4, '2021-09-03 18:32:08', 'Giriş', 1);
INSERT INTO `adminfollow` VALUES (5, '2021-09-03 18:32:29', 'Çıkış', 1);
INSERT INTO `adminfollow` VALUES (7, '2021-09-03 18:33:43', 'Giriş', 1);
INSERT INTO `adminfollow` VALUES (8, '2021-09-03 18:33:53', 'Çıkış', 1);
INSERT INTO `adminfollow` VALUES (9, '2021-09-03 18:35:35', 'Giriş', 1);
INSERT INTO `adminfollow` VALUES (10, '2021-09-03 19:22:20', 'Çıkış', 1);
INSERT INTO `adminfollow` VALUES (11, '2021-09-03 19:22:35', 'Giriş', 1);
INSERT INTO `adminfollow` VALUES (12, '2021-09-03 21:05:22', 'Çıkış', 1);
INSERT INTO `adminfollow` VALUES (13, '2021-09-03 21:05:46', 'Giriş', 1);
INSERT INTO `adminfollow` VALUES (14, '2021-09-03 21:48:29', 'Giriş', 1);

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer`  (
  `cu_id` int NOT NULL AUTO_INCREMENT,
  `cu_address` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cu_code` int NULL DEFAULT NULL,
  `cu_company_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cu_email` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cu_isAvailable` bit(1) NOT NULL,
  `cu_mobile` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cu_password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cu_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cu_status` int NOT NULL,
  `cu_surname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cu_tax_administration` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cu_tax_number` int NOT NULL,
  PRIMARY KEY (`cu_id`) USING BTREE,
  UNIQUE INDEX `UK_1j6q0dsu86iwkwetb32pwbpqd`(`cu_code`) USING BTREE,
  FULLTEXT INDEX `ftxtindex`(`cu_name`, `cu_surname`)
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES (1, 'deneme1', 1, '', '', b'0', '1', 'Ahmet', '', '', 1, 'Bilmez', '', 0);
INSERT INTO `customer` VALUES (2, '', 333, '', '', b'1', '12', 'mert', '', '21', 1, 'dumanlı', '', 0);
INSERT INTO `customer` VALUES (3, 'deneme1', 111111, '', '', b'1', '1', 'yılmaz', '', '', 1, 'dumanlı', '', 0);

-- ----------------------------
-- Table structure for interlayertopayin
-- ----------------------------
DROP TABLE IF EXISTS `interlayertopayin`;
CREATE TABLE `interlayertopayin`  (
  `in_id` int NOT NULL,
  `in_balance` bigint NOT NULL,
  `in_total` bigint NOT NULL,
  `voucherNo` int NOT NULL,
  PRIMARY KEY (`in_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of interlayertopayin
-- ----------------------------

-- ----------------------------
-- Table structure for interlayertopaymenthistory
-- ----------------------------
DROP TABLE IF EXISTS `interlayertopaymenthistory`;
CREATE TABLE `interlayertopaymenthistory`  (
  `pa_id` int NOT NULL,
  `in_balance` bigint NOT NULL,
  `in_id` int NOT NULL,
  `in_total` bigint NOT NULL,
  `pa_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pa_localDateTime` datetime NULL DEFAULT NULL,
  `pa_paid` bigint NOT NULL,
  `voucherNo` int NOT NULL,
  PRIMARY KEY (`pa_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of interlayertopaymenthistory
-- ----------------------------

-- ----------------------------
-- Table structure for interlayertopaymenthistorywithcustomerinfo
-- ----------------------------
DROP TABLE IF EXISTS `interlayertopaymenthistorywithcustomerinfo`;
CREATE TABLE `interlayertopaymenthistorywithcustomerinfo`  (
  `pa_id` int NOT NULL,
  `cu_code` bigint NOT NULL,
  `cu_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cu_surname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `in_balance` bigint NOT NULL,
  `in_total` bigint NOT NULL,
  `pa_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pa_localDateTime` datetime NULL DEFAULT NULL,
  `pa_paid` bigint NOT NULL,
  `voucherNo` int NOT NULL,
  PRIMARY KEY (`pa_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of interlayertopaymenthistorywithcustomerinfo
-- ----------------------------

-- ----------------------------
-- Table structure for invoice
-- ----------------------------
DROP TABLE IF EXISTS `invoice`;
CREATE TABLE `invoice`  (
  `in_id` int NOT NULL AUTO_INCREMENT,
  `in_balance` bigint NOT NULL,
  `in_depoMaliyeti` bigint NOT NULL,
  `in_total` bigint NOT NULL,
  PRIMARY KEY (`in_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of invoice
-- ----------------------------
INSERT INTO `invoice` VALUES (1, 1150, 1000, 1200);
INSERT INTO `invoice` VALUES (2, 18846, 11600, 19850);

-- ----------------------------
-- Table structure for invoice_vouchers
-- ----------------------------
DROP TABLE IF EXISTS `invoice_vouchers`;
CREATE TABLE `invoice_vouchers`  (
  `Invoice_in_id` int NOT NULL,
  `vouchersList_vo_id` int NOT NULL,
  UNIQUE INDEX `UK_20y22py91ddgtpr0ug5ems516`(`vouchersList_vo_id`) USING BTREE,
  INDEX `FK8093f4k9fotumudwdggfmcjf4`(`Invoice_in_id`) USING BTREE,
  CONSTRAINT `FK4tbowc38b2busetlhs8lg3jla` FOREIGN KEY (`vouchersList_vo_id`) REFERENCES `vouchers` (`vo_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK8093f4k9fotumudwdggfmcjf4` FOREIGN KEY (`Invoice_in_id`) REFERENCES `invoice` (`in_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of invoice_vouchers
-- ----------------------------
INSERT INTO `invoice_vouchers` VALUES (1, 1);
INSERT INTO `invoice_vouchers` VALUES (2, 2);
INSERT INTO `invoice_vouchers` VALUES (2, 3);
INSERT INTO `invoice_vouchers` VALUES (2, 4);

-- ----------------------------
-- Table structure for payments
-- ----------------------------
DROP TABLE IF EXISTS `payments`;
CREATE TABLE `payments`  (
  `pa_id` int NOT NULL AUTO_INCREMENT,
  `in_balance` bigint NOT NULL,
  `pa_detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pa_localDateTime` datetime NULL DEFAULT NULL,
  `pa_paid` bigint NOT NULL,
  `in_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`pa_id`) USING BTREE,
  INDEX `FKrql2843vh49dl2xjf151bxk3s`(`in_id`) USING BTREE,
  CONSTRAINT `FKrql2843vh49dl2xjf151bxk3s` FOREIGN KEY (`in_id`) REFERENCES `invoice` (`in_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of payments
-- ----------------------------
INSERT INTO `payments` VALUES (4, 19849, '', '2021-09-03 18:59:19', 1, 2);
INSERT INTO `payments` VALUES (5, 19848, '', '2021-09-03 18:59:21', 1, 2);
INSERT INTO `payments` VALUES (6, 19847, '', '2021-09-03 18:59:23', 1, 2);
INSERT INTO `payments` VALUES (7, 19846, '', '2021-09-03 18:59:25', 1, 2);
INSERT INTO `payments` VALUES (10, 18846, '', '2021-09-03 19:31:29', 1000, 2);
INSERT INTO `payments` VALUES (14, 1190, '', '2021-09-03 22:06:48', 10, 1);
INSERT INTO `payments` VALUES (15, 1180, '', '2021-09-03 22:06:51', 10, 1);
INSERT INTO `payments` VALUES (16, 1150, '', '2021-09-03 22:06:54', 30, 1);

-- ----------------------------
-- Table structure for paymentsout
-- ----------------------------
DROP TABLE IF EXISTS `paymentsout`;
CREATE TABLE `paymentsout`  (
  `op_id` int NOT NULL AUTO_INCREMENT,
  `localDateTime` datetime NULL DEFAULT NULL,
  `op_detail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `op_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `op_total` bigint NOT NULL,
  `op_type` int NOT NULL,
  `ad_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`op_id`) USING BTREE,
  INDEX `FKtbh1td5rlpam2xnqbhuasdvoa`(`ad_id`) USING BTREE,
  CONSTRAINT `FKtbh1td5rlpam2xnqbhuasdvoa` FOREIGN KEY (`ad_id`) REFERENCES `admin` (`ad_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paymentsout
-- ----------------------------
INSERT INTO `paymentsout` VALUES (3, '2021-09-03 20:51:53', '', 'Mert\'e para çıkışı', 100, 1, 1);
INSERT INTO `paymentsout` VALUES (4, '2021-09-03 20:52:19', 'Ali\'ye nakit para verildi.', 'Ali', 1000, 1, 1);
INSERT INTO `paymentsout` VALUES (5, '2021-09-03 20:56:38', 'Yılmaz bey', 'Yılmaz', 11000, 5, 1);

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `pr_id` int NOT NULL AUTO_INCREMENT,
  `pr_amount` int NOT NULL,
  `pr_code` int NULL DEFAULT NULL,
  `pr_detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pr_isAvailable` bit(1) NOT NULL,
  `pr_purchasePrice` int NOT NULL,
  `pr_salePrice` int NOT NULL,
  `pr_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `pr_unit` int NOT NULL,
  `pr_vat` int NOT NULL,
  PRIMARY KEY (`pr_id`) USING BTREE,
  UNIQUE INDEX `UK_ruxm46uxkr6eoayy7qln46rkw`(`pr_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1, 0, 123, '', b'1', 1000, 1200, 'Buzdolabı', 1, 1);
INSERT INTO `product` VALUES (2, 5, 111111, '', b'1', 1500, 2000, 'TV', 1, 1);
INSERT INTO `product` VALUES (3, 99, 684131745, '', b'1', 10000, 15000, 'Telefon', 1, 4);
INSERT INTO `product` VALUES (4, 49, 684155355, '', b'1', 100, 150, 'Masa', 4, 1);
INSERT INTO `product` VALUES (5, 0, 684180707, '', b'0', 100, 120, 'Sopa', 1, 2);
INSERT INTO `product` VALUES (6, 0, 684204435, '', b'0', 1000, 1200, 'Buzdolabı', 1, 1);
INSERT INTO `product` VALUES (7, 0, 684238380, '', b'0', 100, 151, 'Su', 2, 1);
INSERT INTO `product` VALUES (8, 5, 684290395, 'Pahalı', b'1', 15000, 25000, 'Akıllı Telefon', 1, 4);

-- ----------------------------
-- Table structure for purchaseorders
-- ----------------------------
DROP TABLE IF EXISTS `purchaseorders`;
CREATE TABLE `purchaseorders`  (
  `po_id` int NOT NULL AUTO_INCREMENT,
  `number` int NOT NULL,
  `status` bit(1) NOT NULL,
  `voucherNo` int NOT NULL,
  `cu_id` int NULL DEFAULT NULL,
  `pr_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`po_id`) USING BTREE,
  INDEX `FKf9hheh1gjsqe9kdvj7r1b5srq`(`cu_id`) USING BTREE,
  INDEX `FKh8wgk8k43hs106k4gccev8jx8`(`pr_id`) USING BTREE,
  CONSTRAINT `FKf9hheh1gjsqe9kdvj7r1b5srq` FOREIGN KEY (`cu_id`) REFERENCES `customer` (`cu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FKh8wgk8k43hs106k4gccev8jx8` FOREIGN KEY (`pr_id`) REFERENCES `product` (`pr_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of purchaseorders
-- ----------------------------
INSERT INTO `purchaseorders` VALUES (10, 4, b'1', 681887332, 1, 2);
INSERT INTO `purchaseorders` VALUES (13, 1, b'1', 682084908, 2, 1);
INSERT INTO `purchaseorders` VALUES (14, 6, b'1', 681887332, 1, 1);
INSERT INTO `purchaseorders` VALUES (15, 1, b'1', 684637677, 1, 3);
INSERT INTO `purchaseorders` VALUES (16, 1, b'1', 684637677, 1, 2);
INSERT INTO `purchaseorders` VALUES (17, 1, b'1', 684637677, 1, 4);

-- ----------------------------
-- Table structure for vouchers
-- ----------------------------
DROP TABLE IF EXISTS `vouchers`;
CREATE TABLE `vouchers`  (
  `vo_id` int NOT NULL AUTO_INCREMENT,
  `alisFiyati` int NOT NULL,
  `kdv` int NOT NULL,
  `satisFiyati` int NOT NULL,
  `po_id` int NULL DEFAULT NULL,
  PRIMARY KEY (`vo_id`) USING BTREE,
  INDEX `FKr32ud38kidhd1mlttodtiib36`(`po_id`) USING BTREE,
  CONSTRAINT `FKr32ud38kidhd1mlttodtiib36` FOREIGN KEY (`po_id`) REFERENCES `purchaseorders` (`po_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of vouchers
-- ----------------------------
INSERT INTO `vouchers` VALUES (1, 1000, 1, 1200, 13);
INSERT INTO `vouchers` VALUES (2, 10000, 4, 15000, 15);
INSERT INTO `vouchers` VALUES (3, 1500, 1, 2000, 16);
INSERT INTO `vouchers` VALUES (4, 100, 1, 150, 17);

-- ----------------------------
-- View structure for customercount
-- ----------------------------
DROP VIEW IF EXISTS `customercount`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `customercount` AS SELECT
	COUNT( cu_id ) AS count 
FROM
	customer 
WHERE
	cu_isAvailable = 1 ;

-- ----------------------------
-- View structure for lefttable
-- ----------------------------
DROP VIEW IF EXISTS `lefttable`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `lefttable` AS SELECT
	pr_id,
	pr_amount,
	pr_code,
	pr_purchasePrice,
	pr_salePrice,
	pr_title,
	pr_vat 
FROM
	product 
WHERE
	pr_isAvailable = 1 
	AND pr_amount > 0 
ORDER BY
	pr_salePrice DESC 
	LIMIT 5 ;

-- ----------------------------
-- View structure for ortmaliyetsatisdegeri
-- ----------------------------
DROP VIEW IF EXISTS `ortmaliyetsatisdegeri`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `ortmaliyetsatisdegeri` AS SELECT
	avg( pr_purchasePrice ) AS totalMaliyet,
	avg( pr_salePrice ) AS satisDegeri 
FROM
	product 
WHERE
	pr_isAvailable = 1 ;

-- ----------------------------
-- View structure for payintotal
-- ----------------------------
DROP VIEW IF EXISTS `payintotal`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `payintotal` AS SELECT
	SUM( pa_paid ) AS payintotal 
FROM
	payments ;

-- ----------------------------
-- View structure for payintotaltoday
-- ----------------------------
DROP VIEW IF EXISTS `payintotaltoday`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `payintotaltoday` AS SELECT
	SUM( pa_paid ) AS totalPayIn 
FROM
	payments 
WHERE
	`pa_localDateTime` BETWEEN NOW() - INTERVAL 1 DAY 
	AND NOW() ;

-- ----------------------------
-- View structure for payouttotal
-- ----------------------------
DROP VIEW IF EXISTS `payouttotal`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `payouttotal` AS SELECT
	sum( op_total ) AS payouttotal 
FROM
	paymentsout ;

-- ----------------------------
-- View structure for payouttotaltoday
-- ----------------------------
DROP VIEW IF EXISTS `payouttotaltoday`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `payouttotaltoday` AS SELECT
	sum( op_total ) AS totalPayOut 
FROM
	paymentsout 
WHERE
	`localDateTime` BETWEEN NOW() - INTERVAL 1 DAY 
	AND NOW() ;

-- ----------------------------
-- View structure for productcount
-- ----------------------------
DROP VIEW IF EXISTS `productcount`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `productcount` AS SELECT
	COUNT( pr_id ) AS count 
FROM
	product 
WHERE
	pr_isAvailable = 1 ;

-- ----------------------------
-- View structure for righttable
-- ----------------------------
DROP VIEW IF EXISTS `righttable`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `righttable` AS SELECT
	voucherNo AS voucher,
	CONCAT( cu_name, " ", cu_surname ) AS `name`,
	in_total AS tutar 
FROM
	customer AS c
	INNER JOIN purchaseorders AS po ON c.cu_id = po.cu_id
	INNER JOIN vouchers AS v ON v.po_id = po.po_id
	INNER JOIN invoice_vouchers AS iv ON iv.vouchersList_vo_id = v.vo_id
	INNER JOIN invoice AS i ON i.in_id = iv.Invoice_in_id 
GROUP BY
	voucher DESC 
	LIMIT 5 ;

-- ----------------------------
-- View structure for totalsales
-- ----------------------------
DROP VIEW IF EXISTS `totalsales`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `totalsales` AS select SUM(in_total) as totalsales from invoice ;

-- ----------------------------
-- Procedure structure for CompleteSaleProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `CompleteSaleProcedure`;
delimiter ;;
CREATE PROCEDURE `CompleteSaleProcedure`(IN `i1` int)
BEGIN

update purchaseorders SET `status` = 1 WHERE voucherNo = i1;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for FullTextIndexProcedure
-- ----------------------------
DROP PROCEDURE IF EXISTS `FullTextIndexProcedure`;
delimiter ;;
CREATE PROCEDURE `FullTextIndexProcedure`(IN `i1` varchar(255))
BEGIN

SELECT * FROM customer WHERE MATCH(cu_name, cu_surname) against (i1 in boolean mode) ;

END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for SearchProductOneCustomer
-- ----------------------------
DROP PROCEDURE IF EXISTS `SearchProductOneCustomer`;
delimiter ;;
CREATE PROCEDURE `SearchProductOneCustomer`(IN `i1` int,IN `i2` int)
BEGIN
	
	select * from purchaseorders WHERE cu_id = `i1` and pr_id = `i2` AND status=0;

END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
