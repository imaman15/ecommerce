package id.skaynix.ecommerce.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
public class Users implements Serializable {

    @Id
    private String id;
    private String password;
    private String name;
    private String address;
    private String email;
    private String phone;
    private String roles;
    private Boolean isActive;

}
