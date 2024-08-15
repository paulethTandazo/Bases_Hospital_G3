CREATE DATABASE IF NOT EXISTS Grupo3_Hospital;
USE Grupo3_Hospital;

/*
Creando la tabla Paciente
*/
CREATE TABLE IF NOT EXISTS Paciente (
    Paciente_id CHAR(9) ,
    Cedula INT ,
    Contrasenia VARCHAR(45) ,
    Nombre VARCHAR(45) ,
    Apellido VARCHAR(45) ,
    Edad INT ,
    Fcumpleanos DATE ,
    Direccion VARCHAR(45) 
);
ALTER TABLE Paciente     
	MODIFY Paciente_id CHAR(9) NOT NULL,
    MODIFY Cedula INT NOT NULL,
    MODIFY Contrasenia VARCHAR(50) NOT NULL,
    MODIFY Nombre VARCHAR(50) NOT NULL,
    MODIFY Apellido VARCHAR(50) NOT NULL,
    MODIFY Edad INT NOT NULL,
    MODIFY Fcumpleanos DATE NOT NULL,
    MODIFY Direccion VARCHAR(50) NOT NULL,
    ADD CONSTRAINT paciente_cedula UNIQUE (Cedula),
	ADD CONSTRAINT Pk_Paciente PRIMARY KEY (Paciente_id);
/*
Insertando datos en la tabla Paciente
*/
INSERT INTO Paciente VALUES
('P00000001', 950022434, 'root', 'Pauleth', 'Tandazo', 22, STR_TO_DATE('2002-07-23', '%Y-%m-%d'), 'Centro');
/*
Creando la tabla Doctor
*/
CREATE TABLE IF NOT EXISTS Doctor (
    Doctor_id CHAR(9) ,
    Cedula INT ,
    Contrasenia VARCHAR(45) ,
    Nombre VARCHAR(45),
    Apellido VARCHAR(45)
);
ALTER TABLE Doctor
MODIFY Cedula INT NOT NULL,
ADD CONSTRAINT uc_doctor_cedula UNIQUE (Cedula),
MODIFY Contrasenia VARCHAR(50) NOT NULL,
MODIFY Nombre VARCHAR(40) NOT NULL,
MODIFY Apellido VARCHAR(50) NOT NULL,
ADD CONSTRAINT pk_doctor PRIMARY KEY (Doctor_id);
/*
Insertando datos en la tabla Doctor
*/
INSERT INTO Doctor VALUES
('D00000001', 702964545, 'admin', 'Melanie', 'Briones');
/*
Agregamos el TipoUsuario (Paciente o Doctor) para identificar por medio de un procedimiento a qué tipo pertenece cada usuario
*/
ALTER TABLE Paciente ADD COLUMN TipoUsuario VARCHAR(10) NOT NULL DEFAULT 'Paciente';
ALTER TABLE Doctor ADD COLUMN TipoUsuario VARCHAR(10) NOT NULL DEFAULT 'Doctor';
/*
Continuando con las insercciones de datos en la tabla Paciente
*/
INSERT INTO Paciente VALUES
('P00000002', 950022433, 'shaggy123', 'Domenica', 'Moran', 21, STR_TO_DATE('2003-05-04', '%Y-%m-%d'), 'Mucho Lote 2', 'Paciente');
INSERT INTO Paciente VALUES
('P00000003', 456789123, '1234', 'Luis', 'Fernández', 45, '1978-11-10', 'Calle 789, Centro','Paciente'),
('P00000004', 321654987, '2313', 'Ana', 'Martínez', 35, '1989-05-30', 'Avenida 321, Sur','Paciente'),
('P00000005', 654987321, '2212', 'Carlos', 'Rodríguez', 50, '1973-09-12', 'Calle 654, Norte','Paciente');
select*from Paciente;
/*
Continuando con la insercciones de datos en la tabla Doctor
*/
INSERT INTO Doctor VALUES
('D00000002', 923651020, '123', 'Ian', 'Cedeño', 'Doctor');
INSERT INTO Doctor VALUES
('D00000003', 334455667, 'pass9101', 'Luis', 'García','Doctor'),
('D00000004', 445566778, 'pass1122', 'Laura', 'Vázquez','Doctor'),
('D00000005', 556677889, 'pass2233', 'Fernando', 'Lopez','Doctor');
select * from Doctor;

/*
Creando la tabla Especializacion
*/
 CREATE TABLE IF NOT EXISTS Especializacion (
    Especializacion_id CHAR(9) ,
    Doctor_id CHAR(9) ,
    nombre_de_especializacion VARCHAR(45) ,
    Descripcion_Especializacion VARCHAR(255),
    anios_experiencia INT 
);
/*
Consideramos pertienente trabajar con una sola tabla llamada Especialización observamos que se podía unir todo en una sola
tabla, realmente no era necesario construir 3 tablas como en el modelo original y sobre todo simplifica las consultas 
a la hora de querer consultar información sobre el doctor
*/
ALTER TABLE Especializacion ADD CONSTRAINT Pk_Especializacion PRIMARY KEY (Especializacion_id);
ALTER TABLE Especializacion
    MODIFY Especializacion_id CHAR(9) NOT NULL,
    MODIFY Doctor_id CHAR(9) NOT NULL,
    MODIFY nombre_de_especializacion VARCHAR(30) NOT NULL,
    MODIFY Descripcion_Especializacion VARCHAR(50) NOT NULL,
    MODIFY anios_experiencia INT NOT NULL;

ALTER TABLE Especializacion ADD CONSTRAINT Fk_Doctor FOREIGN KEY(Doctor_id) REFERENCES Doctor(Doctor_id) ON DELETE CASCADE;



/*
Comenzando inserciones de datos en la tabla Especializacion
*/
INSERT INTO Especializacion VALUES ('SP0000001','D00000001', 'Cardiologo', 'Trabaja en procedimientos cardíacos y cardíacos','4');
INSERT INTO Especializacion VALUES ('SP0000002', 'D00000002','Medicina General', 'Trabaja en cualquier rama médica','3');
INSERT INTO Especializacion VALUES ('SP0000003','D00000003', 'Medico Cirujano', 'Procedimientos de cirugía','4');
INSERT INTO Especializacion VALUES ('SP0000004', 'D00000004', 'Dermatología', 'Diagnóstico y tratamiento de enfermedades de la piel.', 10);
INSERT INTO Especializacion VALUES ('SP0000005', 'D00000005', 'Pediatría', 'Cuidado médico para niños, desde el nacimiento hasta la adolescencia.', 20);
select*from Especializacion;
/*
Creando la tabla Departamento
*/
CREATE TABLE IF NOT EXISTS Departamento (
    Departamento_id CHAR(9) ,
    Nombre_Departamento VARCHAR(45),
    Localizacion VARCHAR(45) 

);
ALTER TABLE Departamento ADD CONSTRAINT Pk_Departamento PRIMARY KEY(Departamento_id);

ALTER TABLE Departamento
    MODIFY Departamento_id CHAR(9) NOT NULL,
    MODIFY Nombre_Departamento VARCHAR(30) NOT NULL,
    MODIFY Localizacion VARCHAR(30) NOT NULL;


/*
Comenzando inserciones de datos en la tabla Departamento
*/
INSERT INTO Departamento VALUES ('DP0000001', 'Departamento Cardiología', 'Planta A');
INSERT INTO Departamento VALUES ('DP0000002', 'Departamento Medicina General', 'Planta B');
INSERT INTO Departamento VALUES ('DP0000003', 'Departamento de Cirugía', 'Planta C');
INSERT INTO Departamento VALUES ('DP0000004', 'Departamento de Dermatología', 'Planta D');
INSERT INTO Departamento VALUES ('DP0000005', 'Departamento de Pediatria', 'Planta E');
SELECT * FROM Departamento;

/*
Creando la tabla DoctorxDepartamento
*/
CREATE TABLE IF NOT EXISTS DoctorxDepartamento (
    Doctor_id CHAR(9) ,
    Departamento_id CHAR(9) 
    );
ALTER TABLE DoctorxDepartamento ADD CONSTRAINT Pk_DoctorxDepartamento PRIMARY KEY (Doctor_id,Departamento_id);
ALTER TABLE DoctorxDepartamento ADD CONSTRAINT Fk_Doctor_Departamento FOREIGN KEY (Doctor_id) REFERENCES Doctor(Doctor_id) ON DELETE CASCADE;
ALTER TABLE DoctorxDepartamento ADD CONSTRAINT Fk_Departamento FOREIGN KEY (Departamento_id) REFERENCES Departamento(Departamento_id) ON DELETE CASCADE;
ALTER TABLE DoctorxDepartamento
    MODIFY Doctor_id CHAR(9) NOT NULL,
    MODIFY Departamento_id CHAR(9) NOT NULL;

/*
Comenzando inserciones en la tabla DoctorxDepartamento
*/
INSERT INTO DoctorxDepartamento VALUES ('D00000001', 'DP0000001');
INSERT INTO DoctorxDepartamento VALUES ('D00000002', 'DP0000002');
INSERT INTO DoctorxDepartamento VALUES ('D00000001', 'DP0000003');
INSERT INTO DoctorxDepartamento VALUES ('D00000003', 'DP0000003');
INSERT INTO DoctorxDepartamento VALUES ('D00000002', 'DP0000001');
SELECT * FROM DoctorxDepartamento;

/*
Creando la tabla PacientexDepartamento
*/
CREATE TABLE IF NOT EXISTS PacientexDepartamento (
    Paciente_id CHAR(9) ,
    Departamento_id CHAR(9) ,
    Fecha_Asignacion DATE ,
    Numero_Habitacion INT ,
    Fecha_Alta DATE
);
ALTER TABLE PacientexDepartamento ADD CONSTRAINT Pk_Paciente_Departamento PRIMARY KEY (Paciente_id,Departamento_id);
ALTER TABLE PacientexDepartamento ADD CONSTRAINT Fk_Paciente_Departamento FOREIGN KEY(Paciente_id) REFERENCES Paciente(Paciente_id) ON DELETE CASCADE;
ALTER TABLE PacientexDepartamento ADD CONSTRAINT Fk_Departamento_Paciente FOREIGN KEY(Departamento_id) REFERENCES Departamento(Departamento_id) ON DELETE CASCADE;
ALTER TABLE PacientexDepartamento
    MODIFY Paciente_id CHAR(9) NOT NULL,
    MODIFY Departamento_id CHAR(9) NOT NULL,
    MODIFY Fecha_Asignacion DATE NOT NULL,
    MODIFY Numero_Habitacion INT NOT NULL,
    MODIFY Fecha_Alta DATE NULL;

/*
Insertando los datos de la tabla PAcientexDepartamento
*/
INSERT INTO PacientexDepartamento (Paciente_id, Departamento_id, Fecha_Asignacion, Numero_Habitacion, Fecha_Alta)
VALUES
('P00000001', 'DP0000001', '2024-06-01', 101, '2024-06-10'),
('P00000002', 'DP0000002', '2024-06-05', 205, '2024-06-15'),
('P00000003', 'DP0000001', '2024-06-08', 102, '2024-06-18'),
('P00000004', 'DP0000003', '2024-06-10', 301, '2024-06-20'),
('P00000005', 'DP0000002', '2024-06-12', 208, '2024-06-22');
select*from PacientexDepartamento;
/*
Creando la tabla Tratamiento
*/
CREATE TABLE IF NOT EXISTS Tratamiento (
    Tratamiento_id CHAR(9) ,
    precio_Tratamiento DECIMAL(10,2) ,
    Fecha_Inicio_Tratamiento DATE ,
    Enfermedad_a_tratar VARCHAR(45) ,
    Paciente_id CHAR(9) ,
    Doctor_id CHAR(9),
    Fecha_Fin_Tratamiento DATE NULL
   
);
/*
Primera observación: Un paciente podrá realizarse varios tratamientos pero estos tratamientos no podrá relalizarlos
en un mismo día sino que tendrán que ser en diferentes días, para que tenga relevancia mi relación definida en la 
tabla Tratamiento y Factura.
*/

ALTER TABLE Tratamiento
    MODIFY Tratamiento_id CHAR(9) NOT NULL,
    MODIFY  precio_Tratamiento INT NOT NULL,
    MODIFY Fecha_Inicio_Tratamiento DATE NOT NULL,
    MODIFY Enfermedad_a_tratar VARCHAR(45) NOT NULL,
    MODIFY Paciente_id CHAR(9) NOT NULL,
    MODIFY Doctor_id CHAR(10) NOT NULL,
    MODIFY Fecha_Fin_Tratamiento DATE NULL;
ALTER TABLE Tratamiento ADD CONSTRAINT FK_TTratamiento_Paciente FOREIGN KEY (Paciente_id) REFERENCES Paciente(Paciente_id) ON DELETE CASCADE;
ALTER TABLE Tratamiento ADD CONSTRAINT Fk_TTratamiento_Doctor FOREIGN KEY (Doctor_id) REFERENCES Doctor(Doctor_id) ON DELETE CASCADE;
ALTER TABLE Tratamiento ADD CONSTRAINT Pk_Tratamiento PRIMARY KEY (Tratamiento_id);

/*
Insertando los datos de la tabla Tratamiento
*/
INSERT INTO Tratamiento (Tratamiento_id, precio_Tratamiento, Fecha_Inicio_Tratamiento, Enfermedad_a_tratar, Paciente_id, Doctor_id, Fecha_Fin_Tratamiento) VALUES 
('T00000001', 1500.00, '2024-05-01', 'Hipertensión', 'P00000001', 'D00000001', '2024-05-15'),
('T00000002', 800.00, '2024-05-10', 'Asma', 'P00000002', 'D00000002', '2024-06-10'),
('T00000003', 2000.00, '2024-05-20', 'Diabetes Tipo 2', 'P00000003', 'D00000003', NULL),
('T00000004', 1200.00, '2024-06-01', 'Eczema', 'P00000004', 'D00000004', '2024-06-20'),
('T00000005', 1700.00, '2024-06-15', 'Cáncer de piel', 'P00000005', 'D00000005', NULL);
select*from Tratamiento;

/*
Creando la tabla Factura
*/
CREATE TABLE IF NOT EXISTS Factura (
    Factura_id CHAR(9) ,
    Tratamiento_id CHAR(9) ,
    Descripcion VARCHAR(255) ,
	Fecha_emision DATE,
    Monto_Total DECIMAL (10,2)  -- Este valor se calculara en base a la suma del tratamiento + medicamento
);
/*
La estructura proporcionada asegura una relación 1:1 entre Factura y Tratamiento.Ahora 
nuestro modelo de negocio trabajará bajo la idea de que  unicamente el paciente solo podra realizar un solo tratamiento  
es decir no podrá realizar varios en un mismo día, por ello tratamiento debe estar asociado con una única factura y viceversa.
*/
ALTER TABLE Factura ADD CONSTRAINT Pk_Factura PRIMARY KEY (Factura_id);
ALTER TABLE Factura
    MODIFY Factura_id CHAR(9) NOT NULL,
    MODIFY Tratamiento_id CHAR(9) NOT NULL,
    MODIFY Descripcion VARCHAR(45) NOT NULL,
    MODIFY Fecha_emision DATE NOT NULL,
    MODIFY Monto_Total DOUBLE NOT NULL;

ALTER TABLE Factura ADD CONSTRAINT U_Tratamiento_Factura UNIQUE (Tratamiento_id); 
ALTER TABLE Factura ADD CONSTRAINT Fk_Tratamiento_Factura FOREIGN KEY ( Tratamiento_id) REFERENCES Tratamiento(Tratamiento_id) ON DELETE CASCADE;

/*
Insertando los datos de la tabla Factura
*/
INSERT INTO Factura (Factura_id, Tratamiento_id, Descripcion, Fecha_emision, Monto_Total)
VALUES
('F00000001', 'T00000001', 'Factura por tratamiento de hipertensión con seguimiento médico', '2024-05-02', 1500.00),
('F00000002', 'T00000002', 'Factura por tratamiento de asma con medicamentos y consulta', '2024-05-11', 800.00),
('F00000003', 'T00000003', 'Factura por tratamiento de diabetes tipo 2, incluye consultas y medicamentos', '2024-05-21', 2000.00),
('F00000004', 'T00000004', 'Factura por tratamiento de eczema con cremas y consultas', '2024-06-02', 1200.00),
('F00000005', 'T00000005', 'Factura por tratamiento de cáncer de piel, incluye cirugía y seguimiento', '2024-06-16', 1700.00);
select*from Factura;
/*
Creando la tabla Medicamento
*/
CREATE TABLE IF NOT EXISTS Medicamento (
    Medicamento_id CHAR(9),
    Valor_Medicamento DECIMAL(10, 2) ,
    Nombre VARCHAR(255) ,
    Unidad INT,
    Total DECIMAL(10,2) 
  
);

ALTER TABLE Medicamento ADD CONSTRAINT Pk_Medicamento PRIMARY KEY (Medicamento_id);
ALTER TABLE Medicamento
    MODIFY Medicamento_id CHAR(9) NOT NULL,
	MODIFY Valor_Medicamento DECIMAL(10, 2) NOT NULL,
    MODIFY Nombre VARCHAR(45) NOT NULL,
    MODIFY Unidad VARCHAR(45) NOT NULL;
    ALTER TABLE Medicamento RENAME TO Detalle_Prescripción_Medica;


/*
Insertando los valores de la tabla Medicamento
*/
INSERT INTO Medicamento (Medicamento_id, Valor_Medicamento, Nombre, Unidad, Total)
VALUES
('M000001', 50.00, 'Atenolol', 30, 1500.00),
('M000002', 25.00, 'Salbutamol', 20, 500.00),
('M000003', 75.00, 'Metformina', 60, 4500.00),
('M000004', 40.00, 'Clobetasol', 15, 600.00),
('M000005', 90.00, 'Doxorubicina', 10, 900.00);
select *from Medicamento;
/*
Creando la tabla TratamientoXMedicamento 
*/
CREATE TABLE IF NOT EXISTS TratamientoXMedicamento (
    Tratamiento_id CHAR(9),
    Medicamento_id CHAR(9) 
);
ALTER TABLE TratamientoXMedicamento ADD CONSTRAINT Pk_Tratamiento_Medicamento PRIMARY KEY (Tratamiento_id,Medicamento_id);
ALTER TABLE TratamientoXMedicamento
    MODIFY Tratamiento_id CHAR(9) NOT NULL,
    MODIFY Medicamento_id CHAR(9) NOT NULL;
ALTER TABLE TratamientoXMedicamento ADD CONSTRAINT Fk_Tratamiento_Medicamento FOREIGN KEY (Tratamiento_id) REFERENCES Tratamiento(Tratamiento_id);
ALTER TABLE TratamientoXMedicamento ADD CONSTRAINT Fk_Medicamento_Tratamiento FOREIGN KEY (Medicamento_id) REFERENCES Medicamento(Medicamento_id);


/*
Insertando los valores de la tabla TratamientoxMedicamento
*/

INSERT INTO TratamientoXMedicamento (Tratamiento_id, Medicamento_id)
VALUES
('T00000001', 'M000001'),
('T00000001', 'M000002'),
('T00000002', 'M000003'),
('T00000003', 'M000004'),
('T00000004', 'M000005');
select * from TratamientoXMedicamento;

    

SELECT * FROM Tratamiento WHERE Tratamiento_id IN ('T00000001', 'T00000002', 'T00000003', 'T00000004');
SELECT * FROM Medicamento WHERE Medicamento_id IN ('M000001', 'M000002', 'M000003', 'M000004', 'M000005');






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


DELIMITER //

CREATE PROCEDURE GetNextPacienteId(OUT new_id CHAR(9))
BEGIN
    DECLARE last_id CHAR(9);
    DECLARE prefix CHAR(1) DEFAULT 'P';
    DECLARE num_part INT;

    -- Obtener el último ID generado
    SELECT Paciente_id INTO last_id
    FROM Paciente
    ORDER BY Paciente_id DESC
    LIMIT 1;

    -- Si no hay registros, empezar desde el primer ID
    IF last_id IS NULL THEN
        SET new_id = CONCAT(prefix, LPAD(1, 8, '0'));
    ELSE
        -- Extraer la parte numérica del último ID
        SET num_part = CAST(SUBSTRING(last_id, 2) AS UNSIGNED) + 1;

        -- Crear el nuevo ID
        SET new_id = CONCAT(prefix, LPAD(num_part, 8, '0'));
    END IF;
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
use grupo3_hospital;
SELECT d.Doctor_id, e.Especializacion_id, e.nombre_de_especializacion, e.Descripcion_Especializacion, e.anios_experiencia
FROM Doctor d
JOIN Especializacion e ON d.Doctor_id = e.Doctor_id
WHERE d.Cedula = 0702964545;

SELECT * FROM Paciente WHERE Cedula=0950022434;
select * from Doctor where cedula=0702964545;
select * from Experiencia where Doctor_id='D00000001';
SELECT * FROM Especializacion WHERE Especializacion_id = 'SP0000001';
select * from Paciente;
select * from Doctor;
select * from Especializacion;
select * from Experiencia;
select * from Medicamento;
