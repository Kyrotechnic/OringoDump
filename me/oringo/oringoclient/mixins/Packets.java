//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins;

import org.spongepowered.asm.mixin.*;
import net.minecraft.util.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import net.minecraft.network.play.client.*;
import me.oringo.oringoclient.qolfeatures.module.impl.other.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;
import org.spongepowered.asm.mixin.injection.*;
import io.netty.util.concurrent.*;
import io.netty.channel.*;
import net.minecraft.network.play.server.*;
import me.oringo.oringoclient.events.*;

@Mixin({ NetworkManager.class })
public abstract class Packets
{
    private static Vec3 initialPosition;
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacket(final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (packet instanceof C03PacketPlayer && ((Disabler.timerSemi.isEnabled() && !((C03PacketPlayer)packet).isMoving()) || OringoClient.mc.thePlayer == null || OringoClient.mc.thePlayer.ticksExisted < 80.0f * TimerUtil.getTimer().timerSpeed)) {
            if (OringoClient.disabler.isToggled()) {
                Disabler.wasEnabled = true;
                callbackInfo.cancel();
                return;
            }
            Disabler.wasEnabled = false;
        }
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post((Event)new PacketSentEvent((Packet)packet))) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;)V" }, at = { @At("RETURN") }, cancellable = true)
    private void onSendPacketPost(final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post((Event)new PacketSentEvent.Post((Packet)packet))) {
            callbackInfo.cancel();
        }
        PacketUtils.noEvent.remove(packet);
    }
    
    @Inject(method = { "sendPacket(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;[Lio/netty/util/concurrent/GenericFutureListener;)V" }, at = { @At("HEAD") }, cancellable = true)
    private void onSendPacket2(final Packet packetIn, final GenericFutureListener<? extends Future<? super Void>> listener, final GenericFutureListener<? extends Future<? super Void>>[] listeners, final CallbackInfo ci) {
        if (packetIn instanceof C03PacketPlayer && (OringoClient.mc.thePlayer == null || OringoClient.mc.thePlayer.ticksExisted < 80.0f * TimerUtil.getTimer().timerSpeed)) {
            ci.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("HEAD") }, cancellable = true)
    private void onChannelReadHead(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (packet instanceof S01PacketJoinGame) {
            MinecraftForge.EVENT_BUS.post((Event)new WorldJoinEvent());
        }
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post((Event)new PacketReceivedEvent((Packet)packet, context))) {
            callbackInfo.cancel();
        }
    }
    
    @Inject(method = { "channelRead0" }, at = { @At("RETURN") }, cancellable = true)
    private void onPost(final ChannelHandlerContext context, final Packet<?> packet, final CallbackInfo callbackInfo) {
        if (!PacketUtils.noEvent.contains(packet) && MinecraftForge.EVENT_BUS.post((Event)new PacketReceivedEvent.Post((Packet)packet, context))) {
            callbackInfo.cancel();
        }
        PacketUtils.noEvent.remove(packet);
    }
}
