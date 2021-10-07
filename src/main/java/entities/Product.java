package entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pr_id;

    private String pr_title;
    private int pr_purchasePrice;
    private int pr_salePrice;

    @Column(unique = true)
    private int pr_code;

    private int pr_vat;//vat(english) => kdv(turkish)
    private int pr_unit;//unit : birim

    private int pr_amount;//miktar
    private String pr_detail;

    private boolean pr_isAvailable;////1 = ürün mevcut, 0 = ürün gizle
}
