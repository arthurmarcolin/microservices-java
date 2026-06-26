package br.edu.atitus.marketplaceservice.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record TradeInSolicitacaoRequest(
    UUID pedidoId,
    String compradorUserId,
    String categoria,
    String marca,
    String modelo,
    String idadeFaixa,
    String estadoDeclarado,
    String[] fotosDeclaradas,
    BigDecimal valorEstimado
) {}
