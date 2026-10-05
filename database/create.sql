
create DATABASE fxbazis;

use fxbazis;

grant ALL PRIVILEGES
on fxbazis.*
to fxbazis@localhost
identified by 'titok';


create table employees (
    id int not null primary key autoincrement,
    name varchar(30),
    city varchar(30),
    salary int
);

SELECT * FROM employees;

DELETE FROM employees;

DROP TABLE employees;