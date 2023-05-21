delete from users;

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- insert into users (id, username, email, password, locked, enabled) values
--     (1, 'vadim2422', 'breev.vadim@yandex.ru', '$2a$10$/0lgpCYgSUpMW8ocddFjfOhGyS8yFpik9LMNjhT/5SQFqNOJ8LSMe', false, True);
insert into users (id, username, email, password, locked, enabled) values
    (1, 'vadim2422', 'breev.vadim@yandex.ru', crypt('1234', gen_salt('bf', 10)), false, True);