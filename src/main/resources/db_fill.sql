insert into ont.pure_chemical_substance values (1, 'Neon', 'Ne', '');
insert into ont."state" values (1, 'liquid-solid');
insert into ont.data_source values (1, 'Теплофизические свойства неона, аргона, криптона и ксенона. Рабинович В.А. и др. М., Изд-во стандартов, 1976, с. 636');
insert into ont.uncertainty_type values (1,'Standart');
insert into ont.uncertainty_type values (2,'Standart,relative to %');

insert into ont.crystal_structure values (1, 'blank');
insert into ont.magnetic_structure values (1, 'blank');

--insert into ont.physical_quantity_roles values (1, 'arg');
--insert into ont.physical_quantity_roles values (2, 'cnst');
--insert into ont.physical_quantity_roles values (3, 'func');
--insert into ont.physical_quantity_roles values (4, 'scnst'); --константы характеризующие вещество

insert into ont.dimension values (1, 'K', 'K');
insert into ont.dimension values (2, 'Bar', 'Bar');
insert into ont.dimension values (3, 'm3/kg', 'm3/kg');

insert into ont.physical_quantity values (1, 'T', 'Temperature', 'arg');
insert into ont.physical_quantity values (2, 'P', 'Pressure', 'arg'); --по умлчанию все функции от 2х аргументов (T,P)
insert into ont.physical_quantity values (3, 'Pmelt', 'PressureOfMelting', 'func');
insert into ont.physical_quantity values (4, 'Vliquid', 'VolumeOfLiquidPhase', 'func');
insert into ont.physical_quantity values (5, 'Vsolid', 'VolumeOfSolidPhase', 'func');

--в таблицу вносится информация только о константах и функциях
--T
insert into ont.quantity_state values (1, 3, 1, 1, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (1, 4, 1, 1, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (1, 5, 1, 1, -10000, 10000, -10000, 10000);
--P
insert into ont.quantity_state values (1, 3, 2, 2, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (1, 4, 2, 2, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (1, 5, 2, 2, -10000, 10000, -10000, 10000);

insert into ont.quantity_dimension values (1,1);
insert into ont.quantity_dimension values (2,2);
insert into ont.quantity_dimension values (3,2);
insert into ont.quantity_dimension values (4,3);
insert into ont.quantity_dimension values (5,3);

-- {substance_id, state_id} - уникальная пара
insert into ont.substance_in_state values (1, 1, 1, 1, 'blank', 1);

insert into ont.data_set values (1, 1, 'docs_1-5', 'table', 'xls', false);

--=====
--{property_id, row_num, data_set_id} -- уникальный набор, в базе почему-то ограничение не создалось
-- 1 строка таблицы
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (1, 1, 1, 1, 1, 1, 25);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (3, 1, 1, 3, 1, 2, 30.45);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (4, 1, 1, 4, 1, 3, 0.79667E-3);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (5, 1, 1, 5, 1, 3, 0.6979E-3);
-- 2 строка таблицы
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (6, 1, 2, 1, 1, 1, 26);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (8, 1, 2, 3, 1, 2, 98.38);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (9, 1, 2, 4, 1, 3, 0.7876E-3);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (10, 1, 2, 5, 1, 3, 0.6942E-3);
--3 строка таблицы
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (11, 1, 3, 1, 1, 1, 27);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (13, 1, 3, 3, 1, 2, 168.0);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (14, 1, 3, 4, 1, 3, 0.7793E-3);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (15, 1, 3, 5, 1, 3, 0.6904E-3);
-- 4 строка таблицы
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (16, 1, 4, 1, 1, 1, 28);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (18, 1, 4, 3, 1, 2, 239.3);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (19, 1, 4, 4, 1, 3, 0.7717E-3);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (20, 1, 4, 5, 1, 3, 0.6867E-3);
-- 5 строка таблицы
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (21, 1, 5, 1, 1, 1, 29);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (23, 1, 5, 3, 1, 2, 312.2);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (24, 1, 5, 4, 1, 3, 0.7645E-3);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (25, 1, 5, 5, 1, 3, 0.6831E-3);
-- 6 строка таблицы
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (26, 1, 6, 1, 1, 1, 30);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (28, 1, 6, 3, 1, 2, 386.7);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (29, 1, 6, 4, 1, 3, 0.7579E-3);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (30, 1, 6, 5, 1, 3, 0.6796E-3);

-- Pmelt
insert into ont.measurement_uncertainty values (1,'1',3, 1);
insert into ont.measurement_uncertainty values (2,'1',8, 1);
insert into ont.measurement_uncertainty values (3,'1',13, 1);
insert into ont.measurement_uncertainty values (4,'1',18, 1);
insert into ont.measurement_uncertainty values (5,'1',23, 1);
insert into ont.measurement_uncertainty values (6,'1',28, 1);
--Vliquid
insert into ont.measurement_uncertainty values (7,'0.15',4, 2);
insert into ont.measurement_uncertainty values (8,'0.15',9, 2);
insert into ont.measurement_uncertainty values (9,'0.15',14, 2);
insert into ont.measurement_uncertainty values (10,'0.15',19, 2);
insert into ont.measurement_uncertainty values (11,'0.15',24, 2);
insert into ont.measurement_uncertainty values (12,'0.15',29, 2);
--Vsolid
insert into ont.measurement_uncertainty values (13,'0.05',5, 2);
insert into ont.measurement_uncertainty values (14,'0.05',10, 2);
insert into ont.measurement_uncertainty values (15,'0.05',15, 2);
insert into ont.measurement_uncertainty values (16,'0.05',20, 2);
insert into ont.measurement_uncertainty values (17,'0.05',25, 2);
insert into ont.measurement_uncertainty values (18,'0.05',30, 2);

--Neon In TriplePoint/CriticalPoint
insert into ont.physical_quantity values (6, 'Ttr', 'TemperatureInTriplePoint', 'cnst');
insert into ont.physical_quantity values (7, 'Ptr', 'PressureInTriplePoint', 'cnst');
insert into ont.physical_quantity values (8, 'Tc', 'TemperatureInCriticalPoint', 'cnst');
insert into ont.physical_quantity values (9, 'Pc', 'PressureInCriticalPoint', 'cnst');

insert into ont."state" values (2, 'triple point', 'triple point');
insert into ont."state" values (3, 'critical point', 'critical point');

insert into ont.quantity_state values (2,6, NULL, NULL, 0, 0, -10000, 10000);
insert into ont.quantity_state values (2,7, NULL, NULL, 0, 0, -10000, 10000);
insert into ont.quantity_state values (3,8, NULL, NULL, 0, 0, -10000, 10000);
insert into ont.quantity_state values (3,9, NULL, NULL, 0, 0, -10000, 10000);

insert into ont.dimension values (4,'atm','atm');

insert into ont.quantity_dimension values (6,1);
insert into ont.quantity_dimension values (7,4);
insert into ont.quantity_dimension values (8,1);
insert into ont.quantity_dimension values (9,4);

insert into ont.substance_in_state values (2, 1, 1, 1, 'blank', 2);
insert into ont.substance_in_state values (3, 1, 1, 1, 'blank', 3);

insert into ont.data_set values (2, 2, 'docs_1-5', 'table', 'xls', false);
insert into ont.data_set values (3, 3, 'docs_1-5', 'table', 'xls', false);
--row_num = 0 -- соотвествует константам в наборе
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (31, 2, 0, 6, 1, 1, 25.55);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (32, 2, 0, 7, 1, 4, 0.427);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (33, 3, 0, 8, 1, 1, 44.4);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (34, 3, 0, 9, 1, 4, 26.2);

insert into ont.measurement_uncertainty values (19,'0.01',31, 1);
insert into ont.measurement_uncertainty values (20,'0.001',32, 1);
insert into ont.measurement_uncertainty values (21,'0.02',33, 1);
insert into ont.measurement_uncertainty values (22,'0.05',34, 1);

--Neon In Gas
insert into ont.physical_quantity values (10, 'B', 'SecondVirialСoefficient', 'func');

insert into ont."state" values (4, 'Gas');

--T
insert into ont.quantity_state values (4,10, 1, 1, -10000, 10000, -10000, 10000);
--P
insert into ont.quantity_state values (4,10, 2, 2, -10000, 10000, -10000, 10000);

insert into ont.dimension values (5, 'cm3/mol', 'cm3/mol');

insert into ont.quantity_dimension values (10,5);

insert into ont.data_source values (2, 'Landolt-Börnstein Numerical Data and Functional Relationships in Science and Technology Virial Coefficients of Pure Gases J. H. Dymond, K. N. Marsh, R. C. Wilhoit, K. C. Wong Edited by M. Frenkel and K.N. Marsh');
insert into ont.data_source values (3, 'Gibbons R.M.  Cryogenics 9 (1969) 251');
insert into ont.data_source values (4, 'Holborn L., Otto J. Z. Phys. 33 (1925) 1');

insert into ont.uncertainty_type values (3, 'Extended with a significance level of 95%');
insert into ont.uncertainty_type values (4, 'Deviation from the approximating expression');

insert into ont.substance_in_state values (4, 1, 1, 1, 'blank', 4);
insert into ont.data_set values (4, 4, 'docs_1-5', 'table', 'xls', false);
insert into ont.data_set values (5, 4, 'docs_1-5', 'table', 'xls', false);
--data_set = 4
--row_num = 1
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (35, 4, 1, 1, 2, 1, 50);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (36, 4, 1, 10, 2, 5, -36.4);
--row_num = 2
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (37, 4, 2, 1, 2, 1, 60);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (38, 4, 2, 10, 2, 5, -25.3);
--row_num = 3
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (39, 4, 3, 1, 2, 1, 75);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (40, 4, 3, 10, 2, 5, -14.4);
--row_num = 4
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (41, 4, 4, 1, 2, 1, 90);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (42, 4, 4, 10, 2, 5, -7.6);
--row_num = 5
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (43, 4, 5, 1, 2, 1, 110);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (44, 4, 5, 10, 2, 5, -1.9);
--row_num = 6
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (45, 4, 6, 1, 2, 1, 130);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (46, 4, 6, 10, 2, 5, 1.8);
--row_num = 7
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (47, 4, 7, 1, 2, 1, 160);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (48, 4, 7, 10, 2, 5, 5.3);
--row_num = 8
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (49, 4, 8, 1, 2, 1, 200);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (50, 4, 8, 10, 2, 5, 8.1);

insert into ont.measurement_uncertainty values (23,'1',36, 3);
insert into ont.measurement_uncertainty values (24,'1',38, 3);
insert into ont.measurement_uncertainty values (25,'1',40, 3);
insert into ont.measurement_uncertainty values (26,'1',42, 3);
insert into ont.measurement_uncertainty values (27,'1',44, 3);
insert into ont.measurement_uncertainty values (28,'1',46, 3);
insert into ont.measurement_uncertainty values (29,'1',48, 3);
insert into ont.measurement_uncertainty values (30,'1',50, 3);

--data_set = 5
--row_num = 1
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (51, 5, 1, 1, 3, 1, 44);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (52, 5, 1, 10, 3, 5, -46.1);
--row_num = 2
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (53, 5, 2, 1, 3, 1, 46);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (54, 5, 2, 10, 3, 5, -42.2);
--row_num = 3
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (55, 5, 3, 1, 3, 1, 50);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (56, 5, 3, 10, 3, 5, -35.4);
--row_num = 4
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (57, 5, 4, 1, 3, 1, 60);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (58, 5, 4, 10, 3, 5, -24.9);
--row_num = 5
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (59, 5, 5, 1, 4, 1, 62.25);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (60, 5, 5, 10, 4, 5, -21.0);
--row_num = 6
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (61, 5, 6, 1, 3, 1, 70);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (62, 5, 6, 10, 3, 5, -17.1);
--row_num = 7
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (63, 5, 7, 1, 4, 1, 90.65);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (64, 5, 7, 10, 4, 5, -8.2);

insert into ont.measurement_uncertainty values (31,'1.5',52, 1);
insert into ont.measurement_uncertainty values (32,'-0.8',52, 4);
insert into ont.measurement_uncertainty values (33,'1.4',54, 1);
insert into ont.measurement_uncertainty values (34,'-0.1',54, 4);
insert into ont.measurement_uncertainty values (35,'1.4',56, 1);
insert into ont.measurement_uncertainty values (36,'1.0',56, 4);
insert into ont.measurement_uncertainty values (37,'1.3',58, 1);
insert into ont.measurement_uncertainty values (38,'0.4',58, 4);
insert into ont.measurement_uncertainty values (39,'1.0',60, 1);
insert into ont.measurement_uncertainty values (40,'-0.1',60, 4);
insert into ont.measurement_uncertainty values (42,'1.2',62, 1);
insert into ont.measurement_uncertainty values (43,'0.4',62, 4);
insert into ont.measurement_uncertainty values (44,'1.0',64, 1);
insert into ont.measurement_uncertainty values (45,'-0.8',64, 4);

insert into ont.pure_chemical_substance values (2, 'iron', 'Fe', '');

insert into ont.physical_quantity values (11, 'Cp', 'IsobaricHeatСapacity', 'func');

insert into ont.uncertainty_type values (5, 'Precision class');

insert into ont.data_source values (5, 'Ivtantermo DB');

insert into ont.dimension values (6, 'J/mol/K', 'J/mol/K');

insert into ont.quantity_dimension values (11, 6);

insert into ont."state" values (5, 'bcc-fm');
insert into ont."state" values (6, 'bcc-pm');
insert into ont."state" values (7, 'fcc-pm');
insert into ont."state" values (8, 'liquid');

--T
insert into ont.quantity_state values (5,11, 1, 1, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (6,11, 1, 1, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (7,11, 1, 1, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (8,11, 1, 1, -10000, 10000, -10000, 10000);
--P
insert into ont.quantity_state values (5,11, 2, 2, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (6,11, 2, 2, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (7,11, 2, 2, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (8,11, 2, 2, -10000, 10000, -10000, 10000);

insert into ont.substance_in_state values(5, 2, 1, 1, 'blank', 5);
insert into ont.substance_in_state values(6, 2, 1, 1, 'blank', 6);
insert into ont.substance_in_state values(7, 2, 1, 1, 'blank', 7);
insert into ont.substance_in_state values(8, 2, 1, 1, 'blank', 8);

insert into ont.data_set values (6, 5, 'docs_6_7', 'table', 'xls', false);
insert into ont.data_set values (7, 6, 'docs_6_7', 'table', 'xls', false);
insert into ont.data_set values (8, 7, 'docs_6_7', 'table', 'xls', false);
insert into ont.data_set values (9, 8, 'docs_6_7', 'table', 'xls', false);

--data_set = 6 //bcc-ferromagnetic
--row_num = 1
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (65, 6, 1, 1, 5, 1, 298.15);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (66, 6, 1, 11, 5, 6, 25.1);
--row_num = 2
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (67, 6, 2, 1, 5, 1, 300);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (68, 6, 2, 11, 5, 6, 25.136);
--row_num = 3
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (69, 6, 3, 1, 5, 1, 400);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (70, 6, 3, 11, 5, 6, 27.436);
--row_num = 4
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (71, 6, 4, 1, 5, 1, 500);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (72, 6, 4, 11, 5, 6, 29.718);
--row_num = 5
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (73, 6, 5, 1, 5, 1, 600);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (74, 6, 5, 11, 5, 6, 31.942);
--row_num = 6
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (75, 6, 6, 1, 5, 1, 700);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (76, 6, 6, 11, 5, 6, 34.528);
--row_num = 7
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (77, 6, 7, 1, 5, 1, 800);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (78, 6, 7, 11, 5, 6, 38.037);
--row_num = 8
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (79, 6, 8, 1, 5, 1, 900);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (80, 6, 8, 11, 5, 6, 43.162);
--row_num = 9
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (81, 6, 9, 1, 5, 1, 1000);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (82, 6, 9, 11, 5, 6, 55.328);
--row_num = 10
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (83, 6, 10, 1, 5, 1, 1042);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (84, 6, 10, 11, 5, 6, 71.824);

--P = 1 atm
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (115, 6, 1, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (116, 6, 2, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (117, 6, 3, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (118, 6, 4, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (119, 6, 5, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (120, 6, 6, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (121, 6, 7, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (122, 6, 8, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (123, 6, 9, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (124, 6, 10, 2, 5, 4, 1);

--data_set = 7 //bcc-paramagnetic
--row_num = 1
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (85, 7, 1, 1, 5, 1, 1042);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (86, 7, 1, 11, 5, 6, 62.767);
--row_num = 2
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (87, 7, 2, 1, 5, 1, 1100);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (88, 7, 2, 11, 5, 6, 46.082);
--row_num = 3
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (89, 7, 3, 1, 5, 1, 1184);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (90, 7, 3, 11, 5, 6, 40.906);
--row_num = 4
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (105, 7, 4, 1, 5, 1, 1665);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (106, 7, 4, 11, 5, 6, 41.113);
--row_num = 5
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (107, 7, 5, 1, 5, 1, 1700);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (108, 7, 5, 11, 5, 6, 41.464);
--row_num = 6
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (109, 7, 6, 1, 5, 1, 1800);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (110, 7, 6, 11, 5, 6, 42.489);
--row_num = 7
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (111, 7, 7, 1, 5, 1, 1809);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (112, 7, 7, 11, 5, 6, 42.559);

-- P = 1 atm
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (125, 7, 1, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (126, 7, 2, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (127, 7, 3, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (128, 7, 4, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (129, 7, 5, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (130, 7, 6, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (131, 7, 7, 2, 5, 4, 1);


--data_set = 8 //fcc-paramagnetic
--row_num = 1
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (91, 8, 1, 1, 5, 1, 1184);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (92, 8, 1, 11, 5, 6, 34.075);
--row_num = 2
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (93, 8, 2, 1, 5, 1, 1200);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (94, 8, 2, 11, 5, 6, 34.208);
--row_num = 3
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (95, 8, 3, 1, 5, 1, 1300);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (96, 8, 3, 11, 5, 6, 35.038);
--row_num = 4
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (97, 8, 4, 1, 5, 1, 1400);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (98, 8, 4, 11, 5, 6, 35.885);
--row_num = 5
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (99, 8, 5, 1, 5, 1, 1500);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (100, 8, 5, 11, 5, 6, 36.693);
--row_num = 6
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (101, 8, 6, 1, 5, 1, 1600);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (102, 8, 6, 11, 5, 6, 37.521);
--row_num = 7
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (103, 8, 7, 1, 5, 1, 1665);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (104, 8, 7, 11, 5, 6, 38.060);

-- P = 1 atm
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (132, 8, 1, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (133, 8, 2, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (134, 8, 3, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (135, 8, 4, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (136, 8, 5, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (137, 8, 6, 2, 5, 4, 1);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (138, 8, 7, 2, 5, 4, 1);


--data_set = 9 //liquid
--row_num = 1
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (113, 9, 1, 1, 5, 1, 1809);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (114, 9, 1, 11, 5, 6, 48.0);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (139, 9, 1, 2, 5, 4, 1);

--select * from measure_uncertainties
--поскольку неопределенность на весь набор, то соответственно она распространяется и на аргументы (id 65 -- 139)
drop function if exists ont.ins_unc_Fe();
create or replace function ont.ins_uns_Fe ()
	RETURNS void LANGUAGE plpgsql AS
	$$
	declare
	i int;
	j int;
	unc_val character varying(255);
	begin
		j := 46;
		i := 65;
		unc_val := quote_literal('4-D');
		while (i <= 139)
		loop
			EXECUTE format('insert into ont.measurement_uncertainty values (%1$s, %2$s, %3$s, 5)', j, unc_val, i);
			j := j+1;
			i := i+1;
		end loop;
		return;
	end
	$$;

select ont.ins_uns_Fe ();

-- insert info abount transition constants
insert into ont."state" values (9, 'bcc-fm -> bcc-pm', 'bcc-fm -> bcc-pm');
insert into ont."state" values (10, 'bcc -> fcc', 'bcc -> fcc');
insert into ont."state" values (11, 'fcc -> bcc', 'fcc -> bcc');
insert into ont."state" values (12, 'bcc -> liquid', 'bcc -> liquid');

insert into ont.physical_quantity values (12, 'Ttrans', 'TemperatureOfTransition', 'cnst');
insert into ont.physical_quantity values (13, 'DHtrans', 'EnthalpyOfTransition', 'cnst');

insert into ont.quantity_state values ( 9,12, NULL, NULL, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values ( 9,13, NULL, NULL, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (10,12, NULL, NULL, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (10,13, NULL, NULL, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (11,12, NULL, NULL, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (11,13, NULL, NULL, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (12,12, NULL, NULL, -10000, 10000, -10000, 10000);
insert into ont.quantity_state values (12,13, NULL, NULL, -10000, 10000, -10000, 10000);

insert into ont.dimension values (7, 'kJ/mol', 'kJ/mol');

insert into ont.quantity_dimension values (12, 1);
insert into ont.quantity_dimension values (13, 7);

insert into ont.substance_in_state values(9, 2, 1, 1, 'blank', 9);
insert into ont.substance_in_state values(10, 2, 1, 1, 'blank', 10);
insert into ont.substance_in_state values(11, 2, 1, 1, 'blank', 11);
insert into ont.substance_in_state values(12, 2, 1, 1, 'blank', 12);

insert into ont.data_set values (10, 9, 'docs_6_7', 'table', 'xls', false);
insert into ont.data_set values (11, 10, 'docs_6_7', 'table', 'xls', false);
insert into ont.data_set values (12, 11, 'docs_6_7', 'table', 'xls', false);
insert into ont.data_set values (13, 12, 'docs_6_7', 'table', 'xls', false);


insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (140, 10, 0, 12, 5, 1, 1042);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (141, 10, 0, 13, 5, 7, 0.001);

insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (142, 11, 0, 12, 5, 1, 1184);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (143, 11, 0, 13, 5, 7, 0.903);

insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (144, 12, 0, 12, 5, 1, 1665);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (145, 12, 0, 13, 5, 7, 0.841);

insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (146, 13, 0, 12, 5, 1, 1809);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (147, 13, 0, 13, 5, 7, 13.801);

insert into ont.measurement_uncertainty values (121, '4-D', 140, 5);
insert into ont.measurement_uncertainty values (122, '4-D', 141, 5);
insert into ont.measurement_uncertainty values (123, '4-D', 142, 5);
insert into ont.measurement_uncertainty values (124, '4-D', 143, 5);
insert into ont.measurement_uncertainty values (125, '4-D', 144, 5);
insert into ont.measurement_uncertainty values (126, '4-D', 145, 5);
insert into ont.measurement_uncertainty values (127, '4-D', 146, 5);
insert into ont.measurement_uncertainty values (128, '4-D', 147, 5);

--insert data abount O[2+]
insert into ont.pure_chemical_substance values (3, 'oxygen positive ion', 'O[2+]', '');

insert into ont."state" values (13, 'IdGas', 'IdGas');
insert into ont.physical_quantity values (14, 'DHF(0)', 'EnthalpyOfFormationAt0K', 'cnst');
insert into ont.physical_quantity values (15, 'I', 'IonizationPotential', 'scnst');
insert into ont.physical_quantity values (16, 'Z', 'Charge', 'scnst');

insert into ont.quantity_state values (13,14, NULL, NULL, -10000, 10000, -10000, 10000);

--свойства характеризующие только вещество
insert into ont.substance_quantity values (15,3);
insert into ont.substance_quantity values (16,3);

insert into ont.dimension values (8, 'eV', 'eV');

insert into ont.quantity_dimension values (14, 7);
insert into ont.quantity_dimension values (15, 8);

insert into ont.data_source values (6, 'База данных «Термические свойства веществ». ОИВТ РАН, Хим. фак-т МГУ.');

insert into ont.substance_in_state values(13, 3, 1, 1, 'blank', 13);

insert into ont.data_set values (14, 13, '5.XLS','table', 'xls', false);

insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (148, 14, 0, 14, 6, 7, 4949.044);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (149, 14, 0, 15, 6, 8, 54.9);
insert into ont.point_of_measure (id, data_set_id, row_num, quantity_id, data_source_id, dimension_id, quantity_value)
values (150, 14, 0, 16, 6, null, 2);

insert into ont.measurement_uncertainty values (129, '0.711', 148, 1);
insert into ont.measurement_uncertainty values (130, '0.015', 149, 1);

insert into ont.control_function_definition values (1, 1, '<math><apply><geq/><ci>T</ci><cn>0</cn></apply></math>', 1, 10);
insert into ont.domain_of_control_function_definition values (1, 1, 1, 1);

insert into ont.function_definition values (1, 1, '<math><apply><times/><ci>T</ci><cn>2</cn></apply></math>', 1, 1, 10, 1);
insert into ont.domain_of_function_definition values (1, 1, 1, 'Monotonic', 0, 10000, 1);
/*
insert into ont.control_function_definition values (1, 1,
						 '<math>' ||
						 '    <apply\n>' ||
						 '        <leq/>' ||
                         '        <apply>\n' ||
                         '            <abs/>\n' ||
                         '            <apply>\n' ||
                         '                <minus/>\n' ||
                         '                <ci>G</ci>\n' ||
                         '                <apply>\n' ||
                         '                    <minus/>\n' ||
                         '                    <ci>H</ci>\n' ||
                         '                    <apply>\n' ||
                         '                        <times/>\n' ||
                         '                        <ci>T</ci>\n' ||
                         '                        <ci>S</ci>\n' ||
                         '                    </apply>\n' ||
                         '                </apply>\n' ||
                         '            </apply>\n' ||
                         '        <ci>eps</ci>' ||
                         '        </apply>\n' ||
                         '    </apply>\n' ||
                         '</math>', 1, 10);
*/
