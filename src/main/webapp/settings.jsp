<%@ page import="entities.Admin" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="util" class="utils.Util"></jsp:useBean>
<%util.isLogin(request, response);%>
<%
    Admin admin = new Admin();
    admin = util.getXAdmin(request);
%>
<!DOCTYPE html>
<head>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Remember</title>
</head>
<body>

<div class="container">

    <div class="row">
        <div style="text-align:center" class="md-3 mt-5">

            <img src="gifs/remember.gif">
        </div>
        <div class="col-md-4"></div>
        <div class=" col-md-4 mt-5">
            <%
                if (admin != null) {
            %>
            <form action="settings-post?ad_id=<%=admin.getAd_id()%>" method="post">

                <div class="mb-3">
                    <input type="email" value="<%=admin.getAd_email()%>" class="form-control" name="ad_email">
                </div>
                <div class="mb-3">
                    <input type="text" value="<%=admin.getAd_name()%>" class="form-control" name="ad_name"
                           placeholder="Name"
                           required>
                </div>
                <div class="mb-3">
                    <input type="text" value="<%=admin.getAd_surname()%>" class="form-control" name="ad_surname"
                           placeholder="Surname"
                           required>
                </div>

                <div class="mb-3">
                    <input type="password" class="form-control" name="newPassword" placeholder="New Password"
                           required>
                </div>

                <div class="mb-3">
                    <input type="password" class="form-control" name="newPasswordr" placeholder="New Password Repeat"
                           required>
                </div>

                <input class="btn btn-primary" type="submit" value="Change">
                <%
                    String feedback = (String) request.getAttribute("settingFeedback");
                    System.out.println(feedback);
                    if (feedback != null) {
                %>
                <div class="invalid-feedback" style="display: block"><%=feedback%>
                </div>
            </form>
            <% }
            }
            %>
        </div>

    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
        crossorigin="anonymous"></script>
<script src="js/remember.js"></script>
</body>
</html>
