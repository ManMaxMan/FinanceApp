CREATE ROLE FIN_user_service_app WITH
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