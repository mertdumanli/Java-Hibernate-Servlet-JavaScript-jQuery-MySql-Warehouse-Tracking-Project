<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<%util.isLogin(request, response);%>

<!doctype html>
<html lang="en">

<head>
    <title>Kasa Yönetimi / Ödeme Çıkışı</title>
    <jsp:include page="inc/css.jsp"></jsp:include>
</head>

<body>

<div class="wrapper d-flex align-items-stretch">
    <jsp:include page="inc/sideBar.jsp"></jsp:include>
    <div id="content" class="p-4 p-md-5">
        <jsp:include page="inc/topMenu.jsp"></jsp:include>
        <h3 class="mb-3">
            Kasa Yönetimi
            <small class="h6">Ödeme Çıkışı</small>
        </h3>

        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Ödeme Ekle</div>

            <form  id="payOutForm"  class="row p-3">

                <div class="col-md-3 mb-3">
                    <label for="payOutTitle" class="form-label">Başlık</label>
                    <input id="payOutTitle" type="text" name="payOutTitle" class="form-control"/>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="payOutType" class="form-label">Ödeme Türü</label>
                    <select id="payOutType" class="form-select" name="payOutType">
                        <option value="0">Ödeme Türünü Seçiniz</option>
                        <option value="1">Nakit</option>
                        <option value="2">Kredi Kartı</option>
                        <option value="3">Havale</option>
                        <option value="4">EFT</option>
                        <option value="5">Banka Çeki</option>
                    </select>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="payOutTotal" class="form-label">Ödeme Tutarı</label>
                    <input id="payOutTotal" type="number" name="payOutTotal" class="form-control"/>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="payOutDetail" class="form-label">Ödeme Detay</label>
                    <input type="text" name="payOutDetail" id="payOutDetail" class="form-control"/>
                </div>

                <div class="btn-group col-md-3 " role="group">
                    <button type="submit" class="btn btn-outline-primary mr-1">Gönder</button>
                    <button onclick="fncReset()" type="button" class="btn btn-outline-primary">Temizle</button>
                </div>
            </form>
        </div>


        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Ödeme Çıkış Listesi</div>

            <div class="table-responsive">
                <table class="align-middle mb-0 table table-borderless table-striped table-hover">
                    <thead>
                    <tr>
                        <th>PaymentOut Id</th>
                        <th>Başlık</th>
                        <th>Ödeme Türü</th>
                        <th>Ödeme Detayı</th>
                        <th>Ödeme Tutarı</th>
                        <th>Ödeme Tarihi</th>
                        <th>Ödeme Zamanı</th>
                        <th>İşlemi Gerçekleştiren Admin ID</th>
                    </tr>
                    </thead>
                    <tbody id="paymentsOutHistoryTable">
                    <!-- for loop  -->
                    </tbody>
                </table>
            </div>
        </div>


    </div>
</div>
</div>
<jsp:include page="inc/js.jsp"></jsp:include>
<script src="js/payOut.js"></script>
</body>

</html>
