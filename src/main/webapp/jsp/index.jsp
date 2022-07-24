<%@ page import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="eh" uri="/WEB-INF/customtags.tld" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="mainpage"/>

<html lang="${sessionScope.lang}">
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
        <tf:settings></tf:settings>

        <div class="items position-relative">
            <eh:CardsList/>
        </div>
        <nav aria-label="Page navigation example" class="paginationBox d-inline-block">
            <ul class="pagination">
                <li class="page-item"><a class="page-link" href="#"><fmt:message key="prev"/></a></li>
                <li class="page-item"><a class="page-link" href="?page=1">1</a></li>
                <li class="page-item"><a class="page-link" href="?page=2">2</a></li>
                <li class="page-item"><a class="page-link" href="?page=3">3</a></li>
                <li class="page-item"><a class="page-link" href="#"><fmt:message key="next"/></a></li>
            </ul>
        </nav>
    </div>

</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="bootstrap/dist/js/bootstrap.bundle.js"></script>
<script src="scripts/toast.js" charset="utf-8"></script>
<script src="scripts/buttonsHandler.js" charset="utf-8"></script>
<script src="scripts/settingsHandler.js" charset="utf-8"></script>
</body>
</html>


