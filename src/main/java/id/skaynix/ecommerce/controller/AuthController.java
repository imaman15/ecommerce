package id.skaynix.ecommerce.controller;

import id.skaynix.ecommerce.entity.Users;
import id.skaynix.ecommerce.model.JwtResponse;
import id.skaynix.ecommerce.model.LoginRequest;
import id.skaynix.ecommerce.model.SignupRequest;
import id.skaynix.ecommerce.security.jwt.JwtUtils;
import id.skaynix.ecommerce.security.service.UserDetailsImplement;
import id.skaynix.ecommerce.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersService usersService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailsImplement principal = (UserDetailsImplement) authentication.getPrincipal();
        return ResponseEntity.ok().body(new JwtResponse(token, principal.getUsername(), principal.getEmail()));
    }

    @PostMapping("/signup")
    public Users signup(@RequestBody SignupRequest request){
        Users users = new Users();
        users.setId(request.getUsername());
        users.setEmail(request.getEmail());
        users.setPassword(request.getPassword());
        users.setName(request.getName());
        users.setRoles("user");
        Users newUsers = usersService.create(users);
        return newUsers;
    }
}
