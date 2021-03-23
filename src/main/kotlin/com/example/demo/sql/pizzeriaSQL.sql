DROP DATABASE IF EXISTS pizzeria;
CREATE DATABASE pizzeria;
DROP USER IF EXISTS admins;
CREATE USER admins IDENTIFIED BY 'admin123';
GRANT ALL PRIVILEGES ON pizzeria.* TO admins WITH GRANT OPTION;
USE pizzeria;

CREATE TABLE client
	(
    id				INT AUTO_INCREMENT,
    telefon			VARCHAR(9)      NOT NULL,
	nom				VARCHAR(20)		NOT NULL,
    date_deleted    LONG,
    PRIMARY KEY(id)
	)ENGINE=InnoDB
	;
    
INSERT INTO client VALUE (1,'937853354','Josep Vila', 0);
INSERT INTO client VALUE (2,'937883402','Carme Garcia', 0);
INSERT INTO client VALUE (3,'606989866','Enric Miralles', 0);
INSERT INTO client VALUE (4,'937753222','Miquel Bover', 0);
INSERT INTO client VALUE (5,'937862655','Marta Ribas', 0);
INSERT INTO client VALUE (6,'937858555','Guillem Jam', 0);
INSERT INTO client VALUE (7,'626895456','Júlia Guillén', 0);

SELECT * FROM client;