//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;

public class UpdateRenderEvent extends Event
{
    public float partialTicks;
    
    public UpdateRenderEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public static class Pre extends UpdateRenderEvent
    {
        public Pre(final float partialTicks) {
            super(partialTicks);
        }
    }
    
    public static class Post extends UpdateRenderEvent
    {
        public Post(final float partialTicks) {
            super(partialTicks);
        }
    }
}
