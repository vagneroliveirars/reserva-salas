insert into Local (descricao) values ('Local 1');
insert into Local (descricao) values ('Local 2');

insert into Sala (descricao) values ('Sala Amarela');
insert into Sala (descricao) values ('Sala Azul');
insert into Sala (descricao) values ('Sala Vermelha');
insert into Sala (descricao) values ('Sala Branca');

insert into Local_Sala (Local_id, salas_id) values (1, 1);
insert into Local_Sala (Local_id, salas_id) values (1, 2);
insert into Local_Sala (Local_id, salas_id) values (2, 3);
insert into Local_Sala (Local_id, salas_id) values (2, 4);
