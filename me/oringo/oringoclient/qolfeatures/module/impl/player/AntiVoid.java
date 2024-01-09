//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.player;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import java.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.*;
import net.minecraft.network.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;
import java.util.concurrent.*;

public class AntiVoid extends Module
{
    public NumberSetting fallDistance;
    public ModeSetting mode;
    private static BooleanSetting disableFly;
    private static final Queue<C03PacketPlayer> packetQueue;
    private Vec3 lastPos;
    private double motionY;
    
    public AntiVoid() {
        super("Anti Void", 0, Category.PLAYER);
        this.fallDistance = new NumberSetting("Fall distance", 1.0, 0.5, 10.0, 0.1);
        this.mode = new ModeSetting("Mode", "Blink", new String[] { "Flag", "Blink" });
        this.lastPos = new Vec3(0.0, 0.0, 0.0);
        this.motionY = 0.0;
        this.addSettings(this.mode, this.fallDistance, AntiVoid.disableFly);
    }
    
    @SubscribeEvent
    public void onMovePre(final MotionUpdateEvent.Pre event) {
        if (this.isToggled() && (!AntiVoid.disableFly.isEnabled() || !OringoClient.fly.isToggled()) && this.mode.is("Flag") && AntiVoid.mc.thePlayer.fallDistance > this.fallDistance.getValue() && PlayerUtils.isOverVoid()) {
            event.setPosition(AntiVoid.mc.thePlayer.posX + 100.0, AntiVoid.mc.thePlayer.posY + 100.0, AntiVoid.mc.thePlayer.posZ + 100.0);
        }
    }
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPacket(final PacketSentEvent event) {
        if (this.isToggled() && this.mode.is("Blink") && event.packet instanceof C03PacketPlayer && (!AntiVoid.disableFly.isEnabled() || !OringoClient.fly.isToggled())) {
            if (PlayerUtils.isOverVoid()) {
                event.setCanceled(true);
                AntiVoid.packetQueue.offer((C03PacketPlayer)event.packet);
                if (AntiVoid.mc.thePlayer.fallDistance > this.fallDistance.getValue()) {
                    AntiVoid.packetQueue.clear();
                    AntiVoid.mc.thePlayer.fallDistance = 0.0f;
                    AntiVoid.mc.thePlayer.setPosition(this.lastPos.xCoord, this.lastPos.yCoord, this.lastPos.zCoord);
                    AntiVoid.mc.thePlayer.setVelocity(0.0, this.motionY, 0.0);
                }
            }
            else {
                this.lastPos = AntiVoid.mc.thePlayer.getPositionVector();
                this.motionY = AntiVoid.mc.thePlayer.motionY;
                while (!AntiVoid.packetQueue.isEmpty()) {
                    PacketUtils.sendPacketNoEvent((Packet<?>)AntiVoid.packetQueue.poll());
                }
            }
        }
    }
    
    public static boolean isBlinking() {
        return !AntiVoid.packetQueue.isEmpty();
    }
    
    @Override
    public void onDisable() {
        AntiVoid.packetQueue.clear();
    }
    
    @SubscribeEvent
    public void onRespawn(final WorldJoinEvent event) {
        AntiVoid.packetQueue.clear();
    }
    
    static {
        AntiVoid.disableFly = new BooleanSetting("Disable fly", true);
        packetQueue = new ConcurrentLinkedQueue<C03PacketPlayer>();
    }
}
