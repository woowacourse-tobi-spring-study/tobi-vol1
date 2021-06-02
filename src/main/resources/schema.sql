create table USERS (
    Id varchar(10) NOT NULL,
    Name varchar(20) NOT NULL,
    Password varchar(20) NOT NULL,
    Email varchar(100) NOT NULL,
    Level tinyint,
    Login int,
    Recommend int,
    PRIMARY KEY (Id)
);