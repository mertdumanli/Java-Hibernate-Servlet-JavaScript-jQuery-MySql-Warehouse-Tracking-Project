package entities;

import lombok.Data;

import javax.persistence.*;

//Satışı tamamlanmış fişler
//Fişleri paketlediğim yer.

@Entity
@Data
public class Vouchers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vo_id;

    private int alisFiyati;//Ürünün alındığı zamanki alış fiyatı
    private int satisFiyati;//Ürünün alındığı zamanki satış fiyatı
    private int kdv;

    @OneToOne
    @JoinColumn(name = "po_id")
    PurchaseOrders purchaseOrders;

}
