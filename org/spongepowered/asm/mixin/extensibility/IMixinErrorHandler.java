//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\lukes\OneDrive\Desktop\deobfer\1.8.9 MAPPINGS"!

//Decompiled by Procyon!

package org.spongepowered.asm.mixin.extensibility;

import org.apache.logging.log4j.*;

public interface IMixinErrorHandler
{
    ErrorAction onPrepareError(final IMixinConfig p0, final Throwable p1, final IMixinInfo p2, final ErrorAction p3);
    
    ErrorAction onApplyError(final String p0, final Throwable p1, final IMixinInfo p2, final ErrorAction p3);
    
    public enum ErrorAction
    {
        NONE(Level.INFO), 
        WARN(Level.WARN), 
        ERROR(Level.FATAL);
        
        public final Level logLevel;
        
        private ErrorAction(final Level logLevel) {
            this.logLevel = logLevel;
        }
    }
}
