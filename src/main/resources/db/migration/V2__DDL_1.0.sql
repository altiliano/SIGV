CREATE TABLE users
(
    id                   bigserial NOT NULL,
    average_landing_rate int4      NOT NULL,
    birth_date           date NULL,
    city                 varchar(255) NULL,
    country              varchar(255) NULL,
    current_location     varchar(255) NULL,
    email                varchar(255) NULL,
    first_name           varchar(255) NULL,
    hours_flown          int8      NOT NULL,
    last_name            varchar(255) NULL,
    password             varchar(255) NULL,
    pireps               int4      NOT NULL,
    points               int4      NOT NULL,
    status               int4 NULL,
    username             varchar(255) NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

----------------------------------------------------


CREATE TABLE authority
(
    id     bigserial NOT NULL,
    role varchar(255) NULL,
    CONSTRAINT authority_pkey PRIMARY KEY (id)
);


---------------------------------------------------------------------

CREATE TABLE users_authority
(
    id      int8 NOT NULL,
    authority_id int8 NOT NULL,
    CONSTRAINT ct_users_authority UNIQUE (authority_id),
    CONSTRAINT ct_users_authority_pkey PRIMARY KEY (id, authority_id),
    CONSTRAINT ct_users_authority_user FOREIGN KEY (id) REFERENCES users (id),
    CONSTRAINT ct_users_authority_auth FOREIGN KEY (id) REFERENCES authority (id)
);


---------------------------------------------------------------------
CREATE TABLE airports
(
    id        bigserial NOT NULL,
    city      varchar(255) NULL,
    country   varchar(255) NULL,
    iata_code varchar(255) NULL,
    icao_code varchar(255) NULL,
    latitude  varchar(255) NULL,
    longitude varchar(255) NULL,
    name      varchar(255) NULL,
    CONSTRAINT airports_pkey PRIMARY KEY (id)
);

-----------------------------------------------------------------------

CREATE TABLE planes (
                        id bigserial NOT NULL,
                        aircraft_type varchar(255) NULL,
                        aircraft_name varchar(255) NULL,
                        photo_url varchar(255) NULL,
                        registration varchar(255) NULL,
                        status int4 NULL,
                        texture_url varchar(255) NULL,
                        CONSTRAINT planes_pkey PRIMARY KEY (id)
);
-------------------------------------------------------------------------------------------------------

CREATE TABLE sigv_files (
      id int8 NOT NULL,
      file_content bytea NULL,
      file_name varchar(255) NULL,
      file_type varchar(255) NULL,
      CONSTRAINT sigv_file_pkey PRIMARY KEY (id)
);

-----------------------------------------------------------------------------------------------------------




CREATE TABLE bookings (
                          id bigserial NOT NULL,
                          arrival_airport_name varchar(255) NULL,
                          arrival_city varchar(255) NULL,
                          arrival_country varchar(255) NULL,
                          arrival_iata_code varchar(255) NULL,
                          arrival_icao_code varchar(255) NULL,
                          arrival_latitude varchar(255) NULL,
                          arrival_longitude varchar(255) NULL,
                          depart_airport_name varchar(255) NULL,
                          depart_city varchar(255) NULL,
                          depart_country varchar(255) NULL,
                          depart_iata_code varchar(255) NULL,
                          depart_icao_code varchar(255) NULL,
                          depart_latitude varchar(255) NULL,
                          depart_longitude varchar(255) NULL,
                          create_date date NULL,
                          status int4 NULL,
                          status_date date NULL,
                          plane_id int8 NULL,
                          CONSTRAINT bookings_pkey PRIMARY KEY (id),
                          CONSTRAINT fk_planes FOREIGN KEY (plane_id) REFERENCES planes(id)
);
