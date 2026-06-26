package br.edu.atitus.marketplaceservice.repositories;

import br.edu.atitus.marketplaceservice.entities.TradeInBaseCategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface TradeInBaseCategoriaRepository extends JpaRepository<TradeInBaseCategoriaEntity, UUID> {
    List<TradeInBaseCategoriaEntity> findByAtivoTrue();
}
