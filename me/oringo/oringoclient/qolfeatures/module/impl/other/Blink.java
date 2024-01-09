//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.other;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import java.util.*;
import net.minecraft.network.*;
import java.util.concurrent.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import net.minecraftforge.fml.common.network.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.network.play.client.*;
import me.oringo.oringoclient.events.*;
import me.oringo.oringoclient.utils.*;

public class Blink extends Module
{
    public BooleanSetting onlyPos;
    public BooleanSetting pulse;
    public NumberSetting pulseTicks;
    private Queue<Packet<?>> packets;
    private TickTimer timer;
    
    public Blink() {
        super("Blink", Category.OTHER);
        this.onlyPos = new BooleanSetting("Only pos packets", false);
        this.pulse = new BooleanSetting("Pulse", false);
        this.pulseTicks = new NumberSetting("Pulse ticks", 10.0, 1.0, 100.0, 1.0) {
            @Override
            public boolean isHidden() {
                return !Blink.this.pulse.isEnabled();
            }
        };
        this.packets = new ConcurrentLinkedQueue<Packet<?>>();
        this.timer = new TickTimer();
        this.addSettings(this.onlyPos, this.pulse, this.pulseTicks);
    }
    
    @Override
    public void onEnable() {
        this.timer.reset();
    }
    
    @SubscribeEvent
    public void onDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        this.packets.clear();
        if (this.isToggled()) {
            this.setToggled(false);
        }
    }
    
    @SubscribeEvent
    public void onUpdate(final PlayerUpdateEvent event) {
        this.timer.updateTicks();
        if (this.timer.passed((int)this.pulseTicks.getValue()) && this.pulse.isEnabled()) {
            this.sendPackets();
            this.timer.reset();
        }
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (this.isToggled() && Blink.mc.thePlayer == null) {
            this.packets.clear();
            this.setToggled(false);
            return;
        }
        if (this.isToggled() && (event.packet instanceof C03PacketPlayer || !this.onlyPos.isEnabled())) {
            event.setCanceled(true);
            this.packets.offer((Packet<?>)event.packet);
        }
    }
    
    @SubscribeEvent
    public void onWorld(final WorldJoinEvent event) {
        this.packets.clear();
        if (this.isToggled()) {
            this.toggle();
        }
    }
    
    private void sendPackets() {
        if (Blink.mc.getNetHandler() != null) {
            while (!this.packets.isEmpty()) {
                PacketUtils.sendPacketNoEvent(this.packets.poll());
            }
        }
    }
    
    @Override
    public void onDisable() {
        this.sendPackets();
    }
}
