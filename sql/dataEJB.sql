--clear tables
delete from users;
delete from coins;
delete from user_coins;
delete from countries;

--add data to USERS table
Insert into users (user_id, username, password, admin) values (1, 'Admin', '123', 1);

--add data to COUNTRIES table
Insert into countries (country_id, name) values (1, 'Ukraine');
Insert into countries (country_id, name) values (2, 'Polska');
Insert into countries (country_id, name) values (3, 'Italy');
Insert into countries (country_id, name) values (4, 'Canada');
Insert into countries (country_id, name) values (5, 'USA');

--add data to COINS table
Insert into coins (coin_id, country, year, name, metall, diameter_mm, value, weight, fullname) 
values (1, 1, 2004, '1 grivna', 'aluminum', 30, 1, 2, 'Ukrain 1 grivna');
Insert into coins (coin_id, country, year, name, metall, diameter_mm, value, weight, fullname) 
values (2, 1, 2005, '1 kop', 'aluminum', 20, 1, 2, 'Ukrain 1 kop');

--add data to USER_COINS
--Insert into user_coins (user_id, coin_id) values (1, 1);
--Insert into user_coins (user_id, coin_id) values (1, 2);

commit;
