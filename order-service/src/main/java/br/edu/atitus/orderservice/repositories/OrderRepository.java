package br.edu.atitus.orderservice.repositories;

import br.edu.atitus.orderservice.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCompradorUserIdOrderByCriadoEmDesc(Long compradorUserId);
}
