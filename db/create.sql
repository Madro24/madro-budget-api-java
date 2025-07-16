-- Create the BUDGET table
CREATE TABLE my_database.BUDGET (
    id_yymm VARCHAR(6) PRIMARY KEY,
    start DATE NOT NULL,
    end DATE NOT NULL,
    status BOOLEAN NOT NULL,
    planned FLOAT NOT NULL,
    expenses FLOAT NOT NULL,
    preBalance FLOAT NOT NULL,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create the TRANSACTION-TYPE table
CREATE TABLE my_database.TRANSACTION_TYPE (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    netEffect VARCHAR(255) NOT NULL
);

-- Create the TRANSACTION-CATEGORY table
CREATE TABLE my_database.TRANSACTION_CATEGORY (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    planned FLOAT NOT NULL,
    transaction_type_id INT,
    FOREIGN KEY (transaction_type_id) REFERENCES TRANSACTION_TYPE(id)
);

-- Create the USER table
CREATE TABLE my_database.USER (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Create the TRANSACTION table
CREATE TABLE my_database.TRANSACTION (
    id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    budget_id VARCHAR(6),
    amount FLOAT NOT NULL,
    createdAt DATE NOT NULL,
    createdBy VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    transaction_category_id INT,
    user_id INT,
    FOREIGN KEY (budget_id) REFERENCES BUDGET(id_yymm),
    FOREIGN KEY (transaction_category_id) REFERENCES TRANSACTION_CATEGORY(id),
    FOREIGN KEY (user_id) REFERENCES USER(id)
);