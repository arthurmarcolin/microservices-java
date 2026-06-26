package br.edu.atitus.marketplaceservice.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_tradein_threshold")
public class TradeInThresholdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "abatimento_minimo", nullable = false, precision = 12, scale = 2)
    private BigDecimal abatimentoMinimo = new BigDecimal("50.00");

    @Column(name = "abatimento_maximo", precision = 12, scale = 2)
    private BigDecimal abatimentoMaximo;

    @Column(name = "idade_maxima_anos", nullable = false)
    private int idadeMaximaAnos = 10;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public UUID getId() { return id; }
    public BigDecimal getAbatimentoMinimo() { return abatimentoMinimo; }
    public void setAbatimentoMinimo(BigDecimal abatimentoMinimo) { this.abatimentoMinimo = abatimentoMinimo; }
    public BigDecimal getAbatimentoMaximo() { return abatimentoMaximo; }
    public void setAbatimentoMaximo(BigDecimal abatimentoMaximo) { this.abatimentoMaximo = abatimentoMaximo; }
    public int getIdadeMaximaAnos() { return idadeMaximaAnos; }
    public void setIdadeMaximaAnos(int idadeMaximaAnos) { this.idadeMaximaAnos = idadeMaximaAnos; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
