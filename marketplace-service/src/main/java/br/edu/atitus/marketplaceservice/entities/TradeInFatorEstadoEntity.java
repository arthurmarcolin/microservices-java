package br.edu.atitus.marketplaceservice.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_tradein_fator_estado")
public class TradeInFatorEstadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String estado;  // 'perfeito' | 'defeitos_leves' | 'nao_funciona'

    @Column(nullable = false, precision = 5, scale = 3)
    private BigDecimal fator;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public UUID getId() { return id; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public BigDecimal getFator() { return fator; }
    public void setFator(BigDecimal fator) { this.fator = fator; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
