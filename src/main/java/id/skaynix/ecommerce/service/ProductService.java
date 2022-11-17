package id.skaynix.ecommerce.service;

import id.skaynix.ecommerce.entity.Product;
import id.skaynix.ecommerce.exception.BadRequestException;
import id.skaynix.ecommerce.exception.ResourceNotFoundException;
import id.skaynix.ecommerce.repository.CategoryRepository;
import id.skaynix.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Product findById(String id){
        return productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Produk dengan id "+ id + " tidak ditemukan."));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product create(Product product){
        if (!StringUtils.hasText(product.getName())){
            throw new BadRequestException("Nama produk tidak boleh kosong.");
        }

        if (product.getCategory() == null){
            throw new BadRequestException("Kategori tidak boleh kosong.");
        }

        if (!StringUtils.hasText(product.getCategory().getId())){
            throw new BadRequestException("Kategori ID tidak boleh kosong.");
        }

        categoryRepository.findById(product.getCategory().getId())
                        .orElseThrow(()-> new BadRequestException(
                                "Kategori ID " + product.getCategory().getId() + " tidak ditemukan."
                        ));

        product.setId(UUID.randomUUID().toString());
        return productRepository.save(product);
    }

    public Product update(Product product){
        if (!StringUtils.hasText(product.getId())){
            throw new BadRequestException("Product ID harus di isi.");
        }

        if (!StringUtils.hasText(product.getName())){
            throw new BadRequestException("Nama produk tidak boleh kosong.");
        }

        if (product.getCategory() == null){
            throw new BadRequestException("Kategori tidak boleh kosong.");
        }

        if (!StringUtils.hasText(product.getCategory().getId())){
            throw new BadRequestException("Kategori ID tidak boleh kosong.");
        }

        categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(()-> new BadRequestException(
                        "Kategori ID " + product.getCategory().getId() + " tidak ditemukan."
                ));

        return productRepository.save(product);
    }

    public Product changeImage(String id, String image) {
        Product product = findById(id);
        product.setImage(image);
        return productRepository.save(product);
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }

}
