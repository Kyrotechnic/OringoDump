//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class StepEvent extends Event
{
    private double height;
    
    public StepEvent(final double height) {
        this.height = height;
    }
    
    public double getHeight() {
        return this.height;
    }
    
    public void setHeight(final double height) {
        this.height = height;
    }
    
    public static class Pre extends StepEvent
    {
        public Pre(final double height) {
            super(height);
        }
    }
    
    public static class Post extends StepEvent
    {
        public Post(final double height) {
            super(height);
        }
    }
}
