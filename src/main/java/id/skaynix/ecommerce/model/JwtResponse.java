package id.skaynix.ecommerce.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtResponse implements Serializable {
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private String username;
    private String email;

    public JwtResponse(String token,String refreshToken, String username, String email) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.refreshToken = refreshToken;
    }
}
