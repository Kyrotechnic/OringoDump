//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.gui;

import net.minecraft.client.gui.inventory.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.inventory.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.utils.font.*;
import net.minecraft.client.renderer.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import me.oringo.oringoclient.qolfeatures.module.impl.render.*;
import org.lwjgl.opengl.*;

@Mixin({ GuiContainer.class })
public abstract class GuiContainerMixin extends GuiScreenMixin
{
    @Shadow
    public Container inventorySlots;
    
    @Inject(method = { "drawScreen" }, at = { @At("HEAD") }, cancellable = true)
    public void onDrawScreen(final int k1, final int slot, final float i1, final CallbackInfo ci) {
        if (this.inventorySlots instanceof ContainerChest && OringoClient.guiMove.shouldHideGui((ContainerChest)this.inventorySlots)) {
            this.mc.inGameHasFocus = true;
            this.mc.mouseHelper.grabMouseCursor();
            final ScaledResolution res = new ScaledResolution(this.mc);
            Fonts.robotoBig.drawSmoothCenteredStringWithShadow("In terminal!", res.getScaledWidth() / 2, res.getScaledHeight() / 2, OringoClient.clickGui.getColor().getRGB());
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.BackgroundDrawnEvent((GuiScreen)this));
            ci.cancel();
        }
    }
    
    @Inject(method = { "drawScreen" }, at = { @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/inventory/GuiContainer;drawGuiContainerBackgroundLayer(FII)V") }, cancellable = true)
    public void onDrawBackground(final int k1, final int slot, final float i1, final CallbackInfo ci) {
        if (PopupAnimation.shouldScale((GuiScreen)this)) {
            GL11.glPushMatrix();
            PopupAnimation.doScaling();
        }
    }
    
    @Inject(method = { "drawScreen" }, at = { @At("RETURN") }, cancellable = true)
    public void onDrawScreenPost(final int k1, final int slot, final float i1, final CallbackInfo ci) {
        if (PopupAnimation.shouldScale((GuiScreen)this)) {
            GL11.glPopMatrix();
        }
    }
}
