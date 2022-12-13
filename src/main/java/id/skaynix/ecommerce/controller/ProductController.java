package id.skaynix.ecommerce.controller;

import id.skaynix.ecommerce.entity.Product;
import id.skaynix.ecommerce.model.Test;
import id.skaynix.ecommerce.response.ResponseHandler;
import id.skaynix.ecommerce.service.ProductService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Value("${signature}")
    private String keySignature;

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

    @GetMapping("/hash")
    public ResponseEntity<Object> Hash() throws NoSuchAlgorithmException {
        Map<String, Object> object = new HashMap<String, Object>();

        object.put("page", 2);
        object.put("per_page", 6);
        object.put("total_pages", 2);
        object.put("total", 12);

        return ResponseHandler.generateResponse("Success get Data", HttpStatus.OK, object);
    }

}
