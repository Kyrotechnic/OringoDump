//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class MoveFlyingEvent extends Event
{
    private float friction;
    private float yaw;
    private float forward;
    private float strafe;
    
    public float getYaw() {
        return this.yaw;
    }
    
    public float getForward() {
        return this.forward;
    }
    
    public float getFriction() {
        return this.friction;
    }
    
    public float getStrafe() {
        return this.strafe;
    }
    
    public MoveFlyingEvent setYaw(final float yaw) {
        this.yaw = yaw;
        return this;
    }
    
    public MoveFlyingEvent setForward(final float forward) {
        this.forward = forward;
        return this;
    }
    
    public MoveFlyingEvent setFriction(final float friction) {
        this.friction = friction;
        return this;
    }
    
    public MoveFlyingEvent setStrafe(final float strafe) {
        this.strafe = strafe;
        return this;
    }
    
    public MoveFlyingEvent(final float forward, final float strafe, final float friction, final float yaw) {
        this.forward = forward;
        this.friction = friction;
        this.strafe = strafe;
        this.yaw = yaw;
    }
}
