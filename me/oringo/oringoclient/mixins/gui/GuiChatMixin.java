//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.gui;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraftforge.common.*;
import me.oringo.oringoclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(value = { GuiChat.class }, priority = 1)
public abstract class GuiChatMixin extends GuiScreenMixin
{
    @Inject(method = { "drawScreen" }, at = { @At("RETURN") })
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post((Event)new GuiChatEvent.DrawChatEvent(mouseX, mouseY))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "keyTyped" }, at = { @At("RETURN") })
    public void keyTyped(final char typedChar, final int keyCode, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post((Event)new GuiChatEvent.KeyTyped(keyCode, typedChar))) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "mouseClicked" }, at = { @At("RETURN") })
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton, final CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post((Event)new GuiChatEvent.MouseClicked(mouseX, mouseY, mouseButton))) {
            ci.cancel();
        }
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        MinecraftForge.EVENT_BUS.post((Event)new GuiChatEvent.MouseReleased(mouseX, mouseY, state));
    }
    
    @Inject(method = { "onGuiClosed" }, at = { @At("RETURN") })
    public void onGuiClosed(final CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post((Event)new GuiChatEvent.Closed());
    }
}
