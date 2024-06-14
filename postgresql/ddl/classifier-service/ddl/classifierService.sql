CREATE ROLE "FIN_classifier_service_app" WITH
    LOGIN
    NOSUPERUSER
    CREATEDB
    NOCREATEROLE
    INHERIT
    NOREPLICATION
    CONNECTION LIMIT -1
    PASSWORD 'classifier';

CREATE DATABASE classifier_service
    WITH
    OWNER = "FIN_classifier_service_app"
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c classifier_service

CREATE SCHEMA app
    AUTHORIZATION "FIN_classifier_service_app";

CREATE TABLE app.currency
(
    uuid uuid,
    title character varying(100) NOT NULL,
    description character varying(200) NOT NULL,
    dt_create timestamp with time zone NOT NULL,
    dt_update timestamp with time zone NOT NULL,
    CONSTRAINT currency_uuid_pk PRIMARY KEY (uuid),
    CONSTRAINT currency_title_uni UNIQUE (uuid)
);

ALTER TABLE IF EXISTS app.currency
    OWNER to "FIN_classifier_service_app";

CREATE TABLE app.category
(
    uuid uuid,
    title character varying(200) NOT NULL,
    dt_create timestamp with time zone NOT NULL,
    dt_update timestamp with time zone NOT NULL,
    CONSTRAINT category_uuid_pk PRIMARY KEY (uuid),
    CONSTRAINT category_title_uni UNIQUE (title)
);

ALTER TABLE IF EXISTS app.category
    OWNER to "FIN_classifier_service_app";
