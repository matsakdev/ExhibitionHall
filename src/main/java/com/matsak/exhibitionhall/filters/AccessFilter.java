package com.matsak.exhibitionhall.filters;

import com.matsak.exhibitionhall.commands.FrontCommand;
import com.matsak.exhibitionhall.commands.GenerateMainPageCommand;
import com.matsak.exhibitionhall.db.dao.DAOFactory;
import com.matsak.exhibitionhall.db.entity.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccessFilter implements Filter {
    Logger logger = LogManager.getLogger(AccessFilter.class);
    List<String> userPages = new ArrayList<>();
    List<String> adminPages = new ArrayList<>();


    public void init(FilterConfig config) throws ServletException {
        userPages.add("/?");
        userPages.add("/register");
        adminPages.add("/admin");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        //
        try {
            req.getSession().setAttribute("currentUser", DAOFactory.getInstance().getUserDAO().getByLogin("admin01"));
            req.getSession().setAttribute("userRole", "administrator");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // todo for debugging
        try{
            String path = req.getRequestURI().substring(req.getContextPath().length()).toLowerCase(Locale.ROOT);
            if (path.trim().equals("/")) chain.doFilter(request, response);
            for (String page : adminPages) {
                if (path.startsWith(page)) {
                    if ("administrator".equals(((HttpServletRequest) request).getSession().getAttribute("userRole"))) {
                        if (logger.isTraceEnabled()){
                            logger.trace("User accessed as administrator");
                        }
                        chain.doFilter(request, response);
                    }
                    else {
                        logger.warn("User has been tried to get admin functionality");
                        ((HttpServletResponse)response).sendRedirect(req.getContextPath() + "/error");
                    }
                    return;
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
