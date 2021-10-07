package entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int in_id;

    @OneToMany(cascade = CascadeType.DETACH)
    List<Vouchers> vouchersList;

    private long in_total;
    private long in_balance;//kalan tutar
    private long in_depoMaliyeti;//Stok giriş ücreti
}

//Kişinin fişlerini bu tabloya bağladım.
//Fişlerde oynama yapılmayacak.
//Kişinin fişlerinin total ücreti burada tutulacak.(Her fiş numarası farklı)


//Ödemeleri Paymont.java da yapılacak.
//Her ödeme işlemi kalem kalem tutulacak.




