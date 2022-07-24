<%@ tag import="java.time.LocalDateTime" %>
<%@ tag import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="eh" uri="/WEB-INF/customtags.tld" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:setBundle basename="settings" />

<%
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime dateTimeNow = LocalDateTime.now();
    LocalDateTime futureLimitDate = dateTimeNow.plusYears(3);
    String currentDate = dateTimeNow.format(timeFormatter);
    String futureLimit = futureLimitDate.format(timeFormatter);
%>

<form action="" method="get">

    <div class="settingsBox d-flex">
        <div class="sortingRadioButtons">
            <div class="btn-group" role="group" aria-label="Choose sorting type radio group">
                <input type="submit" class="btn-check active" name="sorting" value="date" id="sortingRadio1"
                       autocomplete="off" aria-pressed="true">
                <label class="btn
                    <% if (request.getParameter("sorting") == null || request.getParameter("sorting").equals("date")) out.print("btn-dark");
                    else out.print("btn-outline-dark");%> " for="sortingRadio1"><fmt:message key="date"/></label>

                <input type="submit" class="btn-check" name="sorting" value="price" id="sortingRadio2"
                       autocomplete="off">
                <label class="btn <% if ("price".equals(request.getParameter("sorting"))) out.print("btn-dark");
                    else out.print("btn-outline-dark");%>" for="sortingRadio2"><fmt:message key="price"/></label>

                <input type="submit" class="btn-check" name="sorting" value="popularity" id="sortingRadio3"
                       autocomplete="off">
                <label class="btn <% if ("popularity".equals(request.getParameter("sorting"))) out.print("btn-dark");
                    else out.print("btn-outline-dark");%>" for="sortingRadio3"><fmt:message key="popularity"/></label>
            </div>
        </div>
        <div class="searchInput input-group mb-3">
            <input type="text" class="form-control" placeholder="<fmt:message key="searchMessage"/>"
            <c:choose>
            <c:when test="${searchFilter != null}">
                   value="${searchFilter}"
            </c:when>
            <c:otherwise>
                   value=""
            </c:otherwise>
            </c:choose>
                   aria-label="Recipient's username" aria-describedby="button-addon2" name="search">
            <button class="btn btn-outline-secondary" type="submit" id="button-addon2"><fmt:message key="searchLabel"/></button>
        </div>
        <div class="filtersBox accordion" id="accordionExample">
            <div class="accordion-item">
                <h2 class="accordion-header" id="headingOne">
                    <button class="accordion-button<%
                    if (request.getSession().getAttribute("startDateFilter") == null &&
                    request.getSession().getAttribute("finalDateFilter") == null &&
                    request.getSession().getAttribute("themesFilter") == null)
                        out.println(" collapsed");%>"
                            type="button" data-bs-toggle="collapse"
                            data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                        <fmt:message key="filters"/>
                    </button>
                </h2>
                <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne"
                     data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" id="startDateCheckbox"
                            <c:if test="${startDateFilter != null}">
                                   checked
                            </c:if>
                            >
                            <label for="startDate"><fmt:message key="startDate"/></label>

                            <input type="date" id="startDate" name="startDate"
                            <c:choose>
                            <c:when test="${startDateFilter != null}">
                                   value="${startDateFilter}"
                            </c:when>
                            <c:otherwise>
                                   value="" disabled
                            </c:otherwise>
                            </c:choose>
                                   min="<%=currentDate%>" max="<%=futureLimit%>">
                        </div>
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" id="endDateCheckbox"
                            <c:if test="${finalDateFilter != null}">
                                   checked
                            </c:if>
                            >
                            <label for="endDate"><fmt:message key="endDate"/></label>

                            <input type="date" id="endDate" name="endDate"
                            <c:choose>
                            <c:when test="${finalDateFilter != null}">
                                   value="${finalDateFilter}"
                            </c:when>
                            <c:otherwise>
                                   value="" disabled
                            </c:otherwise>
                            </c:choose>
                                   min="<%=currentDate%>" max="<%=futureLimit%>">
                        </div>
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" id="themesCheckbox"
                            <c:if test="${themesFilter != null}">
                                   checked
                            </c:if>
                            >
                            <label for="themesList"><fmt:message key="selectThemes"/></label>
                        </div>
                        <select class="form-select" name="themesList" id="themesList" multiple size="5"
                                aria-label="size 5 select example"
                                <c:if test="${themesFilter == null}">
                                    disabled
                                </c:if>
                        >
                            <eh:ThemesList/>
                        </select>
                        <button class="btn btn-outline-secondary" type="submit" id="acceptFiltersButton"><fmt:message key="acceptFilters"/>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
