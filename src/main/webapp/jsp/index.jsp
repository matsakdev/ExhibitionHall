<%@ page import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="eh" uri="/WEB-INF/customtags.tld" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <link href="bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <link href="styles/style.css" rel="stylesheet">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Exhibition hall</title>
</head>
<body>
<tf:header></tf:header>
<div class="bg-dark">
    <div class="container-fluid col-md-10 bg-primary widthContainer">
        <div class="sortingRadioButtons">
            <form action="" method="get">
                <div class="btn-group" role="group" aria-label="Choose sorting type radio group">
                    <input type="submit" class="btn-check active" name="sorting" value="date" id="sortingRadio1" autocomplete="off" aria-pressed="true">
                    <label class="btn
                    <% if (request.getParameter("sorting") == null || request.getParameter("sorting").equals("date")) out.print("btn-dark");
                    else out.print("btn-outline-dark");%> " for="sortingRadio1">Date</label>

                    <input type="submit" class="btn-check" name="sorting" value="price" id="sortingRadio2" autocomplete="off">
                    <label class="btn <% if ("price".equals(request.getParameter("sorting"))) out.print("btn-dark");
                    else out.print("btn-outline-dark");%>" for="sortingRadio2">Price</label>

                    <input type="submit" class="btn-check" name="sorting" value="popularity" id="sortingRadio3" autocomplete="off">
                    <label class="btn <% if ("popularity".equals(request.getParameter("sorting"))) out.print("btn-dark");
                    else out.print("btn-outline-dark");%>" for="sortingRadio3">Popularity</label>
                </div>
            </form>
        </div>
        <div class="items position-relative">
            <eh:CardsList/>
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
                    <li class="page-item"><a class="page-link" href="#">1</a></li>
                    <li class="page-item"><a class="page-link" href="#">2</a></li>
                    <li class="page-item"><a class="page-link" href="#">3</a></li>
                    <li class="page-item"><a class="page-link" href="#">Next</a></li>
                </ul>
            </nav>
        </div>
        <button type="button" class="btn btn-primary" id="liveToastBtn">Show live toast</button>

        <div class="toast-container position-fixed bottom-0 end-0 p-3">
            <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                <div class="toast-header">
                    <img src="..." class="rounded me-2" alt="...">
                    <strong class="me-auto">Bootstrap</strong>
                    <small>11 mins ago</small>
                    <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                </div>
                <div class="toast-body">
                    Hello, world! This is a toast message.
                </div>
            </div>
        </div>
    </div>

</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="bootstrap/dist/js/bootstrap.bundle.js"></script>
<script src="scripts/toast.js"></script>
</body>
</html>


