package servlets;

import com.google.gson.Gson;
import entities.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import utils.HibernateUtil;
import utils.SendMail;
import utils.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


//E-posta yoluyla şifre yenileme işlemi.
@WebServlet(name = "adminRememberServlet", value = {"/admin-remember-post", "/admin-remember-get"})
public class AdminRememberServlet extends HttpServlet {
    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //mail gönderme
        Session sesi = sf.openSession();
        try {
            String obj = req.getParameter("obj");
            Gson gson = new Gson();
            String email = gson.fromJson(obj, String.class);

            Admin admin1 = (Admin) sesi.createQuery("from Admin where ad_email=?1")
                    .setParameter(1, email)
                    .getSingleResult();

            //Biri varsa devam edecek kod.

            SendMail sm = new SendMail();
            boolean feedback = sm.isSendEmail(admin1);

            resp.setContentType("application/json");
            resp.getWriter().write(String.valueOf(feedback));

        } catch (Exception ex) {
            System.err.println("adminRememberServlet Post Error : " + ex);
            resp.setContentType("application/json");
            resp.getWriter().write(String.valueOf(false));
        } finally {
            sesi.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("remember doGEt");
        //gelen maile göre yönlendirme
        String ad_id = req.getParameter("aa");
        String ad_name = req.getParameter("bb");
        String ad_surname = req.getParameter("cc");
        String ad_email = req.getParameter("dd");
        String ad_password = req.getParameter("ee");

        Session sesi = sf.openSession();
        Admin admin = new Admin();
        try {
            admin = (Admin) sesi.createQuery("FROM " +
                            "Admin " +
                            "WHERE\n" +
                            "\tMD5( ad_id ) =?1 \n" +
                            "\tAND MD5( ad_name ) =?2 \n" +
                            "\tAND MD5( ad_surname ) =?3 \n" +
                            "\tAND MD5( ad_email ) =?4 \n" +
                            "\tAND ad_password =?5 ")
                    .setParameter(1, ad_id)
                    .setParameter(2, ad_name)
                    .setParameter(3, ad_surname)
                    .setParameter(4, ad_email)
                    .setParameter(5, ad_password)
                    .getSingleResult();
            req.setAttribute("rememberAdminInfos", admin);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/remember.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            resp.sendRedirect(Util.base_url);
        } finally {
            sesi.close();
        }

    }
}
