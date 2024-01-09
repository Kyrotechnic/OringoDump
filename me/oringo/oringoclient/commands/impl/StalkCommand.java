//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import net.minecraft.client.*;
import net.minecraft.entity.player.*;
import me.oringo.oringoclient.ui.notifications.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import java.awt.*;
import me.oringo.oringoclient.utils.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class StalkCommand extends Command
{
    public static UUID stalking;
    
    public StalkCommand() {
        super("stalk", new String[] { "hunt" });
    }
    
    public void execute(final String[] args) throws Exception {
        StalkCommand.stalking = null;
        if (args.length == 1) {
            for (final EntityPlayer player : Minecraft.getMinecraft().theWorld.playerEntities) {
                if (player.getName().equalsIgnoreCase(args[0])) {
                    StalkCommand.stalking = player.getUniqueID();
                    Notifications.showNotification("Oringo Client", "Enabled stalking!", 1000);
                    return;
                }
            }
            Notifications.showNotification("Player not found!", 1000, Notifications.NotificationType.ERROR);
            return;
        }
        Notifications.showNotification("Oringo Client", "Disabled Stalking!", 1000);
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (StalkCommand.stalking != null) {
            for (final EntityPlayer player : StalkCommand.mc.theWorld.playerEntities) {
                if (player.getUniqueID().equals(StalkCommand.stalking)) {
                    RenderUtils.tracerLine((Entity)player, event.partialTicks, 1.0f, Color.cyan);
                    break;
                }
            }
        }
    }
    
    public String getDescription() {
        return "Shows you a player";
    }
}
