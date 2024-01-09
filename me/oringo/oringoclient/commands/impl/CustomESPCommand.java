//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.qolfeatures.module.impl.render.*;
import java.awt.*;
import java.util.*;

public class CustomESPCommand extends Command
{
    public CustomESPCommand() {
        super("esp", new String[] { "customesp" });
    }
    
    public void execute(final String[] args) throws Exception {
        if (args.length == 0) {
            for (final String name : CustomESP.names.keySet()) {
                OringoClient.sendMessageWithPrefix(name);
            }
            OringoClient.sendMessageWithPrefix(String.format("%s%s add/remove", Gui.commandPrefix.getValue(), this.getNames()[0]));
        }
        else {
            final String s = args[0];
            switch (s) {
                case "add": {
                    if (args.length != 3) {
                        OringoClient.sendMessageWithPrefix(String.format("Usage: %s%s add name color", Gui.commandPrefix.getValue(), this.getNames()[0]));
                        break;
                    }
                    if (!CustomESP.names.containsKey(args[1])) {
                        CustomESP.names.put(args[1].toLowerCase(), Color.decode(args[2]));
                        break;
                    }
                    OringoClient.sendMessageWithPrefix("Name already added");
                    break;
                }
                case "remove": {
                    if (args.length == 2) {
                        CustomESP.names.remove(args[1]);
                        break;
                    }
                    break;
                }
                default: {
                    for (final String name2 : CustomESP.names.keySet()) {
                        OringoClient.sendMessageWithPrefix(name2);
                    }
                    break;
                }
            }
        }
    }
    
    public String getDescription() {
        return "Adds or removes names to Custom ESP module";
    }
}
