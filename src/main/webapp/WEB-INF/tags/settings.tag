<%@ tag import="java.time.LocalDateTime" %>
<%@ tag import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="eh" uri="/WEB-INF/customtags.tld" %>

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
                    else out.print("btn-outline-dark");%> " for="sortingRadio1">Date</label>

                <input type="submit" class="btn-check" name="sorting" value="price" id="sortingRadio2"
                       autocomplete="off">
                <label class="btn <% if ("price".equals(request.getParameter("sorting"))) out.print("btn-dark");
                    else out.print("btn-outline-dark");%>" for="sortingRadio2">Price</label>

                <input type="submit" class="btn-check" name="sorting" value="popularity" id="sortingRadio3"
                       autocomplete="off">
                <label class="btn <% if ("popularity".equals(request.getParameter("sorting"))) out.print("btn-dark");
                    else out.print("btn-outline-dark");%>" for="sortingRadio3">Popularity</label>
            </div>
        </div>
        <div class="searchInput input-group mb-3">
            <input type="text" class="form-control" placeholder="Recipient's username"
                   value="<% if (request.getParameter("search") != null) out.print(request.getParameter("search"));%>"
                   aria-label="Recipient's username" aria-describedby="button-addon2" name="search">
            <button class="btn btn-outline-secondary" type="submit" id="button-addon2">Search</button>
        </div>
        <div class="filtersBox accordion" id="accordionExample">
            <div class="accordion-item">
                <h2 class="accordion-header" id="headingOne">
                    <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                            data-bs-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                        Filters
                    </button>
                </h2>
                <div id="collapseOne" class="accordion-collapse collapse" aria-labelledby="headingOne"
                     data-bs-parent="#accordionExample">
                    <div class="accordion-body">
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" id="startDateCheckbox">
                            <label for="startDate">Start date:</label>

                            <input type="date" id="startDate" name="startDate"
                                   value=""
                                   min="<%=currentDate%>" max="<%=futureLimit%>" disabled>
                        </div>
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" id="endDateCheckbox">
                            <label for="endDate">End date:</label>

                            <input type="date" id="endDate" name="endDate"
                                   value=""
                                   min="<%=currentDate%>" max="<%=futureLimit%>" disabled>
                        </div>
                        <div class="form-check form-switch">
                            <input class="form-check-input" type="checkbox" role="switch" id="themesCheckbox">
                            <label for="themesList">Select themes:</label>
                        </div>
                        <select class="form-select" name="themesList" id="themesList" multiple size="5" aria-label="size 5 select example" disabled>
                            <eh:ThemesList/>
                        </select>
                        <button class="btn btn-outline-secondary" type="submit" id="acceptFiltersButton">Accept filters</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
