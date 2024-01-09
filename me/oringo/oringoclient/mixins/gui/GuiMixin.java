//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.gui;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ Gui.class })
public abstract class GuiMixin
{
    @Shadow
    public static void drawRect(final int left, final int top, final int right, final int bottom, final int color) {
    }
    
    @Shadow
    protected abstract void drawGradientRect(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
    
    @Shadow
    public abstract void drawTexturedModalRect(final int p0, final int p1, final int p2, final int p3, final int p4, final int p5);
}
