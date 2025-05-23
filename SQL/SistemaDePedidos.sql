CREATE TABLE Usuario (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  senha VARCHAR(100)
);

CREATE TABLE Cliente (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100),
  email VARCHAR(100)
);

CREATE TABLE Produto (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100),
  descricao TEXT,
  valor DECIMAL(10,2),
  quantidade_estoque INT
);

CREATE TABLE Pedido (
  id INT AUTO_INCREMENT PRIMARY KEY,
  cliente_id INT,
  data_pedido DATETIME DEFAULT CURRENT_TIMESTAMP,
  total DECIMAL(10,2),
  FOREIGN KEY(cliente_id) REFERENCES Cliente(id)
);

CREATE TABLE PedidoItem (
  pedido_id INT,
  produto_id INT,
  quantidade INT,
  sub_total DECIMAL(10,2),
  PRIMARY KEY(pedido_id, produto_id),
  FOREIGN KEY(pedido_id) REFERENCES Pedido(id),
  FOREIGN KEY(produto_id) REFERENCES Produto(id)
);
