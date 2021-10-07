package views.intersectionsetdashboardandchechoutactions;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "PayInTotalToday")
public class PayInTotalToday {
    @Id
    private int totalPayIn;
}