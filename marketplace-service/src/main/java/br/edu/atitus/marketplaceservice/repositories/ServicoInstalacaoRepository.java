package br.edu.atitus.marketplaceservice.repositories;

import br.edu.atitus.marketplaceservice.entities.ServicoInstalacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ServicoInstalacaoRepository extends JpaRepository<ServicoInstalacaoEntity, UUID> {
    List<ServicoInstalacaoEntity> findByAtivoTrue();
    Optional<ServicoInstalacaoEntity> findByCategoriaIgnoreCaseAndAtivoTrue(String categoria);
}
