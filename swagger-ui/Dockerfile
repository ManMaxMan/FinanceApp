FROM swaggerapi/swagger-ui:latest
MAINTAINER ITAcademy

ADD /user-service/spec/user-service.yml /usr/share/nginx/html/doc/user-service.yaml
ADD /classifier-service/spec/classifier-service.yml /usr/share/nginx/html/doc/classifier-service.yaml
ADD /account-service/spec/account-service.yml /usr/share/nginx/html/doc/account-service.yaml
ADD /audit-service/spec/audit-service.yml /usr/share/nginx/html/doc/audit-service.yaml
ADD /account-scheduler-service/spec/account-scheduler-service.yml /usr/share/nginx/html/doc/account-scheduler-service.yaml
ADD /report-service/spec/report-service.yml /usr/share/nginx/html/doc/report-service.yaml

ENV URLS="[{ url: 'doc/user-service.yaml', name: 'Пользователи'},{ url: 'doc/classifier-service.yaml', name: 'Справочники'},{ url: 'doc/account-service.yaml', name: 'Счета'},{ url: 'doc/audit-service.yaml', name: 'Аудит'},{ url: 'doc/account-scheduler-service.yaml', name: 'Планирование затрат'},{ url: 'doc/report-service.yaml', name: 'Отчёты'},]"
ENV URLS_PRIMARY_NAME="Пользователи"