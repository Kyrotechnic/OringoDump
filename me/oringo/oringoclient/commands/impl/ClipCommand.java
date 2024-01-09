//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import me.oringo.oringoclient.ui.notifications.*;

public class ClipCommand extends Command
{
    public ClipCommand() {
        super("clip", new String[] { "vclip" });
    }
    
    public void execute(final String[] args) throws Exception {
        if (args.length == 1) {
            ClipCommand.mc.thePlayer.setPosition(ClipCommand.mc.thePlayer.posX, ClipCommand.mc.thePlayer.posY + Double.parseDouble(args[0]), ClipCommand.mc.thePlayer.posZ);
        }
        else {
            Notifications.showNotification(".clip distance", 1500, Notifications.NotificationType.ERROR);
        }
    }
    
    public String getDescription() {
        return "Clips you up x blocks";
    }
}
