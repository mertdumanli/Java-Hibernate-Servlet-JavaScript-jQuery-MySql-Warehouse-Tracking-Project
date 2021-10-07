package servlets;

import com.google.gson.Gson;
import entities.Admin;
import entities.Invoice;

import entities.PaymentsOut;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import properties.InterlayerToPaymentsOut;
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

@WebServlet(name = "payOutServlet", value = {"/pay-out-get", "/pay-out-post", "/pay-out-delete"})
public class PayOutServlet extends HttpServlet {

    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//Kasadan çıkan ödemeler
        int feedBack = 0;

        String obj = req.getParameter("obj");
        Gson gson = new Gson();
        InterlayerToPaymentsOut i = gson.fromJson(obj, InterlayerToPaymentsOut.class);

        //*****************AdminFollow Bilgisi alımı.
        Util util = new Util();
        Admin admin = util.getXAdmin(req);


        if (admin != null) {
            PaymentsOut paymentsOut = new PaymentsOut();
            paymentsOut.setAdmin(admin);
            paymentsOut.setOp_title(i.getPayOutTitle());
            paymentsOut.setOp_type(i.getPayOutType());
            paymentsOut.setOp_total(i.getPayOutTotal());
            paymentsOut.setOp_detail(i.getPayOutDetail());
            paymentsOut.setLocalDateTime(LocalDateTime.now());

            Session sesi = sf.openSession();
            Transaction tr = sesi.beginTransaction();

            try {
                sesi.save(paymentsOut);
                tr.commit();
                feedBack = 1;
            } catch (Exception e) {
                System.err.println("payOutServlet doPost Error : " + e);
            } finally {
                sesi.close();
            }
        } else {
            System.out.println("Admin bilgisi alınamadı.");
        }
        resp.setContentType("application/json");
        resp.getWriter().write("" + feedBack);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//Kasadan çıkan ödemelerin listesi
        Session sesi = sf.openSession();
        List<PaymentsOut> ls = sesi.createQuery("from PaymentsOut").getResultList();
        System.out.println(ls);

        Gson gson = new Gson();
        String stJson = gson.toJson(ls);

        resp.setContentType("application/json");
        resp.getWriter().write(stJson);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int return_id = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();

        PaymentsOut paymentsOut = new PaymentsOut();
        try {
            //Ödemeyi silme.
            int op_id = Integer.parseInt(req.getParameter("op_id"));
            paymentsOut = sesi.load(PaymentsOut.class, op_id);
            sesi.delete(paymentsOut);
            tr.commit();
            return_id = 1;
        } catch (Exception ex) {
            System.err.println("Delete Error 1.try: " + ex);
            resp.sendRedirect(Util.base_url);
        } finally {
            sesi.close();
        }

        resp.setContentType("application/json");
        resp.getWriter().write("" + return_id);
    }
}
