<%@ tag import="com.matsak.exhibitionhall.db.entity.User" %>
<%@ tag import="com.matsak.exhibitionhall.db.entity.Exposition" %>
<%@ tag import="java.util.List" %>
<%@ tag import="java.util.Optional" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="tableBox col-md-10">
    <table class="table table-striped table-hover caption-top">
        <caption>List of expositions</caption>
        <thead class="table-dark">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Title</th>
            <th scope="col">Date</th>
            <th scope="col">Author</th>
            <th scope="col">Tickets</th>
            <th scope="col">Edit</th>
        </tr>
        </thead>
        <tbody>
        <%int rows = ((List<Exposition>)request.getAttribute("expositions")).size();
        int pageNum = Integer.parseInt(Optional.ofNullable(request.getParameter("page")).orElse("1"));
        int iterator = rows * (pageNum - 1) + 1;%>
        <c:forEach var="exposition" items="${expositions}">
            <tr class="adminTableRow">
                <th scope="row"><%=iterator%></th>
                <td class="adminTableCell" colspan="1"><c:out value="${exposition.getExpName()}"/></td>
                <td class="adminTableCell" colspan="1"><c:out value="${exposition.getExpStartDate()}"/></td>
                <td class="adminTableCell" colspan="1"><c:out value="${exposition.getAuthor()}"/></td>
                <td class="adminTableCell" colspan="1"><c:out value="many"/></td>
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
        <tfoot>
        <tr>
            <td>Footer</td>
            <td>Footer</td>
            <td>Footer</td>
            <td>Footer</td>
            <td>Footer</td>
        </tr>
        </tfoot>
    </table>
</div>
