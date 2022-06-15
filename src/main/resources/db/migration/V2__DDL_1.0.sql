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
    profile_photo_id     bigserial NOT NULL,
    booking_id           int8 NULL ,
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
    user_id int8 NOT NULL,
    authorities_id int8 NOT NULL,
    CONSTRAINT ct_users_authority UNIQUE (authorities_id),
    CONSTRAINT ct_users_authority_pkey PRIMARY KEY (user_id, authorities_id),
    CONSTRAINT ct_users_authority_user FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT ct_users_authority_auth FOREIGN KEY (authorities_id) REFERENCES authority (id)
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
    route_id int8  NULL,
    CONSTRAINT airports_pkey PRIMARY KEY (id)
);

-----------------------------------------------------------------------

CREATE TABLE planes (
                        id bigserial NOT NULL,
                        aircraft_type varchar(255) NULL,
                        name varchar(255) NULL,
                        photo_url varchar(255) NULL,
                        registration varchar(255) NULL,
                        status int4 NULL,
                        texture_url varchar(255) NULL,
                        route_id int8  NULL,
                        CONSTRAINT planes_pkey PRIMARY KEY (id)
);
-------------------------------------------------------------------------------------------------------

CREATE TABLE sigv_file (
      id int8 NOT NULL,
      content bytea NULL,
      name varchar(255) NULL,
      type varchar(255) NULL,
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
                          user_id int8 NOT NULL,
                          CONSTRAINT  fk_users foreign key (user_id) references users(id),
                          CONSTRAINT bookings_pkey PRIMARY KEY (id),
                          CONSTRAINT fk_planes FOREIGN KEY (plane_id) REFERENCES planes(id)
);


ALTER TABLE users ADD   CONSTRAINT fk_booking foreign key (booking_id) references bookings(id);


--------------------------------------------------------------------------------------------------------

CREATE TABLE routes (
                        id bigserial NOT NULL,
                        airport_id int8 NOT NULL,
                        plane_id int8 NOT NULL,
                        status varchar(15) NOT NULL,

                        CONSTRAINT pk_routes_id primary key (id),
                        CONSTRAINT  fk_airport_id foreign key (airport_id) references airports(id),
                        CONSTRAINT  fk_plane_id foreign key (plane_id) references planes(id)

);

--------------------------------------------------------------------------------------------------------------
ALTER TABLE airports ADD  CONSTRAINT  aiports_fkey FOREIGN KEY (route_id) references routes(id);

ALTER TABLE planes ADD  CONSTRAINT  planes_fkey FOREIGN KEY (route_id) references routes(id);



