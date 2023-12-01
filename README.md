# CSCI3170 Sales System

[![standard-readme compliant](https://img.shields.io/badge/readme%20style-standard-brightgreen.svg?style=flat-square)](https://github.com/RichardLitt/standard-readme)

This is a simple sales system for a computer part store to manage the employees, transactions, and inventories. 

The system is implemented in Java and SQL. In order to implement the system, you need to have access to CUHK CSE Server or other servers that support Java and SQL. It contains: Database ER diagram, Database schema, Java source code.

## Table of Contents

## Install  

1. Get access to CUHK CSE Server or other servers that support Java and SQL.
2. Clone the repository to your server by using the following command:
    ```bash
    git clone https://github.com/Gavin-OP/csci3170_group_project_sales_system.git
    ```
3. Go to the repository folder by using the following command:
    ```bash
    cd csci3170_group_project_sales_system/phase2_code_implementation
    ```
   
## Usage

1. Remove existing class files by using the following command:
    ```bash
    rm -rf *.class
    ```
2. Compile the java files by using the following command:
    ```bash
    javac Main.java
    ```
3. Run the program by using the following command:
    ```bash
    java -classpath ./mysql-jdbc.jar:./ Main
    ```
4. Follow the instructions on the screen to use the system.
   - For sample source data folder, please refer to the folder `sample_data`.
   - In the `sample_data`, there are 5 files:
     - `category.txt`  
     - `manufacturer.txt`  
     - `part.txt`  
     - `salesperson.txt`  
     - `transaction.txt`  

## Contributing


## License

[MIT © Gavin-OP.](../LICENSE)

