package utils;

import views.checkoutactionsviews.AllViewsCheckOutActions;
import views.checkoutactionsviews.PayInTotal;
import views.checkoutactionsviews.PayOutTotal;
import views.dashboardviews.*;
import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import entities.InterlayerToPayIn;
import entities.InterlayerToPaymentHistory;
import views.intersectionsetdashboardandchechoutactions.PayInTotalToday;
import views.intersectionsetdashboardandchechoutactions.PayOutTotalToday;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Util {
    public static final String base_url = "http://localhost:8082/depo_project_war_exploded/";

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public void isLogin(HttpServletRequest request, HttpServletResponse response) {

        if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    String vales = cookie.getValue();
                    try {
                        String[] arr = vales.split("___");
                        request.getSession().setAttribute("admin_id", arr[0]);
                        request.getSession().setAttribute("admin_name", arr[1]);
                        request.getSession().setAttribute("admin_surname", arr[2]);
                        request.getSession().setAttribute("admin_email", arr[3]);
                        request.getSession().setAttribute("admin_password", arr[4]);
                    } catch (NumberFormatException e) {
                        Cookie cookie1 = new Cookie("user", "");
                        cookie1.setMaxAge(0);
                        response.addCookie(cookie1);
                    }
                    break;
                }
            }
        }

        Object sessionObj = request.getSession().getAttribute("admin_email");
        if (sessionObj == null) {
            try {
                response.sendRedirect(base_url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    SessionFactory sf = HibernateUtil.getSessionFactory();

    public List<Customer> getCustomerList() {
        Session sesi = sf.openSession();
        List<Customer> ls = sesi.createQuery("from Customer").getResultList();
        sesi.close();
        return ls;
    }

    public List<Product> getProductList() {
        Session sesi = sf.openSession();
        List<Product> ls = sesi.createQuery("from Product").getResultList();
        sesi.close();
        return ls;
    }

    public Product getXProduct(int pr_id) {
        Session sesi = sf.openSession();
        return (Product) sesi.createQuery("from Product where pr_id=?1")
                .setParameter(1, pr_id)
                .getSingleResult();
    }

    //Direkt * verdim yeterli oldu zaten nesne ile sütun isimleri aynı olanları nesneye aktarıyor.

    public List<InterlayerToPayIn> getXCustomerVoucherInformations(int cu_id) {
        //Tüm kişiye ait fiş bilgilerini getirme.
        Session sesi = sf.openSession();
        return sesi.createNativeQuery("SELECT * FROM invoice AS i\n" +
                        "INNER JOIN invoice_vouchers as iv ON i.in_id = iv.Invoice_in_id\n" +
                        "INNER JOIN vouchers AS v ON v.vo_id = iv.vouchersList_vo_id\n" +
                        "INNER JOIN purchaseorders AS p ON v.po_id = p.po_id WHERE cu_id = ?1 GROUP BY in_id")
                .setParameter(1, cu_id)
                .addEntity(InterlayerToPayIn.class)
                .getResultList();
    }

    public InterlayerToPayIn getXCustomerVoucherInformations(int cu_id, int in_id) {
        //Seçilen fişin bilgilerini getirme.
        Session sesi = sf.openSession();
        return (InterlayerToPayIn) sesi.createNativeQuery("SELECT * FROM invoice AS i\n" +
                        "INNER JOIN invoice_vouchers as iv ON i.in_id = iv.Invoice_in_id\n" +
                        "INNER JOIN vouchers AS v ON v.vo_id = iv.vouchersList_vo_id\n" +
                        "INNER JOIN purchaseorders AS p ON v.po_id = p.po_id WHERE cu_id = ?1 and in_id = ?2 GROUP BY in_id")
                .setParameter(1, cu_id)
                .setParameter(2, in_id)
                .addEntity(InterlayerToPayIn.class)
                .getSingleResult();
    }

    public List<InterlayerToPaymentHistory> getXCustomerPaymentHistory(int cu_id) {
        //Seçilen müşterinin geçmiş ödemeleri.
        Session sesi = sf.openSession();
        return sesi.createNativeQuery("SELECT * FROM payments as pay\n" +
                        "INNER JOIN invoice AS i ON i.in_id = pay.in_id\n" +
                        "INNER JOIN invoice_vouchers as iv ON iv.Invoice_in_id = i.in_id\n" +
                        "INNER JOIN vouchers AS v ON v.vo_id = iv.vouchersList_vo_id\n" +
                        "INNER JOIN purchaseorders AS p ON v.po_id = p.po_id WHERE p.cu_id = ?1 GROUP BY pay.pa_id")
                .setParameter(1, cu_id)
                .addEntity(InterlayerToPaymentHistory.class)
                .getResultList();
    }

    public Admin getXAdmin(HttpServletRequest req) {

        Admin admin = new Admin();
        try {
            String stradmin_id = String.valueOf(req.getSession().getAttribute("admin_id"));
            admin.setAd_id(Integer.parseInt(stradmin_id));

            admin.setAd_name((String) req.getSession().getAttribute("admin_name"));

            admin.setAd_surname((String) req.getSession().getAttribute("admin_surname"));

            admin.setAd_email((String) req.getSession().getAttribute("admin_email"));

            admin.setAd_password((String) req.getSession().getAttribute("admin_password"));

            return admin;
        } catch (Exception e) {
            System.err.println("getXAdmin method Error" + e);
        }
        return null;
    }

    public AllViewsDashboard getViewsDashboard(HttpServletRequest req, HttpServletResponse resp) {

        //customercount view
        Session sesi0 = sf.openSession();
        CustomerCountView customerCountViews = (CustomerCountView) sesi0.createQuery("from CustomerCountView").getSingleResult();
        sesi0.close();

        //leftTable view
        Session sesi1 = sf.openSession();
        List<LeftTable> leftTableList = sesi1.createQuery("from LeftTable").getResultList();
        sesi1.close();

        //Ortmmaliyetsatisdegeri
        Session sesi2 = sf.openSession();
        Ortmaliyetsatisdegeri ortmaliyetsatisdegeri = (Ortmaliyetsatisdegeri) sesi2.createQuery("from  Ortmaliyetsatisdegeri").getSingleResult();
        sesi2.close();

        //PayInTotalToday
        Session sesi3 = sf.openSession();
        PayInTotalToday payInTotalToday = (PayInTotalToday) sesi3.createQuery("from PayInTotalToday ").getSingleResult();
        sesi3.close();

        //PayOutTotalToday
        Session sesi4 = sf.openSession();
        PayOutTotalToday payOutTotalToday = (PayOutTotalToday) sesi4.createQuery("from PayOutTotalToday").getSingleResult();
        sesi4.close();

        //productcount view
        Session sesi5 = sf.openSession();
        ProductCountView productCountView = (ProductCountView) sesi5.createQuery("from ProductCountView ").getSingleResult();
        sesi5.close();

        //RightTable
        Session sesi6 = sf.openSession();
        List<RightTable> rightTableList = sesi6.createQuery("from RightTable").getResultList();
        System.out.println(rightTableList);

        //totalsales view
        Session sesi7 = sf.openSession();
        TotalSalesView totalSalesView = (TotalSalesView) sesi7.createQuery("from TotalSalesView ").getSingleResult();
        sesi7.close();

        //View Combining
        AllViewsDashboard allViews = new AllViewsDashboard();
        allViews.setCCount(customerCountViews.getCount());
        allViews.setLeftTableList(leftTableList);
        allViews.setTotalMaliyet(ortmaliyetsatisdegeri.getTotalMaliyet());
        allViews.setSatisDegeri(ortmaliyetsatisdegeri.getSatisDegeri());
        try {
            allViews.setTotalPayIn(payInTotalToday.getTotalPayIn());
        } catch (Exception e) {
            allViews.setTotalPayIn(0);
        }
        try {
            allViews.setTotalPayOut(payOutTotalToday.getTotalPayOut());
        } catch (Exception e) {
            allViews.setTotalPayOut(0);
        }

        allViews.setPCount(productCountView.getCount());
        allViews.setRightTableList(rightTableList);
        System.out.println("Right Table Size : " + rightTableList.size());
        allViews.setTotalSales(totalSalesView.getTotalSales());

        return allViews;
    }

    public AllViewsCheckOutActions getViewsCheckOutActions(HttpServletRequest req, HttpServletResponse resp) {
        Session sesi0 = sf.openSession();
        PayInTotalToday payInTotalToday = (PayInTotalToday) sesi0.createQuery("from PayInTotalToday ").getSingleResult();
        sesi0.close();

        Session sesi1 = sf.openSession();
        PayOutTotalToday payOutTotalToday = (PayOutTotalToday) sesi1.createQuery("from PayOutTotalToday ").getSingleResult();
        sesi1.close();

        Session sesi2 = sf.openSession();
        PayInTotal payInTotal = (PayInTotal) sesi2.createQuery("from PayInTotal ").getSingleResult();
        sesi2.close();

        Session sesi3 = sf.openSession();
        PayOutTotal payOutTotal = (PayOutTotal) sesi3.createQuery("from PayOutTotal ").getSingleResult();
        sesi3.close();

        AllViewsCheckOutActions allViews = new AllViewsCheckOutActions();
        try {
            allViews.setPayInTotalToday(payInTotalToday.getTotalPayIn());
        } catch (Exception e) {
            allViews.setPayInTotalToday(0);
        }
        try {
            allViews.setPayOutTotalToday(payOutTotalToday.getTotalPayOut());
        } catch (Exception e) {
            allViews.setPayOutTotalToday(0);
        }
        try {
            allViews.setPayInTotal(payInTotal.getPayintotal());
        } catch (Exception e) {
            allViews.setPayInTotal(0);
        }
        try {
            allViews.setPayOutTotal(payOutTotal.getPayouttotal());
        } catch (Exception e) {
            allViews.setPayOutTotal(0);
        }
        return allViews;
    }

}