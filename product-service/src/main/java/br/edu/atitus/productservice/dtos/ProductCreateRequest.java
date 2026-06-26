package br.edu.atitus.productservice.dtos;

public record ProductCreateRequest(
        String description,
        String brand,
        String model,
        Double price,
        Integer stock,
        String sellerId,
        String sellerName,
        String categoria,
        String condicao,
        String cidade,
        String estado
) {
}
