<%@ page import="utils.Util" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>404 Error</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="dist/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/fontawesome-free-5.15.4-web/css/all.min.css">
</head>
<body style="background-color: black">

<div class="container">
    <div class="row mt-3" style="text-align: center">

        <div class="col-md-3">
        </div>
        <div class="col-md-6">
            <img src="gifs/error404.gif">
        </div>
        <div class="col-md-3">
        </div>
        <div class="col-md-3"></div>
        <div class="col-md-6 mt-3">
            <div class="card" style="background-color: #0dcaf0">
                <div class="card-body" style="color: white">
                    <h5 class="card-title">Warning</h5>
                    <p class="card-text">You seem to have lost your way. I can point you to the entrance.</p>
                    <a href="<%=Util.base_url%>" class="btn btn-warning">GO</a>
                </div>
            </div>
        </div>
        <div class="col-md-3"></div>

    </div>
</div>
<script src="js/jquery-3.6.0.min.js"></script>
<script src="dist/js/bootstrap.min.js"></script>
</body>
</html>
