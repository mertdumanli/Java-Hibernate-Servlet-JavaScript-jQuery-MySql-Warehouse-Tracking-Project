package entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pa_id;

    @OneToOne
    @JoinColumn(name = "in_id")
    Invoice invoice;

    private long in_balance;//kalan tutar

    private long pa_paid;//ödenen miktar
    @Column(length = 200)
    private String pa_detail;
    private LocalDateTime pa_localDateTime;
}
