package com.matsak.exhibitionhall.tagHandlers;

import com.matsak.exhibitionhall.commands.GenerateListCommand;
import com.matsak.exhibitionhall.db.entity.Exposition;
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
import java.util.ArrayList;
import java.util.List;

public class CardsGeneratingTag extends SimpleTagSupport {
    private static final Logger logger = LogManager.getLogger(CardsGeneratingTag.class.getName());


        public void doTag() throws JspException, IOException {
            JspContext ctx = getJspContext();
            PageContext pageContext = (PageContext) getJspContext();
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
            Object expositionListObject;
            List<Exposition> expositionList;
            JspWriter out = getJspContext().getOut();
            try{
                expositionListObject = request.getAttribute("expositions");
                expositionList = (List<Exposition>)expositionListObject;
            } catch (Exception e) {
                throw new RuntimeException(e);
                //todo log
            }
            for (Exposition card : expositionList){
                out.println("<div class=\"card text-center\">\n" +
                        "                <img src=\"images/knife1.jpg\" class=\"card-img-top\" alt=\"...\">\n" +
                        "                <div class=\"flexContainer\">\n" +
                        "                    <div class=\"card-body itemCard d-flex flex-column\">\n" +
                        "                        <div class=\"rowFlex\">\n" +
                        "                            <h5 class=\"card-title\">" + card.getExpName() +"</h5>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"rowFlex\">\n" +
                        "                            <p class=\"card-text\">" + card.getAuthor() + "</p>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"rowFlex endCard\">\n" +
                        "                            <a href=\"#\" class=\"btn btn-primary\">Go somewhere</a>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                </div>\n" +
                        "            </div>");
            }
            logger.debug("List of expositions");
        }


}
