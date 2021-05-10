create database if not exists tobi
DEFAULT CHARACTER SET utf8
    COLLATE utf8_general_ci;

use tobi;

create table if not exists users DEFAULT CHARACTER SET utf8
    COLLATE utf8_general_ci;
(
    id   varchar(255) 		not null unique,
    name varchar(255)          not null unique,
    password varchar(255) not null,
    primary key (id)
);