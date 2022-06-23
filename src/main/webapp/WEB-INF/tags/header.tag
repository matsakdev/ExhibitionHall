<%@ tag import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
<%--            <a class="navbar-brand" href="#">Navbar</a>--%>
<%--            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"--%>
<%--                    data-bs-target="#navbarSupportedContent"--%>
<%--                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">--%>
<%--                <span class="navbar-toggler-icon"></span>--%>
<%--            </button>--%>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0 navButtons">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" id="profileLink" href="<%=request.getContextPath()%>/main">Home</a>
                    </li>
                    <%if ("administrator".equals(request.getSession().getAttribute("userRole"))) out.println("<li class=\"nav-item\">\n" +
                            "                        <a class=\"nav-link\" href='\" + request.getContextPath() + \"/admin'>Admin tools</a>\n" +
                            "                    </li>");
                    else out.println("<li class=\"nav-item\">\n" +
                                "                        <a class=\"nav-link\" id='profile' " +
//                                "href='" + request.getContextPath() + "/profile'" +
                                ">Profile</a>\n" +
                                "                    </li>");%>
<%--                    <li class="nav-item">--%>
<%--                        <a class="nav-link" href="?command=admin">Link</a>--%>
<%--                    </li>--%>
<%--                    <li class="nav-item dropdown">--%>
<%--                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"--%>
<%--                           data-bs-toggle="dropdown" aria-expanded="false">--%>
<%--                            Dropdown--%>
<%--                        </a>--%>
<%--                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">--%>
<%--                            <li><a class="dropdown-item" href="#">Action</a></li>--%>
<%--                            <li><a class="dropdown-item" href="#">Another action</a></li>--%>
<%--                            <li>--%>
<%--                                <hr class="dropdown-divider">--%>
<%--                            </li>--%>
<%--                            <li><a class="dropdown-item" href="#">Something else here</a></li>--%>
<%--                        </ul>--%>
<%--                    </li>--%>
<%--                    <li class="nav-item">--%>
<%--                        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>--%>
<%--                    </li>--%>
                </ul>

                <div class="languageRadioButton">
                    <div class="btn-group" role="group" aria-label="Basic radio toggle button group">
                        <input type="radio" class="btn-check" name="btnradio" id="btnradio1" autocomplete="off" checked>
                        <label class="btn btn-outline-primary" for="btnradio1">UKR</label>

                        <input type="radio" class="btn-check" name="btnradio" id="btnradio3" autocomplete="off">
                        <label class="btn btn-outline-primary" for="btnradio3">ENG</label>
                    </div>
                </div>
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
<%--                <%if (request.getSession().getAttribute("currentUser") != null)--%>
<%--                    out.println("<div>You're logged as" + ((User)request.getSession().getAttribute("currentUser")).getUserLogin() + "</div>");%>--%>
                    <c:otherwise>
                <button type="button" class="btn btn-outline-success me-2 loginButton" data-bs-toggle="modal" data-bs-target="#loginModal" id="modalButton">
                    Login/Register
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
                                        <h5 class="modal-title" id="exampleModalLabel">Login/Register</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <%
                                        if (request.getSession().getAttribute("loginError") != null) {
                                            if (request.getSession().getAttribute("loginError").equals("Incorrect info"))
                                                out.println("<div class='alert alert-danger' " +
                                                        "id='alertMessage' style='margin-top: 20px; transition-duration: 0.8s;'>Login or password is incorrect</div>\n");
                                            else if (request.getSession().getAttribute("loginError").equals("Auth needed"))
                                                out.println("<div class='alert alert-primary' " +
                                                        "id='alertMessage' style='margin-top: 20px; transition-duration: 0.8s;'>You need to authorize</div>\n");
                                        }
                                    %>
                                    <div class="modal-body">
                                        <form action="login" method="post">
                                            <input type="hidden" name="command" value="login"><br>
                                            <div class="col-md-10">
                                                <label for="validationCustomUsername" class="form-label">Login</label>
                                                <div class="input-group">
                                                    <input type="text" placeholder="Login | Email" class="form-control"
                                                           id="validationCustomUsername" name="login"
                                                           aria-describedby="inputGroupPrepend">
                                                </div>
                                            </div>
                                            <div class="col-md-10">
                                                <label for="validationCustomUsername" class="form-label">Password</label>
                                                <div class="input-group">
                                                    <input type="text" placeholder="Password" class="form-control"
                                                           name="password" id="j" aria-describedby="inputGroupPrepend">
                                                </div>
                                            </div>
                                            <input type="submit" value="Login">
                                            <hr>
                                        </form>

                                        <h5>OR</h5>
                                        <form action="${pageContext.request.requestURL}register">
                                            <button class="btn btn-outline-success me-2" type="submit" data-toggle="modal"
                                                    data-target="#loginModalForm">Register
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
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                        <button type="button" class="btn btn-primary">Save changes</button>
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

<%--                <%if (request.getAttribute("loginError") != null) out.println("<script src='scripts/openModal.js'></script>");%>--%>
<%--                <%if (request.getAttribute("loginError") != null) out.println("<script>document.getElementById(\"modalButton\").click()</script>");%>--%>
            <%--            <c:remove var="loginError" scope="session"/>--%>
            </div>
        </div>
    </nav>
</header>
