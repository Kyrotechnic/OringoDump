//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.qolfeatures.module.impl.other.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.mixins.packet.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.ui.notifications.*;
import me.oringo.oringoclient.events.*;

public class NoFall extends Module
{
    public ModeSetting mode;
    public ModeSetting hypixelSpoofMode;
    
    public NoFall() {
        super("NoFall", Category.PLAYER);
        this.mode = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel", "Packet", "NoGround" });
        this.hypixelSpoofMode = new ModeSetting("Spoof mode", "Fall", new String[] { "Always", "Fall" }) {
            @Override
            public boolean isHidden() {
                return !NoFall.this.mode.is("Hypixel");
            }
        };
        this.addSettings(this.mode, this.hypixelSpoofMode);
    }
    
    @Override
    public boolean isToggled() {
        return super.isToggled();
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onUpdate(final MotionUpdateEvent event) {
        if (this.isToggled()) {
            final String selected = this.mode.getSelected();
            switch (selected) {
                case "Hypixel": {
                    if ((NoFall.mc.thePlayer.fallDistance > 1.5 || this.hypixelSpoofMode.is("Always")) && Disabler.wasEnabled) {
                        event.setOnGround(true);
                        break;
                    }
                    break;
                }
                case "Packet": {
                    if (NoFall.mc.thePlayer.fallDistance > 1.5f) {
                        NoFall.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer(true));
                        NoFall.mc.thePlayer.fallDistance = 0.0f;
                        break;
                    }
                    break;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (event.packet instanceof C03PacketPlayer && this.isToggled()) {
            final String selected = this.mode.getSelected();
            switch (selected) {
                case "NoGround": {
                    ((C03Accessor)event.packet).setOnGround(false);
                    break;
                }
            }
        }
    }
    
    @Override
    public void onEnable() {
        if (!OringoClient.disabler.isToggled()) {
            Notifications.showNotification("Disabler not enabled", 3000, Notifications.NotificationType.WARNING);
        }
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
    }
}
