package entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class AdminFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int adminFollow_id;

    @OneToOne
    Admin admin;

    private LocalDateTime localDateTime;

    @Column(length = 5, nullable = false)
    private String status;
    //giriş
    //çıkış
}
