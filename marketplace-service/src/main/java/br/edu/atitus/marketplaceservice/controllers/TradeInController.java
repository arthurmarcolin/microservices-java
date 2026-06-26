package br.edu.atitus.marketplaceservice.controllers;

import br.edu.atitus.marketplaceservice.dtos.TradeInTabelasDTO;
import br.edu.atitus.marketplaceservice.dtos.TradeInSolicitacaoRequest;
import br.edu.atitus.marketplaceservice.entities.*;
import br.edu.atitus.marketplaceservice.repositories.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tradein")
@CrossOrigin(origins = "*")
public class TradeInController {

    private final TradeInBaseCategoriaRepository baseCatRepo;
    private final TradeInFatorIdadeRepository fatorIdadeRepo;
    private final TradeInFatorEstadoRepository fatorEstadoRepo;
    private final TradeInThresholdRepository thresholdRepo;
    private final TradeInSolicitacaoRepository solicitacaoRepo;

    public TradeInController(
        TradeInBaseCategoriaRepository baseCatRepo,
        TradeInFatorIdadeRepository fatorIdadeRepo,
        TradeInFatorEstadoRepository fatorEstadoRepo,
        TradeInThresholdRepository thresholdRepo,
        TradeInSolicitacaoRepository solicitacaoRepo
    ) {
        this.baseCatRepo    = baseCatRepo;
        this.fatorIdadeRepo = fatorIdadeRepo;
        this.fatorEstadoRepo = fatorEstadoRepo;
        this.thresholdRepo  = thresholdRepo;
        this.solicitacaoRepo = solicitacaoRepo;
    }

    // GET /tradein/tabelas — retorna todas as tabelas de depreciação de uma vez
    @GetMapping("/tabelas")
    public ResponseEntity<TradeInTabelasDTO> getTabelasDepreciacao() {
        Map<String, BigDecimal> base = baseCatRepo.findByAtivoTrue().stream()
            .collect(Collectors.toMap(
                TradeInBaseCategoriaEntity::getCategoria,
                TradeInBaseCategoriaEntity::getValorBase
            ));

        Map<String, BigDecimal> fatIdade = fatorIdadeRepo.findAll().stream()
            .collect(Collectors.toMap(
                TradeInFatorIdadeEntity::getFaixa,
                TradeInFatorIdadeEntity::getFator
            ));

        Map<String, BigDecimal> fatEstado = fatorEstadoRepo.findAll().stream()
            .collect(Collectors.toMap(
                TradeInFatorEstadoEntity::getEstado,
                TradeInFatorEstadoEntity::getFator
            ));

        var threshold = thresholdRepo.findAll().stream().findFirst().orElse(null);
        TradeInTabelasDTO.ThresholdDTO threshDTO = threshold == null ? null :
            new TradeInTabelasDTO.ThresholdDTO(
                threshold.getAbatimentoMinimo(),
                threshold.getAbatimentoMaximo(),
                threshold.getIdadeMaximaAnos()
            );

        return ResponseEntity.ok(new TradeInTabelasDTO(base, fatIdade, fatEstado, threshDTO));
    }

    // POST /tradein/solicitacoes — cria uma solicitação de trade-in
    @PostMapping("/solicitacoes")
    public ResponseEntity<TradeInSolicitacaoEntity> criarSolicitacao(
            @RequestBody TradeInSolicitacaoRequest req) {

        var entity = new TradeInSolicitacaoEntity();
        entity.setPedidoId(req.pedidoId());
        entity.setCompradorUserId(req.compradorUserId());
        entity.setCategoria(req.categoria());
        entity.setMarca(req.marca());
        entity.setModelo(req.modelo());
        entity.setIdadeFaixa(req.idadeFaixa());
        entity.setEstadoDeclarado(req.estadoDeclarado());
        entity.setFotosDeclaradas(req.fotosDeclaradas());
        entity.setValorEstimado(req.valorEstimado());
        entity.setStatus("estimado");

        var saved = solicitacaoRepo.save(entity);
        return ResponseEntity.ok(saved);
    }

    // GET /tradein/solicitacoes?compradorUserId=
    @GetMapping("/solicitacoes")
    public ResponseEntity<List<TradeInSolicitacaoEntity>> listarPorComprador(
            @RequestParam String compradorUserId) {
        return ResponseEntity.ok(
            solicitacaoRepo.findByCompradorUserIdOrderByCreatedAtDesc(compradorUserId)
        );
    }

    // GET /tradein/solicitacoes/{id}
    @GetMapping("/solicitacoes/{id}")
    public ResponseEntity<TradeInSolicitacaoEntity> buscarPorId(@PathVariable UUID id) {
        return solicitacaoRepo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /tradein/solicitacoes/{id}/confirmar — avaliação física confirmou
    @PatchMapping("/solicitacoes/{id}/confirmar")
    public ResponseEntity<TradeInSolicitacaoEntity> confirmar(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> body) {

        return solicitacaoRepo.findById(id).map(s -> {
            s.setValorConfirmado(new BigDecimal(body.get("valorConfirmado").toString()));
            s.setAvaliadorUserId((String) body.get("avaliadorUserId"));
            s.setAvaliacaoLaudo((String) body.get("laudo"));
            s.setAvaliacaoData(LocalDateTime.now());
            s.setStatus("confirmado");
            s.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(solicitacaoRepo.save(s));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH /tradein/solicitacoes/{id}/divergencia — avaliação divergiu do declarado
    @PatchMapping("/solicitacoes/{id}/divergencia")
    public ResponseEntity<TradeInSolicitacaoEntity> registrarDivergencia(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> body) {

        return solicitacaoRepo.findById(id).map(s -> {
            s.setValorConfirmado(new BigDecimal(body.get("novoValor").toString()));
            s.setStatus("divergente");
            s.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(solicitacaoRepo.save(s));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH /tradein/solicitacoes/{id}/aceitar — comprador aceitou o novo valor
    @PatchMapping("/solicitacoes/{id}/aceitar")
    public ResponseEntity<TradeInSolicitacaoEntity> compradoAceitou(
            @PathVariable UUID id,
            @RequestBody Map<String, Object> body) {

        return solicitacaoRepo.findById(id).map(s -> {
            if (body.containsKey("diferenca"))
                s.setDiferencaCobrada(new BigDecimal(body.get("diferenca").toString()));
            if (body.containsKey("pixTxid"))
                s.setPixTxid((String) body.get("pixTxid"));
            s.setStatus("aceito_pelo_comprador");
            s.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(solicitacaoRepo.save(s));
        }).orElse(ResponseEntity.notFound().build());
    }

    // PATCH /tradein/solicitacoes/{id}/recusar — comprador recusou, ficou com o usado
    @PatchMapping("/solicitacoes/{id}/recusar")
    public ResponseEntity<TradeInSolicitacaoEntity> compradoRecusou(@PathVariable UUID id) {
        return solicitacaoRepo.findById(id).map(s -> {
            s.setStatus("recusado_pelo_comprador");
            s.setUpdatedAt(LocalDateTime.now());
            return ResponseEntity.ok(solicitacaoRepo.save(s));
        }).orElse(ResponseEntity.notFound().build());
    }
}
