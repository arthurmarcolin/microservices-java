package br.edu.atitus.orderservice.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comprador_user_id", nullable = false)
    private Long compradorUserId;

    @Column(name = "produto_id", nullable = false)
    private Long produtoId;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "preco_original", nullable = false)
    private Double precoOriginal;

    @Column(name = "moeda_original", nullable = false)
    private String moedaOriginal;

    @Column(name = "preco_em_brl", nullable = false)
    private Double precoEmBrl;

    @Column(nullable = false)
    private String status;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        criadoEm = LocalDateTime.now();
        if (status == null) status = "pendente";
    }

    public Long getId() { return id; }
    public Long getCompradorUserId() { return compradorUserId; }
    public void setCompradorUserId(Long compradorUserId) { this.compradorUserId = compradorUserId; }
    public Long getProdutoId() { return produtoId; }
    public void setProdutoId(Long produtoId) { this.produtoId = produtoId; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public Double getPrecoOriginal() { return precoOriginal; }
    public void setPrecoOriginal(Double precoOriginal) { this.precoOriginal = precoOriginal; }
    public String getMoedaOriginal() { return moedaOriginal; }
    public void setMoedaOriginal(String moedaOriginal) { this.moedaOriginal = moedaOriginal; }
    public Double getPrecoEmBrl() { return precoEmBrl; }
    public void setPrecoEmBrl(Double precoEmBrl) { this.precoEmBrl = precoEmBrl; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getCriadoEm() { return criadoEm; }
}
