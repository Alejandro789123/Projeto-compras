
-- DROP DATABASE compras;
DROP TABLE compras.usuario;

CREATE DATABASE compras;

CREATE SCHEMA compras;

CREATE TABLE compras.usuario
(
  id INT NOT NULL PRIMARY KEY auto_increment,
  nome character varying(150),
  email character varying(100),
  senha character varying(20),
  perfil varchar(20) CHECK (perfil in ('COMPRADOR', 'CLIENTE', 'ADMINISTRADOR')),
  data_cadastro date default now()
);

INSERT INTO compras.usuario (id, nome, email, senha, perfil) VALUES(2, 'Xaxa', 'xaxa@xaxa.com.br', '1234', 'ADMINISTRADOR');

select * from compras.usuario ;
