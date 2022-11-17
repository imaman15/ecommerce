package id.skaynix.ecommerce.repository;

import id.skaynix.ecommerce.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {
}
