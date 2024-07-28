FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=notebd
ENV MYSQL_USER=root
ENV MYSQL_PASSWORD=password

COPY init.sql /docker-entrypoint-initdb.d/
