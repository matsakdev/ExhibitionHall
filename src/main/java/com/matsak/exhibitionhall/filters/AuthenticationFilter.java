package com.matsak.exhibitionhall.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AuthenticationFilter implements Filter {
    List<String> closedPages = new ArrayList<>();

    public void init(FilterConfig config) throws ServletException {
        closedPages.add("/profile");
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length()).toLowerCase(Locale.ROOT);
        for (String page : closedPages) {
            if (path.startsWith(page)) {
                if (((HttpServletRequest) request).getSession().getAttribute("currentUser") != null) chain.doFilter(request, response);
                else {
                    request.setAttribute("authNeed", true);
                    request.getRequestDispatcher("/login").forward(request, response);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
