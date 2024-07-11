create database Base_Hospital;
use Base_Hospital;
CREATE TABLE PATIENT 
    (Cedula         CHAR(10)     NOT NULL,
     Nombre         VARCHAR(50)  NOT NULL,
	 Apellido		VARCHAR(50)	 NOT NULL,
     Edad           INT          NOT NULL,
     Fcumpleanos    DATE         NOT NULL,
     Direccion      VARCHAR(50)  NOT NULL,
     PRIMARY KEY(Cedula));
     