package br.edu.atitus.marketplaceservice.repositories;

import br.edu.atitus.marketplaceservice.entities.TradeInSolicitacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TradeInSolicitacaoRepository extends JpaRepository<TradeInSolicitacaoEntity, UUID> {
    List<TradeInSolicitacaoEntity> findByCompradorUserIdOrderByCreatedAtDesc(String compradorUserId);
    List<TradeInSolicitacaoEntity> findByPedidoId(UUID pedidoId);
}
