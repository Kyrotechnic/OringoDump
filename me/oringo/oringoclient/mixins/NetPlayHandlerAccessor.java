//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.network.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ NetHandlerPlayClient.class })
public interface NetPlayHandlerAccessor
{
    @Accessor("doneLoadingTerrain")
    void setDoneLoadingTerrain(final boolean p0);
    
    @Accessor("doneLoadingTerrain")
    boolean isDoneLoadingTerrain();
}
