package br.edu.atitus.marketplaceservice.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_pedido")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "comprador_user_id", nullable = false)
    private String compradorUserId;

    @Column(name = "lojista_id")
    private String lojistaId;

    // Produto (referência ao product-service)
    @Column(name = "produto_id")
    private String produtoId;

    @Column(name = "produto_nome")
    private String produtoNome;

    @Column(name = "produto_categoria")
    private String produtoCategoria;

    // Componentes do pedido (SEMPRE separados — nunca só o total)
    @Column(name = "valor_produto_novo", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorProdutoNovo;

    @Column(name = "valor_instalacao", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorInstalacao = BigDecimal.ZERO;

    @Column(name = "valor_frete", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorFrete = BigDecimal.ZERO;

    @Column(name = "valor_abatimento_tradein", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorAbatimentoTradeIn = BigDecimal.ZERO;

    @Column(name = "valor_total_pagar", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorTotalPagar;

    // Componentes de receita da plataforma
    @Column(name = "comissao_plataforma", nullable = false, precision = 12, scale = 2)
    private BigDecimal comissaoPlataforma = BigDecimal.ZERO;

    @Column(name = "taxa_tradein", nullable = false, precision = 12, scale = 2)
    private BigDecimal taxaTradeIn = BigDecimal.ZERO;

    @Column(name = "margem_logistica", nullable = false, precision = 12, scale = 2)
    private BigDecimal margemLogistica = BigDecimal.ZERO;

    @Column(name = "repasse_lojista", nullable = false, precision = 12, scale = 2)
    private BigDecimal repasseLojista = BigDecimal.ZERO;

    // Entrega
    @Column(name = "cep_entrega", length = 8)
    private String cepEntrega;

    @Column(name = "zona_entrega")
    private String zonaEntrega;  // 'frota_propria' | 'terceiro'

    @Column(name = "servico_instalacao_id")
    private UUID servicoInstalacaoId;

    @Column(nullable = false)
    private String status = "pendente";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Getters e setters
    public UUID getId() { return id; }
    public String getCompradorUserId() { return compradorUserId; }
    public void setCompradorUserId(String compradorUserId) { this.compradorUserId = compradorUserId; }
    public String getLojistaId() { return lojistaId; }
    public void setLojistaId(String lojistaId) { this.lojistaId = lojistaId; }
    public String getProdutoId() { return produtoId; }
    public void setProdutoId(String produtoId) { this.produtoId = produtoId; }
    public String getProdutoNome() { return produtoNome; }
    public void setProdutoNome(String produtoNome) { this.produtoNome = produtoNome; }
    public String getProdutoCategoria() { return produtoCategoria; }
    public void setProdutoCategoria(String produtoCategoria) { this.produtoCategoria = produtoCategoria; }
    public BigDecimal getValorProdutoNovo() { return valorProdutoNovo; }
    public void setValorProdutoNovo(BigDecimal valorProdutoNovo) { this.valorProdutoNovo = valorProdutoNovo; }
    public BigDecimal getValorInstalacao() { return valorInstalacao; }
    public void setValorInstalacao(BigDecimal valorInstalacao) { this.valorInstalacao = valorInstalacao; }
    public BigDecimal getValorFrete() { return valorFrete; }
    public void setValorFrete(BigDecimal valorFrete) { this.valorFrete = valorFrete; }
    public BigDecimal getValorAbatimentoTradeIn() { return valorAbatimentoTradeIn; }
    public void setValorAbatimentoTradeIn(BigDecimal valorAbatimentoTradeIn) { this.valorAbatimentoTradeIn = valorAbatimentoTradeIn; }
    public BigDecimal getValorTotalPagar() { return valorTotalPagar; }
    public void setValorTotalPagar(BigDecimal valorTotalPagar) { this.valorTotalPagar = valorTotalPagar; }
    public BigDecimal getComissaoPlataforma() { return comissaoPlataforma; }
    public void setComissaoPlataforma(BigDecimal comissaoPlataforma) { this.comissaoPlataforma = comissaoPlataforma; }
    public BigDecimal getTaxaTradeIn() { return taxaTradeIn; }
    public void setTaxaTradeIn(BigDecimal taxaTradeIn) { this.taxaTradeIn = taxaTradeIn; }
    public BigDecimal getMargemLogistica() { return margemLogistica; }
    public void setMargemLogistica(BigDecimal margemLogistica) { this.margemLogistica = margemLogistica; }
    public BigDecimal getRepasseLojista() { return repasseLojista; }
    public void setRepasseLojista(BigDecimal repasseLojista) { this.repasseLojista = repasseLojista; }
    public String getCepEntrega() { return cepEntrega; }
    public void setCepEntrega(String cepEntrega) { this.cepEntrega = cepEntrega; }
    public String getZonaEntrega() { return zonaEntrega; }
    public void setZonaEntrega(String zonaEntrega) { this.zonaEntrega = zonaEntrega; }
    public UUID getServicoInstalacaoId() { return servicoInstalacaoId; }
    public void setServicoInstalacaoId(UUID servicoInstalacaoId) { this.servicoInstalacaoId = servicoInstalacaoId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
