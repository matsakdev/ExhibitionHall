package com.matsak.exhibitionhall.commands;

import jakarta.servlet.ServletException;

import java.io.IOException;

public class UnknownCommand extends FrontCommand{
    @Override
    public void process() throws ServletException, IOException {
        forward("error.jsp");
    }
}
