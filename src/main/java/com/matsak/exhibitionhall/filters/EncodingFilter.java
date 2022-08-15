package com.matsak.exhibitionhall.filters;

import jakarta.servlet.*;

import java.io.IOException;
/**
 * This filter sets the encoding of every request and response.
 * <p>
 * Encoding by default: <strong>UTF-8</strong><p>
 * For changing encoding change the {@link EncodingFilter#init(FilterConfig)} method.
 * <strong>have to be added</strong> to {@link AccessFilter}'s adminPages List.
 * <p>
 *
 */
public class EncodingFilter implements Filter {
    private String encoding;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        if (encoding == null) encoding = "UTF-8";
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
