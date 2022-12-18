insert into doador (id_doador, nome, idade, sexo, contacto, morada) values (1, "Diogo Pinto", 20, "M", "964993876", "Coimbra - Rua do Brasil Nº259 3ºD");
insert into doador  (id_doador, nome, idade, sexo, contacto, morada) values (2, "Francisca Almeida", 55, "F", "964553875", "Lisboa - Rua do Brasil Nº259 3ºE");
insert into doador (id_doador, nome, idade, sexo, contacto, morada) values(3, "João Durães", 50, "M", "964234875", "Lisboa - Rua do Brasil Nº259 4ºE");

insert into sangue (id_sangue, tipo_sangue, preco, id_doador) values (1, "O", 10.5, 1);
insert into sangue  (id_sangue, tipo_sangue, preco, id_doador) values(2, "AB", 10.5, 2);
insert into sangue  (id_sangue, tipo_sangue, preco, id_doador) values(3, "A", 10.5, 3);

insert into hospital (id_hospital, nome, morada, contacto) values(1, "HUC", "Rua do Brasil Nº260", 969146234);

insert into stock (id_stock, morada, id_hospital) values(1, "Rua do Pinheiro Nº267", 1);

insert into armazenamento(id_sangue, id_stock, quantidade) values(1, 1, 5);
insert into armazenamento(id_sangue, id_stock, quantidade) values(2, 1, 5);
insert into armazenamento(id_sangue, id_stock, quantidade) values(3, 1, 5);


insert into doacao (id_doacao, id_sangue, id_stock, id_doador, data, quantidade) values(1, 1, 1, 1, STR_TO_DATE("9/10/2002", "%d/%m/%Y"), 5);
insert into doacao (id_doacao, id_sangue, id_stock, id_doador, data, quantidade) values(2, 2, 1, 2, STR_TO_DATE("5/5/2001", "%d/%m/%Y"), 5);
insert into doacao (id_doacao, id_sangue, id_stock, id_doador, data, quantidade) values(3, 3, 1, 3, STR_TO_DATE("11/9/2001", "%d/%m/%Y"), 5);