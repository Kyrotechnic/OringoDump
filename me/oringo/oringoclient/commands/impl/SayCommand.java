//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import joptsimple.internal.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class SayCommand extends Command
{
    public SayCommand() {
        super("say", new String[0]);
    }
    
    public void execute(final String[] args) throws Exception {
        SayCommand.mc.getNetHandler().addToSendQueue((Packet)new C01PacketChatMessage(Strings.join(args, " ")));
    }
    
    public String getDescription() {
        return null;
    }
}
