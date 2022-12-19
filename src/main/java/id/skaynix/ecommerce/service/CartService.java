package id.skaynix.ecommerce.service;

import id.skaynix.ecommerce.entity.Cart;
import id.skaynix.ecommerce.entity.Product;
import id.skaynix.ecommerce.entity.Users;
import id.skaynix.ecommerce.exception.BadRequestException;
import id.skaynix.ecommerce.repository.CartRepository;
import id.skaynix.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    @Transactional
    public Cart addToCart(String username, String productId, Double quantity) {
        /* If Product exist in Database */
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BadRequestException("Product ID " + productId + " not found."));

        Optional<Cart> optionalCart = cartRepository.findByUsersIdAndProductId(username, productId);
        Cart cart;
        /* If exist in user cart */
        if (optionalCart.isPresent()) {
            /* If existed, update quantity and calculate */
            cart = optionalCart.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            cart.setAmount(new BigDecimal(cart.getPrice().doubleValue() * cart.getQuantity()));
            cartRepository.save(cart);
        } else {
        /* if not exist, create new data*/
            cart = new Cart();
            cart.setId(UUID.randomUUID().toString());
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cart.setPrice(product.getPrice());
            cart.setAmount(new BigDecimal(cart.getPrice().doubleValue() * cart.getQuantity()));
            cart.setUsers(new Users(username));
            cartRepository.save(cart);
        }

        return cart;
    }

    @Transactional
    public Cart updateQuantity(String username, String productId, Double quantity) {
        Cart cart = cartRepository.findByUsersIdAndProductId(username, productId)
                .orElseThrow(() -> new BadRequestException(
                        "Product ID " + productId + " not found in your cart."));
        cart.setQuantity(quantity);
        cart.setAmount(new BigDecimal(cart.getPrice().doubleValue() * cart.getQuantity()));
        cartRepository.save(cart);
        return cart;
    }

    @Transactional
    public void delete(String username, String productId){
        Cart cart = cartRepository.findByUsersIdAndProductId(username, productId)
                .orElseThrow(() -> new BadRequestException(
                        "Product ID " + productId + " not found in your cart."));
        cartRepository.delete(cart);
    }

    public List<Cart> findByUsersId(String username){
        return cartRepository.findByUsersId(username);
    }

}
