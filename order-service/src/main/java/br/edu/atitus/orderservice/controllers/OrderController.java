package br.edu.atitus.orderservice.controllers;

import br.edu.atitus.orderservice.clients.CurrencyClient;
import br.edu.atitus.orderservice.clients.ProductClient;
import br.edu.atitus.orderservice.dtos.CurrencyResponse;
import br.edu.atitus.orderservice.dtos.OrderCreateRequest;
import br.edu.atitus.orderservice.dtos.ProductResponse;
import br.edu.atitus.orderservice.entities.OrderEntity;
import br.edu.atitus.orderservice.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    private final OrderRepository repository;
    private final ProductClient productClient;
    private final CurrencyClient currencyClient;

    @Value("${server.port}")
    private String port;

    public OrderController(OrderRepository repository, ProductClient productClient, CurrencyClient currencyClient) {
        this.repository = repository;
        this.productClient = productClient;
        this.currencyClient = currencyClient;
    }

    // POST /ws/orders — protegido via JWT no gateway
    @PostMapping("/ws/orders")
    public ResponseEntity<OrderEntity> createOrder(@RequestBody OrderCreateRequest req) {
        ProductResponse product = productClient.getProduct(req.produtoId(), "BRL");

        Double precoEmBrl = product.convertedPrice() != null && product.convertedPrice() > 0
                ? product.convertedPrice()
                : product.price();

        OrderEntity order = new OrderEntity();
        order.setCompradorUserId(req.compradorUserId());
        order.setProdutoId(req.produtoId());
        order.setDescricao(product.description() + " - " + product.brand());
        order.setPrecoOriginal(product.price());
        order.setMoedaOriginal(product.currency() != null ? product.currency() : "BRL");
        order.setPrecoEmBrl(precoEmBrl);

        OrderEntity saved = repository.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // GET /ws/orders/BRL — lista todos os pedidos com preço em BRL (protegido via JWT)
    @GetMapping("/ws/orders/BRL")
    public ResponseEntity<List<OrderEntity>> getAllOrdersInBrl() {
        List<OrderEntity> orders = repository.findAll();
        // precoEmBrl já é persistido na criação — retorna direto
        return ResponseEntity.ok(orders);
    }

    // GET /ws/orders — lista pedidos de um comprador específico
    @GetMapping("/ws/orders")
    public ResponseEntity<List<OrderEntity>> getOrdersByUser(@RequestParam Long compradorUserId) {
        return ResponseEntity.ok(
                repository.findByCompradorUserIdOrderByCriadoEmDesc(compradorUserId)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
