<%@ page import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ page import="com.matsak.exhibitionhall.db.entity.Exposition" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="eh" uri="/WEB-INF/customtags.tld" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <link href="../bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <link href="../styles/style.css" rel="stylesheet">
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

        <div class="accordion userTicketsContainer" id="userTicketsContainer">
            <c:forEach var="ticket" items="${previousTickets.keySet()}">
                <div class="accordion-item">
                    <h2 class="accordion-header" id="${ticket.getNum()}">
                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapse${ticket.getNum()}" aria-expanded="false" aria-controls="collapse${ticket.getNum()}">
                                Ticket ${ticket.getNum()} (${previousTickets.get(ticket).getExpName()})
                        </button>
                    </h2>
                    <div id="collapse${ticket.getNum()}" class="accordion-collapse collapse" aria-labelledby="heading${ticket.getNum()}" data-bs-parent="#accordionExample">
                        <div class="accordion-body d-flex">
                           ${ticket.getNum()}
                           ${ticket.getOrder_date()}
                           ${ticket.getExposition_id()}
                        </div>
                    </div>
                </div>
            </c:forEach>
<%--            <div class="accordion-item">--%>
<%--                <h2 class="accordion-header" id="headingThree">--%>
<%--                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">--%>
<%--                        Accordion Item #3--%>
<%--                    </button>--%>
<%--                </h2>--%>
<%--                <div id="collapseThree" class="accordion-collapse collapse" aria-labelledby="headingThree" data-bs-parent="#accordionExample">--%>
<%--                    <div class="accordion-body">--%>
<%--                        <strong>This is the third item's accordion body.</strong> It is hidden by default, until the collapse plugin adds the appropriate classes that we use to style each element. These classes control the overall appearance, as well as the showing and hiding via CSS transitions. You can modify any of this with custom CSS or overriding our default variables. It's also worth noting that just about any HTML can go within the <code>.accordion-body</code>, though the transition does limit overflow.--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--            </div>--%>
        </div>
    </div>
</div>
<script src="../https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="../bootstrap/dist/js/bootstrap.bundle.js" charset="utf-8"></script>
<script src="../scripts/toast.js" charset="utf-8"></script>
<script src="../scripts/order.js" charset="utf-8"></script>
</body>
</html>


