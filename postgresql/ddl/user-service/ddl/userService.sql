CREATE ROLE "FIN_user_service_app" WITH
    LOGIN
    NOSUPERUSER
    CREATEDB
    NOCREATEROLE
    INHERIT
    NOREPLICATION
    CONNECTION LIMIT -1
    PASSWORD 'user';

CREATE DATABASE user_service
    WITH
    OWNER = "FIN_user_service_app"
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c user_service

CREATE SCHEMA app
    AUTHORIZATION "FIN_user_service_app";

CREATE TABLE app.users
(
    uuid uuid,
    dt_create timestamp with time zone NOT NULL,
    dt_update timestamp with time zone NOT NULL,
    mail character varying(100) NOT NULL,
    fio character varying(100) NOT NULL,
    role character varying(50) NOT NULL,
    status character varying(50) NOT NULL,
    password character varying NOT NULL,
    CONSTRAINT users_uuid_pk PRIMARY KEY (uuid),
    CONSTRAINT users_mail_uni UNIQUE (mail)
);

ALTER TABLE IF EXISTS app.users
    OWNER to "FIN_user_service_app";

CREATE TABLE app.verification
(
    uuid uuid,
    verification_code character varying NOT NULL,
    message_status character varying(50) NOT NULL,
    CONSTRAINT verification_uuid_pk PRIMARY KEY (uuid),
    CONSTRAINT verification_uuid_fk FOREIGN KEY (uuid)
        REFERENCES app.users (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE CASCADE
        NOT VALID
);

ALTER TABLE IF EXISTS app.verification
    OWNER to "FIN_user_service_app";

INSERT INTO app.users(
    uuid, dt_create, dt_update, mail, fio, role, status, password)
VALUES ('1f01f564-40d0-4146-8d6e-f4b76529af0e', '2024-06-10 17:19:32.012604+03', '2024-06-10 17:19:32.012604+03',
        'admin@finance.app', 'admin', 'ADMIN', 'ACTIVATED', '$2a$10$jvDjYkG51h0t.8ltdblsp.zkSv6U5TsQh.NFYxx5Z3460WgVxbDrq');