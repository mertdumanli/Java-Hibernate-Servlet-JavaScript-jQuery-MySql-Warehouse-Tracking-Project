package servlets;

import com.google.gson.Gson;
import entities.Invoice;
import entities.PurchaseOrders;
import entities.Vouchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import properties.InterlayerToCompleteTheSale;
import utils.HibernateUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "purchaseOrdersServlet", value = {"/purchase-orders-get", "/purchase-orders-post"})
public class PurchaseOrdersServlet extends HttpServlet {
    SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Seçili kişinin ürünleri getirelecek.
        String ara = req.getParameter("purchase_ara");

        Session sesi = sf.openSession();
        List<PurchaseOrders> ls = sesi.createQuery("from PurchaseOrders where cu_id = ?1 and status=0")
                .setParameter(1, ara)
                .getResultList();
        sesi.close();

        Gson gson = new Gson();
        String stJson = gson.toJson(ls);
        resp.setContentType("application/json");
        resp.getWriter().write(stJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int f = 0;
        Session sesi = sf.openSession();
        Transaction tr = sesi.beginTransaction();
        try {
            String obj = req.getParameter("obj");
            Gson gson = new Gson();
            InterlayerToCompleteTheSale i = gson.fromJson(obj, InterlayerToCompleteTheSale.class);

            //Bu fiş numarasına sahip sepetteki ürünleri durumlarını 1 yap.
            // **************************************************************

            System.out.println(i.getVoucherNumber());


            //***********************************************

            int exec = sesi.createSQLQuery("CALL CompleteSaleProcedure(?)")
                    .setParameter(1, i.getVoucherNumber())
                    .executeUpdate();
            tr.commit();

            //***********************************************

            if (exec > 0) {
                //sepette ürün varmış ve sipariş tamamlanmış.
                f = addVouchers(i.getVoucherNumber());
                System.out.println("f değeri " + f);
            }

        } catch (Exception ex) {
            System.err.println("PurchaseOrdersServlet doPost Error : " + ex);
        } finally {
            sesi.close();
        }

        resp.setContentType("application/json");
        resp.getWriter().write("" + f);
    }

    private int addVouchers(int v) {
        int f = 0;
        Session sesi = sf.openSession();
        List<PurchaseOrders> ls = new ArrayList<>();
        try {
            ls = sesi.createQuery("from PurchaseOrders where voucherNo=?1")
                    .setParameter(1, v)
                    .getResultList();
            f = 1;
        } catch (Exception e) {
            System.err.println("Error addVoucher : " + e);
        } finally {
            sesi.close();
        }

        ls.forEach(item -> {
            System.out.println("item : => " + item);
        });

        List<Vouchers> vouchersList = new ArrayList<>();
        if (ls.size() > 0) {
            Session sesi1 = sf.openSession();
            Transaction tr1 = sesi1.beginTransaction();
            try {
                for (PurchaseOrders item : ls) {
                    Vouchers vouchers = new Vouchers();
                    int alisFiyati = item.getProduct().getPr_purchasePrice();
                    int satisFiyati = item.getProduct().getPr_salePrice();
                    int kdv = item.getProduct().getPr_vat();
                    PurchaseOrders purchaseOrders = item;

                    vouchers.setAlisFiyati(alisFiyati);
                    vouchers.setSatisFiyati(satisFiyati);
                    vouchers.setKdv(kdv);
                    vouchers.setPurchaseOrders(purchaseOrders);

                    int vo_id = (int) sesi1.save(vouchers);
                    vouchers.setVo_id(vo_id);
                    vouchersList.add(vouchers);
                    f = 1;
                }
            } catch (Exception e) {
                f = 0;
                System.err.println("Error addVoucher v2 : " + e);
            } finally {
                tr1.commit();
                sesi1.close();
            }
            if (f == 1) {
                f = 0;

                //Fişleri Birleştirme.
                vouchersList.forEach(item -> {
                    System.out.println("Fişlerin vo_id numaraları : " + item.getVo_id());
                });
                long satisFiyatiKDVli;
                long total = 0;
                long depoMaliyeti = 0;
                for (Vouchers item : vouchersList) {
                    satisFiyatiKDVli = item.getSatisFiyati() + (item.getSatisFiyati() * kdvHesapla(item.getKdv())) / 100;
                    total += satisFiyatiKDVli * item.getPurchaseOrders().getNumber();//Total fiş tutarı.
                    depoMaliyeti += item.getAlisFiyati() * item.getPurchaseOrders().getNumber();
                }

                Invoice invoice = new Invoice();
                invoice.setIn_balance(total);
                invoice.setIn_total(total);
                invoice.setIn_depoMaliyeti(depoMaliyeti);
                invoice.setVouchersList(vouchersList);

                Session sesi2 = sf.openSession();
                Transaction tr2 = sesi2.beginTransaction();

                try {
                    sesi2.save(invoice);
                    tr2.commit();
                    f = 1;
                } catch (Exception e) {
                    System.err.println("sesi2 Error " + e);
                } finally {
                    sesi2.close();
                }
            }
        }
        return f;
    }

    private long kdvHesapla(int kdv) {
        if (kdv == 1) {
            return 0;
        } else if (kdv == 2) {
            return 1;
        } else if (kdv == 3) {
            return 8;
        } else {
            return 18;
        }
    }
}
