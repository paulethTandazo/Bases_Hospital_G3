create database Base_Hospital;
use Base_Hospital;
CREATE TABLE Paciente 
    (Cedula         CHAR(10)     	NOT NULL,
     Nombre         VARCHAR(50)  	NOT NULL,
	 Apellido		VARCHAR(50)	 	NOT NULL,
     Edad           INT          	NOT NULL,
     Fcumpleanos    DATE         	NOT NULL,
     Direccion      VARCHAR(50)  	NOT NULL,
     PRIMARY KEY(Cedula));
CREATE TABLE Doctor
    (Cedula_Doctor  CHAR(10)     	NOT NULL,
     Nombre      	VARCHAR(40) 	NOT NULL,
     Apellido	 	Varchar(50) 	NOT NULL,
     PRIMARY KEY (Cedula_Doctor));
CREATE TABLE Medicamento
    (id_medicamento  CHAR(10)     	NOT NULL,
     Nombre          VARCHAR(40) 	NOT NULL,
     Precio          INT         	NOT NULL,
     PRIMARY KEY(id_medicamento));



select * from Paciente;
select * from Doctor;
select * from Medicamento;
drop table Paciente;
drop table Doctor;
drop table Medicamento;