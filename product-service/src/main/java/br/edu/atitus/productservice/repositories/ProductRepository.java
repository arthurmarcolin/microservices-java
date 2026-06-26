package br.edu.atitus.productservice.repositories;

import br.edu.atitus.productservice.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE " +
           "LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.brand)       LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.model)       LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.sellerName)  LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(p.categoria)   LIKE LOWER(CONCAT('%', :q, '%'))")
    List<ProductEntity> search(@Param("q") String q);
}
