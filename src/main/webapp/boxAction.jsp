<%@ page import="java.util.List" %>
<%@ page import="entities.Customer" %>
<%@ page import="entities.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<%util.isLogin(request, response);%>
<%List<Customer> customerList = util.getCustomerList();%>
<%List<Product> productList = util.getProductList();%>

<!doctype html>
<html lang="en">

<head>
    <title>Yönetim</title>
    <jsp:include page="inc/css.jsp"></jsp:include>
</head>

<body>
<div class="wrapper d-flex align-items-stretch">
    <jsp:include page="inc/sideBar.jsp"></jsp:include>
    <!-- Page Content  -->
    <div id="content" class="p-4 p-md-5">
        <jsp:include page="inc/topMenu.jsp"></jsp:include>
        <h3 class="mb-3">
            Satış Yap
            <small class="h6">Satış Yönetim Paneli</small>
        </h3>

        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Yeni Satış</div>

            <form class="row p-3" id="newSale">

                <div class="col-md-3 mb-3">
                    <label for="customer_select" class="form-label">Müşteriler</label>

                    <select id="customer_select" class="selectpicker" data-width="100%" data-show-subtext="true"
                            data-live-search="true" required>

                        <option value="0" data-subtext="">Seçiniz</option>
                        <%
                            if (customerList.size() > 0) {
                                for (int i = 0; i < customerList.size(); i++) {
                                    if (customerList.get(i).isCu_isAvailable() == true) {
                        %>
                        <option value="<%=customerList.get(i).getCu_id()%>" data-subtext="<%=customerList.get(i).getCu_code()%>"><%=customerList.get(i).getCu_name()%> <%=customerList.get(i).getCu_surname()%>
                        </option>
                        <% }
                        }
                        }
                        %>

                    </select>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="product_select" class="form-label">Ürünler</label>
                    <select id="product_select" class="selectpicker" data-width="100%" data-show-subtext="true"
                            data-live-search="true" required>

                        <option value="0" data-subtext="">Seçim Yapınız</option>

                        <%
                            if (productList.size() > 0) {
                                for (int i = 0; i < productList.size(); i++) {
                                    if (productList.get(i).isPr_isAvailable() == true) {
                        %>
                        <option value="<%=productList.get(i).getPr_id()%>" data-subtext="<%=productList.get(i).getPr_code()%>"><%=productList.get(i).getPr_title()%> <%=productList.get(i).getPr_salePrice()%>
                            ₺
                        </option>
                        <% }
                        }
                        }
                        %>


                    </select>
                </div>


                <div class="col-md-3 mb-3">
                    <label for="count" class="form-label">Adet</label>
                    <input type="number" min="1" name="count" id="count" class="form-control" required/>
                </div>


                <div class="col-md-3 mb-3">
                    <label for="voucherNumber" class="form-label">Fiş No</label>
                    <input type="text" min="1" name="voucherNumber" id="voucherNumber" class="form-control" disabled
                           required/>
                </div>

                <div class="btn-group col-md-3 " role="group">
                    <button type="submit" class="btn btn-outline-primary mr-1">Ekle</button>
                </div>
            </form>
        </div>


        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Sepet Ürünleri</div>


            <div class="table-responsive">
                <table class="align-middle mb-0 table table-borderless table-striped table-hover">
                    <thead>
                    <tr>
                        <th>PO_ID</th>
                        <th>Ürün Başlığı</th>
                        <th>Adet</th>
                        <th>Birim Fiyat</th>
                        <th>Fiyat</th>
                        <th>KDV</th>
                        <th>Fiş No</th>
                        <th class="text-center" style="width: 55px;">Sil</th>
                    </tr>
                    </thead>
                    <tbody id="sepet">
                    <!-- for loop  -->
                    </tbody>
                </table>
            </div>
        </div>

        <div class="btn-group col-md-3 " role="group">
            <button type="button" id="complete" class="btn btn-outline-primary mr-1">Satışı Tamamla</button>
        </div>

    </div>
</div>

<jsp:include page="inc/js.jsp"></jsp:include>
<script src="js/boxAction.js"></script>
</body>

</html>