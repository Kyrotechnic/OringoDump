//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.utils;

import net.minecraftforge.fml.common.network.*;
import me.oringo.oringoclient.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ServerUtils
{
    public static ServerUtils instance;
    private static boolean onHypixel;
    
    @SubscribeEvent
    public void onPacketRecived(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (!event.isLocal) {
            ServerUtils.onHypixel = OringoClient.mc.getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
        }
    }
    
    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        ServerUtils.onHypixel = false;
        System.out.println("Detected leaving hypixel");
    }
    
    public static boolean isOnHypixel() {
        return ServerUtils.onHypixel;
    }
    
    static {
        ServerUtils.instance = new ServerUtils();
    }
}
