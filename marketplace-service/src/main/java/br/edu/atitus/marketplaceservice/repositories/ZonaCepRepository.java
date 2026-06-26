package br.edu.atitus.marketplaceservice.repositories;

import br.edu.atitus.marketplaceservice.entities.ZonaCepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.UUID;

public interface ZonaCepRepository extends JpaRepository<ZonaCepEntity, UUID> {

    @Query("""
        SELECT z FROM ZonaCepEntity z
        WHERE z.ativo = true
          AND z.tipo <> 'sem_cobertura'
          AND z.cepInicio <= :cep
          AND z.cepFim    >= :cep
        ORDER BY z.cepInicio DESC
        LIMIT 1
    """)
    Optional<ZonaCepEntity> findByCep(@Param("cep") String cep);
}
