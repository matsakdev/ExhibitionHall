package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class GenerateMainPageCommand extends FrontCommand{

    private static enum orders{
        DATE("DATE"), POPULARITY("POPULARITY"), PRICE("PRICE");
        final String Type;
        orders(String ordertype){
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
        List<Exposition> expositions = getCardsList(page, cardsOnPage, orders.DATE);
        request.setAttribute("expositions", expositions);
        forward("index.jsp");
    }

    public List<Exposition> getCardsList(int page, int cardsAmount, orders orderType){
        List<Exposition> expositions = null;
        try {
            int shift = (page - 1) * cardsAmount;
            expositions = DAOFactory.getInstance().getExpositionDAO().getAllExpositions(orderType.Type, shift, cardsAmount);
            if (logger.isDebugEnabled()){
                logger.debug(expositions);
            }
            if (expositions.size() == 0){
                //todo errorpage
            }
            return expositions;
        } catch (Exception e) {
            logger.warn("Unnable to return list of expositions");
        }
        return expositions;
    }
}
