CREATE TABLE ordens_cardapio
(
    id                INT AUTO_INCREMENT NOT NULL,
    valor_de_registro DECIMAL            NULL,
    quantidade        INT                NULL,
    CONSTRAINT pk_ordens_cardapio PRIMARY KEY (id)
);