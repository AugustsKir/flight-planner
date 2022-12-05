--liquibase formatted sql
--changeset admin:1

create table AIRPORTS
(
    country varchar(255) not null,
    city    varchar(255) not null,
    airport_id varchar(255) not null
        primary key
        unique
);
create table FLIGHTS
(
    id           serial        not null
        primary key
        unique,
    airport_from varchar(255) not null,
    airport_to   varchar(255) not null,
    airline      varchar(255),
    departure_time   timestamp not null,
    arrival_time     timestamp not null

);





alter table FLIGHTS ADD foreign key (airport_from) references AIRPORTS (airport_id);

alter table FLIGHTS ADD foreign key (airport_to) references AIRPORTS (airport_id);





