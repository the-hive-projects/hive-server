INSERT INTO user_infos (id, firstname, lastname, created_at)
VALUES (1, 'test-firstname-01', 'test-lastname-01', SELECT EXTRACT(EPOCH from CURRENT_TIMESTAMP()) * 1000),
       (2, 'test-firstname-02', 'test-lastname-02', SELECT EXTRACT(EPOCH from CURRENT_TIMESTAMP()) * 1000),
       (3, 'test-firstname-03', 'test-lastname-03', SELECT EXTRACT(EPOCH from CURRENT_TIMESTAMP()) * 1000),
       (4, 'test-firstname-04', 'test-lastname-04', SELECT EXTRACT(EPOCH from CURRENT_TIMESTAMP()) * 1000),
       (5, 'test-firstname-05', 'test-lastname-05', SELECT EXTRACT(EPOCH from CURRENT_TIMESTAMP()) * 1000);

INSERT INTO users (id, username, email, password, user_info_id)
VALUES (1, 'test-username-01', 'test-email01', '{noop}password', 1),
       (2, 'test-username-02', 'test-email02', '{noop}password', 2),
       (3, 'test-username-03', 'test-email03', '{noop}password', 3),
       (4, 'test-username-04', 'test-email04', '{noop}password', 4),
       (5, 'test-username-05', 'test-email05', '{noop}password', 5);


