FROM postgres:16.3-alpine3.19
MAINTAINER ITAcademy

ADD /ddl/account-service/ddl/accountService.sql /docker-entrypoint-initdb.d/
ADD /ddl/audit-service/ddl/auditService.sql /docker-entrypoint-initdb.d/
ADD /ddl/classifier-service/ddl/classifierService.sql /docker-entrypoint-initdb.d/
ADD /ddl/user-service/ddl/userService.sql /docker-entrypoint-initdb.d/
