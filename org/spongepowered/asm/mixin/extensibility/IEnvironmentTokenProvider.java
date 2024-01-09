//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.extensibility;

import org.spongepowered.asm.mixin.*;

public interface IEnvironmentTokenProvider
{
    public static final int DEFAULT_PRIORITY = 1000;
    
    int getPriority();
    
    Integer getToken(final String p0, final MixinEnvironment p1);
}
