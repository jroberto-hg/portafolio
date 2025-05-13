--=========================================================================
--Elaboro  : Jesus Roberto Hernandez Gallegos
--Descripcion: Creacion de tabla 'doctores'
--=========================================================================
DO
$BODY$
DECLARE
BEGIN
	IF EXISTS (SELECT 1 FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'doctores') THEN
		DROP TABLE public.doctores;
	END IF;
	
	CREATE TABLE doctores (
		id  		     INTEGER GENERATED ALWAYS AS IDENTITY,
		nombre 			 CHARACTER VARYING(50) NOT NULL,
		apellido_paterno CHARACTER VARYING(50) NOT NULL,
		apellido_materno CHARACTER VARYING(50) NOT NULL,
		especialidad 	 CHARACTER VARYING(50) NOT NULL,
		CONSTRAINT doctores_pkey PRIMARY KEY (id)
	);
	
	GRANT ALL ON TABLE public.doctores TO postgres;
END;
$BODY$;

--=========================================================================
--Elaboro  : Jesus Roberto Hernandez Gallegos
--Descripcion: Creacion de tabla 'consultorios'
--=========================================================================
DO
$BODY$
DECLARE
BEGIN
	IF EXISTS (SELECT 1 FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'consultorios') THEN
		DROP TABLE public.consultorios;
	END IF;
	
	CREATE TABLE consultorios (
		id 				   INTEGER GENERATED ALWAYS AS IDENTITY,
		numero_consultorio INTEGER NOT NULL,
		piso 			   INTEGER NOT NULL,
		CONSTRAINT consultorios_pkey PRIMARY KEY (id)
	);
	
	GRANT ALL ON TABLE public.consultorios TO postgres;
END;
$BODY$;

--=========================================================================
--Elaboro  : Jesus Roberto Hernandez Gallegos
--Descripcion: Creacion de tabla 'citas'
--=========================================================================
DO
$BODY$
DECLARE
BEGIN
	IF EXISTS (SELECT 1 FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'citas') THEN
		DROP TABLE public.citas;
	END IF;
	
	CREATE TABLE citas (
		id INTEGER 		 GENERATED ALWAYS AS IDENTITY,
		consultorio_id 	 INTEGER NOT NULL,
		doctor_id 		 INTEGER NOT NULL,
		horario_consulta TIMESTAMP NOT NULL,
		nombre_paciente  CHARACTER VARYING(255) NOT NULL,
		FOREIGN KEY (consultorio_id) REFERENCES consultorios(id),
		FOREIGN KEY (doctor_id) REFERENCES doctores(id)
	);
	
	GRANT ALL ON TABLE public.citas TO postgres;
END;
$BODY$;

--
INSERT INTO doctores (nombre, apellido_paterno, apellido_materno, especialidad) VALUES
    ('Juan', 'Pérez', 'Gómez', 'Medicina Interna'),
    ('María', 'García', 'López', 'Cardiología'),
    ('Carlos', 'Rodríguez', 'Martínez', 'Medicina Interna');

INSERT INTO consultorios (numero_consultorio, piso) VALUES
    ('101', '1'),
    ('202', '2'),
    ('303', '3');

INSERT INTO citas (consultorio_id, doctor_id, horario_consulta, nombre_paciente) VALUES
    (1, 1, '2024-07-29 10:00:00', 'Paciente A'),
    (1, 2, '2024-07-29 11:00:00', 'Paciente B'),
    (2, 1, '2024-07-29 14:00:00', 'Paciente C'),
    (3, 3, '2024-07-30 09:00:00', 'Paciente D'),
    (2, 2, '2024-07-30 12:00:00', 'Paciente E'),
    (1, 3, '2024-07-31 15:00:00', 'Paciente F'),
    (3, 1, '2024-08-01 10:30:00', 'Paciente G'),
    (2, 2, '2024-08-01 11:30:00', 'Paciente H'),
    (1, 1, '2024-08-02 16:00:00', 'Paciente I'),
    (3, 3, '2024-08-02 17:00:00', 'Paciente J');