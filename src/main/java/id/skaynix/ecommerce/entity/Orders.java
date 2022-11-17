package id.skaynix.ecommerce.entity;

import id.skaynix.ecommerce.model.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
public class Orders implements Serializable {

    @Id
    private String id;
    private String number;
    @Temporal(TemporalType.DATE)
    private Date date;
    @JoinColumn
    @ManyToOne
    private Users users;
    private String shippingAddress;
    private BigDecimal amount;
    private BigDecimal shippingCost;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderTime;

}
