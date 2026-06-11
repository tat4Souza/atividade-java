DROP DATABASE IF EXISTS cadastro_clientes;
CREATE DATABASE IF NOT EXISTS cadastro_clientes;
USE cadastro_clientes;

DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
  id int NOT NULL AUTO_INCREMENT,
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
  PRIMARY KEY (id),
  UNIQUE KEY cpf (cpf)
);

DROP TABLE IF EXISTS raca;
CREATE TABLE raca (
  id_raca int NOT NULL AUTO_INCREMENT,
  nome_raca varchar(100) NOT NULL,
  tipo_animal ENUM('Gato','Cachorro') DEFAULT NULL,
  Status tinyint(1) DEFAULT NULL,
  PRIMARY KEY (ID_Raca),
  UNIQUE KEY un_tipo_raca (Tipo_Animal,Nome_Raca)
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
  FOREIGN KEY (fk_id_cliente) REFERENCES cliente (id) ON DELETE CASCADE,
  FOREIGN KEY (fk_animal_raca) REFERENCES raca(ID_Raca) ON DELETE CASCADE
);


USE cadastro_clientes;

INSERT INTO cliente VALUES 
(1,'Kate M\Llorte','321.829.384-72','2002-05-14','(90) 32970-7060','Rua Empório de Strelyta','Vista Noturna','Astra','SK','12398-781',1),
(2,'Felix Romero','293.882.910-29','2003-09-07','(90) 82828-3219','Avenida das Luas de Astruam','Vista Noturna','Astra','SK','25432-321',1),
(3,'Eliot Ownes','938.289.183-29','2002-04-29','(90) 98330-5795','Praça dos Refugiados de Lethia','Jardim dos Salves','Astra','SK','39021-873',1),
(4,'Alberto Montes','749.705.078-20','2004-03-18','(90) 98330-5795','Rua das Neves','Parque Sensorial','Astra','SK','39213-291',1),
(16,'Taís Fernandes de Souza','549.243.965-61','2007-06-24','(12) 98171-2406','Rua das Arthemisias','Jardim Américo','São José dos Campos','SP','12264-929',1),
(19,'teste','213.213.232-13','2026-06-19','(21) 32132-1321','teste','teste','teste','te','43232-432',0);


INSERT INTO raca VALUES 
(1,'Vira-Lata','Gato',1),
(2,'testw','Cachorro',0),
(4,'Vira-Latas','Gato',0);

INSERT INTO animal VALUES 
(5,'Luna','2025-12-26','F','Preta e Branca','A gatinha frajolinha mais fofa e educadinha que já existiu.',16,1,1),
(7,'teste','2026-06-03','M','teste','teste',2,1,1),
(8,'TesteA','2026-06-02','F','testeA','',19,2,0);
