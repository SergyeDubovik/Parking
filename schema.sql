create table parking (
car_number varchar(8) primary key,
enter_time timestamp not null,
slot integer not null unique
);