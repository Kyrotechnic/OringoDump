//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import java.util.*;
import net.minecraft.util.*;

public abstract class Command
{
    private final String[] names;
    protected static final Minecraft mc;
    
    public Command(final String name, final String... names) {
        final List<String> names2 = new ArrayList<String>();
        names2.add(name);
        names2.addAll(Arrays.asList(names));
        this.names = names2.toArray(new String[0]);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public String[] getNames() {
        return this.names;
    }
    
    public abstract void execute(final String[] p0) throws Exception;
    
    public abstract String getDescription();
    
    public String getLongDesc() {
        return this.getDescription();
    }
    
    public static void sendMessage(final String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText(message));
    }
    
    public static void sendMessageWithPrefix(final String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage((IChatComponent)new ChatComponentText("§bOringoClient §3» §7" + message));
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
