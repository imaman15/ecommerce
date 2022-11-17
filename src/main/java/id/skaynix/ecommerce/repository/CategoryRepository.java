package id.skaynix.ecommerce.repository;

import id.skaynix.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}
