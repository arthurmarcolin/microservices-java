package br.edu.atitus.marketplaceservice.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_servico_instalacao")
public class ServicoInstalacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String categoria;

    @Column(nullable = false)
    private String complexidade;  // 'baixa' | 'media' | 'alta'

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    @Column(name = "custo_real", nullable = false, precision = 12, scale = 2)
    private BigDecimal custoReal = BigDecimal.ZERO;

    @Column(name = "agendamento_separado", nullable = false)
    private boolean agendamentoSeparado = false;

    @Column(name = "disponivel_zona_terceiro", nullable = false)
    private boolean disponivelZonaTerceiro = true;

    private String descricao;

    @Column(nullable = false)
    private boolean ativo = true;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public UUID getId() { return id; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getComplexidade() { return complexidade; }
    public void setComplexidade(String complexidade) { this.complexidade = complexidade; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public BigDecimal getCustoReal() { return custoReal; }
    public void setCustoReal(BigDecimal custoReal) { this.custoReal = custoReal; }
    public boolean isAgendamentoSeparado() { return agendamentoSeparado; }
    public void setAgendamentoSeparado(boolean agendamentoSeparado) { this.agendamentoSeparado = agendamentoSeparado; }
    public boolean isDisponivelZonaTerceiro() { return disponivelZonaTerceiro; }
    public void setDisponivelZonaTerceiro(boolean disponivelZonaTerceiro) { this.disponivelZonaTerceiro = disponivelZonaTerceiro; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
