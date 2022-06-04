package com.matsak.exhibitionhall.servlets;

import java.util.HashMap;
import java.util.Map;

    public class ServletsContainer {
        private static Map<String, ServletCommand> commands;

        static {
            commands = new HashMap<>();

//            commands.put("login", new LoginCommand());
        }

//        public static Command getCommand(String commandName) {
//            return commands.get(commandName);
//        }
}
