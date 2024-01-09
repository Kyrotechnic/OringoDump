//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import me.oringo.oringoclient.mixins.entity.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.fml.common.eventhandler.*;
import me.oringo.oringoclient.events.*;

public class Criticals extends Module
{
    public static final NumberSetting delay;
    public static final NumberSetting hurtTime;
    public static final ModeSetting mode;
    private C02PacketUseEntity attack;
    private int ticks;
    private float[] offsets;
    private MilliTimer timer;
    
    public Criticals() {
        super("Criticals", Category.COMBAT);
        this.ticks = 0;
        this.offsets = new float[] { 0.0625f, 0.03125f };
        this.timer = new MilliTimer();
        this.addSettings(Criticals.mode, Criticals.delay, Criticals.hurtTime);
    }
    
    @SubscribeEvent
    public void onUpdate(final MotionUpdateEvent.Pre event) {
        if (this.isToggled() && this.attack != null && !OringoClient.speed.isToggled()) {
            final String selected = Criticals.mode.getSelected();
            switch (selected) {
                case "Hypixel 2": {
                    if (Criticals.mc.thePlayer.onGround && event.onGround && this.attack.getEntityFromWorld((World)Criticals.mc.theWorld) instanceof EntityLivingBase && ((EntityLivingBase)this.attack.getEntityFromWorld((World)Criticals.mc.theWorld)).hurtTime <= Criticals.hurtTime.getValue()) {
                        Criticals.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(((PlayerSPAccessor)Criticals.mc.thePlayer).getLastReportedPosX(), ((PlayerSPAccessor)Criticals.mc.thePlayer).getLastReportedPosY() + this.offsets[0] + MathUtil.getRandomInRange(0.0, 0.0010000000474974513), ((PlayerSPAccessor)Criticals.mc.thePlayer).getLastReportedPosZ(), false));
                        Criticals.mc.getNetHandler().getNetworkManager().sendPacket((Packet)new C03PacketPlayer.C04PacketPlayerPosition(((PlayerSPAccessor)Criticals.mc.thePlayer).getLastReportedPosX(), ((PlayerSPAccessor)Criticals.mc.thePlayer).getLastReportedPosY() + this.offsets[1] + MathUtil.getRandomInRange(0.0, 0.0010000000474974513), ((PlayerSPAccessor)Criticals.mc.thePlayer).getLastReportedPosZ(), false));
                        PacketUtils.sendPacketNoEvent((Packet<?>)this.attack);
                        this.attack = null;
                        OringoClient.sendMessageWithPrefix("Hypixel");
                        break;
                    }
                    this.attack = null;
                    break;
                }
                case "Hypixel": {
                    if (Criticals.mc.thePlayer.onGround && this.attack != null && event.onGround && this.attack.getEntityFromWorld((World)Criticals.mc.theWorld) instanceof EntityLivingBase && ((EntityLivingBase)this.attack.getEntityFromWorld((World)Criticals.mc.theWorld)).hurtTime <= Criticals.hurtTime.getValue()) {
                        switch (this.ticks++) {
                            case 0:
                            case 1: {
                                event.y += this.offsets[this.ticks - 1] + MathUtil.getRandomInRange(0.0, 0.0010000000474974513);
                                event.setOnGround(false);
                                OringoClient.sendMessageWithPrefix("Hypixel 2");
                                break;
                            }
                            case 2: {
                                PacketUtils.sendPacketNoEvent((Packet<?>)this.attack);
                                this.ticks = 0;
                                this.attack = null;
                                break;
                            }
                        }
                        break;
                    }
                    this.ticks = 0;
                    this.attack = null;
                    break;
                }
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.attack = null;
        this.ticks = 0;
    }
    
    @SubscribeEvent
    public void onPacket(final PacketSentEvent event) {
        if (this.isToggled() && !OringoClient.speed.isToggled() && event.packet instanceof C02PacketUseEntity && ((C02PacketUseEntity)event.packet).getAction() == C02PacketUseEntity.Action.ATTACK && ((C02PacketUseEntity)event.packet).getEntityFromWorld((World)Criticals.mc.theWorld) instanceof EntityLivingBase && ((EntityLivingBase)((C02PacketUseEntity)event.packet).getEntityFromWorld((World)Criticals.mc.theWorld)).hurtTime <= Criticals.hurtTime.getValue() && this.timer.hasTimePassed((long)Criticals.delay.getValue())) {
            this.attack = (C02PacketUseEntity)event.packet;
            event.setCanceled(true);
            this.timer.reset();
        }
    }
    
    static {
        delay = new NumberSetting("Delay", 500.0, 0.0, 2000.0, 50.0);
        hurtTime = new NumberSetting("Hurt time", 2.0, 0.0, 10.0, 1.0);
        mode = new ModeSetting("Mode", "Hypixel", new String[] { "Hypixel", "Hypixel 2" });
    }
}
