package servlets;

import com.google.gson.Gson;
import entities.Customer;
import entities.Invoice;
import entities.Payments;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import properties.InterlayerToPayInTransServlet;
import entities.InterlayerToPaymentHistory;
import utils.HibernateUtil;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet(name = "payInServlet", value = {"/pay-in-post", "/pay-in-get", "/pay-in-delete"})
public class PayInServlet extends HttpServlet {
    SessionFactory sf = HibernateUtil.getSessionFactory();

    //Ödeme alınan yer.

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int feedBack = 0;
        String obj = req.getParameter("obj");
        Gson gson = new Gson();
        InterlayerToPayInTransServlet i = gson.fromJson(obj, InterlayerToPayInTransServlet.class);
        System.out.println(i);

        Session sesi0 = sf.openSession();
        Invoice invoice = new Invoice();
        try {
            invoice = (Invoice) sesi0.createQuery("from Invoice where in_id =?1")
                    .setParameter(1, i.getSelectedVoucher())
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("payInServlet Error get invoice " + e);
        } finally {
            sesi0.close();
        }
        if (invoice != null) {
            Payments payments = new Payments();
            payments.setInvoice(invoice);
            payments.setIn_balance(invoice.getIn_balance() - i.getPaymentTotal());
            payments.setPa_paid(i.getPaymentTotal());
            payments.setPa_detail(i.getPaymentDetail());
            payments.setPa_localDateTime(LocalDateTime.now());

            Session sesi1 = sf.openSession();
            Transaction tr1 = sesi1.beginTransaction();

            try {
                sesi1.saveOrUpdate(payments);
                feedBack = 1;
                tr1.commit();
            } catch (Exception e) {
                System.err.println("payInServlet Error doPost 2.try :" + e);
            } finally {
                sesi1.close();
            }
            if (feedBack > 0) {
                feedBack = 0;
                Session sesi2 = sf.openSession();
                Transaction tr2 = sesi2.beginTransaction();
                try {
                    invoice.setIn_balance(invoice.getIn_balance() - i.getPaymentTotal());
                    sesi2.update(invoice);
                    tr2.commit();
                    feedBack = 1;
                } catch (Exception e) {
                    System.err.println("payInServlet Error doPost 3.try :" + e);
                } finally {
                    sesi2.close();
                }
            }
        } else {
            System.err.println("PayInServlet casting error : ");
            resp.sendRedirect(Util.base_url);
        }
        resp.setContentType("application/json");
        resp.getWriter().write("" + feedBack);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int cu_id = Integer.parseInt(req.getParameter("cu_id"));
            System.out.println("customer id " + cu_id);

            Util util = new Util();
            List<InterlayerToPaymentHistory> ls = util.getXCustomerPaymentHistory(cu_id);

            ls.forEach(item -> {
                System.out.println(item);
            });

            Gson gson = new Gson();
            String stJson = gson.toJson(ls);
            resp.setContentType("application/json");
            resp.getWriter().write(stJson);
        } catch (Exception e) {
            System.err.println("PayInServlet casting error : " + e);
            resp.sendRedirect(Util.base_url);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int return_id = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();

        long feedMoney = 0;
        Invoice invoice = new Invoice();
        try {
            //Ödemeyi silme.
            int pa_id = Integer.parseInt(req.getParameter("pa_id"));
            Payments payments = sesi.load(Payments.class, pa_id);
            feedMoney = payments.getPa_paid();
            invoice = payments.getInvoice();
            sesi.delete(payments);
            tr.commit();
            return_id = 1;
        } catch (Exception ex) {
            System.err.println("Delete Error 1.try: " + ex);
            resp.sendRedirect(Util.base_url);//Sql çalışmadığında zaten buraya kadar gelemez ama pa_id değerini String olarak / boş
            //geldiğinde durumda burası patlayıp index.jsp'e gitmeli.
        } finally {
            sesi.close();
        }
        //Ödenen faturaya geri ücreti ekleme
        if (return_id == 1) {
            Session sesi1 = sf.openSession();
            Transaction tr1 = sesi1.beginTransaction();
            try {
                System.out.println("----------------------------------------->DENEME<-----------------------------------");
                System.out.println(invoice.getIn_balance());
                System.out.println(feedMoney);
                long i = invoice.getIn_balance() + feedMoney;
                invoice.setIn_balance(i);
                sesi1.update(invoice);
                tr1.commit();
            } catch (Exception e) {
                System.err.println("PayInServlet doDelete, 2.try error. " + e);
            } finally {
                sesi1.close();
            }
        }
        resp.setContentType("application/json");
        resp.getWriter().write("" + return_id);
    }
}
