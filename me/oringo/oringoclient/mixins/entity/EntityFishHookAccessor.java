//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.entity.projectile.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EntityFishHook.class })
public interface EntityFishHookAccessor
{
    @Accessor("inGround")
    boolean inGround();
    
    @Accessor("ticksCatchable")
    int getTicksCatchable();
}
