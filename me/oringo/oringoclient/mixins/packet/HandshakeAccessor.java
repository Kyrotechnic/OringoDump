//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.packet;

import org.spongepowered.asm.mixin.*;
import net.minecraft.network.handshake.client.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ C00Handshake.class })
public interface HandshakeAccessor
{
    @Accessor
    String getIp();
    
    @Accessor
    void setProtocolVersion(final int p0);
}
