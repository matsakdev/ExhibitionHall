package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.FilterSettings;
import com.matsak.exhibitionhall.db.entity.Theme;
import com.matsak.exhibitionhall.utils.AdminFilterSettings;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AdminPanelCommand extends FrontCommand{
    private static final Logger logger = LogManager.getLogger(AdminPanelCommand.class);

    private static enum Orders{
        DATE("DATE"), POPULARITY("POPULARITY"), PRICE("PRICE"), TITLE("TITLE"), AUTHOR("AUTHOR");
        final String Type;
        Orders(String ordertype){
            this.Type = ordertype;
        }
    }

    @Override
    public void process() throws ServletException, IOException {
        try {
            AdminFilterSettings filter = setFilters();
            FilterSettings filterSettings = acceptFilters(filter);
            String order = getOrderType();
            int firstRowIndex = (filter.getPage() - 1) * filter.getRowsAmount();
            List<Exposition> expositionList = DAOFactory.getInstance().getExpositionDAO().getAllExpositions(order, firstRowIndex, filter.getRowsAmount(), filterSettings);
            request.setAttribute("expositions", expositionList);
            Map<Integer, Integer> tickets = DAOFactory.getInstance().getExpositionDAO().ticketsByExpositions();
            request.getSession().setAttribute("ticketsCount", tickets);
            logger.trace("AdminPanel has been loaded successfully. Forwarding to admin.jsp");
            forward("admin.jsp");
        } catch (Exception e) {
            //todo log
            e.printStackTrace();
        }
    }

    private String getOrderType() {
        String sortingType = Optional.ofNullable(request.getParameter("sorting")).orElse("date");
        Orders sortingOrder = null;
        sortingOrder = switch (sortingType.toLowerCase()) {
            case "price" -> Orders.PRICE;
            case "popularity" -> Orders.POPULARITY;
            case "title" -> Orders.TITLE;
            case "author" -> Orders.AUTHOR;
            default -> Orders.DATE;
        };
        return sortingType;
    }

    private FilterSettings acceptFilters(AdminFilterSettings adminFilter) {
        FilterSettings filterSettings = new FilterSettings();
        filterSettings.setAdmin(true);
        HttpSession session = request.getSession();
        if (adminFilter.isShowCompleted()) {
            filterSettings.setShowCompleted(true);
            session.setAttribute("showCompleted", true);
        }
        else session.setAttribute("showCompleted", false);
        if (adminFilter.isSelectCurrent()) {
            session.setAttribute("selectCurrent", true);
        }
        else session.setAttribute("selectCurrent", false);
        setRestrictions(filterSettings);
        filterSettings.setSearch(adminFilter.getSearch());
        return filterSettings;
    }

    private void setRestrictions(FilterSettings filterSettings) {
        String startDate = null;
        String parameter = request.getParameter("startDate");
        if (parameter != null && !parameter.equals("")) {
            startDate = parameter;
        }
        String endDate = null;
        parameter = request.getParameter("endDate");
        if (parameter != null && !parameter.equals("")) {
            endDate = parameter;
        }
        String[] themes = null;
        String[] themesParameter;
        themesParameter = request.getParameterValues("themesList");
        List<Theme> themesList = new ArrayList<>();
        if (themesParameter != null) {
            try {
                themes = themesParameter;
                for (String theme : themes) {
                    int themeId = Integer.parseInt(theme);
                    themesList.add(DAOFactory.getInstance().getThemeDAO().getById(themeId));
                }
            } catch (NumberFormatException e) {
                logger.warn("Number Format of themeId is not correct " + e.getMessage());
            }
        }
        FilterSettings result = new FilterSettings();
        result.setStartDate(startDate);
        result.setEndDate(endDate);
        result.setThemes(themesList);
    }

    private AdminFilterSettings setFilters() {
        String showCompletedField = request.getParameter("showCompleted");
        String selectCurrentField = request.getParameter("selectCurrent");
        String rowsNumberField = request.getParameter("rowsNumberRadio");
        String searchField = request.getParameter("searchBox");
        int pageValue = 1;;
        int rowsNumber = 8;
        boolean showCompleted = Boolean.parseBoolean(showCompletedField);
        boolean selectCurrent = Boolean.parseBoolean(selectCurrentField);
        try {
            rowsNumber = Integer.parseInt(rowsNumberField);
            pageValue = Optional.of(Integer.parseInt(request.getParameter("page"))).orElse(1);
        }
        catch (NumberFormatException e) {
            logger.info("Cannot format number " + rowsNumberField + " to Integer, " + e.getMessage());
        }
        return new AdminFilterSettings(pageValue, showCompleted, selectCurrent, rowsNumber, searchField);

    }
}
