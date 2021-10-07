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

@WebServlet(name = "invoiceSingleServlet", value = "/invoice-single-servlet")
public class InvoiceSingleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int cu_id = Integer.parseInt(req.getParameter("cu_id"));
            int in_id = Integer.parseInt(req.getParameter("in_id"));
            System.out.println("customer id " + cu_id);
            System.out.println("invoice id " + in_id);

            Util util = new Util();
            InterlayerToPayIn i = util.getXCustomerVoucherInformations(cu_id, in_id);

            Gson gson = new Gson();
            String stJson = gson.toJson(i);
            resp.setContentType("application/json");
            resp.getWriter().write(stJson);
        } catch (Exception e) {
            System.err.println("invoiceSingleServlet casting error : " + e);
            resp.sendRedirect(Util.base_url);
            //direkt base_url+invoice-single-servlet yazınca index.jsp e atılıyor.
        }

    }
}

//Bu servleti ödeme yapma ekranında max girilecebilecek değerleri belirleme ve ödeme yaparken kullandım.
//payIn