package br.edu.atitus.marketplaceservice.repositories;

import br.edu.atitus.marketplaceservice.entities.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PedidoRepository extends JpaRepository<PedidoEntity, UUID> {
    List<PedidoEntity> findByCompradorUserIdOrderByCreatedAtDesc(String compradorUserId);
}
