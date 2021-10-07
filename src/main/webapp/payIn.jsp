<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="entities.Customer" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<%util.isLogin(request, response);%>
<%List<Customer> customerList = util.getCustomerList();%>
<!doctype html>
<html lang="en">

<head>
    <title>Kasa Yönetimi / Ödeme Girişi</title>
    <jsp:include page="inc/css.jsp"></jsp:include>
</head>

<body>

<div class="wrapper d-flex align-items-stretch">
    <jsp:include page="inc/sideBar.jsp"></jsp:include>
    <div id="content" class="p-4 p-md-5">
        <jsp:include page="inc/topMenu.jsp"></jsp:include>
        <h3 class="mb-3">
            <a href="payOut.jsp" class="btn btn-danger float-right">Ödeme Çıkışı</a>
            Kasa Yönetimi
            <small class="h6">Ödeme Girişi</small>
        </h3>


        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Ödeme Ekle</div>

            <form id="payInForm" class="row p-3">

                <div class="col-md-3 mb-3">
                    <label for="customer_select" class="form-label">Müşteriler</label>

                    <select id="customer_select" class="selectpicker" data-width="100%" data-show-subtext="true"
                            data-live-search="true">
                        <option value="0" data-subtext="">Seçim Yapınız</option>

                        <%
                            if (customerList.size() > 0) {
                                for (int i = 0; i < customerList.size(); i++) {
                                    if (customerList.get(i).isCu_isAvailable() == true) {
                        %>
                        <option value="<%=customerList.get(i).getCu_id()%>"
                                data-subtext="<%=customerList.get(i).getCu_code()%>"><%=customerList.get(i).getCu_name()%> <%=customerList.get(i).getCu_surname()%>
                        </option>
                        <% }
                        }
                        }
                        %>

                    </select>

                </div>

                <div class="col-md-3 mb-3">
                    <label for="voucher_select" class="form-label">Müşteri Fişleri</label>
                    <select id="voucher_select" class="selectpicker" data-width="100%" data-show-subtext="true"
                            data-live-search="true">

                        <option value="0" data-subtext="">Seçim Yapınız</option>

                    </select>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="payInTotal" class="form-label">Ödeme Tutarı</label>
                    <input id="payInTotal" type="number" min="1" name="payInTotal" class="form-control"/>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="payInDetail" class="form-label">Ödeme Detay</label>
                    <input id="payInDetail" type="text" name="payInDetail" class="form-control"/>
                </div>


                <div class="btn-group col-md-3 " role="group">
                    <button type="submit" class="btn btn-outline-primary mr-1">Gönder</button>
                    <button onclick="fncReset()" type="button" class="btn btn-outline-primary">Temizle</button>
                </div>
            </form>
        </div>


        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Ödeme Giriş Listesi</div>
            <div class="table-responsive">
                <table class="align-middle mb-0 table table-borderless table-striped table-hover">
                    <thead>
                    <tr>
                        <th>Payment ID</th>
                        <th>Fiş Numarası</th>
                        <th>Ödeme Zamanı</th>
                        <th>Ödeme Tarihi</th>
                        <th>Ödenen Tutar</th>
                        <th>Toplam Kalan Tutar</th>
                        <th>Ödeme Detayı</th>
                        <th>Toplam Fatura Tutarı</th>
                        <th class="text-center" style="width: 155px;">Yönetim</th>
                    </tr>
                    </thead>
                    <tbody id="paymentsHistoryTable">
                    <!-- for loop  -->
                    </tbody>
                </table>
            </div>
        </div>


    </div>
</div>
</div>
<jsp:include page="inc/js.jsp"></jsp:include>
<script src="js/payIn.js"></script>
</body>

</html>

