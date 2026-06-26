-- ============================================================
-- Seeds marketplace-service
-- Valores baseados no mercado brasileiro de usados (jun/2025)
-- ADMIN DEVE REVISAR PERIODICAMENTE
-- ============================================================

-- Threshold inicial
INSERT INTO tb_tradein_threshold (abatimento_minimo, abatimento_maximo, idade_maxima_anos)
VALUES (100.00, NULL, 10);

-- Fatores de idade
-- Aplicados sobre o valor base da categoria
INSERT INTO tb_tradein_fator_idade (faixa, fator) VALUES
  ('<1',  0.880),  -- quase novo, pouca depreciação
  ('1-3', 0.750),  -- 1-3 anos: depreciação moderada
  ('3-5', 0.560),  -- 3-5 anos: depreciação significativa
  ('5-8', 0.380),  -- 5-8 anos: alto desgaste
  ('>8',  0.180);  -- acima de 8 anos: valor residual baixo

-- Fatores de estado declarado
INSERT INTO tb_tradein_fator_estado (estado, fator) VALUES
  ('perfeito',       1.000),  -- sem riscos, funcionando perfeitamente
  ('defeitos_leves', 0.650),  -- risco leve ou defeito cosmético
  ('nao_funciona',   0.250);  -- para peças / conserto

-- ─── Valor base por categoria ────────────────────────────────────────────────
-- = preço típico de revenda do aparelho SEMINOVO na categoria (BRL)
-- Exemplo iPhone 14 Pro Premium: 5800 × 0.750 × 1.000 = R$ 4.350 (1-3 anos perfeito)
INSERT INTO tb_tradein_base_categoria (categoria, valor_base) VALUES
  -- Linha branca — eletrodomésticos
  ('Geladeira',                 2200.00),  -- frost free 400L básico ~R$3.500 novo
  ('Geladeira Premium',         4500.00),  -- side by side / french door ~R$7.000+ novo
  ('Freezer',                   1100.00),
  ('Lavadora',                  1800.00),  -- 11-13kg básica ~R$2.800 novo
  ('Lavadora Premium',          3200.00),  -- 14-16kg inverter ~R$5.000 novo
  ('Secadora',                  1600.00),
  ('Lava e Seca',               3000.00),
  ('Fogão',                      750.00),  -- 4 bocas padrão
  ('Fogão Premium',             1800.00),  -- cooktop + forno embutido
  ('Forno Elétrico',             500.00),
  ('Micro-ondas',                450.00),
  ('Ar Condicionado',           2400.00),  -- split 9.000-12.000 BTU inverter
  ('Ar Condicionado Premium',   4500.00),  -- 18.000-24.000 BTU inverter ~R$7.000 novo
  -- TVs
  ('Televisão',                 1400.00),  -- 50" 4K básica ~R$2.500 novo
  ('TV Premium',                4000.00),  -- QLED/OLED 65"+ ~R$7.000 novo
  -- Informática
  ('Notebook',                  3000.00),  -- core i5/i7 intermediário ~R$4.500 novo
  ('Notebook Premium',          8000.00),  -- MacBook Air M2, Dell XPS ~R$12.000+ novo
  -- Smartphones — tier separado para estimativas realistas
  ('Smartphone',                1400.00),  -- básico/intermediário: Moto G, Samsung A ~R$2.000 novo
  ('Smartphone Premium',        5800.00),  -- iPhone Pro, Galaxy S/Ultra ~R$8.000+ novo
  -- Tablets
  ('Tablet',                    1100.00),  -- Samsung Tab A, Positivo
  ('Tablet Premium',            4200.00),  -- iPad Pro, Samsung Tab S ~R$7.000 novo
  ('Outros',                     300.00);

-- Serviços de instalação (placeholder)
INSERT INTO tb_servico_instalacao
  (categoria, complexidade, preco, custo_real, agendamento_separado, disponivel_zona_terceiro, descricao)
VALUES
  ('Geladeira',       'baixa',  80.00,  40.00, FALSE, TRUE,
   'Posicionar, nivelar e orientar tempo de espera para ligar.'),
  ('Freezer',         'baixa',  80.00,  40.00, FALSE, TRUE,
   'Posicionar e nivelar.'),
  ('Lavadora',        'media', 150.00,  70.00, FALSE, TRUE,
   'Conexão de água e dreno; depende de ponto hidráulico existente.'),
  ('Secadora',        'media', 130.00,  60.00, FALSE, TRUE,
   'Conexão elétrica e duto de ventilação.'),
  ('Lava e Seca',     'media', 160.00,  75.00, FALSE, TRUE,
   'Conexão de água, dreno e elétrica.'),
  ('Fogão',           'media', 120.00,  55.00, FALSE, TRUE,
   'Conexão de gás e teste de vazamento; técnico habilitado.'),
  ('Ar Condicionado', 'alta',  350.00, 180.00, TRUE,  FALSE,
   'Furação, suporte, vácuo e carga de gás. Pode exigir agendamento separado.');

-- Zonas de CEP — RS, SC, PR (exemplos de faixas reais; admin deve ajustar)
-- RS: 90000000–99999999
INSERT INTO tb_zona_cep (nome, tipo, estado, cep_inicio, cep_fim, preco_frete, custo_real) VALUES
  ('Grande Porto Alegre',     'frota_propria', 'RS', '90000000', '91999999',  50.00, 25.00),
  ('Serra Gaúcha',            'frota_propria', 'RS', '95000000', '95999999',  80.00, 40.00),
  ('Litoral Gaúcho',          'terceiro',      'RS', '95200000', '95299999', 120.00, 70.00),
  ('Interior RS',             'terceiro',      'RS', '96000000', '99999999', 150.00, 90.00),
-- SC: 88000000–89999999
  ('Grande Florianópolis',    'frota_propria', 'SC', '88000000', '88199999',  60.00, 30.00),
  ('Norte Catarinense',       'frota_propria', 'SC', '89200000', '89299999',  90.00, 45.00),
  ('Oeste Catarinense',       'terceiro',      'SC', '89800000', '89999999', 140.00, 85.00),
-- PR: 80000000–87999999
  ('Grande Curitiba',         'frota_propria', 'PR', '80000000', '83999999',  55.00, 27.00),
  ('Londrina / Maringá',      'frota_propria', 'PR', '86000000', '87499999',  85.00, 42.00),
  ('Interior PR',             'terceiro',      'PR', '84000000', '85999999', 130.00, 75.00);
