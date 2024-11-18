CREATE TABLE USERS (
    internal_id int primary key auto_increment,
    external_id int,
    username varchar(255)
);

CREATE TABLE account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_name VARCHAR(255),
    bank VARCHAR(255),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    balance DECIMAL(15, 2),
    currency VARCHAR(255),
    user_internal_id INT,
    FOREIGN KEY (user_internal_id) REFERENCES users(internal_id)
);


CREATE TABLE transaction (
    id int PRIMARY KEY AUTO_INCREMENT,
    amount DECIMAL(15, 2),
    transaction_type VARCHAR(255),
    date TIMESTAMP,
    sender_id BIGINT,
    receiver_id BIGINT,
    user_internal_id BIGINT,
    FOREIGN KEY (sender_id) REFERENCES account(id),
    FOREIGN KEY (receiver_id) REFERENCES account(id),
    FOREIGN KEY (user_internal_id) REFERENCES users(internal_id)
);

