-- ============================================================
-- V3 — Atualiza valores de trade-in para mercado BR jun/2025
-- Seguro de rodar em cima de V2 existente (usa ON CONFLICT DO UPDATE)
-- ============================================================

-- Threshold: mínimo R$100, idade máx. 10 anos
UPDATE tb_tradein_threshold SET abatimento_minimo = 100.00 WHERE abatimento_minimo = 50.00;

-- Fatores de idade
INSERT INTO tb_tradein_fator_idade (faixa, fator) VALUES
  ('<1',  0.880),
  ('1-3', 0.750),
  ('3-5', 0.560),
  ('5-8', 0.380),
  ('>8',  0.180)
ON CONFLICT (faixa) DO UPDATE SET fator = EXCLUDED.fator;

-- Fatores de estado
INSERT INTO tb_tradein_fator_estado (estado, fator) VALUES
  ('perfeito',       1.000),
  ('defeitos_leves', 0.650),
  ('nao_funciona',   0.250)
ON CONFLICT (estado) DO UPDATE SET fator = EXCLUDED.fator;

-- Categorias existentes — atualiza valores base
INSERT INTO tb_tradein_base_categoria (categoria, valor_base) VALUES
  ('Geladeira',                 2200.00),
  ('Geladeira Premium',         4500.00),
  ('Freezer',                   1100.00),
  ('Lavadora',                  1800.00),
  ('Lavadora Premium',          3200.00),
  ('Secadora',                  1600.00),
  ('Lava e Seca',               3000.00),
  ('Fogão',                      750.00),
  ('Fogão Premium',             1800.00),
  ('Forno Elétrico',             500.00),
  ('Micro-ondas',                450.00),
  ('Ar Condicionado',           2400.00),
  ('Ar Condicionado Premium',   4500.00),
  ('Televisão',                 1400.00),
  ('TV Premium',                4000.00),
  ('Notebook',                  3000.00),
  ('Notebook Premium',          8000.00),
  ('Smartphone',                1400.00),
  ('Smartphone Premium',        5800.00),
  ('Tablet',                    1100.00),
  ('Tablet Premium',            4200.00),
  ('Outros',                     300.00)
ON CONFLICT (categoria) DO UPDATE SET valor_base = EXCLUDED.valor_base;
