package id.skaynix.ecommerce.controller;

import id.skaynix.ecommerce.entity.Users;
import id.skaynix.ecommerce.model.JwtResponse;
import id.skaynix.ecommerce.model.LoginRequest;
import id.skaynix.ecommerce.model.RefreshTokenRequest;
import id.skaynix.ecommerce.model.SignupRequest;
import id.skaynix.ecommerce.security.jwt.JwtUtils;
import id.skaynix.ecommerce.security.service.UserDetailsImplement;
import id.skaynix.ecommerce.security.service.UserDetailsServiceImplement;
import id.skaynix.ecommerce.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private UserDetailsServiceImplement userDetailsServiceImplement;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshJwtToken(authentication);
        UserDetailsImplement principal = (UserDetailsImplement) authentication.getPrincipal();
        return ResponseEntity.ok().body(new JwtResponse(token, refreshToken, principal.getUsername(), principal.getEmail()));
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

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody RefreshTokenRequest request){
        String token = request.getRefreshToken();
        boolean valid = jwtUtils.validateJwtToken(token);
        if (!valid) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String username = jwtUtils.getUserNameFromJwtToken(token);
        UserDetailsImplement userDetailsImplement = (UserDetailsImplement) userDetailsServiceImplement.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetailsImplement, null, userDetailsImplement.getAuthorities());
        String newToken = jwtUtils.generateJwtToken(authentication);
        String refreshToken = jwtUtils.generateRefreshJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(newToken,refreshToken,username,userDetailsImplement.getEmail()));
    }
}
