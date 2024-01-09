//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ GuiScreen.class })
public abstract class GuiScreenMixin extends GuiMixin implements GuiYesNoCallback
{
    @Shadow
    public Minecraft mc;
    @Shadow
    public int height;
    @Shadow
    public int width;
    
    @Shadow
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
    }
    
    @Shadow
    public abstract void drawScreen(final int p0, final int p1, final float p2);
}
