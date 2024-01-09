//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import java.util.*;
import me.oringo.oringoclient.commands.*;
import me.oringo.oringoclient.*;

public class HelpCommand extends Command
{
    public static HashMap<String, Command> helpMap;
    
    public HelpCommand() {
        super("help", new String[] { "commands", "info" });
    }
    
    public void execute(final String[] args) throws Exception {
        if (args.length == 0) {
            OringoClient.sendMessage(String.format("§d%shelp command §7for more info", CommandHandler.getCommandPrefix()));
            HelpCommand.helpMap.forEach((key, value) -> {
                if (value.getDescription() != null) {
                    OringoClient.sendMessage(String.format("§d%s%s §3» §7%s", CommandHandler.getCommandPrefix(), key, value.getDescription()));
                }
            });
        }
        else if (HelpCommand.helpMap.containsKey(args[0])) {
            final String name = args[0];
            final Command command = HelpCommand.helpMap.get(args[0]);
            OringoClient.sendMessage(String.format("§b%s%s §3» §7%s", CommandHandler.getCommandPrefix(), name, command.getLongDesc()));
        }
        else {
            OringoClient.sendMessage(String.format("§bOringoClient §3» §cInvalid command \"%shelp\" for §cmore info", CommandHandler.getCommandPrefix()));
        }
    }
    
    public String getDescription() {
        return "Shows all commands";
    }
    
    static {
        HelpCommand.helpMap = new HashMap<String, Command>();
    }
}
