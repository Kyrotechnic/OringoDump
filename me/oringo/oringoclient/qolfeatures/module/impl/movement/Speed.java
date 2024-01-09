//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.movement;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.mixins.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.potion.*;
import net.minecraft.client.entity.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.utils.*;
import net.minecraftforge.client.event.*;
import net.minecraft.client.gui.*;
import me.oringo.oringoclient.utils.font.*;
import me.oringo.oringoclient.ui.notifications.*;
import net.minecraft.util.*;
import net.minecraft.stats.*;
import me.oringo.oringoclient.events.*;
import net.minecraft.network.play.server.*;

public class Speed extends Module
{
    public BooleanSetting stopOnDisable;
    public BooleanSetting disableOnFlag;
    public BooleanSetting sneak;
    public NumberSetting timer;
    public NumberSetting sneakTimer;
    private static MilliTimer disable;
    int airTicks;
    boolean canApplySpeed;
    
    public Speed() {
        super("Speed", Category.MOVEMENT);
        this.stopOnDisable = new BooleanSetting("Stop on disable", true);
        this.disableOnFlag = new BooleanSetting("Disable on flag", true);
        this.sneak = new BooleanSetting("Sneak timer", true);
        this.timer = new NumberSetting("Timer", 1.0, 0.1, 3.0, 0.05);
        this.sneakTimer = new NumberSetting("SneakTimer", 1.0, 0.1, 3.0, 0.05, aBoolean -> !this.sneak.isEnabled());
        this.addSettings(this.stopOnDisable, this.disableOnFlag, this.sneak, this.sneakTimer, this.timer);
    }
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onUpdate(final MotionUpdateEvent.Pre event) {
        if (this.isToggled() && !isDisabled()) {
            ((MinecraftAccessor)Speed.mc).getTimer().timerSpeed = (float)((this.sneak.isEnabled() && Speed.mc.gameSettings.keyBindSneak.isKeyDown()) ? this.sneakTimer.getValue() : this.timer.getValue());
            if (MovementUtils.isMoving()) {
                event.setYaw(MovementUtils.getYaw());
            }
        }
    }
    
    @SubscribeEvent
    public void onMove(final MoveEvent event) {
        if (this.isToggled() && !isDisabled()) {
            if (MovementUtils.isMoving()) {
                double multi = 1.0;
                if (Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed) && this.canApplySpeed) {
                    multi += 0.015f * (Speed.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
                }
                if (Speed.mc.thePlayer.capabilities.getWalkSpeed() > 0.2f) {
                    multi = 0.8999999761581421;
                }
                final EntityPlayerSP thePlayer = Speed.mc.thePlayer;
                thePlayer.motionX *= multi;
                final EntityPlayerSP thePlayer2 = Speed.mc.thePlayer;
                thePlayer2.motionZ *= multi;
            }
            else {
                Speed.mc.thePlayer.motionX = 0.0;
                Speed.mc.thePlayer.motionZ = 0.0;
            }
            event.setX(Speed.mc.thePlayer.motionX).setZ(Speed.mc.thePlayer.motionZ);
        }
    }
    
    @SubscribeEvent
    public void onUpdateMove(final MoveStateUpdateEvent event) {
        if (this.isToggled() && !isDisabled()) {
            event.setSneak(false);
        }
    }
    
    @SubscribeEvent
    public void onMoveFlying(final MoveHeadingEvent event) {
        if (this.isToggled() && MovementUtils.isMoving() && !isDisabled()) {
            if (Speed.mc.thePlayer.onGround) {
                this.jump();
                this.canApplySpeed = Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed);
                this.airTicks = 0;
            }
            else {
                ++this.airTicks;
                event.setOnGround(true);
                if (!Speed.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    if (!this.canApplySpeed) {
                        if (Speed.mc.thePlayer.fallDistance < 0.4 && Speed.mc.thePlayer.capabilities.getWalkSpeed() < 0.2f) {
                            event.setFriction2Multi(0.95f);
                        }
                    }
                    else {
                        event.setFriction2Multi(0.87f);
                    }
                }
            }
        }
    }
    
    public static boolean isDisabled() {
        return (OringoClient.scaffold.isToggled() && Scaffold.disableSpeed.isEnabled()) || !Speed.disable.hasTimePassed(2000L);
    }
    
    private String getBPS() {
        final double bps = Math.hypot(Speed.mc.thePlayer.posX - Speed.mc.thePlayer.prevPosX, Speed.mc.thePlayer.posZ - Speed.mc.thePlayer.prevPosZ) * TimerUtil.getTimer().timerSpeed * 20.0;
        return String.format("%.2f", bps);
    }
    
    @SubscribeEvent
    public void onRender(final RenderGameOverlayEvent.Post event) {
        if (Speed.mc.theWorld == null || Speed.mc.thePlayer == null || !this.isToggled()) {
            return;
        }
        if (event.type == RenderGameOverlayEvent.ElementType.ALL) {
            final ScaledResolution resolution = new ScaledResolution(Speed.mc);
            Fonts.robotoMediumBold.drawSmoothCenteredStringWithShadow(this.getBPS(), 20.0, resolution.getScaledHeight() - 20, OringoClient.clickGui.getColor().getRGB());
        }
    }
    
    @Override
    public void onDisable() {
        if (TimerUtil.getTimer() != null) {
            ((MinecraftAccessor)Speed.mc).getTimer().timerSpeed = 1.0f;
            this.canApplySpeed = false;
        }
        if (Speed.mc.thePlayer != null && this.stopOnDisable.isEnabled()) {
            Speed.mc.thePlayer.motionX = 0.0;
            Speed.mc.thePlayer.motionZ = 0.0;
        }
    }
    
    @Override
    public void onEnable() {
        this.airTicks = 0;
        if (!OringoClient.disabler.isToggled()) {
            Notifications.showNotification("Disabler not enabled", 3000, Notifications.NotificationType.WARNING);
        }
    }
    
    private void jump() {
        Speed.mc.thePlayer.motionY = 0.41999998688697815;
        if (Speed.mc.thePlayer.isSprinting()) {
            final float f = MovementUtils.getYaw() * 0.017453292f;
            final EntityPlayerSP thePlayer = Speed.mc.thePlayer;
            thePlayer.motionX -= MathHelper.sin(f) * 0.2f;
            final EntityPlayerSP thePlayer2 = Speed.mc.thePlayer;
            thePlayer2.motionZ += MathHelper.cos(f) * 0.2f;
        }
        Speed.mc.thePlayer.isAirBorne = true;
        Speed.mc.thePlayer.triggerAchievement(StatList.jumpStat);
        if (Speed.mc.thePlayer.isSprinting()) {
            Speed.mc.thePlayer.addExhaustion(0.8f);
        }
        else {
            Speed.mc.thePlayer.addExhaustion(0.2f);
        }
    }
    
    @SubscribeEvent(receiveCanceled = true)
    public void onPacket(final PacketReceivedEvent event) {
        if (event.packet instanceof S08PacketPlayerPosLook && this.disableOnFlag.isEnabled()) {
            if (!isDisabled() && this.isToggled()) {
                Notifications.showNotification("Oringo Client", "Disabled speed due to a flag", 1500);
                ((MinecraftAccessor)Speed.mc).getTimer().timerSpeed = 1.0f;
                this.canApplySpeed = false;
                if (Speed.mc.thePlayer != null) {
                    Speed.mc.thePlayer.motionX = 0.0;
                    Speed.mc.thePlayer.motionZ = 0.0;
                }
            }
            Speed.disable.reset();
        }
    }
    
    static {
        Speed.disable = new MilliTimer();
    }
}
