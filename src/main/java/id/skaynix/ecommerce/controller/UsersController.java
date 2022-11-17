package id.skaynix.ecommerce.controller;

import id.skaynix.ecommerce.entity.Users;
import id.skaynix.ecommerce.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UsersController {
    
    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    public List<Users> findAll(){
        return usersService.findAll();
    }

    @GetMapping("/users/{id}")
    public Users findById(@PathVariable("id") String id) {
        return usersService.findById(id);
    }

    @PostMapping("/users")
    public Users create(@RequestBody Users users){
        return  usersService.create(users);
    }

    @PutMapping("/users")
    public Users update(@RequestBody Users users){
        return  usersService.update(users);
    }

    @DeleteMapping("/users/{id}")
    public void deleteById(@PathVariable("id") String id) {
        usersService.deleteById(id);
    };
}
