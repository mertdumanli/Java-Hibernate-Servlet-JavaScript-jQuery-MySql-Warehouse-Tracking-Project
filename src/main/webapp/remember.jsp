<%@ page import="entities.Admin" %>
<%@ page import="utils.Util" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<head>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Remember</title>
</head>
<body>
<%
    Admin admin = (Admin) request.getAttribute("rememberAdminInfos");
    if (admin != null) {
%>

<div class="container">

    <div class="row">
        <div style="text-align:center" class="md-3 mt-5">

            <img src="gifs/remember.gif">
        </div>
        <div class="col-md-4"></div>
        <div class=" col-md-4">


            <form id="changePassword" method="post">
                <div class="mb-3">
                    <input type="hidden" value="<%=admin.getAd_id()%>" class="form-control" id="ad_id">
                </div>

                <div class="mb-3">
                    <input type="password" class="form-control" id="newPassword" placeholder="New Password"
                           required>
                </div>

                <div class="mb-3">
                    <input type="password" class="form-control" id="newPasswordr" placeholder="New Password Repeat"
                           required>
                </div>
                <input class="btn btn-primary" type="submit" value="Change">
            </form>
        </div>

    </div>
</div>

<%
    } else {
        response.sendRedirect(Util.base_url);
    }
%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-U1DAWAznBHeqEIlVSCgzq+c9gqGAJn5c/t99JyeKa9xxaYpSvHU5awsuZVVFIhvj"
        crossorigin="anonymous"></script>
<script src="js/remember.js"></script>
</body>
</html>
