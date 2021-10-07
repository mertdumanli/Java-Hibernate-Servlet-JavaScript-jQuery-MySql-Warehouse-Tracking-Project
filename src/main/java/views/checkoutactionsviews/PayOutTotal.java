package views.checkoutactionsviews;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "payouttotal")
public class PayOutTotal {
    @Id
    private int payouttotal;
}
