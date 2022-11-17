package id.skaynix.ecommerce.service;

import id.skaynix.ecommerce.entity.Category;
import id.skaynix.ecommerce.exception.ResourceNotFoundException;
import id.skaynix.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findById(String id){
        return categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Kategori dengan id "+ id + " tidak ditemukan."));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category create(Category category){
        category.setId(UUID.randomUUID().toString());
        return categoryRepository.save(category);
    }

    public Category update(Category category){
        return categoryRepository.save(category);
    }

    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }

}
