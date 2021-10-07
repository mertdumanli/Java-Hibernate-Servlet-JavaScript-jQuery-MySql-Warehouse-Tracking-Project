package servlets;

import com.google.gson.Gson;
import entities.InterlayerToPayIn;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "invoiceServlet", value = "/invoice-servlet")
public class InvoiceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int cu_id = 0;
        try {
            cu_id = Integer.parseInt(req.getParameter("cu_id"));
        } catch (Exception e) {
            System.err.println("invoiceServlet casting error : " + e);
            resp.sendRedirect(Util.base_url);
            //direkt base_url+invoice-servlet yazınca index.jsp e atılıyor.
        }

        System.out.println("customer id " + cu_id);

        Util util = new Util();
        List<InterlayerToPayIn> ls = util.getXCustomerVoucherInformations(cu_id);

        ls.forEach(item -> {
            System.out.println(item);
        });

        Gson gson = new Gson();
        String stJson = gson.toJson(ls);
        resp.setContentType("application/json");
        resp.getWriter().write(stJson);
    }
}
