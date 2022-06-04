package com.matsak.exhibitionhall.filters;

import com.matsak.exhibitionhall.commands.FrontCommand;
import com.matsak.exhibitionhall.commands.GenerateMainPageCommand;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AccessFilter implements Filter {
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
        String path = req.getRequestURI().substring(req.getContextPath().length()).toLowerCase(Locale.ROOT);
        if (path.trim().equals("/")) chain.doFilter(request, response);
        for (String page : adminPages) {
            if (path.startsWith(page)) {
                if ("administrator".equals(((HttpServletRequest) request).getSession().getAttribute("userRole"))) chain.doFilter(request, response);
                else {
                    ((HttpServletResponse)response).sendRedirect(req.getContextPath() + "/error");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }
}
