//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.qolfeatures.module.impl.combat;

import me.oringo.oringoclient.qolfeatures.module.*;
import me.oringo.oringoclient.qolfeatures.module.settings.impl.*;
import me.oringo.oringoclient.qolfeatures.module.settings.*;
import me.oringo.oringoclient.*;
import me.oringo.oringoclient.qolfeatures.module.impl.movement.*;
import net.minecraft.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.client.event.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import java.awt.*;
import me.oringo.oringoclient.events.*;
import me.oringo.oringoclient.utils.*;

public class TargetStrafe extends Module
{
    public ModeSetting mode;
    public NumberSetting distance;
    public BooleanSetting controllable;
    public BooleanSetting jumpOnly;
    public BooleanSetting thirdPerson;
    public BooleanSetting smart;
    public BooleanSetting liquidCheck;
    public BooleanSetting voidCheck;
    private MilliTimer strafeDelay;
    private int prev;
    double lastx;
    double lasty;
    private float strafe;
    
    public TargetStrafe() {
        super("Target Strafe", Category.COMBAT);
        this.mode = new ModeSetting("Mode", "Normal", new String[] { "Normal", "Back" });
        this.distance = new NumberSetting("Distance", 2.0, 1.0, 4.0, 0.1);
        this.controllable = new BooleanSetting("Controllable", true);
        this.jumpOnly = new BooleanSetting("Space only", true);
        this.thirdPerson = new BooleanSetting("Third person", false);
        this.smart = new BooleanSetting("Smart", true);
        this.liquidCheck = new BooleanSetting("Liquid check", false, aBoolean -> !this.smart.isEnabled());
        this.voidCheck = new BooleanSetting("Void check", true, aBoolean -> !this.smart.isEnabled());
        this.strafeDelay = new MilliTimer();
        this.prev = -1;
        this.strafe = 1.0f;
        this.addSettings(this.mode, this.distance, this.thirdPerson, this.smart, this.voidCheck, this.liquidCheck, this.controllable, this.jumpOnly);
    }
    
    public boolean isUsing() {
        return KillAura.target != null && this.isToggled() && (TargetStrafe.mc.gameSettings.keyBindJump.isKeyDown() || !this.jumpOnly.isEnabled()) && ((OringoClient.speed.isToggled() && !Speed.isDisabled()) || OringoClient.fly.isFlying());
    }
    
    @SubscribeEvent
    public void onMove(final MoveStateUpdateEvent event) {
        if (this.isUsing() && (TargetStrafe.mc.currentScreen == null || Module.getModule(GuiMove.class).isToggled())) {
            if (this.thirdPerson.isEnabled()) {
                if (this.prev == -1) {
                    this.prev = TargetStrafe.mc.gameSettings.thirdPersonView;
                }
                TargetStrafe.mc.gameSettings.thirdPersonView = 1;
            }
            if (this.controllable.isEnabled() && (TargetStrafe.mc.gameSettings.keyBindRight.isKeyDown() || TargetStrafe.mc.gameSettings.keyBindLeft.isKeyDown())) {
                if (TargetStrafe.mc.gameSettings.keyBindLeft.isKeyDown()) {
                    this.strafe = 1.0f;
                }
                if (TargetStrafe.mc.gameSettings.keyBindRight.isKeyDown()) {
                    this.strafe = -1.0f;
                }
            }
            else if (this.strafeDelay.hasTimePassed(200L)) {
                if (TargetStrafe.mc.thePlayer.isCollidedHorizontally || (this.smart.isEnabled() && !OringoClient.fly.isFlying() && ((this.voidCheck.isEnabled() && TargetStrafe.mc.thePlayer.fallDistance < 2.5 && PlayerUtils.isFall(6.0f, (TargetStrafe.mc.thePlayer.posX - TargetStrafe.mc.thePlayer.prevPosX) * 2.5, (TargetStrafe.mc.thePlayer.posZ - TargetStrafe.mc.thePlayer.prevPosZ) * 2.5)) || (this.liquidCheck.isEnabled() && !TargetStrafe.mc.thePlayer.isInLava() && !TargetStrafe.mc.thePlayer.isInWater() && PlayerUtils.isLiquid(3.0f, (TargetStrafe.mc.thePlayer.posX - TargetStrafe.mc.thePlayer.prevPosX) * 2.5, (TargetStrafe.mc.thePlayer.posZ - TargetStrafe.mc.thePlayer.prevPosZ) * 2.5))))) {
                    this.strafe = -this.strafe;
                    this.strafeDelay.reset();
                }
                else if (this.mode.is("Back")) {
                    final Entity entity = (Entity)KillAura.target;
                    final float yaw = (entity.rotationYaw - 90.0f) % 360.0f;
                    final double x = Math.cos(yaw * 3.141592653589793 / 180.0) * this.distance.getValue() + entity.posX;
                    final double z = Math.sin(yaw * 3.141592653589793 / 180.0) * this.distance.getValue() + entity.posZ;
                    if (this.getDistance(x, z, TargetStrafe.mc.thePlayer.posX, TargetStrafe.mc.thePlayer.posZ) > 0.2 && this.getDistance(x, z, TargetStrafe.mc.thePlayer.posX, TargetStrafe.mc.thePlayer.posZ) > this.getDistance(this.lastx, this.lasty, x, z)) {
                        this.strafe = -this.strafe;
                        this.strafeDelay.reset();
                    }
                    this.lastx = TargetStrafe.mc.thePlayer.posX;
                    this.lasty = TargetStrafe.mc.thePlayer.posZ;
                }
            }
            if (this.getDistance((Entity)KillAura.target) <= this.distance.getValue() + 2.0 || (this.controllable.isEnabled() && (TargetStrafe.mc.gameSettings.keyBindRight.isKeyDown() || TargetStrafe.mc.gameSettings.keyBindLeft.isKeyDown()))) {
                event.setStrafe(this.strafe);
            }
            event.setForward(TargetStrafe.mc.gameSettings.keyBindBack.isKeyDown() ? -1.0f : ((float)((this.getDistance((Entity)KillAura.target) > this.distance.getValue()) ? 1 : 0)));
        }
        else if (this.thirdPerson.isEnabled() && this.prev != -1) {
            TargetStrafe.mc.gameSettings.thirdPersonView = this.prev;
            this.prev = -1;
        }
    }
    
    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
        if (this.isUsing()) {
            final Entity entity = (Entity)KillAura.target;
            final float partialTicks = event.partialTicks;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            GL11.glDisable(3553);
            GL11.glDisable(2884);
            GL11.glDisable(2929);
            GL11.glShadeModel(7425);
            GlStateManager.disableLighting();
            GL11.glTranslated(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - TargetStrafe.mc.getRenderManager().viewerPosX, entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - TargetStrafe.mc.getRenderManager().viewerPosY + 0.1, entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - TargetStrafe.mc.getRenderManager().viewerPosZ);
            final double radius = this.distance.getValue();
            GL11.glLineWidth(4.0f);
            GL11.glBegin(2);
            for (int angles = 90, i = 0; i <= angles; ++i) {
                final Color color = Color.white;
                GL11.glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
                GL11.glVertex3d(Math.cos(i * 3.141592653589793 / (angles / 2.0)) * radius, 0.0, Math.sin(i * 3.141592653589793 / (angles / 2.0)) * radius);
            }
            GL11.glEnd();
            GL11.glPopMatrix();
            GL11.glShadeModel(7424);
            GL11.glEnable(2929);
            GL11.glEnable(2884);
            GlStateManager.resetColor();
            GL11.glEnable(3553);
            GL11.glDisable(3042);
            GL11.glDisable(2848);
        }
    }
    
    double getDistance(final double x, final double z, final double x1, final double z1) {
        final double x2 = x1 - x;
        final double z2 = z1 - z;
        return Math.sqrt(x2 * x2 + z2 * z2);
    }
    
    double getDistance(final Entity entity) {
        return Math.hypot(entity.posX - TargetStrafe.mc.thePlayer.posX, entity.posZ - TargetStrafe.mc.thePlayer.posZ);
    }
    
    @SubscribeEvent
    public void onMoveFly(final MoveFlyingEvent event) {
        if (this.isUsing()) {
            event.setYaw(RotationUtils.getRotations(KillAura.target).getYaw());
        }
    }
}
