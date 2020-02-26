CREATE DATABASE willywonkafactory_db;

DROP TABLE IF EXISTS worker;

CREATE TABLE worker (
  id varchar(20) NOT NULL,
  name varchar(50) NOT NULL,
  age int NOT NULL,
  job varchar(50) NOT NULL,
  weight number NOT NULL,
  height number NOT NULL,
  PRIMARY KEY (id)
);