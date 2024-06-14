CREATE ROLE "FIN_audit_service_app" WITH
    LOGIN
    NOSUPERUSER
    CREATEDB
    NOCREATEROLE
    INHERIT
    NOREPLICATION
    CONNECTION LIMIT -1
    PASSWORD 'audit';

CREATE DATABASE audit_service
    WITH
    OWNER = "FIN_audit_service_app"
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

\c audit_service

CREATE SCHEMA app
    AUTHORIZATION "FIN_audit_service_app";

CREATE TABLE app.audit
(
    uuid uuid,
    dt_create timestamp with time zone NOT NULL,
    user_uuid uuid NOT NULL,
    text character varying NOT NULL,
    type_entity character varying NOT NULL,
    uuid_entity uuid,
    CONSTRAINT audit_uuid_pk PRIMARY KEY (uuid)
);

ALTER TABLE IF EXISTS app.audit
    OWNER to "FIN_audit_service_app";

