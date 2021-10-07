package views.dashboardviews;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "customercount")
public class CustomerCountView {
    @Id
    private int count;
}
