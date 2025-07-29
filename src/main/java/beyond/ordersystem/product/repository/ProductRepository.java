package beyond.ordersystem.product.repository;

import beyond.ordersystem.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // post list 조회 시에 Paging 및 검색 처리

}
