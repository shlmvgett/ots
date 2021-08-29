create sequence hibernate_sequence start with 10 increment by 1;

create table users
(
    id bigserial primary key,
    login varchar(50),
    name varchar(50),
    role varchar(50)
);

