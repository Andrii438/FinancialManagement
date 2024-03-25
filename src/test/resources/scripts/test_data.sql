DROP TABLE IF EXISTS financial_user, financial_goal;

CREATE TABLE IF NOT EXISTS financial_user (
                                id SERIAL PRIMARY KEY,
                                first_name VARCHAR(255),
                                surname VARCHAR(255),
                                birthdate DATE,
                                username VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS financial_goal (
                                id SERIAL PRIMARY KEY,
                                goal_name VARCHAR(255),
                                achieved_date DATE,
                                amount DECIMAL(19, 2),
                                user_id BIGINT,
                                CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES financial_user(id)
);


INSERT INTO financial_user (first_name, surname, birthdate, username)
VALUES
    ('John', 'Doe', '1990-05-15', 'johndoe123'),
    ('Alice', 'Smith', '1985-09-20', 'alicesmith456'),
    ('Bob', 'Johnson', '1992-03-10', 'bobjohnson789');

INSERT INTO financial_goal (goal_name, achieved_date, amount, user_id)
VALUES
    ('Retirement', '2030-12-31', 1000000, 1),
    ('Travel', '2025-06-30', 5000, 2),
    ('House Purchase', '2035-01-01', 250000, 3);
