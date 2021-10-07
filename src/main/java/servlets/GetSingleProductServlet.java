package servlets;

import com.google.gson.Gson;
import entities.Product;
import utils.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "getSingleProductServlet", value = "/get-single-product-servlet")
public class GetSingleProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int pr_id = Integer.parseInt(req.getParameter("ara"));
            Util util = new Util();
            Product product = util.getXProduct(pr_id);

            Gson gson = new Gson();
            String stJson = gson.toJson(product);
            resp.setContentType("application/json");
            resp.getWriter().write(stJson);
        } catch (Exception e) {
            System.err.println("getSingleProductServlet casting error : " + e);
            resp.sendRedirect(Util.base_url);
            //direkt base_url+get-single-product-servlet yazınca index.jsp e atılıyor.
        }
    }
}
