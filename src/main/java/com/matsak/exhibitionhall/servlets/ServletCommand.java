package com.matsak.exhibitionhall.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ServletCommand {
    String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
