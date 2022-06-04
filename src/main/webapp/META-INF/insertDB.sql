USE exhibitiondb;

INSERT INTO administrators
(Admin_login, Admin_password, FirstName, LastName, Email, Phone)
VALUES ("admin01", "adm0101main", "Василь", "Петренко", "vasyliypetrenko@gmail.com", "380958284422");

INSERT INTO administrators
(Admin_login, Admin_password, FirstName, LastName, Email, Phone)
VALUES ("admin02", "adm0202main", "Станіслав", "Мацак", "stanislav.matsak@nure.ua", "380990731653");

INSERT INTO expositions
(EXP_name, EXP_startDate, EXP_finalDate, Price, Author)
VALUES ("Виставка творів Марії Приймаченко", "2022-07-01", "2022-07-07", 55, "Олена Безрудько");

INSERT INTO expositions
(EXP_name, EXP_startDate, EXP_finalDate, Price, Author)
VALUES ("Археологічні знахідки трипільскої культури", "2022-07-05", "2022-07-12", 80, "Національний музей українського народного декоративного мистецтва");

INSERT INTO expositions
(EXP_name, EXP_startDate, EXP_finalDate, Price, Author)
VALUES ("Козацькі пам'ятки", "2022-07-15", "2022-07-20", 60, "Запорізька громада");

INSERT INTO showrooms
(SR_name, Area)
VALUES ("Головна зала", 150);

INSERT INTO showrooms
(SR_name, Area)
VALUES ("Центральний виставковий хол", 100);

INSERT INTO showrooms
(SR_name, Area)
VALUES ("Кутова кімната", 75.5);

INSERT INTO showrooms
(SR_name, Area)
VALUES ("Картинний коридор", 90);

INSERT INTO expositions_showrooms
(Exposition_id, Showroom_id)
VALUES (1, 4);

INSERT INTO expositions_showrooms
(Exposition_id, Showroom_id)
VALUES (2, 2);

INSERT INTO expositions_showrooms
(Exposition_id, Showroom_id)
VALUES (2, 3);

INSERT INTO expositions_showrooms
(Exposition_id, Showroom_id)
VALUES (3, 1);


