<%@ tag import="java.time.LocalDateTime" %>
<%@ tag import="java.time.format.DateTimeFormatter" %>
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
<div class="filters d-flex">
    <form class="adminFilterForm" method="get">
        <div class="adminFilterCheckbox">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="showCompleted" value="true"
                       id="showPreviousExhibitions"
                <c:if test="${showCompleted == true}">
                       checked
                </c:if>
                >
                <label class="form-check-label" for="showPreviousExhibitions">
                    Show completed
                </label>
            </div>
        </div>
        <div class="adminFilterCheckbox">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" name="selectCurrent" value="true"
                       id="selectCurrentExhibitions"
                <c:if test="${selectCurrent == true}">
                       checked
                </c:if>
                >
                <label class="form-check-label" for="selectCurrentExhibitions">
                    Select current
                </label>
            </div>
        </div>
        <div>
            <p style="margin-bottom: 0;">Rows amount</p>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="rowsNumberRadio" id="rowsNumberRadio4" value="4">
                <label class="form-check-label" for="rowsNumberRadio4">4</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="rowsNumberRadio" id="rowsNumberRadio8" value="8"
                       checked>
                <label class="form-check-label" for="rowsNumberRadio8">8</label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="rowsNumberRadio" id="rowsNumberRadio16" value="16">
                <label class="form-check-label" for="rowsNumberRadio16">16</label>
            </div>
        </div>
        <div class="finderBox">
            <div class="input-group mb-3">
                <input type="text" name="searchBox" class="form-control"
                       placeholder="Search the exposition (id/title/author)" aria-label="Recipient's username"
                       aria-describedby="finderBox">
            </div>
        </div>
        <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne"
             data-bs-parent="#accordionExample">
            <div class="accordion-body">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" role="switch" id="startDateCheckbox"
                    <c:if test="${startDateAdminFilter != null}">
                           checked
                    </c:if>
                    >
                    <label for="startDate"><fmt:message key="startDate"/></label>

                    <input type="date" id="startDate" name="startDate"
                    <c:choose>
                    <c:when test="${startDateAdminFilter != null}">
                           value="${startDateAdminFilter}"
                    </c:when>
                    <c:otherwise>
                           value="" disabled
                    </c:otherwise>
                    </c:choose>
                           min="<%=currentDate%>" max="<%=futureLimit%>">
                </div>
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" role="switch" id="endDateCheckbox"
                    <c:if test="${finalDateAdminFilter != null}">
                           checked
                    </c:if>
                    >
                    <label for="endDate"><fmt:message key="endDate"/></label>

                    <input type="date" id="endDate" name="endDate"
                    <c:choose>
                    <c:when test="${finalDateAdminFilter != null}">
                           value="${finalDateAdminFilter}"
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
                <button class="btn btn-outline-secondary" type="submit" id="acceptFiltersButton"><fmt:message
                        key="acceptFilters"/>
                </button>
            </div>
        </div>
        <div>
            <button class="btn btn-outline-secondary" type="submit" style="width: 5rem;" id="finderBox">Find</button>
        </div>
    </form>
</div>