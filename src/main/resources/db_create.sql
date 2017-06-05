CREATE SCHEMA ont;
SET search_path TO ont;
--
-- tables
--

CREATE TABLE pure_chemical_substance (
    id bigint PRIMARY KEY,
    substance_name character varying(255),
    substance_formula character varying(255),
    substance_type character varying(255)
);

CREATE TABLE "state" (
    id bigint PRIMARY KEY,
    phase_type character varying(255),
    phase_name character varying(255)
);

CREATE TABLE data_source (
    id bigint PRIMARY KEY,
    bibliographic_reference character varying(10000)
);

CREATE TABLE crystal_structure (
    id bigint PRIMARY KEY,
    crystal_structure character varying(255)
);

CREATE TABLE magnetic_structure (
    id bigint PRIMARY KEY,
    magnetic_type character varying(255)
);

CREATE TABLE dimension (
    id bigint PRIMARY KEY,
    dimension_name character varying(255),
    dimension_designation character varying(255)
);

CREATE TABLE physical_quantity (
    id bigint PRIMARY KEY,
    quantity_designation character varying(255),
    quantity_name character varying(255),
    quantity_type character varying(255)
);

CREATE TABLE uncertainty_type (
    id bigint PRIMARY KEY,
    uncertainty_name character varying(255)
);

CREATE TABLE substance_quantity (
    quantity_id bigint NOT NULL,
    substance_id bigint NOT NULL,
    PRIMARY KEY (quantity_id, substance_id)
);

CREATE TABLE quantity_dimension (
    quantity_id bigint NOT NULL,
    dimension_id bigint NOT NULL,
    PRIMARY KEY (quantity_id, dimension_id)
);

CREATE TABLE quantity_state (
    state_id bigint REFERENCES "state"(id) NOT NULL,
    func_quantity_id bigint REFERENCES physical_quantity(id) NOT NULL,
    func_argument_id bigint REFERENCES physical_quantity(id),
    dimension_id bigint REFERENCES dimension(id),
    lrange_of_definition double precision,
    urange_of_definition double precision,
    lrange_of_variation double precision,
    urange_of_variation double precision
);

CREATE TABLE substance_in_state (
    state_id bigint REFERENCES "state"(id) NOT NULL,
    substance_id bigint REFERENCES pure_chemical_substance(id) NOT NULL,
    crystal_struct_id bigint REFERENCES crystal_structure(id) NOT NULL,
    magnetic_struct_id bigint REFERENCES magnetic_structure(id) NOT NULL,
    additional_name character varying(255),
    id bigint PRIMARY KEY
);

CREATE TABLE data_set (
    id bigint PRIMARY KEY,
    substance_in_state_id bigint REFERENCES substance_in_state(id) NOT NULL,
    data_description character varying(255),
    data_type character varying(255),
    data_format character varying(255),
    isuploadedtoviewmodel boolean
);

CREATE TABLE point_of_measure (
    id bigint PRIMARY KEY,
    data_set_id bigint REFERENCES data_set(id) NOT NULL,
    row_num bigint,
    quantity_id bigint REFERENCES physical_quantity(id) NOT NULL,
    data_source_id bigint REFERENCES data_source(id) NOT NULL,
    dimension_id bigint REFERENCES dimension(id),
    quantity_value double precision
);

CREATE TABLE measurement_uncertainty (
    id bigint PRIMARY KEY,
    uncertainty_value character varying(255),
    point_of_measure_id bigint REFERENCES point_of_measure(id) NOT NULL,
    uncertainty_type_id bigint REFERENCES uncertainty_type(id) NOT NULL
);

CREATE TABLE control_function_definition (
    control_function_id bigint PRIMARY KEY,
    substance_in_state_id bigint REFERENCES substance_in_state(id) NOT NULL,
    function_formula character varying(4096),
    uncertainty_type_id bigint REFERENCES uncertainty_type(id) NOT NULL,
    uncertainty_value character varying(255)
);

CREATE TABLE function_definition (
    function_id bigint PRIMARY KEY,
    substance_in_state_id bigint REFERENCES substance_in_state(id) NOT NULL,
    function_formula character varying(4096),
    standart_dimension_id bigint REFERENCES dimension(id) NOT NULL,
    uncertainty_type_id bigint REFERENCES uncertainty_type(id) NOT NULL,
    uncertainty_value character varying(255),
    func_quantity_id bigint REFERENCES physical_quantity(id) NOT NULL
);

CREATE TABLE domain_of_control_function_definition (
    control_function_id bigint REFERENCES control_function_definition(control_function_id) NOT NULL,
    argument_id bigint REFERENCES physical_quantity(id) NOT NULL,
    standart_dimension_id bigint REFERENCES dimension(id) NOT NULL,
    control_domain_id bigint PRIMARY KEY
);

CREATE TABLE domain_of_function_definition (
    function_id bigint REFERENCES function_definition(function_id) NOT NULL,
    argument_id bigint REFERENCES physical_quantity(id) NOT NULL,
    standart_dimension_id bigint REFERENCES dimension(id) NOT NULL,
    func_connection_type character varying(255),
    lrange_of_definition double precision,
    urange_of_definition double precision,
    func_domain_id bigint PRIMARY KEY
);


ALTER TABLE ONLY point_of_measure
    ADD CONSTRAINT uk_point_of_measure_1 UNIQUE (quantity_id, row_num, data_set_id);

ALTER TABLE ONLY substance_in_state
    ADD CONSTRAINT uk_substance_in_state_1 UNIQUE (substance_id, state_id);

ALTER TABLE ONLY quantity_state
    ADD CONSTRAINT uk_quantity_state_1 UNIQUE (state_id, func_quantity_id, func_argument_id, dimension_id);
