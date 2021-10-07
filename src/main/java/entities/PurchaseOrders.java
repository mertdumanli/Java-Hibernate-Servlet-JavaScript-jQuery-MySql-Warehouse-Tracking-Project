package entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PurchaseOrders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int po_id;

    @OneToOne
    @JoinColumn(name = "cu_id")
    Customer customer;

    @OneToOne
    @JoinColumn(name = "pr_id")
    Product product;

    private int number;//adet
    private int voucherNo;//fiş no
    private boolean status;//sepet = 0, satış tamamlandı = 1

}