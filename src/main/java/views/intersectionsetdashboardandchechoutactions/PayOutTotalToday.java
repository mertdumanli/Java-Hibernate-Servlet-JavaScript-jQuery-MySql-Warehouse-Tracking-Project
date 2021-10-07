package views.intersectionsetdashboardandchechoutactions;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "PayOutTotalToday")
public class PayOutTotalToday {
    @Id
    private int totalPayOut;
}
