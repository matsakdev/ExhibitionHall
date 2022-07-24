package com.matsak.exhibitionhall.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AuthenticationFilter implements Filter {
    Logger logger = LogManager.getLogger(AuthenticationFilter.class);
    List<String> closedPages = new ArrayList<>();

    public void init(FilterConfig config) throws ServletException {
        closedPages.add("/profile");
        closedPages.add("/order");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length()).toLowerCase(Locale.ROOT);
        if (req.getSession().getAttribute("lang") == null) {
            req.getSession().setAttribute("lang", "en");
        }
        for (String page : closedPages) {
            if (path.startsWith(page)) {
                if (((HttpServletRequest) request).getSession().getAttribute("currentUser") != null) {
                    logger.trace("User can visit page without authentication");
                    chain.doFilter(request, response);
                }
                else {
                    logger.trace("User must auth before using this page: " + page);
                    if (((HttpServletRequest) request).getHeader("Content-Type").equals("application/json;charset=utf-8")) {
                        req.getSession().setAttribute("jsonRequest", true);
                    }
                    request.setAttribute("authNeed", true);
//                    request.getRequestDispatcher("/login").forward(request, response);
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.sendRedirect(((HttpServletRequest) request).getContextPath() + "/login");
                }
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
