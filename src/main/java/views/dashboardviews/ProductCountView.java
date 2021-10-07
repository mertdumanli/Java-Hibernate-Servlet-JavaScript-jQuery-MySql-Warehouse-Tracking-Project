package views.dashboardviews;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "productcount")
public class ProductCountView {
    @Id
    private int count;
}