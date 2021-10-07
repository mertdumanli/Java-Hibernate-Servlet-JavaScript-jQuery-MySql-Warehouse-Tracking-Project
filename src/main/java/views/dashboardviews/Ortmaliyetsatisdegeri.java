package views.dashboardviews;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ortmaliyetsatisdegeri")
public class Ortmaliyetsatisdegeri {
    @Id
    private String totalMaliyet;
    private String satisDegeri;
}
