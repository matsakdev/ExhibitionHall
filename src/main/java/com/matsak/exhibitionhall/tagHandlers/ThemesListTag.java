package com.matsak.exhibitionhall.tagHandlers;

import com.matsak.exhibitionhall.db.entity.Exposition;
import com.matsak.exhibitionhall.db.entity.Theme;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.jsp.JspContext;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.PageContext;
import jakarta.servlet.jsp.tagext.JspTag;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ThemesListTag extends SimpleTagSupport {
    Logger logger = LogManager.getLogger(ThemesListTag.class);

    public void doTag() throws JspException, IOException {
        JspContext ctx = getJspContext();
        PageContext pageContext = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        Object themesListObject;
        List<Theme> themesList = null;
        JspWriter out = getJspContext().getOut();
        try{
            themesListObject = request.getSession().getAttribute("themes");
            themesList = (List<Theme>)themesListObject;
        } catch (Exception e) {
            logger.warn("Cannot get themes from session scope " + e.getMessage());
        }
        for (Theme theme : themesList) {
            out.println("<option value=" + theme.getId() + ">" + theme.getThemeName() + "</option>");
        }
    }
}
