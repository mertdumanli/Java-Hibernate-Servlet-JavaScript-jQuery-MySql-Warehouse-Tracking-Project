package entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class PaymentsOut {
    //po purchaseOrders tablosunda kullanıldı.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int op_id;

    @OneToOne
    @JoinColumn(name = "ad_id")
    Admin admin;

    @Column(length = 50)
    private String op_title;
    private int op_type;
    private long op_total;
    @Column(length = 100)
    private String op_detail;

    private LocalDateTime localDateTime;
}
