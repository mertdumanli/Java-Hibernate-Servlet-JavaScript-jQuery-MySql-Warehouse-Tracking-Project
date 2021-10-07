package servlets;

import com.google.gson.Gson;
import entities.Admin;
import entities.AdminFollow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet(name = "adminServlet", value = {"/admin-post", "/admin-get"})
public class AdminServlet extends HttpServlet {
    SessionFactory sf = HibernateUtil.getSessionFactory();

    //login işlemi
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Session sesi = sf.openSession();
        try {
            String obj = req.getParameter("obj");
            Gson gson = new Gson();
            Admin admin = gson.fromJson(obj, Admin.class);

            admin.setAd_password(Util.MD5(admin.getAd_password()));

            Admin admin1 = (Admin) sesi.createQuery("from Admin where ad_email=?1 and ad_password=?2")
                    .setParameter(1, admin.getAd_email())
                    .setParameter(2, admin.getAd_password())
                    .getSingleResult();
            System.out.println(admin1);

            //Bu adımda artık giriş yapılacağı kesinleşiyor.
            //Çünkü admin bilgisi veri tabanında bulunuyor.
            //Burada daha önce giriş yapmış ama güvenli çıkış işlemini yapmamış adminin bilgilerini süpürdüm.

            removeInfoOldAdmin(req, resp);

            //Önceki Girişleri Temizleme
            //*********************************************************************************************************


            //Session Create
            int ad_id = admin1.getAd_id();
            String ad_name = admin1.getAd_name();
            String ad_surname = admin1.getAd_surname();
            String ad_email = admin1.getAd_email();
            String ad_password = admin1.getAd_password();

            req.getSession().setAttribute("admin_id", ad_id);
            req.getSession().setAttribute("admin_name", ad_name);
            req.getSession().setAttribute("admin_surname", ad_surname);
            req.getSession().setAttribute("admin_email", ad_email);
            req.getSession().setAttribute("admin_password", ad_password);
            //attribute isimleri Util.java içindeki isLogin methodundakilerle aynı.

            //***************Admin Girişlerinin Yazılması
            Session sesi1 = sf.openSession();
            Transaction tr1 = sesi1.beginTransaction();
            try {
                AdminFollow adminFollow = new AdminFollow();
                adminFollow.setAdmin(admin1);
                adminFollow.setStatus("Giriş");
                adminFollow.setLocalDateTime(LocalDateTime.now());
                sesi1.save(adminFollow);
                tr1.commit();
            } catch (Exception e) {
                System.err.println("AdminServlet admin girişlerinin yazılması: " + e);
            } finally {
                sesi1.close();
            }
            //********************************************


            String stJson = gson.toJson(admin1);
            resp.setContentType("application/json");
            resp.getWriter().write(stJson);

        } catch (Exception ex) {
            System.err.println("AdminServlet Post Error : " + ex);
            resp.setContentType("application/json");
            resp.getWriter().write(String.valueOf(false));
        } finally {
            sesi.close();
        }
    }

    //quit işlemi
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //***************Admin Çıkışlarının Yazılması
        Admin admin = new Admin();
        try {
            String stradmin_id = String.valueOf(req.getSession().getAttribute("admin_id"));
            admin.setAd_id(Integer.parseInt(stradmin_id));

            admin.setAd_name((String) req.getSession().getAttribute("admin_name"));

            admin.setAd_surname((String) req.getSession().getAttribute("admin_surname"));

            admin.setAd_email((String) req.getSession().getAttribute("admin_email"));

            admin.setAd_password((String) req.getSession().getAttribute("admin_password"));
        } catch (Exception e) {
            System.err.println("Admin LogOut Error!");
            System.err.println("Cookie TimeOut!");
            //Max sürede sistemde kaldığı söylenebilir.
        }

        Session sesi1 = sf.openSession();
        Transaction tr1 = sesi1.beginTransaction();
        try {
            AdminFollow adminFollow = new AdminFollow();
            adminFollow.setAdmin(admin);
            adminFollow.setStatus("Çıkış");
            adminFollow.setLocalDateTime(LocalDateTime.now());
            sesi1.save(adminFollow);
            tr1.commit();
        } catch (Exception e) {
            System.err.println("AdminServlet admin çıkışlarının yazılması: " + e);
        } finally {
            sesi1.close();
        }
        //********************************************


        System.out.println("admin-get called");
        // session items delete
        req.getSession().removeAttribute("admin_id");
        req.getSession().removeAttribute("admin_name");
        req.getSession().removeAttribute("admin_surname");
        req.getSession().removeAttribute("admin_email");
        req.getSession().removeAttribute("admin_password");

        // all session remove
        req.getSession().invalidate();

        // cookie delete
        Cookie cookie = new Cookie("user", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);


        resp.sendRedirect(Util.base_url + "index.jsp");
    }

    public void removeInfoOldAdmin(HttpServletRequest req, HttpServletResponse resp) {
//***************Admin Çıkışlarının Yazılması
        int a = 0;
        Admin admin = new Admin();

        try {
            String stradmin_id = String.valueOf(req.getSession().getAttribute("admin_id"));
            admin.setAd_id(Integer.parseInt(stradmin_id));

            admin.setAd_name((String) req.getSession().getAttribute("admin_name"));

            admin.setAd_surname((String) req.getSession().getAttribute("admin_surname"));

            admin.setAd_email((String) req.getSession().getAttribute("admin_email"));

            admin.setAd_password((String) req.getSession().getAttribute("admin_password"));
            a = 1;
        } catch (Exception e) {
            System.err.println("remove!");
            a = 0;
            //Max sürede sistemde kaldığı söylenebilir.
        }

        if (a != 0) {
            Session sesi1 = sf.openSession();
            Transaction tr1 = sesi1.beginTransaction();
            try {
                AdminFollow adminFollow = new AdminFollow();
                adminFollow.setAdmin(admin);
                adminFollow.setStatus("Çıkış");
                adminFollow.setLocalDateTime(LocalDateTime.now());
                sesi1.save(adminFollow);
                tr1.commit();
            } catch (Exception e) {
                System.err.println("AdminServlet admin çıkışlarının yazılması: " + e);
            } finally {
                sesi1.close();
            }
        }
        //********************************************

        // session items delete
        req.getSession().removeAttribute("admin_id");
        req.getSession().removeAttribute("admin_name");
        req.getSession().removeAttribute("admin_surname");
        req.getSession().removeAttribute("admin_email");
        req.getSession().removeAttribute("admin_password");

        // all session remove
        req.getSession().invalidate();

        // cookie delete
        Cookie cookie = new Cookie("user", "");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
}
