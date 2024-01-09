//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;

@Cancelable
public class MoveEvent extends Event
{
    private double x;
    private double y;
    private double z;
    
    public MoveEvent setY(final double y) {
        this.y = y;
        return this;
    }
    
    public MoveEvent setX(final double x) {
        this.x = x;
        return this;
    }
    
    public MoveEvent setZ(final double z) {
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
    
    public MoveEvent stop() {
        return this.setMotion(0.0, 0.0, 0.0);
    }
    
    public MoveEvent setMotion(final double x, final double y, final double z) {
        this.x = x;
        this.z = z;
        this.y = y;
        return this;
    }
    
    public MoveEvent(final double x, final double y, final double z) {
        this.x = x;
        this.z = z;
        this.y = y;
    }
}
