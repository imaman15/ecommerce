package id.skaynix.ecommerce.controller;

import id.skaynix.ecommerce.entity.Category;
import id.skaynix.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category")
    public List<Category> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("/category/{id}")
    public Category findById(@PathVariable("id") String id) {
        return categoryService.findById(id);
    }

    @PostMapping("/category")
    public Category create(@RequestBody Category category){
        return  categoryService.create(category);
    }

    @PutMapping("/category")
    public Category update(@RequestBody Category category){
        return  categoryService.update(category);
    }

    @DeleteMapping("/category/{id}")
    public void deleteById(@PathVariable("id") String id) {
        categoryService.deleteById(id);
    };

}
