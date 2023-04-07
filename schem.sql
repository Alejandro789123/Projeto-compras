
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

CREATE TABLE compras.Cad_Fornecedor
(
  id INT NOT NULL PRIMARY KEY auto_increment,
  nome VARCHAR(200) NOT NULL,
  cep VARCHAR(20) NOT NULL,
  cidade VARCHAR(30) NOT NULL,
  bairro VARCHAR(50) NOT NULL,
  endereço VARCHAR(50) NOT NULL,
  complemento character varying(20) NOT NULL,
  numero CHAR(5) NOT NULL,
  email VARCHAR (60) NOT NULL,
  telefone CHAR(11) NOT NULL,
  cpf_Cnpj VARCHAR(20) NOT NULL,
  tipo character varying(20) CHECK( tipo IN('CPF','CNPJ')),
  status character varying(20) CHECK (status IN('PENDENTE', 'CANCELADO', 'EFETIVADO')),
  data_cadastro date default now()
);

INSERT INTO compras.usuario (id, nome, email, senha, perfil) VALUES(2, 'Xaxa', 'xaxa@xaxa.com.br', '1234', 'ADMINISTRADOR');

select * from compras.usuario ;

select * from compras.Cad_Fornecedor;
