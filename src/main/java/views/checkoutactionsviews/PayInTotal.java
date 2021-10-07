package views.checkoutactionsviews;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "payintotal")
public class PayInTotal {
    @Id
    private int payintotal;
}
