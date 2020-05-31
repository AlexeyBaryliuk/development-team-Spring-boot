set foreign_key_checks=0;


INSERT INTO projects
VALUES (1, 'Create a web application based on SpringJDBC', '2019-07-15');

INSERT INTO projects
VALUES (2, 'Create a web application based on SpringBoot','2019-08-13');

INSERT INTO projects
VALUES (3, 'Create a web application based on Hibernate', '2020-01-17');

INSERT INTO developers
VALUES (1, 'Ivan', 'Ivanov');

INSERT INTO developers
VALUES (2, 'Petr', 'Petrov');

INSERT INTO developers
VALUES (3, 'Sergey', 'Ivanov');

INSERT INTO developers
VALUES (4, 'Mike', 'Djonson');

INSERT INTO developers
VALUES (5, 'Artem', 'Lopata');

INSERT INTO developers
VALUES (6,'Astra', 'Mironova');

INSERT INTO developers
VALUES (7, 'Zlata', 'Maximochkina');

INSERT INTO developers
VALUES (8, 'Daria', 'Lihacheva');

INSERT INTO projects_developers
VALUES (1, 1);

INSERT INTO projects_developers
VALUES (1, 3);

INSERT INTO projects_developers
VALUES (3, 2);

INSERT INTO projects_developers
VALUES (2, 4);

set foreign_key_checks=1;