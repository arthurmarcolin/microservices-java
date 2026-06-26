package br.edu.atitus.marketplaceservice.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_zona_cep")
public class ZonaCepEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String tipo;  // 'frota_propria' | 'terceiro' | 'sem_cobertura'

    private String estado;  // 'RS' | 'SC' | 'PR'

    @Column(name = "cep_inicio", nullable = false, length = 8)
    private String cepInicio;

    @Column(name = "cep_fim", nullable = false, length = 8)
    private String cepFim;

    @Column(name = "preco_frete", nullable = false, precision = 12, scale = 2)
    private BigDecimal precoFrete = BigDecimal.ZERO;

    @Column(name = "custo_real", nullable = false, precision = 12, scale = 2)
    private BigDecimal custoReal = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCepInicio() { return cepInicio; }
    public void setCepInicio(String cepInicio) { this.cepInicio = cepInicio; }
    public String getCepFim() { return cepFim; }
    public void setCepFim(String cepFim) { this.cepFim = cepFim; }
    public BigDecimal getPrecoFrete() { return precoFrete; }
    public void setPrecoFrete(BigDecimal precoFrete) { this.precoFrete = precoFrete; }
    public BigDecimal getCustoReal() { return custoReal; }
    public void setCustoReal(BigDecimal custoReal) { this.custoReal = custoReal; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
