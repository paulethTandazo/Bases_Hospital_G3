create database Base_Hospital;
use Base_Hospital;
CREATE TABLE Paciente 
    (Paciente_id	CHAR(9)		    NOT NULL,
	 Cedula         INT			    NOT NULL,
	 Contrasenia	VARCHAR(50)		NOT NULL,
     Nombre         VARCHAR(50)  	NOT NULL,
	 Apellido		VARCHAR(50)	 	NOT NULL,
     Edad           INT          	NOT NULL,
     Fcumpleanos    DATE         	NOT NULL,
     Direccion      VARCHAR(50)  	NOT NULL,
     
     PRIMARY KEY(Paciente_id));

CREATE TABLE Doctor
    (Doctor_id  	CHAR(10)     	NOT NULL,
	 Cedula			INT				NOT NULL,
     contrasenia	varchar(50)		NOT NULL,
     Nombre      	VARCHAR(40) 	NOT NULL,
     Apellido	 	Varchar(50) 	NOT NULL,
     PRIMARY KEY (Doctor_id));
CREATE TABLE Medicamento
    (id_medicamento  CHAR(10)     	NOT NULL,
     Nombre          VARCHAR(40) 	NOT NULL,
     Precio          INT         	NOT NULL,
     PRIMARY KEY(id_medicamento));
ALTER TABLE Paciente ADD COLUMN TipoUsuario VARCHAR(10) NOT NULL DEFAULT 'Paciente';
ALTER TABLE Doctor ADD COLUMN TipoUsuario VARCHAR(10) NOT NULL DEFAULT 'Doctor';

/*
Comenzando Insercciones de datos en la tabla pacientes
*/
INSERT INTO Paciente VALUES
('P00000001',0950022434,'root','Pauleth', 'Tandazo',22, STR_TO_DATE('2002-07-23', '%Y-%m-%d'), 'Centro');

/*
Comenzando Insercciones de datos en la tabla doctores
*/
INSERT INTO Doctor VALUES
('D00000001',0702964545,'admin','Melanie','Briones');

/*
Procedimiento almacenado Paciente
*/
DELIMITER //

CREATE FUNCTION logInPaciente (inputCedula INT, inputPassword VARCHAR(50))
RETURNS BOOLEAN
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE userCount INT;
    DECLARE logeado BOOLEAN;

    SELECT COUNT(Paciente_id) INTO userCount 
    FROM Paciente 
    WHERE Cedula = inputCedula AND Contrasenia = inputPassword;

    IF userCount >= 1 THEN 
        SET logeado = TRUE;
    ELSE
        SET logeado = FALSE;
    END IF;

    RETURN logeado;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS almacenadoLogInPaciente;
DELIMITER //

CREATE PROCEDURE almacenadoLogInPaciente (
    IN inputCedula INT,
    IN inputPassword VARCHAR(50),
    OUT logeado BOOLEAN
)
BEGIN
    START TRANSACTION;
    SELECT logInPaciente(inputCedula, inputPassword) INTO logeado;
    COMMIT;
END //

DELIMITER ;
/*
Procedimiento para el doctor
*/
DELIMITER //

CREATE FUNCTION logInDoctor (inputCedula INT, inputPassword VARCHAR(50))
RETURNS BOOLEAN
READS SQL DATA
DETERMINISTIC
BEGIN
    DECLARE userCount INT;
    DECLARE logeado BOOLEAN;

    SELECT COUNT(Doctor_id) INTO userCount 
    FROM Doctor 
    WHERE Cedula = inputCedula AND Contrasenia = inputPassword;

    IF userCount >= 1 THEN 
        SET logeado = TRUE;
    ELSE
        SET logeado = FALSE;
    END IF;

    RETURN logeado;
END //

DELIMITER ;

DROP PROCEDURE IF EXISTS almacenadoLogInDoctor;
DELIMITER //

CREATE PROCEDURE almacenadoLogInDoctor (
    IN inputCedula INT,
    IN inputPassword VARCHAR(50),
    OUT logeado BOOLEAN
)
BEGIN
    START TRANSACTION;
    SELECT logInDoctor(inputCedula, inputPassword) INTO logeado;
    COMMIT;
END //

DELIMITER ;



select * from Paciente;
select * from Doctor;
select * from Medicamento;
drop table Paciente;
drop table Doctor;
drop table Medicamento;