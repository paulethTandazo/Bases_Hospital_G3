CREATE DATABASE IF NOT EXISTS Grupo3_Hospital;
USE Grupo3_Hospital;
/*
Creando la tabla Paciente
*/
CREATE TABLE IF NOT EXISTS Paciente (
    Paciente_id CHAR(9) NOT NULL,
    Cedula INT NOT NULL UNIQUE,
    Contrasenia VARCHAR(50) NOT NULL,
    Nombre VARCHAR(50) NOT NULL,
    Apellido VARCHAR(50) NOT NULL,
    Edad INT NOT NULL,
    Fcumpleanos DATE NOT NULL,
    Direccion VARCHAR(50) NOT NULL
);
ALTER TABLE Paciente ADD CONSTRAINT Pk_Paciente PRIMARY KEY (Paciente_id);

/*
Comenzando inserciones de datos en la tabla Paciente
*/
INSERT INTO Paciente VALUES
('P00000001', 950022434, 'root', 'Pauleth', 'Tandazo', 22, STR_TO_DATE('2002-07-23', '%Y-%m-%d'), 'Centro');

/*
Creando la tabla Doctor
*/
CREATE TABLE IF NOT EXISTS Doctor (
    Doctor_id CHAR(10) NOT NULL,
    Cedula INT NOT NULL UNIQUE,
    Contrasenia VARCHAR(50) NOT NULL,
    Nombre VARCHAR(40) NOT NULL,
    Apellido VARCHAR(50) NOT NULL
);
ALTER TABLE Doctor ADD CONSTRAINT Pk_Doctor PRIMARY KEY (Doctor_id);

/*
Comenzando inserciones de datos en la tabla Doctor
*/
INSERT INTO Doctor VALUES
('D00000001', 702964545, 'admin', 'Melanie', 'Briones');

/*
Agregamos el TipoUsuario (Paciente o Doctor) para identificar por medio de un procedimiento a qué tipo pertenece cada usuario
*/
ALTER TABLE Paciente ADD COLUMN TipoUsuario VARCHAR(10) NOT NULL DEFAULT 'Paciente';
ALTER TABLE Doctor ADD COLUMN TipoUsuario VARCHAR(10) NOT NULL DEFAULT 'Doctor';

INSERT INTO Paciente VALUES
('P00000002', 950022433, 'shaggy123', 'Domenica', 'Moran', 21, STR_TO_DATE('2003-05-04', '%Y-%m-%d'), 'Mucho Lote 2', 'Paciente');
INSERT INTO Doctor VALUES
('D00000002', 923651020, '123', 'Ian', 'Cedeño', 'Doctor');

/*
Creando la tabla Especializacion
*/
CREATE TABLE IF NOT EXISTS Especializacion (
    Especializacion_id CHAR(9) NOT NULL,
    Doctor_id CHAR(9) NOT NULL,
    nombre_de_especializacion VARCHAR(30) NOT NULL,
    Descripcion_Especializacion VARCHAR(50) NOT NULL,
    anios_experiencia INT NOT NULL
   
);
/*
Consideramos pertienente trabajar con una sola tabla llamada Especialización observamos que se podía unir todo en una sola
tabla, realmente no era necesario construir 3 tablas como en el modelo original y sobre todo simplifica las consultas 
a la hora de querer consultar información sobre el doctor
*/
ALTER TABLE Especializacion ADD CONSTRAINT Pk_Especializacion PRIMARY KEY (Especializacion_id);
ALTER TABLE Especializacion ADD CONSTRAINT Fk_Doctor FOREIGN KEY(Doctor_id) REFERENCES Doctor(Doctor_id) ON DELETE CASCADE;
/*
Comenzando inserciones de datos en la tabla Especializacion
*/
INSERT INTO Especializacion VALUES ('SP0000001','D00000001', 'Cardiologo', 'Trabaja en procedimientos cardíacos y cardíacos','4');
INSERT INTO Especializacion VALUES ('SP0000002', 'D00000002','Medicina General', 'Trabaja en cualquier rama médica','3');
INSERT INTO Especializacion VALUES ('SP0000003','D00000001', 'Medico Cirujano', 'Procedimientos de cirugía','4');
select*from Especializacion;
/*
Creando la tabla Departamento
*/
CREATE TABLE IF NOT EXISTS Departamento (
    Departamento_id CHAR(9) NOT NULL,
    Nombre_Departamento VARCHAR(30) NOT NULL,
    Localizacion VARCHAR(30) NOT NULL

);
ALTER TABLE Departamento ADD CONSTRAINT Pk_Departamento PRIMARY KEY(Departamento_id);
/*
Comenzando inserciones de datos en la tabla Departamento
*/
INSERT INTO Departamento VALUES ('DP0000001', 'Departamento Cardiología', 'Planta A');
INSERT INTO Departamento VALUES ('DP0000002', 'Departamento Medicina General', 'Planta B');
INSERT INTO Departamento VALUES ('DP0000003', 'Departamento de Cirugía', 'Planta C');
SELECT * FROM Departamento;

/*
Creando la tabla DoctorxDepartamento
*/
CREATE TABLE IF NOT EXISTS DoctorxDepartamento (
    Doctor_id CHAR(10) NOT NULL,
    Departamento_id CHAR(9) NOT NULL
    );
ALTER TABLE DoctorxDepartamento ADD CONSTRAINT Pk_DoctorxDepartamento PRIMARY KEY (Doctor_id,Departamento_id);
ALTER TABLE DoctorxDepartamento ADD CONSTRAINT Fk_Doctor_Departamento FOREIGN KEY (Doctor_id) REFERENCES Doctor(Doctor_id) ON DELETE CASCADE;
ALTER TABLE DoctorxDepartamento ADD CONSTRAINT Fk_Departamento FOREIGN KEY (Departamento_id) REFERENCES Departamento(Departamento_id) ON DELETE CASCADE;
/*
Comenzando inserciones en la tabla DoctorxDepartamento
*/
INSERT INTO DoctorxDepartamento VALUES ('D00000001', 'DP0000001');
INSERT INTO DoctorxDepartamento VALUES ('D00000002', 'DP0000002');
INSERT INTO DoctorxDepartamento VALUES ('D00000001', 'DP0000003');
SELECT * FROM DoctorxDepartamento;

/*
Creando la tabla PacientexDepartamento
*/
CREATE TABLE IF NOT EXISTS PacientexDepartamento (
    Paciente_id CHAR(9) NOT NULL,
    Departamento_id CHAR(9) NOT NULL,
    Fecha_Asignacion DATE NOT NULL,
    Numero_Habitacion INT NOT NULL,
    Fecha_Alta DATE NULL
);
ALTER TABLE PacientexDepartamento ADD CONSTRAINT Pk_Paciente_Departamento PRIMARY KEY (Paciente_id,Departamento_id);
ALTER TABLE PacientexDepartamento ADD CONSTRAINT Fk_Paciente_Departamento FOREIGN KEY(Paciente_id) REFERENCES Paciente(Paciente_id) ON DELETE CASCADE;
ALTER TABLE PacientexDepartamento ADD CONSTRAINT Fk_Departamento_Paciente FOREIGN KEY(Departamento_id) REFERENCES Departamento(Departamento_id) ON DELETE CASCADE;
/*
Creando la tabla Tratamiento
*/
CREATE TABLE IF NOT EXISTS Tratamiento (
    Tratamiento_id CHAR(9) NOT NULL,
    precio_Tratamiento INT NOT NULL,
    Fecha_Inicio_Tratamiento DATE NOT NULL,
    Enfermedad_a_tratar VARCHAR(45) NOT NULL,
    Paciente_id CHAR(9) NOT NULL,
    Doctor_id CHAR(10) NOT NULL,
    Fecha_Fin_Tratamiento DATE NULL
   
);
/*
Primera observación: Un paciente podrá realizarse varios tratamientos pero estos tratamientos no podrá relalizarlos
en un mismo día sino que tendrán que ser en diferentes días, para que tenga relevancia mi relación definida en la 
tabla Tratamiento y Factura.
*/
ALTER TABLE Tratamiento ADD CONSTRAINT Pk_Tratamiento PRIMARY KEY (Tratamiento_id);
ALTER TABLE Tratamiento ADD CONSTRAINT FK_TTratamiento_Paciente FOREIGN KEY (Paciente_id) REFERENCES Paciente(Paciente_id) ON DELETE CASCADE;
ALTER TABLE Tratamiento ADD CONSTRAINT Fk_TTratamiento_Doctor FOREIGN KEY (Doctor_id) REFERENCES Doctor(Doctor_id) ON DELETE CASCADE;


/*
Creando la tabla Factura
*/
CREATE TABLE IF NOT EXISTS Factura (
    Factura_id CHAR(9) NOT NULL UNIQUE,
    Tratamiento_id CHAR(9) NOT NULL,
    Descripcion VARCHAR(45) NOT NULL,
	Fecha_emision DATE NOT NULL,
    Monto_Total DOUBLE NOT NULL -- Este valor se calculara en base a la suma del tratamiento + medicamento
);
/*
La estructura proporcionada asegura una relación 1:1 entre Factura y Tratamiento.Ahora 
nuestro modelo de negocio trabajará bajo la idea de que  unicamente el paciente solo podra realizar una sola consulta 
durante el día, por ello tratamiento debe estar asociado con una única factura y viceversa.
*/
ALTER TABLE Factura ADD CONSTRAINT Pk_Factura PRIMARY KEY (Factura_id);
ALTER TABLE Factura ADD CONSTRAINT U_Tratamiento_Factura UNIQUE (Tratamiento_id); 
ALTER TABLE Factura ADD CONSTRAINT Fk_Tratamiento_Factura FOREIGN KEY ( Tratamiento_id) REFERENCES Tratamiento(Tratamiento_id) ON DELETE CASCADE;

/*
Creando la tabla Medicamento
*/
CREATE TABLE IF NOT EXISTS Medicamento (
    Medicamento_id CHAR(9) NOT NULL,
    Valor_Medicamento DECIMAL(10, 2) NOT NULL,
    Nombre VARCHAR(45) NOT NULL,
    Unidad VARCHAR(45) NOT NULL 
  
);
ALTER TABLE Medicamento ADD CONSTRAINT Pk_Medicamento PRIMARY KEY (Medicamento_id);
/*
Creando la tabla TratamientoXMedicamento recordar que
*/
CREATE TABLE IF NOT EXISTS TratamientoXMedicamento (
    Tratamiento_id CHAR(9) NOT NULL,
    Medicamento_id CHAR(9) NOT NULL
);
ALTER TABLE TratamientoXMedicamento ADD CONSTRAINT Pk_Tratamiento_Medicamento PRIMARY KEY (Tratamiento_id,Medicamento_id);
ALTER TABLE TratamientoXMedicamento ADD CONSTRAINT Fk_Tratamiento_Medicamento FOREIGN KEY (Tratamiento_id) REFERENCES Tratamiento(Tratamiento_id);
ALTER TABLE TratamientoXMedicamento ADD CONSTRAINT Fk_Medicamento_Tratamiento FOREIGN KEY (Medicamento_id) REFERENCES Medicamento(Medicamento_id);











-- Esto es para el código en java
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
