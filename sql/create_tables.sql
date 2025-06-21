-- Table Client
CREATE TABLE CLIENT (
    id_client INT AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    cin VARCHAR(15) NOT NULL,
    birthdate DATE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_client PRIMARY KEY (id_client)
);


-- Table ACCOUNT
CREATE TABLE ACCOUNT (
    id_account INT AUTO_INCREMENT,
    num_account VARCHAR(50) UNIQUE NOT NULL,
    rib VARCHAR(50) UNIQUE NOT NULL,
    balance DECIMAL(12,2) DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    is_deleted BOOLEAN DEFAULT FALSE,
    deleted_at DATETIME,
    type VARCHAR(20) NOT NULL CHECK (type IN ('CHECKING', 'SAVINGS')),
    id_client INT, 
    CONSTRAINT pk_account PRIMARY KEY (id_account),
    CONSTRAINT fk_account FOREIGN KEY (id_client) REFERENCES client(id_account)
);


-- Table TRANSACTION
CREATE TABLE `TRANSACTION` (
    id_transaction INT AUTO_INCREMENT,
    type VARCHAR(20) NOT NULL CHECK (type IN ('DEPOSIT', 'WITHDRAWAL', 'TRANSFER')),
    amount DECIMAL(12,2) NOT NULL CHECK (amount > 0),
    description VARCHAR(255),
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'COMPLETED' CHECK (status IN ('PENDING', 'COMPLETED', 'FAILED')),
    id_account INT,
    id_recipient INT,
    CONSTRAINT pk_transaction PRIMARY KEY (id_transaction),
    CONSTRAINT fk1_transaction FOREIGN KEY (id_account) REFERENCES ACCOUNT(id_account),
    CONSTRAINT fk2_transaction FOREIGN KEY (id_recipient) REFERENCES ACCOUNT(id_account)
);

-- Table BENEFICIARY
CREATE TABLE BENEFICIARY (
    id_account INT,
    id_beneficiary INT,
    CONSTRAINT pk_beneficiary PRIMARY KEY (id_account, id_beneficiary),
    CONSTRAINT fk1_beneficiary FOREIGN KEY (id_account) REFERENCES ACCOUNT(id_account),
    CONSTRAINT fk2_beneficiary FOREIGN KEY (id_beneficiary) REFERENCES ACCOUNT(id_account),
    CONSTRAINT check_different_accounts CHECK (id_account != id_beneficiary)
);

-- Table SAVING_ACCOUNT
CREATE TABLE SAVING_ACCOUNT (
    id_saving_account INT ,
    id_account INT ,
    CONSTRAINT pk_saving_account PRIMARY KEY (id_saving_account, id_account), 
    CONSTRAINT fk1_saving_account FOREIGN KEY (id_saving_account) REFERENCES ACCOUNT(id_account),
    CONSTRAINT fk2_saving_account FOREIGN KEY (id_account) REFERENCES ACCOUNT(id_account)
);

CREATE TABLE BANK_POLICY (
    policy_name VARCHAR(50) PRIMARY KEY,
    policy_value DECIMAL(5,2),
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

