<%@ page import="views.dashboardviews.AllViewsDashboard" %>
<%@ page import="views.dashboardviews.LeftTable" %>
<%@ page import="views.dashboardviews.RightTable" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<%util.isLogin(request, response);%>
<%AllViewsDashboard allViews = util.getViewsDashboard(request, response);%>
<!doctype html>
<html lang="en">
<head>
    <title>Yönetim</title>
    <jsp:include page="inc/css.jsp"></jsp:include>
</head>
<body>
<div class="wrapper d-flex align-items-stretch">
    <jsp:include page="inc/sideBar.jsp"></jsp:include>
    <div id="content" class="p-4 p-md-5">
        <jsp:include page="inc/topMenu.jsp"></jsp:include>

        <h3 class="mb-3">
            Dashboard
            <small class="h6">Yönetim Paneli</small>
        </h3>


        <div class="row">
            <div class="col-sm-6">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="d-grid gap-2">
                            <a href="customer.jsp" class="btn btn-primary btn-lg mb-2 text-left custom_btn button1"
                               type="button"><i
                                    class="fas fa-user-plus"></i> Müşteri Ekle
                            </a>
                            <a href="products.jsp" class="btn btn-warning btn-lg mb-2 text-left custom_btn button2"
                               type="button"><i
                                    class="fa fa-shopping-basket"></i> Ürün Ekle
                            </a>
                            <a href="boxAction.jsp" class="btn btn-success btn-lg mb-2 text-left custom_btn button3"
                               type="button"><i
                                    class="fa fa-shopping-cart"></i> Sipariş Ekle
                            </a>
                            <a href="payIn.jsp" class="btn btn-danger btn-lg mb-2 text-left  custom_btn button4"
                               type="button"><i
                                    class="fa fa-window-maximize"></i> Ödeme Girişi
                            </a>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="card mb-3" id="card"
                        >
                            <div class="card-body d-flex flex-row justify-content-between"
                                 style="padding-bottom: .6rem;">
                                <div class="card-left">
                                    <div class="card-subtitle mb-1 text-muted" style="opacity: .8; margin-bottom: 0;">
                                        Toplam
                                    </div>
                                    <div class="card-title"
                                         style="font-weight: 700; margin-bottom: 0; letter-spacing: 0.5px;">Müşteri
                                        Hesabı
                                    </div>
                                </div>
                                <div class="card-right">
                                    <div style="font-size: 1.8rem;font-weight: 700; color:#22AE78;"><span
                                            id="totalCustomer"><%=allViews.getCCount()%></span></div>
                                </div>
                            </div>
                        </div>
                        <div class="card mb-3">
                            <div class="card-body d-flex flex-row justify-content-between"
                                 style="padding-bottom: .6rem;">
                                <div class="card-left">
                                    <div class="card-subtitle mb-1 text-muted" style="opacity: .8; margin-bottom: 0;">
                                        Toplam
                                    </div>
                                    <div class="card-title"
                                         style="font-weight: 700; margin-bottom: 0; letter-spacing: 0.5px;">Satış Tutarı
                                    </div>
                                </div>
                                <div class="card-right">
                                    <div style="font-size: 1.8rem;font-weight: 700; color: #244785;"><span
                                            id="totalOrder"><%=allViews.getTotalSales()%> ₺</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card mb-3">
                            <div class="card-body d-flex flex-row justify-content-between"
                                 style="padding-bottom: .6rem;">
                                <div class="card-left">
                                    <div class="card-subtitle mb-1 text-muted" style="opacity: .8; margin-bottom: 0;">
                                        Toplam
                                    </div>
                                    <div class="card-title"
                                         style="font-weight: 700; margin-bottom: 0; letter-spacing: 0.5px;">Stoktaki
                                        Ürün Çeşidi
                                    </div>
                                </div>
                                <div class="card-right">
                                    <div style="font-size: 1.8rem;font-weight: 700;color: #F7B924;"><span
                                            id="totalStock"><%=allViews.getPCount()%></span></div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
            <div class="col-sm-6">
                <div class="card mb-3"
                     style="height:140px;border-width:0; box-shadow: 0 0.46875rem 2.1875rem rgb(4 9 20 / 3%), 0 0.9375rem 1.40625rem rgb(4 9 20 / 3%), 0 0.25rem 0.53125rem rgb(4 9 20 / 5%), 0 0.125rem 0.1875rem rgb(4 9 20 / 3%);">
                    <div class="card-body">
                        <h4 class="card-title badge badge-success mr-1 ml-0 text-white p-2"
                            style="font-size:.88rem;background-color: #3AC47D;">KASA</h4>
                        <div class="row space-5">
                            <div class="col-sm-12 col-md-12" style="height: 20px;">
                                <small class="text-muted" style="color: #978e8e;">KASA</small>
                                <small class="text-muted">toplam</small>
                                <%int value = allViews.getTotalPayIn() - allViews.getTotalPayOut();%>
                                <span id="kasaToplam" style="color: black;"><b title="total"><%=value%> TL</b></span>
                            </div>
                            <div class="col-6 col-sm-6 col-md-6">
                                <div>
                                    <small class="text-muted">bu gün giriş</small>
                                    <br>
                                    <span class="ff-2 fs-14 bold" style="color: black;"><b
                                            id="income"> + <%=allViews.getTotalPayIn()%> TL </b></span>
                                </div>
                            </div>
                            <div class="col-6 col-sm-6 col-md-6">
                                <div>
                                    <small class="text-muted">bu gün çıkış</small>
                                    <br>
                                    <span class="ff-2 fs-14 bold" style="color: black;"><b
                                            id="expense"> - <%=allViews.getTotalPayOut()%> TL </b></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="card"
                     style="height:140px;border-width:0; box-shadow: 0 0.46875rem 2.1875rem rgb(4 9 20 / 3%), 0 0.9375rem 1.40625rem rgb(4 9 20 / 3%), 0 0.25rem 0.53125rem rgb(4 9 20 / 5%), 0 0.125rem 0.1875rem rgb(4 9 20 / 3%);">
                    <div class="card-body">
                        <h4 class="card-title badge badge-success mr-1 ml-0 text-white p-2"
                            style="font-size:.88rem;background-color: #F7B924;">STOK</h4>
                        <div class="row space-5">
                            <div class="col-6 col-sm-6 col-md-6">
                                <div>
                                    <small class="text-muted">maliyet değeri(Ortalama 1 Ürün)</small>
                                    <br>
                                    <span class="ff-2 fs-14 bold" style="color: black;"><b
                                            id="maliyet"> <%=allViews.getTotalMaliyet()%> TL </b></span>
                                </div>
                            </div>
                            <div class="col-6 col-sm-6 col-md-6">
                                <div>
                                    <small class="text-muted">satış değeri(Ortalama 1 Ürün kdv hariç)</small>
                                    <br>
                                    <span class="ff-2 fs-14 bold" style="color: black;"><b
                                            id="satisDegeri"> <%=allViews.getSatisDegeri()%> TL </b></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mt-5">
            <div class="col-sm-6">
                <div class="main-card mb-3 card mainCart">
                    <div class="card-header cardHeader">Depoda mevcut en değerli 5 stok ürünü (Birim fiyat)
                    </div>
                    <div class="table-responsive">
                        <table class="align-middle mb-0 table table-borderless table-striped table-hover">
                            <thead>
                            <tr>
                                <th>Pid</th>
                                <th>Ürün Kodu</th>
                                <th>Ürün Adı</th>
                                <th>Alış Fiyatı</th>
                                <th>Satış Fiyatı</th>
                                <th>KDV Oranı</th>
                                <th>Stok Miktarı</th>
                            </tr>
                            </thead>
                            <tbody id="leftTable">
                            <!-- for loop  -->
                            <%
                                int counter0 = 0;
                                for (int i = 0; i < allViews.getLeftTableList().size(); i++) {
                                    LeftTable item = allViews.getLeftTableList().get(i);
                                    if (counter0 < 5) {
                            %>
                            <tr role="row" class="odd">

                                <td><%=item.getPr_id()%>
                                </td>
                                <td><%=item.getPr_code()%>
                                </td>
                                <td><%=item.getPr_title()%>
                                </td>
                                <td><%=item.getPr_purchasePrice()%> ₺
                                </td>
                                <td><%=item.getPr_salePrice()%> ₺
                                </td>
                                <%
                                    String kdv = "";
                                    int vat = item.getPr_vat();
                                    if (vat == 1) {
                                        kdv = "Dahil";
                                    } else if (vat == 2) {
                                        kdv = "%1";
                                    } else if (vat == 3) {
                                        kdv = "%8";
                                    } else {
                                        kdv = "%18";
                                    }
                                %>
                                <td><%=kdv%>
                                </td>
                                <td><%=item.getPr_amount()%>
                                </td>
                            </tr>
                            <%
                                        counter0++;
                                    } else {
                                        break;
                                    }
                                }
                            %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="main-card mb-3 card"
                     style="box-shadow: 0 0.46875rem 2.1875rem rgb(4 9 20 / 3%), 0 0.9375rem 1.40625rem rgb(4 9 20 / 3%), 0 0.25rem 0.53125rem rgb(4 9 20 / 5%), 0 0.125rem 0.1875rem rgb(4 9 20 / 3%);border-width: 0;">
                    <div class="card-header"
                         style="text-transform: uppercase;color: rgb(13, 27, 62,0.7);font-weight: 700; font-size: 0.88rem;">
                        En Pahalı Kesilen Faturalar (Toplam Satış Tutarı)
                    </div>
                    <div class="table-responsive">
                        <table class="align-middle mb-0 table table-borderless table-striped table-hover">
                            <thead>
                            <tr>
                                <th>FişNo</th>
                                <th>Müşteri Adı</th>
                                <th>Fatura Tutarı</th>
                            </tr>
                            </thead>
                            <!-- for loop  -->
                            <tbody id="rightTable">


                            <%
                                int counter1 = 0;
                                for (int i = 0; i < allViews.getRightTableList().size(); i++) {
                                    RightTable item = allViews.getRightTableList().get(i);
                                    if (counter1 < 5) {
                            %>
                            <tr role="row" class="odd">

                                <td><%=item.getVoucher()%>
                                </td>
                                <td><%=item.getName()%>
                                </td>
                                <td><%=item.getTutar()%> ₺
                                </td>
                            </tr>
                            <%
                                        counter1++;
                                    } else {
                                        break;
                                    }
                                }
                            %>


                            </tbody>
                        </table>
                    </div>
                </div>


            </div>
        </div>


    </div>
</div>
</div>
<jsp:include page="inc/js.jsp"></jsp:include>
</body>

</html>
