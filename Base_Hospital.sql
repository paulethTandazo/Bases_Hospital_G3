create database Base_Hospital;
use Base_Hospital;
CREATE TABLE Paciente 
    (Paciente_id CHAR(9) NOT NULL,
	 Cedula INT NOT NULL,
	 Contrasenia VARCHAR(50) NOT NULL,
     Nombre VARCHAR(50) NOT NULL,
	 Apellido VARCHAR(50) NOT NULL,
     Edad INT NOT NULL,
     Fcumpleanos DATE NOT NULL,
     Direccion VARCHAR(50) NOT NULL,
     
     PRIMARY KEY(Paciente_id));
     
CREATE TABLE Doctor
    (Doctor_id CHAR(10) NOT NULL,
	 Cedula INT	NOT NULL,
     contrasenia varchar(50) NOT NULL,
     Nombre VARCHAR(40) NOT NULL,
     Apellido Varchar(50) NOT NULL,
     PRIMARY KEY (Doctor_id));

ALTER TABLE Paciente ADD COLUMN TipoUsuario VARCHAR(10) NOT NULL DEFAULT 'Paciente';
ALTER TABLE Doctor ADD COLUMN TipoUsuario VARCHAR(10) NOT NULL DEFAULT 'Doctor';

/*
Comenzando Insercciones de datos en la tabla pacientes
*/
INSERT INTO Paciente VALUES
('P00000001',0950022434,'root','Pauleth', 'Tandazo',22, STR_TO_DATE('2002-07-23', '%Y-%m-%d'), 'Centro');
INSERT INTO Paciente VALUES
('P00000002',0950022433,'shaggy123','Domenica', 'Moran',21,STR_TO_DATE('2003-05-04', '%Y-%m-%d'), 'Mucho Lote 2','Paciente');
/*
Comenzando Insercciones de datos en la tabla doctores
*/
INSERT INTO Doctor VALUES
('D00000001',0702964545,'admin','Melanie','Briones');

CREATE TABLE Especializacion
    (Especializacion_id	 CHAR(9) NOT NULL,
     nombre VARCHAR(30) NOT NULL,
     Descripcion VARCHAR(50) NOT NUll,
     PRIMARY KEY(Especializacion_id));
CREATE TABLE Especialidades 
    (Doctor_ID CHAR(9) NOT NULL,
     Spec_ID CHAR(9) NOT NULL,
     Years_exp INT NOT NULL,
     PRIMARY KEY (Doctor_ID, Spec_ID),
     FOREIGN KEY (Doctor_ID) REFERENCES Doctor(Doctor_id ) ON DELETE CASCADE,
     FOREIGN KEY (Spec_ID)   REFERENCES Especializacion(Especializacion_id) ON DELETE CASCADE);
/*
Cambiando el nombre de la tabla "Especialidades" al de "Experiencia"
*/
ALTER TABLE Especialidades RENAME TO Experiencia;
/*
Creando tabla que tiene la relación entre doctor y departamento
*/
 create table DoctorxDepartamento (
	Doctor_id CHAR(9) NOT NULL,
    Departamento_id CHAR(9) NOT NULL
 );
 ALTER TABLE DoctorxDepartamento ADD PRIMARY KEY (Doctor_id,Departamento_id);
 ALTER TABLE DoctorxDepartamento ADD FOREIGN KEY (Doctor_id) REFERENCES Doctor(Doctor_id) ON DELETE CASCADE;
 ALTER TABLE DoctorxDepartamento ADD FOREIGN KEY (Departamento_id) REFERENCES Departamento(Departamento_id) ON DELETE CASCADE;



 CREATE TABLE Departamento
	(Departamento_id		CHAR(9)		NOT NULL,
 	 Nombre_Departamento	VARCHAR(30) 	NOT NULL,
	 Localizacion			VARCHAR(30)		NOT NULL
	);
ALTER TABLE Departamento ADD PRIMARY KEY (Departamento_id);
 CREATE TABLE PacientexDepartamento
	 (Paciente_id CHAR(9) NOT NULL,
      Departamento_id CHAR (9) NOT NULL,
	  Fecha_Asignación DATE NOT NULL,
      Numero_Habitacion INT NOT NULL,
      Fecha_Alta DATE NOT NULL
      );
ALTER TABLE PacientexDepartamento ADD PRIMARY KEY (Paciente_id,Departamento_id);
ALTER TABLE PacientexDepartamento ADD FOREIGN KEY (Paciente_id) REFERENCES Paciente(Paciente_id) ON DELETE CASCADE;
ALTER TABLE PacientexDepartamento ADD FOREIGN KEY (Departamento_id) REFERENCES Departamento(Departamento_id) ON DELETE CASCADE;    
    

CREATE TABLE Medicamento
    (id_medicamento  CHAR(10)     	NOT NULL,
     Nombre          VARCHAR(40) 	NOT NULL,
     Precio          INT         	NOT NULL,
     PRIMARY KEY(id_medicamento));
drop table Medicamento;
 /*
Comenzando Insercciones de datos en la tabla Especializacion
*/ 
INSERT INTO Especializacion VALUES ('SP0000001', 'Cardiologo', 'Trabaja en procedimientos cardíacos y cardíacos');


/*
Comenzando Insercciones de datos en la tabla Experiencia, tener en cuanto que la tabla
"Especialidades" ahora se llama "Experiencia"
*/
INSERT INTO Especialidades VALUES ('D00000001', 'SP0000001', 2);

  
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

/*
Comprobando si las querys se realizan con éxito
*/
SELECT * FROM Paciente WHERE Cedula=0950022434;
select * from Doctor where cedula=0702964545;
select * from Experiencia where Doctor_id='D00000001';
SELECT * FROM Especializacion WHERE Especializacion_id = 'SP0000001';

select * from Paciente;
select * from Doctor;
select * from Especializacion;
select * from Experiencia;
select * from Medicamento;
