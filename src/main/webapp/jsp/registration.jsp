<%@ page import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ page import="com.matsak.exhibitionhall.db.entity.Exposition" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="eh" uri="/WEB-INF/customtags.tld" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="bootstrap/dist/css/bootstrap.css" rel="stylesheet">
    <link href="styles/style.css" rel="stylesheet">
    <title>Title</title>
</head>
<body>
<tf:header></tf:header>
<div class="container-fluid" style="background-color: #eee;">
    <div class="row d-flex justify-content-center align-items-center">
        <div class="col-lg-12 col-xl-11">
            <div class="card text-black" style="border-radius: 25px;">
                <div class="card-body p-md-5">
                    <div class="row justify-content-center">
                        <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

                            <p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>

                            <form class="mx-1 mx-md-4" method="POST" action="register/check">

                                <div class="d-flex flex-row align-items-center mb-4">
                                    <i class="fas fa-user fa-lg me-3 fa-fw"></i>
                                    <div class="form-outline flex-fill mb-0">
                                        <input type="text" id="form3Example1c" class="form-control<c:if test="${loginFieldError != null || loginFieldDBError != null}"> is-invalid</c:if>" value="${loginFieldValue}" name="userLogin"/>
                                        <label class="form-label" for="form3Example1c">Your Login</label>
                                        <c:choose>
                                            <c:when test="${loginFieldError != null && loginFieldError == true}">
                                                <div id="validationLoginLabel" class="invalid-feedback">
                                                    Please enter login from 4 to 20 symbols
                                                </div>
                                            </c:when>
                                            <c:when test="${loginFieldDBError != null && loginFieldDBError == true}">
                                                <div id="validationLoginLabel" class="invalid-feedback">
                                                    This login already exists. Try another one
                                                </div>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>

                                <div class="d-flex flex-row align-items-center mb-4">
                                    <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                    <div class="form-outline flex-fill mb-0">
                                        <input type="email" id="form3Example3c" class="form-control<c:if test="${emailFieldError != null || emailFieldDBError != null}"> is-invalid</c:if>" value="${emailFieldValue}" name="email"/>
                                        <label class="form-label" for="form3Example3c">Your Email</label>
                                        <c:choose>
                                            <c:when test="${emailFieldError != null && emailFieldError == true}">
                                                <div id="validationEmailLabel" class="invalid-feedback">
                                                    Please enter correct email
                                                </div>
                                            </c:when>
                                            <c:when test="${emailFieldDBError != null && emailFieldDBError == true}">
                                                <div id="validationEmailLabel" class="invalid-feedback">
                                                    This email already exists. Try another one
                                                </div>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>

                                <div class="d-flex flex-row align-items-center mb-4">
                                    <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                    <div class="form-outline flex-fill mb-0">
                                        <input type="text" id="phone" class="form-control<c:if test="${phoneFieldError != null || phoneFieldDBError != null}"> is-invalid</c:if>" value="${phoneFieldValue}" name="phone"/>
                                        <label class="form-label" for="phone">Phone</label>
                                        <c:choose>
                                            <c:when test="${phoneFieldError != null && phoneFieldError == true}">
                                                <div id="validationPhoneLabel" class="invalid-feedback">
                                                    Сorrect phone number: 380550005555
                                                </div>
                                            </c:when>
                                            <c:when test="${phoneFieldDBError != null && phoneFieldDBError == true}">
                                                <div id="validationPhoneLabel" class="invalid-feedback">
                                                    This phone already exists. Try another one
                                                </div>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                </div>

                                <div class="d-flex flex-row align-items-center mb-4">
                                    <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                    <div class="form-outline flex-fill mb-0">
                                        <input type="text" id="firstName" class="form-control<c:if test="${firstNameFieldError != null}"> is-invalid</c:if>" value="${firstNameFieldValue}" name="firstName"/>
                                        <label class="form-label" for="firstName">First Name</label>
                                        <c:if test="${firstNameFieldError != null && firstNameFieldValue == true}">
                                            <div id="validationLastNameLabel" class="invalid-feedback">
                                                This field cannot be shorter than 2 symbols
                                            </div>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="d-flex flex-row align-items-center mb-4">
                                    <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                    <div class="form-outline flex-fill mb-0">
                                        <input type="text" id="lastName" class="form-control<c:if test="${lastNameFieldError != null}"> is-invalid</c:if>" value="${lastNameFieldValue}" name="lastName"/>
                                        <label class="form-label" for="lastName">Last Name</label>
                                        <c:if test="${lastNameFieldError != null && lastNameFieldError == true}">
                                        <div id="validationLastNameLabel" class="invalid-feedback">
                                            This field cannot be shorter than 2 symbols
                                        </div>
                                        </c:if>

                                    </div>
                                </div>

                                <div class="d-flex flex-row align-items-center mb-4">
                                    <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                                    <div class="form-outline flex-fill mb-0">
                                        <input type="password" id="password" class="form-control<c:if test="${passwordFieldError != null}"> is-invalid</c:if>" name="userPassword"/>
                                        <label class="form-label" for="password">Password</label>
                                        <c:if test="${passwordFieldError != null && passwordFieldError == true}">
                                            <div id="validationPasswordLabel" class="invalid-feedback">
                                                Passwords must be the same. Password has to be 6+ symbols
                                            </div>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="d-flex flex-row align-items-center mb-4">
                                    <i class="fas fa-key fa-lg me-3 fa-fw"></i>
                                    <div class="form-outline flex-fill mb-0">
                                        <input type="password" id="repeatedPassword" class="form-control<c:if test="${passwordFieldError != null}"> is-invalid</c:if>" name="repeatedPassword"/>
                                        <label class="form-label" for="repeatedPassword">Repeat your password</label>
                                        <c:if test="${passwordFieldError != null && passwordFieldError == true}">
                                            <div id="validationPasswordLabel" class="invalid-feedback">
                                                Passwords must be the same. Password has to be 6+ symbols
                                            </div>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="form-check d-flex justify-content-center mb-5">
                                    <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3c"/>
                                    <label class="form-check-label" for="form2Example3c">
                                        I agree all statements in <a href="#!">Terms of service</a>
                                    </label>
                                </div>

                                <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                    <button type="submit" class="btn btn-primary btn-lg">Register</button>
                                </div>

                            </form>

                        </div>
                        <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                            <img src="https://zakarpat.brovdi.art/media/zoo/images/17_16.08.17._galay_s._17_ae13c6fa53d593b7dc2f1e7398148596.jpg"
                                 class="img-fluid" alt="Sample image" style="width: 100%">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

<c:remove var="loginFieldError"/>
<c:remove var="emailFieldError"/>
<c:remove var="firstNameFieldError"/>
<c:remove var="lastNameFieldError"/>
<c:remove var="passwordFieldError"/>
<c:remove var="phoneFieldError"/>

<c:remove var="loginFieldDBError"/>
<c:remove var="emailFieldDBError"/>
<c:remove var="phoneFieldDBError"/>

<c:remove var="loginFieldValue"/>
<c:remove var="emailFieldValue"/>
<c:remove var="firstNameFieldValue"/>
<c:remove var="lastNameFieldValue"/>
<c:remove var="phoneFieldValue"/>



