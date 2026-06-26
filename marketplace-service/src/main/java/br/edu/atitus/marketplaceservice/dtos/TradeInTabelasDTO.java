package br.edu.atitus.marketplaceservice.dtos;

import java.math.BigDecimal;
import java.util.Map;

public record TradeInTabelasDTO(
    Map<String, BigDecimal> basePorCategoria,   // categoria → valorBase
    Map<String, BigDecimal> fatoresIdade,        // faixa     → fator
    Map<String, BigDecimal> fatoresEstado,       // estado    → fator
    ThresholdDTO thresholds
) {
    public record ThresholdDTO(
        BigDecimal abatimentoMinimo,
        BigDecimal abatimentoMaximo,
        int idadeMaximaAnos
    ) {}
}
