package com.matsak.exhibitionhall;

import com.matsak.exhibitionhall.commands.*;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Dispatcher {
    private static Map<String, FrontCommand> commands;

    static{
        commands = new HashMap<>();
        commands.put("login", new LoginCommand());
        commands.put("register", new RegistrationCommand());
        commands.put("admin", new AdminPanelCommand());
        commands.put("error", new UnknownCommand());
        commands.put("main", new GenerateMainPageCommand());
        commands.put("profile", new ProfileCommand());
    }

    public static FrontCommand dispatch(HttpServletRequest request){
        String path = request.getRequestURI().substring(request.getContextPath().length()).toLowerCase(Locale.ROOT);
        if (path.startsWith("/?") || path.trim().equals("/")) {
            return commands.get("main");
        }
        if (path.startsWith("/admin")) {
            return commands.get("admin");
        }
        else if (path.startsWith("/login")) {
            return commands.get("login");
        }
        else if (path.startsWith("/register")) {
            return commands.get("register");
        }
        else if (path.startsWith("/profile")){
            return commands.get("profile");
        }
        else return commands.get("error");
    }
}
