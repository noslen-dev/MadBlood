insert into encomenda(id_encomenda, quantidade, tipo_sangue, data) values(1, 5, 'O', STR_TO_DATE("9/10/2002", "%d/%m/%Y"));
insert into tipo_sangue_encomenda (id_encomenda_sangue, tipo_sangue) values(1, "O");
insert into hospital (id_hospital, nome, morada, contacto) values(1, "HUC", "Rua do Brasil Nº260", 969146234);
insert into stock (id_stock, morada, id_hospital) values(1, "Rua do Pinheiro Nº267", 1);
insert into doador (id_doador, nome, idade, sexo, contacto, morada) values(1, 'Diogo Pinto', 20, 'M', '964993876', 'Rua do Brasil Nº259 3ºD');
insert into sangue (id_sangue, tipo_sangue, preco, id_doador) values(1, "O", 10.5, 1);
insert into armazenamento(id_sangue, id_stock, quantidade) values(1, 1, 5);
insert into encomenda_sangue (id_encomenda_sangue, id_encomenda, id_hospital, id_stock, id_sangue) values(1, 1, 1, 1, 1);