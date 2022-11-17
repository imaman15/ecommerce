package id.skaynix.ecommerce.repository;

import id.skaynix.ecommerce.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, String> {
    Boolean existsByEmail(String email);
}
