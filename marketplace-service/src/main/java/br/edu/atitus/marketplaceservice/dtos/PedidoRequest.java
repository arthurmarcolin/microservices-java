package br.edu.atitus.marketplaceservice.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record PedidoRequest(
    String compradorUserId,
    String lojistaId,
    String produtoId,
    String produtoNome,
    String produtoCategoria,
    BigDecimal valorProdutoNovo,
    BigDecimal valorInstalacao,
    BigDecimal valorFrete,
    BigDecimal valorAbatimentoTradeIn,
    BigDecimal comissaoPct,
    String cepEntrega,
    String zonaEntrega,
    UUID servicoInstalacaoId
) {}
