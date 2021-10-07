package entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class InterlayerToPayIn {

    @Id
    private int in_id;

    private long in_balance;
    private long in_total;
    private int voucherNo;
}
//Belli bir kişinin fişlerini ve fiş tutarlarını getirmek için arada kullanıldı.
//Sql den gelen veriyi kullanmak için,
//vo_id primary key eklendi.