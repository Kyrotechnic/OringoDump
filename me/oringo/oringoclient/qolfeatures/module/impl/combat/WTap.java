//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.entity.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.mixins.entity.*;

public class WTap extends Module
{
    public ModeSetting mode;
    public BooleanSetting bow;
    
    public WTap() {
        super("WTap", Category.COMBAT);
        this.mode = new ModeSetting("mode", "Packet", new String[] { "Packet", "Extra Packet" });
        this.bow = new BooleanSetting("Bow", true);
        this.addSettings(this.mode, this.bow);
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (this.isToggled() && ((event.packet instanceof C02PacketUseEntity && ((C02PacketUseEntity)event.packet).getAction() == C02PacketUseEntity.Action.ATTACK) || (this.bow.isEnabled() && event.packet instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)event.packet).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM && WTap.mc.thePlayer.getHeldItem() != null && WTap.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow))) {
            final String selected = this.mode.getSelected();
            switch (selected) {
                case "Extra Packet": {
                    for (int i = 0; i < 4; ++i) {
                        WTap.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)WTap.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                        WTap.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)WTap.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    }
                    break;
                }
                default: {
                    if (WTap.mc.thePlayer.isSprinting()) {
                        WTap.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)WTap.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                    }
                    WTap.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C0BPacketEntityAction((Entity)WTap.mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
                    break;
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent.Post event) {
        if (this.isToggled() && ((event.packet instanceof C02PacketUseEntity && ((C02PacketUseEntity)event.packet).getAction() == C02PacketUseEntity.Action.ATTACK) || (this.bow.isEnabled() && event.packet instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)event.packet).getStatus() == C07PacketPlayerDigging.Action.RELEASE_USE_ITEM && WTap.mc.thePlayer.getHeldItem() != null && WTap.mc.thePlayer.getHeldItem().getItem() instanceof ItemBow)) && !WTap.mc.thePlayer.isSprinting()) {
            ((PlayerSPAccessor)WTap.mc.thePlayer).setServerSprintState(false);
        }
    }
}
