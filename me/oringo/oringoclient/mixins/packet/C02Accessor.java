//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.packet;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.play.client.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ C02PacketUseEntity.class })
public interface C02Accessor
{
    @Accessor
    void setEntityId(final int p0);
    
    @Accessor
    void setAction(final C02PacketUseEntity.Action p0);
}
