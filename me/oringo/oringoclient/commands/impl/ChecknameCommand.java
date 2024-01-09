//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import me.oringo.oringoclient.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.inventory.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import com.mojang.realmsclient.gui.*;
import me.oringo.oringoclient.ui.notifications.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ChecknameCommand extends Command
{
    public static String profileView;
    
    public ChecknameCommand() {
        super("checkname", new String[] { "shownicker", "denick", "revealname" });
    }
    
    public void execute(final String[] args) throws Exception {
        if (args.length != 1) {
            OringoClient.sendMessageWithPrefix("/checkname [IGN]");
            return;
        }
        for (final EntityPlayer entity : ChecknameCommand.mc.theWorld.playerEntities) {
            if (entity.getName().equalsIgnoreCase(args[0])) {
                if (entity.getDistanceToEntity((Entity)ChecknameCommand.mc.thePlayer) > 6.0f) {
                    OringoClient.sendMessageWithPrefix("You are too far away!");
                    return;
                }
                if (ChecknameCommand.mc.thePlayer.getHeldItem() != null) {
                    OringoClient.sendMessageWithPrefix("Your hand must be empty!");
                    return;
                }
                ChecknameCommand.mc.playerController.interactWithEntitySendPacket((EntityPlayer)ChecknameCommand.mc.thePlayer, (Entity)entity);
                ChecknameCommand.profileView = args[0];
                return;
            }
        }
        OringoClient.sendMessageWithPrefix("Invalid name");
    }
    
    @SubscribeEvent
    public void onGui(final GuiOpenEvent event) {
        if (event.gui instanceof GuiChest && ChecknameCommand.profileView != null && ((ContainerChest)((GuiChest)event.gui).inventorySlots).getLowerChestInventory().getName().toLowerCase().startsWith(ChecknameCommand.profileView.toLowerCase())) {
            final ItemStack is;
            String name;
            new Thread(() -> {
                try {
                    Thread.sleep(100L);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                is = Minecraft.getMinecraft().thePlayer.openContainer.getSlot(22).getStack();
                if (is != null && is.getItem().equals(Items.skull)) {
                    name = is.serializeNBT().getCompoundTag("tag").getCompoundTag("SkullOwner").getString("Name");
                    Minecraft.getMinecraft().thePlayer.closeScreen();
                    Notifications.showNotification("Oringo Client", "Real name: " + ChatFormatting.GOLD + name.replaceFirst("§", ""), 4000);
                }
                ChecknameCommand.profileView = null;
            }).start();
        }
    }
    
    public String getDescription() {
        return null;
    }
}
