package servlets;

import entities.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "settingsServlet", value = "/settings-post")
public class SettingsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ad_email = req.getParameter("ad_email");
        String ad_name = req.getParameter("ad_name");
        String ad_surname = req.getParameter("ad_surname");
        String newPassword = req.getParameter("newPassword");
        String newPasswordr = req.getParameter("newPasswordr");
        int ad_id = 0;
        int get_ad_id = 0;
        try {
            String strget_ad_id = req.getParameter("ad_id");//get methoduna yazılan ID
            String strad_id = String.valueOf(req.getSession().getAttribute("admin_id"));//Açık admin ID
            ad_id = Integer.parseInt(strad_id);
            get_ad_id = Integer.parseInt(strget_ad_id);
        } catch (Exception e) {
            //Buraya asla gelmiyor sebebi post methodunu direkt url üzerinden erişim istenirse 405 sayfasına gidiyor.
            //System.err.println("SettingsServlet Error : " + e);
            //resp.sendRedirect(Util.base_url);
        }

        int f = 0;

        if (ad_id == get_ad_id && newPassword.equals(newPasswordr)) {

            Admin admin = new Admin();
            admin.setAd_id(ad_id);
            admin.setAd_name(ad_name);
            admin.setAd_surname(ad_surname);
            admin.setAd_email(ad_email);
            admin.setAd_password(Util.MD5(newPassword));

            System.out.println("güncellendi...");
            System.out.println(admin);

            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session sesi = sf.openSession();
            Transaction tr = sesi.beginTransaction();

            try {
                sesi.update(admin);
                tr.commit();
                f = 1;
            } catch (Exception e) {
                System.err.println("Error SettingServlet " + e);
            } finally {
                sesi.close();
            }
            if (f == 1) {
                AdminServlet adminServlet = new AdminServlet();
                adminServlet.removeInfoOldAdmin(req, resp);
                resp.sendRedirect(Util.base_url);
                //Admin cookie ve session temizlendi.
            } else {
                req.setAttribute("settingFeedback", "Sql hatası oluştu tekrar deneyiniz..");
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/settings.jsp");
                dispatcher.forward(req, resp);
            }

        } else {
            //ilk if koşulunun false olma ihtimali yok.
            req.setAttribute("settingFeedback", "Yeni şifreler birbirinden farklı.");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/settings.jsp");
            dispatcher.forward(req, resp);
        }

    }
}
