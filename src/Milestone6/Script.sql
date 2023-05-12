 -- Database creation

 CREATE DATABASE `milestone6`;



-- Photographers definition

CREATE TABLE `Photographers` (
  `PhotographerId` int(11) NOT NULL,
  `Name` varchar(100) DEFAULT NULL,
  `Awared` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`PhotographerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- Pictures definition

CREATE TABLE `Pictures` (
  `PictureId` int(11) NOT NULL,
  `Title` varchar(100) DEFAULT NULL,
  `Date` date DEFAULT NULL,
  `File` varchar(100) DEFAULT NULL,
  `Visits` int(11) DEFAULT NULL,
  `PhotographerId` int(11) DEFAULT NULL,
  PRIMARY KEY (`PictureId`),
  KEY `Pictures_FK` (`PhotographerId`),
  CONSTRAINT `Pictures_FK` FOREIGN KEY (`PhotographerId`) REFERENCES `Photographers` (`PhotographerId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- Photographers data input

INSERT INTO milestone6.Photographers (PhotographerId,Name,Awared) VALUES
	 (1,'ansealdams',1),
	 (2,'rothko',0),
	 (3,'vangogh',1);


	 -- Pictures data input

INSERT INTO Pictures (PictureId,Title,`Date`,File,Visits,PhotographerId) VALUES
	 (1,'mountain','1998-02-06','ansealdams1.jpg',0,1),
	 (2,'rocks','2000-05-03','ansealdams2.jpg',0,1),
	 (3,'rectangles','2018-09-22','rothko1.jpg',0,2),
	 (4,'starry Night','1889-06-01','vangogh1.jpg',0,3),
	 (5,'Wheat field with cypresses','1890-01-01','vangogh2.jpg',0,3);
