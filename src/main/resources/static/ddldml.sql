CREATE DATABASE softlearningtest;
USE softlearningtest;

CREATE TABLE books (
    id INT NOT NULL,
    name VARCHAR(100),
    author VARCHAR(100),
    publication_date DATE,
    availability_date DATE,
    price DOUBLE,
    weight DOUBLE,
    width DOUBLE,
    height DOUBLE,
    depth DOUBLE,
    available BOOLEAN,
    is_fragile BOOLEAN,
    PRIMARY KEY (id)
);

INSERT INTO books VALUES
(
    121, 'PHP Basics', 'Marcombo',
    '2022-06-30', '2022-07-15',
    22.50, 0.25, 12.0, 20.0, 1.5,
    TRUE, FALSE
),
(
    131, 'Java Basics', 'Princeton',
    '2023-04-26', '2023-05-01',
    29.99, 0.45, 14.0, 21.0, 2.5,
    TRUE, TRUE
),
(
    141, 'Java EE', 'Oracle',
    '2024-01-30', '2024-02-15',
    52.00, 0.50, 14.0, 22.5, 3.0,
    FALSE, TRUE
);

CREATE TABLE clients (
    id_client INT NOT NULL,
    id_person VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(30),
    address VARCHAR(150),
    name_person VARCHAR(100),
    registration_date DATE,
    PRIMARY KEY (id_client)
);

INSERT INTO clients VALUES
(
    121,
    'ID-001',
    'php@marcombo.com',
    '+34-600-111111',
    'Madrid',
    'PHP Basics',
    '2022-06-30'
),
(
    131,
    'ID-002',
    'java@princeton.com',
    '+1-609-555-2222',
    'Princeton',
    'Java Basics',
    '2023-04-26'
),
(
    141,
    'ID-003',
    'javaee@oracle.com',
    '+1-650-555-3333',
    'California',
    'Java EE',
    '2024-01-30'
);
