package com.matsak.exhibitionhall.commands;

import java.util.HashMap;
import java.util.Map;

public class CommandsContainer {
    private static Map<String, FrontCommand> commands;

    static{
        commands = new HashMap<>();
        commands.put("login", new LoginCommand());
        commands.put("registration", new RegistrationCommand());
        commands.put("admin", new AdminPanelCommand());
    }

    public static FrontCommand getCommand(String commandName){
        return commands.get(commandName);
    }
}
