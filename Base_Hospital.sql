CREATE DATABASE Base_Hospital;
USE Base_Hospital;

/*
Creando la tabla Paciente
*/
CREATE TABLE Paciente (
    Paciente_id CHAR(9) NOT NULL,
    Cedula INT NOT NULL,
    Contrasenia VARCHAR(50) NOT NULL,
    Nombre VARCHAR(50) NOT NULL,
    Apellido VARCHAR(50) NOT NULL,
    Edad INT NOT NULL,
    Fcumpleanos DATE NOT NULL,
    Direccion VARCHAR(50) NOT NULL,
    PRIMARY KEY (Paciente_id)
);

/*
Comenzando inserciones de datos en la tabla Paciente
*/
INSERT INTO Paciente VALUES
('P00000001', 950022434, 'root', 'Pauleth', 'Tandazo', 22, STR_TO_DATE('2002-07-23', '%Y-%m-%d'), 'Centro');

/*
Creando la tabla Doctor
*/
CREATE TABLE Doctor (
    Doctor_id CHAR(10) NOT NULL,
    Cedula INT NOT NULL,
    Contrasenia VARCHAR(50) NOT NULL,
    Nombre VARCHAR(40) NOT NULL,
    Apellido VARCHAR(50) NOT NULL,
    PRIMARY KEY (Doctor_id)
);

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
CREATE TABLE Especializacion (
    Especializacion_id CHAR(9) NOT NULL,
    nombre VARCHAR(30) NOT NULL,
    Descripcion VARCHAR(50) NOT NULL,
    PRIMARY KEY (Especializacion_id)
);

/*
Comenzando inserciones de datos en la tabla Especializacion
*/
INSERT INTO Especializacion VALUES ('SP0000001', 'Cardiologo', 'Trabaja en procedimientos cardíacos y cardíacos');
INSERT INTO Especializacion VALUES ('SP0000002', 'Medicina General', 'Trabaja en cualquier rama médica');
INSERT INTO Especializacion VALUES ('SP0000003', 'Medico Cirujano', 'Procedimientos de cirugía');

/*
Creando la tabla Titulacion
*/
CREATE TABLE Titulacion (
    Doctor_ID CHAR(10) NOT NULL,
    Especializacion_id CHAR(9) NOT NULL,
    Years_exp INT NOT NULL,
    PRIMARY KEY (Doctor_ID, Especializacion_id),
    FOREIGN KEY (Doctor_ID) REFERENCES Doctor(Doctor_id) ON DELETE CASCADE,
    FOREIGN KEY (Especializacion_id) REFERENCES Especializacion(Especializacion_id) ON DELETE CASCADE
);

ALTER TABLE Titulacion CHANGE Years_exp Anio_Experiencia int not null;


/*
Comenzando inserciones de datos en la tabla Titulacion
*/
INSERT INTO Titulacion VALUES ('D00000001', 'SP0000001', 2);
INSERT INTO Titulacion VALUES ('D00000001', 'SP0000002', 4);
INSERT INTO Titulacion VALUES ('D00000002', 'SP0000002', 10);
SELECT * FROM Titulacion;

/*
Creando la tabla Departamento
*/
CREATE TABLE Departamento (
    Departamento_id CHAR(9) NOT NULL,
    Nombre_Departamento VARCHAR(30) NOT NULL,
    Localizacion VARCHAR(30) NOT NULL,
    PRIMARY KEY (Departamento_id)
);

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
CREATE TABLE DoctorxDepartamento (
    Doctor_id CHAR(10) NOT NULL,
    Departamento_id CHAR(9) NOT NULL,
    PRIMARY KEY (Doctor_id, Departamento_id),
    FOREIGN KEY (Doctor_id) REFERENCES Doctor(Doctor_id) ON DELETE CASCADE,
    FOREIGN KEY (Departamento_id) REFERENCES Departamento(Departamento_id) ON DELETE CASCADE
);

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
CREATE TABLE PacientexDepartamento (
    Paciente_id CHAR(9) NOT NULL,
    Departamento_id CHAR(9) NOT NULL,
    Fecha_Asignacion DATE NOT NULL,
    Numero_Habitacion INT NOT NULL,
    Fecha_Alta DATE NOT NULL,
    PRIMARY KEY (Paciente_id, Departamento_id),
    FOREIGN KEY (Paciente_id) REFERENCES Paciente(Paciente_id) ON DELETE CASCADE,
    FOREIGN KEY (Departamento_id) REFERENCES Departamento(Departamento_id) ON DELETE CASCADE
);

/*
Creando la tabla Tratamiento
*/
CREATE TABLE Tratamiento (
    Tratamiento_id CHAR(9) NOT NULL,
    Fecha_Inicio_Tratamiento DATE NOT NULL,
    Enfermedad_a_tratar VARCHAR(45) NOT NULL,
    Paciente_id CHAR(9) NOT NULL,
    Doctor_id CHAR(10) NOT NULL,
    Fecha_Fin_Tratamiento DATE NULL,
    PRIMARY KEY (Tratamiento_id),
    FOREIGN KEY (Paciente_id) REFERENCES Paciente(Paciente_id) ON DELETE CASCADE,
    FOREIGN KEY (Doctor_id) REFERENCES Doctor(Doctor_id) ON DELETE CASCADE
);

/*
Creando la tabla Factura
*/
CREATE TABLE Factura (
    Factura_id CHAR(9) NOT NULL,
    Tratamiento_id CHAR(9) NOT NULL,
    Descripcion_Servicio VARCHAR(45) NOT NULL,
    Valor_a_pagar DOUBLE NOT NULL,
    PRIMARY KEY (Factura_id),
    UNIQUE (Tratamiento_id),
    FOREIGN KEY (Tratamiento_id) REFERENCES Tratamiento(Tratamiento_id) ON DELETE CASCADE
);

/*
Creando la tabla Medicamento
*/
CREATE TABLE Medicamento (
    Medicamento_id CHAR(9) NOT NULL,
    Nombre VARCHAR(45) NOT NULL,
    Unidad VARCHAR(45) NOT NULL,
    PRIMARY KEY (Medicamento_id)
);

/*
Creando la tabla TratamientoXMedicamento
*/
CREATE TABLE TratamientoXMedicamento (
    Tratamiento_id CHAR(9) NOT NULL,
    Medicamento_id CHAR(9) NOT NULL,
    PRIMARY KEY (Tratamiento_id, Medicamento_id),
    FOREIGN KEY (Tratamiento_id) REFERENCES Tratamiento(Tratamiento_id) ON DELETE CASCADE,
    FOREIGN KEY (Medicamento_id) REFERENCES Medicamento(Medicamento_id) ON DELETE CASCADE
);

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
