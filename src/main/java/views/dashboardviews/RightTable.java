package views.dashboardviews;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "righttable")
public class RightTable {
    @Id
    private int voucher;
    private String name;
    private int tutar;
}
