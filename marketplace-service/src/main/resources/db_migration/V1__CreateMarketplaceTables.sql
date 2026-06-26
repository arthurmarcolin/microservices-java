-- ============================================================
-- EletroHub marketplace-service — schema inicial
-- Flyway V1
-- ============================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Trade-in: valor base por categoria
CREATE TABLE tb_tradein_base_categoria (
  id         UUID        NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  categoria  VARCHAR(100) NOT NULL UNIQUE,
  valor_base NUMERIC(12,2) NOT NULL,
  ativo      BOOLEAN      NOT NULL DEFAULT TRUE,
  updated_at TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- Trade-in: fator multiplicador por faixa de idade
CREATE TABLE tb_tradein_fator_idade (
  id         UUID        NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  faixa      VARCHAR(10) NOT NULL UNIQUE,
  fator      NUMERIC(5,3) NOT NULL,
  updated_at TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- Trade-in: fator multiplicador por estado declarado
CREATE TABLE tb_tradein_fator_estado (
  id         UUID        NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  estado     VARCHAR(30) NOT NULL UNIQUE,
  fator      NUMERIC(5,3) NOT NULL,
  updated_at TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- Trade-in: thresholds de valor / idade (única linha — editável pelo admin)
CREATE TABLE tb_tradein_threshold (
  id                  UUID          NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  abatimento_minimo   NUMERIC(12,2) NOT NULL DEFAULT 50.00,
  abatimento_maximo   NUMERIC(12,2),
  idade_maxima_anos   INTEGER       NOT NULL DEFAULT 10,
  updated_at          TIMESTAMP     NOT NULL DEFAULT NOW()
);

-- Zonas de CEP (configurável pelo admin — nunca hardcoded)
CREATE TABLE tb_zona_cep (
  id          UUID         NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  nome        VARCHAR(100) NOT NULL,
  tipo        VARCHAR(20)  NOT NULL, -- 'frota_propria' | 'terceiro' | 'sem_cobertura'
  estado      VARCHAR(2),
  cep_inicio  CHAR(8)      NOT NULL,
  cep_fim     CHAR(8)      NOT NULL,
  preco_frete NUMERIC(12,2) NOT NULL DEFAULT 0,
  custo_real  NUMERIC(12,2) NOT NULL DEFAULT 0,
  ativo       BOOLEAN       NOT NULL DEFAULT TRUE,
  created_at  TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_zona_cep_lookup ON tb_zona_cep(cep_inicio, cep_fim) WHERE ativo = TRUE;

-- Serviços de instalação (configurável pelo admin)
CREATE TABLE tb_servico_instalacao (
  id                       UUID          NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  categoria                VARCHAR(100)  NOT NULL UNIQUE,
  complexidade             VARCHAR(10)   NOT NULL, -- 'baixa' | 'media' | 'alta'
  preco                    NUMERIC(12,2) NOT NULL,
  custo_real               NUMERIC(12,2) NOT NULL DEFAULT 0,
  agendamento_separado     BOOLEAN       NOT NULL DEFAULT FALSE,
  disponivel_zona_terceiro BOOLEAN       NOT NULL DEFAULT TRUE,
  descricao                TEXT,
  ativo                    BOOLEAN       NOT NULL DEFAULT TRUE,
  updated_at               TIMESTAMP     NOT NULL DEFAULT NOW()
);

-- Pedidos (componentes sempre separados)
CREATE TABLE tb_pedido (
  id                       UUID          NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  comprador_user_id        VARCHAR(255)  NOT NULL,
  lojista_id               VARCHAR(255),
  produto_id               VARCHAR(255),
  produto_nome             VARCHAR(500),
  produto_categoria        VARCHAR(100),
  -- Componentes do pedido
  valor_produto_novo       NUMERIC(12,2) NOT NULL,
  valor_instalacao         NUMERIC(12,2) NOT NULL DEFAULT 0,
  valor_frete              NUMERIC(12,2) NOT NULL DEFAULT 0,
  valor_abatimento_tradein NUMERIC(12,2) NOT NULL DEFAULT 0,
  valor_total_pagar        NUMERIC(12,2) NOT NULL,
  -- Componentes de receita da plataforma
  comissao_plataforma      NUMERIC(12,2) NOT NULL DEFAULT 0,
  taxa_tradein             NUMERIC(12,2) NOT NULL DEFAULT 0,
  margem_logistica         NUMERIC(12,2) NOT NULL DEFAULT 0,
  repasse_lojista          NUMERIC(12,2) NOT NULL DEFAULT 0,
  -- Entrega
  cep_entrega              CHAR(8),
  zona_entrega             VARCHAR(20),
  servico_instalacao_id    UUID,
  status                   VARCHAR(30)   NOT NULL DEFAULT 'pendente',
  created_at               TIMESTAMP     NOT NULL DEFAULT NOW(),
  updated_at               TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_pedido_comprador ON tb_pedido(comprador_user_id);
CREATE INDEX idx_pedido_status    ON tb_pedido(status);

-- Trade-in: solicitações
CREATE TABLE tb_tradein_solicitacao (
  id                  UUID          NOT NULL DEFAULT gen_random_uuid() PRIMARY KEY,
  pedido_id           UUID          REFERENCES tb_pedido(id),
  comprador_user_id   VARCHAR(255)  NOT NULL,
  categoria           VARCHAR(100)  NOT NULL,
  marca               VARCHAR(100),
  modelo              VARCHAR(200),
  idade_faixa         VARCHAR(10)   NOT NULL,
  estado_declarado    VARCHAR(30)   NOT NULL,
  fotos_declaradas    TEXT[],
  valor_estimado      NUMERIC(12,2),
  valor_confirmado    NUMERIC(12,2),
  diferenca_cobrada   NUMERIC(12,2) NOT NULL DEFAULT 0,
  status              VARCHAR(30)   NOT NULL DEFAULT 'estimado',
  avaliador_user_id   VARCHAR(255),
  avaliacao_fotos     TEXT[],
  avaliacao_laudo     TEXT,
  avaliacao_data      TIMESTAMP,
  pix_txid            VARCHAR(100),
  pix_status          VARCHAR(20),
  created_at          TIMESTAMP     NOT NULL DEFAULT NOW(),
  updated_at          TIMESTAMP     NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_tradein_comprador ON tb_tradein_solicitacao(comprador_user_id);
CREATE INDEX idx_tradein_pedido    ON tb_tradein_solicitacao(pedido_id);
