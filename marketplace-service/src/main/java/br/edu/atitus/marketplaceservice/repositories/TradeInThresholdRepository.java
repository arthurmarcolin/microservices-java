package br.edu.atitus.marketplaceservice.repositories;

import br.edu.atitus.marketplaceservice.entities.TradeInThresholdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TradeInThresholdRepository extends JpaRepository<TradeInThresholdEntity, UUID> {}
