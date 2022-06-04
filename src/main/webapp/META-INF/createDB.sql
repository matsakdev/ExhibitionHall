CREATE DATABASE IF NOT EXISTS exhibitiondb;

USE exhibitiondb;

CREATE TABLE Expositions
(
	Id bigint NOT NULL auto_increment PRIMARY KEY,
    EXP_name varchar(60) NOT NULL,
    EXP_startDate datetime NOT NULL,
    EXP_finalDate datetime NOT NULL,
    Price double NOT NULL,
    Author varchar(200)
);

CREATE TABLE Showrooms
(
	Id int NOT NULL auto_increment PRIMARY KEY,
    SR_name VARCHAR(40) NOT NULL,
    Area double NOT NULL
);

CREATE TABLE Expositions_Showrooms
(
    Exposition_id bigint NOT NULL,
    Showroom_id int NOT NULL,
	FOREIGN KEY (Exposition_id) REFERENCES Expositions (Id) ON DELETE CASCADE,
    FOREIGN KEY (Showroom_id) REFERENCES Showrooms (Id) ON DELETE CASCADE,
    PRIMARY KEY (Exposition_id, Showroom_id)
);

CREATE TABLE Themes
(
	Id int NOT NULL auto_increment PRIMARY KEY,
    Theme_name VARCHAR(40) NOT NULL
);

CREATE TABLE Expositions_Themes
(
	Exposition_id bigint NOT NULL,
    Theme_id int NOT NULL,
	FOREIGN KEY (Exposition_id) REFERENCES Expositions (Id) ON DELETE CASCADE,
    FOREIGN KEY (Theme_id) REFERENCES Themes (Id) ON DELETE CASCADE,
    PRIMARY KEY(Exposition_id, Theme_id)
);

CREATE TABLE Users
(
	User_login VARCHAR(20) NOT NULL UNIQUE PRIMARY KEY,
	User_password VARCHAR(20) NOT NULL,
	FirstName VARCHAR(20) NOT NULL,
    LastName VARCHAR(30) NOT NULL,
    Email VARCHAR(50) NOT NULL UNIQUE,
    Phone VARCHAR(15) NULL UNIQUE
);

CREATE TABLE Administrators
(
	Admin_login VARCHAR(20) NOT NULL UNIQUE PRIMARY KEY,
	Admin_password VARCHAR(20) NOT NULL,
	FirstName VARCHAR(20) NOT NULL,
    LastName VARCHAR(30) NOT NULL,
    Email VARCHAR(50) NOT NULL UNIQUE,
    Phone VARCHAR(15) NULL UNIQUE
);

CREATE TABLE Tickets
(
	Num bigint AUTO_INCREMENT PRIMARY KEY,
    Order_date datetime NOT NULL,
    Exposition_id bigint NOT NULL,
    User_login VARCHAR(20) NOT NULL,
	FOREIGN KEY (Exposition_id) REFERENCES Expositions (Id) ON DELETE CASCADE,
    FOREIGN KEY (User_login) REFERENCES Users (User_login) ON DELETE CASCADE
);

drop database exhibitiondb;




