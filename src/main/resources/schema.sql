CREATE TABLE IF NOT EXISTS CUSTOMER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS ADDRESS
(
    id
    BIGINT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    street
    VARCHAR
(
    255
),
    city VARCHAR
(
    255
),
    state VARCHAR
(
    255
),
    zipCode VARCHAR
(
    20
),
    customer_id BIGINT,
    FOREIGN KEY
(
    customer_id
) REFERENCES CUSTOMER
(
    id
)
    );
