package servlets;

import com.google.gson.Gson;
import entities.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import properties.InterlayerToRememberjsAndChangePasswordServlet;
import utils.HibernateUtil;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "changePasswordServlet", value = "/change-password-post")
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Admin şifresi değiştirme, datalar remember.js den geliyor.

        String obj = req.getParameter("obj");

        Gson gson = new Gson();
        InterlayerToRememberjsAndChangePasswordServlet i = gson.fromJson(obj, InterlayerToRememberjsAndChangePasswordServlet.class);

        System.out.println("Şifresi Değiştirilen" + i);

        int feedBack = 0;

        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session sesi = sf.openSession();
        Admin admin = new Admin();
        try {
            System.out.println(Integer.parseInt(i.getAd_id()));
            admin = sesi.load(Admin.class, Integer.parseInt(i.getAd_id()));
            System.out.println("admin : " + admin);
        } catch (Exception e) {
            admin = null;
            System.err.println("ChangePassword 1.try Error : " + e);
        } finally {
            sesi.close();
        }


        if (admin != null) {
            Session sesi1 = sf.openSession();
            Transaction tr1 = sesi1.beginTransaction();
            try {
                admin.setAd_password(Util.MD5(i.getNewPass()));
                sesi1.update(admin);
                tr1.commit();
                feedBack = 1;
            } catch (Exception e) {
                feedBack = 0;
                System.err.println("ChangePassword 2.try Error : " + e);
            } finally {
                sesi1.close();
            }
        }
        resp.setContentType("application/json");
        resp.getWriter().write("" + feedBack);
    }
}
