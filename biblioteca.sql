create database if not exists biblioteca;

use biblioteca;

create table utente(
id int auto_increment primary key,
nome varchar(50) not null,
cognome varchar(50) not null,
email varchar(50)
);

select * from utente;