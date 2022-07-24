<%@ page import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ page import="com.matsak.exhibitionhall.db.entity.Exposition" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="com.matsak.exhibitionhall.db.entity.Showroom" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="eh" uri="/WEB-INF/customtags.tld" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Exposition exhibition = (Exposition) request.getAttribute("currentExhibition");
    Set<Showroom> exhibitionShowrooms = (Set<Showroom>) request.getAttribute("currentExhibitionShowrooms");

%>

<html>
<head>


    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/dist/css/bootstrap.css" TYPE="text/css">

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Exhibition hall</title>
</head>
<body id="body" class="">
<tf:header></tf:header>

<div class="container-fluid col-md-12 d-flex flex-column">

    <div class="d-flex flex-row">

        <div class="imageHolder col-md-5" id="imageHolder"
                <% if (exhibition.getImage() != null) {
                    out.println("style=\"background-size: cover; background-image: url('../images/" + exhibition.getImage() + "')\"");
                } else {
                    out.println("style=\"background-size: cover; background-image: url('../images/clear.jpg')\"");
                }%> >
        </div>
        <div class="infoInputs col-md-5">

            <label for="expositionName" class="form-label">Exposition Title:</label>
            <div class="input-group mb-3">
                <h3 id="expositionName"><%=exhibition.getExpName()%>
                </h3>
            </div>
            <label for="expositionAuthor" class="form-label">Author of the Exposition</label>
            <div class="input-group mb-3">
                <h3 id="expositionAuthor"><%=exhibition.getAuthor()%>
                </h3>
            </div>

            <div class="d-block">
                <div class="datePicker">
                    <label for="startDate" class="dateLabel">Exposition opening date</label>
                    <h3 id="startDate"><%=exhibition.getExpStartDate()%>
                    </h3>
                </div>
            </div>
            <div class="d-block">
                <div class="datePicker">
                    <label for="endDate" class="dateLabel">Exposition closing date</label>
                    <h3 id="endDate"><%=exhibition.getExpFinalDate()%>
                    </h3>
                </div>
            </div>
            <div class="form-outline col-md-3 priceInput">
                <label class="form-label" for="priceInput">Price</label>
                <h3 id="priceInput"><%=exhibition.getPrice()%>
                </h3>
            </div>
        </div>
    </div>
    <div class="d-flex">
        <div class="roomsList col-md-5">
            <div class="list-group">
                <c:forEach var="showroom" items='${currentExhibitionShowrooms}'>
                    <label class="list-group-item">
                        <h3>
                            <c:out value="${showroom.getSrName()} (${showroom.getArea()} sq. m)"/>
                        </h3>
                    </label>
                </c:forEach>
            </div>
        </div>
        <div class="descriptionArea col-md-5">
            <div class="input-group">
                <span class="input-group-text">Description</span>
                <h3><%=exhibition.getDescription()%>
                </h3>
            </div>
        </div>
    </div>
    <div class="counterBlock d-flex flex-column">
        <div class="col-md-3">
            <input class="form-control ticketsCount" id="ticketsCount" style='font-size: 2rem;' type="number" min="1" step="1" value="1">
        </div>
        <button type="button" class="btn btn-success" id="buyButton" data-bs-toggle="modal"
                data-bs-target="#exampleModal" value="<%=exhibition.getId()%>">Buy</button>
    </div>

</div>
<%--<div className="overlay overlay-hide" id="overlay">--%>
<%--    <div className="half-visible" id="halfVisible">--%>

<%--    </div>--%>
<%--    <div className="container-md modal-container basket-container bg-white">--%>
<%--        <div className="container pb-3">--%>
<%--            <div className="row justify-content-between align-items-center py-1">--%>
<%--                <div className="col align-items-end">--%>
<%--                    <span className="h2">Кошик</span>--%>
<%--                </div>--%>
<%--                <div className="col p-3 text-end">--%>
<%--                    <button type="button" className="btn-close btn-outline-light" aria-label="Close"--%>
<%--                            id="closeModalButton"></button>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            &lt;%&ndash;                todo table&ndash;%&gt;--%>
<%--            <div className="text-end">--%>
<%--                <span className="d-block pb-3">Всього:  грн</span>--%>
<%--                <button className="btn btn-dark" to="/checkout">--%>
<%--                    Оформити--%>
<%--                    замовлення</button>--%>
<%--                }--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <table className="table align-middle text-center" id="modalTable">
                    <thead>
                    <tr>
                        <th scope="col">Title</th>
                        <th scope="col">Count</th>
                        <th scope="col">Summ</th>
                    </tr>
                    </thead>
                    <tbody id="tableBody">
<%--frontend js generates rows here--%>
                    </tbody>
                </table>
                <div class="clearBasketMessage" id="clearBasketMessage">
                    No items found in the bucket
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Order</button>
            </div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/exhibitionDetails.js"></script>
<script charset="utf-8" src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
</html>