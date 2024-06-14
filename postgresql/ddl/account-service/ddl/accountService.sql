CREATE ROLE "FIN_account_service_app" WITH
    LOGIN
    NOSUPERUSER
    CREATEDB
    NOCREATEROLE
    INHERIT
    NOREPLICATION
    CONNECTION LIMIT -1
    PASSWORD 'account';

CREATE DATABASE account_service
    WITH
    OWNER = "FIN_account_service_app"
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c account_service

CREATE SCHEMA app
    AUTHORIZATION "FIN_account_service_app";

CREATE TABLE app.account
(
    uuid uuid,
    user_uuid uuid NOT NULL,
    dt_create timestamp with time zone NOT NULL,
    dt_update timestamp with time zone NOT NULL,
    title character varying(150) NOT NULL,
    description character varying NOT NULL,
    balance double precision NOT NULL,
    type_account character varying NOT NULL,
    currency_uuid uuid NOT NULL,
    CONSTRAINT account_uuid_pk PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS app.account
    OWNER to "FIN_account_service_app";

CREATE TABLE app.operation
(
    uuid uuid,
    account_uuid uuid NOT NULL,
    dt_create timestamp with time zone NOT NULL,
    dt_update timestamp with time zone NOT NULL,
    dt_execute timestamp with time zone NOT NULL,
    description character varying NOT NULL,
    category_uuid uuid NOT NULL,
    value double precision NOT NULL,
    currency_uuid uuid NOT NULL,
    CONSTRAINT operation_uuid_pk PRIMARY KEY (uuid),
    CONSTRAINT operation_account_uuid_fk FOREIGN KEY (account_uuid)
        REFERENCES app.account (uuid) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS app.operation
    OWNER to "FIN_account_service_app";