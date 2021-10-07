package views.dashboardviews;

import lombok.Data;

import java.util.List;

@Data
public class AllViewsDashboard {
    private int cCount;
    //-----------------------
    private List<LeftTable> leftTableList;
    //--------------------------
    private String totalMaliyet;
    private String satisDegeri;
    //-------------------------
    private int totalPayIn;
    //-----------------------
    private int totalPayOut;
    //---------------------
    private int pCount;
    //----------------------
    private List<RightTable> rightTableList;
    //---------------
    private int totalSales;
}
