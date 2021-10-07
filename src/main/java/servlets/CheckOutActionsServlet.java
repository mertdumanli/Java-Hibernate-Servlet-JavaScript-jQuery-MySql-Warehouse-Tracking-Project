package servlets;

import com.google.gson.Gson;
import entities.PaymentsOut;
import entities.checkoutactions.InterlayerToPaymentHistoryWithCustomerInfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import properties.InterlayerToCheckOutActions;
import utils.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "checkOutActionsServlet", value = {"/check-out-actions-post", "/check-out-actions-get"})
public class CheckOutActionsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String obj = req.getParameter("obj");
        Gson gson = new Gson();
        InterlayerToCheckOutActions i = gson.fromJson(obj, InterlayerToCheckOutActions.class);

        System.out.println(i);

        int cu_id = i.getCu_id();
        int type = i.getType();
        String startDate = i.getStartDate();
        String endDate = i.getEndDate();

        List<InterlayerToPaymentHistoryWithCustomerInfo> ls0 = new ArrayList<>();//GİRİŞ SEÇİLMİŞ.
        List<PaymentsOut> ls1 = new ArrayList<>();//ÇIKIŞ SEÇİLMİŞ

        String stJson = "";

        if (cu_id == 0) {
            if (type == 1) {
                ls0 = getResultAllCustomerSelectPayIN(startDate, endDate);
                stJson = gson.toJson(ls0);
            } else if (type == 2) {
                ls1 = getResultAllPaymentOut(startDate, endDate);
                stJson = gson.toJson(ls1);
            } else {
                ls0 = getResultAllCustomerSelectPayIN(startDate, endDate);
                ls1 = getResultAllPaymentOut(startDate, endDate);
                Object[] lsTotal = {ls0 = ls0, ls1 = ls1};
                stJson = gson.toJson(lsTotal);
            }
        } else if (cu_id != 0) {//Müşteri seçildi
            if (type == 0 || type == 1) {
                ls0 = getResultXCustomerSelectPayIn(cu_id, startDate, endDate);
                stJson = gson.toJson(ls0);
            } else {
                stJson = gson.toJson(ls0);
            }
        }
        ls0.forEach(item -> {
            System.out.println(item);
        });

        resp.setContentType("application/json");
        resp.getWriter().write(stJson);
    }

    private List<InterlayerToPaymentHistoryWithCustomerInfo> getResultAllCustomerSelectPayIN(String startDate, String endDate) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesi = sf.openSession();

        String sql = "SELECT * FROM payments AS pay " +
                "INNER JOIN invoice AS i ON i.in_id = pay.in_id " +
                "INNER JOIN invoice_vouchers AS iv ON iv.Invoice_in_id = i.in_id " +
                "INNER JOIN vouchers AS v ON v.vo_id = iv.vouchersList_vo_id " +
                "INNER JOIN purchaseorders AS p ON v.po_id = p.po_id " +
                "INNER JOIN customer as cu on cu.cu_id = p.cu_id ";

        if (startDate == null && endDate != null) {
            sql += "WHERE `pa_localDateTime` BETWEEN '0000-00-00' AND '" + endDate + "' + INTERVAL 1 DAY";
        } else if (startDate != null && endDate == null) {
            sql += "WHERE `pa_localDateTime` BETWEEN '" + startDate + "' AND '2100-01-01'";
        } else if (startDate != null && endDate != null) {
            sql += "WHERE `pa_localDateTime` BETWEEN '" + startDate + "' AND '" + endDate + "'";
        } else {
            System.out.println("Tarih kısıtlaması yok tüm sonuçlar getirildi.");
        }

        sql += " GROUP BY pay.pa_id ORDER BY pa_localDateTime desc";
        return sesi.createNativeQuery(sql)
                .addEntity(InterlayerToPaymentHistoryWithCustomerInfo.class)
                .getResultList();
    }

    private List<PaymentsOut> getResultAllPaymentOut(String startDate, String endDate) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesi = sf.openSession();
        String sql = "from PaymentsOut ";
        if (startDate == null && endDate != null) {
            sql += "WHERE localDateTime BETWEEN '0000-00-00' AND '" + endDate + "' + INTERVAL 1 DAY";
        } else if (startDate != null && endDate == null) {
            sql += "WHERE localDateTime BETWEEN '" + startDate + "' AND '2100-01-01'";
        } else if (startDate != null && endDate != null) {
            sql += "WHERE localDateTime BETWEEN '" + startDate + "' AND '" + endDate + "'";
        } else {
            System.out.println("Tarih kısıtlaması yok tüm sonuçlar getirildi.");
        }
        sql += " ORDER BY localDateTime desc";
        return sesi.createQuery(sql).getResultList();
    }

    private List<InterlayerToPaymentHistoryWithCustomerInfo> getResultXCustomerSelectPayIn(int cu_id, String startDate, String endDate) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesi = sf.openSession();

        String sql = "SELECT * FROM payments AS pay " +
                "INNER JOIN invoice AS i ON i.in_id = pay.in_id " +
                "INNER JOIN invoice_vouchers AS iv ON iv.Invoice_in_id = i.in_id " +
                "INNER JOIN vouchers AS v ON v.vo_id = iv.vouchersList_vo_id " +
                "INNER JOIN purchaseorders AS p ON v.po_id = p.po_id " +
                "INNER JOIN customer as cu on cu.cu_id = p.cu_id ";

        if (startDate == null && endDate != null) {
            sql += "WHERE pa_localDateTime BETWEEN '0000-00-00' AND '" + endDate + "' + INTERVAL 1 DAY";
        } else if (startDate != null && endDate == null) {
            sql += "WHERE pa_localDateTime BETWEEN '" + startDate + "' AND '2100-01-01'";
        } else if (startDate != null && endDate != null) {
            sql += "WHERE pa_localDateTime BETWEEN '" + startDate + "' AND '" + endDate + "'";
        } else {
            System.out.println("Tarih kısıtlaması yok tüm sonuçlar getirildi.");
        }

        sql += " and cu.cu_id =?1 GROUP BY pay.pa_id ORDER BY pa_localDateTime desc";
        return sesi.createNativeQuery(sql)
                .setParameter(1, cu_id)
                .addEntity(InterlayerToPaymentHistoryWithCustomerInfo.class)
                .getResultList();
    }
}