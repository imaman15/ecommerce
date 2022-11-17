package id.skaynix.ecommerce.repository;

import id.skaynix.ecommerce.entity.OrdersLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersLogRepository extends JpaRepository<OrdersLog,String> {
}
