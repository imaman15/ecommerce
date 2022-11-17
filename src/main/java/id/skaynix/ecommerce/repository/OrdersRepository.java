package id.skaynix.ecommerce.repository;

import id.skaynix.ecommerce.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, String> {
}
