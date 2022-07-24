<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <link href="bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <link href="styles/style.css" rel="stylesheet">
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Registration success</title>
</head>
<body>
<tf:header></tf:header>
<div class="container-fluid col-md-12 justify-content-center" style="background: #ffe484">
    <div class="d-flex justify-content-center widthContainer text-center">
        <div class="textBlock" style="margin-top: 15%; font-size: xxx-large; font-weight: bold; text-shadow: white 0 0 10px">
        Registration successfully completed.
            <div style="font-size: x-large; font-weight: inherit; text-shadow: white 0 0 10px">
                You will be redirected to the main page in
            </div>
            <div class="textBlock" id="counter" style="font-size: xxx-large; ">5</div>
        </div>
    </div>
</div>
</body>
<script>
    let counter = document.getElementById("counter");
    let step = 1;
    let protocol = window.location.protocol;
    let path = window.location.pathname.substring(0, window.location.pathname.lastIndexOf('/'));
    let host = window.location.host;
    window.onload = setInterval(function () {
        let value = +counter.textContent;
        if (value === 0) {
            window.location.href = path + "/main";
            return;
        }
        else {
            value -= step;
            counter.textContent = value.toString();
        }
    }, 1000)
</script>
</html>
