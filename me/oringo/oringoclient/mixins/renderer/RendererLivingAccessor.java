//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.renderer;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ RendererLivingEntity.class })
public interface RendererLivingAccessor
{
    @Invoker("renderModel")
     <T extends EntityLivingBase> void renderModel(final T p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6);
    
    @Invoker("renderLayers")
    void renderLayers(final EntityLivingBase p0, final float p1, final float p2, final float p3, final float p4, final float p5, final float p6, final float p7);
}
