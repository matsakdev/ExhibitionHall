package com.matsak.exhibitionhall.commands;

import jakarta.servlet.ServletException;

import java.io.IOException;

public class RegistrationSuccessfulCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        forward("registration-successful.jsp");
    }
}
