-- --------------------------------------------------------
-- Anfitrião:                    localhost
-- Versão do servidor:           5.7.24 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Versão:              10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for fj21
CREATE DATABASE IF NOT EXISTS `fj21` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `fj21`;

-- Dumping structure for table fj21.contactos
CREATE TABLE IF NOT EXISTS `contactos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `endereco` varchar(255) DEFAULT NULL,
  `dataNascimento` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;

-- Dumping data for table fj21.contactos: ~5 rows (approximately)
/*!40000 ALTER TABLE `contactos` DISABLE KEYS */;
INSERT INTO `contactos` (`id`, `nome`, `email`, `endereco`, `dataNascimento`) VALUES
	(44, 'Edilson', 'edilsoncuamba@gmail.com', 'C', '1999-10-27'),
	(45, 'Joao Mateus 2', 'JM@gmail.com', 'Magwanini', '2020-06-15'),
	(46, 'Lucas Reinaldo ', 'edilsoncuamba@gmail.com', 'marracuene', '2020-06-13'),
	(47, 'Joao Mateus', 'edilsoncuamba@gmail.com', 'NxNVprXMgf', '2020-06-19'),
	(48, 'Edilson', 'dsds', 'qqweqwewq1', '2020-07-17');
/*!40000 ALTER TABLE `contactos` ENABLE KEYS */;

-- Dumping structure for table fj21.tarefa
CREATE TABLE IF NOT EXISTS `tarefa` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dataFinalizacao` date DEFAULT NULL,
  `descricao` varchar(255) DEFAULT NULL,
  `finalizado` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

-- Dumping data for table fj21.tarefa: ~1 rows (approximately)
/*!40000 ALTER TABLE `tarefa` DISABLE KEYS */;
INSERT INTO `tarefa` (`id`, `dataFinalizacao`, `descricao`, `finalizado`) VALUES
	(7, '2020-07-17', 'sadsdasdsadas', b'1');
/*!40000 ALTER TABLE `tarefa` ENABLE KEYS */;

-- Dumping structure for table fj21.tarefas
CREATE TABLE IF NOT EXISTS `tarefas` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `descricao` varchar(255) DEFAULT NULL,
  `finalizado` tinyint(1) DEFAULT NULL,
  `dataFinalizacao` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- Dumping data for table fj21.tarefas: ~1 rows (approximately)
/*!40000 ALTER TABLE `tarefas` DISABLE KEYS */;
INSERT INTO `tarefas` (`id`, `descricao`, `finalizado`, `dataFinalizacao`) VALUES
	(20, 'ssdfsd', 1, '2020-07-16');
/*!40000 ALTER TABLE `tarefas` ENABLE KEYS */;

-- Dumping structure for table fj21.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `admin` int(1) DEFAULT NULL,
  PRIMARY KEY (`username`,`password`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table fj21.usuario: ~2 rows (approximately)
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`username`, `password`, `admin`) VALUES
	('eac', '123', 1),
	('eac', 'eac', 0);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
