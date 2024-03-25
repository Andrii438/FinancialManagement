CREATE TABLE financial_user (
                        id SERIAL PRIMARY KEY,
                        first_name VARCHAR2(255),
                        surname VARCHAR2(255),
                        birthdate DATE,
                        username VARCHAR2(255)
);

CREATE TABLE financial_goal (
                                id SERIAL PRIMARY KEY,
                                goal_name VARCHAR2(255),
                                achieved_date DATE,
                                amount DECIMAL(19, 2),
                                user_id BIGINT,
                                CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES financial_user(id)
);
