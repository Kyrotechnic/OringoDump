//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.*;
import net.minecraft.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.utils.*;
import java.util.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.network.play.client.*;

public class Derp extends Module
{
    private ArrayList<Packet<?>> packets;
    
    public Derp() {
        super("Derp", Category.OTHER);
        this.packets = new ArrayList<Packet<?>>();
    }
    
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onUpdate(final MotionUpdateEvent.Pre event) {
        if (!this.isToggled() || !this.packets.isEmpty()) {
            return;
        }
        event.yaw = (float)(new Random().nextInt(181) * ((Derp.mc.thePlayer.ticksExisted % 2 == 0) ? -1 : 1));
        event.pitch = (float)(new Random().nextInt(181) - 90);
    }
    
    @SubscribeEvent
    public void onUpdatePost(final MotionUpdateEvent.Post e) {
        if (this.packets.isEmpty()) {
            return;
        }
        for (final Packet<?> packet : this.packets) {
            PacketUtils.sendPacketNoEvent(packet);
        }
        this.packets.clear();
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (!this.isToggled()) {
            return;
        }
        if (event.packet instanceof C02PacketUseEntity || event.packet instanceof C08PacketPlayerBlockPlacement || event.packet instanceof C07PacketPlayerDigging || event.packet instanceof C0APacketAnimation || event.packet instanceof C01PacketChatMessage || event.packet instanceof C09PacketHeldItemChange) {
            this.packets.add((Packet<?>)event.packet);
            event.setCanceled(true);
        }
    }
}
