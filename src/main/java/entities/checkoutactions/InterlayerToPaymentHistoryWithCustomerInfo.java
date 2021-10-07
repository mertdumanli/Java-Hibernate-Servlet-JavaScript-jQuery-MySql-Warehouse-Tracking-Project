package entities.checkoutactions;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
public class InterlayerToPaymentHistoryWithCustomerInfo {
    @Id
    private int pa_id;

    private String pa_detail;
    private LocalDateTime pa_localDateTime;
    private long pa_paid;
    private long in_balance;
    private long in_total;
    private int voucherNo;
    private long cu_code;
    private String cu_name;
    private String cu_surname;
}


