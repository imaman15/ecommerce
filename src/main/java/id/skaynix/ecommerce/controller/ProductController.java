package id.skaynix.ecommerce.controller;

import id.skaynix.ecommerce.entity.Category;
import id.skaynix.ecommerce.entity.Product;
import id.skaynix.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product")
    public List<Product> findAll(){
        return productService.findAll();
    }

    @GetMapping("/product/{id}")
    public Product findById(@PathVariable("id") String id) {
        return productService.findById(id);
    }

    @PostMapping("/product")
    public Product create(@RequestBody Product product){
        return  productService.create(product);
    }

    @PutMapping("/product")
    public Product update(@RequestBody Product product){
        return  productService.update(product);
    }

    @DeleteMapping("/product/{id}")
    public void deleteById(@PathVariable("id") String id) {
        productService.deleteById(id);
    };

}
