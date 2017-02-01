-- DON'T FORGET TO REMOVE THIS LINE
DROP DATABASE if exists AnimalKingdomUserAuthentication;
CREATE DATABASE AnimalKingdomUserAuthentication;
USE AnimalKingdomUserAuthentication;

CREATE TABLE AKUserAuthentication (
	user_name varchar(20) not null primary key,
    user_password varchar(20) not null,
    games_played int(11),
    games_won int(11)
);