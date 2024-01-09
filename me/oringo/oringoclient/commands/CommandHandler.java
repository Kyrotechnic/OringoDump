//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands;

import java.util.*;
import me.oringo.oringoclient.commands.impl.*;
import me.oringo.oringoclient.qolfeatures.module.impl.render.*;
import me.oringo.oringoclient.*;

public class CommandHandler
{
    private static final HashMap<String, Command> COMMAND_MAP;
    
    public static void register(final Command command) {
        for (final String name : command.getNames()) {
            if (!CommandHandler.COMMAND_MAP.containsKey(name.toLowerCase())) {
                CommandHandler.COMMAND_MAP.put(name.toLowerCase(), command);
            }
        }
        HelpCommand.helpMap.put(command.getNames()[0].toLowerCase(), command);
    }
    
    public static void unregister(final Command command) {
        for (final String name : command.getNames()) {
            CommandHandler.COMMAND_MAP.remove(name.toLowerCase());
        }
    }
    
    public static char getCommandPrefix() {
        if (Gui.commandPrefix.getValue().length() < 1) {
            return '.';
        }
        return Gui.commandPrefix.getValue().toLowerCase().charAt(0);
    }
    
    public static boolean handle(String message) {
        message = message.trim();
        if (message.length() > 0 && message.charAt(0) == getCommandPrefix()) {
            message = message.toLowerCase().substring(1);
            final String commandString = message.split(" ")[0];
            String[] args = new String[0];
            if (message.contains(" ")) {
                args = message.replaceFirst(commandString, "").replaceFirst(" ", "").split(" ");
            }
            if (CommandHandler.COMMAND_MAP.containsKey(commandString)) {
                final Command command = CommandHandler.COMMAND_MAP.get(commandString);
                try {
                    command.execute(args);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else {
                OringoClient.sendMessage(String.format("§bOringoClient §3» §cInvalid command \"%shelp\" for §cmore info", getCommandPrefix()));
            }
            return true;
        }
        return false;
    }
    
    static {
        COMMAND_MAP = new HashMap<String, Command>();
    }
}
