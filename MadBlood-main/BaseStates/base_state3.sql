insert into doador (id_doador, nome, idade, sexo, contacto, morada) values(1, 'Diogo Pinto', 20, 'M', '964993876', 'Rua do Brasil Nº259 3ºD');
insert into sangue (id_sangue, tipo_sangue, preco, id_doador) values(1, "O", 10.5, 1);
insert into hospital values(1, 'HUC', 'Rua do brasil Nº267', '964993456');
insert into stock (id_stock, morada, id_hospital) values(1, "Rua do Pinheiro Nº267", 1);
insert into doacao (id_doacao, id_sangue, id_stock, id_doador, data, quantidade) values(1, 1, 1, 1, STR_TO_DATE("09/10/2002", "%d/%m/%Y"), 5);
insert into armazenamento values(1, 1, 10);