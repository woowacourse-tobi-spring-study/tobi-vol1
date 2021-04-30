CREATE DATABASE springbook DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

use springbook;

create table users (
	id varchar(10) primary key,
    name varchar(20) not null,
    password varchar(10) not null
);
