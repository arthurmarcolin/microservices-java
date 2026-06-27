-- Classifica todos os produtos existentes por categoria
UPDATE tb_product SET categoria = 'Smartphone'
  WHERE description LIKE 'iPhone%'
     OR description LIKE 'Galaxy%'
     OR description LIKE 'Moto%'
     OR description LIKE 'Redmi%'
     OR description LIKE 'Pixel%'
     OR description LIKE 'OnePlus%';

UPDATE tb_product SET categoria = 'Geladeira'
  WHERE description LIKE 'Geladeira%';

UPDATE tb_product SET categoria = 'Fogão'
  WHERE description LIKE 'Fogão%';

UPDATE tb_product SET categoria = 'Micro-ondas'
  WHERE description LIKE 'Micro-ondas%';

UPDATE tb_product SET categoria = 'Lavadora'
  WHERE description LIKE 'Lavadora%';

UPDATE tb_product SET categoria = 'Televisão'
  WHERE description LIKE 'Smart TV%';

UPDATE tb_product SET categoria = 'Notebook'
  WHERE description LIKE 'Notebook%';

-- Marca tudo que ficou sem categoria como Outros
UPDATE tb_product SET categoria = 'Outros'
  WHERE categoria IS NULL;

-- Produtos do seed inicial são novos e sem localização definida
UPDATE tb_product SET condicao = 'novo'
  WHERE condicao IS NULL;
