//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import me.oringo.oringoclient.config.*;
import me.oringo.oringoclient.ui.notifications.*;

public class ConfigCommand extends Command
{
    public ConfigCommand() {
        super("config", new String[0]);
    }
    
    public void execute(final String[] args) throws Exception {
        if (args.length > 0) {
            final String name = String.join(" ", (CharSequence[])args).replaceFirst(args[0] + " ", "");
            final String lowerCase = args[0].toLowerCase();
            switch (lowerCase) {
                case "save": {
                    if (ConfigManager.saveConfig(ConfigManager.configPath + String.format("%s.json", name), true)) {
                        Notifications.showNotification("Oringo Client", "Successfully saved config " + name, 3000);
                        break;
                    }
                    Notifications.showNotification("Saving " + name + " failed", 3000, Notifications.NotificationType.ERROR);
                    break;
                }
                case "load": {
                    if (ConfigManager.loadConfig(ConfigManager.configPath + String.format("%s.json", name))) {
                        Notifications.showNotification("Oringo Client", "Config " + name + " loaded", 3000);
                        break;
                    }
                    Notifications.showNotification("Loading config " + name + " failed", 3000, Notifications.NotificationType.ERROR);
                    break;
                }
            }
        }
        else {
            try {
                Notifications.showNotification("Oringo Client", ".config load/save name", 3000);
                ConfigManager.openExplorer();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public String getDescription() {
        return "Save or load a config. .config to open explorer";
    }
}
