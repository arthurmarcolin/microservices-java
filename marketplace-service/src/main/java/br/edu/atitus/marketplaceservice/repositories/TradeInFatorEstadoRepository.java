package br.edu.atitus.marketplaceservice.repositories;

import br.edu.atitus.marketplaceservice.entities.TradeInFatorEstadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TradeInFatorEstadoRepository extends JpaRepository<TradeInFatorEstadoEntity, UUID> {}
