package br.edu.atitus.marketplaceservice.controllers;

import br.edu.atitus.marketplaceservice.dtos.PedidoRequest;
import br.edu.atitus.marketplaceservice.entities.PedidoEntity;
import br.edu.atitus.marketplaceservice.repositories.PedidoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("orders")
@CrossOrigin(origins = "*")
public class OrderController {

    private final PedidoRepository pedidoRepo;

    public OrderController(PedidoRepository pedidoRepo) {
        this.pedidoRepo = pedidoRepo;
    }

    // POST /orders — cria um pedido com todos os componentes separados
    @PostMapping
    public ResponseEntity<PedidoEntity> criarPedido(@RequestBody PedidoRequest req) {
        BigDecimal comissaoPct = req.comissaoPct() != null ? req.comissaoPct() : new BigDecimal("10.0");
        BigDecimal comissao = req.valorProdutoNovo()
            .multiply(comissaoPct.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP))
            .setScale(2, RoundingMode.HALF_UP);
        BigDecimal repasse = req.valorProdutoNovo().subtract(comissao);

        BigDecimal instVal = req.valorInstalacao() != null ? req.valorInstalacao() : BigDecimal.ZERO;
        BigDecimal freteVal = req.valorFrete() != null ? req.valorFrete() : BigDecimal.ZERO;
        BigDecimal abatVal = req.valorAbatimentoTradeIn() != null ? req.valorAbatimentoTradeIn() : BigDecimal.ZERO;
        // margem logística simplificada — diferença entre cobrança e custo (custo_real vem das tabelas, não aqui)
        BigDecimal margemLog = instVal.add(freteVal);

        BigDecimal total = req.valorProdutoNovo()
            .add(instVal)
            .add(freteVal)
            .subtract(abatVal)
            .max(BigDecimal.ZERO)
            .setScale(2, RoundingMode.HALF_UP);

        var pedido = new PedidoEntity();
        pedido.setCompradorUserId(req.compradorUserId());
        pedido.setLojistaId(req.lojistaId());
        pedido.setProdutoId(req.produtoId());
        pedido.setProdutoNome(req.produtoNome());
        pedido.setProdutoCategoria(req.produtoCategoria());
        pedido.setValorProdutoNovo(req.valorProdutoNovo().setScale(2, RoundingMode.HALF_UP));
        pedido.setValorInstalacao(instVal);
        pedido.setValorFrete(freteVal);
        pedido.setValorAbatimentoTradeIn(abatVal);
        pedido.setValorTotalPagar(total);
        pedido.setComissaoPlataforma(comissao);
        pedido.setRepasseLojista(repasse.setScale(2, RoundingMode.HALF_UP));
        pedido.setMargemLogistica(margemLog);
        pedido.setCepEntrega(req.cepEntrega());
        pedido.setZonaEntrega(req.zonaEntrega());
        pedido.setServicoInstalacaoId(req.servicoInstalacaoId());
        pedido.setStatus("pendente");

        return ResponseEntity.ok(pedidoRepo.save(pedido));
    }

    // GET /orders?compradorUserId=
    @GetMapping
    public ResponseEntity<List<PedidoEntity>> listarPorComprador(
            @RequestParam String compradorUserId) {
        return ResponseEntity.ok(
            pedidoRepo.findByCompradorUserIdOrderByCreatedAtDesc(compradorUserId)
        );
    }

    // GET /orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PedidoEntity> buscarPorId(@PathVariable UUID id) {
        return pedidoRepo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /orders/{id}/status
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoEntity> atualizarStatus(
            @PathVariable UUID id,
            @RequestBody java.util.Map<String, String> body) {
        return pedidoRepo.findById(id).map(p -> {
            p.setStatus(body.get("status"));
            p.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(pedidoRepo.save(p));
        }).orElse(ResponseEntity.notFound().build());
    }
}
