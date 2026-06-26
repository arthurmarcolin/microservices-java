package br.edu.atitus.marketplaceservice.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_tradein_solicitacao")
public class TradeInSolicitacaoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "pedido_id")
    private UUID pedidoId;

    @Column(name = "comprador_user_id", nullable = false)
    private String compradorUserId;

    // Aparelho declarado
    @Column(nullable = false)
    private String categoria;

    private String marca;
    private String modelo;

    @Column(name = "idade_faixa", nullable = false)
    private String idadeFaixa;   // '<1' | '1-3' | '3-5' | '5-8' | '>8'

    @Column(name = "estado_declarado", nullable = false)
    private String estadoDeclarado;  // 'perfeito' | 'defeitos_leves' | 'nao_funciona'

    @Column(name = "fotos_declaradas", columnDefinition = "TEXT[]")
    private String[] fotosDeclaradas;

    // Valores
    @Column(name = "valor_estimado", precision = 12, scale = 2)
    private BigDecimal valorEstimado;

    @Column(name = "valor_confirmado", precision = 12, scale = 2)
    private BigDecimal valorConfirmado;

    @Column(name = "diferenca_cobrada", precision = 12, scale = 2)
    private BigDecimal diferencaCobrada = BigDecimal.ZERO;

    @Column(nullable = false)
    private String status = "estimado";
    // estimado | confirmado | divergente | aceito_pelo_comprador | recusado_pelo_comprador

    // Avaliação física
    @Column(name = "avaliador_user_id")
    private String avaliadorUserId;

    @Column(name = "avaliacao_fotos", columnDefinition = "TEXT[]")
    private String[] avaliacaoFotos;

    @Column(name = "avaliacao_laudo", columnDefinition = "TEXT")
    private String avaliacaoLaudo;

    @Column(name = "avaliacao_data")
    private LocalDateTime avaliacaoData;

    @Column(name = "pix_txid")
    private String pixTxid;

    @Column(name = "pix_status")
    private String pixStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Getters e setters
    public UUID getId() { return id; }
    public UUID getPedidoId() { return pedidoId; }
    public void setPedidoId(UUID pedidoId) { this.pedidoId = pedidoId; }
    public String getCompradorUserId() { return compradorUserId; }
    public void setCompradorUserId(String compradorUserId) { this.compradorUserId = compradorUserId; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getIdadeFaixa() { return idadeFaixa; }
    public void setIdadeFaixa(String idadeFaixa) { this.idadeFaixa = idadeFaixa; }
    public String getEstadoDeclarado() { return estadoDeclarado; }
    public void setEstadoDeclarado(String estadoDeclarado) { this.estadoDeclarado = estadoDeclarado; }
    public String[] getFotosDeclaradas() { return fotosDeclaradas; }
    public void setFotosDeclaradas(String[] fotosDeclaradas) { this.fotosDeclaradas = fotosDeclaradas; }
    public BigDecimal getValorEstimado() { return valorEstimado; }
    public void setValorEstimado(BigDecimal valorEstimado) { this.valorEstimado = valorEstimado; }
    public BigDecimal getValorConfirmado() { return valorConfirmado; }
    public void setValorConfirmado(BigDecimal valorConfirmado) { this.valorConfirmado = valorConfirmado; }
    public BigDecimal getDiferencaCobrada() { return diferencaCobrada; }
    public void setDiferencaCobrada(BigDecimal diferencaCobrada) { this.diferencaCobrada = diferencaCobrada; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAvaliadorUserId() { return avaliadorUserId; }
    public void setAvaliadorUserId(String avaliadorUserId) { this.avaliadorUserId = avaliadorUserId; }
    public String[] getAvaliacaoFotos() { return avaliacaoFotos; }
    public void setAvaliacaoFotos(String[] avaliacaoFotos) { this.avaliacaoFotos = avaliacaoFotos; }
    public String getAvaliacaoLaudo() { return avaliacaoLaudo; }
    public void setAvaliacaoLaudo(String avaliacaoLaudo) { this.avaliacaoLaudo = avaliacaoLaudo; }
    public LocalDateTime getAvaliacaoData() { return avaliacaoData; }
    public void setAvaliacaoData(LocalDateTime avaliacaoData) { this.avaliacaoData = avaliacaoData; }
    public String getPixTxid() { return pixTxid; }
    public void setPixTxid(String pixTxid) { this.pixTxid = pixTxid; }
    public String getPixStatus() { return pixStatus; }
    public void setPixStatus(String pixStatus) { this.pixStatus = pixStatus; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
