package br.edu.atitus.orderservice.dtos;

public record ProductResponse(
        Long id,
        String description,
        String brand,
        String model,
        String currency,
        Double price,
        Integer stock,
        Double convertedPrice,
        String environment,
        String targetCurrency
) {}
