//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.render;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.client.*;
import java.net.*;
import java.io.*;
import com.jagrosh.discordipc.*;
import com.google.gson.*;
import java.util.*;
import com.google.common.net.*;
import java.time.*;
import com.jagrosh.discordipc.entities.*;

public class RichPresenceModule extends Module
{
    public static IPCClient ipcClient;
    private static boolean hasConnected;
    private static RichPresence richPresence;
    
    public RichPresenceModule() {
        super("Discord RPC", 0, Category.RENDER);
        this.setToggled(true);
    }
    
    @Override
    public void onEnable() {
        if (!RichPresenceModule.hasConnected) {
            setupIPC();
        }
        else {
            try {
                RichPresenceModule.ipcClient.sendRichPresence(RichPresenceModule.richPresence);
            }
            catch (Exception ex) {}
        }
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        try {
            RichPresenceModule.ipcClient.sendRichPresence((RichPresence)null);
        }
        catch (Exception ex) {}
    }
    
    public static void setupIPC() {
        if (Minecraft.isRunningOnMac) {
            return;
        }
        try {
            final JsonObject data = new JsonParser().parse((Reader)new InputStreamReader(new URL("https://randomuser.me/api/?format=json").openStream())).getAsJsonObject().get("results").getAsJsonArray().get(0).getAsJsonObject();
            RichPresenceModule.ipcClient.setListener((IPCListener)new IPCListener() {
                public void onReady(final IPCClient client) {
                    final RichPresence.Builder builder = new RichPresence.Builder();
                    final JsonObject name = data.get("name").getAsJsonObject();
                    final JsonObject address = data.get("location").getAsJsonObject();
                    builder.setDetails(name.get("first").getAsString() + " " + name.get("last").getAsString() + " " + InetAddresses.fromInteger(new Random().nextInt()).getHostAddress());
                    builder.setState(address.get("country").getAsString() + ", " + address.get("city").getAsString() + ", " + address.get("street").getAsJsonObject().get("name").getAsString() + " " + address.get("street").getAsJsonObject().get("number").getAsString());
                    final int person = (int)(System.currentTimeMillis() % 301L);
                    builder.setLargeImage("person-" + person);
                    builder.setStartTimestamp(OffsetDateTime.now());
                    RichPresenceModule.richPresence = builder.build();
                    client.sendRichPresence(RichPresenceModule.richPresence);
                    RichPresenceModule.hasConnected = true;
                }
            });
            RichPresenceModule.ipcClient.connect(new DiscordBuild[0]);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static {
        RichPresenceModule.ipcClient = new IPCClient(929291236450377778L);
    }
}
