package com.matsak.exhibitionhall.commands;

import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.Exposition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GenerateListCommand {
    private static GenerateListCommand instance;
    Logger logger = LogManager.getLogger(GenerateListCommand.class);

    //every page contains 6 cards of information
    private GenerateListCommand(){
        logger.trace("CardsListGenerator singleton constructor working");
    }

    public static GenerateListCommand getInstance(){
        if (instance == null) instance = new GenerateListCommand();
        return instance;
    }

    public List<Exposition> getCardsList(int page, int cardsAmount, String order){
        List<Exposition> expositions = null;
        try {
            expositions = DAOFactory.getInstance().getExpositionDAO().getAllExpositions();
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
