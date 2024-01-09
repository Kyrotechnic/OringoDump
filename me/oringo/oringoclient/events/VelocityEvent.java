//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class VelocityEvent extends Event
{
    private double x;
    private double y;
    private double z;
    
    public VelocityEvent setY(final double y) {
        this.y = y;
        return this;
    }
    
    public VelocityEvent setX(final double x) {
        this.x = x;
        return this;
    }
    
    public VelocityEvent setZ(final double z) {
        this.z = z;
        return this;
    }
    
    public double getX() {
        return this.x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public VelocityEvent setMotion(final double x, final double y, final double z) {
        this.x = x;
        this.z = z;
        this.y = y;
        return this;
    }
    
    public VelocityEvent(final double x, final double y, final double z) {
        this.x = x;
        this.z = z;
        this.y = y;
    }
}
