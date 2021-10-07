package entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class InterlayerToPaymentHistory {
    @Id
    private int pa_id;

    private String pa_detail;
    private LocalDateTime pa_localDateTime;
    private long pa_paid;
    private long in_balance;
    private long in_total;
    private int voucherNo;
    private int in_id;


    //Belli bir kişinin geçmiş ödemelerini getirmek için arada kullanıldı.
//Sql den gelen veriyi kullanmak için,
//pa_id primary key eklendi.
    //payIn ekranı Ödeme Giriş Listesi

    //Ayrıca CheckOutActions sayfasında ödeme girişlerini getirirken yine property olarak kullanıldı.
}
