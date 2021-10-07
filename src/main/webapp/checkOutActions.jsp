<%@ page import="entities.Customer" %>
<%@ page import="java.util.List" %>
<%@ page import="views.checkoutactionsviews.AllViewsCheckOutActions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<%util.isLogin(request, response);%>
<%List<Customer> customerList = util.getCustomerList();%>
<%AllViewsCheckOutActions allViews = util.getViewsCheckOutActions(request, response);%>
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
            Kasa
            <small class="h6">Kasa Hareketleri</small>
        </h3>

        <div class="row">

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground1" id="card">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;">Toplam Kasaya Giren</h5>
                            <h4><strong><%=allViews.getPayInTotal()%> ₺</strong></h4>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-sm-4 mb-3">
                <div class="card cardBackground2" id="card">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;">Toplam Kasadan Çıkan</h5>
                            <h4><strong><%=allViews.getPayOutTotal()%> ₺</strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground3" id="card">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;"> Kasada Kalan</h5>
                            <%
                                long kalan = allViews.getPayInTotal() - allViews.getPayOutTotal();
                            %>
                            <h4><strong><%=kalan%> ₺</strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground4" id="card">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;"> Bugün Kasaya Giriş</h5>
                            <h4><strong><%=allViews.getPayInTotalToday()%> ₺</strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <div class="card cardBackground4" id="card">
                    <div class="card-body">
                        <div style="display: flex; justify-content: space-between;">
                            <h5 style="align-self: center;"> Bugün Kasadan Çıkan</h5>
                            <h4><strong><%=allViews.getPayOutTotalToday()%> ₺</strong></h4>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-4 mb-3">
                <a href="payIn.jsp">
                    <div class="card cardBackground4" id="card">
                        <div class="card-body">
                            <div style="display: flex; justify-content: space-between;">
                                <h5 style="align-self: center;"> Kasa Yönetimi</h5>
                                <i class="fas fa-link fa-2x" style="color: white; align-self: center;"></i>
                            </div>
                        </div>
                    </div>
                </a>
            </div>


        </div>

        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Arama / Rapor</div>

            <form class="row p-3" id="shownReport">

                <div class="col-md-3 mb-3">
                    <label for="customer_select" class="form-label">Müşteri Seçiniz</label>
                    <select id="customer_select" class="selectpicker" data-width="100%" data-show-subtext="true"
                            data-live-search="true">
                        <option value="0" data-subtext="">Tümü</option>
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
                    <label for="type" class="form-label">Tür</label>
                    <select id="type" class="form-select">
                        <option value="0">Tümü</option>
                        <option value="1">Giriş</option>
                        <option value="2">Çıkış</option>
                    </select>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="startDate" class="form-label">Başlama Tarihi</label>
                    <input id="startDate" type="date" name="startDate" class="form-control"/>
                </div>

                <div class="col-md-3 mb-3">
                    <label for="endDate" class="form-label">Bitiş Tarihi</label>
                    <input id="endDate" type="date" name="endDate" class="form-control"/>
                </div>

                <div class="col-md-3">
                    <button type="submit" class="col btn btn-outline-primary">Göster</button>
                </div>
            </form>
        </div>

        <div class="main-card mb-3 card mainCart">
            <div class="card-header cardHeader">Arama Sonuçları</div>
            <div class="table-responsive">
                <table class="align-middle mb-0 table table-borderless table-striped table-hover">
                    <thead id="inTypeThead">

                    </thead>
                    <tbody id="inTypeTbody">
                    <!-- for loop  -->
                    </tbody>
                </table>
                <table class="align-middle mb-0 table table-borderless table-striped table-hover">
                    <thead id="outTypeThead">

                    </thead>
                    <tbody id="outTypeTbody">
                    <!-- for loop  -->
                    </tbody>
                </table>
            </div>
        </div>


    </div>
</div>

<jsp:include page="inc/js.jsp"></jsp:include>
<script src="js/checkOutActions.js"></script>
</body>

</html>