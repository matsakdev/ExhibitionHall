<%@ page import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ page import="com.matsak.exhibitionhall.db.entity.Exposition" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="eh" uri="/WEB-INF/customtags.tld" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="profile"/>
<%
    Map<Exposition, Integer> exhibitions = null;
    Object exhibitionsAttribute = request.getSession().getAttribute("currentPurchase");
    if (exhibitionsAttribute != null) {
        exhibitions = (Map<Exposition, Integer>)exhibitionsAttribute;
    }
%>
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
<%if (request.getSession().getAttribute("redirectedForJson") != null) {
    if ((boolean) request.getSession().getAttribute("redirectedForJson") != false) {
        out.println("<script src=\"scripts/buttonsHandler.js\" charset=\"utf-8\"></script>\n" +
                "<script>document.getElementById(\"profile\").click()</script>");
        request.getSession().setAttribute("redirectedForJson", false);
    }
}%>
<div class="bg-dark">
    <div class="container-fluid col-md-10 bg-primary widthContainer">

    <form method="get" action="<%=request.getContextPath()%>/makeorder" onsubmit="return confirm('Do you really want to make the order?');">
    <div class='orderContainer'>
        <%
            if (exhibitions != null) {
                for (Exposition exp : exhibitions.keySet()) {
                    out.println(
                            "<div class=\"d-flex flex-row orderRow\">\n" +
                                    "<input type='hidden' name='ex_id' value='" + exp.getId() + "'>" +
                                    "        <div class=\"orderImage\" style=\"background-image: url('images/" + exp.getImage() + "')\">\n" +
                                    "            \n" +
                                    "        </div>\n" +
                                    "        <div class=\"orderSection orderTitle\">\n" +
                                    exp.getExpName() +
                                    "        </div>\n" +
                                    "        <div class=\"orderSection orderPrice\">\n" +
                                    exp.getPrice() + " UAH" +
                                    "        </div>\n" +
                                    "       <div class=\"orderSection orderCount\">\n" +
                                    " <input type='number' name='count' min='1' value='" + exhibitions.get(exp) + "'>" +
                                    "        </div>\n" +
                                    "<div class=\"orderSection orderSum\">\n" +
                                    exp.getPrice() * exhibitions.get(exp) +
                                    "    </div>" +
                                    "</div>");
                }
            }
        %>
    </div>

        <c:choose>
            <c:when test="${sessionScope.currentPurchase != null}">
    <div class="userInfo col-md-6">
        <div class="input-group emailRadioInput">
            <div class="input-group-text">
                <input checked class="form-check-input mt-0" id="oldEmailRadio" name="emailRadio" type="radio" value=<%="'" + ((User)request.getSession().getAttribute("currentUser")).getEmail() + "'"%> aria-label="Radio button for following text input">
                <label for="oldEmailRadio"> Use this email for receipt:</label>
            </div>
            <input type="text" class="form-control" aria-label="Text input with radio button" readonly value=<%="'" + ((User)request.getSession().getAttribute("currentUser")).getEmail() + "'"%>>
        </div>
        <div class="input-group emailRadioInput">
            <div class="input-group-text">
                <input class="form-check-input mt-0" id="newEmailRadio" name="emailRadio" type="radio" value="" aria-label="Radio button for following text input">
                <label for="oldEmailRadio"> Use new email</label>
            </div>
            <input type="email" class="form-control" id="newEmailInput" aria-describedby="emailHelp" placeholder="Enter email" readonly>
            <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
        </div>
    </div>
    <button type="submit">Make an order</button>
            </c:when>
            <c:otherwise>
                <div style="font-size: 25pt"><fmt:message key="clearBasketMessage"/></div>
                <a style="font-size: 25pt; color: #1f1701" href="<%=request.getContextPath()%>/main"><fmt:message key="visitMainPageMessage"/></a>
            </c:otherwise>
        </c:choose>
    </form>



<%--    <div class="d-flex flex-row">--%>
<%--        <div class="image" style="background-image: url()">--%>
<%--            --%>
<%--        </div>--%>
<%--        <div class="title">--%>
<%--            --%>
<%--        </div>--%>
<%--        <div class="price">--%>
<%--            --%>
<%--        </div>--%>
<%--    </div>--%>
</div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="bootstrap/dist/js/bootstrap.bundle.js" charset="utf-8"></script>
<script src="scripts/toast.js" charset="utf-8"></script>
<script src="scripts/order.js" charset="utf-8"></script>
</body>
</html>


