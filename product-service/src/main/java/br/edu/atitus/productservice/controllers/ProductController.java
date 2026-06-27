package br.edu.atitus.productservice.controllers;

import br.edu.atitus.productservice.clients.CurrencyClient;
import br.edu.atitus.productservice.clients.CurrencyResponse;
import br.edu.atitus.productservice.dtos.ProductCreateRequest;
import br.edu.atitus.productservice.dtos.ProductDTO;
import br.edu.atitus.productservice.entities.ProductEntity;
import br.edu.atitus.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private static final double USD_TO_BRL_FALLBACK = 5.75;

    private final ProductRepository repository;
    private final CurrencyClient currencyClient;
    private final CacheManager cacheManager;

    public ProductController(ProductRepository repository, CurrencyClient currencyClient, CacheManager cacheManager) {
        this.repository = repository;
        this.currencyClient = currencyClient;
        this.cacheManager = cacheManager;
    }

    @Value("${server.port}")
    private String port;

    // ── GET /products?q= ────────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll(
            @RequestParam(required = false) String q) {

        List<ProductEntity> entities = (q != null && !q.isBlank())
                ? repository.search(q.trim())
                : repository.findAll();

        List<ProductDTO> dtos = entities.stream().map(e -> toDTO(e, convertPrice(e))).toList();
        return ResponseEntity.ok(dtos);
    }

    // ── GET /products/{id}?targetCurrency= ──────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(
            @PathVariable Long id,
            @RequestParam String targetCurrency) throws Exception {

        targetCurrency = targetCurrency.toUpperCase();
        ProductEntity entity = repository.findById(id)
                .orElseThrow(() -> new Exception("Product not found!"));

        Double convertedPrice;
        String environment = "Product-service running on port: " + port;

        if (targetCurrency.equals(entity.getCurrency())) {
            convertedPrice = entity.getPrice();
        } else {
            String nameCache = "ConvertedValue";
            String keyCache = entity.getCurrency() + "-" + targetCurrency;
            Double cachedRate = cacheManager.getCache(nameCache) != null
                    ? (Double) cacheManager.getCache(nameCache).get(keyCache, Double.class)
                    : null;
            if (cachedRate != null) {
                convertedPrice = cachedRate * entity.getPrice();
                environment = environment + " - Currency in cache";
            } else {
                CurrencyResponse currency = currencyClient.getCurrency(entity.getCurrency(), targetCurrency);
                if (currency != null) {
                    convertedPrice = entity.getPrice() * currency.conversionRate();
                    environment = environment + " - " + currency.environment();
                    cacheManager.getCache(nameCache).put(keyCache, currency.conversionRate());
                } else {
                    convertedPrice = -1.0;
                    environment = environment + " - Currency Fallback";
                }
            }
        }

        return ResponseEntity.ok(toDTO(entity, convertedPrice));
    }

    // ── PUT /ws/product/{id} — protegido via JWT no gateway ─────────────────────
    @PutMapping("/ws/product/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductCreateRequest req) throws Exception {
        ProductEntity e = repository.findById(id)
                .orElseThrow(() -> new Exception("Product not found!"));
        if (req.description() != null) e.setDescription(req.description());
        if (req.brand() != null)       e.setBrand(req.brand());
        if (req.model() != null)       e.setModel(req.model());
        if (req.price() != null)       e.setPrice(req.price());
        if (req.stock() != null)       e.setStock(req.stock());
        if (req.categoria() != null)   e.setCategoria(req.categoria());
        if (req.condicao() != null)    e.setCondicao(req.condicao());
        if (req.cidade() != null)      e.setCidade(req.cidade());
        if (req.estado() != null)      e.setEstado(req.estado());
        ProductEntity saved = repository.save(e);
        return ResponseEntity.ok(toDTO(saved, saved.getPrice()));
    }

    // ── DELETE /ws/product/{id} — protegido via JWT no gateway ──────────────────
    @DeleteMapping("/ws/product/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws Exception {
        if (!repository.existsById(id)) throw new Exception("Product not found!");
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ── POST /products ───────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductCreateRequest req) {
        ProductEntity e = new ProductEntity();
        e.setDescription(req.description());
        e.setBrand(req.brand() != null ? req.brand() : (req.sellerName() != null ? req.sellerName() : "Anúncio"));
        e.setModel(req.model() != null ? req.model() : "");
        e.setCurrency("BRL");
        e.setPrice(req.price() != null ? req.price() : 0.0);
        e.setStock(req.stock() != null ? req.stock() : 1);
        e.setSellerId(req.sellerId());
        e.setSellerName(req.sellerName());
        e.setCategoria(req.categoria());
        e.setCondicao(req.condicao());
        e.setCidade(req.cidade());
        e.setEstado(req.estado());
        ProductEntity saved = repository.save(e);
        return ResponseEntity.ok(toDTO(saved, saved.getPrice()));
    }

    // ── helpers ──────────────────────────────────────────────────────────────────

    private double convertPrice(ProductEntity e) {
        if (e.getCurrency() == null || e.getCurrency().equals("BRL")) return e.getPrice();
        return Math.round(e.getPrice() * USD_TO_BRL_FALLBACK * 100.0) / 100.0;
    }

    private ProductDTO toDTO(ProductEntity e, Double convertedPrice) {
        return new ProductDTO(
                e.getId(),
                e.getDescription(),
                e.getBrand(),
                e.getModel(),
                e.getCurrency(),
                e.getPrice(),
                e.getStock(),
                e.getImageURL(),
                convertedPrice,
                "product-service:" + port,
                "BRL",
                e.getSellerId(),
                e.getSellerName(),
                e.getCategoria(),
                e.getCondicao(),
                e.getCidade(),
                e.getEstado()
        );
    }
    @GetMapping("/noconverter/{idProduct}")
    public ResponseEntity<ProductDTO> getProductNoConverter(@PathVariable Long idProduct) throws Exception {
        var product = repository.findById(idProduct)
                .orElseThrow(() -> new Exception("Produto não encontrado!"));

        ProductDTO dto = new ProductDTO(
                product.getId(),
                product.getDescription(),
                product.getBrand(),
                product.getModel(),
                product.getCurrency(),
                product.getPrice(),
                product.getStock(),
                product.getImageURL(),
                -1.,
                "Product-service running on port: " + port,
                null,
                product.getSellerId(),
                product.getSellerName(),
                product.getCategoria(),
                product.getCondicao(),
                product.getCidade(),
                product.getEstado()
        );

        return ResponseEntity.ok(dto);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam String targetCurrency,
            @PageableDefault(
                    page = 0,
                    size = 5,
                    sort = "description",
                    direction = Sort.Direction.ASC
            ) Pageable pageable) throws Exception {

        Page<ProductEntity> products = repository.findAll(pageable);

        Page<ProductDTO> productDTOs = products.map(product -> {

            String environment = "Product-service running on port: " + port;
            Double convertedPrice = null;

            if (targetCurrency.equals(product.getCurrency())) {

                convertedPrice = product.getPrice();

            } else {
                String nameCache = "ConvertedValue";
                String keyCache = product.getCurrency() + "-" + targetCurrency;
                Double cachedRate = cacheManager.getCache(nameCache) != null
                        ? (Double) cacheManager.getCache(nameCache).get(keyCache, Double.class)
                        : null;
                if (cachedRate != null) {
                    convertedPrice = cachedRate * product.getPrice();
                    environment = environment + " - Currency in cache";
                } else {
                    CurrencyResponse currency = currencyClient.getCurrency(product.getCurrency(), targetCurrency);
                    if (currency != null) {
                        convertedPrice = currency.conversionRate() * product.getPrice();
                        environment = environment + " - " + currency.environment();
                        cacheManager.getCache(nameCache).put(keyCache, currency.conversionRate());
                    } else {
                        convertedPrice = -1.0;
                        environment = environment + " - Currency Fallback";
                    }
                }
            }

            return new ProductDTO(
                    product.getId(),
                    product.getDescription(),
                    product.getBrand(),
                    product.getModel(),
                    product.getCurrency(),
                    product.getPrice(),
                    product.getStock(),
                    product.getImageURL(),
                    convertedPrice,
                    environment,
                    targetCurrency,
                    product.getSellerId(),
                    product.getSellerName(),
                    product.getCategoria(),
                    product.getCondicao(),
                    product.getCidade(),
                    product.getEstado()
            );
        });

        return ResponseEntity.ok(productDTOs);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage().replace("/r/n", ""));
    }
}
