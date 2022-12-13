package id.skaynix.ecommerce.controller;

import id.skaynix.ecommerce.entity.Cart;
import id.skaynix.ecommerce.model.CartRequest;
import id.skaynix.ecommerce.security.service.UserDetailsImplement;
import id.skaynix.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/carts")
    public List<Cart> findByUserId(@AuthenticationPrincipal UserDetailsImplement user){
        return cartService.findByUsersId(user.getUsername());
    }

    @PostMapping("/carts")
    public Cart create(@AuthenticationPrincipal UserDetailsImplement user, @RequestBody CartRequest request) {
        return cartService.addToCart(user.getUsername(), request.getProductId(), request.getQuantity());
    }

    @PatchMapping("/carts/{productId}")
    public Cart update(@AuthenticationPrincipal UserDetailsImplement user, @PathVariable("productId") String productId, @RequestParam("quantity") Double quantity){
        return cartService.updateQuantity(user.getUsername(), productId, quantity);
    }

    @DeleteMapping("/carts/{productId}")
    public void delete(@AuthenticationPrincipal UserDetailsImplement user, @PathVariable("productId") String productId){
        cartService.delete(user.getUsername(), productId);
    }

}
