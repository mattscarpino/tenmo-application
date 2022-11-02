BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account_transaction, account, transaction;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transaction_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

CREATE SEQUENCE seq_transaction_id
	INCREMENT BY 1
	START WITH 3001
	NO MAXVALUE;

CREATE TABLE transaction
(
	transaction_id int NOT NULL DEFAULT nextval('seq_transaction_id'),
	sender_id int NOT NULL,
	transfer_amount decimal(13,2) NOT NULL,
	reciever_id int NOT NULL,
	CONSTRAINT PK_transaction PRIMARY KEY (transaction_id),
	CONSTRAINT FK_account_transaction_user FOREIGN KEY (sender_id) REFERENCES account (account_id),
	CONSTRAINT FK_account_transaction_other FOREIGN KEY (reciever_id) REFERENCES account (account_id)
);

CREATE TABLE account_transaction
(
	account_id int NOT NULL,
	transaction_id int NOT NULL,
	CONSTRAINT PK_acct_trans PRIMARY KEY (account_id, transaction_id),
	CONSTRAINT FK_trans FOREIGN KEY (account_id) REFERENCES account (account_id),
	CONSTRAINT fk_acct FOREIGN KEY (transaction_id) REFERENCES transaction (transaction_id)
);

COMMIT;
