//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.ui.hud;

import me.oringo.oringoclient.utils.*;
import net.minecraft.client.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;

public class Component
{
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected boolean hidden;
    protected MilliTimer hideTimer;
    protected static final Minecraft mc;
    
    public Component() {
        this.hideTimer = new MilliTimer();
    }
    
    public void onTick() {
    }
    
    protected HudVec drawScreen() {
        return null;
    }
    
    public boolean isHovered(final int mouseX, final int mouseY) {
        return mouseX > this.x && mouseX < this.x + this.width && mouseY > this.y && mouseY < this.y + this.height;
    }
    
    public boolean isHovered() {
        return this.isHovered(getMouseX(), getMouseY());
    }
    
    public boolean isHidden() {
        return this.hidden;
    }
    
    public Component setPosition(final double x, final double y) {
        this.x = x;
        this.y = y;
        return this;
    }
    
    public Component setHidden(final boolean hidden) {
        if (hidden != this.hidden) {
            this.hidden = hidden;
            this.hideTimer.reset();
        }
        return this;
    }
    
    public Component setSize(final double width, final double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    
    protected static int getMouseX() {
        return Mouse.getX() * getScaledResolution().getScaledWidth() / Component.mc.displayWidth;
    }
    
    protected static int getMouseY() {
        final int height = getScaledResolution().getScaledHeight();
        return height - Mouse.getY() * height / Component.mc.displayHeight - 1;
    }
    
    protected static ScaledResolution getScaledResolution() {
        return new ScaledResolution(Component.mc);
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public double getWidth() {
        return this.width;
    }
    
    public double getY() {
        return this.y;
    }
    
    public double getX() {
        return this.x;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
