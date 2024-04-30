CREATE SCHEMA IF NOT EXISTS serveeasy;
SET SCHEMA serveeasy;

create table if not exists ingredienteprincipale(
                                                    ID varchar(20) primary key,
                                                    nome varchar(20) not NULL
);