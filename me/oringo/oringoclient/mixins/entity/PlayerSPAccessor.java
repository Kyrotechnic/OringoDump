//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package me.oringo.oringoclient.mixins.entity;

import org.spongepowered.asm.mixin.*;
import net.minecraft.client.entity.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin({ EntityPlayerSP.class })
public interface PlayerSPAccessor
{
    @Accessor
    double getLastReportedPosX();
    
    @Accessor
    void setLastReportedPosX(final double p0);
    
    @Accessor
    double getLastReportedPosY();
    
    @Accessor
    void setLastReportedPosY(final double p0);
    
    @Accessor
    double getLastReportedPosZ();
    
    @Accessor
    void setLastReportedPosZ(final double p0);
    
    @Accessor
    float getLastReportedYaw();
    
    @Accessor
    void setLastReportedYaw(final float p0);
    
    @Accessor
    float getLastReportedPitch();
    
    @Accessor
    void setLastReportedPitch(final float p0);
    
    @Accessor
    void setServerSprintState(final boolean p0);
    
    @Accessor
    boolean getServerSprintState();
    
    @Accessor
    void setServerSneakState(final boolean p0);
    
    @Accessor
    boolean getServerSneakState();
}
