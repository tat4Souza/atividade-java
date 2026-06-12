DROP DATABASE IF EXISTS cadastro_clientes;
CREATE DATABASE IF NOT EXISTS cadastro_clientes;
USE cadastro_clientes;

DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
  id_cliente int NOT NULL AUTO_INCREMENT,
  nome varchar(30) NOT NULL,
  cpf varchar(14) NOT NULL,
  data_nascimento date DEFAULT NULL,
  telefone varchar(20) DEFAULT NULL,
  endereco varchar(150) DEFAULT NULL,
  bairro varchar(100) DEFAULT NULL,
  cidade varchar(100) DEFAULT NULL,
  estado varchar(2) DEFAULT NULL,
  cep varchar(10) DEFAULT NULL,
  status BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (id_cliente),
  UNIQUE KEY cpf (cpf)
);

DROP TABLE IF EXISTS raca;
CREATE TABLE raca (
  id_raca int NOT NULL AUTO_INCREMENT,
  nome_raca varchar(100) NOT NULL,
  tipo_animal ENUM('Gato','Cachorro') DEFAULT NULL,
  status BOOLEAN DEFAULT FALSE,
  PRIMARY KEY (id_raca),
  UNIQUE KEY un_tipo_raca (tipo_Animal,nome_raca)
);

DROP TABLE IF EXISTS animal;
CREATE TABLE animal (
  id_animal int NOT NULL AUTO_INCREMENT,
  nome varchar(100) NOT NULL,
  data_nascimento date DEFAULT NULL,
  sexo char(1) DEFAULT NULL,
  cor varchar(40) DEFAULT NULL,
  observacoes varchar(270) DEFAULT NULL,
  fk_id_cliente int NOT NULL,
  fk_animal_raca INT NOT NULL,
  Status tinyint(1) DEFAULT NULL,
  PRIMARY KEY (id_animal),
  FOREIGN KEY (fk_id_cliente) REFERENCES cliente (id_cliente) ON DELETE CASCADE,
  FOREIGN KEY (fk_animal_raca) REFERENCES raca(id_Raca) ON DELETE CASCADE
);
