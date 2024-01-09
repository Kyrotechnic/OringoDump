//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import me.oringo.oringoclient.ui.notifications.*;

public class TestCommand extends Command
{
    public TestCommand() {
        super("test", new String[0]);
    }
    
    public void execute(final String[] args) throws Exception {
        Notifications.showNotification("Test", 3000, Notifications.NotificationType.INFO);
        Notifications.showNotification("Test", 3000, Notifications.NotificationType.ERROR);
        Notifications.showNotification("Test", 3000, Notifications.NotificationType.WARNING);
    }
    
    public String getDescription() {
        return null;
    }
}
