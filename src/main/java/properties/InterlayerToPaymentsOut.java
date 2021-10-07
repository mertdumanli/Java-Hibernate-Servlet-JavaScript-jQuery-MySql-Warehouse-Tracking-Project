package properties;

import lombok.Data;

@Data
public class InterlayerToPaymentsOut {
    private String payOutTitle;
    private int payOutType;
    private long payOutTotal;
    private String payOutDetail;
}
