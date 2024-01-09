//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Test extends Module
{
    public Test() {
        super("Test", Category.OTHER);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
    }
    
    @Override
    public boolean isDevOnly() {
        return true;
    }
}
