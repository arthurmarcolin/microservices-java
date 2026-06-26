package br.edu.atitus.marketplaceservice.repositories;

import br.edu.atitus.marketplaceservice.entities.TradeInFatorIdadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TradeInFatorIdadeRepository extends JpaRepository<TradeInFatorIdadeEntity, UUID> {}
