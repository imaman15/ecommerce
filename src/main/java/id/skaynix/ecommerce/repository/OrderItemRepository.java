package id.skaynix.ecommerce.repository;

import id.skaynix.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,String> {
}
