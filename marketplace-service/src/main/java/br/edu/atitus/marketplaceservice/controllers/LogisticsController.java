package br.edu.atitus.marketplaceservice.controllers;

import br.edu.atitus.marketplaceservice.entities.ServicoInstalacaoEntity;
import br.edu.atitus.marketplaceservice.entities.ZonaCepEntity;
import br.edu.atitus.marketplaceservice.repositories.ServicoInstalacaoRepository;
import br.edu.atitus.marketplaceservice.repositories.ZonaCepRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("logistics")
@CrossOrigin(origins = "*")
public class LogisticsController {

    private final ZonaCepRepository zonaCepRepo;
    private final ServicoInstalacaoRepository servicoRepo;

    public LogisticsController(ZonaCepRepository zonaCepRepo, ServicoInstalacaoRepository servicoRepo) {
        this.zonaCepRepo = zonaCepRepo;
        this.servicoRepo = servicoRepo;
    }

    // GET /logistics/cobertura/{cep} — verifica se o CEP tem cobertura e retorna a zona
    @GetMapping("/cobertura/{cep}")
    public ResponseEntity<ZonaCepEntity> verificarCobertura(@PathVariable String cep) {
        String cepNum = cep.replaceAll("\\D", "");
        if (cepNum.length() != 8) return ResponseEntity.badRequest().build();
        return zonaCepRepo.findByCep(cepNum)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    // GET /logistics/instalacao — lista todos os serviços de instalação ativos
    @GetMapping("/instalacao")
    public ResponseEntity<List<ServicoInstalacaoEntity>> listarServicos() {
        return ResponseEntity.ok(servicoRepo.findByAtivoTrue());
    }

    // GET /logistics/instalacao/{categoria} — busca serviço pela categoria do produto
    @GetMapping("/instalacao/{categoria}")
    public ResponseEntity<ServicoInstalacaoEntity> buscarPorCategoria(@PathVariable String categoria) {
        return servicoRepo.findByCategoriaIgnoreCaseAndAtivoTrue(categoria)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
