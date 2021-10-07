package properties;

import lombok.Data;

import java.util.Date;

@Data
public class InterlayerToCheckOutActions {
    private int cu_id;
    private int type;
    private String startDate;
    private String endDate;
}