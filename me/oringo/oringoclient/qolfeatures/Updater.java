//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures;

import net.minecraftforge.client.event.*;
import me.oringo.oringoclient.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Updater
{
    @SubscribeEvent
    public void onGuiCreate(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.gui instanceof GuiMainMenu && OringoClient.shouldUpdate) {
            event.buttonList.add(new GuiButton(-2137, 5, 50, 150, 20, "Update Oringo Client"));
        }
    }
    
    @SubscribeEvent
    public void onClick(final GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.gui instanceof GuiMainMenu && event.button.id == -2137) {
            try {
                Desktop.getDesktop().browse(new URI(OringoClient.vers[1]));
                OringoClient.mc.shutdown();
            }
            catch (IOException | URISyntaxException ex2) {
                final Exception ex;
                final Exception e = ex;
                e.printStackTrace();
            }
        }
    }
}
