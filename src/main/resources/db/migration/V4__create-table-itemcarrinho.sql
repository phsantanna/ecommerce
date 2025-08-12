CREATE TABLE item_carrinho
(
    item_carrinho_id BIGINT  NOT NULL,
    carrinho_id      BIGINT  NOT NULL,
    produto_id       BIGINT  NOT NULL,
    quantidade       INTEGER NOT NULL,
    CONSTRAINT pk_itemcarrinho PRIMARY KEY (item_carrinho_id)
);

ALTER TABLE item_carrinho
    ADD CONSTRAINT FK_ITEMCARRINHO_ON_CARRINHO FOREIGN KEY (carrinho_id) REFERENCES carrinho (carrinho_id);

ALTER TABLE item_carrinho
    ADD CONSTRAINT FK_ITEMCARRINHO_ON_PRODUTO FOREIGN KEY (produto_id) REFERENCES produtos (produto_id);