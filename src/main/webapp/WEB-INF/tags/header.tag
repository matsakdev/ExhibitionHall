<%--libs--%>
<%@ tag import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ tag import="java.util.ResourceBundle" %>
<%@ tag import="java.util.Locale" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--lang settings--%>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="header"/>
<%ResourceBundle header = ResourceBundle.getBundle("header", new Locale(request.getSession().getAttribute("lang").toString()));%>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 navButtons">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" id="profileLink" href="<%=request.getContextPath()%>/main"><fmt:message key="home"/></a>
                    </li>
                    <%if ("administrator".equals(request.getSession().getAttribute("userRole"))) out.println("<li class=\"nav-item profileButton\">\n" +
                            "                        <a class=\"nav-link\" href='" + request.getContextPath() + "/admin'>" + header.getString("admin") + "</a>\n" +
                            "                    </li>");
                    else out.println("<li class=\"nav-item profileButton\">\n" +
                                "                        <a class=\"nav-link\" id='profile' " +
                                ">" + header.getString("profile") + "</a>\n" +
                                "                    </li>");%>
                </ul>
                <form action="<%=request.getContextPath()%>/language" method="post">
                    <div class="languageRadioButton">
                        <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                            <input type="radio" onchange='this.form.submit();' class="btn-check" name="lang" id="btnradio1" value="uk" autocomplete="off" <%if (request.getSession().getAttribute("lang").equals("uk")) out.print("checked");%>>
                            <label class="btn btn-outline-primary" for="btnradio1">UKR</label>

                            <input type="radio" onchange='this.form.submit();' class="btn-check" name="lang" id="btnradio3" value="en" autocomplete="off" <%if (request.getSession().getAttribute("lang").equals("en")) out.print("checked");%>
                            <label class="btn btn-outline-primary" for="btnradio3">ENG</label>
                        </div>
                    </div>
                </form>
                <c:choose>
                <c:when test="${sessionScope.currentUser != null}">
                    <form action="<%=request.getContextPath()%>/logout" onsubmit="return confirm('Do you really want to logout?');" class="d-block">
                        <button type="submit"
                                class="btn btn-outline-success me-2 loginButton" data-bs-toggle="modalLogout"
                                data-bs-target="#logoutModal" id="modalButton">
                                ${sessionScope.currentUser.getUserLogin()}
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-door-open" viewBox="0 0 16 16">
                                <path d="M8.5 10c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z"/>
                                <path d="M10.828.122A.5.5 0 0 1 11 .5V1h.5A1.5 1.5 0 0 1 13 2.5V15h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117zM11.5 2H11v13h1V2.5a.5.5 0 0 0-.5-.5zM4 1.934V15h6V1.077l-6 .857z"/>
                            </svg>
                        </button>
                    </form>
                </c:when>
                    <c:otherwise>
                <button type="button" class="btn btn-outline-success me-2 loginButton" data-bs-toggle="modal" data-bs-target="#loginModal" id="modalButton">
                    <fmt:message key="login"/>/<fmt:message key="register"/>
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                         class="bi bi-door-open" viewBox="0 0 16 16">
                        <path d="M8.5 10c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z"/>
                        <path d="M10.828.122A.5.5 0 0 1 11 .5V1h.5A1.5 1.5 0 0 1 13 2.5V15h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117zM11.5 2H11v13h1V2.5a.5.5 0 0 0-.5-.5zM4 1.934V15h6V1.077l-6 .857z"/>
                    </svg>
                </button>
                        <div class="modal fade" id="loginModal" tabindex="-1" aria-labelledby="ModalLabel" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel"><fmt:message key="login"/>/<fmt:message key="register"/></h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <%
                                        if (request.getSession().getAttribute("loginError") != null) {
                                            if (request.getSession().getAttribute("loginError").equals("Incorrect info"))
                                                out.println("<div class='alert alert-danger' " +
                                                        "id='alertMessage' style='margin-top: 20px; transition-duration: 0.8s;'>" + header.getString("loginMessage") + "</div>\n");
                                            else if (request.getSession().getAttribute("loginError").equals("Auth needed"))
                                                out.println("<div class='alert alert-primary' " +
                                                        "id='alertMessage' style='margin-top: 20px; transition-duration: 0.8s;'>" + header.getString("authorizeMessage") + "</div>\n");
                                        }
                                    %>
                                    <div class="modal-body">
                                        <form action="login" method="post">
                                            <input type="hidden" name="command" value="login"><br>
                                            <div class="col-md-12">
                                                <label for="validationCustomUsername" class="form-label"><fmt:message key="login"/></label>
                                                <div class="input-group">
                                                    <input type="text" placeholder="<fmt:message key="loginAndEmailLabel"/>" class="form-control"
                                                           id="validationCustomUsername" name="login"
                                                           aria-describedby="inputGroupPrepend">
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <label for="validationCustomUsername" class="form-label"><fmt:message key="password"/></label>
                                                <div class="input-group">
                                                    <input type="password" placeholder="Password" class="form-control"
                                                           name="password" id="j" aria-describedby="inputGroupPrepend">
                                                </div>
                                            </div>
                                            <br>
                                            <input type="submit" class="btn btn-outline-warning me-2 loginText" value="<fmt:message key="login"/>">
                                            <hr>
                                        </form>
                                        <h5><fmt:message key="or"/></h5>
                                        <form action="<%=request.getContextPath()%>/register">
                                            <button class="btn btn-outline-success me-2" type="submit" data-toggle="modal"
                                                    data-target="#loginModalForm"><fmt:message key="register"/>
                                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                                     fill="currentColor"
                                                     class="bi bi-door-open" viewBox="0 0 16 16">
                                                    <path d="M8.5 10c-.276 0-.5-.448-.5-1s.224-1 .5-1 .5.448.5 1-.224 1-.5 1z"/>
                                                    <path d="M10.828.122A.5.5 0 0 1 11 .5V1h.5A1.5 1.5 0 0 1 13 2.5V15h1.5a.5.5 0 0 1 0 1h-13a.5.5 0 0 1 0-1H3V1.5a.5.5 0 0 1 .43-.495l7-1a.5.5 0 0 1 .398.117zM11.5 2H11v13h1V2.5a.5.5 0 0 0-.5-.5zM4 1.934V15h6V1.077l-6 .857z"/>
                                                </svg>
                                            </button>
                                        </form>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal"><fmt:message key="close"/></button>
                                        <button type="button" class="btn btn-primary"><fmt:message key="saveChanges"/></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
                <%if (request.getSession().getAttribute("loginError") != null) out.println("<script>" +
                        "function load() {\n" +
                        "        document.getElementById(\"modalButton\").click();" +
                        "setTimeout(() => {document.getElementById(\"alertMessage\").parentElement.removeChild(document.getElementById(\"alertMessage\"))}, 5000)\n}\n" +
                        "      window.onload = load;</script>");
                request.getSession().setAttribute("loginError", null);%>
            </div>
        </div>
    </nav>
</header>
