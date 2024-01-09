//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraft.network.play.server.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.network.play.client.*;
import me.oringo.oringoclient.utils.*;
import me.oringo.oringoclient.events.*;

public class NoSlow extends Module
{
    public NumberSetting eatingSlowdown;
    public NumberSetting swordSlowdown;
    public NumberSetting bowSlowdown;
    public ModeSetting mode;
    private final MilliTimer blockDelay;
    
    public NoSlow() {
        super("NoSlow", 0, Category.COMBAT);
        this.eatingSlowdown = new NumberSetting("Eating slow", 1.0, 0.2, 1.0, 0.1);
        this.swordSlowdown = new NumberSetting("Sword slow", 1.0, 0.2, 1.0, 0.1);
        this.bowSlowdown = new NumberSetting("Bow slow", 1.0, 0.2, 1.0, 0.1);
        this.mode = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel", "Vanilla" });
        this.blockDelay = new MilliTimer();
        this.addSettings(this.mode, this.swordSlowdown, this.bowSlowdown, this.eatingSlowdown);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketReceivedEvent event) {
        if (event.packet instanceof S30PacketWindowItems && NoSlow.mc.thePlayer != null && this.isToggled() && this.mode.is("Hypixel") && NoSlow.mc.thePlayer.isUsingItem() && NoSlow.mc.thePlayer.getItemInUse().getItem() instanceof ItemSword) {
            event.setCanceled(true);
        }
    }
    
    @SubscribeEvent
    public void unUpdate(final MotionUpdateEvent.Post event) {
        if (this.isToggled() && NoSlow.mc.thePlayer.isUsingItem() && this.mode.is("Hypixel")) {
            if (this.blockDelay.hasTimePassed(250L) && NoSlow.mc.thePlayer.getItemInUse().getItem() instanceof ItemSword) {
                NoSlow.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(NoSlow.mc.thePlayer.getHeldItem()));
                NoSlow.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)NoSlow.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                NoSlow.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C0BPacketEntityAction((Entity)NoSlow.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                this.blockDelay.reset();
            }
            PacketUtils.sendPacketNoEvent((Packet<?>)new C09PacketHeldItemChange(NoSlow.mc.thePlayer.inventory.currentItem));
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (this.isToggled() && this.mode.is("Hypixel") && event.packet instanceof C08PacketPlayerBlockPlacement && ((C08PacketPlayerBlockPlacement)event.packet).getStack() != null && ((C08PacketPlayerBlockPlacement)event.packet).getStack().getItem() instanceof ItemSword) {
            this.blockDelay.reset();
        }
    }
}
