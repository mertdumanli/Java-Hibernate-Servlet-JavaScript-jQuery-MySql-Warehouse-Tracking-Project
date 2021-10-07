package entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ad_id;

    @Column(length = 200, unique = true)
    private String ad_email;
    @Column(length = 32)
    private String ad_password;

    private String ad_name;
    private String ad_surname;
}
