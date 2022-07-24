package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.FilterSettings;
import com.matsak.exhibitionhall.db.entity.Theme;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GenerateMainPageCommand extends FrontCommand{

    private static enum Orders{
        DATE("DATE"), POPULARITY("POPULARITY"), PRICE("PRICE");
        final String Type;
        Orders(String ordertype){
            this.Type = ordertype;
        }
    }


    Logger logger = LogManager.getLogger(GenerateListCommand.class);
    @Override
    public void process() throws ServletException, IOException {
        Optional<String> pageOptional = Optional.ofNullable(request.getParameter("page"));
        int page = 1;
        if (pageOptional.isPresent()){
            try{
                page = Integer.parseInt(pageOptional.get());
                if (page < 1) page = 1;
            }
            catch (NumberFormatException e){
                logger.debug("The number of page wasn't setted and changed to [1]");
            }
        }
        FilterSettings filterSettings = filterHandler();

        String sortingType = Optional.ofNullable(request.getParameter("sorting")).orElse("date");
        Orders sortingOrder = null;
        sortingOrder = switch (sortingType) {
            case "price" -> Orders.PRICE;
            case "popularity" -> Orders.POPULARITY;
            default -> Orders.DATE;
        };

        Optional<Object> cardsCount = Optional.ofNullable(request.getSession().getAttribute("cardsOnPage"));

        int cardsOnPage = 6;

        if (cardsCount.isPresent()) {
            try {
                cardsOnPage = Integer.parseInt(cardsCount.toString());
                if (cardsOnPage < 6) cardsOnPage = 6;
            } catch (NumberFormatException e) {
                logger.debug("Cards number wasn't changed");
            }
        }

        else{
            request.getSession().setAttribute("cardsOnPage", 6);
        }

        if (request.getSession().getAttribute("dateFrom") != null){
            //todo
        }
        if (request.getSession().getAttribute("dateUntil") != null){
            //todo
        }
        List<Theme> themesList = DAOFactory.getInstance().getThemeDAO().getAllThemes();
        List<Exposition> expositions = getCardsList(page, cardsOnPage, sortingOrder, filterSettings);
        Map<Long, Set<Theme>> expositionThemes = DAOFactory.getInstance().getThemeDAO().getThemesByExpositions(expositions);
        System.out.println(request.getSession().getAttribute("loginError"));
        request.getSession().setAttribute("expositions", expositions);
        request.getSession().setAttribute("expositionsThemes", expositionThemes);
        request.getSession().setAttribute("themes", themesList);
        forward("index.jsp");
    }

    private FilterSettings filterHandler() {
        String search = null;
        String parameter = request.getParameter("search");
        if (parameter != null && !parameter.equals("")) {
            search = parameter;
        }
        String startDate = null;
        parameter = request.getParameter("startDate");
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
        result.setSearch(search);
        result.setStartDate(startDate);
        result.setEndDate(endDate);
        result.setThemes(themesList);
        request.getSession().setAttribute("searchFilter", search);
        request.getSession().setAttribute("startDateFilter", startDate);
        request.getSession().setAttribute("finalDateFilter", endDate);
        request.getSession().setAttribute("themesFilter", themesParameter);
//        DateTimeFormatter formatFromRequest = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        DateTimeFormatter formatForInputValue = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate.parse(startDate, formatFromRequest).format()

        return result;
    }

    public List<Exposition> getCardsList(int page, int cardsAmount, Orders orderType, FilterSettings filters){
        List<Exposition> expositions = null;
        try {
            int shift = (page - 1) * cardsAmount;
            expositions = DAOFactory.getInstance().getExpositionDAO().getAllExpositions(orderType.Type, shift, cardsAmount, filters);
            if (logger.isDebugEnabled()){
                logger.debug(expositions);
            }
            if (expositions.size() == 0){
                //todo errorpage
            }
            return expositions;
        } catch (Exception e) {
            logger.warn("Unable to return list of expositions");
        }
        return expositions;
    }
}
