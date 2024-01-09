//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.commands.impl;

import me.oringo.oringoclient.commands.*;
import me.oringo.oringoclient.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class BanCommand extends Command
{
    public BanCommand() {
        super("selfban", new String[0]);
    }
    
    public void execute(final String[] args) throws Exception {
        if (args.length == 1 && args[0].equals("confirm")) {
            OringoClient.sendMessageWithPrefix("You will get banned in ~3 seconds!");
            for (int i = 0; i < 10; ++i) {
                BanCommand.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(new Random().nextInt(), new Random().nextInt(), new Random().nextInt()), 1, BanCommand.mc.thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
            }
        }
        else {
            OringoClient.sendMessageWithPrefix("/selfban confirm");
        }
    }
    
    public String getDescription() {
        return null;
    }
}
