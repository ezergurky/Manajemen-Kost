/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE DATABASE IF NOT EXISTS `kost` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `kost`;

CREATE TABLE IF NOT EXISTS `kamar` (
  `id_kamar` int NOT NULL AUTO_INCREMENT,
  `id_kost` int DEFAULT NULL,
  `nomor_kamar` varchar(10) DEFAULT NULL,
  `tipe_kamar` varchar(50) DEFAULT NULL,
  `fasilitas` text,
  `harga` double DEFAULT NULL,
  `status` enum('tersedia','terisi') DEFAULT 'tersedia',
  PRIMARY KEY (`id_kamar`),
  KEY `id_kost` (`id_kost`),
  CONSTRAINT `kamar_ibfk_1` FOREIGN KEY (`id_kost`) REFERENCES `kost` (`id_kost`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `kontrak_sewa` (
  `id_kontrak` int NOT NULL AUTO_INCREMENT,
  `id_penghuni` int DEFAULT NULL,
  `id_kamar` int DEFAULT NULL,
  `tanggal_mulai` date DEFAULT NULL,
  `tanggal_selesai` date DEFAULT NULL,
  `status` enum('aktif','selesai') DEFAULT 'aktif',
  PRIMARY KEY (`id_kontrak`),
  KEY `id_penghuni` (`id_penghuni`),
  KEY `id_kamar` (`id_kamar`),
  CONSTRAINT `kontrak_sewa_ibfk_1` FOREIGN KEY (`id_penghuni`) REFERENCES `penghuni` (`id_penghuni`) ON DELETE CASCADE,
  CONSTRAINT `kontrak_sewa_ibfk_2` FOREIGN KEY (`id_kamar`) REFERENCES `kamar` (`id_kamar`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `kost` (
  `id_kost` int NOT NULL AUTO_INCREMENT,
  `nama_kost` varchar(100) DEFAULT NULL,
  `alamat` text,
  PRIMARY KEY (`id_kost`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `pembayaran` (
  `id_pembayaran` int NOT NULL AUTO_INCREMENT,
  `id_tagihan` int DEFAULT NULL,
  `tanggal_bayar` date DEFAULT NULL,
  `metode` varchar(50) DEFAULT NULL,
  `jumlah_bayar` double DEFAULT NULL,
  PRIMARY KEY (`id_pembayaran`),
  KEY `id_tagihan` (`id_tagihan`),
  CONSTRAINT `pembayaran_ibfk_1` FOREIGN KEY (`id_tagihan`) REFERENCES `tagihan` (`id_tagihan`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `penghuni` (
  `id_penghuni` int NOT NULL AUTO_INCREMENT,
  `id_user` int DEFAULT NULL,
  `nik` varchar(16) DEFAULT NULL,
  `no_hp` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_penghuni`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `penghuni_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `tagihan` (
  `id_tagihan` int NOT NULL AUTO_INCREMENT,
  `id_penghuni` int DEFAULT NULL,
  `bulan` varchar(20) DEFAULT NULL,
  `tahun` int DEFAULT NULL,
  `jatuh_tempo` date DEFAULT NULL,
  `jumlah` double DEFAULT NULL,
  `denda` double DEFAULT NULL,
  `status` enum('lunas','belum') DEFAULT 'belum',
  PRIMARY KEY (`id_tagihan`),
  KEY `id_penghuni` (`id_penghuni`),
  CONSTRAINT `tagihan_ibfk_1` FOREIGN KEY (`id_penghuni`) REFERENCES `penghuni` (`id_penghuni`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `role` enum('admin','penghuni') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
