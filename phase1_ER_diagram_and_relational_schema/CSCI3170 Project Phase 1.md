# CSCI3170 Project Phase 1 Group 26

> Group Information
>
> ZHANG Haoxiang, 1155141702
>
> HUANG Ruixiang, 1155173870
>
> WEI, Ziqin, 1155173761

## ER Diagram

![ER_diagram](.\ER_diagram.png)

## Relational Schema

Salesperson(<u>salesperson_id</u>, salesperson_name, salesperson_experience, salesperson_address, salesperson_phone_number)

Transaction_Record(<u>transaction_id</u>, transaction_date, part_id, salesperson_id)

Computer_Part(<u>part_id</u>, part_price, part_available_quantity, part_name, part_warranty, part_manufacturer_id, part_category_id)

Category(<u>category_id</u>, category_name)

Manufacturer(<u>manufacturer_id</u>, manufacturer_name, manufacturer_address, manufacturer_phone_number)

## SQL

```sql
CREATE TABLE Category (
	category_id 			INTEGER, 
	category_name 			CHAR(20), 
	PRIMARY KEY 			(category_id))

CREATE TABLE Manufacturer (
	manufacturer_id				INTEGER, 
	manufacturer_name 			CHAR(20), 
	manufacturer_address 		CHAR(50), 
	manufacturer_phone_number 	INTEGER, 
	PRIMARY KEY 				(manufacturer_id))

CREATE TABLE Part (
	part_id						INTEGER, 
	part_name 					CHAR(30), 
	part_price 					INTEGER, 
	part_manufacturer_id 		INTEGER, 
	part_category_id 			INTEGER, 
	part_warranty				INTEGER, 
	part_available_quantity		INTEGER, 
	PRIMARY KEY 				(part_id),
	FOREIGN KEY 				(category_id) 		REFERENCES Category, 
	FOREIGN KEY 				(manufacturer_id) 	REFERENCES Manufacturer)

CREATE TABLE Salesperson (
	salesperson_id				INTEGER, 
	salesperson_name 			CHAR(20), 
	salesperson_address 		CHAR(50), 
	salesperson_phone_number 	INTEGER, 
	salesperson_experience 		INTEGER, 
	PRIMARY KEY 				(salesperson_id))

CREATE TABLE Transaction_Record (
	transaction_id			INTEGER, 
	part_id 				INTEGER, 
	salesperson_id 			INTEGER, 
	transaction_date 		DATE, 
	PRIMARY KEY 			(transaction_id), 
	FOREIGN KEY 			(part_id) 			REFERENCES Part, 
	FOREIGN KEY 			(salesperson_id) 	REFERENCES Salesperson)
```