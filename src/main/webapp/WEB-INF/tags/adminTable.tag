<%@ tag import="com.matsak.exhibitionhall.db.entity.Exposition" %>
<%@ tag import="java.util.List" %>
<%@ tag import="java.util.Optional" %>
<%@ tag import="java.util.Map" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%Map<Integer, Integer> ticketsCount = (Map<Integer, Integer>)session.getAttribute("ticketsCount");%>
<jsp:useBean id="now" class="java.util.Date" />
<div class="tableBox col-md-12">
    <table class="table table-striped table-hover caption-top">
        <caption>List of expositions</caption>
        <thead class="table-dark">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Title</th>
            <th scope="col">Begins</th>
            <th scope="col">Ends</th>
            <th scope="col">Author</th>
            <th scope="col">Tickets</th>
            <th scope="col">Edit</th>
        </tr>
        </thead>
        <tbody>
        <%int rows = ((List<Exposition>)request.getAttribute("expositions")).size();
        int pageNum = Integer.parseInt(Optional.ofNullable(request.getParameter("page")).orElse("1"));
        int iterator = rows * (pageNum - 1) + 1;%>
<%--        <c:set var="ticketsCount" value="${(sessionScope['ticketsCount'] == null || requestScope['ticketsCount'] eq '') ? 'not available' : sessionScope['ticketsCount']}"/>--%>
        <c:forEach var="exposition" items="${expositions}">
        <tr class="adminTableRow"
            <c:if test="${now >= exposition.getExpStartDate() && now <= exposition.getExpFinalDate() && selectCurrent == true}">style="background-color: lightgreen"</c:if>
        >
                <th scope="row"><%=iterator%></th>
                <td class="adminTableCell" colspan="1"><c:out value="${exposition.getExpName()}"/></td>
                <td class="adminTableCell" colspan="1"><fmt:formatDate value="${exposition.getExpStartDate()}" type="both" pattern="dd-MM-yyyy HH:mm"/></td>
                <td class="adminTableCell" colspan="1"><fmt:formatDate value="${exposition.getExpFinalDate()}" type="both" pattern="dd-MM-yyyy HH:mm"/></td>
                <td class="adminTableCell" colspan="1"><c:out value="${exposition.getAuthor()}"/></td>
                <td class="adminTableCell" colspan="1">
                    <c:set var="expositionId" scope="request" value="${exposition.getId()}"/>
                    <% Integer id = Integer.parseInt(request.getAttribute("expositionId").toString());
                        out.print(ticketsCount.get(id));%>
                </td>
                <td class="editButtonsColumn">
                    <a href="<%=request.getContextPath()%>/admin/expositions/<c:out value="${exposition.getId()}"/>">
                        <div class="editButton flex align-items-center justify-content-center">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
                                 class="bi bi-sliders2" viewBox="0 0 16 16">
                                <path fill-rule="evenodd"
                                      d="M10.5 1a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-1 0V4H1.5a.5.5 0 0 1 0-1H10V1.5a.5.5 0 0 1 .5-.5ZM12 3.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 0 1h-2a.5.5 0 0 1-.5-.5Zm-6.5 2A.5.5 0 0 1 6 6v1.5h8.5a.5.5 0 0 1 0 1H6V10a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5ZM1 8a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 0 1h-2A.5.5 0 0 1 1 8Zm9.5 2a.5.5 0 0 1 .5.5v4a.5.5 0 0 1-1 0V13H1.5a.5.5 0 0 1 0-1H10v-1.5a.5.5 0 0 1 .5-.5Zm1.5 2.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 0 1h-2a.5.5 0 0 1-.5-.5Z"/>
                            </svg>
                        </div>
                    </a>
                </td>
            </tr>
            <%iterator++;%>
        </c:forEach>
        </tbody>
    </table>
</div>
