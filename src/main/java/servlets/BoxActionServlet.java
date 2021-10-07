package servlets;

import com.google.gson.Gson;
import entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import properties.InterlayerToBoxAction;
import utils.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "boxActionServlet", value = {"/box-action-post", "/box-action-delete"})
public class BoxActionServlet extends HttpServlet {
    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Sepete Ürün ekleme
        int id = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        String obj = req.getParameter("obj");
        Gson gson = new Gson();
        InterlayerToBoxAction i = gson.fromJson(obj, InterlayerToBoxAction.class);

        if (i.getCount() == 0 || i.getCustomer_select() == 0 || i.getProduct_select() == 0 || i.getVoucherNumber() == 0) {
            System.out.println("Boş girilen değer mevcut. İşlem iptal edildi.");
        } else {
            int cu_id = i.getCustomer_select();
            int pr_id = i.getProduct_select();

            PurchaseOrders orders = isSameProduct(cu_id, pr_id);
            System.out.println("Ürün => " + orders);

            Product product = getxProduct(pr_id);
            int number = i.getCount();

            if (orders == null) {
                //Daha önce sepete eklenmemiş.
                Customer customer = getxCustomer(cu_id);
                int voucherNo = i.getVoucherNumber();
                boolean status = false;

                PurchaseOrders ordersNew = new PurchaseOrders();

                ordersNew.setCustomer(customer);
                ordersNew.setProduct(product);
                ordersNew.setNumber(number);
                ordersNew.setVoucherNo(voucherNo);
                ordersNew.setStatus(status);
                try {
                    sesi.save(ordersNew);
                    tr.commit();
                    id = 1;
                } catch (Exception e) {
                    System.err.println("BoxActionServlet doGet Error : " + e);
                } finally {
                    sesi.close();
                }
            } else {
                //Daha önce sepete eklenmiş
                orders.setNumber(orders.getNumber() + i.getCount());
                try {
                    sesi.update(orders);
                    tr.commit();
                    id = 1;
                } catch (Exception e) {
                    System.err.println("BoxActionServlet doGet Error : " + e);
                } finally {
                    sesi.close();
                }
            }
            if (id == 1) {
                //Stoktan azaltma.
                if (id != 0) {
                    id = 0;
                    //Ürünlerden satılan ürün adetini düşürme.
                    Session sesi0 = sf.openSession();
                    Transaction tr0 = sesi0.beginTransaction();

                    try {//Önce sepete ekleme gerçekleşti. Sepete eklendiği vakit stoklardan azaltıldı.
                        int eksilen = product.getPr_amount();
                        int cikan = number;
                        int kalan = eksilen - cikan;
                        product.setPr_amount(kalan);
                        sesi0.update(product);
                        tr0.commit();
                        id = 1;
                    } catch (Exception e) {
                        tr.rollback();
                        System.err.println("sesi0 - tr0 Error : " + e);
                    } finally {
                        sesi0.close();
                    }
                }
            }
        }
        resp.setContentType("application/json");
        resp.getWriter().write("" + id);
    }


    private PurchaseOrders isSameProduct(int cu_id, int pr_id) {

        Session sesi = sf.openSession();
        try {
            return (PurchaseOrders) sesi.createSQLQuery("CALL SearchProductOneCustomer(?,?)")
                    .addEntity(PurchaseOrders.class)
                    .setParameter(1, cu_id)
                    .setParameter(2, pr_id)
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("isSameProduct Error : " + e);
        } finally {
            sesi.close();
        }
        return null;
    }

    private Customer getxCustomer(int i) {
        Session sesi = sf.openSession();
        try {
            return (Customer) sesi.createQuery("from Customer where cu_id=?1")
                    .setParameter(1, i)
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("getxCustomer Error : " + e);
        } finally {
            sesi.close();
        }
        return null;
    }

    private Product getxProduct(int i) {
        Session sesi = sf.openSession();
        try {
            return (Product) sesi.createQuery("from Product where pr_id=?1")
                    .setParameter(1, i)
                    .getSingleResult();
        } catch (Exception e) {
            System.err.println("getxProduct Error : " + e);
        } finally {
            sesi.close();
        }
        return null;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//Sepetten silme.
        int return_id = 0;
        Session sesi1 = sf.openSession();

        try {

            //Sepetten silme.
            int po_id = Integer.parseInt(req.getParameter("po_id"));
            PurchaseOrders purchaseOrders = sesi1.load(PurchaseOrders.class, po_id);
            Transaction tr1 = sesi1.beginTransaction();
            sesi1.delete(purchaseOrders);
            tr1.commit();

            //Sepette olan bir ürün gizlenmiş olabilir bu sebeple gösterime de açılacak. Ürün stoklara geri eklenecek.
            Session sesi2 = sf.openSession();


            int toplanan1 = purchaseOrders.getProduct().getPr_amount();//Depodaki ürünün stok miktarı. (Ürün silinmişse bu değer 0 geliyor.)
            int toplanan2 = purchaseOrders.getNumber();//Depoya geri eklenen değer miktarı.
            int toplam = toplanan1 + toplanan2;

            Product product = sesi2.load(Product.class, purchaseOrders.getProduct().getPr_id());
            System.out.println("product : " + product);

            product.setPr_amount(toplam);//yeni stok sayısı.
            product.setPr_isAvailable(true);//gösterime açıldı.

            Transaction tr2 = sesi2.beginTransaction();
            sesi2.update(product);
            tr2.commit();

            return_id = 1;
        } catch (Exception ex) {
            System.err.println("BoxActionServlet doDelete Error : " + ex);
        } finally {
            sesi1.close();
        }

        resp.setContentType("application/json");
        resp.getWriter().write("" + return_id);
    }

}


