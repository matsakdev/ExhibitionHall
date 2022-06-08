<%@ page import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ page import="com.matsak.exhibitionhall.db.entity.Exposition" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="com.matsak.exhibitionhall.db.entity.Showroom" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="eh" uri="/WEB-INF/customtags.tld" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Exposition exposition = (Exposition) request.getAttribute("exposition");
    List<Showroom> showroomsList = (List<Showroom>) request.getSession().getAttribute("showrooms");
%>

<% LocalDate dateNow = LocalDate.now();
    LocalTime timeNow = LocalDateTime.now().toLocalTime();
    DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter formattedTime = DateTimeFormatter.ofPattern("HH:mm");
    Timestamp settedStartTime = exposition.getExpStartDate();
    Timestamp settedEndTime = exposition.getExpFinalDate();
%>

<html>
<head>
    <%--    <link href="bootstrap/dist/css/bootstrap.css" rel="stylesheet">--%>


    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/bootstrap/dist/css/bootstrap.css" TYPE="text/css">


    <%--    <link href="styles/style.css" rel="stylesheet">--%>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Exhibition hall</title>
</head>
<body>
<tf:header></tf:header>

<div class="container-fluid col-md-12 d-flex flex-column">
    <form action="${sessionScope.get("currentPath")}/update" method="post" enctype="multipart/form-data">
        <div class="d-flex">

            <div class="imageHolder col-md-5">
                <div class="uploadBox">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-upload"
                         viewBox="0 0 16 16">
                        <path d="M.5 9.9a.5.5 0 0 1 .5.5v2.5a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1v-2.5a.5.5 0 0 1 1 0v2.5a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2v-2.5a.5.5 0 0 1 .5-.5z"/>
                        <path d="M7.646 1.146a.5.5 0 0 1 .708 0l3 3a.5.5 0 0 1-.708.708L8.5 2.707V11.5a.5.5 0 0 1-1 0V2.707L5.354 4.854a.5.5 0 1 1-.708-.708l3-3z"/>
                    </svg>
                    <div class="mb-3">
                        <label for="uploadImageInput" class="col-form-label-sm">Upload the image</label>
                        <input class="form-control" type="file" name="file" accept="image/jpeg,image/png"
                               id="uploadImageInput">
                    </div>
                </div>
            </div>
            <div class="infoInputs col-md-5">
                <label for="expositionName" class="form-label">Exposition Title:</label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" id="expositionName" aria-describedby="expositionNameLabel"
                           placeholder="Title" name="expTitle" value="${exposition.getExpName()}">
                </div>

                <label for="expositionAuthor" class="form-label">Author of the Exposition</label>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" id="expositionAuthor" aria-describedby="expositionAuthor"
                           placeholder="Author" name="expAuthor" value="${exposition.getAuthor()}">
                </div>

                <div class="d-block">
                    <div class="datePicker">
                        <label for="startDate" class="dateLabel">Exposition opening date</label>
                        <input class="dateInput" type="date" id="startDate" name="trip-start" placeholder="Start date"
                            <%
                           LocalDate startDate = settedStartTime.toLocalDateTime().toLocalDate();
                           out.print("value='" + startDate.format(formattedDate) + "'");
                           if (dateNow.compareTo(startDate) >= 0) {
                               out.print(" readonly ");
                               out.print("min='" + startDate.format(formattedDate) + "'");
                               out.print(" max='" + startDate.format(formattedDate) + "'");
                           }
                           else {
                               out.print("min='min_date'");
                           }
                           %>
                        >
                    </div>

                    <div class="datePicker">
                        <small>Exposition opens at</small>
                        <input type="time" id="startTime" name="appt"
                            <%
                           LocalTime startTime = settedStartTime.toLocalDateTime().toLocalTime();
                           out.print("value='" + startTime.format(formattedTime) + "'");
                           if (dateNow.compareTo(startDate) >= 0 && timeNow.compareTo(startTime) > 0) {
                               out.print(" readonly ");
                               out.print("min='" + startTime.format(formattedTime) + "'");
                               out.print(" max='" + startTime.format(formattedTime) + "'");
                           }
                           else {
                               out.print("min='min_time'");
                           }
                           %>
                               required>
                    </div>
                </div>
                <div class="d-block">
                    <div class="datePicker">
                        <label for="endDate" class="dateLabel">Exposition closing date</label>
                        <input class="dateInput" type="date" id="endDate" name="trip-start" placeholder="End date"
                            <%
                           LocalDate endDate = settedEndTime.toLocalDateTime().toLocalDate();
                           out.print("value='" + endDate.format(formattedDate) + "'");
                           if (dateNow.compareTo(endDate) >= 0) {
                               out.print(" readonly ");
                               out.print("min='" + endDate.format(formattedDate) + "'");
                               out.print(" max='" + endDate.format(formattedDate) + "' ");
                           }
                           else {
                               out.print("min='min_date'");
                           }
                           %>
                        >
                    </div>
                    <div class="datePicker">
                        <small>Exposition closes at</small>
                        <input type="time" id="endTime" name="appt"
                            <%
                           LocalTime endTime = settedEndTime.toLocalDateTime().toLocalTime();
                           out.print("value='" + endTime.format(formattedTime) + "'");
                           if (dateNow.compareTo(endDate) >= 0 && timeNow.compareTo(endTime) > 0) {
                               out.print(" readonly ");
                               out.print("min='" + endTime.format(formattedTime) + "'");
                               out.print(" max='" + endTime.format(formattedTime) + "' ");
                           }
                           else {
                               out.print("min='min_time'");
                           }
                           %>
                               required>
                    </div>
                </div>

            </div>
        </div>
        <div class="d-flex">
            <div class="roomsList col-md-5">
                <div class="list-group">
                    <c:forEach var="showroom" items="${showroomsList}">

                        <label class="list-group-item">
                            <input class="form-check-input me-1" type="checkbox" value="">
                            <c:out value="${showroom.getSrName()} (${showroom.getArea()} sq. m)"/>
                        </label>

                    </c:forEach>
                </div>
            </div>

            <div class="descriptionArea col-md-5">
                <div class="input-group">
                    <span class="input-group-text">Description</span>
                    <textarea class="form-control" aria-label="Description"></textarea>
                </div>
            </div>
        </div>
        <input type="submit" value="Upload"/>
    </form>

</div>
</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/scripts/expositionEdit.js"></script>
</html>