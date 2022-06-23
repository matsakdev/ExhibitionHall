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
        commands.put("expositions", new EditingFormLoadingCommand());
        commands.put("updateExposition", new UpdateExpositionCommand());
        commands.put("exhibitionDetails", new ExhibitionDetailsCommand());
        commands.put("order", new OrderCommand());
        commands.put("makeorder", new MakeOrderCommand());
        commands.put("profileSettings", new ProfileSettingsCommand());
        commands.put("logout", new LogoutCommand());
    }

    public static FrontCommand dispatch(HttpServletRequest request){
        String path = request.getRequestURI().substring(request.getContextPath().length()).toLowerCase(Locale.ROOT);
        if (path.startsWith("/?") || path.trim().equals("/")) {
            return commands.get("main");
        }
        if (path.startsWith("/main")) {
            return commands.get("main");
        }
        else if (path.startsWith("/order")) {
            return commands.get("order");
        }
        else if (path.startsWith("/profile")) {
            path = path.substring("/profile".length());
            if (path.startsWith("/settings")) {
                commands.get("profileSettings");
            }
            return commands.get("profile");
        }
        else if (path.startsWith("/admin")) {
            String regex = "(\\/admin\\/expositions\\/[0-9]+)";
            if (path.matches(regex + "$")) {
                return commands.get("expositions");
            } else if (path.matches(regex + "\\/update")) {
                return commands.get("updateExposition");
            }
            return commands.get("admin");
        }
        else if (path.startsWith("/login")) {
            return commands.get("login");
        }
        else if (path.startsWith("/logout")) {
            return commands.get("logout");
        }
        else if (path.startsWith("/register")) {
            return commands.get("register");
        }
        else if (path.startsWith("/profile")){
            return commands.get("profile");
        }
        else if (path.startsWith("/makeorder")){
            return commands.get("makeorder");
        }
        else if (path.startsWith("/exhibition")){
            String regex = "(/exhibition\\/[0-9]+)";
            if (path.matches(regex + "$")) {
                return commands.get("exhibitionDetails");
            }
            return commands.get("main");
        }
        else return commands.get("error");
    }
}
