CREATE DATABASE IF NOT EXISTS `infosildb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `infosildb`;

-- Tabla de cursos
DROP TABLE IF EXISTS `tbcursos`;
CREATE TABLE `tbcursos` (
  `ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(150) NOT NULL,
  `CODE` char(50) NOT NULL,
  `CREDITOS` int(10) NOT NULL,
  `PERIODOANO` year,
  `PERIODONUM` int(1),
  `FECHAMOD` datetime,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7;
-- Insertando valores por defecto
insert into tbcursos (`ID`,`NOMBRE`, `CODE`, `CREDITOS`, `FECHAMOD`) values
	(1, "PROGRAMACIÓN ORIENTADA A OBJETOS", "FC-INF PROGOROBJ", 2, '2018-11-24 23:11:00'),
	(2, "ENGLISH II", "FC-IDI ENG02", 4, '2018-11-24 23:11:00'),
	(3, "CÁLCULO DE UNA VARIABLE", "FC-FBA CAL01VAR", 4, '2018-11-24 23:11:00'),
    (4, "TALLER DE SOFTWARE I", "FC-INF TALLSFW1", 2, '2018-11-24 23:11:00'),
    (5, "BUSINESS PROCESS MANAGEMENT", "FC-INF BUSPROMG", 4, '2018-11-24 23:11:00'),
    (6, "LENGUAJE II", "FC-FBA LENG02", 4, '2018-11-24 23:11:00')
    ;
    
-- Tabla que almacena los tipos de notas existentes
DROP TABLE IF EXISTS `tbcomponentes_estructuras`;
CREATE TABLE `tbcomponentes_estructuras` (
	`ID` int(10) unsigned NOT NULL auto_increment,
    -- `ISNOTA` bool NOT NULL,
    `NAME` varchar(75) NOT NULL,
    primary key (`ID`)
) engine=InnoDB auto_increment=4;
-- Insertando valores por defecto a la tabla de componentes de estructuras
insert into `tbcomponentes_estructuras` (`ID`,`ISNOTA`, `NAME`) values
	(1, "Evaluación Permanente"),
	(2, "Examen Parcial"),
	(3, "Examen Final")
	;
    
-- Tabla que almacena las estructuras
DROP TABLE IF EXISTS `tbestructuras`;
CREATE TABLE `tbestructuras` (
	`ID` int(10) unsigned NOT NULL auto_increment,
    `ESTRUCTURA` JSON,
    primary key(`ID`)
) engine=InnoDB auto_increment=7;
-- Insertando valores por defecto a la tabla de componentes de estructuras
insert into `tbestructuras` (`ID`,`ESTRUCTURA`) values
	(1, '[
		[1,0.6,[
			["Promedio de Evaluaciones",0.6,[
				["Actividad 01",0.5],
				["Actividad 02",0.5]
			]],
            ["Trabajo Final",0.4]
		]],
        [2,0.2],
        [3,0.2]
	]'),
    (2, '[
		[1,0.5,[
			["Promedio de Controles",0.3,[
				["Control 1"],
                ["Control 2"]
            ]],
            ["Promedio de Prácticas",0.7,[
				["Práctica 1"],
                ["Práctica 2"],
                ["Práctica 3"],
                ["Práctica 4"]
            ]]
        ]],
        [2,0.2],
        [3,0.3]
    ]'),
    (3, '[
		[1,0.4,[
			["Promedio Controles",0.5,[
				["Control 1"],
                ["Control 2"]
            ]],
            ["Promedio de Evaluaciones",0.5,[
				["Promedio 1"],
                ["Promedio 2"]
            ]]
        ]],
        [2,0.25],
        [3,0.35]
    ]'),
    (4, '[
		[1,0.5,[
			["Promedio de Prácticas",0.6,[
				["Práctica 1"],
                ["Práctica 2"]
            ]],
            ["Trabajos",0.4]
        ]],
        [2,0.25],
        [3,0.25]
    ]')
	;