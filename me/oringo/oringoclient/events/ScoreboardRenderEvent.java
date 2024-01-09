//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.events;

import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.scoreboard.*;
import net.minecraft.client.gui.*;

@Cancelable
public class ScoreboardRenderEvent extends Event
{
    public ScoreObjective objective;
    public ScaledResolution resolution;
    
    public ScoreboardRenderEvent(final ScoreObjective objective, final ScaledResolution resolution) {
        this.objective = objective;
        this.resolution = resolution;
    }
}
