<%@ page import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="eh" uri="/WEB-INF/customtags.tld" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%request.getSession().setAttribute("showroomInput", null);%>
<html>
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
<div class="d-flex align-items-center justify-content-center flex-column">
    <form action="admin/expositions/new">
        <button type="submit" class="btn btn-primary" style="justify-self: flex-end">Add new</button>
    </form>
    <div class="container-fluid col-md-12">
        <tf:adminTable></tf:adminTable>

    </div>
</div>
</body>
</html>
