DROP TABLE IF EXISTS CUSTOMERS;

CREATE TABLE CUSTOMERS
(
	ID           NUMBER PRIMARY KEY,
	FULL_NAME    VARCHAR(200) NOT NULL,
	PHONE_NUMBER VARCHAR(20)  NOT NULL,
	ADDRESS      VARCHAR(200) NOT NULL,
	CREATED_AT   DATE         NOT NULL
);