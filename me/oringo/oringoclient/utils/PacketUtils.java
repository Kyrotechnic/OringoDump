//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.utils;

import java.util.*;
import net.minecraft.network.*;
import me.oringo.oringoclient.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.play.client.*;
import java.lang.reflect.*;

public class PacketUtils
{
    public static ArrayList<Packet<?>> noEvent;
    
    public static void sendPacketNoEvent(final Packet<?> packet) {
        PacketUtils.noEvent.add(packet);
        OringoClient.mc.getNetHandler().getNetworkManager().sendPacket((Packet)packet);
    }
    
    public static C03PacketPlayer.C06PacketPlayerPosLook getResponse(final S08PacketPlayerPosLook packet) {
        double x = packet.getX();
        double y = packet.getY();
        double z = packet.getZ();
        float yaw = packet.getYaw();
        float pitch = packet.getPitch();
        if (packet.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
            x += OringoClient.mc.thePlayer.posX;
        }
        if (packet.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
            y += OringoClient.mc.thePlayer.posY;
        }
        if (packet.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
            z += OringoClient.mc.thePlayer.posZ;
        }
        if (packet.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
            pitch += OringoClient.mc.thePlayer.rotationPitch;
        }
        if (packet.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
            yaw += OringoClient.mc.thePlayer.rotationYaw;
        }
        return new C03PacketPlayer.C06PacketPlayerPosLook(x, y, z, yaw % 360.0f, pitch % 360.0f, false);
    }
    
    public static String packetToString(final Packet<?> packet) {
        final StringBuilder postfix = new StringBuilder();
        boolean first = true;
        for (final Field field : packet.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                postfix.append(first ? "" : ", ").append(field.get(packet));
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            first = false;
        }
        return packet.getClass().getSimpleName() + String.format("{%s}", postfix);
    }
    
    static {
        PacketUtils.noEvent = new ArrayList<Packet<?>>();
    }
}
