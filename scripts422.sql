--Описание структуры: у каждого человека есть машина.
--Причем несколько человек могут пользоваться одной машиной.
--У каждого человека есть имя, возраст и признак того, что у него есть права (или их нет).
--У каждой машины есть марка, модель и стоимость.
--Также не забудьте добавить таблицам первичные ключи и связать их.

CREATE TABLE cars(
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(32) NOT NULL,
    model VARCHAR(32) NOT NULL,
    price INT NOT NULL CHECK(price > 0)
);

CREATE TABLE drivers(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(32) NOT NULL,
    age INT NOT NULL CHECK(age >= 18),
    has_driver_license BOOL DEFAULT false,
    car_id BIGINT REFERENCES cars(id)
);

INSERT INTO cars(brand, model, price) VALUES('Lada', 'Vesta', 1500000);
INSERT INTO drivers(name, age) VALUES('Ivan Ivanov', 18);

select * from drivers;

update drivers set car_id = 1;