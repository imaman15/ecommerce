package id.skaynix.ecommerce.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Cart implements Serializable {

    @Id
    private String id;
    @JoinColumn
    @ManyToOne
    private Product product;
    @JoinColumn
    @ManyToOne
    private Users users;
    private Double quantity;
    private BigDecimal price;
    private BigDecimal amount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

}
