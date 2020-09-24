INSERT INTO cliente (id, nome, email) VALUES
(1, 'Mariazinha', 'mariazinha@email.com'),
(2, 'Joãozinho', 'joaozinho@email.com');

INSERT INTO funcionario (id_funcionario, nome, email, senha, acesso) VALUES
(1, 'admin', 'admin@admin.com', '46070D4BF934FB0D4B06D9E2C46E346944E322444900A435D7D9A95E6D7435F5', 0),
(2, 'funcionario', 'funcionario@email.com', '46070D4BF934FB0D4B06D9E2C46E346944E322444900A435D7D9A95E6D7435F5', 1);

INSERT INTO endereco (id_endereco, cep, rua, numero , bairro, cidade, estado) VALUES
(1, '72015035', 'CSA 3', 4, 'Taguatinga Sul (Taguatinga)', 'Brasília', 'DF'),
(2, '03359080', 'Praça Barros Cabral',25, 'Vila Formosa', 'São Paulo', 'SP');

INSERT INTO endereco_cliente (id, id_endereco, id_cliente) VALUES
(1, 1, 2),
(2, 2, 2);