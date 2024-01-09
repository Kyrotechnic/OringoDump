//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.renderer;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ Render.class })
public abstract class RenderMixin
{
    @Shadow
    public <T extends Entity> void doRender(final T entity, final double x, final double y, final double z, final float entityYaw, final float partialTicks) {
    }
}
