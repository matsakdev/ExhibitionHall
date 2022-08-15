package com.matsak.exhibitionhall.tagHandlers;

import com.matsak.exhibitionhall.commands.GenerateListCommand;
import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Theme;
import com.matsak.exhibitionhall.servlets.FrontControllerServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspContext;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CardsGeneratingTag extends SimpleTagSupport {
    private static final Logger logger = LogManager.getLogger(CardsGeneratingTag.class.getName());
    private static ResourceBundle langBundle;

    public void setLang(String language) {
         langBundle = ResourceBundle.getBundle("mainpage", Locale.forLanguageTag(language));
    }

    public void doTag() throws JspException, IOException {
            JspContext ctx = getJspContext();
            String language = (String) ctx.getAttribute("lang");
            PageContext pageContext = (PageContext) getJspContext();
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            Object expositionListObject;
            Object expositionThemesObject;
            List<Exposition> expositionList;
            Map<Long, Set<Theme>> expositionThemesMap;
            JspWriter out = getJspContext().getOut();
            DateTimeFormatter cardDateFormat = DateTimeFormatter.ofPattern("dd-MM-yy HH:mm");
            try{
                expositionListObject = request.getSession().getAttribute("expositions");
                expositionList = (List<Exposition>)expositionListObject;
                expositionThemesObject = request.getSession().getAttribute("expositionsThemes");
                expositionThemesMap = (Map<Long, Set<Theme>>) expositionThemesObject;
            } catch (Exception e) {
                throw new RuntimeException(e);
                //todo log
            }
            for (Exposition card : expositionList){
                String startDateTime = card.getExpStartDate().toLocalDateTime().format(cardDateFormat);
                String finalDateTime = card.getExpFinalDate().toLocalDateTime().format(cardDateFormat);
                Set<Theme> mapOfThemes = expositionThemesMap.get(card.getId());
                StringBuilder currentThemes = new StringBuilder();
                for (Theme theme : mapOfThemes) {
                    currentThemes.append(theme.getThemeName()).append(" | ");
                }
                if (currentThemes.length() < 2) currentThemes = new StringBuilder(" | ");
                String cardThemes = currentThemes.delete(currentThemes.length() - 2, currentThemes.length()).toString();
                out.println("<div class=\"card text-center\">\n" +
                        "<div class=\"imageBox\">\n" +
                        "                <img src='images/" + Optional.ofNullable(card.getImage()).orElse("clear.png") +"' class=\"cardImage card-img-top\" alt=\"...\">\n" +
                        "        </div>" +
                        "                <div class=\"flexContainer\">\n" +
                        "                    <div class=\"card-body itemCard d-flex flex-column\">\n" +
                        "                        <div class=\"rowFlex\">\n" +
                        "                            <h5 class=\"card-title\">" + card.getExpName() +"</h5>\n" +
                        "                        </div>\n" +
                        "                        <hr>" +
                        "                        <div class=\"rowFlex\">\n" +
                        "                            <p class=\"card-text\">" + card.getAuthor() + "</p>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"rowFlex\">\n" +
                        "                            <p class=\"card-text\">" + startDateTime + " - " + finalDateTime + "</p>\n" +
                        "                        </div>\n" +
                        "                        <hr/>" +
                        "                        <div class=\"rowFlex\">\n" +
                        "                            <p class=\"card-text\">" + cardThemes + "</p>\n" +
                        "                        </div>\n" +
                        "                        <hr/>" +
                        "                        <div class=\"rowFlex\">\n" +
                        "                            <p class=\"card-text\">" + card.getDescription() + "</p>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"rowFlex endCard\">\n" +
                        "                            <a href='exhibition/" + card.getId() + "' class=\"btn btn-primary\">See details</a>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </div>");
            }
            if (expositionList.size() == 0) {
                out.println("<div style=\"font-size: 25pt\">" + langBundle.getString("missingExhibitionsMessage") + "</div>");
            }
            logger.debug("List of expositions");
        }
}
