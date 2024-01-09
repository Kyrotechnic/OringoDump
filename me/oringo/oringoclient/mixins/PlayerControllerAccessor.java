//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.multiplayer.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ PlayerControllerMP.class })
public interface PlayerControllerAccessor
{
    @Accessor
    int getCurrentPlayerItem();
    
    @Accessor
    void setCurrentPlayerItem(final int p0);
}
