package views.dashboardviews;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "lefttable")
public class LeftTable {
    @Id
    private int pr_id;
    private int pr_amount;
    private int pr_code;
    private int pr_purchasePrice;
    private int pr_salePrice;
    private String pr_title;
    private int pr_vat;
}

