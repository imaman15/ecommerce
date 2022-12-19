package id.skaynix.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Users implements Serializable {

    @Id
    private String id;
    @JsonIgnore
    private String password;
    private String name;
    @JsonIgnore
    private String address;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String phone;
    @JsonIgnore
    private String roles;
    @JsonIgnore
    private Boolean isActive;

    public Users(String username) {
        this.id = username;
    }
}
