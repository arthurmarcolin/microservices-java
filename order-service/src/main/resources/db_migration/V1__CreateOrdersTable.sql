CREATE TABLE tb_order (
    id                  BIGSERIAL PRIMARY KEY,
    comprador_user_id   BIGINT        NOT NULL,
    produto_id          BIGINT        NOT NULL,
    descricao           VARCHAR(255)  NOT NULL,
    preco_original      DOUBLE PRECISION NOT NULL,
    moeda_original      VARCHAR(10)   NOT NULL DEFAULT 'BRL',
    preco_em_brl        DOUBLE PRECISION NOT NULL,
    status              VARCHAR(50)   NOT NULL DEFAULT 'pendente',
    criado_em           TIMESTAMP     NOT NULL DEFAULT NOW()
);
