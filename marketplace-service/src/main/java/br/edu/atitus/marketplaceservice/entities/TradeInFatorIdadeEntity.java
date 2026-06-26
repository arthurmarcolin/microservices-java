package br.edu.atitus.marketplaceservice.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_tradein_fator_idade")
public class TradeInFatorIdadeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String faixa;   // '<1' | '1-3' | '3-5' | '5-8' | '>8'

    @Column(nullable = false, precision = 5, scale = 3)
    private BigDecimal fator;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public UUID getId() { return id; }
    public String getFaixa() { return faixa; }
    public void setFaixa(String faixa) { this.faixa = faixa; }
    public BigDecimal getFator() { return fator; }
    public void setFator(BigDecimal fator) { this.fator = fator; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
