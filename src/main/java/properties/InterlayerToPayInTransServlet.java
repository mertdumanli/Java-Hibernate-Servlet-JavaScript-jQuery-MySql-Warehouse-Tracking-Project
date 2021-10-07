package properties;

import lombok.Data;

@Data
public class InterlayerToPayInTransServlet {
    private int selectedVoucher;//in_id var içinde.
    private long paymentTotal;
    private String paymentDetail;
}
