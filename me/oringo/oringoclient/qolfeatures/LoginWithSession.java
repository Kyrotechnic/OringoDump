//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures;

import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import me.oringo.oringoclient.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.client.*;
import javax.swing.*;
import com.google.gson.*;
import java.net.*;
import java.io.*;
import java.lang.reflect.*;

public class LoginWithSession
{
    private Session original;
    
    public LoginWithSession() {
        this.original = null;
    }
    
    @SubscribeEvent
    public void onGuiCreate(final GuiScreenEvent.InitGuiEvent.Post event) {
        if (OringoClient.devMode && event.gui instanceof GuiMainMenu) {
            event.buttonList.add(new GuiButton(2137, 5, 5, 100, 20, "Login"));
            event.buttonList.add(new GuiButton(21370, 115, 5, 100, 20, "Save"));
        }
    }
    
    @SubscribeEvent
    public void onClick(final GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.gui instanceof GuiMainMenu) {
            if (event.button.id == 2137) {
                if (this.original == null) {
                    this.original = Minecraft.getMinecraft().getSession();
                }
                final String login = JOptionPane.showInputDialog("Login");
                if (login == null || login.isEmpty()) {
                    return;
                }
                if (login.equalsIgnoreCase("reset")) {
                    try {
                        final Field session = Minecraft.class.getDeclaredField("session");
                        session.setAccessible(true);
                        session.set(Minecraft.getMinecraft(), this.original);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
                try {
                    final Field session = Minecraft.class.getDeclaredField("session");
                    session.setAccessible(true);
                    final String username = new JsonParser().parse((Reader)new InputStreamReader(new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + login.split(": ")[1]).openStream())).getAsJsonObject().get("name").getAsString();
                    session.set(Minecraft.getMinecraft(), new Session(username, login.split(": ")[1], login.split(": ")[0], "mojang"));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (event.button.id == 21370) {
                try {
                    final BufferedWriter savedData = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("savedData")));
                    savedData.write(Minecraft.getMinecraft().getSession().getToken() + ": " + Minecraft.getMinecraft().getSession().getPlayerID());
                    savedData.close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
