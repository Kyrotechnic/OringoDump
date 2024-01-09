//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.utils;

import me.oringo.oringoclient.*;
import net.minecraft.entity.*;
import me.oringo.oringoclient.events.*;
import me.oringo.oringoclient.qolfeatures.module.impl.combat.*;

public class MovementUtils
{
    public static MilliTimer strafeTimer;
    
    public static float getSpeed() {
        return (float)Math.sqrt(OringoClient.mc.thePlayer.motionX * OringoClient.mc.thePlayer.motionX + OringoClient.mc.thePlayer.motionZ * OringoClient.mc.thePlayer.motionZ);
    }
    
    public static float getSpeed(final double x, final double z) {
        return (float)Math.sqrt(x * x + z * z);
    }
    
    public static void strafe() {
        strafe(getSpeed());
    }
    
    public static boolean isMoving() {
        return OringoClient.mc.thePlayer.moveForward != 0.0f || OringoClient.mc.thePlayer.moveStrafing != 0.0f;
    }
    
    public static boolean hasMotion() {
        return OringoClient.mc.thePlayer.motionX != 0.0 && OringoClient.mc.thePlayer.motionZ != 0.0 && OringoClient.mc.thePlayer.motionY != 0.0;
    }
    
    public static boolean isOnGround(final double height) {
        return !OringoClient.mc.theWorld.getCollidingBoundingBoxes((Entity)OringoClient.mc.thePlayer, OringoClient.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }
    
    public static void strafe(final double speed) {
        if (!isMoving()) {
            return;
        }
        final double yaw = getDirection();
        OringoClient.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        OringoClient.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
        MovementUtils.strafeTimer.reset();
    }
    
    public static void strafe(final float speed, final float yaw) {
        if (!isMoving() || !MovementUtils.strafeTimer.hasTimePassed(150L)) {
            return;
        }
        OringoClient.mc.thePlayer.motionX = -Math.sin(Math.toRadians(yaw)) * speed;
        OringoClient.mc.thePlayer.motionZ = Math.cos(Math.toRadians(yaw)) * speed;
        MovementUtils.strafeTimer.reset();
    }
    
    public static void forward(final double length) {
        final double yaw = Math.toRadians(OringoClient.mc.thePlayer.rotationYaw);
        OringoClient.mc.thePlayer.setPosition(OringoClient.mc.thePlayer.posX + -Math.sin(yaw) * length, OringoClient.mc.thePlayer.posY, OringoClient.mc.thePlayer.posZ + Math.cos(yaw) * length);
    }
    
    public static double getDirection() {
        return Math.toRadians(getYaw());
    }
    
    public static void setMotion(final MoveEvent em, final double speed) {
        double forward = OringoClient.mc.thePlayer.movementInput.moveForward;
        double strafe = OringoClient.mc.thePlayer.movementInput.moveStrafe;
        float yaw = ((KillAura.target != null && OringoClient.killAura.movementFix.isEnabled()) || OringoClient.targetStrafe.isUsing()) ? RotationUtils.getRotations(KillAura.target).getYaw() : OringoClient.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            OringoClient.mc.thePlayer.motionX = 0.0;
            OringoClient.mc.thePlayer.motionZ = 0.0;
            if (em != null) {
                em.setX(0.0);
                em.setZ(0.0);
            }
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            final double cos = Math.cos(Math.toRadians(yaw + 90.0f));
            final double sin = Math.sin(Math.toRadians(yaw + 90.0f));
            OringoClient.mc.thePlayer.motionX = forward * speed * cos + strafe * speed * sin;
            OringoClient.mc.thePlayer.motionZ = forward * speed * sin - strafe * speed * cos;
            if (em != null) {
                em.setX(OringoClient.mc.thePlayer.motionX);
                em.setZ(OringoClient.mc.thePlayer.motionZ);
            }
        }
    }
    
    public static float getYaw() {
        float yaw = ((KillAura.target != null && OringoClient.killAura.movementFix.isEnabled()) || OringoClient.targetStrafe.isUsing()) ? RotationUtils.getRotations(KillAura.target).getYaw() : OringoClient.mc.thePlayer.rotationYaw;
        if (OringoClient.mc.thePlayer.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        if (OringoClient.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (OringoClient.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (OringoClient.mc.thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        if (OringoClient.mc.thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        return yaw;
    }
    
    static {
        MovementUtils.strafeTimer = new MilliTimer();
    }
}
