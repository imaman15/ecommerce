package id.skaynix.ecommerce.entity;

import lombok.Data;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.criterion.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
public class OrdersLog implements Serializable {

    @Id
    private String id;
    @JoinColumn
    @ManyToOne
    private Orders orders;
    @JoinColumn
    @ManyToOne
    private Users users;
    private Integer logType;
    private String logMessage;
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

}
