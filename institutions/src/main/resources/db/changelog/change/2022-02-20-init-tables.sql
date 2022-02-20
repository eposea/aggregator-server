--liquibase formatted sql
--changeset lagolini:01
create table institution
(
    id    text primary key not null,
    title text             not null,
    url   text             not null
);
