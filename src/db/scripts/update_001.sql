CREATE TABLE accident_type (
id serial PRIMARY KEY,
name VARCHAR(256)
);

CREATE TABLE rule (
id serial PRIMARY KEY,
name VARCHAR(256)
);

CREATE TABLE accident (
id serial PRIMARY KEY,
name VARCHAR(256),
text VARCHAR(256),
address VARCHAR(256),
accident_type_id int REFERENCES accident_type(id),
);

CREATE TABLE accident_rule (
id serial PRIMARY KEY,
accident_id int REFERENCES accident(id),
rule_id int REFERENCES rule(id)
);

insert into accident_type(name)
values
('Две машины'),
('Машина и человек'),
('Машина и велосипед');

insert into rule(name)
values
('Статья. 1'),
('Статья. 2'),
('Статья. 3');