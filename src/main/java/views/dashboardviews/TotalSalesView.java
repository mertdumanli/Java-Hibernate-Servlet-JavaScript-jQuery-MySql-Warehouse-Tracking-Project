package views.dashboardviews;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "totalsales")
public class TotalSalesView {
    @Id
    private int totalSales;
}
