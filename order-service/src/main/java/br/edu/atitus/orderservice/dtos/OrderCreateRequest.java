package br.edu.atitus.orderservice.dtos;

public record OrderCreateRequest(
        Long compradorUserId,
        Long produtoId,
        String targetCurrency
) {}
